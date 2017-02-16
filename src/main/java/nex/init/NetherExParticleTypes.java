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

import net.minecraft.client.particle.IParticleFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.particle.ParticleSporeExplosionHuge;
import nex.client.particle.ParticleSporeExplosionLarge;

public class NetherExParticleTypes
{
    public static final int SPORE_EXPLOSION_LARGE = 0;
    public static final int SPORE_EXPLOSION_HUGE = 1;

    @SideOnly(Side.CLIENT)
    public static IParticleFactory getFromID(int id)
    {
        switch(id)
        {
            default:
            case 0:
                return new ParticleSporeExplosionLarge.Factory();
            case 1:
                return new ParticleSporeExplosionHuge.Factory();
        }
    }
}
