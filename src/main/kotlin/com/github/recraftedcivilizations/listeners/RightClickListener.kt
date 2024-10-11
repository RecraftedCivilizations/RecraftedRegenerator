package com.github.recraftedcivilizations.listeners

import com.github.recraftedcivilizations.dataparser.IParseData
import net.axay.kspigot.event.listen
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class RightClickListener(private val dataParser: IParseData) {

    init {
        listen<PlayerInteractEvent> {
            onRightClick(it)
        }
    }

    private fun onRightClick(e: PlayerInteractEvent){
        if (e.hand != EquipmentSlot.HAND || e.action != Action.RIGHT_CLICK_BLOCK){ return }

        val respawnTime = dataParser.getRespawnTime(e.clickedBlock!!.location)

        if (respawnTime != null){
            e.player.sendMessage("${ChatColor.GREEN}This block will respawn in $respawnTime seconds!")
        }

    }
}