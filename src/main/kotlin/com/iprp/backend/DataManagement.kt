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
        val teachers = repo.findAllTeachersByIdIn(teacherIds) as MutableList<Teacher>
        val students = repo.findAllStudentsByIdIn(studentIds) as MutableList<Student>
        val roundEnd = LocalDateTime.now().plusWeeks(2)
        val rounds = mutableListOf<SubmissionRound>()
        val grades = mutableListOf<GradeCollection>()

        var criteriaType = ReviewCriteria.fromList(criteria)
        criteriaType = repo.saveReviewCriteria(criteriaType)

        var workshop = Workshop(
            title, content, end, roundEnd, anonymous, students,
            teachers, criteriaType, rounds, grades
        )
        workshop = repo.saveWorkshop(workshop)

        // Add grade structure for every student
        for(student in students) {
            var grade = GradeCollection(null, mutableListOf(), student, workshop)
            grade = repo.saveGradeCollection(grade)
            grades.add(grade)
        }

        // Update workshop in order to add grades
        workshop = repo.saveWorkshop(workshop)

        // Add first submission round
        startRound(workshop)

    }

    private fun startRound(workshop: Workshop) {
        val submissions = mutableListOf<Submission>()
        val grades = mutableListOf<Grade>()
        for(student in workshop.students) {
            var submission = Submission(false, null, null, listOf(), workshop, student, listOf())
            submission = repo.saveSubmission(submission)
            submissions.add(submission)
            var grade = Grade(null, null, null, student, submission, workshop)
            grade = repo.saveGrade(grade)
            grades.add(grade)
            val studentGradeCollection = repo.findGradeCollection(student.id, workshop.id)
            studentGradeCollection.grades.add(grade)
            repo.saveGradeCollection(studentGradeCollection)
        }
        val submissionRound = SubmissionRound(workshop.roundEnd, workshop, submissions, grades)
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
        repo.addPerson(Student(id, firstname, lastname, group))
    }

    fun addTeacher(id: String, firstname: String, lastname: String) {
        repo.addPerson(Teacher(id, firstname, lastname))
    }
}