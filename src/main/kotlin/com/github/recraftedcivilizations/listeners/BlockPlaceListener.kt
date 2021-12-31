package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.commands.PlaceRegenOre
import com.github.recraftedcivilizations.dataparser.IParseData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class BlockPlaceListener(private val regenMode: PlaceRegenOre, private val dataParser: IParseData, private val configParser: ConfigParser): Listener {

    @EventHandler
    fun onBlockPlaceEvent(e: BlockPlaceEvent){
        if (regenMode.isInRegenMode(e.player) && e.block.type in configParser.respawnTimes.keys){
            dataParser.storeBlock(e.block)
        }
    }

}