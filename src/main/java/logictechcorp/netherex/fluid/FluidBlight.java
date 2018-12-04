package logictechcorp.netherex.fluid;

import logictechcorp.libraryex.fluid.FluidLibEx;
import logictechcorp.netherex.NetherEx;
import net.minecraftforge.fluids.FluidStack;

public class FluidBlight extends FluidLibEx
{
    public FluidBlight()
    {
        super(NetherEx.instance, "blight");
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack)
    {
        return false;
    }
}
