package com.iprp.backend

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.grade.GradeCollection
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.submission.SubmissionRound
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import com.iprp.backend.repos.WrapperRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-25
 */
@Component
class DataManagement {
    @Autowired
    lateinit var repo: WrapperRepository

    // Scheduled task
    // See: https://spring.io/guides/gs/scheduling-tasks/
    @Scheduled(fixedRate = 50000)
    fun reportCurrentTime() {
        println("hi!")
    }

    /**
     * Workshop
     */

    fun findAllWorkshops(personId: String): Map<String, List<Map<String, String>>> {
        val workshops = repo.findAllWorkshops(personId)
        val workshopList = mutableListOf<Map<String, String>>()
        for (workshop in workshops) {
            workshopList.add(mapOf("id" to workshop.id, "title" to workshop.title))
        }
        return Collections.singletonMap("workshops", workshopList)
    }

    fun addWorkshop(
        teacherIds: List<String>, studentIds: List<String>,
        title: String, content: String, anonymous: Boolean,
        end: LocalDateTime, criteria: List<Map<String, String>>
    ) {
        // Create workshop
        //val teachers = repo.findAllTeachersByIdIn(teacherIds) as MutableList<Teacher>
        val students = repo.findAllStudentsByIdIn(studentIds) as MutableList<Student>
        val roundEnd = LocalDateTime.now().plusWeeks(2)
        val rounds = mutableListOf<String>()
        val grades = mutableListOf<String>()

        var criteriaType = ReviewCriteria.fromList(criteria)
        criteriaType = repo.saveReviewCriteria(criteriaType)

        var workshop = Workshop(
            title, content, end, roundEnd, anonymous, studentIds as MutableList<String>,
            teacherIds as MutableList<String>, criteriaType.id, rounds, grades
        )
        workshop = repo.saveWorkshop(workshop)

        // Add grade structure for every student
        for(student in students) {
            var grade = GradeCollection(null, mutableListOf(), student.id, workshop.id)
            grade = repo.saveGradeCollection(grade)
            grades.add(grade.id)
        }

        // Update workshop in order to add grades
        workshop = repo.saveWorkshop(workshop)

        // Add first submission round
        startRound(workshop, cachedStudents = students)

    }

    private fun startRound(workshop: Workshop, cachedStudents: MutableList<Student>? = null) {
        val students = cachedStudents ?: repo.findAllStudentsByIdIn(workshop.students)
        val submissions = mutableListOf<String>()
        val grades = mutableListOf<String>()

        for(student in students) {
            var submission = Submission(false, null, null, listOf(), workshop.id, student.id, listOf())
            submission = repo.saveSubmission(submission)
            submissions.add(submission.id)
            var grade = Grade(null, null, null, student.id, submission.id, workshop.id)
            grade = repo.saveGrade(grade)
            grades.add(grade.id)

            val studentGradeCollection = repo.findGradeCollection(student.id, workshop.id)
            if (studentGradeCollection != null) {
                studentGradeCollection.grades.add(grade.id)
                repo.saveGradeCollection(studentGradeCollection)
            }

        }
        val submissionRound = SubmissionRound(workshop.roundEnd, workshop.id, submissions, grades)
        repo.saveSubmissionRound(submissionRound)
    }


    fun addStudentToWorkshop(studentId: String) {
        throw NotImplementedError()
        // inversion of remove student from workshop
    }

    fun removeStudentFromWorkshop(studentId: String) {
        throw NotImplementedError()

        // Lock process for this id
        // See: https://stackoverflow.com/a/13957003/12347616
        /**synchronized(studentId.intern()) {

        }*/

        // TODO
        // remove student from workshop

        // remove student from submission round

        //remove student from grades
    }

    /**
     * Person
     */

    fun addStudent(id: String, firstname: String, lastname: String, group: String) {
        repo.savePerson(Student(id, firstname, lastname, group))
    }

    fun addTeacher(id: String, firstname: String, lastname: String) {
        repo.savePerson(Teacher(id, firstname, lastname))
    }
}