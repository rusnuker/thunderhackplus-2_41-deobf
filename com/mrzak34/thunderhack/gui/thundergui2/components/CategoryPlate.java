//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2.components;

import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.math.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;

public class CategoryPlate
{
    float category_animation;
    private final Module.Category cat;
    private int posX;
    private int posY;
    private final ScaledResolution sr;
    
    public CategoryPlate(final Module.Category cat, final int posX, final int posY) {
        this.category_animation = 0.0f;
        this.cat = cat;
        this.posX = posX;
        this.posY = posY;
        this.sr = new ScaledResolution(Util.mc);
    }
    
    public static double deltaTime() {
        return (Minecraft.getDebugFPS() > 0) ? (1.0 / Minecraft.getDebugFPS()) : 1.0;
    }
    
    public static float fast(final float end, final float start, final float multiple) {
        return (1.0f - MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f) * start;
    }
    
    public void render(final int MouseX, final int MouseY) {
        this.category_animation = fast(this.category_animation, this.isHovered(MouseX, MouseY) ? 1.0f : 0.0f, 15.0f);
        if (this.isHovered(MouseX, MouseY)) {
            RoundedShader.drawRound((float)this.posX, (float)this.posY, 84.0f, 15.0f, 2.0f, new Color(25, 20, 30, (int)MathUtil.clamp(65.0f * this.category_animation, 0.0f, 255.0f)));
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect((double)(this.posX - 1), (double)(this.posY - 1), 85.5, 16.5, 4.0, new Color(0, 0, 0, 255));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(MouseX - 20), (float)(MouseY - 20), 40.0f, 40.0f, 60, new Color(-1017816450, true));
            Stencil.dispose();
            GL11.glPopMatrix();
        }
        FontRender.drawString6(this.cat.getName(), (float)(this.posX + 5), (float)(this.posY + 6), -1, false);
    }
    
    public void movePosition(final float deltaX, final float deltaY) {
        this.posY += (int)deltaY;
        this.posX += (int)deltaX;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        if (this.isHovered(mouseX, mouseY)) {
            ThunderGui2.getInstance().new_category = this.cat;
            if (ThunderGui2.getInstance().current_category == null) {
                ThunderGui2.getInstance().current_category = Module.Category.HUD;
                ThunderGui2.getInstance().new_category = this.cat;
            }
        }
    }
    
    private boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.posX && mouseX < this.posX + 84 && mouseY > this.posY && mouseY < this.posY + 15;
    }
    
    public Module.Category getCategory() {
        return this.cat;
    }
    
    public int getPosY() {
        return this.posY;
    }
}
