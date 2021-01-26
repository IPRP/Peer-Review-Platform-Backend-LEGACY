package com.iprp.backend

import com.iprp.backend.repos.WrapperRepository
import com.iprp.backend.tools.MongoUtilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.time.LocalDateTime


@SpringBootApplication
@EnableScheduling
@Suppress("RedundantModalityModifier") // See: https://stackoverflow.com/a/48545043/12347616
open class BackendApplication : CommandLineRunner {

    @Autowired
    lateinit var dm: DataManagement

    @Autowired
    lateinit var repo: WrapperRepository

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

    override fun run(vararg args: String?) {
        dm.addStudent("georg", "Georg", "Reisinger", "3D")
        dm.addStudent("georg", "Lukas", "Nowy", "3D")
        if (args.contains("student")) {
            repo.deleteAll()
            // Add student debug data
            dm.addStudent("s1", "Max", "Mustermann", "3A")
            dm.addStudent("s2", "Luke", "Skywalker", "3A")
            dm.addStudent("s3", "Gordon", "Freeman", "3A")
            dm.addStudent("s4", "Mario", "Mario", "3A")
            dm.addTeacher("t1", "John", "Doe")
            dm.addTeacher("t2", "John", "Doe  II")
            dm.addWorkshop(
                listOf("t1"), listOf("s1", "s2", "s3", "s4"), "Workshop 1", "Great Workshop", true, LocalDateTime.now(),
                listOf(mapOf("title" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
            )
            dm.addWorkshop(
                listOf("t2"), listOf("s1", "s2"), "Workshop 2", "Better Workshop", true, LocalDateTime.now(),
                listOf(mapOf("title" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
            )
        }
    }

}


