//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fml.client.*;
import java.awt.image.*;
import com.mrzak34.thunderhack.modules.misc.*;
import com.mrzak34.thunderhack.*;

public class PNGtoResourceLocation
{
    private static final HashMap<String, ResourceLocation> image_cache;
    
    public static ResourceLocation getTexture2(final String name, final String format) {
        if (PNGtoResourceLocation.image_cache.containsKey(name)) {
            return PNGtoResourceLocation.image_cache.get(name);
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/temp/heads/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        PNGtoResourceLocation.image_cache.put(name, wr.location);
        return wr.location;
    }
    
    public static ResourceLocation getCustomImg(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/images/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    public static ResourceLocation getTexture3(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/temp/skins/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    public static ResourceLocation getTexture(final String name, final String format) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("ThunderHack/temp/embeds/" + name + "." + format));
        }
        catch (Exception e) {
            return null;
        }
        final float ratio = bufferedImage.getWidth() / (float)bufferedImage.getHeight();
        DiscordEmbeds.nigw = (float)(int)((DiscordEmbeds)Thunderhack.moduleManager.getModuleByClass((Class)DiscordEmbeds.class)).multip.getValue();
        DiscordEmbeds.nigh = (int)((DiscordEmbeds)Thunderhack.moduleManager.getModuleByClass((Class)DiscordEmbeds.class)).multip.getValue() / ratio;
        final DynamicTexture texture = new DynamicTexture(bufferedImage);
        final WrappedResource wr = new WrappedResource(FMLClientHandler.instance().getClient().getTextureManager().getDynamicTextureLocation(name + "." + format, texture));
        return wr.location;
    }
    
    static {
        image_cache = new HashMap<String, ResourceLocation>();
    }
    
    public static class WrappedResource
    {
        public final ResourceLocation location;
        
        public WrappedResource(final ResourceLocation location) {
            this.location = location;
        }
    }
}
