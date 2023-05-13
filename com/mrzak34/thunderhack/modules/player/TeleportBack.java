//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.player;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.combat.*;
import com.mrzak34.thunderhack.notification.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class TeleportBack extends Module
{
    private final Setting<Integer> reset;
    private final Setting<ColorSetting> color;
    BackTrack.Box prev_pos;
    
    public TeleportBack() {
        super("TeleportBack", "\u0432\u043a\u043b\u044e\u0447\u0438\u043b \u043e\u0442\u043e\u0448\u0435\u043b \u043f\u0440\u044b\u0433\u043d\u0443\u043b-\u0442\u0435\u043f\u043d\u0443\u043b\u043e \u0442\u0443\u0434\u0430 \u0433\u0434\u0435 \u0432\u043a\u043b\u044e\u0447\u0430\u043b", "Matrix only", Module.Category.PLAYER);
        this.reset = (Setting<Integer>)this.register(new Setting("ResetDistance", (T)30, (T)1, (T)256));
        this.color = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-2009289807)));
    }
    
    @SubscribeEvent
    public void onSync(final EventSync event) {
        if (fullNullCheck()) {
            return;
        }
        TeleportBack.mc.player.setSprinting(false);
        if (TeleportBack.mc.gameSettings.keyBindJump.isKeyDown()) {
            TeleportBack.mc.player.motionY = 0.41999998688697815;
        }
        if (this.prev_pos != null && TeleportBack.mc.player.getDistanceSq(this.prev_pos.getPosition().x, this.prev_pos.getPosition().y, this.prev_pos.getPosition().z) > this.reset.getValue() * this.reset.getValue()) {
            NotificationManager.publicity("TeleportBack \u0422\u044b \u043e\u0442\u043e\u0448\u0435\u043b \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0434\u0430\u043b\u0435\u043a\u043e! \u0441\u0431\u0440\u0430\u0441\u044b\u0432\u0430\u044e \u043f\u043e\u0437\u0438\u0446\u0438\u044e...", 5, Notification.Type.ERROR);
            Command.sendMessage(ChatFormatting.RED + "TeleportBack \u0422\u044b \u043e\u0442\u043e\u0448\u0435\u043b \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0434\u0430\u043b\u0435\u043a\u043e! \u0441\u0431\u0440\u0430\u0441\u044b\u0432\u0430\u044e \u043f\u043e\u0437\u0438\u0446\u0438\u044e...");
            this.prev_pos = new BackTrack.Box(TeleportBack.mc.player.getPositionVector(), 20, TeleportBack.mc.player.limbSwing, TeleportBack.mc.player.limbSwingAmount, TeleportBack.mc.player.rotationYaw, TeleportBack.mc.player.rotationPitch, (EntityPlayer)TeleportBack.mc.player);
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer player = (CPacketPlayer)event.getPacket();
            ((ICPacketPlayer)player).setOnGround(false);
        }
    }
    
    public void onEnable() {
        this.prev_pos = new BackTrack.Box(TeleportBack.mc.player.getPositionVector(), 20, TeleportBack.mc.player.limbSwing, TeleportBack.mc.player.limbSwingAmount, TeleportBack.mc.player.rotationYaw, TeleportBack.mc.player.rotationPitch, (EntityPlayer)TeleportBack.mc.player);
    }
    
    @SubscribeEvent
    public void onPreRenderEvent(final PreRenderEvent event) {
        if (this.prev_pos == null) {
            return;
        }
        GlStateManager.pushMatrix();
        RenderUtil.renderEntity(this.prev_pos, (ModelBase)this.prev_pos.getModelPlayer(), this.prev_pos.getLimbSwing(), this.prev_pos.getLimbSwingAmount(), this.prev_pos.getYaw(), this.prev_pos.getPitch(), (EntityLivingBase)this.prev_pos.getEnt(), this.color.getValue().getColorObject());
        GlStateManager.popMatrix();
    }
}
