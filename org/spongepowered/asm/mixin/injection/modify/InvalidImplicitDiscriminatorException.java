//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.*;

public class InvalidImplicitDiscriminatorException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidImplicitDiscriminatorException(final String message) {
        super(message);
    }
    
    public InvalidImplicitDiscriminatorException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
