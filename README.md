# RecraftedRegenerator

Recrafted Regenerator allows you to place blocks, that will respawn after a certain amount of time.

# Configuration

Once you started your server the plugin will generate a data and a config file. You should 
not edit the data file manually but the config file is yours to edit and customize.
The generated file will look like this:
```yaml
interval: 10
defaultDelay: 20
empty: BEDROCK

delays:
  IRON_ORE:
    delay: 20
  GOLD_ORE:
    delay: 30
```
The `interval` tag defines how often the plugin will check for respawning ores.  
The `defaultDelay` tag defines the delay that the defined ores will respawn at if no other was defined specifically  
The `empty` tag defines the block type the ore block will show up as when mined
In the `delays` section you can add all the blocks that should respawn, to find all possible types and names use: [this resource](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)
After you defined the ore you can define its respawn delay with the `delay` tag.  
`> Note: All your respawn times should be dividable by the intervall to assure smoother respawn times`

# Commands and Permissions
## Regeneration mode
The `/oreregen` command is used to place blocks that will regenerate after the delay defined in the config  
`> Note: Blocks that aren't defined in the config will not regenerate even if placed in regen mode`  
To leave the ore regeneration mode use the command again.  
#### This command requires the: `recraftedregenerator.regen` permission

## Migration mode
The migration mode is used to make all already placed ores in a defined region regenerate.
To enter the migration mode use the command `/migrateores setup`. Now you can define a region
by using left and right blocks(just like with world edit but without the axe).
After you defined the area type `/migrateores migrate` to migrate all the ores.
To leave migration mode use the `/migrateores setup` command again.  
`> Note: After using /migrateores migrate you will still be in migration mode`  
#### This command requires the: `recraftedregenerator.migrate` permission

## Remove Regeneration mode
The `/removeregen` command is used to put you in a mode that enables you to remove already
placed regenerative ores again. To leave this mode type `/removeregen` again.
#### This command requires the: `recraftedregenerator.removeregen` permission