package com.example.bookingdemo.common.model.event

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

open class DomainEvent(
    val eventType: String,
    val id: String = UUID.randomUUID().toString(),
    var aggregateId: String,
    val createdOn: Instant = LocalDateTime.now().toInstant(ZoneOffset.UTC)
)