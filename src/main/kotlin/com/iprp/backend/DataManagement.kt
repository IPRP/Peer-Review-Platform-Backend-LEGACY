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

    // TODO read write lock ReentrantReadWriteLock or MongoDB transaction

    // Scheduled task
    // See: https://spring.io/guides/gs/scheduling-tasks/
    @Scheduled(fixedRate = 50000)
    fun reportCurrentTime() {
        println("hi!")
    }

    /**
     * Workshop
     */

    /**
     * Get all workshops of a person (student or teacher).
     */
    fun getAllWorkshops(personId: String): Map<String, List<Map<String, String>>> {
        val workshops = repo.findAllWorkshops(personId)
        val workshopList = mutableListOf<Map<String, String>>()
        for (workshop in workshops) {
            workshopList.add(mapOf("id" to workshop.id, "title" to workshop.title))
        }
        return mapOf("workshops" to workshopList)
    }

    /**
     * Get a specific workshop from a teacher's perspective.
     */
    fun getWorkshopTeacher(workshopId: String): Map<String, Any> {
        val workshop = repo.findWorkshop(workshopId)
        if (workshop != null) {
            val teachers = mutableListOf<Map<String, String>>()
            repo.findAllTeachersByIdIn(workshop.teachers).forEach { teacher ->
                teachers.add(
                    mapOf("id" to teacher.id, "firstname" to teacher.firstname, "lastname" to teacher.lastname))
            }
            val students = mutableListOf<Map<String, String>>()
            repo.findAllStudentsByIdIn(workshop.students).forEach { student ->
                students.add(
                    mapOf("id" to student.id, "firstname" to student.firstname, "lastname" to student.lastname))
            }
            val rounds = mutableListOf<Map<String, Any>>()
            repo.findAllSubmissionRounds(workshop.rounds).forEach { submissionRound ->
                val round = mutableMapOf<String, Any>("id" to submissionRound.id)
                val submissions = mutableListOf<Map<String, String>>()
                repo.findAllSubmissions(submissionRound.submissions).forEach { submission ->
                    val student = repo.findStudent(submission.student)
                    if (student != null) {
                        submissions.add(mapOf(
                            "id" to submission.id, "title" to (submission.title?:""),
                            "firstname" to student.firstname, "lastname" to student.lastname))
                    }
                }
                round["submissions"] = submissions
                rounds.add(round)
            }
            return mapOf("ok" to true, "workshop" to mapOf(
                "title" to workshop.title, "content" to workshop.content, "anonymous" to workshop.anonymous,
                "end" to workshop.end, "roundEnd" to workshop.roundEnd, "teachers" to teachers,
                "students" to students, "rounds" to rounds))
        }
        return mapOf("ok" to false)
    }

    /**
     * Add new Workshop. Should be only used by teachers.
     */
    fun addWorkshop(
        teacherIds: List<String>, studentIds: List<String>,
        title: String, content: String, anonymous: Boolean,
        end: LocalDateTime, criteria: List<Map<String, String>>
    ): Map<String, Any>  {
        try {
            // Create workshop
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
            for (student in students) {
                var grade = GradeCollection(null, mutableListOf(), student.id, workshop.id)
                grade = repo.saveGradeCollection(grade)
                grades.add(grade.id)
            }

            // Update workshop in order to add grades
            workshop = repo.saveWorkshop(workshop)

            // Add first submission round
            startRound(workshop, cachedStudents = students)

            return mapOf("ok" to true)
        } catch (ex: Exception) {
            return mapOf("ok" to false)
        }
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
        var submissionRound = SubmissionRound(workshop.roundEnd, workshop.id, submissions, grades)
        submissionRound = repo.saveSubmissionRound(submissionRound)
        workshop.rounds.add(submissionRound.id)
        repo.saveWorkshop(workshop)
    }

    /**
     * Update existing Workshop. Should only be used by teachers.
     */
    fun updateWorkshop(
        workshopId: String, teacherIds: List<String>, studentIds: List<String>,
        title: String, content: String, end: LocalDateTime, roundEnd: LocalDateTime,
        criteria: List<Map<String, String>>
    ): Map<String, Any> {
        // TODO
        // Lock process for this id
        // See: https://stackoverflow.com/a/13957003/12347616
        /**synchronized(studentId.intern()) {
        }*/

        val workshop = repo.findWorkshop(workshopId)
        if (workshop != null) {
            workshop.end = end
            workshop.roundEnd = roundEnd
            var criteriaType = ReviewCriteria.fromList(criteria)
            criteriaType = repo.saveReviewCriteria(criteriaType)
            workshop.criteria = criteriaType.id
            // If the teacherIds are empty, no teacher update will be made,
            // because a course needs a minimum of one teacher
            if (teacherIds.isNotEmpty()) {
                workshop.teachers.clear()
                workshop.teachers.addAll(teacherIds)
            }
            val check = studentIds + workshop.students
            check.forEach { student ->
                if (studentIds.contains(student)) {
                    if (!workshop.students.contains(student)) {
                        // New student
                        if (workshop.rounds.isNotEmpty()) {
                            val round = repo.findSubmissionRound(workshop.rounds.last())
                            if (round != null) {
                                var submission = Submission(
                                    false, null, null, listOf(), workshopId, student, listOf())
                                submission = repo.saveSubmission(submission)
                                var grade = Grade(
                                    null, null, null, student, submission.id, workshopId)
                                grade = repo.saveGrade(grade)
                                round.submissions.add(submission.id)
                                round.grades.add(grade.id)
                            }
                        }
                        workshop.students.add(student)
                    }
                } else {
                    // Student should be removed
                    // TODO remove submissions from submissionRound.submission + grades
                        // and GradeCollection
                    repo.deleteStudentSubmissionsAndGradesInWorkshop(student, workshopId)
                    workshop.students.remove(student)
                }
            }
            repo.saveWorkshop(workshop)
            return mapOf("ok" to true)
        }
        return mapOf("ok" to false)
    }

    fun deleteWorkshop(workshopId: String): Map<String, Any> {
        return try {
            repo.deleteWorkshop(workshopId)
            mapOf("ok" to true)
        } catch (ex: Exception) {
            mapOf("ok" to false)
        }
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