//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package junit.framework;

public interface TestListener
{
    void addError(final Test p0, final Throwable p1);
    
    void addFailure(final Test p0, final AssertionFailedError p1);
    
    void endTest(final Test p0);
    
    void startTest(final Test p0);
}
