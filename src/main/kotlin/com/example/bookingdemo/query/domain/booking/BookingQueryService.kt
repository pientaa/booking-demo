package com.example.bookingdemo.query.domain.booking

import com.example.bookingdemo.common.infastructure.BookingNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class BookingQueryService(
    private val bookingRepository: BookingRepository
) {

    fun getById(bookingId: String): Booking = bookingRepository.findByIdOrNull(bookingId)
            ?: throw BookingNotFoundException(bookingId)

    fun getAllByRoomNumberAndBetween(roomNumber: String?, fromDate: Instant?, toDate: Instant?): List<Booking> =
        when {
            roomNumber == null -> getAllBetween(fromDate, toDate)
            fromDate == null && toDate == null -> bookingRepository.findAllByRoomNumber(roomNumber)
            fromDate == null -> bookingRepository.findAllByRoomNumberAndStartBefore(roomNumber, toDate!!)
            toDate == null -> bookingRepository.findAllByRoomNumberAndEndAfter(roomNumber, fromDate)
            else -> bookingRepository.let {
                it.findAllByRoomNumberAndStartBetween(roomNumber, fromDate, toDate) +
                        it.findAllByRoomNumberAndStartLessThanEqualAndEndGreaterThan(roomNumber, fromDate, fromDate)
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