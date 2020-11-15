package com.example.bookingdemo.query.domain.room

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Room(
    @Id
    val id: String?,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)