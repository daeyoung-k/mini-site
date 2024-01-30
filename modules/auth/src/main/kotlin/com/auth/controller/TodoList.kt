package com.auth.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.web.bind.annotation.*

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping("/api")
class TodoList {

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

    @GetMapping("/csrf")
    fun getCsrf(
        request: HttpServletRequest
    ): CsrfToken {
        logger.info { request }
        return request.getAttribute(CsrfToken::class.java.name) as CsrfToken
    }
}



data class Todo(
    val username: String,
    val description: String,
)