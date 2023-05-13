//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.misc;

import com.mrzak34.thunderhack.modules.misc.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import org.lwjgl.opengl.*;
import java.util.*;
import com.mrzak34.thunderhack.command.*;
import org.lwjgl.input.*;

public class GuiScanner extends GuiScreen
{
    public static boolean neartrack;
    public static boolean track;
    public static boolean busy;
    private static GuiScanner INSTANCE;
    public ArrayList<NoCom.cout> consoleout;
    int radarx;
    int radary;
    int radarx1;
    int radary1;
    int centerx;
    int centery;
    int consolex;
    int consoley;
    int consolex1;
    int consoley1;
    int hovery;
    int hoverx;
    int searchx;
    int searchy;
    int wheely;
    
    public GuiScanner() {
        this.consoleout = new ArrayList<NoCom.cout>();
        this.radarx = 0;
        this.radary = 0;
        this.radarx1 = 0;
        this.radary1 = 0;
        this.centerx = 0;
        this.centery = 0;
        this.consolex = 0;
        this.consoley = 0;
        this.consolex1 = 0;
        this.consoley1 = 0;
        this.hovery = 0;
        this.hoverx = 0;
        this.searchx = 0;
        this.searchy = 0;
        this.wheely = 0;
        this.setInstance();
        this.load();
    }
    
    public static GuiScanner getInstance() {
        if (GuiScanner.INSTANCE == null) {
            GuiScanner.INSTANCE = new GuiScanner();
        }
        return GuiScanner.INSTANCE;
    }
    
