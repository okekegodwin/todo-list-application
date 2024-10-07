package com.todo_list_app.dto

import java.time.LocalDate

data class TodoDTO (
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val priorityLevel: String
)