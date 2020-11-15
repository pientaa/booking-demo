package com.example.bookingdemo.domain.booking

import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.query.domain.booking.Booking
import com.example.bookingdemo.query.domain.booking.BookingService
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
class BookingServiceAnnotationSpec(private val bookingService: BookingService) : AnnotationSpec() {

    @BeforeAll
    fun cleanUp() {
        bookingService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingService.deleteById(it.id!!) }
    }

    @Test
    fun `create booking, happy path`() {
        // given
        val booking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead2",
            start = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 14, 0).toInstant(ZoneOffset.UTC)
        )

        // when
        val createdBooking = bookingService.save(booking)

        // then
        assertEquals(booking.copy(id = createdBooking.id), createdBooking)
    }

    @Test
    fun `create booking, conflict`() {
        // given
        val booking = Booking(
            id = null,
            roomId = "5eada3967f3a1726878aead1",
            start = LocalDateTime.of(2020, 5, 13, 10, 0).toInstant(ZoneOffset.UTC),
            end = LocalDateTime.of(2020, 5, 13, 12, 0).toInstant(ZoneOffset.UTC)
        )
        bookingService.save(booking)

        // then
        assertThrows<RoomConflictException> { bookingService.save(booking) }
    }
}