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

package nex.proxy;

import lex.proxy.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import nex.client.render.entity.*;
import nex.client.render.tileentity.RenderSummoningAltar;
import nex.entity.boss.EntityGhastQueen;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.*;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.neutral.EntityMogus;
import nex.entity.neutral.EntitySalamander;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.entity.projectile.EntityGhastQueenFireball;
import nex.entity.projectile.EntityGhastlingFireball;
import nex.init.NetherExParticleTypes;
import nex.tileentity.TileEntityUrnOfSorrow;

public class ClientProxy implements IProxy
{
    private final Minecraft minecraft = Minecraft.getMinecraft();

    @Override
    public void preInit()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUrnOfSorrow.class, new RenderSummoningAltar());

        RenderingRegistry.registerEntityRenderingHandler(EntityGhastQueenFireball.class, RenderGhastQueenFireball::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastlingFireball.class, RenderGhastlingFireball::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityObsidianBoat.class, RenderObsidianBoat::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigtificateLeader.class, RenderPigtificateLeader::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigtificate.class, RenderPigtificate::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGoldGolem.class, RenderGoldGolem::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMogus.class, RenderMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySalamander.class, RenderSalamander::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, RenderWight::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEmber.class, RenderEmber::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNethermite.class, RenderNethermite::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpinout.class, RenderSpinout::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySporeCreeper.class, RenderSporeCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpore.class, RenderSpore::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastling.class, RenderGhastling::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoneSpider.class, RenderBoneSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBrute.class, RenderBrute::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhastQueen.class, RenderGhastQueen::new);
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
    public void spawnParticle(World world, int particleId, double posX, double posY, double posZ, double speedX, double speedY, double speedZ)
    {
        this.minecraft.effectRenderer.addEffect(NetherExParticleTypes.getFactory(particleId).createParticle(0, world, posX, posY, posZ, speedX, speedY, speedZ));
    }
}
