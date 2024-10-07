package com.todo_list_app.service

import com.todo_list_app.dto.TodoDTO
import com.todo_list_app.entity.Todo
import com.todo_list_app.exception.TodoNotFoundException
import com.todo_list_app.repository.TodoRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class TodoService(val todoRepository: TodoRepository) {

    companion object : KLogging()

    fun addTodo(todoDTO: TodoDTO) : TodoDTO {
        val todoEntity = todoDTO.let {
            Todo(id = null, it.title, it.description, it.dueDate, it.priorityLevel)
        }

        todoRepository.save(todoEntity)

        logger.info("Saved task is : $todoEntity")

        return todoEntity.let {
            it.id?.let { it1 -> TodoDTO(it1, it.title, it.description, it.dueDate, it.priorityLevel) }!!
        }
    }

    fun getAllTodo(): List<TodoDTO> {
        return todoRepository.findAll()
            .map {
                it.id?.let { it1 -> TodoDTO(it1, it.title, it.description, it.dueDate, it.priorityLevel) }!!
            }
    }

    fun getTodoById(id: Int): Todo {
        return todoRepository.findById(id)
            .orElseThrow{ RuntimeException("Todo with id $id not found") }
    }

    fun updateTodo(todoId: Int, todoDTO: TodoDTO): TodoDTO? {
        val existingTodo = todoRepository.findById(todoId)

        return if (existingTodo.isPresent) {
            existingTodo.get()
                .let {
                    it.title = todoDTO.title
                    it.description = todoDTO.description
                    it.dueDate = todoDTO.dueDate
                    it.priorityLevel = todoDTO.priorityLevel
                    todoRepository.save(it)
                    it.id?.let { it1 -> TodoDTO(it1, it.title, it.description, it.dueDate, it.priorityLevel) }
                }
        } else {
            throw TodoNotFoundException("No todo found with the passed in ID $todoId")
        }
    }

    fun deleteTodo(todoId: Int) {
        val existingTodo = todoRepository.findById(todoId)

        if (existingTodo.isPresent) {
            existingTodo.get()
                .let {
                    todoRepository.deleteById(todoId)
                }
        } else {
            throw TodoNotFoundException("No todo found with the passed in ID $todoId")
        }
    }
}