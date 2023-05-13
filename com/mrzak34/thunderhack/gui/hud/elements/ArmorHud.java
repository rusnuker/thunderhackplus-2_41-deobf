//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import net.minecraft.item.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.util.render.*;
import java.awt.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ArmorHud extends HudElement
{
    public ArmorHud() {
        super("ArmorHud", "armorhud", 100, 20);
    }
    
    public static float calculatePercentage(final ItemStack stack) {
        final float durability = (float)(stack.getMaxDamage() - stack.getItemDamage());
        return durability / stack.getMaxDamage() * 100.0f;
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        GlStateManager.enableTexture2D();
        int iteration = 0;
        for (final ItemStack is : ArmorHud.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = (int)(this.getPosX() - 90.0f + (9 - iteration) * 20 + 2.0f);
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, (int)this.getPosY());
            RenderUtil.itemRender.renderItemOverlayIntoGUI(ArmorHud.mc.fontRenderer, is, x, (int)this.getPosY(), "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            ArmorHud.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - ArmorHud.mc.fontRenderer.getStringWidth(s)), this.getPosY() + 9.0f, 16777215);
            final int dmg = (int)calculatePercentage(is);
            ArmorHud.mc.fontRenderer.drawStringWithShadow(dmg + "", x + 8 - ArmorHud.mc.fontRenderer.getStringWidth(dmg + "") / 2.0f, this.getPosY() - 11.0f, new Color(0, 255, 0).getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
}
