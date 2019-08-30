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
	echo Making %%x_slab.json blockstate
	(
		echo {
		echo      "variants": {
		echo          "type=bottom": { "model": "%modid%:block/%%x_slab" },
		echo          "type=top": { "model": "%modid%:block/%%x_slab_top" },
		echo          "type=double": { "model": "%modid%:block/%%x" }
		echo      }
		echo }
	) > ../../blockstates/%%x_slab.json

	echo Making %%x_slab.json block model
    (
    	echo {
    	echo 	"parent": "block/slab",
    	echo     "textures": {
    	echo         "bottom": "%modid%:block/%%x",
    	echo         "top": "%modid%:block/%%x",
    	echo         "side": "%modid%:block/%%x"
    	echo     }
    	echo }
    ) > %%x_slab.json

    echo Making %%x_slab_top.json block model
    (
       	echo {
       	echo 	"parent": "block/slab_top",
       	echo     "textures": {
       	echo         "bottom": "%modid%:block/%%x",
       	echo         "top": "%modid%:block/%%x",
       	echo         "side": "%modid%:block/%%x"
       	echo     }
       	echo }
    ) > %%x_slab_top.json

     echo Making %%x_slab.json item model
     (
        	echo {
        	echo 	"parent": "%modid%:block/%%x_slab"
        	echo }
     ) > ../item/%%x_slab.json
)
