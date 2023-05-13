//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.modules.render;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.setting.*;
import net.minecraft.util.*;
import com.mrzak34.thunderhack.mixin.ducks.*;
import net.minecraft.entity.*;
import java.util.function.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.mixin.mixins.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import com.mrzak34.thunderhack.util.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.passive.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.init.*;
import com.mrzak34.thunderhack.events.*;
import net.minecraft.entity.item.*;

public class NoRender extends Module
{
    private static NoRender INSTANCE;
    public Setting<Boolean> noarmorstands;
    public Setting<Boolean> fire;
    public Setting<Boolean> blin;
    public Setting<Boolean> arrows;
    public Setting<Boolean> SkyLight;
    public Setting<Boolean> portal;
    public Setting<Boolean> totemPops;
    public Setting<Boolean> items;
    public Setting<Boolean> maps;
    public Setting<Boolean> nausea;
    public Setting<Boolean> hurtcam;
    public Setting<Boolean> explosions;
    public Setting<Boolean> lightning;
    public Setting<Boolean> fog;
    public Setting<Boolean> noWeather;
    public Setting<Boss> boss;
    public Setting<Float> scale;
    public Setting<Boolean> bats;
    public Setting<NoArmor> noArmor;
    public Setting<Boolean> blocks;
    public Setting<Boolean> advancements;
    public Setting<Boolean> timeChange;
    public Setting<Integer> time;
    public Setting<Boolean> fireworks;
    public Setting<Boolean> hooks;
    private static final ResourceLocation GUI_BARS_TEXTURES;
    
    public NoRender() {
        super("NoRender", "\u043d\u0435 \u0440\u0435\u043d\u0434\u0435\u0440\u0438\u0442\u044c \u043b\u0430\u0433\u0430\u043d\u044b\u0435-\u0445\u0435\u0440\u043d\u0438", Module.Category.RENDER);
        this.noarmorstands = (Setting<Boolean>)this.register(new Setting("ArmorStands", (T)false));
        this.fire = (Setting<Boolean>)this.register(new Setting("Fire", (T)false));
        this.blin = (Setting<Boolean>)this.register(new Setting("Blind", (T)false));
        this.arrows = (Setting<Boolean>)this.register(new Setting("Arrows", (T)false));
        this.SkyLight = (Setting<Boolean>)this.register(new Setting("SkyLight", (T)false));
        this.portal = (Setting<Boolean>)this.register(new Setting("portal", (T)false));
        this.totemPops = (Setting<Boolean>)this.register(new Setting("TotemPop", (T)false));
        this.items = (Setting<Boolean>)this.register(new Setting("Items", (T)false));
        this.maps = (Setting<Boolean>)this.register(new Setting("Maps", (T)false));
        this.nausea = (Setting<Boolean>)this.register(new Setting("Nausea", (T)false));
        this.hurtcam = (Setting<Boolean>)this.register(new Setting("HurtCam", (T)false));
        this.explosions = (Setting<Boolean>)this.register(new Setting("Explosions", (T)false));
        this.lightning = (Setting<Boolean>)this.register(new Setting("Lightning", (T)false));
        this.fog = (Setting<Boolean>)this.register(new Setting("NoFog", (T)false));
        this.noWeather = (Setting<Boolean>)this.register(new Setting("Weather", (T)false));
        this.boss = (Setting<Boss>)this.register(new Setting("BossBars", (T)Boss.NONE));
        this.scale = (Setting<Float>)this.register(new Setting("Scale", (T)0.5f, (T)0.5f, (T)1.0f, v -> this.boss.getValue() == Boss.MINIMIZE || this.boss.getValue() == Boss.STACK));
        this.bats = (Setting<Boolean>)this.register(new Setting("Bats", (T)false));
        this.noArmor = (Setting<NoArmor>)this.register(new Setting("NoArmor", (T)NoArmor.NONE));
        this.blocks = (Setting<Boolean>)this.register(new Setting("BlockOverlay", (T)false));
        this.advancements = (Setting<Boolean>)this.register(new Setting("Advancements", (T)false));
        this.timeChange = (Setting<Boolean>)this.register(new Setting("TimeChange", (T)false));
        this.time = (Setting<Integer>)this.register(new Setting("Time", (T)0, (T)0, (T)23000, v -> this.timeChange.getValue()));
        this.fireworks = (Setting<Boolean>)this.register(new Setting("FireWorks", (T)false));
        this.hooks = (Setting<Boolean>)this.register(new Setting("Hooks", (T)false));
        this.setInstance();
    }
    
    public static NoRender getInstance() {
        if (NoRender.INSTANCE == null) {
            NoRender.INSTANCE = new NoRender();
        }
        return NoRender.INSTANCE;
    }
    
