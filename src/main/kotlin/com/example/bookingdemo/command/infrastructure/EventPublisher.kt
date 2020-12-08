package com.example.bookingdemo.command.infrastructure

import com.example.bookingdemo.command.domain.room.BookingCancelled
import com.example.bookingdemo.command.domain.room.BookingCreated
import com.example.bookingdemo.command.domain.room.BookingRescheduled
import com.example.bookingdemo.command.domain.room.RoomCreated
import com.example.bookingdemo.common.event.DomainEvent
import com.example.bookingdemo.common.infastructure.RabbitEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class EventPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    companion object {
        val mapper: ObjectMapper
            get() = jacksonObjectMapper().apply {
                registerModule(JavaTimeModule())
                registerKotlinModule()
            }
    }

    fun publish(event: DomainEvent) {
        when (event) {
            is RoomCreated -> RabbitEvent("RoomCreated", mapper.writeValueAsString(event))
            is BookingCreated -> RabbitEvent("BookingCreated", mapper.writeValueAsString(event))
            is BookingRescheduled -> RabbitEvent("BookingUpdated", mapper.writeValueAsString(event))
            is BookingCancelled -> RabbitEvent("BookingCancelled", mapper.writeValueAsString(event))
            else -> throw IllegalStateException()
        }
            .let {
                rabbitTemplate.convertAndSend("event_queue", mapper.writeValueAsString(it))
            }
    }
}