package com.github.recraftedcivilizations.commands

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.RecraftedRegenerator
import com.github.recraftedcivilizations.dataparser.IParseData
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.chat.literalText
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import net.axay.kspigot.event.listen
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.scheduler.BukkitRunnable

class RemoveRegenOre(private val configParser: ConfigParser, private val dataParser: IParseData) {
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

        listen<BlockBreakEvent> {
            onBlockBreak(it)
        }

    }


    fun isInRemoveMode(player: Player): Boolean{
        return player in playerInRemoveMode
    }

    private fun onBlockBreak(blockBreakEvent: BlockBreakEvent){
        val block = blockBreakEvent.block

        if (block.type in configParser.respawnTimes.keys && dataParser.isStored(block.location)){

            if (isInRemoveMode(blockBreakEvent.player)){
                dataParser.removeBlock(block.location)
                blockBreakEvent.player.sendMessage(literalText("The block got removed from regeneration") { color = KColors.RED})
            }else{
                dataParser.setRespawnTime(block.location, configParser.respawnTimes[block.type]!!)
                setBlock(block.location, configParser.emptyBlock!!)
            }
        }
    }

    private fun setBlock(location: Location, type: Material){

        // This is fucking stupid, but I can't change it
        // my guess is that events are called asynchronous
        // and therefore we cannot modify blocks
        object: BukkitRunnable() {
            override fun run() {
                location.block.type = type
            }
        }.runTask(RecraftedRegenerator.plugin)
    }
}