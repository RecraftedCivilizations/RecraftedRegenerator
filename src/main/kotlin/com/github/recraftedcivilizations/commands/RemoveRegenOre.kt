package com.github.recraftedcivilizations.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveRegenOre: CommandExecutor {
    private val playerInRemoveMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

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
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player){ sender.sendMessage("Stupid console ape!!"); return true }

        if (sender in playerInRemoveMode){
            playerInRemoveMode.remove(sender)
            sender.sendMessage("${ChatColor.RED}You are not longer in regen remove mode!!")
        }else{
            playerInRemoveMode.add(sender)
            sender.sendMessage("${ChatColor.GREEN} You are now in regen remove mode!!")
        }

        return true
    }

    fun isInRemoveMode(player: Player): Boolean{
        return player in playerInRemoveMode
    }
}