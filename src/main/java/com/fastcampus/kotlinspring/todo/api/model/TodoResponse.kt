package com.fastcampus.kotlinspring.todo.api.model

import com.fastcampus.kotlinspring.todo.domain.Todo
import java.time.LocalDateTime

data class TodoResponse(
        val id: Long,
        val title: String,
        val description: String,
        val done: Boolean,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime?
) {
    companion object {
        fun of(todo: Todo?): TodoResponse {
            checkNotNull(todo) { "Todo is null" }
            // 위에서 id 의 Null 을 체크해 주거나
            checkNotNull(todo.id) { "Todo id is null" }

            // todoResponse 가 있을 때 id 는 null 이 될 수 없음을 명시
            return TodoResponse(
                    id = todo.id!!,
                    title = todo.title,
                    description = todo.description,
                    done = todo.done,
                    createdAt = todo.createdAt,
                    updatedAt = todo.updatedAt,
            )
        }
    }
}