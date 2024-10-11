package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.RecraftedRegenerator
import com.github.recraftedcivilizations.commands.RemoveRegenOre
import com.github.recraftedcivilizations.dataparser.IParseData
import io.mockk.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitScheduler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
internal class BlockBreakListenerTest {

    @AfterEach
    fun teardown(){
        clearAllMocks()
    }

    @Test
    fun onBlockBreak() {

        val removeRegenOre: RemoveRegenOre = mockk()
        val e: BlockBreakEvent = mockk(null, true)
        val dataParser: IParseData = mockk(null, true)
        val configParser: ConfigParser = mockk(null, true)
        val block: Block = mockk(null, true)
        val listener = BlockBreakListener(dataParser, configParser, removeRegenOre)
        val l: Location = mockk(null, true)
        val plugin: JavaPlugin = mockk(null, true)
        val bukkitScheduler: BukkitScheduler = mockk(null, true)

        every { e.block } returns block
        every { removeRegenOre.isInRemoveMode(any()) } returns false
        every { block.getType() } returns Material.IRON_ORE
        every { block.location } returns l
        every { l.block } returns block
        every { configParser.respawnTimes } returns mapOf<Material, Int>(Pair(Material.IRON_ORE, 100)).toMutableMap()
        every { configParser.emptyBlock } returns Material.BEDROCK
        every { dataParser.isStored(l) } returns true


        mockkObject(RecraftedRegenerator){
            mockkStatic(Bukkit::getPluginManager){
                every { RecraftedRegenerator.plugin } returns plugin
                every { Bukkit.getScheduler() } returns bukkitScheduler
                listener.onBlockBreak(e)
            }
        }

        verify(exactly = 1) { dataParser.setRespawnTime(l, 100) }
    }

    @Test
    fun setBlock(){

        val removeRegenOre: RemoveRegenOre = mockk()
        val e: BlockBreakEvent = mockk(null, true)
        val dataParser: IParseData = mockk(null, true)
        val configParser: ConfigParser = mockk(null, true)
        val block: Block = mockk(null, true)
        val listener = BlockBreakListener(dataParser, configParser, removeRegenOre)
        val l: Location = mockk(null, true)
        val plugin: JavaPlugin = mockk(null, true)
        val bukkitScheduler: BukkitScheduler = mockk(null, true)

        every { e.block } returns block
        every { removeRegenOre.isInRemoveMode(any()) } returns false
        every { block.getType() } returns Material.IRON_ORE
        every { block.location } returns l
        every { l.block } returns block
        every { configParser.respawnTimes } returns mapOf<Material, Int>(Pair(Material.IRON_ORE, 100)).toMutableMap()
        every { configParser.emptyBlock } returns Material.BEDROCK
        every { dataParser.isStored(l) } returns true

        every { bukkitScheduler.runTask(plugin, any<Runnable>()) } answers {
            (secondArg() as Runnable).run()

            mockk(null, false)
        }

        mockkObject(RecraftedRegenerator){
            mockkStatic(Bukkit::getPluginManager){
                every { RecraftedRegenerator.plugin } returns plugin
                every { Bukkit.getScheduler() } returns bukkitScheduler
                listener.onBlockBreak(e)
            }
        }


        verify(exactly = 1) { block.setType(Material.BEDROCK) }

    }
}