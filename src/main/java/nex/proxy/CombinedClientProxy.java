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

package nex.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.render.entity.*;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.*;
import nex.entity.neutral.EntityMogus;
import nex.entity.neutral.EntitySalamander;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityObsidianBoat.class, RenderObsidianBoat::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMogus.class, RenderMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySalamander.class, RenderSalamander::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, RenderWight::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEmber.class, RenderEmber::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNethermite.class, RenderNethermite::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpinout.class, RenderSpinout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySporeCreeper.class, RenderSporeCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpore.class, RenderSpore::new);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }

    @Override
    public void spawnParticle(World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, IParticleFactory factory)
    {
        Minecraft.getMinecraft().effectRenderer.addEffect(factory.createParticle(0, world, posX, posY, posZ, speedX, speedY, speedZ));
    }
}
