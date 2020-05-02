package com.example.bookingdemo.repository

import com.example.bookingdemo.infastructure.BookingNotFoundException
import com.example.bookingdemo.model.Booking
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.findByIdOrNull
import java.time.Instant

interface BookingRepository : MongoRepository<Booking, String> {
    fun findAllByStartBefore(toDate: Instant): List<Booking>
    fun findAllByEndAfter(fromDate: Instant): List<Booking>
    fun findAllByStartBetween(fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByStartLessThanEqualAndEndGreaterThan(fromDate: Instant, toDate: Instant): List<Booking>

    fun findAllByRoomId(roomId: String): List<Booking>
    fun findAllByRoomIdAndStartBefore(roomId: String, toDate: Instant): List<Booking>
    fun findAllByRoomIdAndEndAfter(roomId: String, fromDate: Instant): List<Booking>
    fun findAllByRoomIdAndStartBetween(roomId: String, fromDate: Instant, toDate: Instant): List<Booking>
    fun findAllByRoomIdAndStartLessThanEqualAndEndGreaterThan(
        roomId: String, fromDate: Instant, toDate: Instant
    ): List<Booking>

    @JvmDefault
    fun findByIdOrThrow(id: String): Booking =
        findByIdOrNull(id) ?: throw BookingNotFoundException(id)
}