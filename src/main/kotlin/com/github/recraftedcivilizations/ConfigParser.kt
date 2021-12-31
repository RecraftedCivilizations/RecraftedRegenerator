package com.github.recraftedcivilizations

import org.bukkit.Material
import org.bukkit.configuration.Configuration
import org.bukkit.configuration.ConfigurationSection

/**
 * @author DarkVanityOfLight
 */

fun Configuration.getMaterial(path: String, def: Material): Material{
    val strMat = this.getString(path) ?: return  def
    return Material.matchMaterial(strMat) ?: return def
}

fun Configuration.getMaterial(path: String): Material?{
    val strMat = this.getString(path) ?: return  null
    return Material.matchMaterial(strMat) ?: return null
}

class ConfigParser(private val config: Configuration) {
    var respawnTimes: MutableMap<Material, Int> = emptyMap<Material, Int>().toMutableMap()
    var defaultDelay: Int? = null
    var emptyBlock: Material? = null
    var interval: Int? = null

    /**
     * Load data from a configuration
     */
    fun load(){


        // Get the interval in which we check for the blocks
        interval = config.getInt("interval")

        // Create a new empty mutable map so we don't use the old config values again
        respawnTimes = emptyMap<Material, Int>().toMutableMap()
        // Get the default delay for every material if not defined explicitly
        defaultDelay = config.getInt("defaultDelay", 0)
        // Get the material the block should show up as when mined
        emptyBlock = config.getMaterial("empty", Material.BEDROCK)

        // Get the Config section with all materials and delays
        val delaySection: ConfigurationSection = config.getConfigurationSection("delays")
            ?: throw NoSuchElementException("Please define a delay section in your config file!!")

        // Loop through all listed materials
        for (materialKey in delaySection.getKeys(false)){

            // Get the material from the string key
            val material: Material = Material.matchMaterial(materialKey)
                ?: throw IllegalArgumentException("The supplied material $materialKey does not exist as material!!")

            // Get the section of the material
            val materialSection = delaySection.getConfigurationSection(materialKey)!!
            // Get the delay of the material if none is set put the default delay in
            val delay = materialSection.getInt("${materialKey}.delay", defaultDelay!!)

            // Add the material to the respawn types
            respawnTimes[material] = delay

        }

    }


}