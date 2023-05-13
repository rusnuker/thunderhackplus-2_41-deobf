//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import java.awt.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import com.mrzak34.thunderhack.setting.*;

public class ModulePlate
{
    float scroll_animation;
    private final Module module;
    private int posX;
    private int posY;
    private float scrollPosY;
    private float prevPosY;
    private int progress;
    private final ScaledResolution sr;
    private int fade;
    private final int index;
    private boolean first_open;
    private boolean listening_bind;
    
    public ModulePlate(final Module module, final int posX, final int posY, final int index) {
        this.scroll_animation = 0.0f;
        this.first_open = true;
        this.listening_bind = false;
        this.module = module;
        this.posX = posX;
        this.posY = posY;
        this.sr = new ScaledResolution(Util.mc);
        this.fade = 0;
        this.index = index * 5;
        this.scrollPosY = (float)posY;
        this.scroll_animation = 0.0f;
    }
    
    public void render(final int MouseX, final int MouseY) {
        if (this.scrollPosY != this.posY) {
            this.scroll_animation = ThunderGui2.fast(this.scroll_animation, 1.0f, 15.0f);
            this.posY = (int)RenderUtil.interpolate(this.scrollPosY, this.prevPosY, this.scroll_animation);
        }
        if (this.posY > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.posY < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (this.module.isOn()) {
            RoundedShader.drawGradientRound((float)this.posX, (float)this.posY, 90.0f, 30.0f, 4.0f, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), this.getFadeFactor()), ColorUtil.applyOpacity(new Color(25, 20, 30, 255), this.getFadeFactor()), ColorUtil.applyOpacity(ThunderHackGui.getInstance().onColor1.getValue().getColorObject(), this.getFadeFactor()), ColorUtil.applyOpacity(ThunderHackGui.getInstance().onColor2.getValue().getColorObject(), this.getFadeFactor()));
        }
        else {
            RoundedShader.drawRound((float)this.posX, (float)this.posY, 90.0f, 30.0f, 4.0f, ColorUtil.applyOpacity(new Color(25, 20, 30, 255), this.getFadeFactor()));
        }
        if (this.first_open) {
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect(this.posX - 0.5, this.posY - 0.5, 91.0, 31.0, 8.0, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), this.getFadeFactor()));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(MouseX - 20), (float)(MouseY - 20), 40.0f, 40.0f, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), this.getFadeFactor()));
            Stencil.dispose();
            GL11.glPopMatrix();
            this.first_open = false;
        }
        if (this.isHovered(MouseX, MouseY)) {
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect(this.posX - 0.5, this.posY - 0.5, 91.0, 31.0, 8.0, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), this.getFadeFactor()));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(MouseX - 20), (float)(MouseY - 20), 40.0f, 40.0f, 60, ColorUtil.applyOpacity(new Color(-1017816450, true), this.getFadeFactor()));
            Stencil.dispose();
            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
        Stencil.write(false);
        Particles.roundedRect(this.posX - 0.5, this.posY - 0.5, 91.0, 31.0, 8.0, ColorUtil.applyOpacity(new Color(0, 0, 0, 255), this.getFadeFactor()));
        Stencil.erase(true);
        if (ThunderGui2.selected_plate != this) {
            FontRender.drawIcon("H", (int)(this.posX + 80.0f), (int)(this.posY + 22.0f), ColorUtil.applyOpacity(new Color(-1250068, true).getRGB(), this.getFadeFactor()));
        }
        else {
            String gear = "H";
            switch (this.progress) {
                case 0: {
                    gear = "H";
                    break;
                }
                case 1: {
                    gear = "N";
                    break;
                }
                case 2: {
                    gear = "O";
                    break;
                }
                case 3: {
                    gear = "P";
                    break;
                }
                case 4: {
                    gear = "Q";
                    break;
                }
            }
            FontRender.drawBigIcon(gear, (int)(this.posX + 80.0f), (int)(this.posY + 5.0f), ColorUtil.applyOpacity(new Color(-10197916, true).getRGB(), this.getFadeFactor()));
        }
        Stencil.dispose();
        GL11.glPopMatrix();
        FontRender.drawString6(this.module.getName(), (float)(this.posX + 5), (float)(this.posY + 5), ColorUtil.applyOpacity(-1, this.getFadeFactor()), false);
        if (this.listening_bind) {
            FontRender.drawString6("...", (float)(this.posX + 85 - FontRender.getStringWidth6(this.module.getBind().toString())), (float)(this.posY + 5), ColorUtil.applyOpacity(new Color(11579568), this.getFadeFactor()).getRGB(), false);
        }
        else if (!Objects.equals(this.module.getBind().toString(), "None")) {
            FontRender.drawString6(this.module.getBind().toString(), (float)(this.posX + 85 - FontRender.getStringWidth6(this.module.getBind().toString())), (float)(this.posY + 5), ColorUtil.applyOpacity(new Color(11579568), this.getFadeFactor()).getRGB(), false);
        }
        final String[] splitString = this.module.getDescription().split("-");
        if (splitString[0] != null && !splitString[0].equals("")) {
            FontRender.drawString7(splitString[0], (float)(this.posX + 5), (float)(this.posY + 13), ColorUtil.applyOpacity(new Color(-4342339, true).getRGB(), this.getFadeFactor()), false);
        }
        if (splitString.length > 1 && splitString[1] != null && !splitString[1].equals("")) {
            FontRender.drawString7(splitString[1], (float)(this.posX + 5), (float)(this.posY + 18), ColorUtil.applyOpacity(new Color(-4342339, true).getRGB(), this.getFadeFactor()), false);
        }
        if (splitString.length == 3 && splitString[2] != null && !splitString[2].equals("")) {
            FontRender.drawString7(splitString[2], (float)(this.posX + 5), (float)(this.posY + 23), ColorUtil.applyOpacity(new Color(-4342339, true).getRGB(), this.getFadeFactor()), false);
        }
    }
    
    private float getFadeFactor() {
        return this.fade / (5.0f + this.index);
    }
    
    public void onTick() {
        if (this.progress > 4) {
            this.progress = 0;
        }
        ++this.progress;
        if (this.fade < 10 + this.index) {
            ++this.fade;
        }
    }
    
    private boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.posX && mouseX < this.posX + 90 && mouseY > this.posY && mouseY < this.posY + 30;
    }
    
    public void movePosition(final float deltaX, final float deltaY) {
        this.posY += (int)deltaY;
        this.posX += (int)deltaX;
        this.scrollPosY = (float)this.posY;
    }
    
    public void scrollElement(final float deltaY) {
        this.scroll_animation = 0.0f;
        this.prevPosY = (float)this.posY;
        this.scrollPosY += deltaY;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.posY > ThunderGui2.getInstance().main_posY + ThunderGui2.getInstance().height || this.posY < ThunderGui2.getInstance().main_posY) {
            return;
        }
        if (mouseX > this.posX && mouseX < this.posX + 90 && mouseY > this.posY && mouseY < this.posY + 30) {
            switch (clickedButton) {
                case 0: {
                    this.module.toggle();
                    break;
                }
                case 1: {
                    ThunderGui2.selected_plate = this;
                    break;
                }
                case 2: {
                    this.listening_bind = !this.listening_bind;
                    break;
                }
            }
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        if (this.listening_bind) {
            Bind bind = new Bind(keyCode);
            if (bind.toString().equalsIgnoreCase("Escape")) {
                return;
            }
            if (bind.toString().equalsIgnoreCase("Delete")) {
                bind = new Bind(-1);
            }
            this.module.bind.setValue(bind);
            this.listening_bind = false;
        }
    }
    
    public double getPosX() {
        return this.posX;
    }
    
    public double getPosY() {
        return this.posY;
    }
    
    public Module getModule() {
        return this.module;
    }
}
