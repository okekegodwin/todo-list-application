package com.todo_list_app.repository

import com.todo_list_app.entity.Todo
import com.todo_list_app.entity.User
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {
    fun findByUser(user: User): List<Todo>
}