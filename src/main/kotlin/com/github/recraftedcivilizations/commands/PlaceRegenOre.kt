package com.github.recraftedcivilizations.commands

import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PlaceRegenOre {
    private val playerInRegenMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

    init {

        command("oreregen") {
            runs {
                onCommand(player)
            }
        }

    }

    /**
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    private fun onCommand(sender: CommandSender): Boolean {
        if(sender !is Player){ sender.sendMessage("Stupid console ape!!"); return true }

        if (sender in playerInRegenMode){
            playerInRegenMode.remove(sender)
            sender.sendMessage("${ChatColor.RED}You are not longer in regen mode!!")
        }else{
            playerInRegenMode.add(sender)
            sender.sendMessage("${ChatColor.GREEN} You are now in regen mode!!")
        }

        return true
    }

    fun isInRegenMode(player: Player): Boolean{
        return player in playerInRegenMode
    }
}