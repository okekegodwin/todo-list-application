package com.todo_list_app.controller

import com.todo_list_app.config.MyUserDetails
import com.todo_list_app.dto.TodoDTO
import com.todo_list_app.entity.Todo
import com.todo_list_app.entity.User
import com.todo_list_app.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tasks")
class TodoController(val todoService: TodoService) {

    // Create Request
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTodo(@RequestBody todoDTO: TodoDTO) : TodoDTO {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as MyUserDetails
        val userId = userDetails.user.id ?: throw RuntimeException("User with ID not found")
        println("Adding Todo: $todoDTO")
        println("User ID: $userId")
        return todoService.addTodo(todoDTO, userId)
    }

    // Read Request
    @GetMapping
    fun getAllTodo() : List<TodoDTO> = todoService.getAllTodo()

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Int) : Todo {
        return todoService.getTodoById(id)
    }

    // Update Request
    @PutMapping("/{id}")
    fun updateTodo(@RequestBody todoDTO: TodoDTO, @PathVariable("id") todoId : Int) = todoService.updateTodo(todoId, todoDTO)

    // Delete Request
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTodo(@PathVariable("id") todoId : Int) = todoService.deleteTodo(todoId)
}