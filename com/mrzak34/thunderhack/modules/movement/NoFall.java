//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.movement;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class NoFall extends Module
{
    public Setting<rotmod> mod;
    
    public NoFall() {
        super("NoFall", "\u0440\u0443\u0431\u0435\u0440\u0431\u0435\u043d\u0434\u0438\u0442 \u0435\u0441\u043b\u0438 \u0442\u044b-\u0443\u043f\u0430\u043b", Module.Category.MOVEMENT);
        this.mod = (Setting<rotmod>)this.register(new Setting("Mode", (T)rotmod.Rubberband));
    }
    
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (NoFall.mc.player.fallDistance > 3.0f && !NoFall.mc.player.isSneaking() && this.mod.getValue() == rotmod.Rubberband) {
            final EntityPlayerSP player = NoFall.mc.player;
            player.motionY -= 0.1;
            NoFall.mc.player.onGround = true;
            NoFall.mc.player.capabilities.disableDamage = true;
        }
        if (this.mod.getValue() == rotmod.Default && NoFall.mc.player.fallDistance > 2.5) {
            NoFall.mc.player.connection.sendPacket((Packet)new CPacketPlayer(true));
        }
    }
    
    public enum rotmod
    {
        Rubberband, 
        Default;
    }
}
