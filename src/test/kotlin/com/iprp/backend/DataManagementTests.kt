package com.iprp.backend

import com.iprp.backend.data.Review
import com.iprp.backend.data.Workshop
import com.iprp.backend.repos.*
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


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
    @Autowired
    lateinit var personRepo: PersonRepository
    @Autowired
    lateinit var studentRepo: StudentRepository
    @Autowired
    lateinit var teacherRepo: TeacherRepository
    @Autowired
    lateinit var reviewRepo: ReviewRepository
    @Autowired
    lateinit var workshopRepo: WorkshopRepository


    @BeforeAll
    fun setup() {
        personRepo = repo.personRepository
        studentRepo = repo.studentRepository
        teacherRepo = repo.teacherRepository
        reviewRepo = repo.reviewRepository
        workshopRepo = repo.workshopRepository
    }

    @BeforeEach
    fun setupEach() {
        repo.deleteAll()
    }


    @Test
    fun getAllPersons() {
        // Save something
        personRepo.save(Student("a", "Max", "Mustermann", "3A"))
        personRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all
        val foundPersons = personRepo.findAll()

        Assertions.assertEquals(2, foundPersons.size)
    }

    @Test
    fun getAllStudents() {
        // Save something
        personRepo.save(Student("a", "Max", "Mustermann","3A"))
        personRepo.save(Teacher("b", "John", "Doe"))
        // Fetch all students
        val foundStudents = studentRepo.findAll()

        Assertions.assertEquals(1, foundStudents.size)
        Assertions.assertEquals("3A", foundStudents[0].group)
    }

    @Test
    fun getAllReviews() {
        // Save something
        val student = Student("w", "Max", "Mustermann", "3A")
        personRepo.save(student)
        reviewRepo.save(Review(student))
        reviewRepo.save(Review(student))
        // Fetch all students
        val foundReviews = reviewRepo.findAll()

        Assertions.assertEquals(2, foundReviews.size)
    }

    @Test
    fun workshopStudentSave() {
        // DBref docs
        // See: https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/pdf/spring-data-mongodb-parent-reference.pdf
        // --
        // Save something
        val workShop = Workshop("a", "a", mutableListOf(), mutableListOf())
        val student = Student("w", "Max", "Mustermann", "3A")
        personRepo.save(student)
        workShop.addStudent(student)
        workshopRepo.save(workShop)
        // Update student
        student.group = "3B"
        personRepo.save(student)

        val foundWorkshops = workshopRepo.findAll()
        val studentsFromWorkshopRepo = foundWorkshops[0].students
        val studentsFromStudentRepo = studentRepo.findAll()

        // Check update
        Assertions.assertEquals("3B", studentsFromWorkshopRepo[0].group)
        Assertions.assertEquals("3B", studentsFromStudentRepo[0].group)
    }

    @Test
    fun searchReviews() {
        val student = Student("w", "Max", "Mustermann", "3A")
        personRepo.save(student)
        reviewRepo.save(Review(student))

        val foundReviews = reviewRepo.findByStudentId(student.id)

        Assertions.assertEquals(1, foundReviews.size)
    }


}
