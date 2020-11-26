package com.iprp.backend.repos

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.grade.GradeCollection
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.submission.SubmissionRound
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
    lateinit var gradeCollectionRepository: GradeCollectionRepository
    @Autowired
    lateinit var gradeRepository: GradeRepository

    /**
     * Person
     */

    fun savePerson(p: Person) {
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

    fun saveWorkshop(w: Workshop): Workshop {
        return workshopRepository.save(w)
    }


    fun findAllWorkshops(personId: String): List<Workshop> {
        if (studentRepository.findById(personId).isPresent) {
            return workshopRepository.findByStudents(personId)
        }
        return workshopRepository.findByTeachers(personId)
    }

    fun allWorkshops(): List<Workshop> {
        return workshopRepository.findAll()
    }

    /**
     * Submission
     */
    fun saveSubmission(s: Submission): Submission {
        return submissionRepository.save(s)
    }

    fun saveSubmissionRound(sr: SubmissionRound): SubmissionRound {
        return submissionRoundRepository.save(sr)
    }

    /**
     * Review
     */
    fun saveReviewCriteria(c: ReviewCriteria): ReviewCriteria {
        return reviewCriteriaRepository.save(c)
    }

    /**
     * Grade
     */
    fun saveGrade(g: Grade): Grade {
        return gradeRepository.save(g)
    }

    fun saveGradeCollection(gr: GradeCollection): GradeCollection {
        return gradeCollectionRepository.save(gr)
    }

    fun findGradeCollection(studentId: String, workshopId: String): GradeCollection? {
        return gradeCollectionRepository.findByStudentAndWorkshop(studentId, workshopId)
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
        gradeCollectionRepository.deleteAll()
        submissionRepository.deleteAll()
        submissionRoundRepository.deleteAll()
        reviewCriteriaRepository.deleteAll()
    }

}