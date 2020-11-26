package com.iprp.backend

import com.iprp.backend.tools.MongoUtilities
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
@Suppress("RedundantModalityModifier") // See: https://stackoverflow.com/a/48545043/12347616
open class BackendApplication : CommandLineRunner {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Start MongoDB via start scripts
            // Works on Windows + Ubuntu
            MongoUtilities.start()
            // Start Spring
            runApplication<BackendApplication>(*args)
        }
    }

    override fun run(vararg args: String?) {}

}


