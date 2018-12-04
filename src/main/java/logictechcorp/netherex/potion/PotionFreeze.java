/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package logictechcorp.netherex.potion;

import logictechcorp.libraryex.potion.PotionLibEx;
import logictechcorp.libraryex.util.CollectionHelper;
import logictechcorp.libraryex.util.EntityHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExEffects;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class PotionFreeze extends PotionLibEx
{
    public PotionFreeze()
    {
        super(NetherEx.instance, "freeze", true, 93, 188, 210);
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
        World world = entity.getEntityWorld();

        if(this.canFreeze(entity))
        {
            boolean frozen = entity.isPotionActive(NetherExEffects.FREEZE);

            if(!frozen && world.rand.nextInt(ConfigHandler.biomeConfig.arcticAbyss.mobFreezeRarity) == 0 && world.getBiome(entity.getPosition()) == NetherExBiomes.ARCTIC_ABYSS)
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
            if(frozen)
            {
                if(entity instanceof EntityPlayer)
                {
                    entity.setPosition(entity.prevPosX, entity.posY, entity.prevPosZ);
                }
                if(world.rand.nextInt(ConfigHandler.potionEffectConfig.freeze.thawRarity) == 0)
                {
                    entity.removePotionEffect(NetherExEffects.FREEZE);
                }
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

    public boolean canFreeze(EntityLivingBase entity)
    {
        if(entity instanceof EntityPlayer && ConfigHandler.biomeConfig.arcticAbyss.canPlayersFreeze && !((EntityPlayer) entity).isCreative())
        {
            return true;
        }

        String entityRegistryName = EntityHelper.getEntityLocation(entity);
        return entityRegistryName != null && !CollectionHelper.contains(ConfigHandler.potionEffectConfig.freeze.mobBlacklist, entityRegistryName);
    }
}
