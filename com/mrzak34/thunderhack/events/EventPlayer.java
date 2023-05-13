//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;

public class EventPlayer extends Event
{
    private final SPacketPlayerListItem.AddPlayerData addPlayerData;
    private final SPacketPlayerListItem.Action action;
    
    public EventPlayer(final SPacketPlayerListItem.AddPlayerData addPlayerData, final SPacketPlayerListItem.Action action) {
        this.addPlayerData = addPlayerData;
        this.action = action;
    }
    
    public SPacketPlayerListItem.AddPlayerData getPlayerData() {
        return this.addPlayerData;
    }
    
    public SPacketPlayerListItem.Action getAction() {
        return this.action;
    }
}
