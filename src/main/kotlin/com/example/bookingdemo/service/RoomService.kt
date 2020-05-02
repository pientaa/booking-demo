package com.example.bookingdemo.service

import com.example.bookingdemo.model.Room
import com.example.bookingdemo.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(
    val roomRepository: RoomRepository
) {
    fun save(room: Room): Room = roomRepository.save(room)
    fun getAll(): List<Room> = roomRepository.findAll()
}