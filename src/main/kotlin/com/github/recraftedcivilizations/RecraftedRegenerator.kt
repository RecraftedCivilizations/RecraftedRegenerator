package com.github.recraftedcivilizations

import com.github.recraftedcivilizations.commands.PlaceRegenOre
import com.github.recraftedcivilizations.dataparser.DataParser
import com.github.recraftedcivilizations.dataparser.blockparser.YAMLBlockParser
import com.github.recraftedcivilizations.dataparser.timeparser.CachedTimeParser
import com.github.recraftedcivilizations.listeners.BlockBreakListener
import com.github.recraftedcivilizations.listeners.BlockPlaceListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class RecraftedRegenerator: JavaPlugin() {

    override fun onEnable(){

        val configParser = ConfigParser(this.config)
        configParser.load()

        val regenCommand = PlaceRegenOre()
        this.getCommand("oreregen")?.setExecutor(regenCommand)

        val dataParser = DataParser(YAMLBlockParser(this.dataFolder.path), CachedTimeParser())

        Bukkit.getPluginManager().registerEvents(BlockBreakListener(dataParser, configParser), this)
        Bukkit.getPluginManager().registerEvents(BlockPlaceListener(regenCommand, dataParser, configParser), this)
    }
}