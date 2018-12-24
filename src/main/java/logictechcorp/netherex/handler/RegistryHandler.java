package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.village.PigtificateProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class RegistryHandler
{
    @SubscribeEvent
    public static void onNewRegistry(RegistryEvent.NewRegistry event)
    {
        new RegistryBuilder<PigtificateProfession>()
                .setName(new ResourceLocation(NetherEx.MOD_ID + ":pigtificate_professions"))
                .setType(PigtificateProfession.class)
                .setDefaultKey(new ResourceLocation(NetherEx.MOD_ID + ":missing_no"))
                .create();
    }
}
