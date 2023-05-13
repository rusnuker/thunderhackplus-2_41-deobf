//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.util.text.*;
import com.mrzak34.thunderhack.modules.misc.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen extends Gui
{
    @Inject(method = { "renderToolTip" }, at = { @At("HEAD") }, cancellable = true)
    public void renderToolTipHook(final ItemStack stack, final int x, final int y, final CallbackInfo info) {
        if (ToolTips.getInstance().isOn() && stack.getItem() instanceof ItemShulkerBox) {
            ToolTips.getInstance().renderShulkerToolTip(stack, x, y, null);
            info.cancel();
        }
    }
    
    @Inject(method = { "handleComponentHover" }, at = { @At("HEAD") }, cancellable = true)
    private void handleComponentHoverHook(final ITextComponent component, final int x, final int y, final CallbackInfo info) {
        if (component != null) {
            DiscordEmbeds.saveDickPick(component.getStyle().getHoverEvent().getValue().getUnformattedText(), "png");
            DiscordEmbeds.nado = true;
            DiscordEmbeds.timer.reset();
        }
    }
}
