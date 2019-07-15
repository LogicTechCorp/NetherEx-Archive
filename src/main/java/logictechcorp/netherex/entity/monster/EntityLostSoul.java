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

package logictechcorp.netherex.entity.monster;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.ai.controller.FloatingMovementController;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class EntityLostSoul extends EntityCreature implements IMob
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLostSoul.class, DataSerializers.VARINT);

    public EntityLostSoul(World world)
    {
        super(world);
        this.setSize(0.75F, 1.0F);
        this.setNoGravity(true);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        this.moveHelper = new FloatingMovementController(this, false);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new AIFlee(this, 10.0F, 3.0D, 2.0D));
        this.tasks.addTask(0, new AITaunt(this));
        this.tasks.addTask(0, new AIScare(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D, 10));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 1, false, false, null));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TYPE, Type.NEUTRAL.ordinal());
    }

    @Override
    protected PathNavigate createNavigator(World world)
    {
        PathNavigateFlying navigateFlying = new PathNavigateFlying(this, world);
        navigateFlying.setCanOpenDoors(false);
        navigateFlying.setCanFloat(true);
        navigateFlying.setCanEnterDoors(true);
        return navigateFlying;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data)
    {
        this.setRandomType();
        return super.onInitialSpawn(difficulty, data);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            if(this.getType() == Type.MEAN || this.getType() == Type.ANGRY)
            {
                this.setDead();
            }
        }
    }

    @Override
    protected void updateAITasks()
    {
        if(!this.hasHome())
        {
            this.setHomePosAndDistance(new BlockPos(this), 8);
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {

    }

    @Override
    protected void updateFallState(double y, boolean onGround, IBlockState state, BlockPos pos)
    {

    }

    @Override
    public boolean isOnLadder()
    {
        return false;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public boolean hasEnlargedHead()
    {
        return false;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.rand.nextInt(10) == 0 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 8;
    }

    @Override
    public String getName()
    {
        if(this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            String type = this.getType().toString().toLowerCase();
            return I18n.translateToLocal("entity." + NetherEx.MOD_ID + ":" + type + "_lost_soul.name");
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return this.getType().lootTable;
    }

    public Type getType()
    {
        return Type.getFromOrdinal(this.dataManager.get(TYPE));
    }

    public void setType(Type type)
    {
        this.dataManager.set(TYPE, type.ordinal());
    }

    private void setRandomType()
    {
        this.setType(Type.getFromOrdinal(this.rand.nextInt(Type.values().length)));
    }

    public enum Type
    {
        KIND(NetherExTextures.KIND_LOST_SOUL, NetherExLootTables.KIND_LOST_SOUL),
        TIMID(NetherExTextures.TIMID_LOST_SOUL, NetherExLootTables.TIMID_LOST_SOUL),
        NEUTRAL(NetherExTextures.NEUTRAL_LOST_SOUL, NetherExLootTables.NEUTRAL_LOST_SOUL),
        MEAN(NetherExTextures.MEAN_LOST_SOUL, NetherExLootTables.MEAN_LOST_SOUL),
        ANGRY(NetherExTextures.ANGRY_LOST_SOUL, NetherExLootTables.ANGRY_LOST_SOUL);

        ResourceLocation texture;
        ResourceLocation lootTable;

        Type(ResourceLocation texture, ResourceLocation lootTable)
        {
            this.texture = texture;
            this.lootTable = lootTable;
        }

        public ResourceLocation getTexture()
        {
            return this.texture;
        }

        public ResourceLocation getLootTable()
        {
            return this.lootTable;
        }

        static Type getFromOrdinal(int ordinal)
        {
            if(ordinal < 0 || ordinal >= values().length)
            {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

    static class AIFlee extends EntityAIBase
    {
        private EntityLostSoul lostSoul;
        private float avoidDistance;
        private double nearSpeed;
        private double farSpeed;
        private PathNavigate navigator;
        private Path path;
        private EntityPlayer closestPlayer;

        AIFlee(EntityLostSoul lostSoul, float avoidDistance, double nearSpeed, double farSpeed)
        {
            this.lostSoul = lostSoul;
            this.avoidDistance = avoidDistance;
            this.nearSpeed = nearSpeed;
            this.farSpeed = farSpeed;
            this.navigator = lostSoul.navigator;
            this.setMutexBits(1);
        }

        @Override
        public void startExecuting()
        {
            this.navigator.setPath(this.path, this.farSpeed);
        }

        @Override
        public void updateTask()
        {
            if(this.lostSoul.getDistanceSq(this.closestPlayer) < 49.0D)
            {
                this.lostSoul.getNavigator().setSpeed(this.nearSpeed);
            }
            else
            {
                this.lostSoul.getNavigator().setSpeed(this.farSpeed);
            }
        }

        @Override
        public boolean shouldExecute()
        {
            if(this.lostSoul.getType() != Type.TIMID)
            {
                return false;
            }

            List<EntityPlayer> list = this.lostSoul.world.getEntitiesWithinAABB(EntityPlayer.class, this.lostSoul.getEntityBoundingBox().grow(this.avoidDistance, this.avoidDistance, this.avoidDistance));

            if(list.isEmpty())
            {
                return false;
            }
            else
            {
                this.closestPlayer = list.get(0);

                Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.lostSoul, 16, 7, new Vec3d(this.closestPlayer.posX, this.closestPlayer.posY, this.closestPlayer.posZ));

                if(vec3d == null)
                {
                    return false;
                }
                else
                {
                    this.path = this.navigator.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
                    return this.path != null;
                }
            }
        }

        @Override
        public boolean shouldContinueExecuting()
        {
            return !this.navigator.noPath();
        }

        @Override
        public void resetTask()
        {
            this.closestPlayer = null;
        }
    }

    static class AITaunt extends EntityAIBase
    {
        private EntityLostSoul lostSoul;

        AITaunt(EntityLostSoul lostSoul)
        {
            this.lostSoul = lostSoul;
            this.setMutexBits(1);
        }

        @Override
        public void startExecuting()
        {
        }

        @Override
        public void updateTask()
        {
        }

        @Override
        public void resetTask()
        {
        }

        @Override
        public boolean shouldExecute()
        {
            return this.lostSoul.getType() == Type.MEAN && this.lostSoul.getAttackTarget() != null;
        }
    }

    static class AIScare extends EntityAIBase
    {
        private EntityLostSoul lostSoul;

        public AIScare(EntityLostSoul lostSoul)
        {
            this.lostSoul = lostSoul;
            this.setMutexBits(1);
        }

        @Override
        public void startExecuting()
        {
        }

        @Override
        public void updateTask()
        {
        }

        @Override
        public void resetTask()
        {
        }

        @Override
        public boolean shouldExecute()
        {
            return this.lostSoul.getType() == Type.ANGRY && this.lostSoul.getAttackTarget() != null;
        }
    }
}
