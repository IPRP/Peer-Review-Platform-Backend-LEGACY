package com.iprp.backend.repos

import com.iprp.backend.attachments.Attachment
import com.iprp.backend.data.Workshop
import com.iprp.backend.data.review.Review
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.user.Person
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime


/**
 * File that defines all MongoRepository for CRUD operations.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */

/**@Autowired
lateinit var mongoTemplate: MongoTemplate*/

interface PersonRepository : MongoRepository<Person, String> {
    fun findByIdIn(ids: List<String>): List<Person>
}

interface StudentRepository : MongoRepository<Student, String> {
    fun findByIdIn(ids: List<String>): List<Student>
    fun findFirstById(id: String): Student?
    fun findFirstByFirstnameAndLastname(firstname: String, lastname: String): Student?
    fun findByGroup(group: String): List<Student>
}

interface TeacherRepository : MongoRepository<Teacher, String> {
    fun findByIdIn(ids: List<String>): List<Teacher>
    fun findFirstById(id: String): Teacher?
}




interface WorkshopRepository : MongoRepository<Workshop, String> {
    fun findByStudents(student_id: String): List<Workshop>
    fun findByTeachers(teacher_id: String): List<Workshop>
    fun findFirstById(id: String): Workshop?
}



interface SubmissionRepository : MongoRepository<Submission, String> {
    fun deleteByWorkshop(workshop_id: String)
    fun findFirstById(id: String): Submission?
    fun findFirstByIdAndWorkshop(id: String, workshop_id: String): Submission?
    fun findByIdIn(ids: List<String>): List<Submission>
    fun deleteByStudentAndWorkshop(student_id: String, workshop_id: String)
    fun findByStudentAndWorkshop(student_id: String, workshop_id: String): List<Submission>
    fun findFirstByIdAndStudentAndWorkshop(id: String, student: String, workshop: String): Submission?
    fun countByStudentAndWorkshop(student: String, workshop: String): Long
    fun findFirstByAttachmentsContaining(attachments: MutableList<Attachment>): Submission?
}




interface ReviewRepository : MongoRepository<Review, String> {
    fun findFirstById(review_id: String): Review?
    fun findByStudent(student_id: String): List<Review>
    fun deleteByWorkshop(workshop_id: String)
    fun findByWorkshopAndStudentAndDeadlineGreaterThanEqual(workshop: String, student: String, deadline: LocalDateTime): List<Review>
    fun findByWorkshopAndStudentAndDeadlineLessThanEqual(workshop: String, student: String, deadline: LocalDateTime): List<Review>
}

interface ReviewCriteriaRepository : MongoRepository<ReviewCriteria, String> {
    fun findFirstById(id: String): ReviewCriteria?
}
