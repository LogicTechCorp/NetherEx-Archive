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

package nex.world.biome;

import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BiomeEntity
{
    private final Biome.SpawnListEntry spawnListEntry;
    private final EnumCreatureType creatureType;

    private BiomeEntity(Biome.SpawnListEntry spawnListEntryIn, EnumCreatureType creatureTypeIn)
    {
        spawnListEntry = spawnListEntryIn;
        creatureType = creatureTypeIn;
    }

    public static BiomeEntity deserialize(JsonObject config)
    {
        EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(JsonUtils.getString(config, "entityConfig", "")));

        if(entityEntry != null)
        {
            Class<? extends Entity> entityCls = entityEntry.getEntityClass();

            if(entityCls != null && EntityLiving.class.isAssignableFrom(entityCls))
            {
                String creatureTypeIdentifier = JsonUtils.getString(config, "creatureType", "");

                EnumCreatureType creatureType = null;

                for(Enum value : EnumCreatureType.class.getEnumConstants())
                {
                    if(value.name().equalsIgnoreCase(creatureTypeIdentifier))
                    {
                        creatureType = (EnumCreatureType) value;
                    }
                }
                if(creatureType == null)
                {
                    creatureType = EnumCreatureType.CREATURE;
                }

                int weight = JsonUtils.getInt(config, "weight", 10);
                int minGroupCount = JsonUtils.getInt(config, "minGroupCount", 1);
                int maxGroupCount = JsonUtils.getInt(config, "maxGroupCount", 4);

                return new BiomeEntity(new Biome.SpawnListEntry((Class<? extends EntityLiving>) entityCls, weight, minGroupCount, maxGroupCount), creatureType);
            }
        }

        return null;
    }

    public Biome.SpawnListEntry getSpawnListEntry()
    {
        return spawnListEntry;
    }

    public EnumCreatureType getCreatureType()
    {
        return creatureType;
    }
}
