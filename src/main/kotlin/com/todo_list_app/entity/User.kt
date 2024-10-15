package com.todo_list_app.entity

import com.fasterxml.jackson.annotation.*
import jakarta.persistence.*


@Entity
@Table(name = "app_user")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val username: String = "",
    val email: String = "",
    val password: String = "",

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var todos: List<Todo> = mutableListOf()
)