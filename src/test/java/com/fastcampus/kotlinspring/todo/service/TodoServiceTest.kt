package com.fastcampus.kotlinspring.todo.service

import com.fastcampus.kotlinspring.todo.domain.Todo
import com.fastcampus.kotlinspring.todo.domain.TodoRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime


@ExtendWith(SpringExtension::class)
class TodoServiceTest {

    @MockkBean
    lateinit var repository: TodoRepository

    lateinit var service: TodoService

    // 변수를 immutable 하게 사용할 수 있다.
    // helper 같은 역할
    val stub: Todo by lazy {
        Todo(
                id = 1,
                title = "test",
                description = "test detail",
                done = false,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
        )
    }

    @BeforeEach
    fun setUp() {
        service = TodoService(repository)
    }

    @Test
    fun `한_개의_TODO를_반환해야한다`() {
        // given
        every { repository.findByIdOrNull(id = 1) }.returns(stub)

        // when
        val actual = service.findById(id = 1)

        // then
        assertThat(actual).isNotNull
        assertThat(actual).isEqualTo(stub)
    }


}