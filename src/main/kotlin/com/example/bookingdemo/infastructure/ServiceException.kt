package com.example.bookingdemo.infastructure

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.*

open class ServiceException(
    private val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val message: String
) : ResponseStatusException(httpStatus, message)

class RoomNotFoundException(
    roomId: String
) : ServiceException(HttpStatus.NOT_FOUND, "Room with id: $roomId not found")

class BookingNotFoundException(
    bookingId: String
) : ServiceException(HttpStatus.NOT_FOUND, "Booking with id: $bookingId not found")

class RoomConflictException(
    roomId: String, start: Instant, end: Instant
) : ServiceException(HttpStatus.CONFLICT, "Room with id: $roomId is already booked in period: $start - $end")
