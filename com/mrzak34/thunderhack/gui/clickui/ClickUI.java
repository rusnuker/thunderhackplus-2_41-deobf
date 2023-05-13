//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui;

import com.mrzak34.thunderhack.gui.clickui.window.*;
import com.google.common.collect.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.*;
import java.util.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.client.*;
import java.io.*;

public class ClickUI extends GuiScreen
{
    private static ClickUI INSTANCE;
    private final List<ModuleWindow> windows;
    private double scrollSpeed;
    private boolean firstOpen;
    
    public ClickUI() {
        this.windows = (List<ModuleWindow>)Lists.newArrayList();
        this.firstOpen = true;
        this.setInstance();
    }
    
    public static ClickUI getInstance() {
        if (ClickUI.INSTANCE == null) {
            ClickUI.INSTANCE = new ClickUI();
        }
        return ClickUI.INSTANCE;
    }
    
    public static ClickUI getClickGui() {
        return getInstance();
    }
    
    private void setInstance() {
        ClickUI.INSTANCE = this;
    }
    
    public void initGui() {
        if (this.firstOpen) {
            final double x = 20.0;
            final double y = 20.0;
            double offset = 0.0;
            final int windowHeight = 18;
            final ScaledResolution sr = new ScaledResolution(this.mc);
            int i = 0;
            for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
                if (category.getName().contains("HUD")) {
                    continue;
                }
                final ModuleWindow window = new ModuleWindow(category.getName(), Thunderhack.moduleManager.getModulesByCategory(category), i, x + offset, y, 108.0, windowHeight);
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
        final double dWheel = Mouse.getDWheel();
        if (dWheel > 0.0) {
            this.scrollSpeed += 14.0;
        }
        else if (dWheel < 0.0) {
            this.scrollSpeed -= 14.0;
        }
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
            if (dWheel != 0.0) {
                window.setY(window.getY() + this.scrollSpeed);
            }
            else {
                this.scrollSpeed = 0.0;
            }
            window.render(mouseX, mouseY, delta, ClickGui.getInstance().hcolor1.getValue().getColorObject(), true);
        }
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
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.windows.forEach(w -> w.mouseReleased(mouseX, mouseY, button));
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
            this.mc.currentScreen = null;
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    static {
        ClickUI.INSTANCE = new ClickUI();
    }
}
