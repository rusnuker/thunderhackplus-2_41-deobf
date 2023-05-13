//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util.phobos;

import net.minecraft.util.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public enum Swing
{
    None {
        @Override
        public void swing(final EnumHand hand) {
        }
    }, 
    Packet {
        @Override
        public void swing(final EnumHand hand) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
    }, 
    Full {
        @Override
        public void swing(final EnumHand hand) {
            Util.mc.player.swingArm(hand);
        }
    }, 
    Client {
        @Override
        public void swing(final EnumHand hand) {
            Util.mc.player.swingArm(hand);
        }
    };
    
    public abstract void swing(final EnumHand p0);
}
