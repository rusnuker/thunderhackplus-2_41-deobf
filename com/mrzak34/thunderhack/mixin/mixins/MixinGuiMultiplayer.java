//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.mrzak34.thunderhack.modules.client.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.gui.*;

@Mixin({ GuiMultiplayer.class })
public abstract class MixinGuiMultiplayer extends GuiScreen
{
    @Shadow
    private ServerSelectionList serverListSelector;
    
    @Inject(method = { "createButtons" }, at = { @At("HEAD") })
    public void dobovlyaemhuiny(final CallbackInfo ci) {
        if (MultiConnect.getInstance().isEnabled()) {
            final IGuiScreen screen = (IGuiScreen)this;
            final List<GuiButton> buttonList = (List<GuiButton>)screen.getButtonList();
            buttonList.add(new GuiButton(22810007, this.width / 2 + 4 + 76 + 95, this.height - 52, 98, 20, "MultiConnect"));
            buttonList.add(new GuiButton(1337339, this.width / 2 + 4 + 76 + 95, this.height - 28, 98, 20, "Clear Selected"));
            screen.setButtonList((List)buttonList);
        }
    }
    
    @Inject(method = { "actionPerformed" }, at = { @At("RETURN") })
    public void chekarmknopki(final GuiButton button, final CallbackInfo ci) {
        if (MultiConnect.getInstance().isEnabled()) {
            if (button.id == 1337339) {
                MultiConnect.getInstance().serverData.clear();
            }
            if (button.id == 22810007) {
                if (!MultiConnect.getInstance().serverData.isEmpty()) {
                    for (final int serv : MultiConnect.getInstance().serverData) {
                        this.connectToSelected(serv);
                    }
                }
                else {
                    System.out.println("THUNDER ERROR!!!  \u0411\u043b\u044f \u0432\u044b\u0431\u0435\u0440\u0438 \u0441\u0435\u0440\u0432\u0435\u0440\u044b");
                }
            }
        }
    }
    
    public void connectToSelected(final int pizda) {
        if (MultiConnect.getInstance().isEnabled()) {
            final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (pizda < 0) ? null : this.serverListSelector.getListEntry(pizda);
            if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                FMLClientHandler.instance().connectToServer((GuiScreen)this, ((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
            }
        }
    }
}
