package com.todo_list_app.repository

import com.todo_list_app.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
}