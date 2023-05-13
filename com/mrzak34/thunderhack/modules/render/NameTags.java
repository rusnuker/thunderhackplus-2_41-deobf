//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import java.util.concurrent.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import com.mrzak34.thunderhack.*;
import net.minecraft.init.*;
import org.lwjgl.opengl.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.text.*;
import net.minecraft.nbt.*;
import com.mrzak34.thunderhack.util.*;
import net.minecraft.client.network.*;
import java.util.*;
import com.mrzak34.thunderhack.util.render.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;

public class NameTags extends Module
{
    private final Setting<Boolean> health;
    private final Setting<Boolean> armor;
    private final Setting<Float> scaling;
    private final Setting<Boolean> invisibles;
    private final Setting<Boolean> ping;
    private final Setting<Boolean> totemPops;
    private final Setting<Boolean> gamemode;
    private final Setting<Boolean> entityID;
    private final Setting<Boolean> rect;
    private final Setting<Boolean> outline;
    private final Setting<Float> lineWidth;
    private final Setting<Boolean> sneak;
    private final Setting<Boolean> heldStackName;
    private final Setting<Boolean> onlyFov;
    private final Setting<Boolean> scaleing;
    private final Setting<Float> factor;
    private final Setting<Boolean> smartScale;
    private final Setting<Boolean> ench;
    private final Setting<ColorSetting> mainColor;
    private final Setting<ColorSetting> outlineColor;
    private final Setting<ColorSetting> textColor;
    private final Setting<ColorSetting> friendColor;
    private final Setting<ColorSetting> friendtextColor;
    private final Setting<ColorSetting> invisibleText;
    private final Setting<ColorSetting> shiftColor;
    public Setting<mode> Mode;
    private CopyOnWriteArrayList<EntityPlayer> players;
    
