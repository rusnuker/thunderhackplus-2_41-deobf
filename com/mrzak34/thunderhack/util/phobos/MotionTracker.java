//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

public class MotionTracker extends EntityPlayerNoInterp
{
    public double extraPosX;
    public double extraPosY;
    public double extraPosZ;
    public double lastExtraPosX;
    public double lastExtraPosY;
    public double lastExtraPosZ;
    public EntityPlayer tracked;
    public volatile boolean active;
    public boolean shrinkPush;
    public boolean gravity;
    public double gravityFactor;
    public double yPlusFactor;
    public double yMinusFactor;
    public int ticks;
    
    public MotionTracker(final World worldIn, final EntityPlayer from) {
        super(worldIn, new GameProfile(from.getGameProfile().getId(), "Motion-Tracker-" + from.getName()));
        this.gravityFactor = 1.0;
        this.yPlusFactor = 1.0;
        this.yMinusFactor = 1.0;
        this.tracked = from;
        this.setEntityId(from.getEntityId() * -1);
        this.copyLocationAndAnglesFrom((Entity)from);
    }
    
    private MotionTracker(final World worldIn) {
        super(worldIn, new GameProfile(UUID.randomUUID(), "Motion-Tracker"));
        this.gravityFactor = 1.0;
        this.yPlusFactor = 1.0;
        this.yMinusFactor = 1.0;
    }
    
    public void resetMotion() {
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
    }
    
    public void updateFromTrackedEntity() {
        this.motionX = this.tracked.motionX;
        this.motionY = ((this.tracked.motionY > 0.0) ? (this.tracked.motionY * this.yPlusFactor) : (this.tracked.motionY * this.yMinusFactor));
        this.motionZ = this.tracked.motionZ;
        if (this.gravity) {
            this.motionY -= 0.03999999910593033 * this.gravityFactor * this.ticks;
        }
        final List<AxisAlignedBB> list1 = (List<AxisAlignedBB>)this.world.getCollisionBoxes((Entity)this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ));
        if (this.motionY != 0.0) {
            for (int k = 0, l = list1.size(); k < l; ++k) {
                this.motionY = list1.get(k).calculateYOffset(this.getEntityBoundingBox(), this.motionY);
            }
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, this.motionY, 0.0));
        }
        if (this.motionX != 0.0) {
            for (int j5 = 0, l2 = list1.size(); j5 < l2; ++j5) {
                this.motionX = list1.get(j5).calculateXOffset(this.getEntityBoundingBox(), this.motionX);
            }
            if (this.motionX != 0.0) {
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(this.motionX, 0.0, 0.0));
            }
        }
        if (this.motionY != 0.0) {
            for (int k2 = 0, i6 = list1.size(); k2 < i6; ++k2) {
                this.motionZ = list1.get(k2).calculateZOffset(this.getEntityBoundingBox(), this.motionZ);
            }
            if (this.motionZ != 0.0) {
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, this.motionZ));
            }
        }
        this.resetPositionToBB();
        this.onGround = this.tracked.onGround;
        this.prevPosX = this.tracked.prevPosX;
        this.prevPosY = this.tracked.prevPosY;
        this.prevPosZ = this.tracked.prevPosZ;
        this.collided = this.tracked.collided;
        this.collidedHorizontally = this.tracked.collidedHorizontally;
        this.collidedVertically = this.tracked.collidedVertically;
        this.moveForward = this.tracked.moveForward;
        this.moveStrafing = this.tracked.moveStrafing;
        this.moveVertical = this.tracked.moveVertical;
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        this.lastExtraPosX = this.extraPosX;
        this.lastExtraPosY = this.extraPosY;
        this.lastExtraPosZ = this.extraPosZ;
    }
    
    public void onUpdate() {
    }
    
    public void onLivingUpdate() {
    }
    
    public void setDead() {
        this.isDead = true;
        this.dead = true;
    }
    
    public boolean isSpectator() {
        return false;
    }
    
    public boolean isCreative() {
        return false;
    }
}
