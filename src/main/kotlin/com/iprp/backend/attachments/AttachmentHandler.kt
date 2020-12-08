package com.iprp.backend.attachments

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
)