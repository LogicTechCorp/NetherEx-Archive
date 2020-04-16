/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.mobeffect;

import logictechcorp.libraryex.potion.MobEffectMod;
import logictechcorp.libraryex.utility.CollectionHelper;
import logictechcorp.libraryex.utility.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExBiomes;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.Random;

public class MobEffectFrozen extends MobEffectMod
{
    public MobEffectFrozen()
    {
        super(NetherEx.instance, "frozen", true, 93, 188, 210);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMap, int amplifier)
    {
        super.applyAttributesModifiersToEntity(entity, attributeMap, amplifier);

        if(entity instanceof EntityLiving)
        {
            ((EntityLiving) entity).setNoAI(true);
        }

        entity.setSilent(true);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
        if(canFreeze(entity))
        {
            Random random = entity.getRNG();

            if(entity instanceof EntityPlayer)
            {
                entity.setPosition(entity.prevPosX, entity.posY, entity.prevPosZ);
            }
            if(random.nextInt(ConfigHandler.mobEffectConfig.freeze.thawRarity) == 0)
            {
                entity.removePotionEffect(this);
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMap, int amplifier)
    {
        super.removeAttributesModifiersFromEntity(entity, attributeMap, amplifier);

        if(entity instanceof EntityLiving)
        {
            if(((EntityLiving) entity).isAIDisabled())
            {
                ((EntityLiving) entity).setNoAI(false);
            }
        }

        entity.setSilent(false);
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public static boolean canFreeze(EntityLivingBase entity)
    {
        World world = entity.getEntityWorld();
        Biome biome = world.getBiome(entity.getPosition());

        if(entity instanceof EntityPlayer && !((EntityPlayer) entity).isCreative())
        {
            return biome != NetherExBiomes.ARCTIC_ABYSS || ConfigHandler.biomeConfig.arcticAbyss.canPlayersFreeze;
        }

        String entityRegistryName = EntityHelper.getEntityLocation(entity);
        return entityRegistryName != null && !CollectionHelper.contains(ConfigHandler.mobEffectConfig.freeze.mobBlacklist, entityRegistryName);
    }
}
