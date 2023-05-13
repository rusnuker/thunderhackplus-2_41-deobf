//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.util;

import javax.imageio.*;
import java.awt.image.*;
import java.net.*;
import java.util.concurrent.atomic.*;
import java.io.*;
import net.minecraft.client.network.*;
import java.util.*;

public class ThunderUtils
{
    private static final List<Pair<String, BufferedImage>> userCapes;
    
    public static void saveUserAvatar(final String s, final String nickname) {
        try {
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    ImageIO.write(img, "png", new File("ThunderHack/temp/heads/" + nickname + ".png"));
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static String solvename(final String notsolved) {
        final AtomicReference<String> mb = new AtomicReference<String>("err");
        final AtomicReference<String> atomicReference;
        Objects.requireNonNull(Util.mc.getConnection()).getPlayerInfoMap().forEach(player -> {
            if (notsolved.contains(player.getGameProfile().getName())) {
                atomicReference.set(player.getGameProfile().getName());
            }
            return;
        });
        return mb.get();
    }
    
    public static void savePlayerSkin(final String s, final String nickname) {
        try {
            final URL url = new URL(s);
            final URLConnection openConnection = url.openConnection();
            boolean check = true;
            try {
                openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                openConnection.connect();
                if (openConnection.getContentLength() > 8000000) {
                    System.out.println(" file size is too big.");
                    check = false;
                }
            }
            catch (Exception e) {
                System.out.println("Couldn't create a connection to the link, please recheck the link.");
                check = false;
                e.printStackTrace();
            }
            if (check) {
                BufferedImage img = null;
                try {
                    final InputStream in = new BufferedInputStream(openConnection.getInputStream());
                    final ByteArrayOutputStream out = new ByteArrayOutputStream();
                    final byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    final byte[] response = out.toByteArray();
                    img = ImageIO.read(new ByteArrayInputStream(response));
                }
                catch (Exception e2) {
                    System.out.println(" couldn't read an image from this link.");
                    e2.printStackTrace();
                }
                try {
                    ImageIO.write(img, "png", new File("ThunderHack/temp/skins/" + nickname + ".png"));
                }
                catch (IOException e3) {
                    System.out.println("Couldn't create/send the output image.");
                    e3.printStackTrace();
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public static BufferedImage getCustomCape(final String name) {
        for (final Pair<String, BufferedImage> donator : ThunderUtils.userCapes) {
            if (((String)donator.getKey()).equalsIgnoreCase(name)) {
                return (BufferedImage)donator.getValue();
            }
        }
        return null;
    }
    
    public static boolean isTHUser(final String name) {
        for (final Pair<String, BufferedImage> donator : ThunderUtils.userCapes) {
            if (((String)donator.getKey()).equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    public static void syncCapes() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ldc_w           Lcom/mrzak34/thunderhack/modules/client/MainSettings;.class
        //     6: invokevirtual   com/mrzak34/thunderhack/manager/ModuleManager.getModuleByClass:(Ljava/lang/Class;)Lcom/mrzak34/thunderhack/modules/Module;
        //     9: checkcast       Lcom/mrzak34/thunderhack/modules/client/MainSettings;
        //    12: getfield        com/mrzak34/thunderhack/modules/client/MainSettings.DownloadCapes:Lcom/mrzak34/thunderhack/setting/Setting;
        //    15: invokevirtual   com/mrzak34/thunderhack/setting/Setting.getValue:()Ljava/lang/Object;
        //    18: checkcast       Ljava/lang/Boolean;
        //    21: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //    24: ifne            28
        //    27: return         
        //    28: new             Ljava/lang/Thread;
        //    31: dup            
        //    32: invokedynamic   BootstrapMethod #1, run:()Ljava/lang/Runnable;
        //    37: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;)V
        //    40: invokevirtual   java/lang/Thread.start:()V
        //    43: return         
        //    StackMapTable: 00 01 1C
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:538)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:552)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:510)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$21(Deobfuscator3000.java:329)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        userCapes = new ArrayList<Pair<String, BufferedImage>>();
    }
}
