package com.example.bookingdemo.common.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

abstract class DomainEvent(
    val type: String,
    val id: UUID = UUID.randomUUID(),
    val createdOn: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC)
)