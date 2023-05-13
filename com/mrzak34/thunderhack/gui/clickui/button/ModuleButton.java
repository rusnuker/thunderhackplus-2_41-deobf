//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.clickui.button;

import com.mrzak34.thunderhack.gui.clickui.base.*;
import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.notification.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.gui.clickui.elements.*;
import java.util.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.manager.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mrzak34.thunderhack.gui.clickui.*;
import java.io.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import org.lwjgl.input.*;

public class ModuleButton
{
    private final Animation animation;
    private final Animation enableAnimation;
    private final List<AbstractElement> elements;
    private final Module module;
    private double x;
    private double y;
    private double width;
    private double height;
    private double offsetY;
    private boolean open;
    private boolean hovered;
    private boolean binding;
    
    public ModuleButton(final Module module) {
        this.animation = new DecelerateAnimation(260, 1.0, Direction.BACKWARDS);
        this.enableAnimation = new DecelerateAnimation(180, 1.0, Direction.BACKWARDS);
        this.binding = false;
        this.module = module;
        this.elements = new ArrayList<AbstractElement>();
        for (final Setting setting : module.getSettings()) {
            if (setting.getValue() instanceof Boolean && !setting.getName().equals("Enabled") && !setting.getName().equals("Drawn")) {
                this.elements.add(new CheckBoxElement(setting));
            }
            else if (setting.getValue() instanceof ColorSetting) {
                this.elements.add(new ColorPickerElement(setting));
            }
            else if (setting.isNumberSetting() && setting.hasRestriction()) {
                this.elements.add(new SliderElement(setting));
            }
            else if (setting.isEnumSetting() && !(setting.getValue() instanceof Parent) && !(setting.getValue() instanceof PositionSetting)) {
                this.elements.add(new ModeElement(setting));
            }
            else if (setting.getValue() instanceof SubBind) {
                this.elements.add(new SubBindElement(setting));
            }
            else if ((setting.getValue() instanceof String || setting.getValue() instanceof Character) && !setting.getName().equalsIgnoreCase("displayName")) {
                this.elements.add(new StringElement(setting));
            }
            else {
                if (!(setting.getValue() instanceof Parent)) {
                    continue;
                }
                this.elements.add(new ParentElement(setting));
            }
        }
    }
    
    public void init() {
        this.elements.forEach(AbstractElement::init);
    }
    
    public void tick() {
        if (this.isOpen()) {
            this.elements.forEach(AbstractElement::tick);
        }
    }
    
