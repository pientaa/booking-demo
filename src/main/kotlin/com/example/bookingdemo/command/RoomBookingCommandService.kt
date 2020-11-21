package com.example.bookingdemo.command

import com.example.bookingdemo.common.model.event.booking.Booking
import com.example.bookingdemo.common.model.event.room.Room
import com.example.bookingdemo.common.model.event.room.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoomBookingCommandService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(command: CreateRoom): String =
        roomRepository.findByNumber(command.number)?.roomId ?: roomRepository.save(Room(command)).getId()

    fun createBooking(command: CreateBooking): String {
        val room = roomRepository.findByIdOrNull(command.roomId) ?: throw Exception() //TODO
        val bookingId = room.addBooking(Booking(command))

        roomRepository.save(room)
        return bookingId
    }
}