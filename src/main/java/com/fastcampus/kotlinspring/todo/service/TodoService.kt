package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.api.model.TodoRequest
import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.by
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class TodoService(
        val todoRepository: TodoRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Todo> =
            todoRepository.findAll(by(Sort.Direction.DESC, "id"))

    @Transactional(readOnly = true)
    fun findById(id: Long): Todo = todoRepository.findByIdOrNull(id = id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @Transactional
    fun create(request: TodoRequest): Todo {
        checkNotNull(request) { "TodoRequest is null" }

        // 위에서 not null 체크를 해서 request 항상 존재함.
        // 아래는 안전 연산자가 필요없다. (none null 이 되므로)
        val todo = Todo(
                title = request.title,
                description = request.description,
                done = request.done,
                createdAt = request.createAt
        )

        return todoRepository.save(todo)
    }

    @Transactional
    fun update(id: Long, request: TodoRequest): Todo {
        checkNotNull(request) { "TodoRequest is null" }

        // 여기도 없을 경우 exception 발생
        // 그래서 data 가 항상 존재함.

        return findById(id = id).let {
            it.update(request.title, request.description, request.done)
            todoRepository.save(it)
        }
    }

    fun delete(id: Long) = todoRepository.deleteById(id)
}