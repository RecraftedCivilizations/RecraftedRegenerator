package com.github.recraftedcivilizations

import com.github.recraftedcivilizations.commands.MigrateOres
import com.github.recraftedcivilizations.commands.PlaceRegenOre
import com.github.recraftedcivilizations.commands.RemoveRegenOre
import com.github.recraftedcivilizations.dataparser.DataParser
import com.github.recraftedcivilizations.dataparser.blockparser.YAMLBlockParser
import com.github.recraftedcivilizations.dataparser.timeparser.CachedTimeParser
import com.github.recraftedcivilizations.listeners.BlockBreakListener
import com.github.recraftedcivilizations.listeners.BlockPlaceListener
import com.github.recraftedcivilizations.listeners.RightClickListener
import com.github.recraftedcivilizations.runnables.Regenerator
import net.axay.kspigot.commands.command
import net.axay.kspigot.commands.runs
import net.axay.kspigot.extensions.bukkit.register
import net.axay.kspigot.main.KSpigot
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class RecraftedRegenerator: KSpigot() {
    private lateinit var dataParser: DataParser

    companion object{
        lateinit var plugin: JavaPlugin
    }

    override fun startup() {

        saveDefaultConfig()

        val configParser = ConfigParser(this.config)
        configParser.load()

        val regenCommand = PlaceRegenOre()


        dataParser = DataParser(YAMLBlockParser(this.dataFolder.path), CachedTimeParser())

        val migrator = MigrateOres(configParser, dataParser)

        val removeRegenOre = RemoveRegenOre()


        val regenerator = Regenerator(dataParser, configParser)
        regenerator.runTaskTimer(this, 0L, configParser.interval!! * 20L)

        plugin = this

    }

    override fun shutdown() {
        Bukkit.getLogger().info("Respawning all blocks!!")
        for((location, time) in dataParser.getBlocksToRespawn().entries){
            location.block.type = dataParser.getBlockType(location)!!
        }
    }
}