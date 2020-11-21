package com.example.bookingdemo.common.model.event.room

import com.example.bookingdemo.command.CreateRoom
import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.model.event.AggregateRoot
import com.example.bookingdemo.common.model.event.booking.Booking
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Document
class Room(
    @Id
    var roomId: String? = null,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) : AggregateRoot<RoomEvent>() {
    constructor(command: CreateRoom) : this(
        number = command.number,
        hasWhiteboard = command.hasWhiteboard,
        hasProjector = command.hasProjector
    )

    var bookings = mutableMapOf<String, Booking>()

    fun addBooking(booking: Booking): String {
        val overlappingBookings = getUpcomingBookingsBetween(booking.start, booking.end)

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(getId(), booking.start, booking.end)

        bookings[booking.id] = booking

        registerEvent(BookingCreated(booking))

        return booking.id
    }

    fun updateBooking(booking: Booking): String {
        val oldBooking = getBooking(bookingId = booking.id)

        val overlappingBookings = getUpcomingBookingsBetween(booking.start, booking.end)
            .filter { it.id != booking.id }

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(getId(), booking.start, booking.end)

        oldBooking.updateBooking(newStart = booking.start, newEnd = booking.end)

        registerEvent(BookingUpdated(booking))

        return booking.id
    }

    fun cancelBooking(bookingId: String) {
        getBooking(bookingId = bookingId)
            .cancelBooking()

        registerEvent(BookingCancelled(bookingId))
    }

    private fun getUpcomingBookingsBetween(start: Instant, end: Instant): List<Booking> {
        val now = LocalDateTime.now().toInstant(ZoneOffset.UTC)
        return bookings.values.filter { booking ->
            !booking.end.isBefore(now) && booking.isNotCancelled() &&
            booking.end.isAfter(start) && booking.start.isBefore(end)
        }
    }

    private fun getBooking(bookingId: String) = bookings[bookingId] ?: throw Exception() //TODO
    fun getId(): String = roomId ?: throw Exception()
}