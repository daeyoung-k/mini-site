package com.auth.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TodoList {
    private val logger = KotlinLogging.logger {}

    val todosList: MutableList<Todo> = mutableListOf(Todo("user1", "todo1"), Todo("user2", "todo2"))

    @GetMapping("/todo")
    fun allTodos(): List<Todo> {
        return todosList
    }

    @GetMapping("/todo/{username}")
    fun getTodo(@PathVariable username: String): List<Todo> {
        return todosList.filter { it.username == username }
    }

    @PostMapping("/todo/update")
    fun updateTodo(
        @RequestBody todo: Todo
    ): List<Todo> {
        todosList.add(todo)
        logger.info { "updateTodo: $todosList"}
        return todosList
    }

}



data class Todo(
    val username: String,
    val description: String,
)