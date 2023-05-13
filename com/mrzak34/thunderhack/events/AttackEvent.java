//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class AttackEvent extends Event
{
    short stage;
    Entity entity;
    
    public AttackEvent(final Entity attack, final short stage) {
        this.entity = attack;
        this.stage = stage;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public short getStage() {
        return this.stage;
    }
}
