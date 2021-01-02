package com.iprp.backend.attachments

import org.springframework.jmx.access.InvalidInvocationException
import java.io.InputStream

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-12-08
 */
class AttachmentHandler(
    val ok: Boolean,
    val title: String?,
    val stream: InputStream?
) {
    fun title(): String {
        if (ok && title is String) return title
        throw InvalidInvocationException("Invoked on non ok AttachmentHandler")
    }

    fun stream(): InputStream {
        if (ok && stream is InputStream) return stream
        throw InvalidInvocationException("Invoked on non ok AttachmentHandler")
    }
}