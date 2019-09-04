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

package logictechcorp.netherex.entity.hostile;

import logictechcorp.netherex.entity.NetherExEntities;
import logictechcorp.netherex.entity.ai.goal.SporeCreeperSwellGoal;
import logictechcorp.netherex.particle.NetherExParticles;
import logictechcorp.netherex.potion.NetherExEffects;
import logictechcorp.netherex.utility.NetherExSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.*;

public class SporeCreeperEntity extends MonsterEntity
{
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(SporeCreeperEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(SporeCreeperEntity.class, DataSerializers.BOOLEAN);

    private int lastIgnitionTime;
    private int timeSinceIgnited;
    private int fuseTime = 30;
    private int explosionRadius = 2;

    public SporeCreeperEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<SporeCreeperEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSounds.ENTITY_SPORE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSounds.ENTITY_SPORE_DEATH;
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SporeCreeperSwellGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, ZombiePigmanEntity.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(STATE, -1);
        this.dataManager.register(IGNITED, false);
    }

    @Override
    public void tick()
    {
        if(this.isAlive())
        {
            this.lastIgnitionTime = this.timeSinceIgnited;

            if(this.hasIgnited())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if(i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound(NetherExSounds.ENTITY_SPORE_WARN, 1.0F, 0.5F);
            }

            this.timeSinceIgnited += i;

            if(this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if(this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putShort("Fuse", (short) this.fuseTime);
        compound.putByte("ExplosionRadius", (byte) this.explosionRadius);
        compound.putBoolean("ignited", this.hasIgnited());
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);

        if(compound.contains("Fuse", 99))
        {
            this.fuseTime = compound.getShort("Fuse");
        }

        if(compound.contains("ExplosionRadius", 99))
        {
            this.explosionRadius = compound.getByte("ExplosionRadius");
        }

        if(compound.getBoolean("ignited"))
        {
            this.ignite();
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        super.fall(distance, damageMultiplier);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + distance * 1.5F);

        if(this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return true;
    }

    @Override
    public int getMaxFallHeight()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    @OnlyIn(Dist.CLIENT)
    public float getFlashIntensity(float partialTicks)
    {
        return ((float) this.lastIgnitionTime + (float) (this.timeSinceIgnited - this.lastIgnitionTime) * partialTicks) / (float) (this.fuseTime - 2);
    }

    public int getCreeperState()
    {
        return this.dataManager.get(STATE);
    }

    public void setCreeperState(int state)
    {
        this.dataManager.set(STATE, state);
    }

    @Override
    protected boolean processInteract(PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItem(hand);

        if(stack.getItem() == Items.FLINT_AND_STEEL)
        {
            this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            player.swingArm(hand);

            if(!this.world.isRemote)
            {
                this.ignite();
                stack.damageItem(1, player, playerEntity -> player.sendBreakAnimation(hand));
                return true;
            }
        }

        return super.processInteract(player, hand);
    }

    private void explode()
    {
        this.dead = true;
        Explosion explosion = new SporeExplosion(this.world, this, this.posX, this.posY, this.posZ, (float) this.explosionRadius, this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING));
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        this.remove();
        this.spawnLingeringCloud();
    }

    private void spawnLingeringCloud()
    {
        Collection<EffectInstance> effects = this.getActivePotionEffects();

        if(!effects.isEmpty())
        {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.world, this.posX, this.posY, this.posZ);
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());

            for(EffectInstance effect : effects)
            {
                cloud.addEffect(new EffectInstance(effect));
            }

            this.world.addEntity(cloud);
        }
    }

    private boolean hasIgnited()
    {
        return this.dataManager.get(IGNITED);
    }

    private void ignite()
    {
        this.dataManager.set(IGNITED, true);
    }

    public class SporeExplosion extends Explosion
    {
        private final World world;

        private final Entity exploder;

        private final double explosionX;
        private final double explosionY;
        private final double explosionZ;

        private final float explosionSize;

        private final List<BlockPos> affectedBlockPositions;
        private final Map<PlayerEntity, Vec3d> playerKnockbackMap;
        private final Vec3d position;

