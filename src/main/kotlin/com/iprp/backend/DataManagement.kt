package com.iprp.backend

import com.iprp.backend.attachments.Attachment
import com.iprp.backend.attachments.AttachmentService
import com.iprp.backend.data.Workshop
import com.iprp.backend.data.review.Review
import com.iprp.backend.data.review.ReviewCriteria
import com.iprp.backend.data.submission.Submission
import com.iprp.backend.data.user.Student
import com.iprp.backend.data.user.Teacher
import com.iprp.backend.repos.WrapperRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.time.LocalDateTime
import java.time.LocalTime
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

    @Autowired
    lateinit var attService: AttachmentService

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
    fun getTeacherWorkshop(workshopId: String): Map<String, Any> {
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
    fun getStudentWorkshop(studentId: String, workshopId: String, ): Map<String, Any> {
        try {
            val workshop = repo.findWorkshop(workshopId)
            if (workshop != null) {
                val teachers = mutableListOf<Map<String, String>>()
                repo.findAllTeachersByIdIn(workshop.teachers).forEach { teacher ->
                    teachers.add(
                        mapOf("id" to teacher.id, "firstname" to teacher.firstname, "lastname" to teacher.lastname)
                    )
                }
                val students = mutableListOf<Map<String, String>>()
                repo.findAllStudentsByIdIn(workshop.students).forEach { student ->
                    students.add(
                        mapOf("id" to student.id, "firstname" to student.firstname, "lastname" to student.lastname,
                            "group" to student.group)
                    )
                }

                val submissions = mutableListOf<Map<String, Any>>()
                repo.findAllStudentSubmissionsInWorkshop(studentId, workshopId).forEach { submission ->
                    val s = mutableMapOf<String, Any>(
                        "id" to submission.id, "locked" to submission.locked,
                        "date" to submission.date, "title" to submission.title, "reviewsDone" to submission.reviewsDone
                    )
                    if (submission.reviewsDone) {
                        s["points"] = submission.pointsMean ?: 0
                        s["maxPoints"] = submission.maxPoints ?: 0
                    }
                    submissions.add(s)
                }

                val reviews = mutableListOf<Map<String, Any>>()
                repo.findAllStudentReviewsInWorkshop(studentId, workshopId).forEach { review ->
                    val submission = repo.findSubmissionByIdAndStudentInWorkship(
                        review.submission, review.student, workshopId)
                    val student = repo.findStudent(review.student)
                    if (submission != null && student != null) {
                        reviews.add(mapOf(
                            "id" to review.id, "deadline" to review.deadline, "title" to submission.title,
                            "done" to review.done, "firstname" to student.firstname, "lastname" to student.lastname
                        ))
                    }
                }

                return mapOf(
                    "ok" to true, "workshop" to mapOf(
                        "title" to workshop.title, "content" to workshop.content, "end" to workshop.end,
                        "teachers" to teachers, "students" to students, "submissions" to submissions,
                        "reviews" to reviews
                    )
                )
            }
        } catch (ex: Exception) {}
        return mapOf("ok" to false)
    }

    fun getStudentTodos(studentId: String): Map<String, Any> {
        try {
            val workshops = repo.findAllWorkshops(studentId)
            val reviews = mutableListOf<Map<String, Any>>()
            val submissions = mutableListOf<Map<String, Any>>()

            for (workshop in workshops) {
                repo.findAllStudentReviewsInWorkshop(studentId, workshop.id).forEach { review ->
                    val submission = repo.findSubmissionByIdAndStudentInWorkship(
                        review.submission, review.student, workshop.id)
                    val student = repo.findStudent(review.student)
                    if (submission != null && student != null) {
                        reviews.add(mapOf(
                            "id" to review.id, "deadline" to review.deadline, "title" to submission.title,
                            "firstname" to student.firstname, "lastname" to student.lastname,
                            "workshopName" to workshop.title
                        ))
                    }
                }
                if (repo.countStudentSubmissionsInWorkshop(studentId, workshop.id) == 0L) {
                    submissions.add(mapOf("id" to workshop.id, "workshopName" to workshop.title))
                }
            }

            return mapOf("ok" to true, "reviews" to reviews, "submissions" to submissions)
        } catch (ex: Exception) {
            return mapOf("ok" to false)
        }
    }

    //================================================================================
    // 
    // Submission
    //
    //================================================================================
    
    fun addSubmissionToWorkshop(
        studentId: String, workshopId: String,
        title: String, comment: String, attachments: List<Map<String, String>>
    ): Map<String, Any> {
        try {
            val workshop = repo.findWorkshop(workshopId)
            if (workshop != null) {
                if (!workshop.students.contains(studentId)) return mapOf("ok" to false)
                if (repo.findAllStudentSubmissionsInWorkshop(studentId, workshopId).any { !it.locked })
                    return mapOf("ok" to false)
                val attachmentsParsed = mutableListOf<Attachment>()
                for (attachment in attachments) {
                    if (attachment.containsKey("id") && attachment.containsKey("title")) {
                        val id = attachment["id"].toString()
                        val attachmentTitle = attachment["title"].toString()
                        attachmentsParsed.add(Attachment(id, attachmentTitle))
                    }
                }
                var submission = Submission(
                    false, LocalDateTime.now(), title, comment, attachmentsParsed, workshopId, studentId,
                    mutableListOf(), false, null, null
                )
                submission = repo.saveSubmission(submission)
                // Assign reviews, submission id is needed for this
                val reviewHandler = assignReviews(submission)
                if (!reviewHandler.first) return mapOf("ok" to false)
                submission.reviews.addAll(reviewHandler.second)
                // Apply review change
                repo.saveSubmission(submission)

                return mapOf("ok" to true, "id" to submission.id)
            }
        }
        catch (ex: Exception) { }
        return mapOf("ok" to false)
    }

    fun getSubmissionStudent(studentId: String, submissionId: String): Map<String, Any> {
        val submission = repo.findSubmission(submissionId) ?: return mapOf("ok" to false)
        return if (submission.student == studentId) {
            // Own submission
            getSubmissionInternal(submission, studentMode = true)
        } else {
            // Submission of colleague
            val attachments = mutableListOf<Map<String, String>>()
            for (attachment in submission.attachments) {
                attachments.add(mapOf("id" to attachment.id, "title" to attachment.title))
            }
            mapOf(
                "ok" to true, "title" to submission.title, "attachments" to attachments
            )
        }
    }

    fun getSubmissionTeacher(teacherId: String, submissionId: String): Map<String, Any> {
        val submission = repo.findSubmission(submissionId) ?: return mapOf("ok" to false)
        return getSubmissionInternal(submission, studentMode = false)
    }

    private fun getSubmissionInternal(submission: Submission, studentMode: Boolean = true): Map<String, Any> {
        val attachments = mutableListOf<Map<String, String>>()
        for (attachment in submission.attachments) {
            attachments.add(mapOf("id" to attachment.id, "title" to attachment.title))
        }

        val response = mutableMapOf(
            "ok" to true, "title" to submission.title, "attachments" to attachments,
            "locked" to submission.locked, "date" to submission.date, "reviewsDone" to submission.reviewsDone
        )

        if (submission.reviewsDone) {
            // Check if workshop is anonymous
            val workshop = repo.findWorkshop(submission.workshop)
            val anonymous = workshop != null && workshop.anonymous
            // Get reviews
            val reviews = mutableListOf<Map<String, Any>>()
            for (reviewId in submission.reviews) {
                val review = repo.findReview(reviewId)
                if (review != null) {
                    val student = repo.findStudent(review.student)
                    val criteria = repo.findReviewCriteria(review.criteria)
                    if (student != null && criteria != null) {
                        val points = mutableListOf<Map<String, Any>>()
                        for (i in review.points.indices) {
                            points.add(mapOf(
                                "type" to criteria.criteria[i].type.name, "title" to criteria.criteria[i].title,
                                "content" to criteria.criteria[i].content, "title" to criteria.criteria[i].weight,
                                "points" to review.points[i]
                            ))
                        }
                        // Add review with reviewer info or not
                        if (!studentMode || !anonymous) {
                            reviews.add(mapOf(
                                "id" to review.id, "firstname" to student.firstname, "lastname" to student.lastname,
                                "feedback" to review.feedback, "points" to points
                            ))
                        } else {
                            reviews.add(mapOf("id" to review.id, "feedback" to review.feedback, "points" to points))
                        }
                    }
                }
            }
            // Add review info
            response["points"] = submission.pointsMean ?: 0
            response["maxPoints"] = submission.maxPoints ?: 0
            response["reviews"] = reviews
        }

        return response
    }

    //================================================================================
    // Attachments
    //================================================================================

    fun uploadAttachment(title: String, file: MultipartFile): Map<String, Any> {
        val attachmentUpload = attService.uploadAttachment(title, file)
        if (attachmentUpload.ok && attachmentUpload.attachment is Attachment) {
            val attachment = attachmentUpload.attachment
            return mapOf("ok" to true, "attachment" to mapOf("id" to attachment.id, "title" to attachment.title))
        }
        return mapOf("ok" to false)
    }

    fun downloadAttachment(attachmentId: String): InputStream? {
        val handler = attService.downloadAttachment(attachmentId)
        if (handler.ok) {
            return handler.stream
        }
        return null
    }

    //================================================================================
    // 
    // Logic
    //
    //================================================================================

    // Scheduled task
    // See: https://spring.io/guides/gs/scheduling-tasks/
    /**@Scheduled(fixedRate = 50000)
    private fun schedule() {
        println("hi!")
    }*/

    private fun calculateReviewDeadline(submissionDate: LocalDateTime): LocalDateTime {
        // Get Midnight of given submission
        // See: https://stackoverflow.com/a/6850919/12347616
        val midnight = LocalTime.MIDNIGHT
        val todayMidnight = LocalDateTime.of(submissionDate.toLocalDate(), midnight)
        // Reviews ends at midnight in seven days
        return todayMidnight.plusDays(7)
    }

    private fun assignReviews(submission: Submission): Pair<Boolean, List<String>> {
        try {
            val workshop = repo.findWorkshop(submission.workshop)
            if (workshop != null) {
                // Copy student list
                // See: https://stackoverflow.com/a/46846074/12347616
                val students = workshop.students.toMutableList()
                // No students, no reviews
                if (students.size == 0) return Pair(false, listOf())
                // Remove student who added submission
                students.remove(submission.student)
                // Calculate deadline
                val deadline = calculateReviewDeadline(submission.date)
                // Get review criteria
                val criteria = repo.findReviewCriteria(workshop.criteria) ?: return Pair(false, listOf())
                val maxPoints = criteria.maxPoints()

                // Get three reviews at max
                val reviews = mutableListOf<String>()
                val reviewCount = if (students.size >= 3) 3 else students.size
                for (i in 0..reviewCount) {
                    // Get random student
                    // See: https://stackoverflow.com/a/45687695/12347616
                    val reviewer = students.random()
                    students.remove(reviewer)
                    // Save review
                    var review = Review(
                        false, deadline, "", mutableListOf(), maxPoints,
                        criteria.id, reviewer, submission.id, workshop.id
                    )
                    review = repo.saveReview(review)
                    reviews.add(review.id)
                }
                return Pair(true, reviews.toList())
            }

        } catch (ex: Exception) {}
        return Pair(false, listOf())
    }


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