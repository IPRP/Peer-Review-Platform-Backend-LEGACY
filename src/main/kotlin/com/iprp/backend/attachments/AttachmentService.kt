package com.iprp.backend.attachments

import com.mongodb.BasicDBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsOperations
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

/**
 * See: https://www.baeldung.com/spring-boot-mongodb-upload-file
 *
 * @author Kacper Urbaniec
 * @version 2020-12-08
 */
@Service
class AttachmentService {

    @Autowired
    private lateinit var gridFsTemplate: GridFsTemplate

    @Autowired
    private lateinit var operations: GridFsOperations

    fun uploadAttachment(title: String, file: MultipartFile): AttachmentUpload {
        return try {
            val metaData = BasicDBObject()
            metaData["type"] = "attachment"
            metaData["title"] = title
            val id = gridFsTemplate.store(
                file.inputStream, file.name, file.contentType, metaData
            )
            AttachmentUpload(true, Attachment(id.toString(), title))
        } catch (ex: Exception) {
            AttachmentUpload(false, null)
        }
    }

    fun getAttachment(id: String): AttachmentHandler {
        return try {
            val file = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(id)))
            AttachmentHandler(
                true, file.metadata!!["title"].toString(), operations.getResource(file).inputStream
            )
        } catch (ex: Exception) {
            AttachmentHandler(
                false, null, null
            )
        }
    }
}