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

package logictechcorp.netherex.tileentity;

import logictechcorp.libraryex.multiblock.IPatternComponent;
import logictechcorp.libraryex.multiblock.Pattern;
import logictechcorp.libraryex.tileentity.TileEntityInventory;
import logictechcorp.netherex.block.BlockNetherReactorCore;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntityNetherReactorCore extends TileEntityInventory implements ITickable
{
    private Pattern pattern;
    private int summoningTime = 0;

    public TileEntityNetherReactorCore()
    {
        super(1);

        IPatternComponent goldElement = Pattern.createElement(Blocks.GOLD_BLOCK.getDefaultState(), 'G', "blockGold", "storageGold");
        IPatternComponent obsidianElement = Pattern.createElement(Blocks.OBSIDIAN.getDefaultState(), 'O', "obsidian");
        IPatternComponent netherBrickElement = Pattern.createElement(Blocks.NETHER_BRICK.getDefaultState(), 'N', "blockBrickNether", "blockNetherBrick");
        IPatternComponent quartzElement = Pattern.createElement(Blocks.QUARTZ_BLOCK.getDefaultState(), 'Q', "blockQuartz", "storageQuartz");

        IPatternComponent layer1 = Pattern.createLayer(
                Pattern.createRow("GOG"),
                Pattern.createRow("OOO"),
                Pattern.createRow("GOG")
        );

        IPatternComponent layer2 = Pattern.createLayer(
                Pattern.createRow("Q*Q"),
                Pattern.createRow("***"),
                Pattern.createRow("Q*Q")
        );

        IPatternComponent layer3 = Pattern.createLayer(
                Pattern.createRow(" N "),
                Pattern.createRow("NNN"),
                Pattern.createRow(" N ")
        );

        this.pattern = Pattern.createPattern(
                layer1,
                layer2,
                layer3,
                goldElement,
                obsidianElement,
                netherBrickElement,
                quartzElement
        );
    }

    @Override
    public void update()
    {
        if(this.hasFormed() && !this.inventory.getStackInSlot(0).isEmpty() && this.world.getRedstonePowerFromNeighbors(this.pos) > 0)
        {
            this.summoningTime++;

            if(this.summoningTime / 20.0F >= 6.8F)
            {

            }

            this.world.setBlockState(this.pos, NetherExBlocks.NETHER_REACTOR_CORE.getDefaultState().withProperty(BlockNetherReactorCore.ACTIVATED, true));
        }
        else if(this.summoningTime / 20.0F < 6.8F)
        {
            this.summoningTime = 0;
            this.world.setBlockState(this.pos, NetherExBlocks.NETHER_REACTOR_CORE.getDefaultState().withProperty(BlockNetherReactorCore.ACTIVATED, false));
        }
    }

    @Override
    public boolean acceptsItemStack(ItemStack stack)
    {
        return stack.getItem() == Items.NETHER_STAR;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SummoningTime", this.summoningTime);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.summoningTime = compound.getInteger("SummoningTime");
    }

    public boolean hasFormed()
    {
        return this.pattern.hasFormed(this.world, this.pos.add(-1, -1, -1));
    }
}
