package com.iprp.backend

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.review.ReviewCriteria
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

    // TODO implement MongoDB transactions

    // TODO fileService

    //================================================================================
    //
    // WORKSHOP
    //
    //================================================================================

    //================================================================================
    // Student/Teacher
    //================================================================================

    /**
     * Get all workshops of a person (student or teacher).
     */
    fun getAllWorkshops(personId: String): Map<String, List<Map<String, String>>> {
        try {
            val workshops = repo.findAllWorkshops(personId)
            val workshopList = mutableListOf<Map<String, String>>()
            for (workshop in workshops) {
                workshopList.add(mapOf("id" to workshop.id, "title" to workshop.title))
            }
            return mapOf("workshops" to workshopList)
        } catch (ex: Exception) {}
        return mapOf("workshops" to listOf())
    }

    //================================================================================
    // Teacher
    //================================================================================

    /**
     * Get a specific workshop from a teacher's perspective.
     */
    fun getWorkshopTeacher(workshopId: String): Map<String, Any> {
        try {
            val workshop = repo.findWorkshop(workshopId)
            if (workshop != null) {
                val teachers = mutableListOf<Map<String, String>>()
                repo.findAllTeachersByIdIn(workshop.teachers).forEach { teacher ->
                    teachers.add(
                        mapOf("id" to teacher.id, "firstname" to teacher.firstname, "lastname" to teacher.lastname)
                    )
                }

                val students = mutableListOf<Map<String, Any>>()
                repo.findAllStudentsByIdIn(workshop.students).forEach { student ->
                    val submissions = mutableListOf<Map<String, Any>>()
                    repo.findAllStudentSubmissionsInWorkshop(student.id, workshopId).forEach { submission ->
                        val s = mutableMapOf<String, Any>("id" to submission.id,
                            "date" to submission.date, "title" to submission.title, "reviewsDone" to submission.reviewsDone)
                        if (submission.reviewsDone) {
                            s["points"] = submission.pointsMean ?: 0
                            s["maxPoints"] = submission.maxPoints ?: 0
                        }
                        submissions.add(s)
                    }
                    students.add(
                        mapOf(
                            "id" to student.id, "firstname" to student.firstname, "lastname" to student.lastname,
                            "group" to student.group, "submissions" to submissions
                        )
                    )
                }

                return mapOf(
                    "ok" to true, "workshop" to mapOf(
                        "title" to workshop.title, "content" to workshop.content, "anonymous" to workshop.anonymous,
                        "end" to workshop.end, "teachers" to teachers, "students" to students
                    )
                )
            }
        } catch (ex: Exception) {}
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

            var criteriaType = ReviewCriteria.fromList(criteria)
            criteriaType = repo.saveReviewCriteria(criteriaType)

            val workshop = Workshop(
                title, content, end, anonymous, studentIds as MutableList<String>,
                teacherIds as MutableList<String>, criteriaType.id
            )

            repo.saveWorkshop(workshop)
            return mapOf("ok" to true)
        } catch (ex: Exception) {
            return mapOf("ok" to false)
        }
    }

    /**
     * Update existing Workshop. Should only be used by teachers.
     */
    fun updateWorkshop(
        workshopId: String, teacherIds: List<String>, studentIds: List<String>,
        title: String, content: String, end: LocalDateTime, criteria: List<Map<String, String>>
    ): Map<String, Any> {
        try {
            // Lock process for this id ?
            // See: https://stackoverflow.com/a/13957003/12347616
            /**synchronized(studentId.intern()) {
            }*/
            val workshop = repo.findWorkshop(workshopId)
            if (workshop != null) {
                workshop.end = end
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
                            workshop.students.add(student)
                        }
                    } else {
                        // Student that should be removed
                        // First, remove all linked submissions, then remove it from workshop
                        repo.deleteStudentSubmissionsInWorkshop(student, workshopId)
                        workshop.students.remove(student)
                    }
                }
                repo.saveWorkshop(workshop)
                return mapOf("ok" to true)
            }
        } catch (ex: Exception) {}
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

    //================================================================================
    // Student
    //================================================================================

    /**
     * Get a specific workshop from a student's perspective.
     */
    fun getWorkshopStudent(workshopId: String): Map<String, Any> {
        try {
            val workshop = repo.findWorkshop(workshopId)
            if (workshop != null) {
                val teachers = mutableListOf<Map<String, String>>()
                repo.findAllTeachersByIdIn(workshop.teachers).forEach { teacher ->
                    teachers.add(
                        mapOf("id" to teacher.id, "firstname" to teacher.firstname, "lastname" to teacher.lastname)
                    )
                }

                val students = mutableListOf<Map<String, Any>>()
                repo.findAllStudentsByIdIn(workshop.students).forEach { student ->
                    val submissions = mutableListOf<Map<String, Any>>()
                    repo.findAllStudentSubmissionsInWorkshop(student.id, workshopId).forEach { submission ->
                        val s = mutableMapOf<String, Any>("id" to submission.id,
                            "date" to submission.date, "title" to submission.title, "reviewsDone" to submission.reviewsDone)
                        if (submission.reviewsDone) {
                            s["points"] = submission.pointsMean ?: 0
                            s["maxPoints"] = submission.maxPoints ?: 0
                        }
                        submissions.add(s)
                    }
                    students.add(
                        mapOf(
                            "id" to student.id, "firstname" to student.firstname, "lastname" to student.lastname,
                            "group" to student.group, "submissions" to submissions
                        )
                    )
                }

                return mapOf(
                    "ok" to true, "workshop" to mapOf(
                        "title" to workshop.title, "content" to workshop.content, "anonymous" to workshop.anonymous,
                        "end" to workshop.end, "teachers" to teachers, "students" to students
                    )
                )
            }
        } catch (ex: Exception) {}
        return mapOf("ok" to false)
    }


    //================================================================================
    // Logic
    //================================================================================

    // Scheduled task
    // See: https://spring.io/guides/gs/scheduling-tasks/
    @Scheduled(fixedRate = 50000)
    private fun schedule() {
        println("hi!")
    }

    /**
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

            val studentGradeCollection = repo.findGradeCollectionInWorkshop(student.id, workshop.id)
            if (studentGradeCollection != null) {
                studentGradeCollection.grades.add(grade.id)
                repo.saveGradeCollection(studentGradeCollection)
            }

        }
        var submissionRound = SubmissionRound(workshop.roundEnd, workshop.id, submissions, grades)
        submissionRound = repo.saveSubmissionRound(submissionRound)
        workshop.rounds.add(submissionRound.id)
        repo.saveWorkshop(workshop)
    }*/


    //================================================================================
    //
    // GRADES
    //
    //================================================================================

    // TODO delete category?

    //================================================================================
    //
    // SEARCH
    //
    //================================================================================

    fun searchStudent(firstname: String, lastname: String): Map<String, Any> {
        val firstNameCap = firstname.capitalize()
        val lastNameCap = lastname.capitalize()
        try {
            val student = repo.findStudent(firstNameCap, lastNameCap)
            if (student != null) return mapOf("ok" to true, "id" to student.id)
        } catch (ex: Exception) {}
        return mapOf("ok" to false)
    }

    fun searchStudents(group: String): Map<String, Any> {
        try {
            val students = repo.findStudents(group)
            if (students.isNotEmpty()) {
                val studentIds = mutableListOf<String>()
                students.forEach { student ->  studentIds.add(student.id)}
                return mapOf("ok" to true, "ids" to studentIds)
            }
        } catch (ex: Exception) {}
        return mapOf("ok" to false)
    }



    /**
     * Methods to fill database
     */

    fun addStudent(id: String, firstname: String, lastname: String, group: String) {
        repo.savePerson(Student(id, firstname, lastname, group))
    }

    fun addTeacher(id: String, firstname: String, lastname: String) {
        repo.savePerson(Teacher(id, firstname, lastname))
    }
}