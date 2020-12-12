package com.example.bookingdemo.query.infrastructure

import com.example.bookingdemo.command.domain.room.BookingCancelled
import com.example.bookingdemo.command.domain.room.BookingCreated
import com.example.bookingdemo.command.domain.room.BookingRescheduled
import com.example.bookingdemo.command.domain.room.RoomCreated
import com.example.bookingdemo.common.event.DomainEvent
import com.example.bookingdemo.common.infastructure.RabbitEvent
import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingRepository
import com.example.bookingdemo.query.domain.room.Room
import com.example.bookingdemo.query.domain.room.RoomRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["event_queue"])
class EventListener(
    private val bookingRepository: BookingRepository,
    private val roomRepository: RoomRepository
) {
    companion object {
        val mapper: ObjectMapper
            get() = jacksonObjectMapper().apply {
                registerModule(JavaTimeModule())
                registerKotlinModule()
            }
    }

    @RabbitHandler
    fun handle(message: String) {
        val rabbitEvent: RabbitEvent = mapper.readValue(message)

        val actualEvent = when (rabbitEvent.eventType) {
            "RoomCreated" -> mapper.readValue(rabbitEvent.eventLoad) as RoomCreated
            "BookingCreated" -> mapper.readValue(rabbitEvent.eventLoad) as BookingCreated
            "BookingUpdated" -> mapper.readValue(rabbitEvent.eventLoad) as BookingRescheduled
            "BookingCancelled" -> mapper.readValue(rabbitEvent.eventLoad) as BookingCancelled
            else -> throw IllegalStateException()
        }
        handle(actualEvent)
    }

    private fun handle(event: DomainEvent) {
        when (event) {
            is RoomCreated -> if (roomRepository.findByNumber(event.number) == null) roomRepository.save(Room(event))
            is BookingCreated -> bookingRepository.save(Booking(event))
            is BookingRescheduled -> bookingRepository.save(Booking(event))
            is BookingCancelled -> bookingRepository.deleteById(event.bookingId)
        }
    }
}