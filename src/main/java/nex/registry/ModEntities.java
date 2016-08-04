package nex.registry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import nex.NetherEx;
import nex.entity.hostile.EntityWight;
import nex.entity.neutral.EntityBrownMogus;
import nex.entity.neutral.EntityRedMogus;

public class ModEntities
{
    public static void register()
    {
        registerEntityWithEgg(EnumCreatureType.AMBIENT, EntityRedMogus.class, "mogusRed", 0, 64, 20, 10, 2, 4, 16711680, 16762880);
        registerEntityWithEgg(EnumCreatureType.AMBIENT, EntityBrownMogus.class, "mogusBrown", 1, 64, 20, 10, 2, 4, 16711680, 16762880);
        registerEntityWithEgg(EnumCreatureType.MONSTER, EntityWight.class, "wight", 2, 64, 20, 10, 2, 4, 16711680, 16762880);
    }

    private static void registerEntity(EnumCreatureType type, Class<? extends EntityLiving> cls, String name, int id, int trackingRange, int updateFrequency, int weight, int min, int max, Biome... biomes)
    {
        EntityRegistry.registerModEntity(cls, name, id, NetherEx.instance, trackingRange, updateFrequency, true);

        if(biomes != null)
        {
            EntityRegistry.addSpawn(cls, weight, min, max, type, biomes);
        }
    }

    private static void registerEntityWithEgg(EnumCreatureType type, Class<? extends EntityLiving> cls, String name, int id, int trackingRange, int updateFrequency, int weight, int min, int max, int solidColor, int spotColor, Biome... biomes)
    {
        EntityRegistry.registerModEntity(cls, name, id, NetherEx.instance, trackingRange, updateFrequency, true, solidColor, spotColor);

        if(biomes != null)
        {
            EntityRegistry.addSpawn(cls, weight, min, max, type, biomes);
        }
    }
}
