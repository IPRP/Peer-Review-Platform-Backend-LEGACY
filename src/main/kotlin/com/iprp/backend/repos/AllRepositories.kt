package com.iprp.backend.repos

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.grade.GradeCollection
import com.iprp.backend.data.review.Review
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Person
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository


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
}




interface WorkshopRepository : MongoRepository<Workshop, String> {
    fun findByStudents(student_id: String): List<Workshop>
    fun findByTeachers(teacher_id: String): List<Workshop>
    fun findFirstById(id: String): Workshop?
}




interface SubmissionRepository : MongoRepository<Submission, String> {
    fun deleteByWorkshop(workshop_id: String)
    fun findFirstById(id: String): Submission?
    fun findByIdIn(ids: List<String>): List<Submission>
    fun deleteByStudentAndWorkshop(student_id: String, workshop_id: String)
    fun findByStudentAndWorkshop(student_id: String, workshop_id: String): List<Submission>
}

interface SubmissionRoundRepository : MongoRepository<SubmissionRound, String> {
    fun deleteByWorkshop(workshop_id: String)
    fun findByIdIn(ids: List<String>): List<SubmissionRound>
    fun findFirstById(id: String): SubmissionRound?
    fun findFirstBySubmissionsContainsAndWorkshop(submission_id: String, workshop_id: String): SubmissionRound?
}



interface ReviewRepository : MongoRepository<Review, String> {
    fun findByStudent(student_id: String): List<Review>
    fun deleteByWorkshop(workshop_id: String)
}

interface ReviewCriteriaRepository : MongoRepository<ReviewCriteria, String>


interface GradeCollectionRepository : MongoRepository<GradeCollection, String> {
    fun findByStudentAndWorkshop(student_id: String, workshop_id: String): GradeCollection?
    fun deleteByWorkshop(workshop_id: String)
    fun deleteByStudentAndWorkshop(student_id: String, workshop_id: String)

    //fun findByStudentIdAndWorkshopId(student_id: String, workshop_id: String): GradeCollection

    /**fun findByWorkshopId(workshop_id: String): GradeCollection? {
        val query = Query.query(Criteria.where("workshop._id").`is`(ObjectId(workshop_id)))
        return mongoTemplate.findOne(query, GradeCollection::class.java)
    }
    fun findByStudentId(student_id: ObjectId): GradeCollection*/
}

interface GradeRepository : MongoRepository<Grade, String> {
    fun deleteByWorkshop(workshop_id: String)
    fun deleteByStudentAndWorkshop(student_id: String, workshop_id: String)
}

