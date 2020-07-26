package com.example.bookingdemo

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.listeners.Listener
import io.kotest.spring.SpringListener

class ProjectConfig : AbstractProjectConfig() {
    override fun listeners(): List<Listener> = listOf(SpringListener)
}