//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import com.mrzak34.thunderhack.modules.movement.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { MovementInputFromOptions.class }, priority = 10000)
public abstract class MixinMovementInputFromOptions extends MovementInput
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        final int keyCode = keyBinding.getKeyCode();
        if (keyCode > 0 && keyCode < 256 && ((GuiMove)Thunderhack.moduleManager.getModuleByClass((Class)GuiMove.class)).isEnabled() && Minecraft.getMinecraft().currentScreen != null && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat) && keyCode != Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode()) {
            return Keyboard.isKeyDown(keyCode);
        }
        return keyBinding.isKeyDown();
    }
}
