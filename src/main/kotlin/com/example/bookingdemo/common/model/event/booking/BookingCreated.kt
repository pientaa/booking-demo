package com.example.bookingdemo.common.model.event.booking

import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.model.event.DomainEvent
import java.time.Instant

data class BookingCreated(
    val bookingId: String,
    val roomId: String,
    val start: Instant,
    val end: Instant
) : DomainEvent(eventType = "BookingCreated", aggregateId = roomId) {
    constructor(booking: Booking) : this(
        bookingId = booking.id!!,
        roomId = booking.roomId,
        start = booking.start,
        end = booking.end
    )
}