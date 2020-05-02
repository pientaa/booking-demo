package com.example.bookingdemo.service

import com.example.bookingdemo.model.Room
import com.example.bookingdemo.repository.BookingRepository
import com.example.bookingdemo.repository.RoomRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomService(
    private val roomRepository: RoomRepository,
    private val bookingRepository: BookingRepository
) {
    fun save(room: Room): Room = roomRepository.save(room)

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