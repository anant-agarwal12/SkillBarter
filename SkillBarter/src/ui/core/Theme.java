package ui.core;

import java.awt.*;

/**
 * Professional Skill Exchange Platform Theme
 * LinkedIn Learning + Duolingo + Airbnb aesthetic
 */
public class Theme {
    // Primary Colors - Deep Navy
    public static final Color NAVY_PRIMARY = new Color(25, 35, 60);
    public static final Color NAVY_DARK = new Color(15, 22, 40);
    public static final Color NAVY_LIGHT = new Color(35, 47, 75);
    
    // Backgrounds
    public static final Color BG_PRIMARY = new Color(248, 250, 252); // Light gray-white
    public static final Color BG_SECONDARY = new Color(255, 255, 255);
    public static final Color BG_CARD = new Color(255, 255, 255);
    public static final Color BG_HOVER = new Color(245, 247, 250);
    public static final Color BG_DARK = NAVY_PRIMARY;
    
    // Accent Colors
    public static final Color CORAL_ACCENT = new Color(255, 107, 107); // Warm coral
    public static final Color CORAL_LIGHT = new Color(255, 140, 140);
    public static final Color CORAL_DARK = new Color(220, 85, 85);
    
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);
    public static final Color SUCCESS_LIGHT = new Color(74, 222, 128);
    public static final Color SUCCESS_DARK = new Color(22, 163, 74);
    
    public static final Color INFO_BLUE = new Color(59, 130, 246);
    public static final Color WARNING_YELLOW = new Color(234, 179, 8);
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(71, 85, 105);
    public static final Color TEXT_MUTED = new Color(148, 163, 184);
    public static final Color TEXT_LIGHT = new Color(255, 255, 255);
    public static final Color TEXT_ON_DARK = new Color(248, 250, 252);
    
    // Borders
    public static final Color BORDER_LIGHT = new Color(226, 232, 240);
    public static final Color BORDER_MEDIUM = new Color(203, 213, 225);
    public static final Color BORDER_DARK = new Color(148, 163, 184);
    
    // Fonts - Modern Typography with Unicode/Emoji Support
    private static Font getUnicodeCompatibleFont(String fontName, int style, int size) {
        // Try Unicode-compatible fonts first (Windows)
        String[] unicodeFonts = {
            "Segoe UI Emoji",      // Best for emojis on Windows
            "Segoe UI Symbol",     // Good Unicode support
            "Segoe UI",            // Fallback
            "Arial Unicode MS",    // Universal Unicode support
            "Lucida Sans Unicode", // Alternative
            "Dialog"               // System default
        };
        
        Font font = null;
        for (String fontNameToTry : unicodeFonts) {
            try {
                font = new Font(fontNameToTry, style, size);
                if (font.canDisplay('📊') && font.canDisplay('💰') && font.canDisplay('👤')) {
                    // This font supports emojis
                    break;
                }
            } catch (Exception e) {
                // Try next font
            }
        }
        
        // If no Unicode font found, use the requested font
        if (font == null) {
            font = new Font(fontName, style, size);
        }
        
        return font;
    }
    
    public static final Font FONT_LARGE = getUnicodeCompatibleFont("Segoe UI", Font.BOLD, 32);
    public static final Font FONT_TITLE = getUnicodeCompatibleFont("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = getUnicodeCompatibleFont("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = getUnicodeCompatibleFont("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = getUnicodeCompatibleFont("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_CAPTION = getUnicodeCompatibleFont("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 14);
    
    // Unicode-compatible font for emoji-heavy components
    public static Font getEmojiFont(int size) {
        return getUnicodeCompatibleFont("Segoe UI Emoji", Font.PLAIN, size);
    }
    
    // Spacing
    public static final int PADDING_XS = 4;
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XL = 32;
    public static final int PADDING_XXL = 48;
    
    // Radius - Rounded corners
    public static final int RADIUS_SMALL = 6;
    public static final int RADIUS_MEDIUM = 8;
    public static final int RADIUS_LARGE = 12;
    public static final int RADIUS_XL = 16;
    
    // Backward compatibility aliases
    public static final int spacingXS = PADDING_XS;
    public static final int spacingS = PADDING_SMALL;
    public static final int spacingM = PADDING_MEDIUM;
    public static final int spacingL = PADDING_LARGE;
    public static final int spacingXL = PADDING_XL;
    public static final int spacingXXL = PADDING_XXL;
    
    public static final int radiusS = RADIUS_SMALL;
    public static final int radiusM = RADIUS_MEDIUM;
    public static final int radiusL = RADIUS_LARGE;
    public static final int radiusXL = RADIUS_XL;
    
    public static final Color bgPrimary = BG_PRIMARY;
    public static final Color bgSecondary = BG_SECONDARY;
    public static final Color bgCard = BG_CARD;
    public static final Color bgHover = BG_HOVER;
    public static final Color bgLight = BG_SECONDARY;
    public static final Color bgDark = BG_DARK;
    
    public static final Color textPrimary = TEXT_PRIMARY;
    public static final Color textSecondary = TEXT_SECONDARY;
    public static final Color textMuted = TEXT_MUTED;
    public static final Color textInverse = TEXT_LIGHT;
    public static final Color textOnDark = TEXT_ON_DARK;
    
    public static final Color primary = NAVY_PRIMARY;
    public static final Color primaryDark = NAVY_DARK;
    public static final Color primaryLight = NAVY_LIGHT;
    public static final Color secondary = CORAL_ACCENT;
    public static final Color accent = CORAL_ACCENT;
    public static final Color success = SUCCESS_GREEN;
    
    public static final Color borderLight = BORDER_LIGHT;
    public static final Color borderMedium = BORDER_MEDIUM;
    public static final Color borderDark = BORDER_DARK;
    
    public static final Color rating = WARNING_YELLOW;
    
    // Backward compatibility - Neon colors (aliases for old components)
    public static final Color NEON_CYAN = INFO_BLUE;
    public static final Color NEON_PURPLE = new Color(139, 92, 246);
    public static final Color NEON_PINK = CORAL_ACCENT;
    public static final Color NEON_GREEN = SUCCESS_GREEN;
    public static final Color NEON_YELLOW = WARNING_YELLOW;
    public static final Color NEON_BLUE = INFO_BLUE;
    public static final Color BORDER_NEON = INFO_BLUE;
    
    public static final Font headingXL = FONT_LARGE;
    public static final Font headingL = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font headingM = FONT_TITLE;
    public static final Font headingS = FONT_SUBTITLE;
    public static final Font bodyLarge = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font body = FONT_BODY;
    public static final Font bodySmall = FONT_SMALL;
    public static final Font button = FONT_BODY;
    public static final Font caption = FONT_CAPTION;
    
    // Glass effects (backward compatibility)
    public static final Color glassLight = new Color(255, 255, 255, 180);
    public static final Color glassDark = new Color(25, 35, 60, 200);
    public static final Color glassOverlay = new Color(255, 255, 255, 120);
    
    // Gradient colors (backward compatibility)
    public static final Color gradientStart = INFO_BLUE;
    public static final Color gradientAccent = CORAL_ACCENT;
    public static final Color gradientEnd = SUCCESS_GREEN;
    
    // Gradient helpers
    public static java.awt.GradientPaint getPrimaryGradient(int width, int height) {
        return new java.awt.GradientPaint(0, 0, NAVY_PRIMARY, width, height, NAVY_LIGHT);
    }
    
    public static java.awt.RadialGradientPaint getGlowGradient(int width, int height, Color centerColor) {
        return new java.awt.RadialGradientPaint(
            width / 2, height / 2, Math.max(width, height) / 2,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 120),
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 60),
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 0)
            }
        );
    }
    
    public static java.awt.LinearGradientPaint getVibrantGradient(int width, int height) {
        return new java.awt.LinearGradientPaint(
            0, 0, width, height,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{INFO_BLUE, CORAL_ACCENT, SUCCESS_GREEN}
        );
    }
    
    public static java.awt.RadialGradientPaint getRadialGradient(int centerX, int centerY, int radius) {
        return new java.awt.RadialGradientPaint(
            centerX, centerY, radius,
            new float[]{0.0f, 0.7f, 1.0f},
            new Color[]{
                new Color(INFO_BLUE.getRed(), INFO_BLUE.getGreen(), INFO_BLUE.getBlue(), 200),
                new Color(CORAL_ACCENT.getRed(), CORAL_ACCENT.getGreen(), CORAL_ACCENT.getBlue(), 100),
                new Color(CORAL_ACCENT.getRed(), CORAL_ACCENT.getGreen(), CORAL_ACCENT.getBlue(), 0)
            }
        );
    }
    
    public static java.awt.LinearGradientPaint getHeroGradient(int width, int height) {
        return new java.awt.LinearGradientPaint(
            0, 0, width, height,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(INFO_BLUE.getRed(), INFO_BLUE.getGreen(), INFO_BLUE.getBlue(), 80),
                new Color(CORAL_ACCENT.getRed(), CORAL_ACCENT.getGreen(), CORAL_ACCENT.getBlue(), 60),
                new Color(SUCCESS_GREEN.getRed(), SUCCESS_GREEN.getGreen(), SUCCESS_GREEN.getBlue(), 40)
            }
        );
    }
    
    public static java.awt.GradientPaint getCardGradient(int width, int height, Color startColor, Color endColor) {
        return new java.awt.GradientPaint(0, 0, startColor, width, height, endColor);
    }
    
    public static java.awt.GradientPaint getSubtleGradient(int width, int height) {
        return new java.awt.GradientPaint(
            0, 0, BG_CARD,
            width, height, new Color(BG_CARD.getRed() - 5, BG_CARD.getGreen() - 5, BG_CARD.getBlue() - 5)
        );
    }
}