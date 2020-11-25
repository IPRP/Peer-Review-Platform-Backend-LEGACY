package com.iprp.backend.repos

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.user.Person
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */
@Component
class WrapperRepository {

    @Autowired
    lateinit var personRepository: PersonRepository
    @Autowired
    lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var teacherRepository: TeacherRepository
    @Autowired
    lateinit var reviewRepository: ReviewRepository
    @Autowired
    lateinit var reviewCriteriaRepository: ReviewCriteriaRepository
    @Autowired
    lateinit var workshopRepository: WorkshopRepository
    @Autowired
    lateinit var submissionRepository: SubmissionRepository
    @Autowired
    lateinit var submissionRoundRepository: SubmissionRoundRepository
    @Autowired
    lateinit var gradeRepository: GradeRepository
    @Autowired
    lateinit var gradeRoundRepository: GradeRoundRepository

    /**
     * Person
     */

    fun addPerson(p: Person) {
        personRepository.save(p)
    }

    fun findAllPersons(): List<Person> {
        return personRepository.findAll()
    }

    fun findAllStudentsByIdIn(ids: List<String>): List<Student> {
        return studentRepository.findByIdIn(ids)
    }

    fun findAllTeachersByIdIn(ids: List<String>): List<Teacher> {
        return teacherRepository.findByIdIn(ids)
    }


    /**
     * Workshop
     */

    fun addWorkshop(w: Workshop): Workshop {
        return workshopRepository.save(w)
    }

    fun findAllWorkshops(personId: String): List<Workshop> {
        if (studentRepository.findById(personId).isPresent) {
            return workshopRepository.findByStudentsId(personId)
        }
        return workshopRepository.findByTeachersId(personId)
    }


    /**
     * Overall
     */

    /**
     * Deletes all documents from all repositories.
     */
    fun deleteAll() {
        personRepository.deleteAll()
        reviewRepository.deleteAll()
        workshopRepository.deleteAll()
        gradeRepository.deleteAll()
        gradeRoundRepository.deleteAll()
        submissionRepository.deleteAll()
        submissionRoundRepository.deleteAll()
        reviewCriteriaRepository.deleteAll()
    }

}