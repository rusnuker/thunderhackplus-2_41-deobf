//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.manager;

import com.mrzak34.thunderhack.modules.*;
import com.mrzak34.thunderhack.util.shaders.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.entity.living.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.modules.client.*;
import com.mojang.realmsclient.gui.*;
import com.mrzak34.thunderhack.command.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.network.*;
import com.mrzak34.thunderhack.gui.thundergui2.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import com.mrzak34.thunderhack.setting.*;
import com.mrzak34.thunderhack.util.render.*;
import java.awt.*;
import com.mrzak34.thunderhack.util.*;
import com.mrzak34.thunderhack.gui.fontstuff.*;
import com.mrzak34.thunderhack.gui.hud.elements.*;
import com.mrzak34.thunderhack.modules.render.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.client.event.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.macro.*;
import java.util.*;
import net.minecraft.util.math.*;

public class EventManager
{
    public static Module hoveredModule;
    public static boolean serversprint;
    public static int backX;
    public static int backY;
    public static int backZ;
    public static boolean lock_sprint;
    public static BetterDynamicAnimation timerAnimation;
    public static boolean isMacro;
    private final Timer chorusTimer;
    Timer lastPacket;
    
    public EventManager() {
        this.chorusTimer = new Timer();
        this.lastPacket = new Timer();
    }
    
    public static void setColor(final int color) {
        GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
    }
    
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void onUnload() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onUpdate(final LivingEvent.LivingUpdateEvent event) {
        if (!fullNullCheck() && event.getEntity().getEntityWorld().isRemote && event.getEntityLiving().equals((Object)Util.mc.player)) {
            Thunderhack.moduleManager.onUpdate();
            Thunderhack.moduleManager.sortModules(true);
        }
        if (!fullNullCheck() && Thunderhack.moduleManager.getModuleByClass(ClickGui.class).getBind().getKey() == -1) {
            Command.sendMessage(ChatFormatting.RED + "Default clickgui keybind --> P");
            Thunderhack.moduleManager.getModuleByClass(ClickGui.class).setBind(Keyboard.getKeyIndex("P"));
        }
    }
    
