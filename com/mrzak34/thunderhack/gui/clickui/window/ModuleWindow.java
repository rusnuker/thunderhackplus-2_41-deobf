//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.window;

import com.mrzak34.thunderhack.gui.clickui.button.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import com.mrzak34.thunderhack.notification.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import java.util.*;
import java.io.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.clickui.base.*;

public class ModuleWindow
{
    private final List<ModuleButton> buttons;
    private final ResourceLocation ICON;
    private final Animation animation;
    private final Animation dragAnimation;
    private final Animation rotationAnim;
    public double animationY;
    public boolean dragging;
    protected double prevTargetX;
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected boolean hovered;
    protected double factor;
    private double prevScrollProgress;
    private double scrollProgress;
    private boolean scrollHover;
    private float rotation;
    private final String name;
    private double prevX;
    private double prevY;
    private boolean open;
    
    public ModuleWindow(final String name, final List<Module> features, final int index, final double x, final double y, final double width, final double height) {
        this.animation = (Animation)new EaseBackIn(270, 1.0, 1.03f, Direction.BACKWARDS);
        this.dragAnimation = new DecelerateAnimation(260, 1.0, Direction.BACKWARDS);
        this.rotationAnim = new DecelerateAnimation(260, 1.0, Direction.FORWARDS);
        this.rotation = 0.0f;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.open = false;
        this.buttons = new ArrayList<ModuleButton>();
        this.ICON = new ResourceLocation("textures/" + name.toLowerCase() + ".png");
        final ModuleButton button;
        features.forEach(feature -> {
            button = new ModuleButton(feature);
            button.setHeight(15.0);
            this.buttons.add(button);
        });
    }
    
    public void init() {
        this.buttons.forEach(ModuleButton::init);
    }
    
    public void render(final int mouseX, final int mouseY, final float delta, final Color color, final boolean finished) {
        this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        this.animationY = RenderUtil.interpolate(this.y, this.animationY, 0.05);
        if (this.dragging) {
            this.prevTargetX = this.x;
            this.x = this.prevX + mouseX;
            this.y = this.prevY + mouseY;
        }
        else {
            this.prevTargetX = this.x;
        }
        final double maxHeight = 4000.0;
        this.scrollHover = Drawable.isHovered(mouseX, mouseY, this.x, this.y + this.height, this.width, maxHeight);
        this.animation.setDirection(this.isOpen() ? Direction.FORWARDS : Direction.BACKWARDS);
        this.dragAnimation.setDirection(this.dragging ? Direction.FORWARDS : Direction.BACKWARDS);
        this.rotationAnim.setDirection(Direction.FORWARDS);
        GlStateManager.pushMatrix();
        final float centerX = (float)(this.x + (mouseX - this.prevTargetX) / 2.0);
        final float centerY = (float)(this.y + this.height / 2.0);
        this.rotation = ((this.prevTargetX > this.x) ? RenderUtil.scrollAnimate(this.rotation, (float)(-(5.0 - (this.x - this.prevTargetX) * 3.3)), 0.94f) : ((this.prevTargetX < this.x) ? RenderUtil.scrollAnimate(this.rotation, (float)(5.0 + (this.x - this.prevTargetX) * 3.3), 0.94f) : RenderUtil.scrollAnimate(this.rotation, 0.0f, 0.8f)));
        final float dragScale = (float)(1.0 - 0.01600000075995922 * this.dragAnimation.getOutput());
        GlStateManager.translate(centerX, centerY, 1.0f);
        GlStateManager.scale(dragScale + Math.abs(this.rotation / 200.0f), dragScale, 1.0f);
        GlStateManager.rotate(this.rotation, 0.0f, 0.0f, 1.0f);
        GlStateManager.translate(-centerX, -centerY, 1.0f);
        RoundedShader.drawRound((float)this.x + 2.0f, (float)(this.y + this.height - 5.0), (float)this.width - 4.0f, (float)((this.getButtonsHeight() + 8.0) * this.animation.getOutput()), 3.0f, true, ClickGui.getInstance().plateColor.getValue().getColorObject());
        if (this.animation.finished(Direction.FORWARDS)) {
            Drawable.drawBlurredShadow((float)((int)this.x + 4), (float)(int)(this.y + this.height - 1.0), (float)((int)this.width - 8), 3.0f, 7, new Color(0, 0, 0, 180));
            for (final ModuleButton button : this.buttons) {
                button.setX(this.x + 2.0);
                button.setY(this.y + this.height - this.getScrollProgress());
                button.setWidth(this.width - 4.0);
                button.setHeight(15.0);
                button.render(mouseX, mouseY, delta, color, finished);
            }
        }
        Drawable.drawRectWH(this.x, this.y, this.width, this.height, ClickGui.getInstance().catColor.getValue().getColor());
        Drawable.drawTexture(this.ICON, this.x + 3.0, this.y + (this.height - 12.0) / 2.0, 12.0, 12.0);
        FontRender.drawString6(this.getName(), (float)this.x + 19.0f, (float)this.y + (float)this.height / 2.0f - FontRender.getFontHeight6() / 2, -1, true);
        GlStateManager.popMatrix();
        this.updatePosition();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered && button == 0) {
            this.dragging = true;
            this.prevX = this.x - mouseX;
            this.prevY = this.y - mouseY;
        }
        if (button == 1 && this.hovered) {
            this.setOpen(!this.isOpen());
        }
        if (this.isOpen() && this.scrollHover) {
            this.buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, button));
        }
        else if (!this.isOpen()) {
            this.buttons.forEach(ModuleButton::resetAnimation);
        }
    }
    
    public void tick() {
        if (this.isOpen()) {
            this.buttons.forEach(ModuleButton::tick);
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        if (button == 0) {
            this.dragging = false;
        }
        if (this.isOpen()) {
            this.buttons.forEach(b -> b.mouseReleased(mouseX, mouseY, button));
        }
    }
    
    public void handleMouseInput() throws IOException {
        for (final ModuleButton button : this.buttons) {
            button.handleMouseInput();
        }
    }
    
    public void keyTyped(final char chr, final int keyCode) {
        if (this.isOpen()) {
            for (final ModuleButton button : this.buttons) {
                button.keyTyped(chr, keyCode);
            }
        }
    }
    
    public void onClose() {
        this.buttons.forEach(ModuleButton::onGuiClosed);
    }
    
    private double getScrollProgress() {
        return this.prevScrollProgress + (this.scrollProgress - this.prevScrollProgress) * Util.mc.getRenderPartialTicks();
    }
    
    private void updatePosition() {
        double offsetY = 0.0;
        final double openY = 0.0;
        for (final ModuleButton button : this.buttons) {
            button.setOffsetY(offsetY);
            if (button.isOpen()) {
                for (final AbstractElement element : button.getElements()) {
                    if (element.isVisible()) {
                        offsetY += element.getHeight();
                    }
                }
                offsetY += 2.0;
            }
            offsetY += button.getHeight() + openY;
        }
    }
    
    public double getButtonsHeight() {
        double height = 0.0;
        for (final ModuleButton button : this.buttons) {
            height += button.getElementsHeight();
            height += button.getHeight();
        }
        return height;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public void setWidth(final double width) {
        this.width = width;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    public void setFactor(final double factor) {
        this.factor = factor;
    }
}
