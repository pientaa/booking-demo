package com.example.bookingdemo.command.infrastructure

import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {
    @Bean
    fun queue(): Queue = QueueBuilder.durable("event_queue")
        .withArgument(
            "x-dead-letter-exchange",
            "dead_letter_exchange"
        )
        .withArgument(
            "x-dead-letter-routing-key",
            "dead_letter_queue"
        )
        .build()
}