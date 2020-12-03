package com.iprp.backend

import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

/**
 *
 *
 * @author Kacper Urbaniec
 * @version 2020-12-03
 */
class Helper {
    companion object {
        // See: https://stackoverflow.com/a/48159066/12347616
        // And: https://stackoverflow.com/a/35539628/12347616
        @Suppress("UNCHECKED_CAST")
        fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
            val property = instance::class.members
                // don't cast here to <Any, R>, it would succeed silently
                .first { it.name == propertyName } as KProperty1<Any, *>
            // force a invalid cast exception if incorrect type here
            property.isAccessible = true
            return property.get(instance) as R
        }
    }

}