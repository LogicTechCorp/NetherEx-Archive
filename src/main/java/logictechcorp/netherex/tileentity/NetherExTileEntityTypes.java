package logictechcorp.netherex.tileentity;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class NetherExTileEntityTypes
{
    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, NetherEx.MOD_ID);

    // @formatter:off
    public static final RegistryObject<TileEntityType<QuartzOreTileEntity>> QUARTZ_ORE_TILE_ENTITY_TYPE = registerTileEntity("quartz_ore_tile_entity", () -> TileEntityType.Builder.create(QuartzOreTileEntity::new, NetherExBlocks.QUARTZ_ORE.get()).build(null));
    // @formatter:on

    public static void register(IEventBus modEventBus)
    {
        TILE_ENTITY_TYPES.register(modEventBus);
    }

    private static <TE extends TileEntity> RegistryObject<TileEntityType<TE>> registerTileEntity(String name, Supplier<TileEntityType<TE>> supplier)
    {
        return TILE_ENTITY_TYPES.register(name, supplier);
    }
}