    private void setInstance() {
        NoRender.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (this.portal.getValue()) {
            ((IEntity)NoRender.mc.player).setInPortal(false);
        }
        if (this.items.getValue()) {
            NoRender.mc.world.loadedEntityList.stream().filter(EntityItem.class::isInstance).map(EntityItem.class::cast).forEach(Entity::setDead);
        }
        if (this.arrows.getValue()) {
            NoRender.mc.world.loadedEntityList.stream().filter(EntityArrow.class::isInstance).map(EntityArrow.class::cast).forEach(Entity::setDead);
        }
        if (this.hooks.getValue()) {
            NoRender.mc.world.loadedEntityList.stream().filter(EntityFishHook.class::isInstance).map(EntityFishHook.class::cast).forEach(Entity::setDead);
        }
        if (this.noWeather.getValue() && NoRender.mc.world.isRaining()) {
            NoRender.mc.world.setRainStrength(0.0f);
        }
        if (this.timeChange.getValue()) {
            NoRender.mc.world.setWorldTime((long)this.time.getValue());
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketMaps && this.maps.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketTimeUpdate & this.timeChange.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion & this.explosions.getValue()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketEntityEffect && this.blin.getValue()) {
            final SPacketEntityEffect var3 = (SPacketEntityEffect)event.getPacket();
            if (var3.getEffectId() == 15) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof SPacketSpawnGlobalEntity && this.lightning.getValue() && ((SPacketSpawnGlobalEntity)event.getPacket()).getType() == 1) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRenderPre(final RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onRenderPost(final RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && this.boss.getValue() != Boss.NONE) {
            if (this.boss.getValue() == Boss.MINIMIZE) {
                final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)((IGuiBossOverlay)NoRender.mc.ingameGUI.getBossOverlay()).getMapBossInfos();
                if (map == null) {
                    return;
                }
                final ScaledResolution scaledresolution = new ScaledResolution(NoRender.mc);
                final int i = scaledresolution.getScaledWidth();
                int j = 12;
                for (final Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
                    final BossInfoClient info = entry.getValue();
                    final String text = info.getName().getFormattedText();
                    final int k = (int)(i / this.scale.getValue() / 2.0f - 91.0f);
                    GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), 1.0);
                    if (!event.isCanceled()) {
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        NoRender.mc.getTextureManager().bindTexture(NoRender.GUI_BARS_TEXTURES);
                        ((IGuiBossOverlay)NoRender.mc.ingameGUI.getBossOverlay()).invokeRender(k, j, (BossInfo)info);
                        NoRender.mc.fontRenderer.drawStringWithShadow(text, i / this.scale.getValue() / 2.0f - NoRender.mc.fontRenderer.getStringWidth(text) / 2.0f, (float)(j - 9), 16777215);
                    }
                    GL11.glScaled(1.0 / this.scale.getValue(), 1.0 / this.scale.getValue(), 1.0);
                    j += 10 + NoRender.mc.fontRenderer.FONT_HEIGHT;
                }
            }
            else if (this.boss.getValue() == Boss.STACK) {
                final Map<UUID, BossInfoClient> map = (Map<UUID, BossInfoClient>)((IGuiBossOverlay)NoRender.mc.ingameGUI.getBossOverlay()).getMapBossInfos();
                final HashMap<String, Pair<BossInfoClient, Integer>> to = new HashMap<String, Pair<BossInfoClient, Integer>>();
                for (final Map.Entry<UUID, BossInfoClient> entry2 : map.entrySet()) {
                    final String s = entry2.getValue().getName().getFormattedText();
                    if (to.containsKey(s)) {
                        Pair<BossInfoClient, Integer> p = to.get(s);
                        p = new Pair<BossInfoClient, Integer>(p.getKey(), p.getValue() + 1);
                        to.put(s, p);
                    }
                    else {
                        final Pair<BossInfoClient, Integer> p = new Pair<BossInfoClient, Integer>(entry2.getValue(), 1);
                        to.put(s, p);
                    }
                }
                final ScaledResolution scaledresolution2 = new ScaledResolution(NoRender.mc);
                final int l = scaledresolution2.getScaledWidth();
                int m = 12;
                for (final Map.Entry<String, Pair<BossInfoClient, Integer>> entry3 : to.entrySet()) {
                    String text = entry3.getKey();
                    final BossInfoClient info2 = entry3.getValue().getKey();
                    final int a = entry3.getValue().getValue();
                    text = text + " x" + a;
                    final int k2 = (int)(l / this.scale.getValue() / 2.0f - 91.0f);
                    GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), 1.0);
                    if (!event.isCanceled()) {
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        NoRender.mc.getTextureManager().bindTexture(NoRender.GUI_BARS_TEXTURES);
                        ((IGuiBossOverlay)NoRender.mc.ingameGUI.getBossOverlay()).invokeRender(k2, m, (BossInfo)info2);
                        NoRender.mc.fontRenderer.drawStringWithShadow(text, l / this.scale.getValue() / 2.0f - NoRender.mc.fontRenderer.getStringWidth(text) / 2.0f, (float)(m - 9), 16777215);
                    }
                    GL11.glScaled(1.0 / this.scale.getValue(), 1.0 / this.scale.getValue(), 1.0);
                    m += 10 + NoRender.mc.fontRenderer.FONT_HEIGHT;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onRenderLiving(final RenderLivingEvent.Pre<?> event) {
        if (this.bats.getValue() && event.getEntity() instanceof EntityBat) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlaySound(final PlaySoundAtEntityEvent event) {
        if ((this.bats.getValue() && event.getSound().equals(SoundEvents.ENTITY_BAT_AMBIENT)) || event.getSound().equals(SoundEvents.ENTITY_BAT_DEATH) || event.getSound().equals(SoundEvents.ENTITY_BAT_HURT) || event.getSound().equals(SoundEvents.ENTITY_BAT_LOOP) || event.getSound().equals(SoundEvents.ENTITY_BAT_TAKEOFF)) {
            event.setVolume(0.0f);
            event.setPitch(0.0f);
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onFireWorkSpawned(final EventEntitySpawn event) {
        if (event.getEntity() instanceof EntityFireworkRocket && this.fireworks.getValue()) {
            event.setCanceled(true);
        }
    }
    
    static {
        NoRender.INSTANCE = new NoRender();
        GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");
    }
    
    public enum Fog
    {
        NONE, 
        AIR, 
        NOFOG;
    }
    
    public enum Boss
    {
        NONE, 
        REMOVE, 
        STACK, 
        MINIMIZE;
    }
    
    public enum NoArmor
    {
        NONE, 
        ALL, 
        HELMET;
    }
}
