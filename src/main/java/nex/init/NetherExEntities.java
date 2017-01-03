/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.init;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import nex.NetherEx;
import nex.entity.monster.EntityWight;
import nex.entity.neutral.EntityMogus;
import nex.entity.neutral.EntitySalamander;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExEntities
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEntities");

    public static void init()
    {
        LOGGER.info("Entity registration started.");

        registerEntity("mogus", EntityMogus.class, 0, 16711680, 16762880);
        registerEntity("salamander", EntitySalamander.class, 1, 16711680, 16762880);
        registerEntity("wight", EntityWight.class, 2, 16711680, 16762880);

        addEntitySpawn(EntityPigZombie.class, 100, 4, 4, EnumCreatureType.MONSTER, NetherExBiomes.HELL);
        addEntitySpawn(EntityGhast.class, 50, 4, 4, EnumCreatureType.MONSTER, NetherExBiomes.HELL);
        addEntitySpawn(EntityBlaze.class, 25, 4, 4, EnumCreatureType.MONSTER, NetherExBiomes.HELL);
        addEntitySpawn(EntityMagmaCube.class, 2, 4, 4, EnumCreatureType.MONSTER, NetherExBiomes.HELL);
        addEntitySpawn(EntityEnderman.class, 1, 4, 4, EnumCreatureType.MONSTER, NetherExBiomes.HELL);
        addEntitySpawn(EntityWitherSkeleton.class, 100, 1, 4, EnumCreatureType.MONSTER, NetherExBiomes.RUTHLESS_SANDS);
        addEntitySpawn(EntityMogus.class, 100, 4, 8, EnumCreatureType.CREATURE, NetherExBiomes.FUNGI_FOREST);
        addEntitySpawn(EntitySalamander.class, 100, 1, 4, EnumCreatureType.CREATURE, NetherExBiomes.TORRID_WASTELAND);
        addEntitySpawn(EntityWight.class, 100, 1, 4, EnumCreatureType.CREATURE, NetherExBiomes.ARCTIC_ABYSS);

        LOGGER.info("Entity registration Ended.");
    }

    private static void registerEntity(String name, Class<? extends EntityLiving> cls, int id)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID + ":" + name);
        EntityRegistry.registerModEntity(registryName, cls, registryName.toString(), id, NetherEx.instance, 64, 1, true);
    }

    private static void registerEntity(String name, Class<? extends EntityLiving> cls, int id, int primaryEggColor, int secondaryEggColor)
    {
        ResourceLocation registryName = new ResourceLocation(NetherEx.MOD_ID + ":" + name);
        EntityRegistry.registerModEntity(registryName, cls, registryName.toString(), id, NetherEx.instance, 64, 1, true, primaryEggColor, secondaryEggColor);
    }

    private static void addEntitySpawn(Class<? extends EntityLiving> cls, int weight, int minSpawnCount, int maxSpawnCount, EnumCreatureType creatureType, Biome... biomes)
    {
        EntityRegistry.addSpawn(cls, weight, minSpawnCount, maxSpawnCount, creatureType, biomes);
    }
}
