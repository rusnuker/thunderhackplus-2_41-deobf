//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.hud.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.item.*;

public class PvPResources extends HudElement
{
    public final Setting<ColorSetting> shadowColor;
    public final Setting<ColorSetting> color2;
    public final Setting<ColorSetting> color3;
    
    public PvPResources() {
        super("PvPResources", "PvPResources", 42, 42);
        this.shadowColor = (Setting<ColorSetting>)this.register(new Setting("ShadowColor", (T)new ColorSetting(-15724528)));
        this.color2 = (Setting<ColorSetting>)this.register(new Setting("Color", (T)new ColorSetting(-15724528)));
        this.color3 = (Setting<ColorSetting>)this.register(new Setting("Color2", (T)new ColorSetting(-979657829)));
    }
    
    @SubscribeEvent
    @Override
    public void onRender2D(final Render2DEvent e) {
        super.onRender2D(e);
        RenderUtil.drawBlurredShadow(this.getPosX(), this.getPosY(), 42.0f, 42.0f, 20, this.shadowColor.getValue().getColorObject());
        RoundedShader.drawRound(this.getPosX(), this.getPosY(), 42.0f, 42.0f, 7.0f, this.color2.getValue().getColorObject());
        final int n2 = this.Method492(Items.TOTEM_OF_UNDYING);
        final int n3 = this.Method492(Items.EXPERIENCE_BOTTLE);
        final int n4 = this.Method492(Items.END_CRYSTAL);
        final int n5 = this.Method492(Items.GOLDEN_APPLE);
        final List<ItemStack> list = new ArrayList<ItemStack>();
        if (n2 > 0) {
            list.add(new ItemStack(Items.TOTEM_OF_UNDYING, n2));
        }
        if (n3 > 0) {
            list.add(new ItemStack(Items.EXPERIENCE_BOTTLE, n3));
        }
        if (n4 > 0) {
            list.add(new ItemStack(Items.END_CRYSTAL, n4));
        }
        if (n5 > 0) {
            list.add(new ItemStack(Items.GOLDEN_APPLE, n5, 1));
        }
        for (int n6 = list.size(), i = 0; i < n6; ++i) {
            GlStateManager.pushMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.clear(256);
            RenderHelper.enableStandardItemLighting();
            PvPResources.mc.getRenderItem().zLevel = -150.0f;
            GlStateManager.disableAlpha();
            GlStateManager.enableDepth();
            GlStateManager.disableCull();
            int n7 = (int)this.getPosX();
            int n8 = (int)this.getPosY();
            final ItemStack itemStack = list.get(i);
            n7 = i % 2 * 20;
            n8 = i / 2 * 20;
            PvPResources.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, (int)(this.getPosX() + n7 + 2.0f), (int)(this.getPosY() + n8 + 2.0f));
            PvPResources.mc.getRenderItem().renderItemOverlays(PvPResources.mc.fontRenderer, itemStack, (int)(this.getPosX() + n7 + 2.0f), (int)(this.getPosY() + n8 + 2.0f));
            PvPResources.mc.getRenderItem().zLevel = 0.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.enableCull();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
        }
    }
    
    public int Method492(final Item item) {
        if (PvPResources.mc.player == null) {
            return 0;
        }
        int n = 0;
        for (int n2 = 44, i = 0; i <= n2; ++i) {
            final ItemStack itemStack = PvPResources.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == item) {
                n += itemStack.getCount();
            }
        }
        return n;
    }
}
