package com.example.bookingdemo.migrations

import com.github.mongobee.Mongobee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongobeeConfig {
    @Autowired
    var mongoTemplate: MongoTemplate? = null

    @Bean
    fun mongobee(): Mongobee {
        val runner = Mongobee("mongodb://localhost:27017/booking-demo")

        runner.setMongoTemplate(mongoTemplate)

        runner.setChangeLogsScanPackage("com.example.bookingdemo.migrations")
        return runner
    }
}