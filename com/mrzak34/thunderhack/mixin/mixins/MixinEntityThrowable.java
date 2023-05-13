//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.entity.projectile.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.render.*;
import com.mrzak34.thunderhack.*;
import org.spongepowered.asm.mixin.injection.*;
import com.mrzak34.thunderhack.util.*;
import java.awt.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.math.*;

@Mixin({ EntityThrowable.class })
public abstract class MixinEntityThrowable extends Entity
{
    @Shadow
    public Entity ignoreEntity;
    public boolean predictTick;
    public boolean breaked;
    @Shadow
    protected EntityLivingBase thrower;
    @Shadow
    protected boolean inGround;
    double lastTickPosX2;
    double lastTickPosY2;
    double lastTickPosZ2;
    double posX2;
    double posY2;
    double posZ2;
    double prevPosX2;
    double prevPosY2;
    double prevPosZ2;
    double motionX2;
    double motionY2;
    double motionZ2;
    int ticksInGround2;
    int ticksInAir2;
    int ignoreTime2;
    int ticksExisted2;
    float prevRotationYaw2;
    float prevRotationPitch2;
    Entity ignoreEntity2;
    boolean onGround2;
    boolean inGround2;
    float rotationYaw2;
    float rotationPitch2;
    @Shadow
    private int ticksInGround;
    @Shadow
    private int ticksInAir;
    @Shadow
    private int ignoreTime;
    
    public MixinEntityThrowable(final World worldIn) {
        super(worldIn);
    }
    
    @Inject(method = { "setVelocity" }, at = { @At("RETURN") })
    private void setVelocityHook(final double x, final double y, final double z, final CallbackInfo ci) {
        if (!this.breaked && ((PearlESP)Thunderhack.moduleManager.getModuleByClass((Class)PearlESP.class)).isOn()) {
            this.lastTickPosX2 = this.lastTickPosX;
            this.lastTickPosY2 = this.lastTickPosY;
            this.lastTickPosZ2 = this.lastTickPosZ;
            this.posX2 = this.posX;
            this.posY2 = this.posY;
            this.posZ2 = this.posZ;
            this.prevPosX2 = this.prevPosX;
            this.prevPosY2 = this.prevPosY;
            this.prevPosZ2 = this.prevPosZ;
            this.motionX2 = this.motionX;
            this.motionY2 = this.motionY;
            this.motionZ2 = this.motionZ;
            this.ticksInGround2 = this.ticksInGround;
            this.ticksInAir2 = this.ticksInAir;
            this.ignoreTime2 = this.ignoreTime;
            this.ticksExisted2 = this.ticksExisted;
            this.prevRotationYaw2 = this.prevRotationYaw;
            this.prevRotationPitch2 = this.prevRotationPitch;
            this.ignoreEntity2 = this.ignoreEntity;
            this.onGround2 = this.onGround;
            this.inGround2 = this.inGround;
            this.rotationYaw2 = this.rotationYaw;
            this.rotationPitch2 = this.rotationPitch;
            this.buildPositions(200);
        }
    }
    
    public void buildPositions(final int ticks) {
        final PearlESP tm = (PearlESP)Thunderhack.moduleManager.getModuleByClass((Class)PearlESP.class);
        int i = 0;
        final double prevLastPosX = this.lastTickPosX2;
        final double prevLastPosY = this.lastTickPosY2;
        final double prevLastPosZ = this.lastTickPosZ2;
        final double prevprevPosX = this.prevPosX2;
        final double prevprevPosY = this.prevPosY2;
        final double prevprevPosZ = this.prevPosZ2;
        final double prevPosX = this.posX2;
        final double prevPosY = this.posY2;
        final double prevPosZ = this.posZ2;
        final double prevMotionX = this.motionX2;
        final double prevMotionY = this.motionY2;
        final double prevMotionZ = this.motionZ2;
        final boolean prevOnGround = this.onGround2;
        final boolean prevInGround = this.inGround2;
        final int prevTicksInGround = this.ticksInGround2;
        final int prevTicksInAir = this.ticksInAir2;
        final int prevIgnoreTime = this.ignoreTime2;
        final int prevTicksExisted = this.ticksExisted2;
        final float prevPrevYaw = this.prevRotationYaw2;
        final float prevPrevPitch = this.prevRotationPitch2;
        final Entity prevIgnoreEntity = this.ignoreEntity2;
        this.predictTick = true;
        if (tm.entAndTrail.get(Util.mc.world.getEntityByID(this.getEntityId())) != null) {
            tm.entAndTrail.get(Util.mc.world.getEntityByID(this.getEntityId())).clear();
        }
        final List<PearlESP.PredictedPosition> trails22 = new ArrayList<PearlESP.PredictedPosition>();
        tm.entAndTrail.putIfAbsent(Util.mc.world.getEntityByID(this.getEntityId()), trails22);
        while (i < ticks) {
            this.onUpdateFake();
            final PearlESP.PredictedPosition pos = new PearlESP.PredictedPosition();
            pos.pos = this.getFakePosition();
            pos.tick = i;
            pos.color = new Color(-1);
            tm.entAndTrail.get(Util.mc.world.getEntityByID(this.getEntityId())).add(pos);
            if (i == 0) {
                this.breaked = false;
            }
            if (this.breaked) {
                break;
            }
            ++i;
        }
        this.prevRotationYaw2 = prevPrevYaw;
        this.prevRotationPitch2 = prevPrevPitch;
        this.rotationYaw2 = this.prevRotationYaw2;
        this.rotationPitch2 = this.prevRotationPitch2;
        this.predictTick = false;
        this.lastTickPosX2 = prevLastPosX;
        this.lastTickPosY2 = prevLastPosY;
        this.lastTickPosZ2 = prevLastPosZ;
        this.prevPosX2 = prevprevPosX;
        this.prevPosY2 = prevprevPosY;
        this.prevPosZ2 = prevprevPosZ;
        this.posX2 = prevPosX;
        this.posY2 = prevPosY;
        this.posZ2 = prevPosZ;
        this.motionX2 = prevMotionX;
        this.motionY2 = prevMotionY;
        this.motionZ2 = prevMotionZ;
        this.onGround2 = prevOnGround;
        this.inGround2 = prevInGround;
        this.ticksInGround2 = prevTicksInGround;
        this.ticksInAir2 = prevTicksInAir;
        this.ignoreTime2 = prevIgnoreTime;
        this.ticksExisted2 = prevTicksExisted;
        this.ignoreEntity2 = prevIgnoreEntity;
    }
    
