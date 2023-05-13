//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.dism;

import net.minecraft.world.*;
import com.mrzak34.thunderhack.modules.misc.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityGib extends Entity
{
    public EntityLivingBase parent;
    public int type;
    public float pitchSpin;
    public float yawSpin;
    public int groundTime;
    public int liveTime;
    public boolean explosion;
    
    public EntityGib(final World world) {
        super(world);
        this.parent = null;
        this.type = 0;
        this.groundTime = 0;
        this.liveTime = Dismemberment.ticks;
        this.ignoreFrustumCheck = true;
    }
    
    public EntityGib(final World world, final EntityLivingBase gibParent, final int gibType, final Entity explo) {
        this(world);
        this.parent = gibParent;
        this.type = gibType;
        this.liveTime = Dismemberment.ticks;
        this.setLocationAndAngles(this.parent.posX, this.parent.getEntityBoundingBox().minY, this.parent.posZ, this.parent.rotationYaw, this.parent.rotationPitch);
        this.rotationYaw = this.parent.prevRenderYawOffset;
        this.prevRotationYaw = this.parent.rotationYaw;
        this.prevRotationPitch = this.parent.rotationPitch;
        this.motionX = this.parent.motionX + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.25;
        this.motionY = this.parent.motionY;
        this.motionZ = this.parent.motionZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.25;
        if (this.type == -1) {
            this.rotationYaw = this.parent.rotationYawHead;
            this.setSize(1.0f, 1.0f);
            this.posY += 1.5;
        }
        if (this.type == 0) {
            this.rotationYaw = this.parent.rotationYawHead;
            this.setSize(0.5f, 0.5f);
            if (this.parent instanceof EntityCreeper) {
                this.posY += 1.25;
            }
            else {
                this.posY += 1.5;
            }
        }
        else if (this.type == 1 || this.type == 2) {
            this.setSize(0.3f, 0.4f);
            double offset = 0.35;
            final double offset2 = -0.25;
            if (this.parent instanceof EntitySkeleton) {
                offset -= 0.05;
                this.posY += 0.15;
            }
            if (this.type == 2) {
                offset *= -1.0;
            }
            this.posX += offset * Math.cos(Math.toRadians(this.parent.renderYawOffset));
            this.posZ += offset * Math.sin(Math.toRadians(this.parent.renderYawOffset));
            this.posX += offset2 * Math.sin(Math.toRadians(this.parent.renderYawOffset));
            this.posZ -= offset2 * Math.cos(Math.toRadians(this.parent.renderYawOffset));
            this.posY += 1.25;
            final float n = -90.0f;
            this.rotationPitch = n;
            this.prevRotationPitch = n;
        }
        else if (this.type == 3) {
            this.setSize(0.5f, 0.5f);
            if (this.parent instanceof EntityCreeper) {
                this.posY += 0.75;
            }
            else {
                ++this.posY;
            }
        }
        else if (this.type == 4 || this.type == 5) {
            this.setSize(0.3f, 0.4f);
            double offset = 0.125;
            if (this.type == 5) {
                offset *= -1.0;
            }
            this.posX += offset * Math.cos(Math.toRadians(this.parent.renderYawOffset));
            this.posZ += offset * Math.sin(Math.toRadians(this.parent.renderYawOffset));
            this.posY += 0.375;
        }
        else if (this.type >= 6) {
            this.setSize(0.3f, 0.4f);
            double offset = 0.125;
            double offset2 = -0.25;
            if (this.parent instanceof EntitySkeleton) {
                offset -= 0.05;
                this.posY += 0.15;
            }
            if (this.type % 2 == 1) {
                offset *= -1.0;
            }
            if (this.type >= 8) {
                offset2 *= -1.0;
            }
            this.posX += offset * Math.cos(Math.toRadians(this.parent.renderYawOffset));
            this.posZ += offset * Math.sin(Math.toRadians(this.parent.renderYawOffset));
            this.posX += offset2 * Math.sin(Math.toRadians(this.parent.renderYawOffset));
            this.posZ -= offset2 * Math.cos(Math.toRadians(this.parent.renderYawOffset));
            this.posY += 0.3125;
        }
        float i = this.rand.nextInt(45) + 5.0f + this.rand.nextFloat();
        float j = this.rand.nextInt(45) + 5.0f + this.rand.nextFloat();
        if (this.rand.nextInt(2) == 0) {
            i *= -1.0f;
        }
        if (this.rand.nextInt(2) == 0) {
            j *= -1.0f;
        }
        this.pitchSpin = i * (float)(this.motionY + 0.3);
        this.yawSpin = j * (float)(Math.sqrt(this.motionX * this.motionZ) + 0.3);
        this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        if (explo != null) {
            double mag = 1.0;
            double mag2 = 1.0;
            double dist = explo.getDistance((Entity)this.parent);
            dist = Math.pow(dist / 2.0, 2.0);
            if (dist < 0.1) {
                dist = 0.1;
            }
            if (explo instanceof EntityTNTPrimed || explo instanceof EntityMinecartTNT) {
                mag = 4.0 / dist;
            }
            else if (explo instanceof EntityCreeper) {
                final EntityCreeper creep = (EntityCreeper)explo;
                if (creep.getPowered()) {
                    mag = 6.0 / dist;
                }
                else {
                    mag = 3.0 / dist;
                }
            }
            mag = Math.pow(mag, 2.0) * 0.2;
            mag2 = this.posY - explo.posY;
            this.motionX *= mag;
            this.motionY = mag2 * 0.4 + 0.22;
            this.motionZ *= mag;
            this.explosion = true;
        }
    }
    
    public void onUpdate() {
        if (this.parent == null) {
            this.setDead();
            return;
        }
        if (this.explosion) {
            this.motionX *= 1.0869565217391304;
            this.motionY *= 1.0526315789473684;
            this.motionZ *= 1.0869565217391304;
        }
        super.onUpdate();
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionY -= 0.08;
        this.motionY *= 0.98;
        this.motionX *= 0.91;
        this.motionZ *= 0.91;
        if (this.inWater) {
            this.motionY = 0.3;
            this.pitchSpin = 0.0f;
            this.yawSpin = 0.0f;
        }
        if (this.onGround || this.handleWaterMovement()) {
            this.rotationPitch += (-90.0f - this.rotationPitch % 360.0f) / 2.0f;
            this.motionY *= 0.8;
            this.motionX *= 0.8;
            this.motionZ *= 0.8;
        }
        else {
            this.rotationPitch += this.pitchSpin;
            this.rotationYaw += this.yawSpin;
            this.pitchSpin *= 0.98f;
            this.yawSpin *= 0.98f;
        }
        final List var2 = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().grow(0.15, 0.0, 0.15));
        if (var2 != null && !var2.isEmpty()) {
            for (final Entity var4 : var2) {
                if (var4 instanceof EntityGib && !var4.onGround) {
                    continue;
                }
                if (!var4.canBePushed()) {
                    continue;
                }
                var4.applyEntityCollision((Entity)this);
            }
        }
        if (this.onGround || this.handleWaterMovement()) {
            ++this.groundTime;
            if (this.groundTime > (int)((Dismemberment)Thunderhack.moduleManager.getModuleByClass((Class)Dismemberment.class)).gibGroundTime.getValue() + 20) {
                this.setDead();
            }
        }
        else if (this.groundTime > (int)((Dismemberment)Thunderhack.moduleManager.getModuleByClass((Class)Dismemberment.class)).gibGroundTime.getValue()) {
            --this.groundTime;
        }
        else {
            this.groundTime = 0;
        }
        if (this.liveTime + (int)((Dismemberment)Thunderhack.moduleManager.getModuleByClass((Class)Dismemberment.class)).gibTime.getValue() < Dismemberment.ticks) {
            this.setDead();
        }
    }
    
    public void fall(final float distance, final float damageMultiplier) {
    }
    
    public boolean isEntityAlive() {
        return !this.isDead;
    }
    
    protected void entityInit() {
    }
    
    public boolean writeToNBTOptional(final NBTTagCompound par1NBTTagCompound) {
        return false;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
    }
}
