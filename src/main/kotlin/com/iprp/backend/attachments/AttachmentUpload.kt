package com.iprp.backend.attachments

import org.springframework.jmx.access.InvalidInvocationException

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-12-08
 */
data class AttachmentUpload(
    val ok: Boolean,
    val attachment: Attachment?
) {
    fun ok(): Boolean {
        return ok && attachment is Attachment
    }
}