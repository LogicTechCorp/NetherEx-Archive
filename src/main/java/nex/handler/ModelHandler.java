package nex.handler;

import lex.client.model.item.IModelContainer;
import lex.client.render.block.model.BakedModelDynamic;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import nex.NetherEx;
import nex.init.NetherExBlocks;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class ModelHandler
{
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        NetherEx.instance.getModelContainers().forEach(IModelContainer::registerModel);
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event)
    {
        IBakedModel model = event.getModelRegistry().getObject(NetherExBlocks.QUARTZ_ORE.getModelLocation());

        if(model != null)
        {
            event.getModelRegistry().putObject(NetherExBlocks.QUARTZ_ORE.getModelLocation(), new BakedModelDynamic(model, NetherExBlocks.QUARTZ_ORE.getTexturePlacement()));
        }
    }
}
