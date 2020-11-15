package com.example.bookingdemo.query.domain.booking

import com.example.bookingdemo.common.infastructure.BookingNotFoundException
import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.repository.BookingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BookingQueryService(
    private val bookingRepository: BookingRepository
) {

    fun getById(bookingId: String): Booking = bookingRepository.findByIdOrNull(bookingId)
            ?: throw BookingNotFoundException(bookingId)

    fun getAllByRoomIdAndBetween(roomId: String?, fromDate: Instant?, toDate: Instant?): List<Booking> =
        when {
            roomId == null -> getAllBetween(fromDate, toDate)
            fromDate == null && toDate == null -> bookingRepository.findAllByRoomId(roomId)
            fromDate == null -> bookingRepository.findAllByRoomIdAndStartBefore(roomId, toDate!!)
            toDate == null -> bookingRepository.findAllByRoomIdAndEndAfter(roomId, fromDate)
            else -> bookingRepository.let {
                it.findAllByRoomIdAndStartBetween(roomId, fromDate, toDate) +
                        it.findAllByRoomIdAndStartLessThanEqualAndEndGreaterThan(roomId, fromDate, fromDate)
            }.distinctBy { it.id }
        }
            .sortedBy { it.start }

    fun getAllBetween(fromDate: Instant?, toDate: Instant?): List<Booking> =
        when {
            fromDate == null && toDate == null -> bookingRepository.findAll()
            fromDate == null -> bookingRepository.findAllByStartBefore(toDate!!)
            toDate == null -> bookingRepository.findAllByEndAfter(fromDate)
            else -> bookingRepository.let {
                it.findAllByStartBetween(fromDate, toDate) +
                        it.findAllByStartLessThanEqualAndEndGreaterThan(fromDate, fromDate)
            }.distinctBy { it.id }
        }
}