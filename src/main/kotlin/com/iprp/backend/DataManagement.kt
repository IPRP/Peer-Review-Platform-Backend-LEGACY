package com.iprp.backend

import com.iprp.backend.data.Workshop
import com.iprp.backend.data.grade.Grade
import com.iprp.backend.data.review.ReviewCriteria
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
        val grades = mutableListOf<Grade>()

        var workshop = Workshop(title, content, end, roundEnd, anonymous, students, teachers, rounds, grades)
        workshop = repo.addWorkshop(workshop)

        // Add first submission round
        val criteria = ReviewCriteria.fromList(criteria)
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