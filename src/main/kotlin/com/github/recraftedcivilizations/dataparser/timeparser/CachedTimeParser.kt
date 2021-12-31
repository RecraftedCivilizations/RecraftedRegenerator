package com.github.recraftedcivilizations.dataparser.timeparser

import org.bukkit.Location

class CachedTimeParser: IParseTimes {
    private val respawnTimes : MutableMap<Location, Int> = emptyMap<Location, Int>().toMutableMap()

    /**
     * Get the respawn time of a block
     * @param blockLocation The location of the block
     * @return The respawn time that is left
     */
    override fun getRespawnTime(blockLocation: Location): Int? {
        return respawnTimes[blockLocation]
    }

    /**
     * Set the respawn time of a block
     * @param blockLocation The location of the block
     * @param respawnTime The new respawn time
     */
    override fun setRespawnTime(blockLocation: Location, respawnTime: Int) {
        if (respawnTime <= 0){ respawnTimes.remove(blockLocation); return }
        respawnTimes[blockLocation] = respawnTime
    }

    /**
     * Get all blocks that still need to respawn
     * @return A map of all locations of blocks that still need to respawn with their respawn time as value
     */
    override fun getBlocksToRespawn(): Map<Location, Int> {
        return respawnTimes
    }
}