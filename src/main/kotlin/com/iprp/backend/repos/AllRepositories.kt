package com.iprp.backend.repos

import com.iprp.backend.data.review.Review
import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.GradeCollection
import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Person
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * File that defines all [MongoRepository] for CRUD operations.
 *
 * @author Kacper Urbaniec
 * @version 2020-11-20
 */


interface PersonRepository : MongoRepository<Person, String> {
    fun findByIdIn(ids: List<String>): List<Person>
}

interface StudentRepository : MongoRepository<Student, String> {
    fun findByIdIn(ids: List<String>): List<Student>
}

interface TeacherRepository : MongoRepository<Teacher, String> {
    fun findByIdIn(ids: List<String>): List<Teacher>
}




interface WorkshopRepository : MongoRepository<Workshop, String> {
    fun findByStudentsId(student_id: String): List<Workshop>
    fun findByTeachersId(teacher_id: String): List<Workshop>
}




interface SubmissionRepository : MongoRepository<Submission, String>

interface SubmissionRoundRepository : MongoRepository<SubmissionRound, String>




interface ReviewRepository : MongoRepository<Review, String> {
    fun findByStudentId(student_id: String): List<Review>
}

interface ReviewCriteriaRepository : MongoRepository<ReviewCriteria, String>


interface GradeCollectionRepository : MongoRepository<GradeCollection, String> {
    fun findByStudentIdAndWorkshopId(student_id: String, workshop_id: String): GradeCollection
}

interface GradeRepository : MongoRepository<Grade, String>

