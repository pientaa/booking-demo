package com.example.bookingdemo.command.domain

import com.example.bookingdemo.command.domain.room.*
import com.example.bookingdemo.common.infastructure.BookingDateException
import com.example.bookingdemo.common.infastructure.InvalidDateTimeParamsException
import java.time.*
import java.util.*

class AddRoom(
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
) {
    fun toEvent(number: String): RoomCreated =
        RoomCreated(number, hasWhiteboard, hasProjector)
}

class CreateBooking(
    val roomNumber: String,
    var start: Instant,
    var end: Instant
) {
    fun toEvent(bookingId: String = UUID.randomUUID().toString()): BookingCreated =
        BookingCreated(bookingId, roomNumber, start, end)

    init {
        validateDateTimeParams(start, end)
    }
}

class RescheduleBooking(
    val roomNumber: String,
    val bookingId: String,
    var start: Instant,
    var end: Instant
) {
    fun toEvent(): RoomEvent =
        BookingRescheduled(bookingId, roomNumber, start, end)

    init {
        validateDateTimeParams(start, end)
    }
}

class CancelBooking(
    val roomNumber: String,
    val bookingId: String
) {
    fun toEvent(): BookingCancelled =
        BookingCancelled(
            bookingId = bookingId,
            roomNumber = roomNumber
        )
}

private fun validateDateTimeParams(start: Instant, end: Instant) {
    if (start.isAfter(end)) throw InvalidDateTimeParamsException()
    val today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.UTC)
    if (start.isBefore(today)) throw BookingDateException()
}