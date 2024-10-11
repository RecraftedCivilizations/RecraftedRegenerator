package com.github.recraftedcivilizations.commands

import net.axay.kspigot.chat.KColors
import net.axay.kspigot.chat.literalText
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveRegenOre {
    private val playerInRemoveMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

    init {
        command("removeOre"){
            runs {
                if (player in playerInRemoveMode){
                    playerInRemoveMode.remove(player)
                    player.sendMessage(literalText("You are not longer in regen remove mode!!") { color = KColors.RED})
                }else{
                    playerInRemoveMode.add(player)
                    player.sendMessage(literalText("You are now in regen remove mode!!") { color = KColors.GREEN})
                }
            }
        }
    }


    fun isInRemoveMode(player: Player): Boolean{
        return player in playerInRemoveMode
    }
}