    public NameTags() {
        super("Nametags", "Better Nametags", Module.Category.RENDER);
        this.health = (Setting<Boolean>)this.register(new Setting("Health", (T)true));
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (T)true));
        this.scaling = (Setting<Float>)this.register(new Setting("Size", (T)0.3f, (T)0.1f, (T)20.0f));
        this.invisibles = (Setting<Boolean>)this.register(new Setting("Invisibles", (T)false));
        this.ping = (Setting<Boolean>)this.register(new Setting("Ping", (T)true));
        this.totemPops = (Setting<Boolean>)this.register(new Setting("TotemPops", (T)true));
        this.gamemode = (Setting<Boolean>)this.register(new Setting("Gamemode", (T)false));
        this.entityID = (Setting<Boolean>)this.register(new Setting("ID", (T)false));
        this.rect = (Setting<Boolean>)this.register(new Setting("Rectangle", (T)true));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)Boolean.FALSE, v -> this.rect.getValue()));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.5f, (T)0.1f, (T)5.0f, v -> this.outline.getValue()));
        this.sneak = (Setting<Boolean>)this.register(new Setting("SneakColor", (T)false));
        this.heldStackName = (Setting<Boolean>)this.register(new Setting("StackName", (T)false));
        this.onlyFov = (Setting<Boolean>)this.register(new Setting("OnlyFov", (T)false));
        this.scaleing = (Setting<Boolean>)this.register(new Setting("Scale", (T)false));
        this.factor = (Setting<Float>)this.register(new Setting("Factor", (T)0.3f, (T)0.1f, (T)1.0f, v -> this.scaleing.getValue()));
        this.smartScale = (Setting<Boolean>)this.register(new Setting("SmartScale", (T)false, v -> this.scaleing.getValue()));
        this.ench = (Setting<Boolean>)this.register(new Setting("Enchantments", (T)false));
        this.mainColor = (Setting<ColorSetting>)this.register(new Setting("MainColor", (T)new ColorSetting(new Color(-1157627897, true).getRGB())));
        this.outlineColor = (Setting<ColorSetting>)this.register(new Setting("OutlineColor", (T)new ColorSetting(new Color(-1144535097, true).getRGB())));
        this.textColor = (Setting<ColorSetting>)this.register(new Setting("TextColor", (T)new ColorSetting(new Color(-1144535097, true).getRGB())));
        this.friendColor = (Setting<ColorSetting>)this.register(new Setting("FriendColor", (T)new ColorSetting(new Color(-1693361408, true).getRGB())));
        this.friendtextColor = (Setting<ColorSetting>)this.register(new Setting("FriendText", (T)new ColorSetting(new Color(-1150039040, true).getRGB())));
        this.invisibleText = (Setting<ColorSetting>)this.register(new Setting("InvisText", (T)new ColorSetting(new Color(-1157548383, true).getRGB())));
        this.shiftColor = (Setting<ColorSetting>)this.register(new Setting("ShiftText", (T)new ColorSetting(new Color(-1147600692, true).getRGB())));
        this.Mode = (Setting<mode>)this.register(new Setting("RectMode", (T)mode.Shadow));
        this.players = new CopyOnWriteArrayList<EntityPlayer>();
    }
    
    public void onUpdate() {
        this.players.clear();
        this.players.addAll(NameTags.mc.world.playerEntities);
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (!fullNullCheck()) {
            for (final EntityPlayer player : this.players) {
                if (player != null && !player.equals((Object)NameTags.mc.player) && player.isEntityAlive() && (!player.isInvisible() || this.invisibles.getValue())) {
                    if (this.onlyFov.getValue() && !RotationUtil.isInFov((Entity)player)) {
                        continue;
                    }
                    final double x = this.interpolate(player.lastTickPosX, player.posX, event.getPartialTicks()) - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosX();
                    final double y = this.interpolate(player.lastTickPosY, player.posY, event.getPartialTicks()) - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosY();
                    final double z = this.interpolate(player.lastTickPosZ, player.posZ, event.getPartialTicks()) - ((IRenderManager)Util.mc.getRenderManager()).getRenderPosZ();
                    this.renderNameTag(player, x, y, z, event.getPartialTicks());
                }
            }
        }
    }
    
    public void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth((float)this.lineWidth.getValue());
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public void drawOutlineRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth((float)this.lineWidth.getValue());
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    private void renderNameTag(final EntityPlayer player, final double x, final double y, final double z, final float delta) {
        double tempY = y;
        tempY += (player.isSneaking() ? 0.5 : 0.7);
        final Entity camera = NameTags.mc.getRenderViewEntity();
        assert camera != null;
        final double originalPositionX = camera.posX;
        final double originalPositionY = camera.posY;
        final double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        final String displayTag = this.getDisplayTag(player);
        final double distance = camera.getDistance(x + NameTags.mc.getRenderManager().viewerPosX, y + NameTags.mc.getRenderManager().viewerPosY, z + NameTags.mc.getRenderManager().viewerPosZ);
        final int width = Util.fr.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + this.scaling.getValue() * (distance * this.factor.getValue())) / 1000.0;
        if (distance <= 8.0 && this.smartScale.getValue()) {
            scale = 0.0245;
        }
        if (!this.scaleing.getValue()) {
            scale = this.scaling.getValue() / 100.0;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)x, (float)tempY + 1.4f, (float)z);
        GlStateManager.rotate(-NameTags.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(NameTags.mc.getRenderManager().playerViewX, (NameTags.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        if (this.rect.getValue()) {
            this.drawRect((float)(-width - 2), (float)(-(Util.fr.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, Thunderhack.friendManager.isFriend(player) ? this.friendColor.getValue().getColor() : this.mainColor.getValue().getColor());
            if (this.outline.getValue()) {
                this.drawOutlineRect((float)(-width - 2), (float)(-(NameTags.mc.fontRenderer.FONT_HEIGHT + 1)), width + 2.0f, 1.5f, this.outlineColor.getValue().getColor());
            }
        }
        GlStateManager.disableBlend();
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (this.heldStackName.getValue() && !renderMainHand.isEmpty() && renderMainHand.getItem() != Items.AIR) {
            final String stackName = renderMainHand.getDisplayName();
            final int stackNameWidth = Util.fr.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef(0.75f, 0.75f, 0.0f);
            Util.fr.drawStringWithShadow(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 20.0f), -1);
            GL11.glScalef(1.5f, 1.5f, 1.0f);
            GL11.glPopMatrix();
        }
        if (this.armor.getValue()) {
            GlStateManager.pushMatrix();
            int xOffset = -8;
            for (final ItemStack stack : player.inventory.armorInventory) {
                if (stack == null) {
                    continue;
                }
                xOffset -= 8;
            }
            xOffset -= 8;
            final ItemStack renderOffhand = player.getHeldItemOffhand().copy();
            this.renderItemStack(renderOffhand, xOffset, -26);
            xOffset += 16;
            for (final ItemStack stack2 : player.inventory.armorInventory) {
                if (stack2 == null) {
                    continue;
                }
                final ItemStack armourStack = stack2.copy();
                this.renderItemStack(armourStack, xOffset, -26);
                xOffset += 16;
            }
            this.renderItemStack(renderMainHand, xOffset, -26);
            GlStateManager.popMatrix();
        }
        Util.fr.drawStringWithShadow(displayTag, (float)(-width), (float)(-(Util.fr.FONT_HEIGHT - 1)), this.getDisplayColour(player));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderItemStack(final ItemStack stack, final int x, final int y) {
        GlStateManager.pushMatrix();
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        NameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        NameTags.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        NameTags.mc.getRenderItem().renderItemOverlays(NameTags.mc.fontRenderer, stack, x, y);
        NameTags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        if (this.ench.getValue()) {
            this.renderEnchantmentText(stack, x, y);
        }
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    private void renderEnchantmentText(final ItemStack stack, final int x, final int y) {
        int enchantmentY = y - 8;
        if (stack.getItem() == Items.GOLDEN_APPLE && stack.hasEffect()) {
            Util.fr.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        }
        final NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            final short id = enchants.getCompoundTagAt(index).getShort("id");
            final short level = enchants.getCompoundTagAt(index).getShort("lvl");
            final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
            if (enc != null) {
                String encName = enc.isCurse() ? (TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase()) : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
                encName += level;
                Util.fr.drawStringWithShadow(encName, (float)(x * 2), (float)enchantmentY, -1);
                enchantmentY -= 8;
            }
        }
    }
    
    private float getBiggestArmorTag(final EntityPlayer player) {
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (final ItemStack stack : player.inventory.armorInventory) {
            float encY = 0.0f;
            if (stack != null) {
                final NBTTagList enchants = stack.getEnchantmentTagList();
                for (int index = 0; index < enchants.tagCount(); ++index) {
                    final short id = enchants.getCompoundTagAt(index).getShort("id");
                    final Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc != null) {
                        encY += 8.0f;
                        arm = true;
                    }
                }
            }
            if (encY <= enchantmentY) {
                continue;
            }
            enchantmentY = encY;
        }
        final ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                final short id = enchants2.getCompoundTagAt(index2).getShort("id");
                final Enchantment enc2 = Enchantment.getEnchantmentByID((int)id);
                if (enc2 != null) {
                    encY2 += 8.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        final ItemStack renderOffHand;
        if ((renderOffHand = player.getHeldItemOffhand().copy()).hasEffect()) {
            float encY2 = 0.0f;
            final NBTTagList enchants2 = renderOffHand.getEnchantmentTagList();
            for (int index = 0; index < enchants2.tagCount(); ++index) {
                final short id2 = enchants2.getCompoundTagAt(index).getShort("id");
                final Enchantment enc = Enchantment.getEnchantmentByID((int)id2);
                if (enc != null) {
                    encY2 += 8.0f;
                    arm = true;
                }
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        return (arm ? 0 : 20) + enchantmentY;
    }
    
    private String getDisplayTag(final EntityPlayer player) {
        String name = player.getDisplayName().getFormattedText();
        if (name.contains(NameTags.mc.getSession().getUsername())) {
            name = "You";
        }
        if (!this.health.getValue()) {
            return name;
        }
        final float health = EntityUtil.getHealth((Entity)player);
        final String color = (health > 18.0f) ? "§a" : ((health > 16.0f) ? "§2" : ((health > 12.0f) ? "§e" : ((health > 8.0f) ? "§6" : ((health > 5.0f) ? "§c" : "§4"))));
        String pingStr = "";
        if (this.ping.getValue()) {
            try {
                final int responseTime = Objects.requireNonNull(NameTags.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception ex) {}
        }
        String popStr = " ";
        if (this.totemPops.getValue()) {
            popStr += Thunderhack.combatManager.getPops((Entity)player);
        }
        String idString = "";
        if (this.entityID.getValue()) {
            idString = idString + "ID: " + player.getEntityId() + " ";
        }
        String gameModeStr = "";
        if (this.gamemode.getValue()) {
            gameModeStr = (player.isCreative() ? (gameModeStr + "[C] ") : ((player.isSpectator() || player.isInvisible()) ? (gameModeStr + "[I] ") : (gameModeStr + "[S] ")));
        }
        name = ((Math.floor(health) == health) ? (name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)Math.floor(health)) : "dead")) : (name + color + " " + ((health > 0.0f) ? Integer.valueOf((int)health) : "dead")));
        return pingStr + idString + gameModeStr + name + popStr;
    }
    
    private int getDisplayColour(final EntityPlayer player) {
        int colour = this.textColor.getValue().getColor();
        if (Thunderhack.friendManager.isFriend(player)) {
            return this.friendtextColor.getValue().getColor();
        }
        if (player.isInvisible()) {
            colour = this.invisibleText.getValue().getColor();
        }
        else if (player.isSneaking() && this.sneak.getValue()) {
            colour = this.shiftColor.getValue().getColor();
        }
        return colour;
    }
    
    private double interpolate(final double previous, final double current, final float delta) {
        return previous + (current - previous) * delta;
    }
    
    private void renderEnchantmentText2(final int x) {
        final int enchantmentY = -78;
        FontRender.drawString3("ILLEGAL", (float)(x * 3.1), (float)enchantmentY, PaletteHelper.rainbow(300, 1.0f, 1.0f).getRGB());
    }
    
    public enum mode
    {
        Default, 
        ShaderSmooth, 
        Shadow;
    }
}
