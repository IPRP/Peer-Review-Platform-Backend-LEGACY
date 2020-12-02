package com.iprp.backend

import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import com.iprp.backend.repos.WrapperRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-26
 */
@SpringBootTest(
        properties = ["spring.data.mongodb.database=test_db"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataManagementTests {
    @Autowired
    lateinit var dm: DataManagement

    @Autowired
    lateinit var repo: WrapperRepository

    @BeforeEach
    fun setupEach() {
        repo.deleteAll()
    }

    @Test
    fun addPersons() {
        // Add student and teacher
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")

        // Fetch all
        val foundPersons = repo.personRepository.findAll()

        Assertions.assertEquals(2, foundPersons.size)
    }

    @Test
    fun addWorkshop() {
        // Add workshop
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop",
            true, LocalDateTime.now(), listOf(mapOf("type" to "point", "content" to "abc"))
        )

        val foundWorkshops = repo.workshopRepository.findAll()

        Assertions.assertEquals(1, foundWorkshops.size)
    }

    @Test
    fun deleteWorkshop() {
        // Add workshop
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop",
            true, LocalDateTime.now(), listOf(mapOf("type" to "point", "content" to "abc"))
        )
        val workshop = repo.workshopRepository.findAll().first()!!
        dm.deleteWorkshop(workshop.id)

        val foundWorkshops = repo.workshopRepository.findAll()

        Assertions.assertEquals(0, foundWorkshops.size)
    }
}