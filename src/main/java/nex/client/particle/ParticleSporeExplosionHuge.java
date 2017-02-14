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

package nex.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

@SideOnly(Side.CLIENT)
public class ParticleSporeExplosionHuge extends Particle
{
    private int timeSinceStart;

    public ParticleSporeExplosionHuge(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
    }

    @Override
    public void onUpdate()
    {
        int maximumTime = 8;

        for(int i = 0; i < 6; i++)
        {
            double d0 = posX + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
            double d1 = posY + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
            double d2 = posZ + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
            NetherEx.proxy.spawnParticle(world, d0, d1, d2, (double) ((float) timeSinceStart / (float) maximumTime), 0.0D, 0.0D, new ParticleSporeExplosionLarge.Factory());
        }

        timeSinceStart++;

        if(timeSinceStart == maximumTime)
        {
            setExpired();
        }
    }

    @Override
    public int getFXLayer()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleSporeExplosionHuge(world, xCoordIn, yCoordIn, zCoordIn);
        }
    }
}
