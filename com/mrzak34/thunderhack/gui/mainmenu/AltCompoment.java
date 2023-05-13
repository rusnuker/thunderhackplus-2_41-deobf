//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.mainmenu;

import net.minecraft.client.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.gui.*;

public class AltCompoment
{
    int posX;
    int posY;
    String name;
    ResourceLocation head;
    ResourceLocation crackedSkin;
    
    public AltCompoment(final int posX, final int posY, final String name) {
        this.head = null;
        this.crackedSkin = new ResourceLocation("textures/cracked.png");
        this.posX = posX;
        this.posY = posY;
        this.head = PNGtoResourceLocation.getTexture2(name, "png");
        this.name = name;
    }
    
    public static void login(final String string) {
        try {
            final Field field = Minecraft.class.getDeclaredField("session");
            field.setAccessible(true);
            final Field field2 = Field.class.getDeclaredField("modifiers");
            field2.setAccessible(true);
            field2.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            field.set(Util.mc, new Session(string, "", "", "mojang"));
            System.out.println("logged in " + string);
        }
        catch (Exception e) {
            System.out.println("ALT MANAGER ERROR!");
            e.printStackTrace();
        }
    }
    
    public void render(final int mouseX, final int mouseY) {
        final Color color = new Color(1644167168, true);
        final Color selected_color = new Color(-2128776703, true);
        RoundedShader.drawRound((float)this.posX, (float)this.posY, 210.0f, 40.0f, 8.0f, Util.mc.getSession().getUsername().equals(this.name) ? selected_color : color);
        this.renderCustomTexture(this.posX + 5, this.posY + 5, 30, 30, 16.0f, 16.0f);
        FontRender.drawString3(this.name, (float)(this.posX + 38), (float)(this.posY + 5), Util.mc.getSession().getUsername().equals(this.name) ? new Color(8026746).getRGB() : -1);
        RoundedShader.drawRound((float)(this.posX + 165), (float)(this.posY + 5), 35.0f, 12.0f, 3.0f, this.isHoveringLoggin(mouseX, mouseY) ? new Color(-2130380743, true) : new Color(-2126577920, true));
        FontRender.drawCentString6("log", this.posX + 182.5f, (float)(this.posY + 10), this.isHoveringLoggin(mouseX, mouseY) ? new Color(8026746).getRGB() : -1);
        RoundedShader.drawRound((float)(this.posX + 165), (float)(this.posY + 22), 35.0f, 12.0f, 3.0f, this.isHoveringDelete(mouseX, mouseY) ? new Color(-2113994752, true) : new Color(-2114584062, true));
        FontRender.drawCentString6("del", this.posX + 182.5f, (float)(this.posY + 27), this.isHoveringDelete(mouseX, mouseY) ? new Color(8026746).getRGB() : -1);
        if (Mouse.isButtonDown(0)) {
            this.mouseClicked(mouseX, mouseY, 0);
        }
    }
    
    private boolean isHoveringLoggin(final int x, final int y) {
        return x >= this.posX + 165 && x <= this.posX + 200 && y >= this.posY + 5 && y <= this.posY + 17;
    }
    
    private boolean isHoveringDelete(final int x, final int y) {
        return x >= this.posX + 165 && x <= this.posX + 200 && y >= this.posY + 22 && y <= this.posY + 34;
    }
    
    public void mouseClicked(final int x, final int y, final int button) {
        if (!GuiAltManager.clicktimer.passedMs(500L)) {
            return;
        }
        if (this.isHoveringLoggin(x, y)) {
            login(this.name);
            GuiAltManager.clicktimer.reset();
        }
        if (this.isHoveringDelete(x, y)) {
            Thunderhack.alts.remove(this.name);
            GuiAltManager.clicktimer.reset();
        }
    }
    
    public void renderCustomTexture(final double x, final double y, final int width, final int height, final float tileWidth, final float tileHeight) {
        if (this.head != null) {
            Util.mc.getTextureManager().bindTexture(this.head);
        }
        else {
            Util.mc.getTextureManager().bindTexture(this.crackedSkin);
        }
        GL11.glEnable(3042);
        Gui.drawScaledCustomSizeModalRect((int)x, (int)y, 0.0f, 0.0f, 16, 16, width, height, tileWidth, tileHeight);
        GL11.glDisable(3042);
    }
}
