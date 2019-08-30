@echo off

set modid=netherex

setlocal enabledelayedexpansion

for %%x in (%*) do (
	echo Making %%x.json blockstate
	(
		echo {
		echo     "multipart": [
		echo         {   "when": { "up": "true" },
		echo             "apply": { "model": "%modid%:block/%%x_wall_post" }
		echo         },
		echo         {   "when": { "north": "true" },
		echo             "apply": { "model": "%modid%:block/%%x_wall_side", "uvlock": true  }
		echo         },
		echo         {   "when": { "east": "true" },
		echo             "apply": { "model": "%modid%:block/%%x_wall_side", "y": 90, "uvlock": true }
		echo         },
		echo         {   "when": { "south": "true" },
		echo             "apply": { "model": "%modid%:block/%%x_wall_side", "y": 180, "uvlock": true }
		echo         },
		echo         {   "when": { "west": "true" },
		echo             "apply": { "model": "%modid%:block/%%x_wall_side", "y": 270, "uvlock": true }
		echo         }
		echo     ]
		echo }
	) > ../../blockstates/%%x_wall.json

	echo Making %%x_wall_post.json block model
	(
		echo {
		echo 	"parent": "minecraft:block/template_wall_post",
		echo 	"textures": {
		echo 		"wall": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_wall_post.json

	echo Making %%x_wall_side.json block model
	(
		echo {
		echo 	"parent": "minecraft:block/template_wall_side",
		echo 	"textures": {
		echo 		"wall": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_wall_side.json

	echo Making %%x_wall_inventory.json block model
	(
		echo {
		echo 	"parent": "minecraft:block/wall_inventory",
		echo 	"textures": {
		echo 		"wall": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_wall_inventory.json

	echo Making %%x_wall.json item model
	(
		echo {
		echo	"parent": "%modid%:block/%%x_wall_inventory"
		echo }
	) > ../item/%%x_wall.json
)
