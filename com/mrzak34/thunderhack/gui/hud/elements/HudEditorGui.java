//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.hud.elements;

import com.mrzak34.thunderhack.gui.clickui.window.*;
import com.google.common.collect.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import com.mrzak34.thunderhack.notification.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.io.*;

public class HudEditorGui extends GuiScreen
{
    private static HudEditorGui INSTANCE;
    private final List<ModuleWindow> windows;
    private Animation openAnimation;
    private Animation bgAnimation;
    private Animation rAnimation;
    private double scrollSpeed;
    private boolean firstOpen;
    private double dWheel;
    public static boolean mouse_state;
    public static int mouse_x;
    public static int mouse_y;
    
    public HudEditorGui() {
        this.windows = (List<ModuleWindow>)Lists.newArrayList();
        this.firstOpen = true;
        this.setInstance();
    }
    
    public static HudEditorGui getInstance() {
        if (HudEditorGui.INSTANCE == null) {
            HudEditorGui.INSTANCE = new HudEditorGui();
        }
        return HudEditorGui.INSTANCE;
    }
    
    public static HudEditorGui getHudGui() {
        return getInstance();
    }
    
    private void setInstance() {
        HudEditorGui.INSTANCE = this;
    }
    
    public void initGui() {
        this.openAnimation = (Animation)new EaseBackIn(270, 0.4000000059604645, 1.13f);
        this.rAnimation = new DecelerateAnimation(300, 1.0);
        this.bgAnimation = new DecelerateAnimation(300, 1.0);
        if (this.firstOpen) {
            final double x = 20.0;
            final double y = 20.0;
            double offset = 0.0;
            final int windowHeight = 18;
            final ScaledResolution sr = new ScaledResolution(this.mc);
            int i = 0;
            for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
                if (!category.getName().contains("HUD")) {
                    continue;
                }
                final ModuleWindow window = new ModuleWindow(category.getName(), (List)Thunderhack.moduleManager.getModulesByCategory(category), i, x + offset, y, 108.0, (double)windowHeight);
                window.setOpen(true);
                this.windows.add(window);
                offset += 110.0;
                if (offset > sr.getScaledWidth()) {
                    offset = 0.0;
                }
                ++i;
            }
            this.firstOpen = false;
        }
        this.windows.forEach(ModuleWindow::init);
        super.initGui();
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float delta) {
        if (this.openAnimation.isDone() && this.openAnimation.getDirection().equals(Direction.BACKWARDS)) {
            this.windows.forEach(ModuleWindow::onClose);
            this.mc.currentScreen = null;
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        this.dWheel = Mouse.getDWheel();
        HudEditorGui.mouse_x = mouseX;
        HudEditorGui.mouse_y = mouseY;
        if (this.dWheel > 0.0) {
            this.scrollSpeed += 14.0;
        }
        else if (this.dWheel < 0.0) {
            this.scrollSpeed -= 14.0;
        }
        final double anim = this.openAnimation.getOutput() + 0.6000000238418579;
        GlStateManager.pushMatrix();
        final double centerX = this.width >> 1;
        final double centerY = this.height >> 1;
        GlStateManager.translate(centerX, centerY, 0.0);
        GlStateManager.scale(anim, anim, 1.0);
        GlStateManager.translate(-centerX, -centerY, 0.0);
        for (final ModuleWindow window : this.windows) {
            if (Keyboard.isKeyDown(208)) {
                window.setY(window.getY() + 2.0);
            }
            else if (Keyboard.isKeyDown(200)) {
                window.setY(window.getY() - 2.0);
            }
            else if (Keyboard.isKeyDown(203)) {
                window.setX(window.getX() - 2.0);
            }
            else if (Keyboard.isKeyDown(205)) {
                window.setX(window.getX() + 2.0);
            }
            if (this.dWheel != 0.0) {
                window.setY(window.getY() + this.scrollSpeed);
            }
            else {
                this.scrollSpeed = 0.0;
            }
            window.render(mouseX, mouseY, delta, ClickGui.getInstance().hcolor1.getValue().getColorObject(), this.openAnimation.isDone() && this.openAnimation.getDirection() == Direction.FORWARDS);
        }
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, delta);
    }
    
    public void onGuiClosed() {
    }
    
    public void updateScreen() {
        this.windows.forEach(ModuleWindow::tick);
        super.updateScreen();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        this.windows.forEach(w -> {
            w.mouseClicked(mouseX, mouseY, button);
            this.windows.forEach(w1 -> {
                if (w.dragging && w != w1) {
                    w1.dragging = false;
                }
            });
            return;
        });
        HudEditorGui.mouse_state = true;
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.windows.forEach(w -> w.mouseReleased(mouseX, mouseY, button));
        HudEditorGui.mouse_state = false;
        super.mouseReleased(mouseX, mouseY, button);
    }
    
    public void handleMouseInput() throws IOException {
        this.windows.forEach(w -> {
            try {
                w.handleMouseInput();
            }
            catch (IOException ex) {}
            return;
        });
        super.handleMouseInput();
    }
    
    public void keyTyped(final char chr, final int keyCode) throws IOException {
        this.windows.forEach(w -> w.keyTyped(chr, keyCode));
        if (keyCode == 1 || keyCode == Thunderhack.moduleManager.getModuleByClass(ClickGui.class).getBind().getKey()) {
            this.bgAnimation.setDirection(Direction.BACKWARDS);
            this.rAnimation.setDirection(Direction.BACKWARDS);
            this.openAnimation.setDirection(Direction.BACKWARDS);
        }
    }
    
    static {
        HudEditorGui.INSTANCE = new HudEditorGui();
    }
}
