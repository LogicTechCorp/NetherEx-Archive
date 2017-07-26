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

package nex.world.gen.feature;

import com.google.gson.JsonObject;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Random;

public abstract class BiomeFeature extends IForgeRegistryEntry.Impl<BiomeFeature>
{
    private final float genAttempts;
    private final int minHeight;
    private final int maxHeight;

    protected BiomeFeature(float genAttemptsIn, int minHeightIn, int maxHeightIn)
    {
        genAttempts = genAttemptsIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
    }

    public abstract BiomeFeature deserialize(JsonObject config);

    public abstract boolean generate(World world, BlockPos pos, Random rand);

    public float getGenAttempts()
    {
        return genAttempts;
    }

    public int getGenAttempts(Random rand)
    {
        int attempts = (int) MathHelper.abs(getGenAttempts());
        float chance = MathHelper.abs(getGenAttempts()) - attempts;

        if(chance > 0.0F && rand.nextFloat() > chance)
        {
            attempts = 0;
        }
        if(getGenAttempts() < 0.0F && attempts > 0)
        {
            attempts = rand.nextInt(MathHelper.abs(attempts)) + 1;
        }

        return attempts;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }
}
