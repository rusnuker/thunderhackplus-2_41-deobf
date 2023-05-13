//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.block.state.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ReverseStep extends Module
{
    public Setting<Float> timer;
    public Setting<Boolean> anyblock;
    private boolean Field292;
    private boolean Field293;
    private final Setting<Mode> mode;
    
    public ReverseStep() {
        super("ReverseStep", "ReverseStep", Module.Category.MOVEMENT);
        this.timer = (Setting<Float>)this.register(new Setting("Timer", (T)3.0f, (T)1.0f, (T)10.0f));
        this.anyblock = (Setting<Boolean>)this.register(new Setting("AnyBlock", (T)false));
        this.Field292 = true;
        this.Field293 = false;
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.Motion));
    }
    
    @SubscribeEvent
    public void onEntitySync(final EventSync eventPlayerUpdateWalking) {
        if (((PacketFly)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly.class)).isEnabled() || ((PacketFly2)Thunderhack.moduleManager.getModuleByClass((Class)PacketFly2.class)).isEnabled()) {
            return;
        }
        final IBlockState iBlockState = ReverseStep.mc.world.getBlockState(new BlockPos(ReverseStep.mc.player.posX, ReverseStep.mc.player.posY, ReverseStep.mc.player.posZ).down(2));
        final IBlockState iBlockState2 = ReverseStep.mc.world.getBlockState(new BlockPos(ReverseStep.mc.player.posX, ReverseStep.mc.player.posY, ReverseStep.mc.player.posZ).down(3));
        final IBlockState iBlockState3 = ReverseStep.mc.world.getBlockState(new BlockPos(ReverseStep.mc.player.posX, ReverseStep.mc.player.posY, ReverseStep.mc.player.posZ).down(4));
        if ((iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN || this.anyblock.getValue()) && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
            if (ReverseStep.mc.player.onGround && this.mode.getValue() == Mode.Motion) {
                final EntityPlayerSP player = ReverseStep.mc.player;
                --player.motionY;
            }
            if (this.mode.getValue() == Mode.Timer && this.Field293 && !ReverseStep.mc.player.onGround && ReverseStep.mc.player.motionY < -0.1 && !this.Field292 && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
                Thunderhack.TICK_TIMER = this.timer.getValue();
                this.Field292 = true;
            }
        }
        else if ((iBlockState2.getBlock() == Blocks.BEDROCK || iBlockState2.getBlock() == Blocks.OBSIDIAN || this.anyblock.getValue()) && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
            if (ReverseStep.mc.player.onGround && this.mode.getValue() == Mode.Motion) {
                final EntityPlayerSP player2 = ReverseStep.mc.player;
                --player2.motionY;
            }
            if (this.mode.getValue() == Mode.Timer && this.Field293 && !ReverseStep.mc.player.onGround && ReverseStep.mc.player.motionY < -0.1 && !this.Field292 && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
                Thunderhack.TICK_TIMER = this.timer.getValue();
                this.Field292 = true;
            }
        }
        else if ((iBlockState3.getBlock() == Blocks.BEDROCK || iBlockState3.getBlock() == Blocks.OBSIDIAN || this.anyblock.getValue()) && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
            if (ReverseStep.mc.player.onGround && this.mode.getValue() == Mode.Motion) {
                final EntityPlayerSP player3 = ReverseStep.mc.player;
                --player3.motionY;
            }
            if (this.mode.getValue() == Mode.Timer && this.Field293 && !ReverseStep.mc.player.onGround && ReverseStep.mc.player.motionY < -0.1 && !this.Field292 && !ReverseStep.mc.player.isInLava() && !ReverseStep.mc.player.isInWater() && !((IEntity)ReverseStep.mc.player).isInWeb() && !ReverseStep.mc.player.isElytraFlying() && !ReverseStep.mc.player.capabilities.isFlying) {
                Thunderhack.TICK_TIMER = this.timer.getValue();
                this.Field292 = true;
            }
        }
        if (this.Field292 && (ReverseStep.mc.player.onGround || ReverseStep.mc.player.isInLava() || ReverseStep.mc.player.isInWater() || ((IEntity)ReverseStep.mc.player).isInWeb() || ReverseStep.mc.player.isElytraFlying() || ReverseStep.mc.player.capabilities.isFlying)) {
            this.Field292 = false;
            Thunderhack.TICK_TIMER = 1.0f;
        }
        this.Field293 = ReverseStep.mc.player.onGround;
    }
    
    public enum Mode
    {
        Timer, 
        Motion;
    }
}
