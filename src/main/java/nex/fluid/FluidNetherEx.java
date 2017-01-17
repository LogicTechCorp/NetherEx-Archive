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

package nex.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidNetherEx extends Fluid
{
    public FluidNetherEx(String fluidName)
    {
        super(fluidName, new ResourceLocation("nex:blocks/fluid_" + fluidName + "_still"), new ResourceLocation("nex:blocks/fluid_" + fluidName + "_flow"));

        FluidRegistry.registerFluid(this);
        FluidRegistry.addBucketForFluid(this);
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        return false;
    }
}
