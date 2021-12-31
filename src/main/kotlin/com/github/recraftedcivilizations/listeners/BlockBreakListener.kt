package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.dataparser.IParseData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener(private val dataParser: IParseData, private val configParser: ConfigParser): Listener {

    @EventHandler
    fun onBlockBreak(blockBreakEvent: BlockBreakEvent){
        val block = blockBreakEvent.block

        if (block.type in configParser.respawnTimes.keys && dataParser.isStored(block.location)){
            setBlock(block.location, configParser.emptyBlock!!)
            dataParser.setRespawnTime(block.location, configParser.respawnTimes[block.type]!!)
        }
    }

    private fun setBlock(location: Location, type: Material){
        location.block.type = type
    }
}