package com.example.bookingdemo.query.domain.room

import com.example.bookingdemo.common.model.Room
import com.example.bookingdemo.common.repository.BookingRepository
import com.example.bookingdemo.common.repository.RoomRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomQueryService(
    private val roomRepository: RoomRepository,
    private val bookingRepository: BookingRepository
) {
    fun getAll(): List<Room> = roomRepository.findAll()

    fun getAllAvailableRoomsBetween(start: Instant, end: Instant): List<Room> {
        val roomsAlreadyBooked =
            (bookingRepository.findAllByStartBetween(start, end) +
                    bookingRepository.findAllByStartLessThanEqualAndEndGreaterThan(start, start))
                .distinctBy { it.id }
                .map { it.roomId }

        return getAll().filter { it.id !in roomsAlreadyBooked }
    }
}