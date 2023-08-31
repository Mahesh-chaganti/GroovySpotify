package com.example.groovyspotify.model.services

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}