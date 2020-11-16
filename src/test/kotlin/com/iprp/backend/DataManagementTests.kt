package com.iprp.backend

import com.iprp.backend.data.repos.PersonRepository
import com.iprp.backend.data.repos.StudentRepository
import com.iprp.backend.data.repos.TeacherRepository
import com.iprp.backend.data.repos.WrapperRepository
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.BeforeTestClass


// Add custom properties
// See: https://stackoverflow.com/a/40606120/12347616
@SpringBootTest(
        properties = ["spring.data.mongodb.database=test_db"]
)
// Needed to make setup non-static
// See: https://stackoverflow.com/a/55720750/12347616
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataManagementTests {

    @Autowired
    lateinit var repo: WrapperRepository
    lateinit var personRepo: PersonRepository
    lateinit var studentRepo: StudentRepository
    lateinit var teacherRepo: TeacherRepository


    @BeforeAll
    fun setup() {
        personRepo = repo.personRepository
        studentRepo = repo.studentRepository
        teacherRepo = repo.teacherRepository
    }

    @BeforeEach
    fun setupEach() {
        repo.deleteAll()
    }


    @Test
    fun getAllPersons() {
        // Save something
        personRepo.save(Student("a", "Max", "Mustermann"))
        personRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all
        val foundPersons = personRepo.findAll()

        Assertions.assertEquals(2, foundPersons.size)
    }

    @Test
    fun getAllStudents() {
        // Save something
        personRepo.save(Student("a", "Max", "Mustermann"))
        personRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all students
        val foundStudents = studentRepo.findAll()

        Assertions.assertEquals(1, foundStudents.size)
    }

    /**
    @Test
    fun getAllPersons() {
        // Save something
        studentRepo.save(Student("a", "Max", "Mustermann"))
        teacherRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all
        val foundPersons = repo.findAllPersons()

        Assertions.assertEquals(2, foundPersons.size)
    }

    @Test
    fun getAllStudents() {
        // Save something
        studentRepo.save(Student("a", "Max", "Mustermann"))
        teacherRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all students
        val foundStudents = studentRepo.findAll()

        Assertions.assertEquals(1, foundStudents.size)
    }*/

}
