package com.iprp.backend.repos

import com.iprp.backend.attachments.Attachment
import com.iprp.backend.data.Workshop
import com.iprp.backend.data.review.Review
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.user.Person
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */
@Component
class WrapperRepository {

    // TODO make props private - Tests will need reflection to access them...

    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var studentRepository: StudentRepository
    @Autowired
    private lateinit var teacherRepository: TeacherRepository
    @Autowired
    private lateinit var reviewRepository: ReviewRepository
    @Autowired
    private lateinit var reviewCriteriaRepository: ReviewCriteriaRepository
    @Autowired
    private lateinit var workshopRepository: WorkshopRepository
    @Autowired
    private lateinit var submissionRepository: SubmissionRepository

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

    fun findStudent(id: String): Student? {
        return studentRepository.findFirstById(id)
    }

    fun findStudent(firstname: String, lastname: String): Student? {
        return studentRepository.findFirstByFirstnameAndLastname(firstname, lastname)
    }

    fun findStudents(group: String): List<Student> {
        return studentRepository.findByGroup(group)
    }

    fun isStudent(id: String): Boolean {
        val student = studentRepository.findFirstById(id)
        return student != null
    }

    fun isTeacher(id: String): Boolean {
        val teacher = teacherRepository.findFirstById(id)
        return teacher != null
    }

    /**
     * Workshop
     */

    fun saveWorkshop(w: Workshop): Workshop {
        return workshopRepository.save(w)
    }

    fun deleteWorkshop(workshopId: String) {
        workshopRepository.deleteById(workshopId)
        reviewRepository.deleteByWorkshop(workshopId)
        submissionRepository.deleteByWorkshop(workshopId)
    }

    fun findWorkshop(workshopId: String): Workshop? {
        return workshopRepository.findFirstById(workshopId)
    }

    fun findAllWorkshops(personId: String): List<Workshop> {
        if (studentRepository.findFirstById(personId) != null) {
            return workshopRepository.findByStudents(personId)
        }
        return workshopRepository.findByTeachers(personId)
    }

    fun allWorkshops(): List<Workshop> {
        return workshopRepository.findAll()
    }

    fun deleteStudentSubmissionsAndGradesInWorkshop(studentId: String, workshopId: String) {
        submissionRepository.deleteByStudentAndWorkshop(studentId, workshopId)
    }

    fun deleteStudentSubmissionsInWorkshop(studentId: String, workshopId: String) {
        submissionRepository.deleteByStudentAndWorkshop(studentId, workshopId)
    }

    /**
     * Submission
     */
    fun saveSubmission(s: Submission): Submission {
        return submissionRepository.save(s)
    }

    fun findSubmission(id: String): Submission? {
        return submissionRepository.findFirstById(id)
    }

    fun findAllSubmissionsIn(ids: List<String>): List<Submission> {
        return submissionRepository.findByIdIn(ids)
    }

    fun findAllStudentSubmissionsInWorkshop(studentId: String, workshopId: String): List<Submission> {
        return submissionRepository.findByStudentAndWorkshop(studentId, workshopId)
    }


    fun findSubmissionByIdAndStudentInWorkshop(submissionId: String, studentId: String, workshopId: String): Submission? {
        return submissionRepository.findFirstByIdAndStudentAndWorkshop(submissionId, studentId, workshopId)
    }

    fun findSubmissionByIdAndWorkshop(submissionId: String, workshopId: String): Submission? {
        return submissionRepository.findFirstByIdAndWorkshop(submissionId, workshopId)
    }

    fun findSubmissionByAttachment(attachment: Attachment): Submission? {
        return submissionRepository.findFirstByAttachmentsContaining(listOf(attachment) as MutableList<Attachment>)
    }

    fun countStudentSubmissionsInWorkshop(studentId: String, workshopId: String): Long {
        return submissionRepository.countByStudentAndWorkshop(studentId, workshopId)
    }



    /**
     * Review
     */
    fun saveReviewCriteria(c: ReviewCriteria): ReviewCriteria {
        return reviewCriteriaRepository.save(c)
    }

    fun saveReview(r: Review): Review {
        return reviewRepository.save(r)
    }

    fun findReview(reviewId: String): Review? {
        return reviewRepository.findFirstById(reviewId)
    }

    fun findReview(reviewId: String, workshopId: String): Review? {
        return reviewRepository.findFirstByIdAndWorkshop(reviewId, workshopId)
    }

    fun findAllReviews(reviewIds: List<String>): List<Review> {
        return reviewRepository.findAllById(reviewIds)
    }

    fun findReviewCriteria(criteriaId: String): ReviewCriteria? {
        return reviewCriteriaRepository.findFirstById(criteriaId)
    }

    fun findAllStudentReviewsInWorkshop(studentId: String, workshopId: String): List<Review> {
        return reviewRepository.findByWorkshopAndStudentAndDeadlineGreaterThanEqual(workshopId, studentId, LocalDateTime.now())
    }

    fun findAllReviewsOlderThat(deadline: LocalDateTime): List<Review> {
        return reviewRepository.findByDeadlineLessThanEqual(deadline)
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
        submissionRepository.deleteAll()
        reviewCriteriaRepository.deleteAll()
    }

}