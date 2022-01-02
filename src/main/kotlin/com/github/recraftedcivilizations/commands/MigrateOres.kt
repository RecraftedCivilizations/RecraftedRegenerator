package com.github.recraftedcivilizations.commands

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.RecraftedRegenerator.Companion.plugin
import com.github.recraftedcivilizations.dataparser.DataParser
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.BlockIterator
import kotlin.math.ceil
import kotlin.math.floor

private class MigrateRunner(private val blocks: List<Block>, private val configParser: ConfigParser, private val dataParser: DataParser, private val migrator: Player): BukkitRunnable(){
    override fun run() {
        for (block in blocks){
            if (block.type in configParser.respawnTimes && !dataParser.isStored(block.location)){
                dataParser.storeBlock(block)

            }
        }

        object: BukkitRunnable() {
            override fun run() {
                migrator.sendMessage("${ChatColor.GREEN}Finished migration!!")
            }
        }.runTask(plugin)

    }
}



class MigrateOres(private val configParser: ConfigParser, private val dataParser: DataParser): CommandExecutor, Listener{
    private val locationMap: MutableMap<Player, Pair<Location?, Location?>> = emptyMap<Player, Pair<Location?, Location?>>().toMutableMap()
    private val inSetupMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

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
        if (sender !is Player){ return false }

        if(args.isEmpty()){ return false }

        if (args[0] == "setup"){

            if (sender in inSetupMode){
                inSetupMode.remove(sender)
                locationMap.remove(sender)
                sender.sendMessage("${ChatColor.RED}You are not longer in migration mode!!")
            }else{
                inSetupMode.add(sender)
                locationMap[sender] = Pair(null, null)
                sender.sendMessage("${ChatColor.GREEN}You are now in migration mode!")
            }

            return true

        }else if (args[0] == "migrate"){

            if (locationMap[sender]?.first != null && locationMap[sender]?.second != null){
                val blocks = getRegionBlocks(sender.world, locationMap[sender]?.first!!, locationMap[sender]?.second!!)

                val migrationRunner = MigrateRunner(blocks, configParser, dataParser, sender)
                migrationRunner.runTaskAsynchronously(plugin)
                return true

            }else{
                sender.sendMessage("${ChatColor.RED}You did not set two locations!!")
            }

        }

        return false
    }


    private fun getRegionBlocks(world: World, loc1: Location, loc2: Location): List<Block> {
        val blocks = emptyList<Block>().toMutableList()

        for(x in ceil(loc1.x.coerceAtMost(loc2.x)).toInt()..floor(loc1.x.coerceAtLeast(loc2.x)).toInt()){
            for(y in ceil(loc1.y.coerceAtMost(loc2.y)).toInt()..floor(loc1.y.coerceAtLeast(loc2.y)).toInt()){
                for(z in ceil(loc1.z.coerceAtMost(loc2.z)).toInt()..floor(loc1.z.coerceAtLeast(loc2.z)).toInt()){
                    blocks.add(world.getBlockAt(x, y, z))
                }

            }

        }

        return blocks

    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent){

        if (e.player in inSetupMode){
            e.isCancelled = true
            if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND){
                locationMap[e.player] = Pair(e.clickedBlock?.location, locationMap[e.player]?.second)
                e.player.sendMessage("${ChatColor.GREEN}Set the second migration location!!")
            }else if (e.action == Action.LEFT_CLICK_BLOCK){
                locationMap[e.player] = Pair(locationMap[e.player]?.first, e.clickedBlock?.location)
                e.player.sendMessage("${ChatColor.RED}Set the first migration location!!")
            }
        }

    }
}