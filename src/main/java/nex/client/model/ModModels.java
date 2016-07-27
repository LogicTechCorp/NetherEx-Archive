package nex.client.model;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.block.IVariantContainer;
import nex.registry.ModItems;

@SideOnly(Side.CLIENT)
public class ModModels
{
    public static void register()
    {
        ModItems.variantContainers.forEach(ModModels::registerEnumModels);
    }

    private static void registerEnumModels(IVariantContainer container)
    {
        if(container != null && (container.getVariants().length > 1 && container.getVariantEnum() != null || container.getVariants().length == 1 && container.getVariantEnum() == null))
        {
            Item item = (Item) container;
            Class cls = container.getVariantEnum();

            if(item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof IVariantContainer)
            {
                Block block = ((ItemBlock) item).getBlock();

                if(container.getVariants().length == 1)
                {
                    registerBlockItemModel(block, new ModelResourceLocation(block.getRegistryName(), "normal"));
                }
                else
                {
                    for(Object type : cls.getEnumConstants())
                    {
                        Enum e = (Enum) type;
                        registerBlockItemModelForMeta(block, e.ordinal(), "type=" + e.toString().toLowerCase());
                    }
                }
            }
            else
            {
                if(container.getVariants().length == 1)
                {
                    registerItemModel(item, new ModelResourceLocation(item.getRegistryName(), "normal"));
                }
                else
                {
                    for(Object type : cls.getEnumConstants())
                    {
                        Enum e = (Enum) type;
                        registerItemModelForMeta(item, e.ordinal(), "type=" + e.toString().toLowerCase());
                    }
                }
            }
        }
    }

    private static void registerBlockItemModel(Block block, ModelResourceLocation fullModelLocation)
    {
        registerItemModel(Item.getItemFromBlock(block), fullModelLocation);
    }

    private static void registerBlockItemModelForMeta(Block block, int metadata, String variant)
    {
        registerItemModelForMeta(Item.getItemFromBlock(block), metadata, variant);
    }

    private static void registerItemModel(Item item, ModelResourceLocation fullModelLocation)
    {
        ModelBakery.registerItemVariants(item, fullModelLocation);
        registerItemModel(item, MeshDefinitionFix.create(stack -> fullModelLocation));
    }

    private static void registerItemModel(Item item, ItemMeshDefinition meshDefinition)
    {
        ModelLoader.setCustomMeshDefinition(item, meshDefinition);
    }

    private static void registerItemModelForMeta(Item item, int metadata, String variant)
    {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private static void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation modelResourceLocation)
    {
        ModelLoader.setCustomModelResourceLocation(item, metadata, modelResourceLocation);
    }
}
