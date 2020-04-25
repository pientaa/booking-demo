package com.example.bookingdemo.room

import org.springframework.stereotype.Service

@Service
class RoomService(
    val roomRepository: RoomRepository
) {
    fun save(room: Room): Room = roomRepository.save(room)
    fun getAll(): List<Room> = roomRepository.findAll()
}