package nex.handler;

import lex.client.render.block.model.BakedModelDynamic;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.init.NetherExBlocks;

@Mod.EventBusSubscriber
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
