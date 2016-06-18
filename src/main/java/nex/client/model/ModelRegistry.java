package nex.client.model;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.ModContent;
import nex.NetherEx;
import nex.block.BlockModNetherrack;
import nex.block.BlockModOre;

@SideOnly(Side.CLIENT)
public class ModelRegistry
{
    public static final ModelRegistry INSTANCE = new ModelRegistry();

    private ModelRegistry()
    {
    }

    public void initModels()
    {
        for(BlockModNetherrack.EnumType type : BlockModNetherrack.EnumType.values())
        {
            registerBlockItemModelForMeta(ModContent.Blocks.netherrack, type.getMeta(), "variant=" + type.getName());
        }

        for(BlockModOre.EnumType type : BlockModOre.EnumType.values())
        {
            registerBlockItemModelForMeta(ModContent.Blocks.ore, type.getMeta(), "variant=" + type.getName());
        }
    }

    private void registerBlockItemModel(Block block, ModelResourceLocation fullModelLocation)
    {
        registerItemModel(Item.getItemFromBlock(block), fullModelLocation);
    }

    private void registerBlockItemModelForMeta(Block block, int metadata, String variant)
    {
        registerItemModelForMeta(Item.getItemFromBlock(block), metadata, variant);
    }

    private void registerBlockItemModelForMeta(Block block, int metadata, ModelResourceLocation modelResourceLocation)
    {
        registerItemModelForMeta(Item.getItemFromBlock(block), metadata, modelResourceLocation);
    }

    @SideOnly(Side.CLIENT)
    private void registerFluidModel(IFluidBlock block)
    {
        Item item = Item.getItemFromBlock((Block) block);
        ModelBakery.registerItemVariants(item);
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(NetherEx.MOD_ID + ":fluid", block.getFluid().getName());
        ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

        ModelLoader.setCustomStateMapper((Block) block, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return modelResourceLocation;
            }
        });
    }

    private void registerItemModel(Item item, ModelResourceLocation fullModelLocation)
    {
        ModelBakery.registerItemVariants(item, fullModelLocation);
        registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
    }

    private void registerItemModel(Item item, ItemMeshDefinition meshDefinition)
    {
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    private void registerItemModelForMeta(Item item, int metadata, String variant)
    {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation)
    {
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }
}
