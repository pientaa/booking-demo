package com.example.bookingdemo.query.domain.room

import com.example.bookingdemo.query.domain.room.Room
import org.springframework.data.mongodb.repository.MongoRepository

interface RoomRepository : MongoRepository<Room, String>