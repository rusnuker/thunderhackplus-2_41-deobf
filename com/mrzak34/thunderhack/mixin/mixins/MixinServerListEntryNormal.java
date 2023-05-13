//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.modules.client.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.util.*;

@Mixin({ ServerListEntryNormal.class })
public abstract class MixinServerListEntryNormal
{
    @Inject(method = { "mousePressed" }, at = { @At("HEAD") })
    public void Z(final int slotIndex, final int mouseX, final int mouseY, final int mouseEvent, final int relativeX, final int relativeY, final CallbackInfoReturnable<Boolean> cir) {
        if (MultiConnect.getInstance().isEnabled() && Mouse.isButtonDown(1)) {
            MultiConnect.getInstance().serverData.add(slotIndex);
            System.out.println("THUNDER HACK \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u0441\u043b\u043e\u0442 " + slotIndex);
        }
    }
    
    @Inject(method = { "drawEntry" }, at = { @At("TAIL") })
    public void drawEntry(final int slotIndex, final int x, final int y, final int listWidth, final int slotHeight, final int mouseX, final int mouseY, final boolean isSelected, final float partialTicks, final CallbackInfo ci) {
        if (MultiConnect.getInstance().isEnabled() && MultiConnect.getInstance().serverData.contains(slotIndex)) {
            Util.fr.drawString("SELECTED", x - 45, y + 14, -1);
        }
    }
}
