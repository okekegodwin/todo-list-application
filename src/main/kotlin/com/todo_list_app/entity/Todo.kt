package com.todo_list_app.entity

import com.fasterxml.jackson.annotation.JsonBackReference
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
) {
    constructor() : this(
        id = null,
        title = "",
        description = "",
        dueDate = LocalDate.now(),
        priorityLevel = "",
        user = null
    )
}