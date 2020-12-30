package com.iprp.backend

import com.iprp.backend.data.review.ReviewCriterionType
import com.iprp.backend.repos.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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

        assertEquals(2, foundPersons.size)
    }

    @Test
    fun testSearch() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addStudent("s2", "John", "Doe", "3A")

        val s1Id = dm.searchStudent("Max", "Mustermann")
        val s1 = dm.searchStudent("s1")
        val s2Id = dm.searchStudent("John", "Doe")
        val s2 = dm.searchStudent("s2")
        val ids = dm.searchStudents("3A")

        assertEquals("s1", s1Id["id"])
        assertEquals("Max", s1["firstname"])
        assertEquals("Mustermann", s1["lastname"])
        assertEquals("s2", s2Id["id"])
        assertEquals("John", s2["firstname"])
        assertEquals("Doe", s2["lastname"])
        assertEquals(listOf("s1", "s2"), ids["ids"])
    }

    @Test
    fun addWorkshop() {
        // Add workshop
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )

        val foundWorkshops = workshopRepo.findAll()

        assertEquals(1, foundWorkshops.size)
    }

    @Test
    fun updateWorkshop() {
        // Add workshop
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("title" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )
        val workshop = workshopRepo.findByTeachers("t1").first()

        val end = LocalDateTime.now()
        dm.updateWorkshop(
            workshop.id, listOf("t1", "t2"), listOf("s1", "s2"), "Workshop", "My Workshop", end,
            listOf(mapOf("title" to "Criterion", "type" to "truefalse", "content" to "abc2", "weight" to "20"))
        )
        val updatedWorkshop = workshopRepo.findByTeachers("t1").first()
        val updatedCriteria = reviewCriteriaRepo.findFirstById(updatedWorkshop.criteria)!!

        assertEquals(listOf("t1", "t2"), updatedWorkshop.teachers)
        assertEquals(listOf("s1", "s2"), updatedWorkshop.students)
        assertEquals("Workshop", updatedWorkshop.title)
        assertEquals("My Workshop", updatedWorkshop.content)
        // Compare time with AssertJ
        // See: https://stackoverflow.com/a/1698926/12347616
        assertThat(end).isCloseTo(updatedWorkshop.end, within(1, ChronoUnit.MINUTES))
        assertTrue(updatedCriteria.criteria.first().title == "Criterion")
        assertTrue(updatedCriteria.criteria.first().type == ReviewCriterionType.TrueFalse)
        assertTrue(updatedCriteria.criteria.first().content == "abc2")
        assertTrue(updatedCriteria.criteria.first().weight == 20)
    }

    @Test
    fun deleteWorkshop() {
        // Add workshop
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        val id = dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop",
            true, LocalDateTime.now(), listOf(mapOf("type" to "point", "content" to "abc"))
        )["id"] as String

        dm.deleteWorkshop(id)

        val foundWorkshops = workshopRepo.findAll()

        assertEquals(0, foundWorkshops.size)
    }

    @Test
    fun getAllWorkshopsTeacher() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )

        val workshops = dm.getAllWorkshops("t1")["workshops"]!!

        assertEquals(1, workshops.size)
        assertEquals("workshop", workshops.first()["title"])
        assertNotNull(workshops.first()["id"])
    }

    @Test
    fun getAllWorkshopsStudent() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )

        val workshops = dm.getAllWorkshops("s1")["workshops"]!!

        assertEquals(1, workshops.size)
        assertEquals("workshop", workshops.first()["title"])
        assertNotNull(workshops.first()["id"])
    }

    @Test
    fun getWorkshopTeacher() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        val id = dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )["id"] as String

        val response = dm.getTeacherWorkshop(id)
        val workshop = response["workshop"] as Map<*, *>

        assertNotNull(workshop)
        assertEquals("workshop", workshop["title"])
        assertEquals("my workshop", workshop["content"])
        assertEquals(true, workshop["anonymous"])
        assertNotNull(workshop["end"])
        assertEquals(1, (workshop["students"] as List<*>).size)
        assertEquals(1, (workshop["teachers"] as List<*>).size)
    }

    @Test
    fun getWorkshopStudent() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        val id = dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )["id"] as String

        val response = dm.getStudentWorkshop("s1", id)
        val workshop = response["workshop"] as Map<*, *>

        assertNotNull(workshop)
        assertEquals("workshop", workshop["title"])
        assertEquals("my workshop", workshop["content"])
        assertNotNull(workshop["end"])
        assertEquals(1, (workshop["students"] as List<*>).size)
        assertEquals(1, (workshop["teachers"] as List<*>).size)
        assertEquals(0, (workshop["submissions"] as List<*>).size)
        assertEquals(0, (workshop["reviews"] as List<*>).size)
    }

    @Test
    fun getStudentTODOs() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        dm.addWorkshop(
            listOf("t1"), listOf("s1"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )

        val response = dm.getStudentTodos("s1")

        assertNotNull(response)
        assertEquals(0, (response["reviews"] as List<*>).size)
        assertEquals(1, (response["submissions"] as List<*>).size)
        assertEquals("workshop", ((response["submissions"] as List<*>).first() as Map<*, *>)["workshopName"])
    }

    @Test
    fun addSubmissionToWorkshop() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addStudent("s2", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        val id = dm.addWorkshop(
            listOf("t1"), listOf("s1", "s2"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )["id"] as String

        dm.addSubmissionToWorkshop("s1", id, "S1 submission", "Very Good", listOf())
        val submissions = submissionRepo.findAll()
        val reviews = reviewRepo.findAll()

        assertEquals(1, submissions.size)
        assertEquals(1, reviews.size)
    }

    @Test
    fun addSubmissionToWorkshopAndTestStudentTODO() {
        dm.addStudent("s1", "Max", "Mustermann", "3A")
        dm.addStudent("s2", "Max", "Mustermann", "3A")
        dm.addTeacher("t1", "John", "Doe")
        val id = dm.addWorkshop(
            listOf("t1"), listOf("s1", "s2"), "workshop", "my workshop", true, LocalDateTime.now(),
            listOf(mapOf("name" to "criterion", "type" to "point", "content" to "abc", "weight" to "10"))
        )["id"] as String

        dm.addSubmissionToWorkshop("s1", id, "S1 submission", "Very Good", listOf())
        val review = reviewRepo.findAll().first()!!
        val response = dm.getStudentTodos("s2")
        val todoReview = ((response["reviews"] as List<*>).first() as Map<*, *>)

        assertEquals(review.id, todoReview["id"])
    }


}