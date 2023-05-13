//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.thundergui2;

import com.mrzak34.thunderhack.util.shaders.*;
import java.util.concurrent.*;
import com.mrzak34.thunderhack.modules.*;
import net.minecraft.client.*;
import com.mrzak34.thunderhack.util.math.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.manager.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import org.lwjgl.opengl.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.thundergui2.components.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import java.awt.*;
import com.mrzak34.thunderhack.command.*;
import net.minecraft.util.*;
import java.io.*;
import org.lwjgl.input.*;

public class ThunderGui2 extends GuiScreen
{
    public static CurrentMode currentMode;
    public static boolean scroll_lock;
    public static ModulePlate selected_plate;
    public static ModulePlate prev_selected_plate;
    public static BetterAnimation open_animation;
    public static boolean open_direction;
    private static ThunderGui2 INSTANCE;
    public final ArrayList<ModulePlate> components;
    public final CopyOnWriteArrayList<CategoryPlate> categories;
    public final ArrayList<SettingElement> settings;
    public final CopyOnWriteArrayList<ConfigComponent> configs;
    public final CopyOnWriteArrayList<FriendComponent> friends;
    private final int main_width = 400;
    public int main_posX;
    public int main_posY;
    public Module.Category current_category;
    public Module.Category new_category;
    float category_animation;
    float settings_animation;
    float manager_animation;
    int prevCategoryY;
    int CategoryY;
    int slider_y;
    int slider_x;
    private int main_height;
    private boolean dragging;
    private boolean rescale;
    private int drag_x;
    private int drag_y;
    private int rescale_y;
    private float scroll;
    private boolean first_open;
    private boolean searching;
    private boolean listening_friend;
    private boolean listening_config;
    private String search_string;
    private String config_string;
    private String friend_string;
    private CurrentMode prevMode;
    public static boolean mouse_state;
    public static int mouse_x;
    public static int mouse_y;
    
    public ThunderGui2() {
        this.components = new ArrayList<ModulePlate>();
        this.categories = new CopyOnWriteArrayList<CategoryPlate>();
        this.settings = new ArrayList<SettingElement>();
        this.configs = new CopyOnWriteArrayList<ConfigComponent>();
        this.friends = new CopyOnWriteArrayList<FriendComponent>();
        this.main_posX = 100;
        this.main_posY = 100;
        this.current_category = Module.Category.COMBAT;
        this.new_category = Module.Category.COMBAT;
        this.category_animation = 0.0f;
        this.settings_animation = 0.0f;
        this.manager_animation = 0.0f;
        this.main_height = 250;
        this.dragging = false;
        this.rescale = false;
        this.drag_x = 0;
        this.drag_y = 0;
        this.rescale_y = 0;
        this.scroll = 0.0f;
        this.first_open = true;
        this.searching = false;
        this.listening_friend = false;
        this.listening_config = false;
        this.search_string = "Search";
        this.config_string = "Save config";
        this.friend_string = "Add friend";
        this.prevMode = CurrentMode.Modules;
        this.setInstance();
        this.load();
        this.CategoryY = this.getCategoryY(this.new_category);
    }
    
    public static ThunderGui2 getInstance() {
        if (ThunderGui2.INSTANCE == null) {
            ThunderGui2.INSTANCE = new ThunderGui2();
        }
        return ThunderGui2.INSTANCE;
    }
    
    public static ThunderGui2 getThunderGui() {
        ThunderGui2.open_animation = new BetterAnimation();
        ThunderGui2.open_direction = true;
        return getInstance();
    }
    
    public static String removeLastChar(final String str) {
        String output = "";
        if (str != null && str.length() > 0) {
            output = str.substring(0, str.length() - 1);
        }
        return output;
    }
    
    public static double deltaTime() {
        return (Minecraft.getDebugFPS() > 0) ? (1.0 / Minecraft.getDebugFPS()) : 1.0;
    }
    
