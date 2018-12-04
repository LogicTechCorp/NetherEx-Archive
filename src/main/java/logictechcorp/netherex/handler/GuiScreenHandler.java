package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class GuiScreenHandler
{
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();
    private static GuiButton resetNetherButton;

    @SubscribeEvent
    public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();
        List<GuiButton> buttons = event.getButtonList();

        if(guiScreen instanceof GuiWorldSelection)
        {
            resetNetherButton = new GuiButton(getButtonId(buttons), guiScreen.width / 2 - 154, 6, 90, 20, I18n.format("gui." + NetherEx.MOD_ID + ":selectWorld.resetNether"));
            resetNetherButton.enabled = false;
            buttons.add(resetNetherButton);
        }
    }

    @SubscribeEvent
    public static void onMousePressed(GuiScreenEvent.MouseInputEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();

        if(guiScreen instanceof GuiWorldSelection)
        {
            GuiListWorldSelection guiWorldList = ReflectionHelper.getPrivateValue(GuiWorldSelection.class, (GuiWorldSelection) guiScreen, "field_184866_u", "selectionList");

            if(guiWorldList.getSelectedWorld() != null)
            {
                resetNetherButton.enabled = true;
            }
        }
    }

    @SubscribeEvent
    public static void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event)
    {
        GuiScreen guiScreen = event.getGui();
        GuiButton guiButton = event.getButton();

        if(guiScreen instanceof GuiWorldSelection)
        {
            if(guiButton == resetNetherButton)
            {
                GuiListWorldSelection guiWorldList = ReflectionHelper.getPrivateValue(GuiWorldSelection.class, (GuiWorldSelection) guiScreen, "field_184866_u", "selectionList");
                GuiListWorldSelectionEntry guiWorld = guiWorldList.getSelectedWorld();

                if(guiWorld != null)
                {
                    WorldSummary worldSummary = ReflectionHelper.getPrivateValue(GuiListWorldSelectionEntry.class, guiWorld, "field_186786_g", "worldSummary");
                    String worldDisplayName = worldSummary.getDisplayName();

                    MINECRAFT.displayGuiScreen(new GuiYesNo((result, id) ->
                    {
                        if(result)
                        {
                            MINECRAFT.displayGuiScreen(new GuiScreenWorking());
                            ISaveFormat saveFormat = MINECRAFT.getSaveLoader();
                            saveFormat.flushCache();
                            saveFormat.deleteWorldDirectory(worldSummary.getFileName() + "/DIM-1");
                            guiWorldList.refreshList();
                        }

                        MINECRAFT.displayGuiScreen(guiWorldList.getGuiWorldSelection());
                    }, I18n.format("gui." + NetherEx.MOD_ID + ":selectWorld.resetNetherQuestion", worldDisplayName), I18n.format("gui." + NetherEx.MOD_ID + ":selectWorld.resetNetherWarning", worldDisplayName, worldDisplayName), I18n.format("gui." + NetherEx.MOD_ID + ":selectWorld.reset"), I18n.format("gui.cancel"), 0));
                }
            }
        }
    }

    private static int getButtonId(List<GuiButton> buttons)
    {
        int buttonId = 0;

        buttons.sort(Comparator.comparingInt(button -> button.id));

        for(GuiButton button : buttons)
        {
            if(buttonId == button.id)
            {
                buttonId++;
            }
        }

        return buttonId;
    }
}
