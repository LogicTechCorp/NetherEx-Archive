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

package nex.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.block.BlockNetherrackPath;
import nex.block.BlockTilledSoulSand;
import nex.entity.item.EntityObsidianBoat;
import nex.entity.monster.EntityGhastling;
import nex.entity.monster.EntityNethermite;
import nex.entity.monster.EntitySpore;
import nex.entity.neutral.EntityMogus;
import nex.init.*;
import nex.util.ArmorUtil;
import nex.world.NetherExTeleporter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class EventHandler
{
    private static final Field FIELD_WORLD_TELEPORTER = ReflectionHelper.findField(WorldServer.class, "field_85177_Q", "worldTeleporter");

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!Loader.isModLoaded("netherportalfix") && ConfigHandler.Dimension.Nether.enablePortalFix && world instanceof WorldServer)
        {
            if(world.provider.getDimension() == 0 || world.provider.getDimension() == -1)
            {
                try
                {
                    FIELD_WORLD_TELEPORTER.set(world, new NetherExTeleporter((WorldServer) world));
                }
                catch(IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onMouse(MouseEvent event)
    {
        EntityPlayer player = Minecraft.getMinecraft().player;

        if(player.isPotionActive(NetherExEffects.FREEZE))
        {
            KeyBinding.unPressAllKeys();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderBlockOverlay(RenderBlockOverlayEvent event)
    {
        RenderBlockOverlayEvent.OverlayType type = event.getOverlayType();
        EntityPlayer player = event.getPlayer();
        World world = player.getEntityWorld();
        float partialTicks = event.getRenderPartialTicks();
        BlockPos pos = new BlockPos(player.getPositionEyes(partialTicks));

        if(type == RenderBlockOverlayEvent.OverlayType.FIRE)
        {
            if(player.isRiding() && player.getRidingEntity() instanceof EntityObsidianBoat || ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_HIDE_SALAMANDER))
            {
                event.setCanceled(true);
            }
        }
        else if(type == RenderBlockOverlayEvent.OverlayType.WATER && world.getBlockState(pos).getBlock() == NetherExBlocks.FLUID_ICHOR)
        {
            event.setCanceled(true);

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(NetherEx.MOD_ID + ":textures/blocks/fluid_ichor_overlay.png"));
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer vertexbuffer = tessellator.getBuffer();
            float f = Minecraft.getMinecraft().player.getBrightness(partialTicks);
            GlStateManager.color(f, f, f, 0.85F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.pushMatrix();
            float f7 = -Minecraft.getMinecraft().player.rotationYaw / 64.0F;
            float f8 = Minecraft.getMinecraft().player.rotationPitch / 64.0F;
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-1.0D, -1.0D, -0.5D).tex((double) (4.0F + f7), (double) (4.0F + f8)).endVertex();
            vertexbuffer.pos(1.0D, -1.0D, -0.5D).tex((double) (0.0F + f7), (double) (4.0F + f8)).endVertex();
            vertexbuffer.pos(1.0D, 1.0D, -0.5D).tex((double) (0.0F + f7), (double) (0.0F + f8)).endVertex();
            vertexbuffer.pos(-1.0D, 1.0D, -0.5D).tex((double) (4.0F + f7), (double) (0.0F + f8)).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = event.getItemStack();

        if(stack.getItem() == NetherExItems.TOOL_SHOVEL_BONE || ConfigHandler.Block.Netherrack.allowAllShovelsToFlatten && stack.getItem() instanceof ItemHoe)
        {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if(block == Blocks.NETHERRACK || block == NetherExBlocks.BLOCK_NETHERRACK || block == NetherExBlocks.BLOCK_HYPHAE)
            {
                for(EnumHand hand : EnumHand.values())
                {
                    if(player.getHeldItem(hand).getItem() instanceof ItemSpade)
                    {
                        player.swingArm(hand);
                    }
                }

                int meta = block == Blocks.NETHERRACK ? 0 : block == NetherExBlocks.BLOCK_HYPHAE ? 3 : NetherExBlocks.BLOCK_NETHERRACK.getMetaFromState(state) + 1;

                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.BLOCK_NETHERRACK_PATH.getDefaultState().withProperty(BlockNetherrackPath.TYPE, BlockNetherrackPath.EnumType.fromMeta(meta)), 11);
                stack.damageItem(1, player);
            }

        }
        if(stack.getItem() == NetherExItems.TOOL_HOE_BONE || ConfigHandler.Block.SoulSand.allowAllHoesToTill && stack.getItem() instanceof ItemHoe)
        {
            if(world.getBlockState(pos).getBlock() == Blocks.SOUL_SAND)
            {
                for(EnumHand hand : EnumHand.values())
                {
                    if(player.getHeldItem(hand).getItem() instanceof ItemHoe)
                    {
                        player.swingArm(hand);
                    }
                }

                world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), 11);
                stack.damageItem(1, player);
            }
        }
    }

    @SubscribeEvent
    public static void onBoneMealUse(BonemealEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getBlock();
        EntityPlayer player = event.getEntityPlayer();

        if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() == NetherExItems.ITEM_DUST_WITHER)
        {
            if(state.getBlock() == Blocks.NETHER_WART)
            {
                int age = state.getValue(BlockNetherWart.AGE);

                if(age < 3)
                {
                    state = state.withProperty(BlockNetherWart.AGE, age + 1);
                    world.setBlockState(pos, state);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            else if(state.getBlock() instanceof IGrowable)
            {
                IGrowable growable = (IGrowable) state.getBlock();

                if(growable.canGrow(world, pos, state, world.isRemote))
                {
                    growable.grow(world, world.rand, pos, state);
                }
            }
            else
            {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onCropPreGrow(BlockEvent.CropGrowEvent.Pre event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(ConfigHandler.Block.SoulSand.doesNetherwartUseNewGrowthSystem && state.getBlock() == Blocks.NETHER_WART)
        {
            if(world.getBlockState(pos.down()) == NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState().withProperty(BlockTilledSoulSand.MOISTURE, 7))
            {
                event.setResult(Event.Result.ALLOW);
            }
            else
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(!(event.getPlayer() instanceof FakePlayer))
        {
            EntityPlayer player = event.getPlayer();

            if(state.getBlock() == Blocks.MAGMA)
            {
                if(ConfigHandler.Block.Magma.turnIntoLava)
                {
                    if(EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player.getHeldItemMainhand()) == 0)
                    {
                        world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
                        player.getHeldItemMainhand().damageItem(1, player);
                        event.setCanceled(true);
                    }
                }
            }
            if(player.dimension == -1)
            {
                boolean canSpawn = Arrays.asList(ConfigHandler.Entity.Nethermite.whitelist).contains(state.getBlock().getRegistryName().toString());

                if(canSpawn && world.rand.nextInt(ConfigHandler.Entity.Nethermite.chanceOfSpawning) == 0)
                {
                    EntityNethermite nethermite = new EntityNethermite(world);
                    nethermite.setPosition((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D);
                    world.spawnEntity(nethermite);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();

        if(world.provider.getDimension() == -1)
        {
            if(state.getBlock() == Blocks.LAVA || state.getBlock() == Blocks.FLOWING_LAVA)
            {
                event.setResult(ConfigHandler.Dimension.Nether.isLavaInfinite ? Event.Result.ALLOW : Event.Result.DEFAULT);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingSpecialSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getWorld();
        BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
        EntityLivingBase entity = event.getEntityLiving();

        boolean canFreeze = !(entity instanceof EntityPlayer) && !Arrays.asList(ConfigHandler.PotionEffect.Freeze.blacklist).contains(EntityList.getKey(entity).toString());

        if(world.getBiomeForCoordsBody(pos) == NetherExBiomes.ARCTIC_ABYSS)
        {
            if(canFreeze)
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event)
    {
        World world = event.getEntityLiving().getEntityWorld();
        BlockPos pos = new BlockPos(event.getEntityLiving());
        EntityLivingBase entity = event.getEntityLiving();

        boolean canFreeze = !(entity instanceof EntityPlayer) && !Arrays.asList(ConfigHandler.PotionEffect.Freeze.blacklist).contains(EntityList.getKey(entity).toString());

        if(world.getBiomeForCoordsBody(pos) == NetherExBiomes.ARCTIC_ABYSS)
        {
            if(canFreeze && !entity.isPotionActive(NetherExEffects.FREEZE) && world.rand.nextInt(ConfigHandler.Biome.ArcticAbyss.chanceOfFreezing) == 0)
            {
                entity.addPotionEffect(new PotionEffect(NetherExEffects.FREEZE, 300, 0));
            }
        }
        if(entity instanceof EntityLiving && canFreeze)
        {
            if(!entity.isPotionActive(NetherExEffects.FREEZE))
            {
                if(((EntityLiving) entity).isAIDisabled())
                {
                    ((EntityLiving) entity).setNoAI(false);
                    entity.setSilent(false);
                }
            }
            else
            {
                ((EntityLiving) entity).setNoAI(true);
                entity.setSilent(true);

                if(world.rand.nextInt(ConfigHandler.PotionEffect.Freeze.chanceOfThawing) == 0)
                {
                    entity.removePotionEffect(NetherExEffects.FREEZE);
                }
            }
        }

        if(entity instanceof EntityPlayer && entity.isPotionActive(NetherExEffects.FREEZE))
        {
            entity.setPosition(entity.prevPosX, entity.prevPosY, entity.prevPosZ);
        }

        boolean canSpawnSpore = entity instanceof EntityPlayer || !Arrays.asList(ConfigHandler.PotionEffect.Spore.blacklist).contains(EntityList.getKey(entity).toString());

        if(canSpawnSpore && entity.isPotionActive(NetherExEffects.SPORE) && world.rand.nextInt(ConfigHandler.PotionEffect.Spore.chanceOfSporeSpawning) == 0)
        {
            if(world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(1, 1, 1)).size() < 3)
            {
                BlockPos newPos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(world.rand));

                if(!world.isRemote && world.isAirBlock(newPos) && world.getBlockState(newPos.down()).isSideSolid(world, newPos.down(), EnumFacing.UP))
                {
                    EntitySpore spore = new EntitySpore(world, 0);
                    spore.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
                    world.spawnEntity(spore);
                }
            }
        }

        boolean canSpawnGhastling = entity instanceof EntityPlayer && world.provider.getDimension() == -1;

        if(canSpawnGhastling && entity.isPotionActive(NetherExEffects.LOST) && world.rand.nextInt(ConfigHandler.PotionEffect.Lost.chanceOfGhastlingSpawning) == 0)
        {
            BlockPos newPos = pos.add(0, 5, 0).offset(entity.getHorizontalFacing().getOpposite(), 5);

            if(!world.isRemote && world.isAirBlock(newPos))
            {
                EntityGhastling ghastling = new EntityGhastling(world);
                ghastling.setPosition(newPos.getX(), newPos.getY(), newPos.getZ());
                ghastling.setAttackTarget(entity);
                world.spawnEntity(ghastling);
            }
        }
    }

    @SubscribeEvent
    public static void onSetAttackTarget(LivingSetAttackTargetEvent event)
    {
        EntityLivingBase attacker = (EntityLivingBase) event.getEntity();
        EntityLivingBase attackee = event.getTarget();

        if(attackee instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) attackee;

            if(attacker instanceof AbstractSkeleton)
            {
                if(ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_BONE_WITHERED))
                {
                    ((AbstractSkeleton) attacker).setAttackTarget(null);
                    player.addStat(NetherExAchievements.IN_PLAIN_SIGHT);
                }
            }
            if(attacker instanceof EntityPigZombie)
            {
                player.addStat(NetherExAchievements.UH_OH);
            }
            if(attacker instanceof EntityMogus)
            {
                player.addStat(NetherExAchievements.CUTE_BUT_DEADLY);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event)
    {
        EntityLivingBase entity = (EntityLivingBase) event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        DamageSource source = event.getSource();

        if(source.isFireDamage())
        {
            if(!entity.isImmuneToFire() && entity.isRiding() && entity.getRidingEntity() instanceof EntityObsidianBoat)
            {
                event.setCanceled(true);
            }
        }
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;

            if(source.isFireDamage())
            {
                if(ArmorUtil.isWearingFullArmorSet(player, NetherExMaterials.ARMOR_HIDE_SALAMANDER))
                {
                    event.setCanceled(true);
                }
            }
            if(player.dimension == -1)
            {
                if(source == DamageSource.LAVA && player.isPotionActive(MobEffects.FIRE_RESISTANCE))
                {
                    player.addStat(NetherExAchievements.STAYIN_FROSTY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        DamageSource source = event.getSource();

        if(entity instanceof AbstractSkeleton)
        {
            if(source.getSourceOfDamage() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
                player.addStat(NetherExAchievements.FROM_WITHIN);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event)
    {
        Random rand = new Random();
        BlockPos deathPoint = event.getEntity().getPosition();

        if(event.getEntity() instanceof EntityGhast)
        {
            event.getDrops().add(new EntityItem(event.getEntity().getEntityWorld(), deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.FOOD_MEAT_GHAST_RAW, rand.nextInt(3) + 1, 0)));
        }
        else if(event.getEntity() instanceof EntityWitherSkeleton)
        {
            ListIterator<EntityItem> iter = event.getDrops().listIterator();

            while(iter.hasNext())
            {
                EntityItem entityItem = iter.next();
                ItemStack stack = entityItem.getEntityItem();

                if(stack.getItem() == Items.BONE || stack.getItem() == Items.COAL)
                {
                    iter.remove();
                }
            }

            event.getDrops().add(new EntityItem(event.getEntity().world, deathPoint.getX(), deathPoint.getY(), deathPoint.getZ(), new ItemStack(NetherExItems.ITEM_BONE_WITHER, rand.nextInt(4), 0)));
        }
    }
}
