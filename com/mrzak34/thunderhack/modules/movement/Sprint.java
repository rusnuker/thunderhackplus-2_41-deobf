//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.mixin.ducks.*;

public class Sprint extends Module
{
    public static double oldSpeed;
    public static double contextFriction;
    int cooldown;
    private final Setting<mode> Mode;
    public Setting<Float> speed1;
    
    public Sprint() {
        super("Sprint", "\u0430\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438-\u0441\u043f\u0440\u0438\u043d\u0442\u0438\u0442\u0441\u044f", Module.Category.MOVEMENT);
        this.Mode = (Setting<mode>)this.register(new Setting("Mode", (T)mode.Default));
        this.speed1 = (Setting<Float>)this.register(new Setting("Speed", (T)0.1f, (T)0.0f, (T)0.5f, v -> this.Mode.getValue() == mode.MatrixOmniSprint));
    }
    
    public static double calculateSpeed(final double speed) {
        final float speedAttributes = getAIMoveSpeed((EntityPlayer)Sprint.mc.player);
        final float n6 = getFrictionFactor((EntityPlayer)Sprint.mc.player);
        final float n7 = 0.16277136f / (n6 * n6 * n6);
        final float n8 = speedAttributes * n7;
        double max2 = Sprint.oldSpeed + n8;
        max2 = Math.max(0.25, max2);
        Sprint.contextFriction = n6;
        return max2 + speed;
    }
    
    public static void postMove(final double horizontal) {
        Sprint.oldSpeed = horizontal * Sprint.contextFriction;
    }
    
    public static float getAIMoveSpeed(final EntityPlayer contextPlayer) {
        final boolean prevSprinting = contextPlayer.isSprinting();
        contextPlayer.setSprinting(false);
        final float speed = contextPlayer.getAIMoveSpeed() * 1.3f;
        contextPlayer.setSprinting(prevSprinting);
        return speed;
    }
    
    private static float getFrictionFactor(final EntityPlayer contextPlayer) {
        final BlockPos.PooledMutableBlockPos blockpos = BlockPos.PooledMutableBlockPos.retain(Sprint.mc.player.prevPosX, Sprint.mc.player.getEntityBoundingBox().minY, Sprint.mc.player.prevPosZ);
        return contextPlayer.world.getBlockState((BlockPos)blockpos).getBlock().slipperiness * 0.91f;
    }
    
    @SubscribeEvent
    public void onSync(final EventSync e) {
        if (fullNullCheck()) {
            return;
        }
        if (Sprint.mc.player.isSneaking()) {
            return;
        }
        if (Sprint.mc.gameSettings.keyBindForward.isKeyDown()) {
            Sprint.mc.player.setSprinting(true);
        }
        if (this.cooldown > 0) {
            --this.cooldown;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.cooldown = 60;
        }
    }
    
    @SubscribeEvent
    public void onMove(final EventMove event) {
        if (this.Mode.getValue() == mode.MatrixOmniSprint && ((Speed)Thunderhack.moduleManager.getModuleByClass((Class)Speed.class)).isDisabled() && ((Strafe)Thunderhack.moduleManager.getModuleByClass((Class)Strafe.class)).isDisabled() && ((RusherScaffold)Thunderhack.moduleManager.getModuleByClass((Class)RusherScaffold.class)).isDisabled()) {
            final double dX = Sprint.mc.player.posX - Sprint.mc.player.prevPosX;
            final double dZ = Sprint.mc.player.posZ - Sprint.mc.player.prevPosZ;
            postMove(Math.sqrt(dX * dX + dZ * dZ));
            if (this.strafes()) {
                double forward = Sprint.mc.player.movementInput.moveForward;
                double strafe = Sprint.mc.player.movementInput.moveStrafe;
                float yaw = Sprint.mc.player.rotationYaw;
                if (forward == 0.0 && strafe == 0.0) {
                    Sprint.oldSpeed = 0.0;
                }
                else {
                    if (forward != 0.0) {
                        if (strafe > 0.0) {
                            yaw += ((forward > 0.0) ? -45 : 45);
                        }
                        else if (strafe < 0.0) {
                            yaw += ((forward > 0.0) ? 45 : -45);
                        }
                        strafe = 0.0;
                        if (forward > 0.0) {
                            forward = 1.0;
                        }
                        else if (forward < 0.0) {
                            forward = -1.0;
                        }
                    }
                    final double speed = calculateSpeed(this.speed1.getValue() / 10.0f);
                    event.set_x(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
                    event.set_z(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
                }
            }
            else {
                Sprint.oldSpeed = 0.0;
            }
            event.setCanceled(true);
        }
    }
    
    public boolean strafes() {
        return !Sprint.mc.player.isSneaking() && !Sprint.mc.player.isInLava() && !Sprint.mc.player.isInWater() && !((IEntity)Sprint.mc.player).isInWeb() && Sprint.mc.player.onGround && !Sprint.mc.gameSettings.keyBindJump.isKeyDown() && this.cooldown <= 0 && !((LongJump)Thunderhack.moduleManager.getModuleByClass((Class)LongJump.class)).isOn() && !Sprint.mc.player.capabilities.isFlying;
    }
    
    public enum mode
    {
        Default, 
        MatrixOmniSprint;
    }
}
