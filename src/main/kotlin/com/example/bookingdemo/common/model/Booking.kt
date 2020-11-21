package com.example.bookingdemo.common.model

import com.example.bookingdemo.command.CreateBooking
import com.example.bookingdemo.command.UpdateBooking
import java.time.Instant
import java.util.*

class Booking(
    val id: String,
    val roomId: String,
    var start: Instant,
    var end: Instant
) {
    constructor(createBooking: CreateBooking) : this(
        id = UUID.randomUUID().toString(),
        roomId = createBooking.roomId,
        start = createBooking.start,
        end = createBooking.end
    )

    constructor(updatedBooking: UpdateBooking) : this(
        id = updatedBooking.bookingId,
        roomId = updatedBooking.roomId,
        start = updatedBooking.start,
        end = updatedBooking.end
    )

    private var state: BookingState = BookingState.ACTIVE

    fun cancelBooking() {
        state = BookingState.CANCELLED
    }

    fun isNotCancelled(): Boolean = this.state != BookingState.CANCELLED

    enum class BookingState {
        ACTIVE,
        CANCELLED
    }
}
