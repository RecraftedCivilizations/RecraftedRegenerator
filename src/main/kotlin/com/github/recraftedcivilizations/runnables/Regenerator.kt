package com.github.recraftedcivilizations.runnables

import com.github.recraftedcivilizations.ConfigParser
import com.github.recraftedcivilizations.dataparser.DataParser
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable

class Regenerator(private val dataParser: DataParser, private val configParser: ConfigParser): BukkitRunnable() {

    override fun run() {

        val toRespawn = dataParser.getBlocksToRespawn().toMap()

        for((location, time) in toRespawn.entries){

            val shouldRespawn = dataParser.setRespawnTime(location, time - configParser.interval!!)

            if(shouldRespawn){
                val material = dataParser.getBlockType(location) ?: continue
                respawn(location, material)

            }
        }
    }

    private fun respawn(location: Location, type: Material){
        location.block.type = type
    }
}