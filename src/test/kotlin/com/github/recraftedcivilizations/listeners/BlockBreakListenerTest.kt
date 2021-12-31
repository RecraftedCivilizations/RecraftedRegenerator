package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.dataparser.IParseData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class BlockBreakListenerTest {

    @Test
    fun onBlockBreak() {

        val e: BlockBreakEvent = mockk(null, true)
        val dataParser: IParseData = mockk(null, true)
        val configParser: ConfigParser = mockk(null, true)
        val block: Block = mockk(null, true)
        val listener = BlockBreakListener(dataParser, configParser)
        val l: Location = mockk(null, true)

        every { e.block } returns block
        every { block.getType() } returns Material.IRON_ORE
        every { block.location } returns l
        every { l.block } returns block
        every { configParser.respawnTimes } returns mapOf<Material, Int>(Pair(Material.IRON_ORE, 100)).toMutableMap()
        every { configParser.emptyBlock } returns Material.BEDROCK
        every { dataParser.isStored(l) } returns true

        listener.onBlockBreak(e)

        verify(exactly = 1) { dataParser.setRespawnTime(l, 100) }
        verify(exactly = 1) { block.setType(Material.BEDROCK) }

    }
}