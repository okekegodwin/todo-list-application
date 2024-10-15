package com.todo_list_app.controller

import com.todo_list_app.config.JwtUtil
import com.todo_list_app.config.MyUserDetails
import com.todo_list_app.dto.LoginDTO
import com.todo_list_app.dto.UserDTO
import com.todo_list_app.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(val userService: UserService, val jwtUtil: JwtUtil) {

    @PostMapping("/signup")
    fun signup(@RequestBody userDTO: UserDTO): String {
        val user = userService.registerUser(
            email = userDTO.email,
            password = userDTO.password,
            username = userDTO.username
        )
        return "User ${user.username} registered successfully"
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<String?> {
        val user = userService.authenticateUser(
            email = loginDTO.email,
            password = loginDTO.password
        ) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password")


        val userDetails: UserDetails = MyUserDetails(user)

        val jwtToken: String = jwtUtil.generateToken(userDetails)
        println("Generated JWT Token: $jwtToken")

        return ResponseEntity.ok(jwtToken)
    }
}