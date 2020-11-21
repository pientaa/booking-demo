package com.example.bookingdemo.common.model

import com.example.bookingdemo.command.CreateRoom
import com.example.bookingdemo.common.infastructure.BookingNotFoundException
import com.example.bookingdemo.common.model.event.*
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

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

    fun addBooking(booking: Booking): Booking {
        bookings[booking.id] = booking

        registerEvent(BookingCreated(booking))

        return booking
    }

    fun updateBooking(bookingId: String, booking: Booking): Booking {
        getBooking(bookingId = bookingId)
            .updateBooking(newStart = booking.start, newEnd = booking.end)

        registerEvent(BookingUpdated(booking))

        return booking
    }

    fun cancelBooking(bookingId: String) {
        getBooking(bookingId = bookingId)
            .cancelBooking()

        registerEvent(BookingCancelled(bookingId))
    }

    private fun getBooking(bookingId: String) = bookings[bookingId] ?: throw BookingNotFoundException(bookingId)
    fun getId(): String = roomId ?: throw Exception()
}