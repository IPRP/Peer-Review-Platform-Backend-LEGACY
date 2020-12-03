package com.iprp.backend

import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import com.iprp.backend.repos.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import kotlin.reflect.full.memberProperties

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
    lateinit var personRepo: PersonRepository
    lateinit var studentRepo: StudentRepository
    lateinit var teacherRepo: TeacherRepository
    lateinit var reviewRepo: ReviewRepository
    lateinit var reviewCriteriaRepo: ReviewCriteriaRepository
    lateinit var workshopRepo: WorkshopRepository
    lateinit var submissionRepo: SubmissionRepository

    @BeforeAll
    fun setup() {
        personRepo = Helper.readInstanceProperty(repo, "personRepository")
        studentRepo = Helper.readInstanceProperty(repo, "studentRepository")
        teacherRepo = Helper.readInstanceProperty(repo, "teacherRepository")
        reviewRepo = Helper.readInstanceProperty(repo, "reviewRepository")
        reviewCriteriaRepo = Helper.readInstanceProperty(repo, "reviewCriteriaRepository")
        workshopRepo = Helper.readInstanceProperty(repo, "workshopRepository")
        submissionRepo = Helper.readInstanceProperty(repo, "submissionRepository")
    }

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
        val foundPersons = personRepo.findAll()

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

        val foundWorkshops = workshopRepo.findAll()

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
        val workshop = workshopRepo.findAll().first()!!
        dm.deleteWorkshop(workshop.id)

        val foundWorkshops = workshopRepo.findAll()

        Assertions.assertEquals(0, foundWorkshops.size)
    }
}