package com.todo_list_app.entity

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "Todo")
data class Todo (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    var title: String,
    var description: String,
    var dueDate: LocalDate,
    var priorityLevel: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)