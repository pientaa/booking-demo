package com.example.bookingdemo.domain.booking

import com.example.bookingdemo.common.infastructure.RoomConflictException
import com.example.bookingdemo.common.model.Booking
import com.example.bookingdemo.query.domain.booking.BookingQueryService
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.ZoneOffset

@SpringBootTest
class BookingServiceAnnotationSpec(private val bookingQueryService: BookingQueryService) : AnnotationSpec() {

    @BeforeAll
    fun cleanUp() {
        bookingQueryService.getAllBetween(fromDate = null, toDate = null)
            .forEach { bookingQueryService.deleteById(it.id!!) }
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
        val createdBooking = bookingQueryService.save(booking)

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
        bookingQueryService.save(booking)

        // then
        assertThrows<RoomConflictException> { bookingQueryService.save(booking) }
    }
}