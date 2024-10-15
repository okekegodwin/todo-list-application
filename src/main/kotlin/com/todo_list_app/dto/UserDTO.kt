package com.todo_list_app.dto

data class UserDTO (
    val id: Int? = null,
    val username: String,
    val password: String,
    val email: String
//    val password: String
)