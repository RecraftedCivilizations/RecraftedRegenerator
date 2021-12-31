package com.github.recraftedcivilizations.dataparser.timeparser

import org.bukkit.Location

/**
 * Store respawn times for blocks
 */
interface IParseTimes {

    /**
     * Get the respawn time of a block
     * @param blockLocation The location of the block
     * @return The respawn time that is left or null if the block isn't stored
     */
    fun getRespawnTime(blockLocation: Location): Int?

    /**
     * Set the respawn time of a block
     * @param blockLocation The location of the block
     * @param respawnTime The new respawn time
     * @return Return true if the block should respawn now
     */
    fun setRespawnTime(blockLocation: Location, respawnTime: Int): Boolean

    /**
     * Get all blocks that still need to respawn
     * @return A map of all locations of blocks that still need to respawn with their respawn time as value
     */
    fun getBlocksToRespawn(): Map<Location, Int>

}