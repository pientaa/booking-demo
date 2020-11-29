package com.example.bookingdemo.command

import com.example.bookingdemo.common.infastructure.EventPublisher
import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.model.Room
import com.example.bookingdemo.common.model.UnInitializedRoom
import com.example.bookingdemo.common.model.event.EventStore
import com.example.bookingdemo.common.model.value.Booking
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class RoomBookingCommandService(
    private val store: EventStore,
    private val eventPublisher: EventPublisher
) {
    fun createRoom(command: CreateRoom) {
        val room = getRoom(command.number)

        val event = command.toEvent(room.number)

        room.handle(event)
            .also { store.saveEvent(event) }
            .also { eventPublisher.publish(event) }
    }

    fun createBooking(command: CreateBooking) {
        val room = getRoom(command.number)

        val overlappingBookings = room.getUpcomingBookingsBetween(command.start, command.end)

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(command.number, command.start, command.end)

        val event = command.toEvent()

        room.handle(event)
            .also { store.saveEvent(event) }
            .also { eventPublisher.publish(event) }
    }

    fun updateBooking(command: UpdateBooking) {
        val room = getRoom(command.number)

        val overlappingBookings = room.getUpcomingBookingsBetween(command.start, command.end)
            .filter { it.id != command.number }

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(command.number, command.start, command.end)

        val event = command.toEvent()

        room.handle(event)
            .also { store.saveEvent(event) }
            .also { eventPublisher.publish(event) }
    }

    fun cancelBooking(command: CancelBooking) {
        val room = getRoom(command.number)

//        room.handle(BookingCancelled(command.bookingId))
//        store.save(room)

        //        publish: BookingCancelled
    }

    private fun Room.getUpcomingBookingsBetween(start: Instant, end: Instant): List<Booking> {
        val now = LocalDateTime.now().toInstant(ZoneOffset.UTC)
        return bookings.values.filter { booking ->
            !booking.end.isBefore(now) &&
                    booking.end.isAfter(start) && booking.start.isBefore(end)
        }
    }

    private fun getRoom(aggregateId: String): Room {
        val events = store.findByAggregateId(aggregateId)?.events ?: mutableListOf()
        return (UnInitializedRoom(aggregateId)
            .addAll(events)
            .getAggregate() as Room)
    }
}