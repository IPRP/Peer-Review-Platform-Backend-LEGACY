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

    fun uploadAttachment(personId: String, title: String, file: MultipartFile): AttachmentUpload {
        return try {
            val metaData = BasicDBObject()
            metaData["title"] = title
            metaData["contentType"] = file.contentType
            metaData["owner"] = personId
            val id = gridFsTemplate.store(
                file.inputStream, file.name, file.contentType, metaData
            )
            AttachmentUpload(true, Attachment(id.toString(), title))
        } catch (ex: Exception) {
            AttachmentUpload(false, null)
        }
    }

    fun downloadAttachment(id: String): AttachmentHandler {
        return try {
            val file = gridFsTemplate.findOne(Query(Criteria.where("_id").`is`(id)))
            val resource = operations.getResource(file)
            AttachmentHandler(
                true, file.metadata!!["title"].toString(),
                file.metadata!!["owner"].toString(),
                file.metadata!!["contentType"].toString(),
                resource.inputStream, resource,
            )
        } catch (ex: Exception) {
            AttachmentHandler(
                false, null,null, null, null, null
            )
        }
    }

    fun removeAttachment(id: String) {
        gridFsTemplate.delete(Query(Criteria.where("_id").`is`(id)))
    }
}