package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.RecraftedRegenerator
import com.github.recraftedcivilizations.commands.RemoveRegenOre
import com.github.recraftedcivilizations.dataparser.IParseData
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.scheduler.BukkitRunnable

class BlockBreakListener(private val dataParser: IParseData, private val configParser: ConfigParser, private val removeRegenOre: RemoveRegenOre): Listener {

    @EventHandler
    fun onBlockBreak(blockBreakEvent: BlockBreakEvent){
        val block = blockBreakEvent.block

        if (block.type in configParser.respawnTimes.keys && dataParser.isStored(block.location)){

            if (removeRegenOre.isInRemoveMode(blockBreakEvent.player)){
                dataParser.removeBlock(block.location)
                blockBreakEvent.player.sendMessage("${ChatColor.RED}The block got removed from regeneration")
            }else{
                dataParser.setRespawnTime(block.location, configParser.respawnTimes[block.type]!!)
                setBlock(block.location, configParser.emptyBlock!!)
            }
        }
    }

    private fun setBlock(location: Location, type: Material){

        // This is fucking stupid but I can't change it
        // my guess is that events are called asynchronous
        // and therefore we cannot modify blocks
        object: BukkitRunnable() {
            override fun run() {
                location.block.type = type
            }
        }.runTask(RecraftedRegenerator.plugin)
    }
}