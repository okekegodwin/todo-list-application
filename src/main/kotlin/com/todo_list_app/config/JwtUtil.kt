package com.todo_list_app.config

import com.todo_list_app.entity.User
import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    private val secretKey = "your-secret-key"

    fun generateToken(user: User): String {
        val claims = Jwts.claims().setSubject(user.username)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            return false
        }
    }
}
