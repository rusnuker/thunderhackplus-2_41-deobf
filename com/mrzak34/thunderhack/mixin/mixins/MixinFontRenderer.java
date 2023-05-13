//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.modules.misc.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer
{
    @Shadow
    protected abstract void renderStringAtPos(final String p0, final boolean p1);
    
    @Redirect(method = { "renderString(Ljava/lang/String;FFIZ)I" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
    public void renderStringAtPosHook(final FontRenderer fontRenderer, final String string, final boolean bl) {
        if (Thunderhack.moduleManager == null) {
            this.renderStringAtPos(string, bl);
            return;
        }
        if (((PasswordHider)Thunderhack.moduleManager.getModuleByClass((Class)PasswordHider.class)).isEnabled() && (string.contains("/l ") || string.contains("/login ") || string.contains("/reg ") || (string.contains("/register ") && Util.mc.currentScreen instanceof GuiChat))) {
            final StringBuilder final_string = new StringBuilder();
            for (final char cha : string.replace("/login ", "").replace("/register ", "").replace("/l ", "").replace("/reg ", "").toCharArray()) {
                final_string.append("*");
            }
            if (string.contains("/register")) {
                this.renderStringAtPos("/register " + (Object)final_string, bl);
                return;
            }
            if (string.contains("/login")) {
                this.renderStringAtPos("/login " + (Object)final_string, bl);
                return;
            }
            if (string.contains("/l ")) {
                this.renderStringAtPos("/l " + (Object)final_string, bl);
                return;
            }
            if (string.contains("/reg ")) {
                this.renderStringAtPos("/reg " + (Object)final_string, bl);
                return;
            }
        }
        if (((NameProtect)Thunderhack.moduleManager.getModuleByClass((Class)NameProtect.class)).isEnabled()) {
            if (Util.mc == null || Util.mc.getSession() == null) {
                return;
            }
            this.renderStringAtPos(string.replace(Util.mc.getSession().getUsername(), "Protected"), bl);
        }
        else {
            this.renderStringAtPos(string, bl);
        }
    }
}
