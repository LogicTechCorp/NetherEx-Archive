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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.potion.PotionNetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffects
{
    public static final PotionNetherEx FREEZE = new PotionNetherEx("freeze", true, 93, 188, 210){
        @Override
        public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
            super.applyAttributesModifiersToEntity(entity, attributeMapIn, amplifier);
            if(entity instanceof EntityLiving)
            {
                ((EntityLiving) entity).setNoAI(true);
            }

            entity.setSilent(true);
        }

        @Override
        public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
            super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);
            if(entity instanceof EntityLiving)
            {
                if(((EntityLiving) entity).isAIDisabled())
                {
                    ((EntityLiving) entity).setNoAI(false);
                }
            }

            entity.setSilent(false);
        }
    };
    public static final PotionNetherEx FROSTBITE = new PotionNetherEx("frostbite", true, 19, 226, 255);
    public static final PotionNetherEx SPORE = new PotionNetherEx("spore", true, 142, 96, 40);
    public static final PotionNetherEx LOST = new PotionNetherEx("lost", true, 103, 62, 124);

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEffects");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
        {
            LOGGER.info("Effect registration started.");

            event.getRegistry().registerAll(
                    FREEZE,
                    FROSTBITE,
                    SPORE,
                    LOST
            );

            LOGGER.info("Effect registration completed.");
        }
    }
}
