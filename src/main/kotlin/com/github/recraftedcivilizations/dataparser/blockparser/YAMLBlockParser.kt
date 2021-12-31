package com.github.recraftedcivilizations.dataparser.blockparser

import com.github.recraftedcivilizations.getMaterial
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

const val fileName = "data.yml"

class YAMLBlockParser(var filePath: String): IParseBlocks{
    private val dataFile : YamlConfiguration = YamlConfiguration()

    init {
        filePath = if (filePath.endsWith("/")){
            "$filePath$fileName"
        }else{
            "$filePath/$fileName"
        }

        val file = File(filePath)

        if (file.exists()){
            dataFile.load(file)
        }else{
            file.createNewFile()
        }
    }

    /**
     * Set a block in the data
     * @param block The block that should respawn after a delay
     */
    override fun storeBlock(block: Block) {
        val type = block.type
        val location = block.location

        dataFile.set("blocks.${location.hashCode()}.location", location)
        dataFile.set("blocks.${location.hashCode()}.material", type.name)
        save()
    }

    /**
     * Delete a block from the storage
     * @param blockLocation The location of the block that should be removed
     */
    override fun removeBlock(blockLocation: Location) {
        dataFile.set("blocks.${blockLocation.hashCode()}", null)
        save()
    }

    /**
     * Get the type of a stored block
     * @param blockLocation The location of the block
     * @return The Material the block should respawn as or null if the block isn't stored
     */
    override fun getBlockType(blockLocation: Location): Material? {
        return dataFile.getMaterial("blocks.${blockLocation.hashCode()}.material")
    }

    /**
     * Check if the block at the specific location will/should respawn
     * @param location The location of the block
     * @return If the block is stored and should therefore respawn
     */
    override fun isStored(location: Location): Boolean {
        return dataFile.isSet("blocks.${location.hashCode()}")
    }

    private fun load(){
        dataFile.load(filePath)
    }

    private fun save(){
        dataFile.save(filePath)
    }
}