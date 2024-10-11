package com.github.recraftedcivilizations.commands

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.RecraftedRegenerator.Companion.plugin
import com.github.recraftedcivilizations.dataparser.DataParser
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.chat.literalText
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.literal
import net.axay.kspigot.commands.runs
import net.axay.kspigot.event.listen
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.scheduler.BukkitRunnable
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
                migrator.sendMessage(literalText("Finished migration!!") { color = KColors.GREEN})
            }
        }.runTask(plugin)

    }
}



class MigrateOres(private val configParser: ConfigParser, private val dataParser: DataParser){
    private val locationMap: MutableMap<Player, Pair<Location?, Location?>> = emptyMap<Player, Pair<Location?, Location?>>().toMutableMap()
    private val inSetupMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

    init {
        command("migrateOres") {
                literal("setup") {
                    runs {
                        setup(player)
                    }
                }

                literal("migrate") {
                    runs {
                        migrate(player)
                    }
                }
        }

        listen<PlayerInteractEvent> {
            onPlayerInteract(it)
        }
    }

    private fun setup(sender: Player){
        if (sender in inSetupMode){
            inSetupMode.remove(sender)
            locationMap.remove(sender)
            sender.sendMessage(literalText("You are not longer in migration mode!") { color = KColors.RED})
        }else{
            inSetupMode.add(sender)
            locationMap[sender] = Pair(null, null)
            sender.sendMessage(literalText("You are now in migration mode!") { color = KColors.GREEN})
        }
    }

    private fun migrate(sender: Player){
        if (locationMap[sender]?.first != null && locationMap[sender]?.second != null){
            val blocks = getRegionBlocks(sender.world, locationMap[sender]?.first!!, locationMap[sender]?.second!!)

            val migrationRunner = MigrateRunner(blocks, configParser, dataParser, sender)
            migrationRunner.runTaskAsynchronously(plugin)

        }else{
            sender.sendMessage(literalText("You did not set two locations!!") { color = KColors.RED})
        }
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

    private fun onPlayerInteract(e: PlayerInteractEvent){
        if (e.player in inSetupMode){
            e.isCancelled = true
            if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND){
                locationMap[e.player] = Pair(e.clickedBlock?.location, locationMap[e.player]?.second)
                e.player.sendMessage(literalText("Set the second migration location!!") { color = KColors.GREEN})
            }else if (e.action == Action.LEFT_CLICK_BLOCK){
                locationMap[e.player] = Pair(locationMap[e.player]?.first, e.clickedBlock?.location)
                e.player.sendMessage(literalText("Set the first migration location!!") { color = KColors.GREEN})
            }
        }

    }
}