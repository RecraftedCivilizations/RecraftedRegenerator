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
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class RecraftedRegenerator: JavaPlugin() {
    private lateinit var dataParser: DataParser

    companion object{
        lateinit var plugin: JavaPlugin
    }

    override fun onEnable(){

        saveDefaultConfig()

        val configParser = ConfigParser(this.config)
        configParser.load()

        val regenCommand = PlaceRegenOre()
        this.getCommand("oreregen")?.setExecutor(regenCommand)

        dataParser = DataParser(YAMLBlockParser(this.dataFolder.path), CachedTimeParser())

        val migrator = MigrateOres(configParser, dataParser)
        this.getCommand("migrateores")?.setExecutor(migrator)

        val removeRegenOre = RemoveRegenOre()
        this.getCommand("removeregen")?.setExecutor(removeRegenOre)

        Bukkit.getPluginManager().registerEvents(BlockBreakListener(dataParser, configParser, removeRegenOre), this)
        Bukkit.getPluginManager().registerEvents(BlockPlaceListener(regenCommand, dataParser, configParser), this)
        Bukkit.getPluginManager().registerEvents(migrator, this)
        Bukkit.getPluginManager().registerEvents(RightClickListener(dataParser), this)


        val regenerator = Regenerator(dataParser, configParser)
        regenerator.runTaskTimer(this, 0L, configParser.interval!! * 20L)

        plugin = this

    }

    override fun onDisable() {
        Bukkit.getLogger().info("Respawning all blocks!!")
        for((location, time) in dataParser.getBlocksToRespawn().entries){
            location.block.type = dataParser.getBlockType(location)!!
        }
    }
}