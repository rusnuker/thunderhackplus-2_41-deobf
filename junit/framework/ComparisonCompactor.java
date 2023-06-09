//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\rusherhack"!

//Decompiled by Procyon!

package junit.framework;

public class ComparisonCompactor
{
    private static final String ELLIPSIS = "...";
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";
    private int fContextLength;
    private String fExpected;
    private String fActual;
    private int fPrefix;
    private int fSuffix;
    
    public ComparisonCompactor(final int contextLength, final String expected, final String actual) {
        this.fContextLength = contextLength;
        this.fExpected = expected;
        this.fActual = actual;
    }
    
    public String compact(final String message) {
        if (this.fExpected == null || this.fActual == null || this.areStringsEqual()) {
            return Assert.format(message, (Object)this.fExpected, (Object)this.fActual);
        }
        this.findCommonPrefix();
        this.findCommonSuffix();
        final String expected = this.compactString(this.fExpected);
        final String actual = this.compactString(this.fActual);
        return Assert.format(message, (Object)expected, (Object)actual);
    }
    
    private String compactString(final String source) {
        String result = "[" + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + "]";
        if (this.fPrefix > 0) {
            result = this.computeCommonPrefix() + result;
        }
        if (this.fSuffix > 0) {
            result += this.computeCommonSuffix();
        }
        return result;
    }
    
    private void findCommonPrefix() {
        this.fPrefix = 0;
        final int end = Math.min(this.fExpected.length(), this.fActual.length());
        while (this.fPrefix < end && this.fExpected.charAt(this.fPrefix) == this.fActual.charAt(this.fPrefix)) {
            ++this.fPrefix;
        }
    }
    
    private void findCommonSuffix() {
        int expectedSuffix = this.fExpected.length() - 1;
        for (int actualSuffix = this.fActual.length() - 1; actualSuffix >= this.fPrefix && expectedSuffix >= this.fPrefix && this.fExpected.charAt(expectedSuffix) == this.fActual.charAt(actualSuffix); --actualSuffix, --expectedSuffix) {}
        this.fSuffix = this.fExpected.length() - expectedSuffix;
    }
    
    private String computeCommonPrefix() {
        return ((this.fPrefix > this.fContextLength) ? "..." : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
    }
    
    private String computeCommonSuffix() {
        final int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
        return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + ((this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength) ? "..." : "");
    }
    
    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }
}