    private Vec3d getFakePosition() {
        return new Vec3d(this.posX2, this.posY2, this.posZ2);
    }
    
    public void onUpdateFake() {
        this.lastTickPosX2 = this.posX2;
        this.lastTickPosY2 = this.posY2;
        this.lastTickPosZ2 = this.posZ2;
        if (this.inGround2) {
            this.inGround2 = false;
            this.motionX2 *= this.rand.nextFloat() * 0.2f;
            this.motionY2 *= this.rand.nextFloat() * 0.2f;
            this.motionZ2 *= this.rand.nextFloat() * 0.2f;
            this.ticksInGround2 = 0;
            this.ticksInAir2 = 0;
        }
        else {
            ++this.ticksInAir2;
        }
        Vec3d vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
        Vec3d vec3d2 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d2);
        vec3d = new Vec3d(this.posX2, this.posY2, this.posZ2);
        vec3d2 = new Vec3d(this.posX2 + this.motionX2, this.posY2 + this.motionY2, this.posZ2 + this.motionZ2);
        if (raytraceresult != null) {
            vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
        }
        Entity entity = null;
        final List<Entity> list = (List<Entity>)this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().expand(this.motionX2, this.motionY2, this.motionZ2).grow(1.0));
        double d0 = 0.0;
        boolean flag = false;
        for (final Entity entity2 : list) {
            if (entity2.canBeCollidedWith()) {
                if (entity2 == this.ignoreEntity2) {
                    flag = true;
                }
                else if (this.thrower != null && this.ticksExisted < 2 && this.ignoreEntity2 == null) {
                    this.ignoreEntity2 = entity2;
                    flag = true;
                }
                else {
                    flag = false;
                    final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().grow(0.30000001192092896);
                    final RayTraceResult raytraceresult2 = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                    if (raytraceresult2 == null) {
                        continue;
                    }
                    final double d2 = vec3d.squareDistanceTo(raytraceresult2.hitVec);
                    if (d2 >= d0 && d0 != 0.0) {
                        continue;
                    }
                    entity = entity2;
                    d0 = d2;
                }
            }
        }
        if (this.ignoreEntity2 != null) {
            if (flag) {
                this.ignoreTime2 = 2;
            }
            else if (this.ignoreTime2-- <= 0) {
                this.ignoreEntity2 = null;
            }
        }
        if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
        }
        if (raytraceresult != null) {
            if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK || this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() != Blocks.PORTAL) {
                if (this.predictTick) {
                    this.breaked = true;
                }
            }
        }
        this.posX2 += this.motionX2;
        this.posY2 += this.motionY2;
        this.posZ2 += this.motionZ2;
        final float f = MathHelper.sqrt(this.motionX2 * this.motionX2 + this.motionZ2 * this.motionZ2);
        this.rotationYaw2 = (float)(MathHelper.atan2(this.motionX2, this.motionZ2) * 57.29577951308232);
        this.rotationPitch2 = (float)(MathHelper.atan2(this.motionY2, (double)f) * 57.29577951308232);
        while (this.rotationPitch2 - this.prevRotationPitch2 < -180.0f) {
            this.prevRotationPitch2 -= 360.0f;
        }
        while (this.rotationPitch2 - this.prevRotationPitch2 >= 180.0f) {
            this.prevRotationPitch2 += 360.0f;
        }
        while (this.rotationYaw2 - this.prevRotationYaw2 < -180.0f) {
            this.prevRotationYaw2 -= 360.0f;
        }
        while (this.rotationYaw2 - this.prevRotationYaw2 >= 180.0f) {
            this.prevRotationYaw2 += 360.0f;
        }
        this.rotationPitch2 = this.prevRotationPitch2 + (this.rotationPitch2 - this.prevRotationPitch2) * 0.2f;
        this.rotationYaw2 = this.prevRotationYaw2 + (this.rotationYaw2 - this.prevRotationYaw2) * 0.2f;
        final float f2 = 0.99f;
        final float f3 = 0.03f;
        this.motionX2 *= f2;
        this.motionY2 *= f2;
        this.motionZ2 *= f2;
        if (!this.hasNoGravity()) {
            this.motionY2 -= f3;
        }
    }
}