        public SporeExplosion(World world, Entity entity, double x, double y, double z, float size, boolean flaming)
        {
            super(world, entity, x, y, z, size, flaming, Mode.DESTROY);

            this.world = world;
            this.exploder = entity;
            this.explosionX = x;
            this.explosionY = y;
            this.explosionZ = z;
            this.explosionSize = size;
            this.affectedBlockPositions = new ArrayList<>();
            this.playerKnockbackMap = new HashMap<>();
            this.position = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
        }

        @Override
        public void doExplosionA()
        {
            if(!this.world.isRemote)
            {
                Set<BlockPos> set = new HashSet<>();

                for(int x = 0; x < 16; x++)
                {
                    for(int y = 0; y < 16; y++)
                    {
                        for(int z = 0; z < 16; z++)
                        {
                            if(x == 0 || x == 15 || y == 0 || y == 15 || z == 0 || z == 15)
                            {
                                double posX = ((float) x / 15.0F * 2.0F - 1.0F);
                                double posY = ((float) y / 15.0F * 2.0F - 1.0F);
                                double posZ = ((float) z / 15.0F * 2.0F - 1.0F);
                                double d3 = Math.sqrt(posX * posX + posY * posY + posZ * posZ);
                                posX = posX / d3;
                                posY = posY / d3;
                                posZ = posZ / d3;
                                float explosionSize = this.explosionSize * (0.7F + this.world.rand.nextFloat() * 0.6F);
                                double explosionX = this.explosionX;
                                double explosionY = this.explosionY;
                                double explosionZ = this.explosionZ;

                                for(float size = 0.3F; explosionSize > 0.0F; explosionSize -= 0.22500001F)
                                {
                                    BlockPos explosionPos = new BlockPos(explosionX, explosionY, explosionZ);
                                    BlockState blockState = this.world.getBlockState(explosionPos);
                                    IFluidState fluidState = this.world.getFluidState(explosionPos);

                                    if(!blockState.isAir(this.world, explosionPos) || !fluidState.isEmpty())
                                    {
                                        float f2 = Math.max(blockState.getExplosionResistance(this.world, explosionPos, this.exploder, this), fluidState.getExplosionResistance(this.world, explosionPos, this.exploder, this));

                                        if(this.exploder != null)
                                        {
                                            f2 = this.exploder.getExplosionResistance(this, this.world, explosionPos, blockState, fluidState, f2);
                                        }

                                        explosionSize -= (f2 + 0.3F) * 0.3F;
                                    }

                                    if(explosionSize > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.world, explosionPos, blockState, explosionSize)))
                                    {
                                        set.add(explosionPos);
                                    }

                                    explosionX += posX * 0.30000001192092896D;
                                    explosionY += posY * 0.30000001192092896D;
                                    explosionZ += posZ * 0.30000001192092896D;
                                }
                            }
                        }
                    }
                }

                this.affectedBlockPositions.addAll(set);
                float f3 = this.explosionSize * 2.0F;
                int minX = MathHelper.floor(this.explosionX - (double) f3 - 1.0D);
                int maxX = MathHelper.floor(this.explosionX + (double) f3 + 1.0D);
                int minY = MathHelper.floor(this.explosionY - (double) f3 - 1.0D);
                int maxY = MathHelper.floor(this.explosionY + (double) f3 + 1.0D);
                int minZ = MathHelper.floor(this.explosionZ - (double) f3 - 1.0D);
                int maxZ = MathHelper.floor(this.explosionZ + (double) f3 + 1.0D);
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
                ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
                Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);

