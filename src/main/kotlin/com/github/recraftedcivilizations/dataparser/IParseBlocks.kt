package com.github.recraftedcivilizations.dataparser

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * Store blocks
 */
interface IParseBlocks {
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
     * Check if the block at the specific location will/should respawn
     * @param location The location of the block
     * @return If the block is stored and should therefore respawn
     */
    fun isStored(location: Location): Boolean
}