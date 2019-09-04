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

import logictechcorp.netherex.particle.NetherExParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeCreeperExplosionEmitterParticle extends MetaParticle
{
    private int timeSinceStart;
    private final int maximumTime = 8;

    private SporeCreeperExplosionEmitterParticle(World world, double posX, double posY, double posZ)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void tick()
    {
        for(int i = 0; i < 6; i++)
        {
            double randomPosX = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double randomPosY = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double randomPosZ = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            this.world.addParticle(NetherExParticles.SPORE_CREEPER_EXPLOSION, randomPosX, randomPosY, randomPosZ, ((float) this.timeSinceStart / (float) this.maximumTime), 0.0D, 0.0D);
        }

        this.timeSinceStart++;

        if(this.timeSinceStart == this.maximumTime)
        {
            this.setExpired();
        }

    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        @Override
        public Particle makeParticle(BasicParticleType type, World world, double posX, double posY, double posZ, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SporeCreeperExplosionEmitterParticle(world, posX, posY, posZ);
        }
    }
}
