//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import org.spongepowered.asm.mixin.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;
import org.lwjgl.input.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiContainer.class })
public abstract class MixinGuiContainer extends GuiScreen
{
    @Shadow
    public Container inventorySlots;
    private final Timer delayTimer;
    
    public MixinGuiContainer() {
        this.delayTimer = new Timer();
    }
    
    @Shadow
    protected abstract boolean isMouseOverSlot(final Slot p0, final int p1, final int p2);
    
    @Shadow
    protected abstract void handleMouseClick(final Slot p0, final int p1, final int p2, final ClickType p3);
    
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGradientRect(IIIIII)V", shift = At.Shift.BEFORE) })
    private void drawScreenHook(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        final ItemScroller scroller = (ItemScroller)Thunderhack.moduleManager.getModuleByClass((Class)ItemScroller.class);
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
            final Slot slot = this.inventorySlots.inventorySlots.get(i1);
            if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.isEnabled() && scroller.isEnabled() && Mouse.isButtonDown(0) && Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode()) && this.delayTimer.passedMs(scroller.delay.getValue())) {
                this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.QUICK_MOVE);
                this.delayTimer.reset();
            }
        }
    }
}
