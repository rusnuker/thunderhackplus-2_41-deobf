//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Shadow {
    String prefix() default "shadow$";
    
    boolean remap() default true;
    
    String[] aliases() default {};
}