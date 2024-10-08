package com.todo_list_app.controller

import com.todo_list_app.config.JwtUtil
import com.todo_list_app.dto.UserDTO
import com.todo_list_app.entity.User
import com.todo_list_app.service.UserService
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
}