@echo off
:: Vazkii's JSON creator for blocks
:: Put in your /resources/assets/%modid%/models/block
:: Makes basic block JSON files as well as the acossiated item and simple blockstate
:: Can make multiple blocks at once
::
:: Usage:
:: _make (block name 1) (block name 2) (block name x)
::
:: Change this to your mod's ID
set modid=netherex

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x_slab.json
	(
		echo {
		echo   "forge_marker": 1,
		echo   "defaults": {
		echo     "model": "minecraft:half_slab",
		echo     "textures": {
		echo       "bottom": "%modid%:blocks/%%x",
		echo       "top": "%modid%:blocks/%%x",
		echo       "side": "%modid%:blocks/%%x"
		echo     },
		echo     "transform": "forge:default-block"
		echo   },
		echo   "variants": {
		echo     "type=top": [{
		echo       "x": 180,
		echo       "uvlock": true
		echo     }],
		echo     "type=bottom": [{}],
		echo     "type=double": [{
		echo       "model": "minecraft:cube_all",
        echo       "textures": {
        echo         "all": "%modid%:blocks/%%x"
        echo       }
		echo     }],
		echo     "inventory": [{}]
		echo   }
		echo }
	) > %%x_slab.json
)
