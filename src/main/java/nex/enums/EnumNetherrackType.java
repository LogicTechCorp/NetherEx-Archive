package nex.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumNetherrackType implements IStringSerializable
{
    BIOTIC,
    BURNT,
    FROZEN;

    @Override
    public String getName()
    {
        return toString().toLowerCase();
    }
}
