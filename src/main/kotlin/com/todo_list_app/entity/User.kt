package com.todo_list_app.entity

import jakarta.persistence.*


@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val username: String,
    val email: String,
    val password: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val todo: List<Todo> = emptyList()
)