package com.iprp.backend.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*


/**
 * Custom Authenticaton Provider for Spring Security.
 *
 * @see: https://frugalisminds.com/using-custom-authentication-provider-spring-security/
 * @author Kacper Urbaniec
 * @version 2020-10-21
 */
@Component
class AuthProvider : AuthenticationProvider{

    override fun authenticate(authentication: Authentication?): Authentication? {
        val name = authentication!!.name
        val password = authentication.credentials.toString()

        return if (name == "admin" && password == "admin") {
            val grantedAuths: MutableList<GrantedAuthority> = ArrayList()
            grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
            val principal: UserDetails = User(name, password, grantedAuths)
            UsernamePasswordAuthenticationToken(principal, password, grantedAuths)
        } else {
            // Allow following students
            return if (name == "s1" || name == "s2" || name == "s3" || name == "s4") {
                val grantedAuths: MutableList<GrantedAuthority> = ArrayList()
                grantedAuths.add(SimpleGrantedAuthority("ROLE_USER"))
                val principal: UserDetails = User(name, password, grantedAuths)
                UsernamePasswordAuthenticationToken(principal, password, grantedAuths)
            } else null
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication!! == UsernamePasswordAuthenticationToken::class.java
    }

}