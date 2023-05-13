//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.util.math.*;

public class FreeLook extends Module
{
    private float dYaw;
    private float dPitch;
    private final Setting<Boolean> autoThirdPerson;
    
    public FreeLook() {
        super("FreeLook", "FreeLook", Module.Category.RENDER);
        this.dYaw = 0.0f;
        this.dPitch = 0.0f;
        this.autoThirdPerson = (Setting<Boolean>)this.register(new Setting("AutoThirdPerson", (T)true));
    }
    
    public void onEnable() {
        this.dYaw = 0.0f;
        this.dPitch = 0.0f;
        if (this.autoThirdPerson.getValue()) {
            FreeLook.mc.gameSettings.thirdPersonView = 1;
        }
    }
    
    public void onDisable() {
        if (this.autoThirdPerson.getValue()) {
            FreeLook.mc.gameSettings.thirdPersonView = 0;
        }
    }
    
    @SubscribeEvent
    public void onCameraSetup(final EntityViewRenderEvent.CameraSetup event) {
        if (FreeLook.mc.gameSettings.thirdPersonView > 0) {
            event.setYaw(event.getYaw() + this.dYaw);
            event.setPitch(event.getPitch() + this.dPitch);
        }
    }
    
    @SubscribeEvent
    public void onTurnEvent(final TurnEvent event) {
        if (FreeLook.mc.gameSettings.thirdPersonView > 0) {
            this.dYaw += (float)(event.getYaw() * 0.15);
            this.dPitch -= (float)(event.getPitch() * 0.15);
            this.dPitch = MathHelper.clamp(this.dPitch, -90.0f, 90.0f);
            event.setCanceled(true);
        }
    }
}
