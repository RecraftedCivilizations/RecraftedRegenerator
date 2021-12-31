package com.github.recraftedcivilizations.dataparser

import com.github.recraftedcivilizations.dataparser.blockparser.IParseBlocks
import com.github.recraftedcivilizations.dataparser.timeparser.IParseTimes
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.bukkit.Location
import org.bukkit.block.Block
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class DataParserTest {
    lateinit var dataParser: DataParser
    lateinit var blockParser: IParseBlocks
    lateinit var timeParser: IParseTimes

    @BeforeEach
    fun setup(){
        timeParser = mockk(null, true)
        blockParser = mockk(null, true)

        dataParser = DataParser(blockParser, timeParser)

    }

    @Test
    fun storeBlock() {
        val block: Block = mockk(null, true)

        dataParser.storeBlock(block)

        verify(exactly = 1) { blockParser.storeBlock(block) }
        confirmVerified(blockParser)
    }

    @Test
    fun removeBlock() {
        val location: Location = mockk(null, true)

        dataParser.removeBlock(location)

        verify(exactly = 1) { blockParser.removeBlock(location) }
        confirmVerified(blockParser)
    }

    @Test
    fun getBlockType() {
        val location: Location = mockk(null, true)

        dataParser.getBlockType(location)

        verify(exactly = 1) { blockParser.getBlockType(location) }
        confirmVerified(blockParser)

    }

    @Test
    fun isStored() {
        val location: Location = mockk(null, true)

        dataParser.isStored(location)

        verify(exactly = 1) { blockParser.isStored(location) }
        confirmVerified(blockParser)
    }

    @Test
    fun getRespawnTime() {
        val location: Location = mockk(null, true)

        dataParser.getRespawnTime(location)

        verify(exactly = 1) { timeParser.getRespawnTime(location) }
        confirmVerified(timeParser)
    }

    @Test
    fun setRespawnTime() {
        val location: Location = mockk(null, true)

        dataParser.setRespawnTime(location, 100)

        verify(exactly = 1) { timeParser.setRespawnTime(location, 100) }
        confirmVerified(timeParser)
    }

    @Test
    fun getBlocksToRespawn() {
        dataParser.getBlocksToRespawn()

        verify(exactly = 1) { timeParser.getBlocksToRespawn() }
        confirmVerified(timeParser)
    }
}