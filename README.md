# NetherEx
Downloads: http://minecraft.curseforge.com/projects/netherex/files

##About
NetherEx is a mod that adds new biomes to the Nether. It is also an API that allows other mods to add their own Nether biomes.

##Installation
Add the following to your build.gradle

    repositories {
        ivy {
            name "NetherEx"
            artifactPattern "http://addons-origin.cursecdn.com/files/2319/21/[module]_[revision].[ext]"
        }
    }

    dependencies {
        compile group: 'NetherEx', name: 'NetherEx', version: '<VERSION>-deobf', ext: 'jar'
    }

 `<VERSION>` can be found on Curseforge: http://minecraft.curseforge.com/projects/netherex