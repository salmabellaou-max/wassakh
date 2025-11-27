import java.awt.*;

/**
 * UI Constants for MyWelly Application
 * Updated with modern, minimalistic, and aesthetic color scheme
 */
public class UIConstants {

    // Modern Color Palette - Minimalistic and Aesthetic
    public static final Color PRIMARY_COLOR = new Color(102, 126, 234);      // Soft Blue
    public static final Color PRIMARY_DARK = new Color(80, 102, 204);        // Darker Blue
    public static final Color PRIMARY_LIGHT = new Color(144, 164, 244);      // Lighter Blue

    public static final Color ACCENT_COLOR = new Color(94, 228, 195);        // Turquoise
    public static final Color ACCENT_DARK = new Color(74, 208, 175);         // Darker Turquoise

    public static final Color SUCCESS_COLOR = new Color(102, 187, 106);      // Green
    public static final Color WARNING_COLOR = new Color(255, 167, 38);       // Orange
    public static final Color ERROR_COLOR = new Color(239, 83, 80);          // Red
    public static final Color INFO_COLOR = new Color(66, 165, 245);          // Light Blue

    public static final Color BACKGROUND_COLOR = new Color(250, 251, 252);   // Very Light Gray
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color HOVER_COLOR = new Color(240, 242, 245);        // Light Gray
    public static final Color BORDER_COLOR = new Color(224, 224, 224);       // Medium Gray
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);          // Almost Black
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117);     // Gray
    public static final Color TEXT_HINT = new Color(158, 158, 158);          // Light Gray

    // Shadows and Effects
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 15);         // Subtle shadow
    public static final Color OVERLAY_COLOR = new Color(0, 0, 0, 50);        // Semi-transparent

    // Typography - Modern and Clean
    public static final Font LOGO_FONT = new Font("Segoe UI", Font.BOLD, 32);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BODY_BOLD_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font CAPTION_FONT = new Font("Segoe UI", Font.PLAIN, 11);

    // Spacing and Sizing
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XLARGE = 32;

    public static final int BORDER_RADIUS = 12;
    public static final int BORDER_RADIUS_SMALL = 8;
    public static final int BORDER_RADIUS_LARGE = 16;

    public static final int BUTTON_HEIGHT = 40;
    public static final int INPUT_HEIGHT = 40;
    public static final int CARD_ELEVATION = 2;

    // Animation
    public static final int ANIMATION_DURATION = 200; // milliseconds

    // Deprecated color constants for backward compatibility
    @Deprecated
    public static final Color PRIMARY_GREEN = PRIMARY_COLOR;
    @Deprecated
    public static final Color LIGHT_GREEN = PRIMARY_LIGHT;
    @Deprecated
    public static final Color VERY_LIGHT_GREEN = ACCENT_COLOR;
    @Deprecated
    public static final Color HOVER_GREEN = HOVER_COLOR;
    @Deprecated
    public static final Color SUCCESS_GREEN = SUCCESS_COLOR;
    @Deprecated
    public static final Color ERROR_RED = ERROR_COLOR;
    @Deprecated
    public static final Color SOFT_GRAY = TEXT_SECONDARY;

    // Application Metadata
    public static final String APP_NAME = "MyWelly";
    public static final String APP_VERSION = "2.0.0";
    public static final String APP_TAGLINE = "Your Health, Simplified";
}
