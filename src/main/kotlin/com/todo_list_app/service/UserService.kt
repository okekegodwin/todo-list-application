package com.todo_list_app.service

import com.todo_list_app.config.MyUserDetails
import com.todo_list_app.entity.User
import com.todo_list_app.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) : UserDetailsService {

    fun registerUser(username: String, email: String, password: String): User {
        val encodedPassword = passwordEncoder.encode(password)
        val user = User(username = username, email = email, password = encodedPassword)
        return userRepository.save(user)
    }

    fun authenticateUser(email: String, password: String): User? {

        val user = userRepository.findByEmail(email)

        return if (user != null && passwordEncoder.matches(password, user.password)) {
            user
        } else {
            null
        }
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User with username $username not found")

        return MyUserDetails(user)
    }
}