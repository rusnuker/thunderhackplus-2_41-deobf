//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.mainmenu;

import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import net.minecraft.client.gui.*;

public class ThunderMenu extends GuiScreen
{
    private MainMenuShader backgroundShader;
    
    public ThunderMenu() {
        try {
            if (Thunderhack.moduleManager != null) {
                switch (Thunderhack.moduleManager.getModuleByClass(MainSettings.class).shaderMode.getValue()) {
                    case WarThunder: {
                        this.backgroundShader = new MainMenuShader("/moon.fsh");
                        break;
                    }
                    case Smoke: {
                        this.backgroundShader = new MainMenuShader("/mainmenu.fsh");
                        break;
                    }
                    case Dicks: {
                        this.backgroundShader = new MainMenuShader("/dicks.fsh");
                        break;
                    }
                }
            }
        }
        catch (IOException var9) {
            throw new IllegalStateException("Failed to load backgound shader", var9);
        }
    }
    
    public static float func(float var0) {
        if ((var0 %= 360.0f) >= 180.0f) {
            var0 -= 360.0f;
        }
        if (var0 < -180.0f) {
            var0 += 360.0f;
        }
        return var0;
    }
    
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();
        this.buttonList.add(new GuiMainMenuButton(1, sr.getScaledWidth() / 2 - 110, sr.getScaledHeight() / 2 - 70, false, "SINGLEPLAYER", false));
        this.buttonList.add(new GuiMainMenuButton(2, sr.getScaledWidth() / 2 + 4, sr.getScaledHeight() / 2 - 70, false, "MULTIPLAYER", false));
        this.buttonList.add(new GuiMainMenuButton(0, sr.getScaledWidth() / 2 - 110, sr.getScaledHeight() / 2 - 29, false, "SETTINGS", false));
        this.buttonList.add(new GuiMainMenuButton(14, sr.getScaledWidth() / 2 + 4, sr.getScaledHeight() / 2 - 29, false, "ALTMANAGER", false));
        this.buttonList.add(new GuiMainMenuButton(666, sr.getScaledWidth() / 2 - 110, sr.getScaledHeight() / 2 + 12, true, "EXIT", false));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        GlStateManager.disableCull();
        this.backgroundShader.useShader((int)(sr.getScaledWidth() * 2.0f), (int)(sr.getScaledHeight() * 2.0f), (float)mouseX, (float)mouseY, (System.currentTimeMillis() - Thunderhack.initTime) / 1000.0f);
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        GlStateManager.disableCull();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final Color color = new Color(-2046820352, true);
        final float half_w = sr.getScaledWidth() / 2.0f;
        final float halh_h = sr.getScaledHeight() / 2.0f;
        RoundedShader.drawGradientRound(half_w - 120.0f, halh_h - 80.0f, 240.0f, 140.0f, 15.0f, color, color, color, color);
        FontRender.drawCentString8("THUNDERHACK", (float)((int)half_w - 52), (float)((int)halh_h - 82 - FontRender.getFontHeight8()), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            this.mc.displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, this.mc.gameSettings));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiWorldSelection((GuiScreen)this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
        }
        if (button.id == 14) {
            this.mc.displayGuiScreen((GuiScreen)new GuiAltManager());
        }
        if (button.id == 666) {
            Thunderhack.unload(false);
            this.mc.shutdown();
        }
    }
}
