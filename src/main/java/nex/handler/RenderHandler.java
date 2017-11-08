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

package nex.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.entity.item.EntityObsidianBoat;
import nex.init.NetherExMaterials;
import nex.util.ArmorUtil;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class RenderHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        RenderBlockOverlayEvent.OverlayType type = event.getOverlayType();
        EntityPlayer player = event.getPlayer();

        if(type == RenderBlockOverlayEvent.OverlayType.FIRE)
        {
            if(player.isRiding() && player.getRidingEntity() instanceof EntityObsidianBoat || ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.SALAMANDER_HIDE))
            {
                event.setCanceled(true);
            }
        }
    }
}
