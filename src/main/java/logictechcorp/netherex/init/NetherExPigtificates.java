package logictechcorp.netherex.init;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.village.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExPigtificates
{
    public static final PigtificateProfessionLeader LEADER = null;
    public static final PigtificateProfessionForager FORAGER = null;
    public static final PigtificateProfessionSmith SMITH = null;
    public static final PigtificateProfessionSorcerer SORCERER = null;

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<PigtificateProfession> event)
        {
            event.getRegistry().registerAll(
                    new PigtificateProfessionLeader(),
                    new PigtificateProfessionForager(),
                    new PigtificateProfessionSmith(),
                    new PigtificateProfessionSorcerer()
            );
        }
    }
}
