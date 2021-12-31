package com.github.recraftedcivilizations

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * @author DarkVanityOfLight
 */

/**
 * Store and get data about blocks that have
 * to be regenerated
 */
interface IParseData {

    /**
     * Set a block in the data
     * @param block The block that should respawn after a delay
     */
    fun storeBlock(block: Block)

    /**
     * Delete a block from the storage
     * @param blockLocation The location of the block that should be removed
     */
    fun removeBlock(blockLocation: Location)

    /**
     * Get the type of a stored block
     * @param blockLocation The location of the block
     * @return The Material the block should respawn as
     */
    fun getBlockType(blockLocation: Location): Material

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