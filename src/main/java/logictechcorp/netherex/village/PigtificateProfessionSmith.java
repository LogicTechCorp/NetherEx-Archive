package logictechcorp.netherex.village;

import logictechcorp.libraryex.village.Trade;
import logictechcorp.libraryex.village.TradeStack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class PigtificateProfessionSmith extends PigtificateProfession
{
    private final PigtificateProfession.Career armorsmith = new CareerArmorsmith(this);
    private final PigtificateProfession.Career toolsmith = new CareerToolsmith(this);

    public PigtificateProfessionSmith()
    {
        super(new ResourceLocation(NetherEx.MOD_ID + ":smith"));
        this.registerCareer(this.armorsmith);
        this.registerCareer(this.toolsmith);
    }

    public Career getArmorsmithCareer()
    {
        return this.armorsmith;
    }

    public Career getToolsmithCareer()
    {
        return this.toolsmith;
    }

    static class CareerArmorsmith extends PigtificateProfession.Career
    {
        CareerArmorsmith(PigtificateProfessionSmith profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":armorsmith"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_ARMORSMITH,
                    NetherExTextures.PIGTIFICATE_ARMORSMITH,
                    NetherExTextures.PIGTIFICATE_ARMORSMITH_ALT
            );

            this.addTrade(new Trade(new TradeStack(NetherExItems.ORANGE_SALAMANDER_HIDE, 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 5), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLACK_SALAMANDER_HIDE, 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 5), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), new TradeStack(NetherExItems.WITHER_BONE, 5, 20), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHER_BONE, 1, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.ORANGE_SALAMANDER_HIDE_HELMET, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.ORANGE_SALAMANDER_HIDE_CHESTPLATE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.ORANGE_SALAMANDER_HIDE_LEGGINGS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.ORANGE_SALAMANDER_HIDE_BOOTS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLACK_SALAMANDER_HIDE_HELMET, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLACK_SALAMANDER_HIDE_CHESTPLATE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 3), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLACK_SALAMANDER_HIDE_LEGGINGS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLACK_SALAMANDER_HIDE_BOOTS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHER_BONE_HELMET, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 1, 3, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHER_BONE_CHESTPLATE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 1, 3, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHER_BONE_LEGGINGS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 1, 3, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHER_BONE_BOOTS, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 1, 3, 3));

        }
    }

    static class CareerToolsmith extends PigtificateProfession.Career
    {
        CareerToolsmith(PigtificateProfessionSmith profession)
        {
            super(new ResourceLocation(NetherEx.MOD_ID + ":toolsmith"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_TOOLSMITH,
                    NetherExTextures.PIGTIFICATE_TOOLSMITH,
                    NetherExTextures.PIGTIFICATE_TOOLSMITH_ALT
            );

            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), new TradeStack(Items.GOLD_INGOT, 2, 4), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.STONE_SWORD, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 5), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.STONE_PICKAXE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.STONE_SHOVEL, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 3), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.STONE_HOE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.DIAMOND, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 4), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.IRON_SWORD, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 6), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.IRON_PICKAXE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 5), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.IRON_SHOVEL, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.IRON_HOE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 2, 3), TradeStack.EMPTY, 2, 4, 2));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHERED_AMEDIAN_SWORD, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 7), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHERED_AMEDIAN_PICKAXE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHERED_AMEDIAN_SHOVEL, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 5), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHERED_AMEDIAN_HOE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.WITHERED_AMEDIAN_HAMMER, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 7), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLAZED_AMEDIAN_SWORD, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 7), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLAZED_AMEDIAN_PICKAXE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLAZED_AMEDIAN_SHOVEL, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 5), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLAZED_AMEDIAN_HOE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.BLAZED_AMEDIAN_HAMMER, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 7), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.FROSTED_AMEDIAN_SWORD, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 7), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.FROSTED_AMEDIAN_PICKAXE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 6), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.FROSTED_AMEDIAN_SHOVEL, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 5), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.FROSTED_AMEDIAN_HOE, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 2, 4, 3));
            this.addTrade(new Trade(new TradeStack(NetherExItems.FROSTED_AMEDIAN_HAMMER, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 7), TradeStack.EMPTY, 2, 4, 3));
        }
    }
}
