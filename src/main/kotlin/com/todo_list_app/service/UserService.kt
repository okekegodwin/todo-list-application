package com.todo_list_app.service

import com.todo_list_app.entity.User
import com.todo_list_app.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) {

    fun registerUser(username: String, email: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(username = username, email = email, password = encodedPassword)
        return userRepository.save(user)
    }

    fun authenticateUser(username: String, password: String): User? {
        val user = userRepository.findByUsername(username)
        return if (user != null && passwordEncoder.matches(password, user.password)) {
            user
        } else {
            null
        }
    }
}