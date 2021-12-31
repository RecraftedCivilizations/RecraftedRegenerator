package com.github.recraftedcivilizations.dataparser

import com.github.recraftedcivilizations.dataparser.blockparser.IParseBlocks
import com.github.recraftedcivilizations.dataparser.timeparser.IParseTimes
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

class DataParser(private val blockParser: IParseBlocks, private val timeParser: IParseTimes): IParseData {



    /**
     * Set a block in the data
     * @param block The block that should respawn after a delay
     */
    override fun storeBlock(block: Block) {
        blockParser.storeBlock(block)
    }

    /**
     * Delete a block from the storage
     * @param blockLocation The location of the block that should be removed
     */
    override fun removeBlock(blockLocation: Location) {
        blockParser.removeBlock(blockLocation)
    }

    /**
     * Get the type of a stored block
     * @param blockLocation The location of the block
     * @return The Material the block should respawn as or null if the block isn't stored
     */
    override fun getBlockType(blockLocation: Location): Material? {
        return blockParser.getBlockType(blockLocation)
    }

    /**
     * Check if the block at the specific location will/should respawn
     * @param location The location of the block
     * @return If the block is stored and should therefore respawn
     */
    override fun isStored(location: Location): Boolean {
        return blockParser.isStored(location)
    }

    /**
     * Get the respawn time of a block
     * @param blockLocation The location of the block
     * @return The respawn time that is left or null if the block isn't stored
     */
    override fun getRespawnTime(blockLocation: Location): Int? {
        return timeParser.getRespawnTime(blockLocation)
    }

    /**
     * Set the respawn time of a block
     * @param blockLocation The location of the block
     * @param respawnTime The new respawn time
     */
    override fun setRespawnTime(blockLocation: Location, respawnTime: Int) {
        timeParser.setRespawnTime(blockLocation, respawnTime)
    }
}