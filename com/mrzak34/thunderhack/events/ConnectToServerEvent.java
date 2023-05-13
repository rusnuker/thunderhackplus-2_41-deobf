//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class ConnectToServerEvent extends Event
{
    String ip;
    
    public ConnectToServerEvent(final String ip) {
        this.ip = ip;
    }
    
    public String getIp() {
        return this.ip;
    }
}
