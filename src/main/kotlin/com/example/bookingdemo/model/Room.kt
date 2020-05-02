package com.example.bookingdemo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Room(
    @Id
    val id: UUID = UUID.randomUUID(),
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)