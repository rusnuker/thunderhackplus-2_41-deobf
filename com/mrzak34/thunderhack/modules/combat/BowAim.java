//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import com.mrzak34.thunderhack.events.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;

public class BowAim extends Module
{
    private final Setting<Float> range;
    private final Setting<Float> fov;
    Entity target;
    private final Setting<Boolean> ignoreWalls;
    private final Setting<Boolean> noVertical;
    private double sideMultiplier;
    private double upMultiplier;
    private Vec3d predict;
    
    public BowAim() {
        super("AimBot", "AimBot", Category.COMBAT);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)60.0f, (T)0.0f, (T)200.0f));
        this.fov = (Setting<Float>)this.register(new Setting("fov", (T)60.0f, (T)0.0f, (T)180.0f));
        this.ignoreWalls = (Setting<Boolean>)this.register(new Setting("IgnoreWalls", (T)false));
        this.noVertical = (Setting<Boolean>)this.register(new Setting("NoVertical", (T)false));
    }
    
    @SubscribeEvent
    public void onMotionUpdate(final EventPostSync event) {
        if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && BowAim.mc.player.isHandActive() && BowAim.mc.player.getItemInUseMaxCount() > 0) {
            this.target = (Entity)this.findTarget();
            if (this.target == null) {
                return;
            }
            final double xPos = this.target.posX;
            final double yPos = this.target.posY;
            final double zPos = this.target.posZ;
            this.sideMultiplier = BowAim.mc.player.getDistance(this.target) / (BowAim.mc.player.getDistance(this.target) / 2.0f) * 5.0f;
            this.upMultiplier = BowAim.mc.player.getDistance(this.target) / 320.0f * 1.1;
            this.predict = new Vec3d(xPos, yPos + this.upMultiplier, zPos);
            final float[] rotation = this.lookAtPredict(this.predict);
            BowAim.mc.player.rotationYaw = rotation[0];
            if (this.noVertical.getValue()) {
                BowAim.mc.player.rotationPitch = rotation[1];
            }
            this.target = null;
        }
    }
    
    private float[] lookAtPredict(final Vec3d vec) {
        final double diffX = vec.x + 0.5 - BowAim.mc.player.posX;
        final double diffY = vec.y + 0.5 - (BowAim.mc.player.posY + BowAim.mc.player.getEyeHeight());
        final double diffZ = vec.z + 0.5 - BowAim.mc.player.posZ;
        final double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { BowAim.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - BowAim.mc.player.rotationYaw), BowAim.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - BowAim.mc.player.rotationPitch) };
    }
    
    public EntityPlayer findTarget() {
        EntityPlayer target = null;
        double distance = this.range.getValue() * this.range.getValue();
        for (final EntityPlayer entity : BowAim.mc.world.playerEntities) {
            if (entity == BowAim.mc.player) {
                continue;
            }
            if (Thunderhack.friendManager.isFriend(entity)) {
                continue;
            }
            if (EntityUtil.canEntityBeSeen((Entity)entity) && !this.ignoreWalls.getValue()) {
                continue;
            }
            if (!EntityUtil.canSeeEntityAtFov((Entity)entity, this.fov.getValue())) {
                continue;
            }
            if (BowAim.mc.player.getDistanceSq((Entity)entity) > distance) {
                continue;
            }
            target = entity;
            distance = BowAim.mc.player.getDistanceSq((Entity)entity);
        }
        return target;
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.target != null) {
            RenderHelper.drawEntityBox(this.target, new Color(PaletteHelper.astolfo(false, 12).getRGB()), new Color(PaletteHelper.astolfo(false, 12).getRGB()), false, 255.0f);
        }
    }
}
