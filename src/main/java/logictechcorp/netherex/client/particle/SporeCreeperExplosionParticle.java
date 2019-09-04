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

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeCreeperExplosionParticle extends SpriteTexturedParticle
{
    private final IAnimatedSprite sprite;

    private SporeCreeperExplosionParticle(World world, double posX, double posY, double posZ, double scale, IAnimatedSprite sprite)
    {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.maxAge = 6 + this.rand.nextInt(4);
        float randomColor = this.rand.nextFloat() * 0.6F + 0.4F;
        this.particleRed = randomColor;
        this.particleGreen = randomColor;
        this.particleBlue = randomColor;
        this.particleScale = 2.0F * (1.0F - (float) scale * 0.5F);
        this.sprite = sprite;
        this.selectSpriteWithAge(sprite);
    }

    @Override
    public int getBrightnessForRender(float partialTick)
    {
        return 15728880;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(this.age++ >= this.maxAge)
        {
            this.setExpired();
        }
        else
        {
            this.selectSpriteWithAge(this.sprite);
        }
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType>
    {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite)
        {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType type, World world, double posX, double posY, double posZ, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SporeCreeperExplosionParticle(world, posX, posY, posZ, xSpeed, this.sprite);
        }
    }
}
