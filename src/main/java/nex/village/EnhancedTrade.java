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

package nex.village;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.BlockUtil;
import nex.util.NBTUtil;
import nex.util.RandomUtil;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class EnhancedTrade extends MerchantRecipe
{
    private final int outputMinStackSize;
    private final int outputMaxStackSize;
    private final int inputAMinStackSize;
    private final int inputAMaxStackSize;
    private final int inputBMinStackSize;
    private final int inputBMaxStackSize;
    private final int minTradesAvailable;
    private final int maxTradesAvailable;
    private final int tradeLevel;

    private EnhancedTrade(ItemStack output, int outputMinStackSizeIn, int outputMaxStackSizeIn, ItemStack inputA, int inputAMinStackSizeIn, int inputAMaxStackSizeIn, ItemStack inputB, int inputBMinStackSizeIn, int inputBMaxStackSizeIn, int minTradesAvailableIn, int maxTradesAvailableIn, int tradeLevelIn)
    {
        super(inputA, inputB, output, 0, 1);

        outputMinStackSize = outputMinStackSizeIn;
        outputMaxStackSize = outputMaxStackSizeIn;
        inputAMinStackSize = inputAMinStackSizeIn;
        inputAMaxStackSize = inputAMaxStackSizeIn;
        inputBMinStackSize = inputBMinStackSizeIn;
        inputBMaxStackSize = inputBMaxStackSizeIn;
        minTradesAvailable = minTradesAvailableIn;
        maxTradesAvailable = maxTradesAvailableIn;
        tradeLevel = tradeLevelIn;
    }

    public static EnhancedTrade deserialize(JsonObject config)
    {
        JsonArray tradeConfigs = JsonUtils.getJsonArray(config, "trades", new JsonArray());
        Random rand = new Random();

        if(tradeConfigs.size() > 0)
        {
            for(JsonElement tradeConfig : tradeConfigs)
            {
                JsonObject outputConfig = JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "output", new JsonObject());
                ItemStack outputStack = deserializeItemStack(outputConfig);
                ItemStack inputAStack = deserializeItemStack(JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "inputA", new JsonObject()));
                ItemStack inputBStack = deserializeItemStack(JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "inputB", new JsonObject()));

                if(outputStack != null && inputAStack != null)
                {
                    if(inputBStack == null)
                    {
                        inputBStack = ItemStack.EMPTY;
                    }

                    JsonObject displayConfig = JsonUtils.getJsonObject(outputConfig, "display", new JsonObject());

                    if(displayConfig.size() > 0)
                    {
                        String name = JsonUtils.getString(displayConfig, "name", "");

                        if(!name.isEmpty())
                        {
                            outputStack.setStackDisplayName(name);
                        }

                        JsonArray loreConfig = JsonUtils.getJsonArray(displayConfig.getAsJsonObject(), "lore", new JsonArray());

                        if(loreConfig.size() > 0)
                        {
                            NBTUtil.setTag(outputStack);
                            NBTTagList loreList = new NBTTagList();

                            for(JsonElement lore : loreConfig)
                            {
                                if(lore.isJsonPrimitive() && lore.getAsJsonPrimitive().isString())
                                {
                                    loreList.appendTag(new NBTTagString(lore.getAsJsonPrimitive().getAsString()));
                                }
                            }

                            NBTTagCompound displayCompound = new NBTTagCompound();
                            displayCompound.setTag("Lore", loreList);
                            NBTTagCompound compound = new NBTTagCompound();
                            compound.setTag("display", displayCompound);
                            NBTUtil.setTag(outputStack, compound);
                        }
                    }

                    for(JsonElement enchantmentConfig : JsonUtils.getJsonArray(outputConfig, "enchantments", new JsonArray()))
                    {
                        Enchantment enchantment = Enchantment.getEnchantmentByLocation(JsonUtils.getString(enchantmentConfig.getAsJsonObject(), "enchantment", ""));

                        if(enchantment != null)
                        {
                            int minEnchantmentLevel = JsonUtils.getInt(enchantmentConfig.getAsJsonObject(), "minEnchantmentLevel", 1);
                            int maxEnchantmentLevel = JsonUtils.getInt(enchantmentConfig.getAsJsonObject(), "maxEnchantmentLevel", 1);

                            if(outputStack.getItem() instanceof ItemEnchantedBook)
                            {
                                ((ItemEnchantedBook) outputStack.getItem()).addEnchantment(outputStack, new EnchantmentData(enchantment, RandomUtil.getNumberInRange(minEnchantmentLevel, maxEnchantmentLevel, rand)));
                            }
                            else
                            {
                                outputStack.addEnchantment(enchantment, RandomUtil.getNumberInRange(minEnchantmentLevel, maxEnchantmentLevel, rand));
                            }
                        }
                    }

                    Tuple<Integer, Integer> outputTuple = getItemStackSizes((JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "output", new JsonObject())));
                    Tuple<Integer, Integer> inputATuple = getItemStackSizes((JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "inputA", new JsonObject())));
                    Tuple<Integer, Integer> inputBTuple = getItemStackSizes((JsonUtils.getJsonObject(tradeConfig.getAsJsonObject(), "inputB", new JsonObject())));
                    int minTrades = JsonUtils.getInt(tradeConfig.getAsJsonObject(), "minTradesAvailable", 1);
                    int maxTrades = JsonUtils.getInt(tradeConfig.getAsJsonObject(), "maxTradesAvailable", 10);
                    int tradeLevel = JsonUtils.getInt(tradeConfig.getAsJsonObject(), "tradeLevel", 1);

                    return new EnhancedTrade(outputStack, outputTuple.getFirst(), outputTuple.getSecond(), inputAStack, inputATuple.getFirst(), inputATuple.getSecond(), inputBStack, inputBTuple.getFirst(), inputBTuple.getSecond(), minTrades, maxTrades, tradeLevel);
                }
            }
        }

        return null;
    }

    private static ItemStack deserializeItemStack(JsonObject config)
    {
        ItemStack stack = null;

        if(config.size() > 0)
        {
            String itemIdentifier = JsonUtils.getString(config, "item", "");
            ResourceLocation item = new ResourceLocation(itemIdentifier);

            if(ForgeRegistries.ITEMS.containsKey(item))
            {
                stack = new ItemStack(ForgeRegistries.ITEMS.getValue(item), 1, JsonUtils.getInt(config, "meta", 0));
            }
            if(ForgeRegistries.BLOCKS.containsKey(item))
            {
                IBlockState state = null;

                Block block = ForgeRegistries.BLOCKS.getValue(item);

                if(block != null)
                {
                    state = block.getDefaultState();
                }

                JsonObject blockProperties = JsonUtils.getJsonObject(config, "properties", new JsonObject());

                if(blockProperties.entrySet().size() > 0)
                {
                    state = BlockUtil.getBlockWithProperties(state, JsonUtils.getJsonObject(config, "properties"));
                }

                if(block != null && state != null)
                {
                    stack = new ItemStack(block, 1, block.getMetaFromState(state));
                }
            }
        }

        return stack;
    }

    private static Tuple<Integer, Integer> getItemStackSizes(JsonObject config)
    {
        Tuple<Integer, Integer> tuple;

        if(config.size() > 0)
        {
            int minStackSize = JsonUtils.getInt(config, "minStackSize", 1);
            int maxStackSize = JsonUtils.getInt(config, "maxStackSize", 2);

            tuple = new Tuple<>(minStackSize, maxStackSize);
        }
        else
        {
            tuple = new Tuple<>(1, 2);
        }

        return tuple;
    }

    public MerchantRecipe getRandomTrade(Random rand)
    {
        ItemStack outputStack = getItemToSell().copy();
        ItemStack inputAStack = getItemToBuy().copy();
        ItemStack inputBStack = getSecondItemToBuy().copy();
        outputStack.setCount(RandomUtil.getNumberInRange(outputMinStackSize, outputMaxStackSize, rand));
        inputAStack.setCount(RandomUtil.getNumberInRange(inputAMinStackSize, inputAMaxStackSize, rand));
        inputBStack.setCount(RandomUtil.getNumberInRange(inputBMinStackSize, inputBMaxStackSize, rand));

        return new MerchantRecipe(inputAStack, inputBStack, outputStack, 0, RandomUtil.getNumberInRange(minTradesAvailable, maxTradesAvailable, rand));
    }

    public int getTradeLevel()
    {
        return tradeLevel;
    }
}
