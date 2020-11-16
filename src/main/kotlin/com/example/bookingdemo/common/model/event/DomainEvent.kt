package com.example.bookingdemo.common.model.event

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

open class DomainEvent(
    var eventType: String,
    var id: String = UUID.randomUUID().toString(),
    var aggregateId: String,
    var createdOn: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC)
)