@echo off

set modid=netherex

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x.json block post
	(
		echo {
		echo 	"parent": "minecraft:block/wall_post",
		echo 	"textures": {
		echo 		"wall": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x_wall_post.json

	echo Making %%x.json block side
	(
		echo {
		echo 	"parent": "minecraft:block/wall_side",
		echo 	"textures": {
		echo 		"wall": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x_wall_side.json

	echo Making %%x.json block inventory
	(
		echo {
		echo 	"parent": "minecraft:block/wall_inventory",
		echo 	"textures": {
		echo 		"wall": "%modid%:blocks/%%x"
		echo 	}
		echo }
	) > %%x_wall_inventory.json

	echo Making %%x.json item
	(
		echo {
		echo	"parent": "%modid%:block/%%x_wall_inventory"
		echo }
	) > ../item/%%x_wall.json


	echo Making %%x.json blockstate
	(
		echo {
		echo     "multipart": [
		echo         {   "when": { "up": "true" },
		echo             "apply": { "model": "%modid%:%%x_wall_post" }
		echo         },
		echo         {   "when": { "north": "true" },
		echo             "apply": { "model": "%modid%:%%x_wall_side", "uvlock": true  }
		echo         },
		echo         {   "when": { "east": "true" },
		echo             "apply": { "model": "%modid%:%%x_wall_side", "y": 90, "uvlock": true }
		echo         },
		echo         {   "when": { "south": "true" },
		echo             "apply": { "model": "%modid%:%%x_wall_side", "y": 180, "uvlock": true }
		echo         },
		echo         {   "when": { "west": "true" },
		echo             "apply": { "model": "%modid%:%%x_wall_side", "y": 270, "uvlock": true }
		echo         }
		echo     ]
		echo }
	) > ../../blockstates/%%x_wall.json
)
