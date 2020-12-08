package com.iprp.backend.attachments

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-12-08
 */
data class AttachmentUpload(
    val ok: Boolean,
    val attachment: Attachment?
)