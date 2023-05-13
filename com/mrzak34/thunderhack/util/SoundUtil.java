//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import net.minecraft.util.*;
import sun.audio.*;
import java.io.*;

public class SoundUtil
{
    public static final SoundUtil INSTANCE;
    public static ResourceLocation ON_SOUND;
    public static ResourceLocation OFF_SOUND;
    public static ResourceLocation SUCCESS_SOUND;
    public static ResourceLocation ERROR_SOUND;
    
    public static void playSound(final ThunderSound snd) {
        ResourceLocation resourceLocation = null;
        switch (snd) {
            case ON: {
                resourceLocation = SoundUtil.ON_SOUND;
                break;
            }
            case OFF: {
                resourceLocation = SoundUtil.OFF_SOUND;
                break;
            }
            case SUCCESS: {
                resourceLocation = SoundUtil.SUCCESS_SOUND;
                break;
            }
            case ERROR: {
                resourceLocation = SoundUtil.ERROR_SOUND;
                break;
            }
        }
        try {
            AudioPlayer.player.start(new AudioStream(Util.mc.getResourceManager().getResource(resourceLocation).getInputStream()));
        }
        catch (Exception ex) {}
    }
    
    static {
        INSTANCE = new SoundUtil();
        SoundUtil.ON_SOUND = new ResourceLocation("sounds/on.wav");
        SoundUtil.OFF_SOUND = new ResourceLocation("sounds/off.wav");
        SoundUtil.SUCCESS_SOUND = new ResourceLocation("sounds/success.wav");
        SoundUtil.ERROR_SOUND = new ResourceLocation("sounds/error.wav");
    }
    
    public enum ThunderSound
    {
        ON, 
        OFF, 
        ERROR, 
        SUCCESS;
    }
}
