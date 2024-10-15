package com.todo_list_app.config

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(jwtSecret.toByteArray())
    }

    fun generateToken(user: UserDetails): String {
        val claims = Jwts.claims().setSubject(user.username)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()
    }

    fun extractUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
            val isTokenExpired = claims.expiration.before(Date())
            return !isTokenExpired && userDetails.username == claims.subject
        } catch (e: JwtException) {
            println("JWT Validation Error: ${e.message}")
            false
        }
    }

    fun getAuthenticationToken(token: String, userDetails: UserDetails): Authentication {
        return UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
    }

}
