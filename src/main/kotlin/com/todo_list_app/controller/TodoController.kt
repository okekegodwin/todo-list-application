package com.todo_list_app.controller

import com.todo_list_app.dto.TodoDTO
import com.todo_list_app.entity.Todo
import com.todo_list_app.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/tasks")
class TodoController(val todoService: TodoService) {

    // Create Request
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addTodo(@RequestBody todoDTO: TodoDTO) : TodoDTO {
        return todoService.addTodo(todoDTO)
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