    public static GuiScanner getGuiScanner() {
        return getInstance();
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    private void load() {
    }
    
    private void setInstance() {
        GuiScanner.INSTANCE = this;
    }
    
    public float getscale() {
        if (NoCom.getInstance().scale.getValue() == 1) {
            return 500.0f;
        }
        if (NoCom.getInstance().scale.getValue() == 2) {
            return 250.0f;
        }
        if (NoCom.getInstance().scale.getValue() == 3) {
            return 125.0f;
        }
        if (NoCom.getInstance().scale.getValue() == 4) {
            return 75.0f;
        }
        return 705.0f;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        this.checkMouseWheel(mouseX, mouseY);
        this.radarx = sr.getScaledWidth() / 8;
        this.radarx1 = sr.getScaledWidth() * 5 / 8;
        this.radary = sr.getScaledHeight() / 2 - (this.radarx1 - this.radarx) / 2;
        this.radary1 = sr.getScaledHeight() / 2 + (this.radarx1 - this.radarx) / 2;
        this.centerx = (this.radarx + this.radarx1) / 2;
        this.centery = (this.radary + this.radary1) / 2;
        this.consolex = (int)(sr.getScaledWidth() * 5.5f / 8.0f);
        this.consolex1 = sr.getScaledWidth() - 50;
        this.consoley = this.radary;
        this.consoley1 = this.radary1 - 50;
        RenderUtil.drawOutlineRect((float)this.consolex, (float)this.consoley, (float)(this.consolex1 - this.consolex), (float)(this.consoley1 - this.consoley), 4.0f, new Color(-844584792, true).getRGB());
        RenderUtil.drawRect2(this.consolex, this.consoley, this.consolex1, this.consoley1, new Color(-150205428, true).getRGB());
        RenderUtil.drawOutlineRect((float)this.consolex, (float)(this.consoley1 + 3), (float)(this.consolex1 - this.consolex), 15.0f, 4.0f, new Color(-844584792, true).getRGB());
        RenderUtil.drawRect2(this.consolex, this.consoley1 + 3, this.consolex1, this.consoley1 + 17, new Color(-150205428, true).getRGB());
        FontRender.drawString3("cursor pos: " + this.hoverx * 64 + "x  " + this.hovery * 64 + "z", (float)(this.consolex + 4), (float)(this.consoley1 + 6), -1);
        RenderUtil.drawOutlineRect((float)this.consolex, (float)(this.consoley1 + 20), (float)(this.consolex1 - this.consolex), 15.0f, 4.0f, new Color(-844584792, true).getRGB());
        if (!GuiScanner.track) {
            RenderUtil.drawRect2(this.consolex, this.consoley1 + 20, this.consolex1, this.consoley1 + 35, new Color(-150205428, true).getRGB());
            FontRender.drawString3("tracker off", (float)(this.consolex + 4), (float)(this.consoley1 + 26), -1);
        }
        else {
            RenderUtil.drawRect2(this.consolex, this.consoley1 + 20, this.consolex1, this.consoley1 + 35, new Color(-144810402, true).getRGB());
            FontRender.drawString3("tracker on", (float)(this.consolex + 4), (float)(this.consoley1 + 26), -1);
        }
        RenderUtil.drawOutlineRect((float)this.radarx, (float)this.radary, (float)(this.radarx1 - this.radarx), (float)(this.radary1 - this.radary), 4.0f, new Color(-844584792, true).getRGB());
        RenderUtil.drawRect2(this.radarx, this.radary, this.radarx1, this.radary1, new Color(-535489259, true).getRGB());
        try {
            for (final NoCom.Dot point : NoCom.dots) {
                if (point.type == NoCom.DotType.Searched) {
                    RenderUtil.drawRect2(point.posX / 4.0f + this.centerx, point.posY / 4.0f + this.centery, point.posX / 4.0f + (this.radarx1 - this.radarx) / this.getscale() + this.centerx, point.posY / 4.0f + (this.radary1 - this.radary) / this.getscale() + this.centery, new Color(-408377176, true).getRGB());
                }
                else {
                    RenderUtil.drawRect2(point.posX / 4.0f + this.centerx, point.posY / 4.0f + this.centery, point.posX / 4.0f + (this.radarx1 - this.radarx) / this.getscale() + this.centerx, point.posY / 4.0f + (this.radary1 - this.radary) / this.getscale() + this.centery, new Color(3991304).getRGB());
                }
            }
        }
        catch (Exception ex) {}
        RenderUtil.drawRect2(this.centerx - 1.0f, this.centery - 1.0f, this.centerx + 1.0f, this.centery + 1.0f, new Color(16712451).getRGB());
        RenderUtil.drawRect2(this.mc.player.posX / 16.0 / 4.0 + this.centerx, this.mc.player.posZ / 16.0 / 4.0 + this.centery, this.mc.player.posX / 16.0 / 4.0 + (this.radarx1 - this.radarx) / this.getscale() + this.centerx, this.mc.player.posZ / 16.0 / 4.0 + (this.radary1 - this.radary) / this.getscale() + this.centery, new Color(4863).getRGB());
        if (mouseX > this.radarx && mouseX < this.radarx1 && mouseY > this.radary && mouseY < this.radary1) {
            this.hoverx = mouseX - this.centerx;
            this.hovery = mouseY - this.centery;
        }
        RenderUtil.glScissor((float)this.consolex, (float)this.consoley, (float)this.consolex1, (float)(this.consoley1 - 10), sr);
        GL11.glEnable(3089);
        try {
            for (final NoCom.cout out : this.consoleout) {
                FontRender.drawString3(out.string, (float)(this.consolex + 4), (float)(this.consoley + 6 + out.posY * 11 + this.wheely), -1);
            }
        }
        catch (Exception ex2) {}
        GL11.glDisable(3089);
        FontRender.drawString3("X+", (float)(this.radarx1 + 5), (float)this.centery, -1);
        FontRender.drawString3("X-", (float)(this.radarx - 15), (float)this.centery, -1);
        FontRender.drawString3("Y+", (float)this.centerx, (float)(this.radary1 + 5), -1);
        FontRender.drawString3("Y-", (float)this.centerx, (float)(this.radary - 8), -1);
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        if (mouseX > this.radarx && mouseX < this.radarx1 && mouseY > this.radary && mouseY < this.radary1) {
            GuiScanner.busy = true;
            this.searchx = mouseX - this.centerx;
            this.searchy = mouseY - this.centery;
            Command.sendMessage(this.searchx * 64 + " " + this.searchy * 64);
            NoCom.rerun(this.searchx * 64, this.searchy * 64);
            getInstance().consoleout.add(new NoCom.cout(NoCom.getInstance().couti, "Selected pos " + this.searchx * 65 + "x " + this.searchy * 64 + "z "));
            final NoCom instance = NoCom.getInstance();
            ++instance.couti;
        }
        if (mouseX > this.consolex && mouseX < this.consolex1 && mouseY > this.consoley1 + 20 && mouseY < this.consoley1 + 36) {
            GuiScanner.track = !GuiScanner.track;
        }
    }
    
    public void checkMouseWheel(final int mouseX, final int mouseY) {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            this.wheely -= 20;
        }
        else if (dWheel > 0) {
            this.wheely += 20;
        }
    }
    
    static {
        GuiScanner.neartrack = false;
        GuiScanner.track = false;
        GuiScanner.busy = false;
        GuiScanner.INSTANCE = new GuiScanner();
    }
}
