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

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.world.World;

public interface IProxy
{
    void preInit();

    void init();

    void postInit();

    void spawnParticle(World world, double posX, double posY, double posZ, double speedX, double speedY, double speedZ, IParticleFactory factory);
}
