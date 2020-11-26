package com.iprp.backend.authentication

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


/**
 * Configure Authentication to use custom Provider [AuthProvider].
 *
 * @author Kacper Urbaniec
 * @version 2020-10-21
 */
@Configuration
@EnableWebSecurity
@Suppress("RedundantModalityModifier")
open class AuthConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var authProvider: AuthProvider

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // TODO add authentication again later on
        //http.authorizeRequests().anyRequest().authenticated()
        //        .and().httpBasic()
        http.authorizeRequests().anyRequest().permitAll()
    }
}