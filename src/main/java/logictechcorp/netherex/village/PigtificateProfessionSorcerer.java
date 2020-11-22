/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.village;

import logictechcorp.libraryex.trade.Trade;
import logictechcorp.libraryex.trade.TradeStack;
import logictechcorp.libraryex.utility.ItemHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.init.Items;

public class PigtificateProfessionSorcerer extends PigtificateProfession
{
    public PigtificateProfessionSorcerer()
    {
        super(NetherEx.getResource("sorcerer"));
    }

    public void registerDefaultCareers()
    {
        this.registerCareer(new CareerEnchanter(this));
        this.registerCareer(new CareerBrewer(this));
    }

    static class CareerEnchanter extends PigtificateProfession.Career
    {
        CareerEnchanter(PigtificateProfessionSorcerer profession)
        {
            super(NetherEx.getResource("enchanter"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_ENCHANTER,
                    NetherExTextures.PIGTIFICATE_ENCHANTER,
                    NetherExTextures.PIGTIFICATE_ENCHANTER_ALT
            );

            this.addTrade(new Trade(new TradeStack(Items.BOOK, 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 10), TradeStack.EMPTY, 2, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.GLOWSTONE_DUST, 3, 6), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 4, 8, 1));
            this.addTrade(new Trade(new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), new TradeStack(Items.BLAZE_ROD, 4, 8), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(6), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 16), TradeStack.EMPTY, 1, 2, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(8), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 8, 32), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(8), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 8, 32), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(Items.EXPERIENCE_BOTTLE, 1, 4), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 6, 16), TradeStack.EMPTY, 1, 2, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(10), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(10), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomlyEnchantedBook(10), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 12, 48), TradeStack.EMPTY, 1, 6, 3));
        }
    }

    static class CareerBrewer extends PigtificateProfession.Career
    {
        CareerBrewer(PigtificateProfessionSorcerer profession)
        {
            super(NetherEx.getResource("brewer"),
                    profession,
                    NetherExLootTables.PIGTIFICATE_BREWER,
                    NetherExTextures.PIGTIFICATE_BREWER,
                    NetherExTextures.PIGTIFICATE_BREWER_ALT
            );

            this.addTrade(new Trade(new TradeStack(NetherExItems.SPORE, 2, 5), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 1, 8, 1));
            this.addTrade(new Trade(new TradeStack(Items.MAGMA_CREAM, 2, 5), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 2), TradeStack.EMPTY, 2, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.GLASS_BOTTLE, 3, 6), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 1, 1), TradeStack.EMPTY, 4, 8, 1));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 1), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 3, 4), TradeStack.EMPTY, 1, 4, 1));
            this.addTrade(new Trade(new TradeStack(Items.BLAZE_POWDER, 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 6, 12), TradeStack.EMPTY, 1, 4, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 2), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 4, 5), TradeStack.EMPTY, 1, 2, 2));
            this.addTrade(new Trade(new TradeStack(ItemHelper.getRandomPotion(), 1, 3), new TradeStack(NetherExItems.AMETHYST_CRYSTAL, 5, 6), TradeStack.EMPTY, 1, 2, 3));
        }
    }
}
