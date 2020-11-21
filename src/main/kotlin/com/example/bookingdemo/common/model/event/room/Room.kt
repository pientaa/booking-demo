package com.example.bookingdemo.common.model.event.room

import com.example.bookingdemo.common.model.event.AggregateRoot
import com.example.bookingdemo.common.model.event.booking.Booking
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

    fun addBooking(booking: Booking): String {
        //TODO: Check if not conflicting

        bookings[booking.id] = booking

        registerEvent(BookingCreated(booking))

        return booking.id
    }

    fun updateBooking(booking: Booking) {
        getBooking(bookingId = booking.id)

        //TODO: Check if not conflicting with another (not himself)

        registerEvent(BookingUpdated(booking))
    }

    private fun getBooking(bookingId: String) = bookings[bookingId] ?: throw Exception() //TODO
    fun getId(): String = roomId ?: throw Exception()
}