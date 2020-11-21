package com.example.bookingdemo.command

import com.example.bookingdemo.command.api.BookingDTO
import com.example.bookingdemo.command.api.RoomDTO
import com.example.bookingdemo.common.infastructure.RoomNotFoundException
import com.example.bookingdemo.common.model.event.booking.Booking
import com.example.bookingdemo.common.model.event.room.Room
import com.example.bookingdemo.common.model.event.room.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RoomBookingCommandService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(command: CreateRoom): RoomDTO =
        RoomDTO((roomRepository.findByNumber(command.number) ?: roomRepository.save(Room(command))))

    fun createBooking(command: CreateBooking): BookingDTO {
        val room = findByIdOrException(command.roomId)
        val booking = room.addBooking(Booking(command))

        roomRepository.save(room)
        return BookingDTO(booking)
    }

    fun updateBooking(command: UpdateBooking): BookingDTO {
        val room = findByIdOrException(command.roomId)

        val booking = room.updateBooking(Booking(command))

        roomRepository.save(room)
        return BookingDTO(booking)
    }

    fun cancelBooking(command: CancelBooking) {
        val room = findByIdOrException(command.roomId)
        room.cancelBooking(command.bookingId)
        roomRepository.save(room)
    }

    private fun findByIdOrException(roomId: String) =
        roomRepository.findByIdOrNull(roomId) ?: throw RoomNotFoundException(roomId = roomId)
}