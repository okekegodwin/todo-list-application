package com.todo_list_app.service

import com.todo_list_app.dto.TodoDTO
import com.todo_list_app.entity.Todo
import com.todo_list_app.exception.TodoNotFoundException
import com.todo_list_app.repository.TodoRepository
import com.todo_list_app.repository.UserRepository
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class TodoService(val todoRepository: TodoRepository, val userRepository: UserRepository) {

    companion object : KLogging()

    @Transactional
    fun addTodo(todoDTO: TodoDTO, userId: Int) : TodoDTO {

        val user = userRepository.findById(userId).orElseThrow { RuntimeException("User not found") }

        val todoEntity = Todo(
            id = null,
            title = todoDTO.title,
            description = todoDTO.description,
            dueDate = todoDTO.dueDate,
            priorityLevel = todoDTO.priorityLevel,
            user = user
        )

        todoRepository.save(todoEntity)

        logger.info("Saved task is : $todoEntity")

        return todoEntity.let {
            TodoDTO(it.id!!, it.title, it.description, it.dueDate, it.priorityLevel)
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