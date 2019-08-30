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
	echo Making %%x_fence_gate.json blockstate
	(
		echo {
		echo 	"variants": {
        echo        "facing=south,in_wall=false,open=false": { "model": "%modid%:block/%%x_fence_gate_closed", "uvlock": true },
        echo        "facing=west,in_wall=false,open=false":  { "model": "%modid%:block/%%x_fence_gate_closed", "uvlock": true, "y": 90 },
        echo        "facing=north,in_wall=false,open=false": { "model": "%modid%:block/%%x_fence_gate_closed", "uvlock": true, "y": 180 },
        echo        "facing=east,in_wall=false,open=false":  { "model": "%modid%:block/%%x_fence_gate_closed", "uvlock": true, "y": 270 },
        echo        "facing=south,in_wall=false,open=true": { "model": "%modid%:block/%%x_fence_gate_open", "uvlock": true },
        echo        "facing=west,in_wall=false,open=true":  { "model": "%modid%:block/%%x_fence_gate_open", "uvlock": true, "y": 90 },
        echo        "facing=north,in_wall=false,open=true": { "model": "%modid%:block/%%x_fence_gate_open", "uvlock": true, "y": 180 },
        echo        "facing=east,in_wall=false,open=true":  { "model": "%modid%:block/%%x_fence_gate_open", "uvlock": true, "y": 270 },
        echo        "facing=south,in_wall=true,open=false": { "model": "%modid%:block/%%x_wall_gate_closed", "uvlock": true },
        echo        "facing=west,in_wall=true,open=false":  { "model": "%modid%:block/%%x_wall_gate_closed", "uvlock": true, "y": 90 },
        echo        "facing=north,in_wall=true,open=false": { "model": "%modid%:block/%%x_wall_gate_closed", "uvlock": true, "y": 180 },
        echo        "facing=east,in_wall=true,open=false":  { "model": "%modid%:block/%%x_wall_gate_closed", "uvlock": true, "y": 270 },
        echo        "facing=south,in_wall=true,open=true": { "model": "%modid%:block/%%x_wall_gate_open", "uvlock": true },
        echo        "facing=west,in_wall=true,open=true":  { "model": "%modid%:block/%%x_wall_gate_open", "uvlock": true, "y": 90 },
        echo        "facing=north,in_wall=true,open=true": { "model": "%modid%:block/%%x_wall_gate_open", "uvlock": true, "y": 180 },
        echo        "facing=east,in_wall=true,open=true": { "model": "%modid%:block/%%x_wall_gate_open", "uvlock": true, "y": 270 }
		echo 	}
		echo }
	) > ../../blockstates/%%x_fence_gate.json

	echo Making %%x_fence_gate_closed.json block model
	(
		echo {
		echo 	"parent": "block/template_fence_gate",
		echo 	"textures": {
		echo 		"texture": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_fence_gate_closed.json

	echo Making %%x_fence_gate_open.json block model
	(
		echo {
		echo 	"parent": "block/template_fence_gate_open",
		echo 	"textures": {
		echo 		"texture": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_fence_gate_open.json

	echo Making %%x_wall_gate_closed.json block model
	(
		echo {
		echo 	"parent": "block/template_fence_gate_wall",
		echo 	"textures": {
		echo 		"texture": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_wall_gate_closed.json

	echo Making %%x_wall_gate_open.json block model
	(
		echo {
		echo 	"parent": "block/template_fence_gate_wall_open",
		echo 	"textures": {
		echo 		"texture": "%modid%:block/%%x"
		echo 	}
		echo }
	) > %%x_wall_gate_open.json

	echo Making %%x_fence_gate.json item model
	(
		echo {
		echo 	"parent": "%modid%:block/%%x_fence_gate_closed"
		echo }
	) > ../item/%%x_fence_gate.json
)