                for(Entity entity : list)
                {
                    if(!entity.isImmuneToExplosions())
                    {
                        double d12 = entity.getDistanceSq(this.explosionX, this.explosionY, this.explosionZ) / (double) f3;

                        if(d12 <= 1.0D)
                        {
                            double d5 = entity.posX - this.explosionX;
                            double d7 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
                            double d9 = entity.posZ - this.explosionZ;
                            double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

                            if(d13 != 0.0D)
                            {
                                d5 = d5 / d13;
                                d7 = d7 / d13;
                                d9 = d9 / d13;
                                double d14 = func_222259_a(vec3d, entity);
                                double d10 = (1.0D - d12) * d14;

                                if(entity.getType() != NetherExEntities.SPORE && entity.getType() != NetherExEntities.SPORE_CREEPER)
                                {
                                    entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f3 + 1.0D)));
                                }
                                if(entity instanceof LivingEntity)
                                {
                                    ((LivingEntity) entity).addPotionEffect(new EffectInstance(NetherExEffects.INFESTED, 2400, 0));
                                }

                                double d11 = d10;

                                if(entity instanceof LivingEntity)
                                {
                                    d11 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity) entity, d10);
                                }

                                entity.setMotion(entity.getMotion().add(d5 * d11, d7 * d11, d9 * d11));

                                if(entity instanceof PlayerEntity)
                                {
                                    PlayerEntity player = (PlayerEntity) entity;

                                    if(!player.isSpectator() && (!player.isCreative() || !player.abilities.isFlying))
                                    {
                                        this.playerKnockbackMap.put(player, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void doExplosionB(boolean spawnParticles)
        {
            this.world.playSound(null, this.explosionX, this.explosionY, this.explosionZ, NetherExSounds.ENTITY_SPORE_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

            if(!(this.explosionSize < 2.0F))
            {
                this.world.addParticle(NetherExParticles.SPORE_CREEPER_EXPLOSION_EMITTER, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
            }
            else
            {
                this.world.addParticle(NetherExParticles.SPORE_CREEPER_EXPLOSION, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
            }

            for(BlockPos affectedPos : this.affectedBlockPositions)
            {
                BlockState blockState = this.world.getBlockState(affectedPos);

                if(spawnParticles)
                {
                    double d0 = ((float) affectedPos.getX() + this.world.rand.nextFloat());
                    double d1 = ((float) affectedPos.getY() + this.world.rand.nextFloat());
                    double d2 = ((float) affectedPos.getZ() + this.world.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 = d3 / d6;
                    d4 = d4 / d6;
                    d5 = d5 / d6;
                    double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
                    d7 = d7 * (double) (this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
                    d3 = d3 * d7;
                    d4 = d4 * d7;
                    d5 = d5 * d7;
                    this.world.addParticle(ParticleTypes.POOF, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5);
                    this.world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, d3, d4, d5);
                }

                if(!blockState.isAir(this.world, affectedPos))
                {
                    if(this.world instanceof ServerWorld && blockState.canDropFromExplosion(this.world, affectedPos, this))
                    {
                        TileEntity tileEntity = blockState.hasTileEntity() ? this.world.getTileEntity(affectedPos) : null;
                        LootContext.Builder lootBuilder = new LootContext.Builder((ServerWorld) this.world).withRandom(this.world.rand).withParameter(LootParameters.POSITION, affectedPos).withParameter(LootParameters.TOOL, ItemStack.EMPTY).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntity);
                        lootBuilder.withParameter(LootParameters.EXPLOSION_RADIUS, this.explosionSize);
                        Block.spawnDrops(blockState, lootBuilder);
                    }

                    blockState.onBlockExploded(this.world, affectedPos, this);
                }
            }

            for(BlockPos affectedPos : this.affectedBlockPositions)
            {
                if(this.world.getBlockState(affectedPos).isAir(this.world, affectedPos) && this.world.getBlockState(affectedPos.down()).isOpaqueCube(this.world, affectedPos.down()) && this.world.rand.nextInt(3) == 0)
                {
                    SporeEntity spore = new SporeEntity(NetherExEntities.SPORE, this.world);
                    spore.setPosition((double) affectedPos.getX() + 0.5D, affectedPos.getY(), (double) affectedPos.getZ() + 0.5D);
                    this.world.addEntity(spore);
                }
            }
        }

        @Override
        public Map<PlayerEntity, Vec3d> getPlayerKnockbackMap()
        {
            return this.playerKnockbackMap;
        }

        @Override
        public LivingEntity getExplosivePlacedBy()
        {
            return this.exploder == null ? null : (this.exploder instanceof LivingEntity ? (LivingEntity) this.exploder : null);
        }

        @Override
        public void clearAffectedBlockPositions()
        {
            this.affectedBlockPositions.clear();
        }

        @Override
        public List<BlockPos> getAffectedBlockPositions()
        {
            return this.affectedBlockPositions;
        }

        @Override
        public Vec3d getPosition()
        {
            return this.position;
        }
    }
}
