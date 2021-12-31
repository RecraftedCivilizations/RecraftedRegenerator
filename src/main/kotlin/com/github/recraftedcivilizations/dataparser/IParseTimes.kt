package com.github.recraftedcivilizations.dataparser

import org.bukkit.Location

/**
 * Store respawn times for blocks
 */
interface IParseTimes {

    /**
     * Get the respawn time of a block
     * @param blockLocation The location of the block
     * @return The respawn time that is left
     */
    fun getRespawnTime(blockLocation: Location): Int

    /**
     * Set the respawn time of a block
     * @param blockLocation The location of the block
     * @param respawnTime The new respawn time
     */
    fun setRespawnTime(blockLocation: Location, respawnTime: Int)

}