package com.example.bookingdemo.room

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Room(
    @Id
    val id: String,
    val number: String,
    val hasWhiteboard: Boolean,
    val hasProjector: Boolean
)