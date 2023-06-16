package com.example.groovyspotify

import com.example.groovyspotify.ui.home.calculateJaccardSimilarity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun similarityIsCorrect() {
        val expected = calculateJaccardSimilarity(
            listA = listOf("Thaman", "Devi Sri Prasad", "Maroon 5", "Ava Max"),
            listB = listOf("Thaman", "Devi Sri Prasad", "Maroon 5", "Ava Max"),
        )
        val actual = calculateJaccardSimilarity(
            listA = listOf("Thaman", "Maroon 5", "Ava Max"),
            listB = listOf("Thaman", "Devi Sri Prasad",  "Ava Max"),
        )
        val delta = 0.01 // The acceptable difference between expected and actual values


        assertEquals(
            expected, actual, delta
        )
    }

}