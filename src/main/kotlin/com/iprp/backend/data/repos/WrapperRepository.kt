package com.iprp.backend.data.repos

import com.iprp.backend.data.user.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlinx.coroutines.*

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-11-05
 */
@Component
class WrapperRepository {
    @Autowired
    lateinit var studentRepository: StudentRepository
    @Autowired
    lateinit var teacherRepository: TeacherRepository

    /**
     * Deletes all documents from all repositories.
     */
    fun deleteAll() {
        studentRepository.deleteAll()
        teacherRepository.deleteAll()
    }

    fun findAllPersons(): List<Person>{
        return runBlocking {  findAllPersonsAsync() }
    }

    private suspend fun findAllPersonsAsync(): List<Person>{
        // Kotlin with async await
        // See: https://stackoverflow.com/a/52372823/12347616
        val studentJob = GlobalScope.async {
            studentRepository.findAll() as MutableList<Person>
        }
        val teacherJob = GlobalScope.async {
            teacherRepository.findAll() as List<Person>
        }
        val students = studentJob.await()
        val teachers = teacherJob.await()
        students.addAll(teachers)
        return students
    }
}