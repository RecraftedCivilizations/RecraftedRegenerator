package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.commands.PlaceRegenOre
import com.github.recraftedcivilizations.dataparser.IParseData
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.block.BlockPlaceEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@Disabled
internal class BlockPlaceListenerTest {
    private lateinit var blockPlaceEvent: BlockPlaceEvent
    private lateinit var placeRegenOre: PlaceRegenOre
    private lateinit var dataParser: IParseData
    private lateinit var configParser: ConfigParser
    private lateinit var block: Block


    @BeforeEach
    fun setUp() {
        blockPlaceEvent = mockk(null, true)
        placeRegenOre = mockk(null, true)
        dataParser = mockk(null, true)
        configParser = mockk(null, true)
        block = mockk(null, true)
    }

    @Test
    fun onBlockPlaceEvent() {

        every { placeRegenOre.isInRegenMode(any()) } returns true
        every { configParser.respawnTimes.keys } returns setOf(Material.IRON_ORE).toMutableSet()
        every { blockPlaceEvent.block } returns block
        every { block.type } returns Material.IRON_ORE




        verify { placeRegenOre.isInRegenMode(any()) }
        verify { block.type }
        verify { configParser.respawnTimes.keys }
        verify { dataParser.storeBlock(block) }
        confirmVerified(placeRegenOre, configParser, dataParser)

    }
}