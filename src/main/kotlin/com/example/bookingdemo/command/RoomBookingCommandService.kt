package com.example.bookingdemo.command

import com.example.bookingdemo.command.api.dto.BookingDTO
import com.example.bookingdemo.command.api.dto.RoomDTO
import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.infastructure.RoomNotFoundException
import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.common.model.Room
import com.example.bookingdemo.common.model.RoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class RoomBookingCommandService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(command: CreateRoom): RoomDTO =
        RoomDTO((roomRepository.findByNumber(command.number) ?: roomRepository.save(Room(command))))

    fun createBooking(command: CreateBooking): BookingDTO {
        val room = findByIdOrException(command.roomId)

        val overlappingBookings = room.getUpcomingBookingsBetween(command.start, command.end)

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(command.roomId, command.start, command.end)

        val booking = room.addBooking(Booking(command))

        roomRepository.save(room)
        return BookingDTO(booking)
    }

    fun updateBooking(command: UpdateBooking): BookingDTO {
        val room = findByIdOrException(command.roomId)

        val overlappingBookings = room.getUpcomingBookingsBetween(command.start, command.end)
            .filter { it.id != command.roomId }

        if (overlappingBookings.isNotEmpty())
            throw RoomConflictException(command.roomId, command.start, command.end)

        val booking = room.updateBooking(command.bookingId, Booking(command))

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

    private fun Room.getUpcomingBookingsBetween(start: Instant, end: Instant): List<Booking> {
        val now = LocalDateTime.now().toInstant(ZoneOffset.UTC)
        return bookings.values.filter { booking ->
            !booking.end.isBefore(now) && booking.isNotCancelled() &&
                    booking.end.isAfter(start) && booking.start.isBefore(end)
        }
    }
}