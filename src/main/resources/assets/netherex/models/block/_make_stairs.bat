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
	echo Making %%x_stairs.json blockstate
	(
		echo {
		echo   "variants": {
		echo		"facing=east,half=bottom,shape=straight":  { "model": "%modid%:block/%%x_stairs" },
		echo		"facing=west,half=bottom,shape=straight":  { "model": "%modid%:block/%%x_stairs", "y": 180, "uvlock": true },
		echo		"facing=south,half=bottom,shape=straight": { "model": "%modid%:block/%%x_stairs", "y": 90, "uvlock": true },
		echo		"facing=north,half=bottom,shape=straight": { "model": "%modid%:block/%%x_stairs", "y": 270, "uvlock": true },
		echo		"facing=east,half=bottom,shape=outer_right":  { "model": "%modid%:block/%%x_stairs_outer" },
		echo		"facing=west,half=bottom,shape=outer_right":  { "model": "%modid%:block/%%x_stairs_outer", "y": 180, "uvlock": true },
		echo		"facing=south,half=bottom,shape=outer_right": { "model": "%modid%:block/%%x_stairs_outer", "y": 90, "uvlock": true },
		echo		"facing=north,half=bottom,shape=outer_right": { "model": "%modid%:block/%%x_stairs_outer", "y": 270, "uvlock": true },
		echo		"facing=east,half=bottom,shape=outer_left":  { "model": "%modid%:block/%%x_stairs_outer", "y": 270, "uvlock": true },
		echo		"facing=west,half=bottom,shape=outer_left":  { "model": "%modid%:block/%%x_stairs_outer", "y": 90, "uvlock": true },
		echo		"facing=south,half=bottom,shape=outer_left": { "model": "%modid%:block/%%x_stairs_outer" },
		echo		"facing=north,half=bottom,shape=outer_left": { "model": "%modid%:block/%%x_stairs_outer", "y": 180, "uvlock": true },
		echo		"facing=east,half=bottom,shape=inner_right":  { "model": "%modid%:block/%%x_stairs_inner" },
		echo		"facing=west,half=bottom,shape=inner_right":  { "model": "%modid%:block/%%x_stairs_inner", "y": 180, "uvlock": true },
		echo		"facing=south,half=bottom,shape=inner_right": { "model": "%modid%:block/%%x_stairs_inner", "y": 90, "uvlock": true },
		echo		"facing=north,half=bottom,shape=inner_right": { "model": "%modid%:block/%%x_stairs_inner", "y": 270, "uvlock": true },
		echo		"facing=east,half=bottom,shape=inner_left":  { "model": "%modid%:block/%%x_stairs_inner", "y": 270, "uvlock": true },
		echo		"facing=west,half=bottom,shape=inner_left":  { "model": "%modid%:block/%%x_stairs_inner", "y": 90, "uvlock": true },
		echo		"facing=south,half=bottom,shape=inner_left": { "model": "%modid%:block/%%x_stairs_inner" },
		echo		"facing=north,half=bottom,shape=inner_left": { "model": "%modid%:block/%%x_stairs_inner", "y": 180, "uvlock": true },
		echo		"facing=east,half=top,shape=straight":  { "model": "%modid%:block/%%x_stairs", "x": 180, "uvlock": true },
		echo		"facing=west,half=top,shape=straight":  { "model": "%modid%:block/%%x_stairs", "x": 180, "y": 180, "uvlock": true },
		echo		"facing=south,half=top,shape=straight": { "model": "%modid%:block/%%x_stairs", "x": 180, "y": 90, "uvlock": true },
		echo		"facing=north,half=top,shape=straight": { "model": "%modid%:block/%%x_stairs", "x": 180, "y": 270, "uvlock": true },
		echo		"facing=east,half=top,shape=outer_right":  { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 90, "uvlock": true },
		echo		"facing=west,half=top,shape=outer_right":  { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 270, "uvlock": true },
		echo		"facing=south,half=top,shape=outer_right": { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 180, "uvlock": true },
		echo		"facing=north,half=top,shape=outer_right": { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "uvlock": true },
		echo		"facing=east,half=top,shape=outer_left":  { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "uvlock": true },
		echo		"facing=west,half=top,shape=outer_left":  { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 180, "uvlock": true },
		echo		"facing=south,half=top,shape=outer_left": { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 90, "uvlock": true },
		echo		"facing=north,half=top,shape=outer_left": { "model": "%modid%:block/%%x_stairs_outer", "x": 180, "y": 270, "uvlock": true },
		echo		"facing=east,half=top,shape=inner_right":  { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 90, "uvlock": true },
		echo		"facing=west,half=top,shape=inner_right":  { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 270, "uvlock": true },
		echo		"facing=south,half=top,shape=inner_right": { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 180, "uvlock": true },
		echo		"facing=north,half=top,shape=inner_right": { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "uvlock": true },
		echo		"facing=east,half=top,shape=inner_left":  { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "uvlock": true },
		echo		"facing=west,half=top,shape=inner_left":  { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 180, "uvlock": true },
		echo		"facing=south,half=top,shape=inner_left": { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 90, "uvlock": true },
		echo		"facing=north,half=top,shape=inner_left": { "model": "%modid%:block/%%x_stairs_inner", "x": 180, "y": 270, "uvlock": true }
		echo   }
		echo }
	) > ../../blockstates/%%x_stairs.json

	echo Making %%x_stairs.json block model
    (
    	echo {
    	echo 	"parent": "block/stairs",
    	echo     "textures": {
    	echo         "bottom": "%modid%:block/%%x",
    	echo         "top": "%modid%:block/%%x",
    	echo         "side": "%modid%:block/%%x"
    	echo     }
    	echo }
    ) > %%x_stairs.json

    echo Making %%x_stairs_outer.json block model
    (
       	echo {
       	echo 	"parent": "block/outer_stairs",
       	echo     "textures": {
       	echo         "bottom": "%modid%:block/%%x",
       	echo         "top": "%modid%:block/%%x",
       	echo         "side": "%modid%:block/%%x"
       	echo     }
       	echo }
    ) > %%x_stairs_outer.json

    echo Making %%x_stairs_inner.json block model
    (
       	echo {
       	echo 	"parent": "block/inner_stairs",
       	echo     "textures": {
       	echo         "bottom": "%modid%:block/%%x",
       	echo         "top": "%modid%:block/%%x",
       	echo         "side": "%modid%:block/%%x"
       	echo     }
       	echo }
    ) > %%x_stairs_inner.json

     echo Making %%x_stairs.json item model
     (
        echo {
        echo 	"parent": "%modid%:block/%%x_stairs"
        echo }
     ) > ../item/%%x_stairs.json
)
