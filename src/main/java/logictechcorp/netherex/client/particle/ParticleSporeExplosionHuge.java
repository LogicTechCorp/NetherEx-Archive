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

package logictechcorp.netherex.client.particle;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExParticleTypes;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleSporeExplosionHuge extends Particle
{
    private int timeSinceStart;

    public ParticleSporeExplosionHuge(World world, double posX, double posY, double posZ)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entity, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
    }

    @Override
    public void onUpdate()
    {
        int maximumTime = 8;

        for(int i = 0; i < 6; i++)
        {
            double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            NetherEx.proxy.spawnParticle(NetherExParticleTypes.SPORE_EXPLOSION_LARGE.getId(), d0, d1, d2, ((float) this.timeSinceStart / (float) maximumTime), 0.0D, 0.0D);
        }

        this.timeSinceStart++;

        if(this.timeSinceStart == maximumTime)
        {
            this.setExpired();
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
        @Override
        public Particle createParticle(int particleId, World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, int... parameters)
        {
            return new ParticleSporeExplosionHuge(world, posX, posY, posZ);
        }
    }
}
