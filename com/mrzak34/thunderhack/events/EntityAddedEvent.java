//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

public class EntityAddedEvent extends Event
{
    public Entity entity;
    
    public EntityAddedEvent(final Entity entity) {
        this.entity = entity;
    }
}
