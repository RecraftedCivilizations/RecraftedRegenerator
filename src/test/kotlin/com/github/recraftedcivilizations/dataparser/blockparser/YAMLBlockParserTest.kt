package com.github.recraftedcivilizations.dataparser.blockparser

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
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
        //File("./data.yml").delete()
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

        val block: Block = mockk(null, true)
        parser.storeBlock(block)

        assert(parser.isStored(block.location))

        val block2: Block = mockk(null, true)
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
}