package com.example.bookingdemo.command

import com.example.bookingdemo.common.infastructure.BookingDateException
import com.example.bookingdemo.common.infastructure.RoomNotFoundException
import com.example.bookingdemo.common.model.event.booking.Booking
import com.example.bookingdemo.common.model.event.room.Room
import com.example.bookingdemo.common.model.event.room.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@Service
class RoomBookingCommandService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(command: CreateRoom): String =
        roomRepository.findByNumber(command.number)?.roomId ?: roomRepository.save(Room(command)).getId()

    fun createBooking(command: CreateBooking): String {


        val room = findByIdOrException(command.roomId)
        val bookingId = room.addBooking(Booking(command))

        roomRepository.save(room)
        return bookingId
    }

    fun updateBooking(command: UpdateBooking): String {
        val today = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).toInstant(ZoneOffset.UTC)
        if (command.start.isBefore(today)) throw BookingDateException()

        val room = findByIdOrException(command.roomId)

        val bookingId = room.updateBooking(Booking(command))

        roomRepository.save(room)
        return bookingId
    }

    fun cancelBooking(command: CancelBooking) {
        val room = findByIdOrException(command.roomId)
        room.cancelBooking(command.bookingId)
        roomRepository.save(room)
    }

    private fun findByIdOrException(roomId: String) =
        roomRepository.findByIdOrNull(roomId) ?: throw RoomNotFoundException(roomId = roomId)
}