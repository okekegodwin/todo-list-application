package com.todo_list_app.repository

import com.todo_list_app.entity.Todo
import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {
}