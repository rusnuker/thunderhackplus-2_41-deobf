//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.combat;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TargetStrafe extends Module
{
    public Setting<Float> reversedDistance;
    public Setting<Float> speedIfUsing;
    public Setting<Float> range;
    public Setting<Float> spd;
    public Setting<Boolean> reversed;
    public Setting<Boolean> autoJump;
    public Setting<Boolean> smartStrafe;
    public Setting<Boolean> usingItemCheck;
    public Setting<Boolean> speedpot;
    public Setting<Float> spdd;
    public Setting<Boolean> autoThirdPerson;
    public Setting<Float> trgrange;
    public Setting<Boolean> drawradius;
    public Setting<Boolean> strafeBoost;
    public Setting<Boolean> addddd;
    public Setting<Float> reduction;
    public Setting<Float> velocityUse;
    public Setting<Integer> bticks;
    public Setting<Integer> velocitydecrement;
    EntityPlayer strafeTarget;
    int boostticks;
    float speedy;
    int velocity;
    private float wrap;
    private boolean switchDir;
    
    public TargetStrafe() {
        super("TargetStrafe", "\u0412\u0440\u0430\u0449\u0430\u0442\u044c\u0441\u044f \u0432\u043e\u043a\u0440\u0443\u0433 \u0446\u0435\u043b\u0438", Category.COMBAT);
        this.reversedDistance = (Setting<Float>)this.register(new Setting("Reversed Distance", (T)3.0f, (T)1.0f, (T)6.0f));
        this.speedIfUsing = (Setting<Float>)this.register(new Setting("Speed if using", (T)0.1f, (T)0.1f, (T)2.0f));
        this.range = (Setting<Float>)this.register(new Setting("Strafe Distance", (T)2.4f, (T)0.1f, (T)6.0f));
        this.spd = (Setting<Float>)this.register(new Setting("Strafe Speed", (T)0.23f, (T)0.1f, (T)2.0f));
        this.reversed = (Setting<Boolean>)this.register(new Setting("Reversed", (T)false));
        this.autoJump = (Setting<Boolean>)this.register(new Setting("AutoJump", (T)true));
        this.smartStrafe = (Setting<Boolean>)this.register(new Setting("Smart Strafe", (T)true));
        this.usingItemCheck = (Setting<Boolean>)this.register(new Setting("EatingSlowDown", (T)false));
        this.speedpot = (Setting<Boolean>)this.register(new Setting("Speed if Potion ", (T)true));
        this.spdd = (Setting<Float>)this.register(new Setting("PotionSpeed", (T)0.45f, (T)0.1f, (T)2.0f, v -> this.speedpot.getValue()));
        this.autoThirdPerson = (Setting<Boolean>)this.register(new Setting("AutoThirdPers", (T)Boolean.TRUE));
        this.trgrange = (Setting<Float>)this.register(new Setting("TrgtRange", (T)3.8f, (T)0.1f, (T)7.0f));
        this.drawradius = (Setting<Boolean>)this.register(new Setting("drawradius", (T)true));
        this.strafeBoost = (Setting<Boolean>)this.register(new Setting("StrafeBoost", (T)false));
        this.addddd = (Setting<Boolean>)this.register(new Setting("add", (T)false));
        this.reduction = (Setting<Float>)this.register(new Setting("reduction ", (T)2.0f, (T)1.0f, (T)5.0f));
        this.velocityUse = (Setting<Float>)this.register(new Setting("velocityUse ", (T)50000.0f, (T)0.1f, (T)100000.0f));
        this.bticks = (Setting<Integer>)this.register(new Setting("BoostTicks", (T)5, (T)0, (T)60));
        this.velocitydecrement = (Setting<Integer>)this.register(new Setting("BoostDecr", (T)5, (T)0, (T)5000));
        this.strafeTarget = null;
        this.boostticks = 0;
        this.speedy = 1.0f;
        this.velocity = 0;
        this.wrap = 0.0f;
        this.switchDir = true;
    }
    
    @Override
    public void onEnable() {
        this.wrap = 0.0f;
        this.switchDir = true;
        Thunderhack.TICK_TIMER = 1.0f;
        this.velocity = 0;
    }
    
    @Override
    public void onDisable() {
        if (this.autoThirdPerson.getValue()) {
            TargetStrafe.mc.gameSettings.thirdPersonView = 0;
        }
    }
    
    public boolean needToSwitch(final double x, final double z) {
        if (TargetStrafe.mc.gameSettings.keyBindLeft.isPressed() || TargetStrafe.mc.gameSettings.keyBindRight.isPressed()) {
            return true;
        }
        int i = (int)(TargetStrafe.mc.player.posY + 4.0);
        while (i >= 0) {
            final BlockPos playerPos = new BlockPos(x, (double)i, z);
            if (!TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.LAVA)) {
                if (!TargetStrafe.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.FIRE)) {
                    if (TargetStrafe.mc.world.isAirBlock(playerPos)) {
                        --i;
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }
        return true;
    }
    
    private float toDegree(final double x, final double z) {
        return (float)(Math.atan2(z - TargetStrafe.mc.player.posZ, x - TargetStrafe.mc.player.posX) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    @Override
    public void onUpdate() {
        if (Aura.target != null) {
            if (!(Aura.target instanceof EntityPlayer)) {
                return;
            }
            this.strafeTarget = (EntityPlayer)Aura.target;
        }
        else {
            this.strafeTarget = null;
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPre(final EventSync event) {
        if (this.strafeTarget == null) {
            return;
        }
        if (TargetStrafe.mc.player.getDistanceSq((Entity)this.strafeTarget) < 0.2) {
            return;
        }
        if (this.autoThirdPerson.getValue()) {
            if (this.strafeTarget.getHealth() > 0.0f && TargetStrafe.mc.player.getDistance((Entity)this.strafeTarget) <= this.trgrange.getValue() && TargetStrafe.mc.player.getHealth() > 0.0f) {
                if (((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled()) {
                    TargetStrafe.mc.gameSettings.thirdPersonView = 1;
                }
            }
            else {
                TargetStrafe.mc.gameSettings.thirdPersonView = 0;
            }
        }
        if (TargetStrafe.mc.player.getDistance((Entity)this.strafeTarget) <= this.trgrange.getValue()) {
            if (EntityUtil.getHealth((Entity)this.strafeTarget) > 0.0f && this.autoJump.getValue() && ((Aura)Thunderhack.moduleManager.getModuleByClass((Class)Aura.class)).isEnabled() && TargetStrafe.mc.player.onGround) {
                TargetStrafe.mc.player.jump();
            }
            if (EntityUtil.getHealth((Entity)this.strafeTarget) > 0.0f) {
                final EntityLivingBase target = (EntityLivingBase)this.strafeTarget;
                if (target == null || TargetStrafe.mc.player.ticksExisted < 20) {
                    return;
                }
                if (this.speedpot.getValue()) {
                    if (TargetStrafe.mc.player.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionFromResourceLocation("speed")))) {
                        this.speedy = this.spdd.getValue();
                    }
                    else {
                        this.speedy = this.spd.getValue();
                    }
                }
                else {
                    this.speedy = this.spd.getValue();
                }
                float speed = (TargetStrafe.mc.gameSettings.keyBindUseItem.isKeyDown() && this.usingItemCheck.getValue()) ? this.speedIfUsing.getValue() : this.speedy;
                if (this.velocity > this.velocityUse.getValue() && this.strafeBoost.getValue()) {
                    if (this.velocity < 0) {
                        this.velocity = 0;
                    }
                    if (this.addddd.getValue()) {
                        speed += this.velocity / 8000.0f / this.reduction.getValue();
                    }
                    else {
                        speed = this.velocity / 8000.0f / this.reduction.getValue();
                    }
                    ++this.boostticks;
                    this.velocity -= this.velocitydecrement.getValue();
                }
                if (this.boostticks >= this.bticks.getValue()) {
                    this.boostticks = 0;
                    this.velocity = 0;
                }
                this.wrap = (float)Math.atan2(TargetStrafe.mc.player.posZ - target.posZ, TargetStrafe.mc.player.posX - target.posX);
                this.wrap += (this.switchDir ? (speed / TargetStrafe.mc.player.getDistance((Entity)target)) : (-(speed / TargetStrafe.mc.player.getDistance((Entity)target))));
                double x = target.posX + this.range.getValue() * Math.cos(this.wrap);
                double z = target.posZ + this.range.getValue() * Math.sin(this.wrap);
                if (this.smartStrafe.getValue() && this.needToSwitch(x, z)) {
                    this.switchDir = !this.switchDir;
                    this.wrap += 2.0f * (this.switchDir ? (speed / TargetStrafe.mc.player.getDistance((Entity)target)) : (-(speed / TargetStrafe.mc.player.getDistance((Entity)target))));
                    x = target.posX + this.range.getValue() * Math.cos(this.wrap);
                    z = target.posZ + this.range.getValue() * Math.sin(this.wrap);
                }
                final float searchValue = (this.reversed.getValue() && TargetStrafe.mc.player.getDistance((Entity)this.strafeTarget) < this.reversedDistance.getValue()) ? -90.0f : 0.0f;
                final float reversedValue = (!TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown() && !TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown()) ? searchValue : 0.0f;
                TargetStrafe.mc.player.motionX = speed * -Math.sin((float)Math.toRadians(this.toDegree(x + reversedValue, z + reversedValue)));
                TargetStrafe.mc.player.motionZ = speed * Math.cos((float)Math.toRadians(this.toDegree(x + reversedValue, z + reversedValue)));
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent e) {
        if (Aura.target != null && this.drawradius.getValue()) {
            final EntityLivingBase entity = Aura.target;
            final double calcX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * TargetStrafe.mc.getRenderPartialTicks() - ((IRenderManager)TargetStrafe.mc.getRenderManager()).getRenderPosX();
            final double calcY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * TargetStrafe.mc.getRenderPartialTicks() - ((IRenderManager)TargetStrafe.mc.getRenderManager()).getRenderPosY();
            final double calcZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * TargetStrafe.mc.getRenderPartialTicks() - ((IRenderManager)TargetStrafe.mc.getRenderManager()).getRenderPosZ();
            final float radius = this.range.getValue();
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glBegin(3);
            for (int i = 0; i <= 360; ++i) {
                final int rainbow = PaletteHelper.rainbow(300, 1.0f, 1.0f).getRGB();
                GlStateManager.color((rainbow >> 16 & 0xFF) / 255.0f, (rainbow >> 8 & 0xFF) / 255.0f, (rainbow & 0xFF) / 255.0f);
                GL11.glVertex3d(calcX + radius * Math.cos(Math.toRadians(i)), calcY, calcZ + radius * Math.sin(Math.toRadians(i)));
            }
            GL11.glEnd();
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
            GlStateManager.resetColor();
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)e.getPacket()).getEntityID() == TargetStrafe.mc.player.getEntityId()) {
            final SPacketEntityVelocity pack = (SPacketEntityVelocity)e.getPacket();
            int vX = pack.getMotionX();
            int vZ = pack.getMotionZ();
            if (vX < 0) {
                vX *= -1;
            }
            if (vZ < 0) {
                vZ *= -1;
            }
            this.velocity = vX + vZ;
        }
    }
}