    public void render(final int mouseX, final int mouseY, final float delta, final Color color, final boolean finished) {
        this.hovered = Drawable.isHovered(mouseX, mouseY, this.x, this.y, this.width, this.height);
        if (this.hovered) {
            EventManager.hoveredModule = this.module;
        }
        final double ix = this.x + 5.0;
        final double iy = this.y + this.height / 2.0 - FontRender.getFontHeight6() / 2.0f;
        this.enableAnimation.setDirection(this.module.isEnabled() ? Direction.BACKWARDS : Direction.FORWARDS);
        this.animation.setDirection(this.hovered ? Direction.FORWARDS : Direction.BACKWARDS);
        if (this.isOpen()) {
            final int sbg = new Color(24, 24, 27).getRGB();
            Drawable.horizontalGradient(this.x, this.y + this.height + 2.0, (this.x + this.width) * (1.0 - this.enableAnimation.getOutput()), this.y + this.height + 2.0 + this.getElementsHeight() * (1.0 - this.enableAnimation.getOutput()), this.module.isEnabled() ? ColorUtil.applyOpacity(ClickGui.getInstance().getColor(200), 0.7f).getRGB() : sbg, this.module.isEnabled() ? ColorUtil.applyOpacity(ClickGui.getInstance().getColor(0), 0.7f).getRGB() : sbg);
            double offsetY = 0.0;
            for (final AbstractElement element : this.elements) {
                if (!element.isVisible()) {
                    continue;
                }
                element.setOffsetY(offsetY);
                element.setX(this.x);
                element.setY(this.y + this.height + 2.0);
                element.setWidth(this.width);
                element.setHeight(15.0);
                if (element instanceof ColorPickerElement) {
                    element.setHeight(56.0);
                }
                else if (element instanceof SliderElement) {
                    element.setHeight(18.0);
                }
                if (element instanceof ModeElement) {
                    final ModeElement combobox = (ModeElement)element;
                    combobox.setWHeight(17.0);
                    if (combobox.isOpen()) {
                        offsetY += combobox.getSetting().getModes().length * 6;
                        element.setHeight(element.getHeight() + combobox.getSetting().getModes().length * 6 + 3.0);
                    }
                    else {
                        element.setHeight(17.0);
                    }
                }
                element.render(mouseX, mouseY, delta);
                offsetY += element.getHeight();
            }
            Drawable.drawBlurredShadow((float)(int)this.x, (float)(int)(this.y + this.height), (float)(int)this.width, 3.0f, 9, new Color(0, 0, 0, 190));
        }
        Drawable.drawRectWH(this.x, this.y, this.width, this.isOpen() ? (this.height + 2.0) : this.height, ClickGui.getInstance().downColor.getValue().getColor());
        if (!this.enableAnimation.finished(Direction.FORWARDS)) {
            Drawable.horizontalGradient(this.x, this.y, (this.x + this.width) * (1.0 - this.enableAnimation.getOutput()), this.y + (this.isOpen() ? (this.height + 2.0) : this.height), ColorUtil.applyOpacity(ClickGui.getInstance().getColor(200), 0.9f).getRGB(), ColorUtil.applyOpacity(ClickGui.getInstance().getColor(0), 0.9f).getRGB());
        }
        if (!ClickGui.getInstance().showBinds.getValue()) {
            if (this.module.getSettings().size() > 4) {
                FontRender.drawCentString6(this.isOpen() ? "-" : "+", (float)this.x + (float)this.width - 8.0f, (float)this.y + 6.0f, -1);
            }
        }
        else if (!this.module.getBind().toString().equalsIgnoreCase("none")) {
            FontRender.drawString5(this.module.getBind().toString(), (float)this.x + (float)this.width - FontRender.getStringWidth5(this.module.getBind().toString()) - 3.0f, (float)this.y + 6.0f, -1);
        }
        final float scale = (float)this.animation.getOutput();
        if (this.binding) {
            FontRender.drawString6("Keybind: " + this.module.getBind().toString(), (float)ix, (float)iy, -1381654, true);
        }
        else {
            FontRender.drawString6(this.module.getName(), (float)ix + scale, (float)iy + 3.0f, -1381654, true);
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.hovered) {
            if (button == 0) {
                this.module.toggle();
            }
            else if (button == 1 && this.module.getSettings().size() > 4) {
                this.setOpen(!this.isOpen());
            }
            if (button == 2) {
                this.binding = !this.binding;
            }
        }
        if (this.open) {
            this.elements.forEach(element -> {
                if (element.isVisible()) {
                    element.mouseClicked(mouseX, mouseY, button);
                }
            });
        }
        else {
            this.resetAnimation();
        }
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        if (this.isOpen()) {
            this.elements.forEach(element -> element.mouseReleased(mouseX, mouseY, button));
        }
    }
    
    public void handleMouseInput() throws IOException {
        if (!this.isOpen()) {
            return;
        }
        for (final AbstractElement element : this.elements) {
            element.handleMouseInput();
        }
    }
    
    public void keyTyped(final char chr, final int keyCode) {
        if (this.isOpen()) {
            for (final AbstractElement element : this.elements) {
                element.keyTyped(chr, keyCode);
            }
        }
        if (this.binding) {
            if (keyCode == 1 || keyCode == 57 || keyCode == 211) {
                this.module.setBind(0);
                Command.sendMessage("\u0423\u0434\u0430\u043b\u0435\u043d \u0431\u0438\u043d\u0434 \u0441 \u043c\u043e\u0434\u0443\u043b\u044f " + ChatFormatting.LIGHT_PURPLE + this.module.getName());
            }
            else {
                this.module.setBind(keyCode);
                Command.sendMessage(ChatFormatting.LIGHT_PURPLE + this.module.getName() + ChatFormatting.WHITE + " \u0431\u0438\u043d\u0434 \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430 " + ChatFormatting.GREEN + Keyboard.getKeyName(this.module.getBind().getKey()));
            }
            this.binding = false;
        }
    }
    
    public void onGuiClosed() {
        this.elements.forEach(AbstractElement::onClose);
        this.resetAnimation();
    }
    
    public void resetAnimation() {
        this.elements.forEach(AbstractElement::resetAnimation);
    }
    
    public List<AbstractElement> getElements() {
        return this.elements;
    }
    
    public double getElementsHeight() {
        double offsetY = 0.0;
        final double openedY = 0.0;
        if (this.isOpen()) {
            for (final AbstractElement element : this.getElements()) {
                if (element.isVisible()) {
                    offsetY += element.getHeight();
                }
            }
        }
        return offsetY + openedY;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
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
        this.y = y + this.offsetY;
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
    
    public void setOffsetY(final double offsetY) {
        this.offsetY = offsetY;
    }
}
