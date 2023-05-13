//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.gui.fontstuff;

import com.mrzak34.thunderhack.*;

public class FontRender
{
    public static float drawStringWithShadow(final String text, final float x, final float y, final int color) {
        return drawStringWithShadow(text, (int)x, (int)y, color);
    }
    
    public static float drawString(final String text, final float x, final float y, final int color) {
        return drawString(text, (int)x, (int)y, color);
    }
    
    public static float drawStringWithShadow(final String text, final int x, final int y, final int color) {
        return Thunderhack.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
    }
    
    public static float drawString(final String text, final int x, final int y, final int color) {
        return Thunderhack.fontRenderer.drawString(text, (float)x, (float)y, color);
    }
    
    public static float drawString2(final String text, final int x, final int y, final int color) {
        return Thunderhack.fontRenderer2.drawString(text, (float)x, (float)y, color);
    }
    
    public static float drawIcon(final String id, final int x, final int y, final int color) {
        return Thunderhack.icons.drawString(id, (float)x, (float)y, color);
    }
    
    public static float drawIconF(final String id, final float x, final float y, final int color) {
        return Thunderhack.icons.drawString(id, x, y, color);
    }
    
    public static float drawMidIcon(final String id, final float x, final float y, final int color) {
        return Thunderhack.middleicons.drawString(id, x, y, color);
    }
    
    public static float drawMidIconF(final String id, final int x, final int y, final int color) {
        return Thunderhack.middleicons.drawString(id, (float)x, (float)y, color);
    }
    
    public static float drawBigIcon(final String id, final int x, final int y, final int color) {
        return Thunderhack.BIGicons.drawString(id, (float)x, (float)y, color);
    }
    
    public static float drawString3(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer3.drawString(text, x, y, color);
    }
    
    public static float drawCentString3(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer3.drawString(text, x - getStringWidth3(text) / 2.0f, y - getFontHeight3() / 2.0f, color);
    }
    
    public static float drawCentString4(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer4.drawString(text, x - getStringWidth4(text) / 2.0f, y - getFontHeight4() / 2.0f, color);
    }
    
    public static float drawString8(final String text, final int x, final int y, final int color) {
        return Thunderhack.fontRenderer8.drawString(text, (float)x, (float)y, color);
    }
    
    public static float drawCentString8(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer8.drawString(text, x - getStringWidth6(text) / 2.0f, y, color);
    }
    
    public static float drawString4(final String text, final int x, final int y, final int color) {
        return Thunderhack.fontRenderer4.drawString(text, (float)x, (float)y, color);
    }
    
    public static float drawString5(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer5.drawString(text, x, y, color);
    }
    
    public static float drawString6(final String text, final float x, final float y, final int color, final boolean shadow) {
        return Thunderhack.fontRenderer6.drawString(text, x, y, color);
    }
    
    public static float drawString7(final String text, final float x, final float y, final int color, final boolean shadow) {
        return Thunderhack.fontRenderer7.drawString(text, x, y, color);
    }
    
    public static float drawCentString6(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer6.drawString(text, x - getStringWidth6(text) / 2.0f, y, color);
    }
    
    public static float drawCentString5(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer5.drawString(text, x - getStringWidth5(text) / 2.0f, y, color);
    }
    
    public static float drawCentString2(final String text, final float x, final float y, final int color) {
        return Thunderhack.fontRenderer2.drawString(text, x - getStringWidth2(text) / 2.0f, y, color);
    }
    
    public static int getStringWidth2(final String str) {
        return Thunderhack.fontRenderer2.getStringWidth(str);
    }
    
    public static int getStringWidth(final String str) {
        return Thunderhack.fontRenderer.getStringWidth(str);
    }
    
    public static int getStringWidth6(final String str) {
        if (str == null) {
            return 1;
        }
        if (str.equals("")) {
            return 1;
        }
        return Thunderhack.fontRenderer6.getStringWidth(str);
    }
    
    public static int getStringWidth5(final String str) {
        return Thunderhack.fontRenderer5.getStringWidth(str);
    }
    
    public static int getStringWidth3(final String str) {
        return Thunderhack.fontRenderer3.getStringWidth(str);
    }
    
    public static int getStringWidth4(final String str) {
        return Thunderhack.fontRenderer4.getStringWidth(str);
    }
    
    public static int getFontHeight() {
        return Thunderhack.fontRenderer.getHeight() + 2;
    }
    
    public static int getFontHeight2() {
        return Thunderhack.fontRenderer2.getHeight() + 2;
    }
    
    public static int getFontHeight3() {
        return Thunderhack.fontRenderer3.getHeight();
    }
    
    public static int getFontHeight4() {
        return Thunderhack.fontRenderer3.getHeight();
    }
    
    public static int getFontHeight6() {
        return Thunderhack.fontRenderer6.getHeight();
    }
    
    public static int getFontHeight8() {
        return Thunderhack.fontRenderer8.getHeight();
    }
    
    public static int getFontHeight5() {
        return Thunderhack.fontRenderer5.getHeight() + 2;
    }
}
