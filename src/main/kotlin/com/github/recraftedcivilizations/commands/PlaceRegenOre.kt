package com.github.recraftedcivilizations.commands

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.dataparser.IParseData
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.chat.literalText
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import net.axay.kspigot.event.listen
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockPlaceEvent

class PlaceRegenOre(private val configParser: ConfigParser, private val dataParser: IParseData) {
    private val playerInRegenMode: MutableSet<Player> = emptySet<Player>().toMutableSet()

    init {

        command("oreregen") {
            runs {
                onCommand(player)
            }
        }

        listen<BlockPlaceEvent> {
            onBlockPlaceEvent(it)
        }


    }

    private fun onCommand(player: Player) {

        if (player in playerInRegenMode){
            playerInRegenMode.remove(player)
            player.sendMessage(literalText("You are not longer in regen mode!!") { color = KColors.RED})
        }else{
            playerInRegenMode.add(player)
            player.sendMessage(literalText("You are now in regen mode!!") { color = KColors.GREEN})
        }

    }

    fun isInRegenMode(player: Player): Boolean{
        return player in playerInRegenMode
    }

    private fun onBlockPlaceEvent(e: BlockPlaceEvent){
        if (isInRegenMode(e.player) && e.block.type in configParser.respawnTimes.keys){
            dataParser.storeBlock(e.block)
        }
    }
}