    public static float fast(final float end, final float start, final float multiple) {
        return (1.0f - MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f)) * end + MathUtil.clamp((float)(deltaTime() * multiple), 0.0f, 1.0f) * start;
    }
    
    private void setInstance() {
        ThunderGui2.INSTANCE = this;
    }
    
    public void load() {
        this.categories.clear();
        this.components.clear();
        this.configs.clear();
        this.friends.clear();
        int module_y = 0;
        for (final Module module : Thunderhack.moduleManager.getModulesByCategory(this.current_category)) {
            this.components.add(new ModulePlate(module, this.main_posX + 100, this.main_posY + 40 + module_y, module_y / 35));
            module_y += 35;
        }
        int category_y = 0;
        for (final Module.Category category : Thunderhack.moduleManager.getCategories()) {
            this.categories.add(new CategoryPlate(category, this.main_posX + 8, this.main_posY + 43 + category_y));
            category_y += 17;
        }
    }
    
    public void loadConfigs() {
        this.friends.clear();
        this.configs.clear();
        int config_y;
        final Iterator<String> iterator;
        String file1;
        new Thread(() -> {
            config_y = 3;
            Objects.requireNonNull(ConfigManager.getConfigList()).iterator();
            while (iterator.hasNext()) {
                file1 = iterator.next();
                this.configs.add(new ConfigComponent(file1, ConfigManager.getConfigDate(file1), this.main_posX + 100, this.main_posY + 40 + config_y, config_y / 35));
                config_y += 35;
            }
        }).start();
    }
    
    public void loadFriends() {
        this.configs.clear();
        this.friends.clear();
        int friend_y = 3;
        for (final String friend : Thunderhack.friendManager.getFriends()) {
            this.friends.add(new FriendComponent(friend, this.main_posX + 100, this.main_posY + 40 + friend_y, friend_y / 35));
            friend_y += 35;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        ThunderGui2.mouse_x = mouseX;
        ThunderGui2.mouse_y = mouseY;
        TargetHud.sizeAnimation((double)(this.main_posX + 200.0f), (double)(this.main_posY + this.main_height / 2.0f), ThunderGui2.open_animation.getAnimationd());
        if (ThunderGui2.open_animation.getAnimationd() > 0.0) {
            this.renderGui(mouseX, mouseY, partialTicks);
        }
        if (ThunderGui2.open_animation.getAnimationd() <= 0.01 && !ThunderGui2.open_direction) {
            ThunderGui2.open_animation = new BetterAnimation();
            ThunderGui2.open_direction = false;
            this.mc.currentScreen = null;
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        GlStateManager.popMatrix();
    }
    
    public void renderGui(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (this.dragging) {
            final float deltaX = (float)(mouseX - this.drag_x - this.main_posX);
            final float deltaY = (float)(mouseY - this.drag_y - this.main_posY);
            this.main_posX = mouseX - this.drag_x;
            this.main_posY = mouseY - this.drag_y;
            this.slider_y += (int)deltaY;
            this.slider_x += (int)deltaX;
            this.configs.forEach(configComponent -> configComponent.movePosition(deltaX, deltaY));
            this.friends.forEach(friendComponent -> friendComponent.movePosition(deltaX, deltaY));
            this.components.forEach(component -> component.movePosition(deltaX, deltaY));
            this.categories.forEach(category -> category.movePosition(deltaX, deltaY));
        }
        if (this.rescale) {
            final float deltaY2 = (float)(mouseY - this.rescale_y - this.main_height);
            if (this.main_height + deltaY2 > 250.0f) {
                this.main_height += (int)deltaY2;
            }
        }
        if (this.current_category != null && this.current_category != this.new_category) {
            this.prevCategoryY = this.getCategoryY(this.current_category);
            this.CategoryY = this.getCategoryY(this.new_category);
            this.current_category = this.new_category;
            this.category_animation = 0.0f;
            this.slider_y = 0;
            this.search_string = "Search";
            this.config_string = "Save config";
            this.friend_string = "Add friend";
            ThunderGui2.currentMode = CurrentMode.Modules;
            this.load();
        }
        this.manager_animation = fast(this.manager_animation, 1.0f, 15.0f);
        this.category_animation = fast(this.category_animation, 1.0f, 15.0f);
        this.checkMouseWheel(mouseX, mouseY);
        RoundedShader.drawRound((float)this.main_posX, (float)this.main_posY, 400.0f, (float)this.main_height, 9.0f, ThunderHackGui.getInstance().getColorByTheme(0));
        RoundedShader.drawRound((float)(this.main_posX + 5), (float)(this.main_posY + 5), 90.0f, 30.0f, 7.0f, ThunderHackGui.getInstance().getColorByTheme(1));
        FontRender.drawString2("THUNDERHACK+", this.main_posX + 10, this.main_posY + 15, ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
        FontRender.drawString5("v2.41", (float)(this.main_posX + 75), (float)(this.main_posY + 30), ThunderHackGui.getInstance().getColorByTheme(3).getRGB());
        RoundedShader.drawRound((float)(this.main_posX + 5), (float)(this.main_posY + 40), 90.0f, 140.0f, 7.0f, ThunderHackGui.getInstance().getColorByTheme(4));
        if (ThunderGui2.currentMode == CurrentMode.Modules) {
            RoundedShader.drawRound((float)(this.main_posX + 20), (float)(this.main_posY + 195), 60.0f, 20.0f, 4.0f, ThunderHackGui.getInstance().getColorByTheme(4));
        }
        else if (ThunderGui2.currentMode == CurrentMode.ConfigManager) {
            RoundedShader.drawGradientRound((float)(this.main_posX + 20), (float)(this.main_posY + 195), 60.0f, 20.0f, 4.0f, ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(4));
        }
        else {
            RoundedShader.drawGradientRound((float)(this.main_posX + 20), (float)(this.main_posY + 195), 60.0f, 20.0f, 4.0f, ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(4), ThunderHackGui.getInstance().getColorByTheme(5), ThunderHackGui.getInstance().getColorByTheme(5));
        }
        RoundedShader.drawRound(this.main_posX + 49.5f, (float)(this.main_posY + 197), 1.0f, 16.0f, 0.5f, ThunderHackGui.getInstance().getColorByTheme(6));
        FontRender.drawMidIcon("u", (float)(this.main_posX + 20), (float)(this.main_posY + 195), (ThunderGui2.currentMode == CurrentMode.ConfigManager) ? ThunderHackGui.getInstance().getColorByTheme(2).getRGB() : new Color(9276813).getRGB());
        FontRender.drawMidIcon("v", (float)(this.main_posX + 54), (float)(this.main_posY + 196), (ThunderGui2.currentMode == CurrentMode.FriendManager) ? ThunderHackGui.getInstance().getColorByTheme(2).getRGB() : new Color(9276813).getRGB());
        if (this.isHoveringItem((float)(this.main_posX + 20), (float)(this.main_posY + 195), 60.0f, 20.0f, (float)mouseX, (float)mouseY)) {
            RoundedShader.drawRound((float)(this.main_posX + 20), (float)(this.main_posY + 195), 60.0f, 20.0f, 4.0f, new Color(76, 56, 93, 31));
            GL11.glPushMatrix();
            Stencil.write(false);
            Particles.roundedRect((double)(this.main_posX + 20), (double)(this.main_posY + 195), 61.0, 21.0, 8.0, new Color(0, 0, 0, 255));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(mouseX - 20), (float)(mouseY - 20), 40.0f, 40.0f, 60, new Color(-1017816450, true));
            Stencil.dispose();
            GL11.glPopMatrix();
        }
        if (this.first_open) {
            this.category_animation = 1.0f;
            RoundedShader.drawRound((float)(this.main_posX + 8), this.CategoryY + (float)this.slider_y, 84.0f, 15.0f, 2.0f, ThunderHackGui.getInstance().getColorByTheme(7));
            this.first_open = false;
        }
        else if (ThunderGui2.currentMode == CurrentMode.Modules) {
            RoundedShader.drawRound((float)(this.main_posX + 8), (float)RenderUtil.interpolate(this.CategoryY, this.prevCategoryY, this.category_animation) + this.slider_y, 84.0f, 15.0f, 2.0f, ThunderHackGui.getInstance().getColorByTheme(7));
        }
        if (ThunderGui2.selected_plate != ThunderGui2.prev_selected_plate) {
            ThunderGui2.prev_selected_plate = ThunderGui2.selected_plate;
            this.settings_animation = 0.0f;
            this.settings.clear();
            this.scroll = 0.0f;
            if (ThunderGui2.selected_plate != null) {
                for (final Setting<?> setting : ThunderGui2.selected_plate.getModule().getSettings()) {
                    if (setting.getValue() instanceof Parent) {
                        this.settings.add((SettingElement)new ParentComponent((Setting)setting));
                    }
                    if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled") && !setting.getName().equals("Drawn")) {
                        this.settings.add((SettingElement)new BooleanComponent((Setting)setting));
                    }
                    if (setting.isEnumSetting()) {
                        this.settings.add((SettingElement)new ModeComponent((Setting)setting));
                    }
                    if (setting.isColorSetting()) {
                        this.settings.add((SettingElement)new ColorPickerComponent((Setting)setting));
                    }
                    if (setting.isNumberSetting() && setting.hasRestriction()) {
                        this.settings.add((SettingElement)new SliderComponent((Setting)setting));
                    }
                }
            }
        }
        this.settings_animation = fast(this.settings_animation, 1.0f, 15.0f);
        if (ThunderGui2.currentMode != this.prevMode) {
            if (this.prevMode != CurrentMode.ConfigManager) {
                this.manager_animation = 0.0f;
                if (ThunderGui2.currentMode == CurrentMode.ConfigManager) {
                    this.loadConfigs();
                }
            }
            if (this.prevMode != CurrentMode.FriendManager) {
                this.manager_animation = 0.0f;
                if (ThunderGui2.currentMode == CurrentMode.FriendManager) {
                    this.loadFriends();
                }
            }
            this.prevMode = ThunderGui2.currentMode;
        }
        if (ThunderGui2.selected_plate != null && ThunderGui2.currentMode == CurrentMode.Modules) {
            RoundedShader.drawRound((float)RenderUtil.interpolate(this.main_posX + 200, ThunderGui2.selected_plate.getPosX(), this.settings_animation), (float)RenderUtil.interpolate(this.main_posY + 40, ThunderGui2.selected_plate.getPosY(), this.settings_animation), (float)RenderUtil.interpolate(195.0, 90.0, this.settings_animation), (float)RenderUtil.interpolate(this.main_height - 45, 30.0, this.settings_animation), 4.0f, ThunderHackGui.getInstance().getColorByTheme(7));
        }
        if (ThunderGui2.currentMode != CurrentMode.Modules) {
            this.searching = false;
            RenderUtil.glScissor((float)RenderUtil.interpolate(this.main_posX + 80, this.main_posX + 200, this.manager_animation), (float)(this.main_posY + 39), (float)RenderUtil.interpolate(399.0, 195.0, this.manager_animation) + this.main_posX + 36.0f, this.main_height + (float)this.main_posY - 3.0f, sr, ThunderGui2.open_animation.getAnimationd());
            GL11.glEnable(3089);
            RoundedShader.drawRound((float)(this.main_posX + 100), this.main_posY + 40.0f, 295.0f, this.main_height - 44.0f, 4.0f, ThunderHackGui.getInstance().getColorByTheme(7));
            this.configs.forEach(components -> components.render(mouseX, mouseY));
            this.friends.forEach(components -> components.render(mouseX, mouseY));
            RenderUtil.draw2DGradientRect((float)(this.main_posX + 102), (float)(this.main_posY + 34), (float)(this.main_posX + 393), (float)(this.main_posY + 60), new Color(25, 20, 30, 0).getRGB(), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), new Color(25, 20, 30, 0).getRGB(), new Color(37, 27, 41, 245).getRGB());
            RenderUtil.draw2DGradientRect((float)(this.main_posX + 102), (float)(this.main_posY + this.main_height - 35), (float)(this.main_posX + 393), (float)(this.main_posY + this.main_height), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), new Color(25, 20, 30, 0).getRGB(), ThunderHackGui.getInstance().getColorByTheme(7).getRGB(), new Color(37, 27, 41, 0).getRGB());
            GL11.glDisable(3089);
        }
        RenderUtil.glScissor((float)(this.main_posX + 79), (float)(this.main_posY + 35), (float)(this.main_posX + 396 + 40), (float)(this.main_posY + this.main_height), sr, ThunderGui2.open_animation.getAnimationd());
        GL11.glEnable(3089);
        this.components.forEach(components -> components.render(mouseX, mouseY));
        GL11.glDisable(3089);
        this.categories.forEach(category -> category.render(mouseX, mouseY));
        if (ThunderGui2.currentMode == CurrentMode.Modules) {
            RenderUtil.draw2DGradientRect((float)(this.main_posX + 98), (float)(this.main_posY + 34), (float)(this.main_posX + 191), (float)(this.main_posY + 50), new Color(37, 27, 41, 0).getRGB(), new Color(37, 27, 41, 245).getRGB(), new Color(37, 27, 41, 0).getRGB(), new Color(37, 27, 41, 245).getRGB());
            RenderUtil.draw2DGradientRect((float)(this.main_posX + 98), (float)(this.main_posY + this.main_height - 15), (float)(this.main_posX + 191), (float)(this.main_posY + this.main_height), new Color(37, 27, 41, 245).getRGB(), new Color(37, 27, 41, 0).getRGB(), new Color(37, 27, 41, 245).getRGB(), new Color(37, 27, 41, 0).getRGB());
        }
        RoundedShader.drawRound((float)(this.main_posX + 100), (float)(this.main_posY + 5), 295.0f, 30.0f, 7.0f, new Color(25, 20, 30, 250));
        if (this.isHoveringItem((float)(this.main_posX + 105), (float)(this.main_posY + 14), 11.0f, 11.0f, (float)mouseX, (float)mouseY)) {
            RoundedShader.drawRound((float)(this.main_posX + 105), (float)(this.main_posY + 14), 11.0f, 11.0f, 3.0f, new Color(68, 49, 75, 250));
        }
        else {
            RoundedShader.drawRound((float)(this.main_posX + 105), (float)(this.main_posY + 14), 11.0f, 11.0f, 3.0f, new Color(52, 38, 58, 250));
        }
        FontRender.drawString6("current cfg: " + ConfigManager.currentConfig.getName(), (float)(this.main_posX + 120), (float)(this.main_posY + 18), new Color(-838860801, true).getRGB(), false);
        FontRender.drawIcon("t", this.main_posX + 106, this.main_posY + 17, new Color(-1023410177, true).getRGB());
        RoundedShader.drawRound((float)(this.main_posX + 250), (float)(this.main_posY + 15), 140.0f, 10.0f, 3.0f, new Color(52, 38, 58, 250));
        if (ThunderGui2.currentMode == CurrentMode.Modules) {
            FontRender.drawIcon("s", this.main_posX + 378, this.main_posY + 18, this.searching ? new Color(-872415233, true).getRGB() : new Color(-2080374785, true).getRGB());
        }
        if (this.isHoveringItem((float)(this.main_posX + 250), (float)(this.main_posY + 15), 140.0f, 20.0f, (float)mouseX, (float)mouseY)) {
            GL11.glPushMatrix();
            RoundedShader.drawRound((float)(this.main_posX + 250), (float)(this.main_posY + 15), 140.0f, 10.0f, 3.0f, new Color(84, 63, 94, 36));
            Stencil.write(false);
            Particles.roundedRect((double)(this.main_posX + 250), (double)(this.main_posY + 15), 140.0, 10.0, 6.0, new Color(0, 0, 0, 255));
            Stencil.erase(true);
            RenderUtil.drawBlurredShadow((float)(mouseX - 20), (float)(mouseY - 20), 40.0f, 40.0f, 60, new Color(-1017816450, true));
            Stencil.dispose();
            GL11.glPopMatrix();
        }
        if (ThunderGui2.currentMode == CurrentMode.Modules) {
            FontRender.drawString6(this.search_string, (float)(this.main_posX + 252), (float)(this.main_posY + 18), this.searching ? new Color(-872415233, true).getRGB() : new Color(-2080374785, true).getRGB(), false);
        }
        if (ThunderGui2.currentMode == CurrentMode.ConfigManager) {
            FontRender.drawString6(this.config_string, (float)(this.main_posX + 252), (float)(this.main_posY + 18), this.listening_config ? new Color(-872415233, true).getRGB() : new Color(-2080374785, true).getRGB(), false);
            RoundedShader.drawRound((float)(this.main_posX + 368), (float)(this.main_posY + 17), 20.0f, 6.0f, 1.0f, this.isHoveringItem((float)(this.main_posX + 368), (float)(this.main_posY + 17), 20.0f, 6.0f, (float)mouseX, (float)mouseY) ? new Color(59, 42, 63, 194) : new Color(33, 23, 35, 194));
            FontRender.drawCentString6("+", (float)(this.main_posX + 378), (float)(this.main_posY + 19), ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
        }
        if (ThunderGui2.currentMode == CurrentMode.FriendManager) {
            FontRender.drawString6(this.friend_string, (float)(this.main_posX + 252), (float)(this.main_posY + 18), this.listening_friend ? new Color(-872415233, true).getRGB() : new Color(-2080374785, true).getRGB(), false);
            RoundedShader.drawRound((float)(this.main_posX + 368), (float)(this.main_posY + 17), 20.0f, 6.0f, 1.0f, this.isHoveringItem((float)(this.main_posX + 368), (float)(this.main_posY + 17), 20.0f, 6.0f, (float)mouseX, (float)mouseY) ? new Color(59, 42, 63, 194) : new Color(33, 23, 35, 194));
            FontRender.drawCentString6("+", (float)(this.main_posX + 378), (float)(this.main_posY + 19), ThunderHackGui.getInstance().getColorByTheme(2).getRGB());
        }
        if (ThunderGui2.selected_plate == null) {
            return;
        }
        final float scissorX1 = (float)RenderUtil.interpolate(this.main_posX + 200, ThunderGui2.selected_plate.getPosX(), this.settings_animation) - 20.0f;
        final float scissorY1 = (float)RenderUtil.interpolate(this.main_posY + 40, ThunderGui2.selected_plate.getPosY(), this.settings_animation);
        float scissorX2 = Math.max((float)RenderUtil.interpolate(395.0, 90.0, this.settings_animation) + this.main_posX, (float)(this.main_posX + 205)) + 40.0f;
        float scissorY2 = Math.max((float)RenderUtil.interpolate(this.main_height - 5, 30.0, this.settings_animation) + this.main_posY, (float)(this.main_posY + 45));
        if (scissorX2 < scissorX1) {
            scissorX2 = scissorX1;
        }
        if (scissorY2 < scissorY1) {
            scissorY2 = scissorY1;
        }
        RenderUtil.glScissor(scissorX1, scissorY1, scissorX2, scissorY2, sr, ThunderGui2.open_animation.getAnimationd());
        GL11.glEnable(3089);
        if (!this.settings.isEmpty()) {
            double offsetY = 0.0;
            for (final SettingElement element : this.settings) {
                if (!element.isVisible()) {
                    continue;
                }
                element.setOffsetY(offsetY);
                element.setX((double)(this.main_posX + 215));
                element.setY((double)(this.main_posY + 45 + this.scroll));
                element.setWidth(175.0);
                element.setHeight(15.0);
                if (element instanceof ColorPickerComponent && ((ColorPickerComponent)element).isOpen()) {
                    element.setHeight(56.0);
                }
                if (element instanceof ModeComponent) {
                    final ModeComponent component2 = (ModeComponent)element;
                    component2.setWHeight(15.0);
                    if (component2.isOpen()) {
                        offsetY += component2.getSetting().getModes().length * 6;
                        element.setHeight(element.getHeight() + component2.getSetting().getModes().length * 6 + 3.0);
                    }
                    else {
                        element.setHeight(15.0);
                    }
                }
                element.render(mouseX, mouseY, partialTicks);
                offsetY += element.getHeight() + 3.0;
            }
        }
        if (ThunderGui2.selected_plate != null && this.settings_animation < 0.9999) {
            RoundedShader.drawRound((float)RenderUtil.interpolate(this.main_posX + 200, ThunderGui2.selected_plate.getPosX(), this.settings_animation), (float)RenderUtil.interpolate(this.main_posY + 40, ThunderGui2.selected_plate.getPosY(), this.settings_animation), (float)RenderUtil.interpolate(195.0, 90.0, this.settings_animation), (float)RenderUtil.interpolate(this.main_height - 45, 30.0, this.settings_animation), 4.0f, ColorUtil.applyOpacity(ThunderHackGui.getInstance().getColorByTheme(7), 1.0f - this.settings_animation));
        }
        GL11.glDisable(3089);
    }
    
    private int getCategoryY(final Module.Category category) {
        for (final CategoryPlate categoryPlate : this.categories) {
            if (categoryPlate.getCategory() == category) {
                return categoryPlate.getPosY();
            }
        }
        return 0;
    }
    
    public void onTick() {
        ThunderGui2.open_animation.update(ThunderGui2.open_direction);
        this.components.forEach(ModulePlate::onTick);
        this.settings.forEach(SettingElement::onTick);
        this.configs.forEach(ConfigComponent::onTick);
        this.friends.forEach(FriendComponent::onTick);
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        ThunderGui2.mouse_state = true;
        if (this.isHoveringItem((float)(this.main_posX + 368), (float)(this.main_posY + 17), 20.0f, 6.0f, (float)mouseX, (float)mouseY)) {
            if (this.listening_config) {
                ConfigManager.save(this.config_string);
                this.config_string = "Save config";
                this.listening_config = false;
                this.loadConfigs();
                return;
            }
            if (this.listening_friend) {
                Thunderhack.friendManager.addFriend(this.friend_string);
                this.friend_string = "Add friend";
                this.listening_friend = false;
                this.loadFriends();
                return;
            }
        }
        if (this.isHoveringItem((float)(this.main_posX + 105), (float)(this.main_posY + 14), 11.0f, 11.0f, (float)mouseX, (float)mouseY)) {
            try {
                Desktop.getDesktop().browse(new File("ThunderHack/configs/").toURI());
            }
            catch (Exception e) {
                Command.sendMessage("\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0440\u043e\u0432\u043e\u0434\u043d\u0438\u043a!");
            }
        }
        if (this.isHoveringItem((float)(this.main_posX + 20), (float)(this.main_posY + 195), 28.0f, 20.0f, (float)mouseX, (float)mouseY)) {
            this.current_category = null;
            ThunderGui2.currentMode = CurrentMode.ConfigManager;
            this.settings.clear();
            this.components.clear();
        }
        if (this.isHoveringItem((float)(this.main_posX + 50), (float)(this.main_posY + 195), 28.0f, 20.0f, (float)mouseX, (float)mouseY)) {
            this.current_category = null;
            ThunderGui2.currentMode = CurrentMode.FriendManager;
            this.settings.clear();
            this.components.clear();
        }
        if (this.isHoveringItem((float)this.main_posX, (float)this.main_posY, 400.0f, 30.0f, (float)mouseX, (float)mouseY)) {
            this.drag_x = mouseX - this.main_posX;
            this.drag_y = mouseY - this.main_posY;
            this.dragging = true;
        }
        if (this.isHoveringItem((float)(this.main_posX + 250), (float)(this.main_posY + 15), 140.0f, 10.0f, (float)mouseX, (float)mouseY) && ThunderGui2.currentMode == CurrentMode.Modules) {
            this.searching = true;
        }
        if (this.isHoveringItem((float)(this.main_posX + 250), (float)(this.main_posY + 15), 110.0f, 10.0f, (float)mouseX, (float)mouseY) && ThunderGui2.currentMode == CurrentMode.ConfigManager) {
            this.listening_config = true;
        }
        if (this.isHoveringItem((float)(this.main_posX + 250), (float)(this.main_posY + 15), 110.0f, 10.0f, (float)mouseX, (float)mouseY) && ThunderGui2.currentMode == CurrentMode.FriendManager) {
            this.listening_friend = true;
        }
        if (this.isHoveringItem((float)this.main_posX, (float)(this.main_posY + this.main_height - 6), 400.0f, 12.0f, (float)mouseX, (float)mouseY)) {
            this.rescale_y = mouseY - this.main_height;
            this.rescale = true;
        }
        this.settings.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
        this.categories.forEach(category -> category.mouseClicked(mouseX, mouseY, clickedButton));
        this.configs.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
        this.friends.forEach(component -> component.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        ThunderGui2.mouse_state = false;
        this.dragging = false;
        this.rescale = false;
        this.settings.forEach(settingElement -> settingElement.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            ThunderGui2.open_direction = false;
            this.searching = false;
        }
        this.settings.forEach(settingElement -> settingElement.keyTyped(typedChar, keyCode));
        this.components.forEach(component -> component.keyTyped(typedChar, keyCode));
        if (this.searching) {
            this.components.clear();
            if (this.search_string.equalsIgnoreCase("search")) {
                this.search_string = "";
            }
            int module_y = 0;
            for (final Module module : Thunderhack.moduleManager.getModulesSearch(this.search_string)) {
                this.components.add(new ModulePlate(module, this.main_posX + 100, this.main_posY + 40 + module_y, module_y / 35));
                module_y += 35;
            }
            switch (keyCode) {
                case 1: {
                    this.search_string = "Search";
                    this.searching = false;
                    return;
                }
                case 14: {
                    this.search_string = removeLastChar(this.search_string);
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.search_string += typedChar;
            }
        }
        if (this.listening_config) {
            if (this.config_string.equalsIgnoreCase("Save config")) {
                this.config_string = "";
            }
            switch (keyCode) {
                case 1: {
                    this.config_string = "Save config";
                    this.listening_config = false;
                    return;
                }
                case 14: {
                    this.config_string = removeLastChar(this.config_string);
                    break;
                }
                case 28: {
                    if (!this.config_string.equals("Save config") && !this.config_string.equals("")) {
                        ConfigManager.save(this.config_string);
                        this.config_string = "Save config";
                        this.listening_config = false;
                        this.loadConfigs();
                        break;
                    }
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.config_string += typedChar;
            }
        }
        if (this.listening_friend) {
            if (this.friend_string.equalsIgnoreCase("Add friend")) {
                this.friend_string = "";
            }
            switch (keyCode) {
                case 1: {
                    this.friend_string = "Add friend";
                    this.listening_friend = false;
                    return;
                }
                case 14: {
                    this.friend_string = removeLastChar(this.friend_string);
                    break;
                }
                case 28: {
                    if (!this.friend_string.equals("Add friend") && !this.config_string.equals("")) {
                        Thunderhack.friendManager.addFriend(this.friend_string);
                        this.friend_string = "Add friend";
                        this.listening_friend = false;
                        this.loadFriends();
                        break;
                    }
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.friend_string += typedChar;
            }
        }
    }
    
    public void onGuiClosed() {
    }
    
    public boolean isHoveringItem(final float x, final float y, final float x1, final float y1, final float mouseX, final float mouseY) {
        return mouseX >= x && mouseY >= y && mouseX <= x1 + x && mouseY <= y1 + y;
    }
    
    public void checkMouseWheel(final int mouseX, final int mouseY) {
        final float dWheel = (float)Mouse.getDWheel();
        this.settings.forEach(component -> component.checkMouseWheel(dWheel));
        if (ThunderGui2.scroll_lock) {
            ThunderGui2.scroll_lock = false;
            return;
        }
        if (this.isHoveringItem((float)(this.main_posX + 200), (float)(this.main_posY + 40), (float)(this.main_posX + 395), (float)(this.main_posY - 5 + this.main_height), (float)mouseX, (float)mouseY)) {
            this.scroll += dWheel * ThunderHackGui.getInstance().scrollSpeed.getValue();
        }
        else {
            this.components.forEach(component -> component.scrollElement(dWheel * ThunderHackGui.getInstance().scrollSpeed.getValue()));
        }
        this.configs.forEach(component -> component.scrollElement(dWheel * ThunderHackGui.getInstance().scrollSpeed.getValue()));
        this.friends.forEach(component -> component.scrollElement(dWheel * ThunderHackGui.getInstance().scrollSpeed.getValue()));
    }
    
    static {
        ThunderGui2.currentMode = CurrentMode.Modules;
        ThunderGui2.scroll_lock = false;
        ThunderGui2.open_animation = new BetterAnimation(5);
        ThunderGui2.open_direction = false;
        ThunderGui2.INSTANCE = new ThunderGui2();
    }
    
    public enum CurrentMode
    {
        Modules, 
        ConfigManager, 
        FriendManager;
    }
}