    @SubscribeEvent
    public void onClientConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Thunderhack.moduleManager.onLogin();
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Thunderhack.moduleManager.onLogout();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (Util.mc.currentScreen instanceof GuiGameOver) {
            EventManager.backY = (int)Util.mc.player.posY;
            EventManager.backZ = (int)Util.mc.player.posZ;
            EventManager.backX = (int)Util.mc.player.posX;
        }
        Thunderhack.moduleManager.onTick();
        ThunderGui2.getInstance().onTick();
        EventManager.timerAnimation.update();
    }
    
    @SubscribeEvent
    public void onPlayer(final EventSync event) {
        if (fullNullCheck()) {
            return;
        }
        if (!this.lastPacket.passedMs(100L)) {
            Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).m();
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketPlayer.Position || e.getPacket() instanceof CPacketPlayer.PositionRotation || e.getPacket() instanceof CPacketPlayer.Rotation) {
            this.lastPacket.reset();
        }
        if (e.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction ent = (CPacketEntityAction)e.getPacket();
            if (ent.getAction() == CPacketEntityAction.Action.START_SPRINTING) {
                if (EventManager.lock_sprint) {
                    e.setCanceled(true);
                    return;
                }
                EventManager.serversprint = true;
            }
            if (ent.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                if (EventManager.lock_sprint) {
                    e.setCanceled(true);
                    return;
                }
                EventManager.serversprint = false;
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketEntityStatus) {
            final SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity((World)Util.mc.world) instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)packet.getEntity((World)Util.mc.world);
                MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player));
            }
        }
        if (event.getPacket() instanceof SPacketSoundEffect && ((SPacketSoundEffect)event.getPacket()).getSound() == SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT) {
            if (!this.chorusTimer.passedMs(100L)) {
                MinecraftForge.EVENT_BUS.post((Event)new ChorusEvent(((SPacketSoundEffect)event.getPacket()).getX(), ((SPacketSoundEffect)event.getPacket()).getY(), ((SPacketSoundEffect)event.getPacket()).getZ()));
            }
            this.chorusTimer.reset();
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        GL11.glPushAttrib(1048575);
        GL11.glDisable(2884);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GlStateManager.glLineWidth(1.0f);
        final Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
        Thunderhack.moduleManager.onRender3D(render3dEvent);
        GlStateManager.glLineWidth(1.0f);
        GL11.glPopAttrib();
    }
    
    @SubscribeEvent
    public void renderHUD(final RenderGameOverlayEvent.Post event) {
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (event.getType().equals((Object)RenderGameOverlayEvent.ElementType.TEXT)) {
            final boolean blend = GL11.glIsEnabled(3042);
            final boolean depth = GL11.glIsEnabled(2929);
            final ScaledResolution resolution = new ScaledResolution(Util.mc);
            final Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
            Thunderhack.moduleManager.onRender2D(render2DEvent);
            if (Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).indicator.getValue()) {
                final float posX = resolution.getScaledWidth() / 2.0f;
                final float posY = (float)(resolution.getScaledHeight() - Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).yyy.getValue());
                final Color a = com.mrzak34.thunderhack.modules.misc.Timer.TwoColoreffect(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color.getValue().getColorObject(), Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).slices.getValue() * 2.55) / 60.0);
                final Color b = com.mrzak34.thunderhack.modules.misc.Timer.TwoColoreffect(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color.getValue().getColorObject(), Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).slices1.getValue() * 2.55) / 60.0);
                final Color c = com.mrzak34.thunderhack.modules.misc.Timer.TwoColoreffect(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color.getValue().getColorObject(), Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).slices2.getValue() * 2.55) / 60.0);
                final Color d = com.mrzak34.thunderhack.modules.misc.Timer.TwoColoreffect(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color.getValue().getColorObject(), Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).color2.getValue().getColorObject(), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0 * (Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).slices3.getValue() * 2.55) / 60.0);
                RenderUtil.drawBlurredShadow(posX - 33.0f, posY - 3.0f, 66.0f, 16.0f, 10, a);
                float timerStatus = (float)(61.0 * ((10.0 - com.mrzak34.thunderhack.modules.misc.Timer.value) / (Math.abs(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).getMin()) + 10)));
                EventManager.timerAnimation.setValue(timerStatus);
                timerStatus = (float)EventManager.timerAnimation.getAnimationD();
                final int status = (int)((10.0 - com.mrzak34.thunderhack.modules.misc.Timer.value) / (Math.abs(Thunderhack.moduleManager.getModuleByClass(com.mrzak34.thunderhack.modules.misc.Timer.class).getMin()) + 10) * 100.0);
                RoundedShader.drawGradientRound(posX - 31.0f, posY, 62.0f, 12.0f, 3.0f, new Color(1), new Color(1), new Color(1), new Color(1));
                RoundedShader.drawGradientRound(posX - 30.5f, posY + 0.5f, timerStatus, 11.0f, 3.0f, a, b, c, d);
                FontRender.drawCentString6((status >= 99) ? "100%" : (status + "%"), resolution.getScaledWidth() / 2.0f, posY + 5.25f, new Color(200, 200, 200, 255).getRGB());
            }
            GlStateManager.resetColor();
            if (blend) {
                GL11.glEnable(3042);
            }
            if (depth) {
                GL11.glEnable(2929);
            }
            if (Thunderhack.gps_position != null) {
                final float xOffset = resolution.getScaledWidth() / 2.0f;
                final float yOffset = resolution.getScaledHeight() / 2.0f;
                GlStateManager.pushMatrix();
                final float yaw = RadarRewrite.getRotations(Thunderhack.gps_position) - Util.mc.player.rotationYaw;
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                Thunderhack.moduleManager.getModuleByClass(PearlESP.class).drawTriangle(xOffset, yOffset - 50.0f, 12.5f, ClickGui.getInstance().getColor(1).getRGB());
                GL11.glTranslatef(xOffset, yOffset, 0.0f);
                GL11.glRotatef(-yaw, 0.0f, 0.0f, 1.0f);
                GL11.glTranslatef(-xOffset, -yOffset, 0.0f);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.popMatrix();
                FontRender.drawCentString6("gps (" + this.getDistance(Thunderhack.gps_position) + "m)", (float)this.get_x(yaw) + xOffset, (float)(yOffset - this.get_y(yaw)) - 20.0f, -1);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            Thunderhack.moduleManager.onKeyPressed(Keyboard.getEventKey());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(final ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                Util.mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    Thunderhack.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                }
                else {
                    Command.sendMessage("\u041d\u0435\u0432\u0435\u0440\u043d\u0430\u044f \u043a\u043e\u043c\u0430\u043d\u0434\u0430!");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(ChatFormatting.RED + "\u041e\u0448\u0438\u0431\u043a\u0430 \u043a\u043e\u043c\u0430\u043d\u0434\u044b!");
            }
        }
    }
    
    @SubscribeEvent
    public void onKeyPress(final KeyEvent event) {
        if (event.getKey() == 0) {
            return;
        }
        if (Thunderhack.moduleManager.getModuleByClass(Macros.class).isEnabled()) {
            for (final Macro m : Thunderhack.macromanager.getMacros()) {
                if (m.getBind() == event.getKey()) {
                    EventManager.isMacro = true;
                    m.runMacro();
                    EventManager.isMacro = false;
                }
            }
        }
    }
    
    private double get_x(final double rad) {
        return Math.sin(Math.toRadians(rad)) * 50.0;
    }
    
    private double get_y(final double rad) {
        return Math.cos(Math.toRadians(rad)) * 50.0;
    }
    
    public int getDistance(final BlockPos bp) {
        final double d0 = Util.mc.player.posX - bp.getX();
        final double d2 = Util.mc.player.posZ - bp.getZ();
        return (int)MathHelper.sqrt(d0 * d0 + d2 * d2);
    }
    
    public static boolean fullNullCheck() {
        return Util.mc.player == null || Util.mc.world == null;
    }
    
    static {
        EventManager.serversprint = false;
        EventManager.lock_sprint = false;
        EventManager.timerAnimation = new BetterDynamicAnimation();
        EventManager.isMacro = false;
    }
}
