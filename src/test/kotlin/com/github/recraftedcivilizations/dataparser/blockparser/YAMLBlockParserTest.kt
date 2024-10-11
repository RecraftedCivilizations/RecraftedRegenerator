package com.github.recraftedcivilizations.dataparser.blockparser

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File

internal class YAMLBlockParserTest {
    private lateinit var parser: YAMLBlockParser

    @BeforeEach
    fun setup(){
        parser = YAMLBlockParser(".")
    }

    @AfterEach
    fun teardown(){
        File("./data.yml").delete()
    }

    @Test
    fun createsFileIfNotExists(){
        assert(File("./data.yml").exists())
    }

    @Test
    fun doesNotOverrideOldFile(){

        File("./data.yml").delete()
        val f = File("./data.yml")
        f.createNewFile()
        f.writeText("foo: bar")

        parser = YAMLBlockParser(".")

        assert(f.readText().contains("foo: bar"))
    }

    @Test
    fun storeBlock() {
        val block: Block = mockk(null, true)
        parser.storeBlock(block)
    }

    @Test
    fun isStored() {
        // FUCK THIS STUPID MOCKING WHY CAN'T I MOCK THE LOCATION CORRECTLY

        val block: Block = mockk(relaxed = true)
        val location = Location(null, 0.0, 0.0, 0.0)
        every { block.location } returns location


        // Store the block
        parser.storeBlock(block)

        // Assert that block is stored
        assert(parser.isStored(block.location))

        // Creating second block and location
        val block2: Block = mockk(relaxed = true)
        val location2 = Location(null, 1.0, 1.0, 1.0)
        every { block2.location } returns location2

        // Debugging: printing hash codes to check uniqueness
        println(block.location.hashCode())
        println(block2.location.hashCode())

        // Assert that block2 is not stored
        assert(!parser.isStored(block2.location))
    }


    @Test
    fun removeBlock() {
        val block: Block = mockk(null, true)

        parser.storeBlock(block)
        parser.removeBlock(block.location)

        assert(!parser.isStored(block.location))
    }

    @Test
    fun getBlockType() {

        val block: Block = mockk(null, true)
        every {
            (block).type
        } returns Material.IRON_ORE

        parser.storeBlock(block)

        assertEquals(Material.IRON_ORE, parser.getBlockType(block.location))

    }

    @Test
    fun shouldNotAppendSlashIfAlreadyPartOfURI() {

        val parser = YAMLBlockParser("./")
        assertEquals("./data.yml", parser.filePath)

    }
}