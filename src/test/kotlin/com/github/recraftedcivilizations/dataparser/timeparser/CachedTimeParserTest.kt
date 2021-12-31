package com.github.recraftedcivilizations.dataparser.timeparser

import io.mockk.mockk
import org.bukkit.Location
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class CachedTimeParserTest {
    private lateinit var parser: CachedTimeParser

    @BeforeEach
    fun setup(){
        parser = CachedTimeParser()
    }

    @Test
    fun setRespawnTime() {
        val location: Location = mockk()
        parser.setRespawnTime(location, 100)
    }

    @Test
    fun getRespawnTime() {
        val location: Location = mockk()
        parser.setRespawnTime(location, 100)
        assertEquals(100, parser.getRespawnTime(location))
    }

    @Test
    fun shouldRemoveWhenTimeSmallerEqualZero(){

        val location: Location = mockk()
        parser.setRespawnTime(location, 100)

        parser.setRespawnTime(location, 0)
        assertEquals(null, parser.getRespawnTime(location))

        parser.setRespawnTime(location, -5)
        assertEquals(null, parser.getRespawnTime(location))

    }
}