package com.iprp.backend

import com.iprp.backend.tools.MongoUtilities
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendApplication : CommandLineRunner {

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


