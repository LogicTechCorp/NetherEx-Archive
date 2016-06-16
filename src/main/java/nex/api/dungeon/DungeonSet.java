package nex.api.dungeon;

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.DungeonHooks;

import java.util.List;

public class DungeonSet extends WeightedRandom.Item
{
    private String name;
    private IBlockState wallBlockState;
    private IBlockState floorBlockState;
    private List<DungeonHooks.DungeonMob> dungeonMobs;

    public DungeonSet(String name, int weight, IBlockState wallBlockState, IBlockState floorBlockState, DungeonHooks.DungeonMob... dungeonMobs)
    {
        super(weight);
        this.name = name;
        this.wallBlockState = wallBlockState;
        this.floorBlockState = floorBlockState;
        this.dungeonMobs = Lists.newArrayList(dungeonMobs);
    }

    public String getName()
    {
        return name;
    }

    public IBlockState getWallBlockState()
    {
        return wallBlockState;
    }

    public IBlockState getFloorBlockState()
    {
        return floorBlockState;
    }

    public List<DungeonHooks.DungeonMob> getDungeonMobs()
    {
        return dungeonMobs;
    }
}
