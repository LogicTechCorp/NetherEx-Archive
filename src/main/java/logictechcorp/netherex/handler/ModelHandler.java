package logictechcorp.netherex.handler;

import logictechcorp.libraryex.client.render.block.model.BakedModelDynamic;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class ModelHandler
{
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
