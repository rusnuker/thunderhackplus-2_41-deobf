//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.mainmenu;

import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.io.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.clickui.elements.*;
import net.minecraft.util.*;
import java.util.*;

public class GuiAltManager extends GuiScreen
{
    public static List<AltCompoment> altscomponents;
    public static Timer clicktimer;
    int dwheel;
    private MainMenuShader backgroundShader;
    private boolean listening;
    private String add_name;
    
    public GuiAltManager() {
        this.listening = false;
        this.add_name = "";
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
    
    public void initGui() {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();
        this.buttonList.add(new GuiMainMenuButton(420, sr.getScaledWidth() / 2 - 120, sr.getScaledHeight() - 135, false, "ADD", true));
        this.buttonList.add(new GuiMainMenuButton(69, sr.getScaledWidth() / 2 + 4, sr.getScaledHeight() - 135, false, "RANDOM", true));
        this.buttonList.add(new GuiMainMenuButton(228, sr.getScaledWidth() / 2 - 120, sr.getScaledHeight() - 96, true, "BACK", true));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        GlStateManager.disableCull();
        this.backgroundShader.useShader(sr.getScaledWidth() * 2, sr.getScaledHeight() * 2, (float)mouseX, (float)mouseY, (System.currentTimeMillis() - Thunderhack.initTime) / 1000.0f);
        this.checkMouseWheel();
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
        final Color color2 = new Color(-469762048, true);
        final Color color3 = new Color(-466538191, true);
        final float half_w = sr.getScaledWidth() / 2.0f;
        final float halh_h = sr.getScaledHeight() / 2.0f;
        RoundedShader.drawGradientRound(half_w - 120.0f, 20.0f, 240.0f, (float)(sr.getScaledHeight() - 160), 15.0f, color, color, color, color);
        int alts_y = 0;
        for (final String alt : Thunderhack.alts) {
            GuiAltManager.altscomponents.add(new AltCompoment((int)(half_w - 105.0f), 30 + alts_y + this.dwheel, alt));
            alts_y += 49;
        }
        RenderUtil.glScissor(half_w - 110.0f, 20.0f, half_w - 105.0f + 215.0f, (float)(sr.getScaledHeight() - 140), sr);
        GL11.glEnable(3089);
        GuiAltManager.altscomponents.forEach(altCompoment -> altCompoment.render(mouseX, mouseY));
        GL11.glDisable(3089);
        if (this.listening) {
            RoundedShader.drawGradientRound(half_w - 60.0f, halh_h - 40.0f, 120.0f, 80.0f, 7.0f, color2, color2, color2, color2);
            RoundedShader.drawGradientRound(half_w - 55.0f, halh_h - 10.0f, 110.0f, 10.0f, 1.0f, color3, color3, color3, color3);
            RoundedShader.drawGradientRound(half_w - 15.0f, halh_h + 10.0f, 30.0f, 10.0f, 2.0f, color3, color3, color3, color3);
            final boolean hover_add = mouseX > half_w - 15.0f && mouseX < half_w + 15.0f && mouseY > halh_h + 10.0f && mouseY < halh_h + 20.0f;
            FontRender.drawCentString6("ADD", half_w, halh_h + 14.0f, hover_add ? new Color(8026746).getRGB() : -1);
            FontRender.drawCentString6(this.listening ? this.add_name : "name", half_w, halh_h - 7.0f, this.listening ? -1 : new Color(8026746).getRGB());
        }
        if (mouseX > half_w - 15.0f && mouseX < half_w + 15.0f && mouseY > halh_h + 10.0f && mouseY < halh_h + 20.0f && Mouse.isButtonDown(0) && this.listening) {
            Thunderhack.alts.add(this.add_name);
            this.add_name = "";
            this.listening = false;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        GuiAltManager.altscomponents.clear();
    }
    
    public void mouseClicked(final int x, final int y, final int button) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (x >= sr.getScaledWidth() / 2 - 120 && x <= sr.getScaledWidth() / 2 - 13 && y >= sr.getScaledHeight() - 135 && y <= sr.getScaledHeight() - 100) {
            this.listening = true;
        }
        if (x >= sr.getScaledWidth() / 2 + 4 && x <= sr.getScaledWidth() / 2 + 111 && y >= sr.getScaledHeight() - 135 && y <= sr.getScaledHeight() - 100) {
            final String name = "Th" + (int)(Math.random() * 10000.0);
            Thunderhack.alts.add(name);
            try {
                final String nickname;
                new Thread(() -> ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + nickname + "/16.png", nickname));
            }
            catch (Exception ex) {}
        }
        if (x >= sr.getScaledWidth() / 2 - 120 && x <= sr.getScaledWidth() / 2 + 102 && y >= sr.getScaledHeight() - 96 && y <= sr.getScaledHeight() - 61) {
            this.mc.displayGuiScreen((GuiScreen)new ThunderMenu());
        }
    }
    
    public void keyTyped(final char chr, final int keyCode) {
        if (this.listening) {
            switch (keyCode) {
                case 1: {
                    return;
                }
                case 28: {
                    Thunderhack.alts.add(this.add_name);
                    ThunderUtils.saveUserAvatar("https://minotar.net/helm/" + this.add_name + "/16.png", this.add_name);
                    this.add_name = "";
                    this.listening = false;
                }
                case 14: {
                    this.add_name = SliderElement.removeLastChar(this.add_name);
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(chr)) {
                this.add_name += chr;
            }
        }
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.dwheel -= 10;
        }
        else if (dWheel > 0) {
            this.dwheel += 10;
        }
    }
    
    static {
        GuiAltManager.altscomponents = new ArrayList<AltCompoment>();
        GuiAltManager.clicktimer = new Timer();
    }
}
