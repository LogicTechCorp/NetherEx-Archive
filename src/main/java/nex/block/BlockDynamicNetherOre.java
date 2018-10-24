package nex.block;

import lex.block.BlockDynamic;
import lex.client.model.item.ItemModelHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

import java.util.Random;

public class BlockDynamicNetherOre extends BlockDynamicNetherBiome
{
    private ResourceLocation dropRegistryName;
    private Item drop;

    public BlockDynamicNetherOre(String name, int harvestLevel, float hardness, float resistance, float lightLevel, TexturePlacement texturePlacement)
    {
        super(NetherEx.instance, name, Material.ROCK, texturePlacement);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setLightLevel(lightLevel);
    }

    public BlockDynamicNetherOre(String name, int harvestLevel, ResourceLocation dropRegistryName, float hardness, float resistance, float lightLevel, TexturePlacement texturePlacement)
    {
        super(NetherEx.instance, name, Material.ROCK, texturePlacement);
        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setLightLevel(lightLevel);
        this.dropRegistryName = dropRegistryName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(this.drop == null)
        {
            if(this.dropRegistryName != null)
            {
                Item dropItem = ForgeRegistries.ITEMS.getValue(this.dropRegistryName);
                Block dropBlock = ForgeRegistries.BLOCKS.getValue(this.dropRegistryName);

                if(dropItem != null)
                {
                    this.drop = dropItem;
                }
                else if(dropBlock != null)
                {
                    this.drop = Item.getItemFromBlock(dropBlock);
                }
            }

            if(this.drop == null)
            {
                this.drop = Item.getItemFromBlock(this);
            }
        }

        return this.drop;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand)
    {
        if(fortune > 0)
        {
            int i = rand.nextInt(fortune + 2) - 1;

            if(i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(rand) * (i + 1);
        }
        else
        {
            return this.quantityDropped(rand);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(rand, 2, 5);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        BlockDynamic dynamic = this;
        ModelLoader.setCustomStateMapper(this, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return dynamic.getModelLocation();
            }
        });

        ItemModelHandler.registerBlockModel(this, 0, this.getRegistryName().toString(), "inventory");
    }
}
