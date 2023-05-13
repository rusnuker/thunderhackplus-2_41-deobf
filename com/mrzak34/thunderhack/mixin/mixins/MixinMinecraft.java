//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.mixin.mixins;

import com.mrzak34.thunderhack.util.phobos.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.crash.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mrzak34.thunderhack.*;
import com.mrzak34.thunderhack.gui.mainmenu.*;
import com.mrzak34.thunderhack.util.*;
import org.lwjgl.input.*;
import com.mrzak34.thunderhack.events.*;
import com.mrzak34.thunderhack.modules.client.*;
import net.minecraft.client.gui.*;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft implements InterfaceMinecraft
{
    @Shadow
    @Nullable
    public GuiScreen currentScreen;
    private int gameLoop;
    
    public MixinMinecraft() {
        this.gameLoop = 0;
    }
    
    @Inject(method = { "shutdownMinecraftApplet" }, at = { @At("HEAD") })
    private void stopClient(final CallbackInfo callbackInfo) {
        this.unload();
    }
    
    @Redirect(method = { "run" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReport(final Minecraft minecraft, final CrashReport crashReport) {
        this.unload();
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE", remap = false, target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 0, shift = At.Shift.BEFORE) })
    private void onKeyboard(final CallbackInfo callbackInfo) {
        final int n;
        final int i = n = ((Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + '\u0100') : Keyboard.getEventKey());
        if (Keyboard.getEventKeyState()) {
            final KeyEvent event = new KeyEvent(i);
            MinecraftForge.EVENT_BUS.post((Event)event);
        }
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At("HEAD") })
    private void runGameLoopHead(final CallbackInfo callbackInfo) {
        ++this.gameLoop;
    }
    
    @Inject(method = { "middleClickMouse" }, at = { @At("HEAD") }, cancellable = true)
    public void middleClickMouseHook(final CallbackInfo callbackInfo) {
        final ClickMiddleEvent event = new ClickMiddleEvent();
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "runTick()V" }, at = { @At("RETURN") })
    private void runTick(final CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && Thunderhack.moduleManager != null && ((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).mainMenu.getValue()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new ThunderMenu());
        }
    }
    
    @Inject(method = { "displayGuiScreen" }, at = { @At("HEAD") })
    private void displayGuiScreenHook(final GuiScreen screen, final CallbackInfo ci) {
        if (screen instanceof GuiMainMenu && Thunderhack.moduleManager != null && ((MainSettings)Thunderhack.moduleManager.getModuleByClass((Class)MainSettings.class)).mainMenu.getValue()) {
            Util.mc.displayGuiScreen((GuiScreen)new ThunderMenu());
        }
    }
    
    @Inject(method = { "runTickMouse" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I", remap = false) })
    public void runTickMouseHook(final CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new MouseEvent(Mouse.getEventButton(), Mouse.getEventButtonState()));
    }
    
    @Inject(method = { "runTick" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;tick()V", shift = At.Shift.AFTER) })
    private void postUpdateWorld(final CallbackInfo info) {
        MinecraftForge.EVENT_BUS.post((Event)new PostWorldTick());
    }
    
    @Inject(method = { "runGameLoop" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void post_ScheduledTasks(final CallbackInfo callbackInfo) {
        MinecraftForge.EVENT_BUS.post((Event)new GameZaloopEvent());
    }
    
    @Override
    public int getGameLoop() {
        return this.gameLoop;
    }
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false) })
    public void runTickKeyboardHook(final CallbackInfo callbackInfo) {
        MinecraftForge.EVENT_BUS.post((Event)new KeyboardEvent(Keyboard.getEventKeyState(), Keyboard.getEventKey(), Keyboard.getEventCharacter()));
    }
    
    @Redirect(method = { "runGameLoop" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shutdown()V"))
    private void Method5080(final Minecraft minecraft) {
        if (minecraft.world != null && ((AntiDisconnect)Thunderhack.moduleManager.getModuleByClass((Class)AntiDisconnect.class)).isOn()) {
            final GuiScreen screen = minecraft.currentScreen;
            final GuiYesNo g = new GuiYesNo((result, id) -> {
                if (result) {
                    minecraft.shutdown();
                }
                else {
                    Minecraft.getMinecraft().displayGuiScreen(screen);
                }
            }, "\u0422\u044b \u0442\u043e\u0447\u043d\u043e \u0445\u043e\u0447\u0435\u0448\u044c \u0437\u0430\u043a\u0440\u044b\u0442\u044c \u043c\u0430\u0439\u043d?", "", 0);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)g);
        }
        else {
            minecraft.shutdown();
        }
    }
    
    private void unload() {
        Thunderhack.unload(false);
    }
}
