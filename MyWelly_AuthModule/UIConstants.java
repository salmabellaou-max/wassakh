import java.awt.Font;
import java.awt.Color;

// 
// Decompiled by Procyon v0.6.0
// 

class UIConstants
{
    public static final Color PRIMARY_GREEN;
    public static final Color LIGHT_GREEN;
    public static final Color VERY_LIGHT_GREEN;
    public static final Color HOVER_GREEN;
    public static final Color SUCCESS_GREEN;
    public static final Color ERROR_RED;
    public static final Color SOFT_GRAY;
    public static final Font TITLE_FONT;
    public static final Font HEADER_FONT;
    public static final Font SUBHEADER_FONT;
    public static final Font BODY_FONT;
    public static final Font APP_NAME_FONT;
    public static final Font LOGO_FONT;
    
    static {
        PRIMARY_GREEN = new Color(0, 100, 63);
        LIGHT_GREEN = new Color(46, 125, 50);
        VERY_LIGHT_GREEN = new Color(232, 245, 233);
        HOVER_GREEN = new Color(27, 94, 32);
        SUCCESS_GREEN = new Color(76, 175, 80);
        ERROR_RED = new Color(211, 47, 47);
        SOFT_GRAY = new Color(245, 245, 245);
        TITLE_FONT = new Font("Segoe UI", 1, 36);
        HEADER_FONT = new Font("Segoe UI", 1, 24);
        SUBHEADER_FONT = new Font("Segoe UI", 1, 18);
        BODY_FONT = new Font("Segoe UI", 0, 14);
        APP_NAME_FONT = new Font("Segoe Script", 1, 48);
        LOGO_FONT = new Font("Segoe Script", 1, 64);
    }
}
