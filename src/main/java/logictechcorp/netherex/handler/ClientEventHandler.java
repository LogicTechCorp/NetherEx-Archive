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

package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public static void onFOVChange(FOVUpdateEvent event)
    {
        PlayerEntity player = event.getEntity();

        if(player.isPotionActive(NetherExEffects.FROZEN))
        {
            float fov = 1.0F;

            if(player.abilities.isFlying)
            {
                fov *= 1.1F;
            }

            if(player.isHandActive() && player.getActiveItemStack().getItem() instanceof BowItem)
            {
                float useCount = (float) player.getItemInUseMaxCount() / 20.0F;

                if(useCount > 1.0F)
                {
                    useCount = 1.0F;
                }
                else
                {
                    useCount = useCount * useCount;
                }

                fov *= 1.0F - useCount * 0.15F;
            }

            event.setNewfov(fov);
        }
    }
}
