package com.iprp.backend.repos

import com.iprp.backend.data.user.Person
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

    fun findAllPersons(): List<Person>{
        return personRepository.findAll()
    }

    /**
     * Legacy code
     *
    private suspend fun findAllPersonsAsync(): List<Person>{
        // Kotlin with async await
        // See: https://stackoverflow.com/a/52372823/12347616
        val studentJob = GlobalScope.async {
        studentRepository.findAll() as MutableList<Person>
        }
        val teacherJob = GlobalScope.async {
        teacherRepository.findAll() as List<Person>
        }
        val students = studentJob.await()
        val teachers = teacherJob.await()
        students.addAll(teachers)
        return students
    }
     */

}