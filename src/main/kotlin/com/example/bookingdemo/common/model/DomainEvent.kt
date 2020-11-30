package com.example.bookingdemo.common.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

abstract class DomainEvent(
    var aggregateId: String,
    var aggregateType: String,
    var occurredAt: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC)
)