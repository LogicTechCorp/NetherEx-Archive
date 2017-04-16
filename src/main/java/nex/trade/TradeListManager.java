/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.trade;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import nex.NetherEx;
import nex.util.NBTUtil;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class TradeListManager
{
    public static HashMap<EnumCareer, HashMap<Integer, List<TradeRecipe>>> offerLists = Maps.newHashMap();
    private static Random rand = new Random();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|TradeListManager");

    public static void init(File file)
    {
        for(EnumCareer career : EnumCareer.values())
        {
            offerLists.put(career, Maps.newHashMap());
        }

        try
        {
            LOGGER.info("Attempting to copy the Pigtificate Trade List to the config folder.");

            if(file.createNewFile())
            {
                try
                {
                    InputStream inStream = NetherEx.class.getResourceAsStream("/assets/nex/pigtificate_trade_list.json");
                    FileWriter writer = new FileWriter(file);
                    IOUtils.copy(inStream, writer);
                    writer.close();
                    inStream.close();
                }
                catch(Exception e)
                {
                    LOGGER.fatal("The attempt to copy the Pigtificate Trade List was unsuccessful.");
                    LOGGER.fatal(e);
                }

                LOGGER.info("The Pigtificate Trade List was successfully copied to config folder.");
            }
            else
            {
                LOGGER.info("The Pigtificate Trade List is already located in the config folder.");
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("The attempt to copy the Pigtificate Trade List to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }

        try
        {
            Gson gson = new Gson();
            String jsonText = Files.toString(file, Charsets.UTF_8);
            TradeList list = gson.fromJson(jsonText, TradeList.class);

            for(TradeProfession profession : list.getProfessions())
            {
                for(TradeCareer career : profession.getCareers())
                {
                    for(TradeOffer offer : career.getOffers())
                    {
                        ItemStack outputStack;
                        TradeOffer.Output output = offer.getOutput();
                        String outputId = output.getId();
                        int outputMeta = output.getMeta();
                        TradeOffer.Amount outAmount = output.getAmount();
                        List<TradeOffer.Enchantment> enchantments = output.getEnch();
                        TradeOffer.Display display = output.getDisplay();

                        if(Block.getBlockFromName(outputId) != null)
                        {
                            outputStack = new ItemStack(Block.getBlockFromName(outputId), rand.nextInt((outAmount.getMax() - outAmount.getMin()) + 1) + outAmount.getMin(), outputMeta);
                        }
                        else if(Item.getByNameOrId(outputId) != null)
                        {
                            outputStack = new ItemStack(Item.getByNameOrId(outputId), rand.nextInt((outAmount.getMax() - outAmount.getMin()) + 1) + outAmount.getMin(), outputMeta);
                        }
                        else
                        {
                            continue;
                        }

                        if(enchantments.size() > 0)
                        {
                            for(TradeOffer.Enchantment enchantment : enchantments)
                            {
                                outputStack.addEnchantment(Enchantment.getEnchantmentByLocation(enchantment.getId()), enchantment.getLevel());
                            }
                        }

                        if(!display.getName().equals(""))
                        {
                            outputStack.setStackDisplayName(display.getName());
                        }
                        if(display.getLore().size() > 0)
                        {
                            NBTUtil.setTag(outputStack);
                            NBTTagList loreList = new NBTTagList();

                            for(String lore : display.getLore())
                            {
                                loreList.appendTag(new NBTTagString(lore));
                            }

                            NBTTagCompound displayCompound = new NBTTagCompound();
                            displayCompound.setTag("Lore", loreList);
                            NBTTagCompound compound = new NBTTagCompound();
                            compound.setTag("display", displayCompound);
                            NBTUtil.setTag(outputStack, compound);
                        }

                        ItemStack inputStackA;
                        TradeOffer.Input inputA = offer.getInputA();
                        String inputAId = inputA.getId();
                        int inputAMeta = inputA.getMeta();
                        TradeOffer.Amount inputAAmount = inputA.getAmount();

                        if(Block.getBlockFromName(inputAId) != null)
                        {
                            inputStackA = new ItemStack(Block.getBlockFromName(inputAId), rand.nextInt((inputAAmount.getMax() - inputAAmount.getMin()) + 1) + inputAAmount.getMin(), inputAMeta);
                        }
                        else if(Item.getByNameOrId(inputAId) != null)
                        {
                            inputStackA = new ItemStack(Item.getByNameOrId(inputAId), rand.nextInt((inputAAmount.getMax() - inputAAmount.getMin()) + 1) + inputAAmount.getMin(), inputAMeta);
                        }
                        else
                        {
                            continue;
                        }

                        ItemStack inputStackB;
                        TradeOffer.Input inputB = offer.getInputB();
                        String inputBId = inputB.getId();
                        int inputBMeta = inputB.getMeta();
                        TradeOffer.Amount inputBAmount = inputB.getAmount();

                        if(Block.getBlockFromName(inputBId) != null)
                        {
                            inputStackB = new ItemStack(Block.getBlockFromName(inputBId), rand.nextInt((inputBAmount.getMax() - inputBAmount.getMin()) + 1) + inputBAmount.getMin(), inputBMeta);
                        }
                        else if(Item.getByNameOrId(inputBId) != null)
                        {
                            inputStackB = new ItemStack(Item.getByNameOrId(inputBId), rand.nextInt((inputBAmount.getMax() - inputBAmount.getMin()) + 1) + inputBAmount.getMin(), inputBMeta);
                        }
                        else
                        {
                            continue;
                        }

                        TradeOffer.Amount offerAmount = offer.getAmount();
                        TradeRecipe trade = new TradeRecipe(outputStack, inputStackA, inputStackB, rand.nextInt((offerAmount.getMax() - offerAmount.getMin()) + 1) + offerAmount.getMin());
                        offerLists.get(EnumCareer.fromCareer(career)).computeIfAbsent(offer.getLevel(), k -> Lists.newArrayList()).add(trade);
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
