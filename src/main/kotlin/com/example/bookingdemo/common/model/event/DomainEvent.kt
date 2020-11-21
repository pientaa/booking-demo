package com.example.bookingdemo.common.model.event

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

abstract class DomainEvent(
    var occuredAt: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC)
)