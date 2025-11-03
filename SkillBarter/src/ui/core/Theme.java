package ui.core;

import java.awt.*;

/**
 * Black & White Neon Theme - Sleek, Modern, Cyberpunk
 */
public class Theme {
    // Pure Black & White
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    
    // Backgrounds
    public static final Color BG_PRIMARY = BLACK;
    public static final Color BG_SECONDARY = new Color(10, 10, 10);
    public static final Color BG_CARD = new Color(20, 20, 20);
    public static final Color BG_HOVER = new Color(30, 30, 30);
    
    // Neon Colors
    public static final Color NEON_CYAN = new Color(0, 255, 255);
    public static final Color NEON_PURPLE = new Color(162, 0, 255);
    public static final Color NEON_PINK = new Color(255, 0, 162);
    public static final Color NEON_GREEN = new Color(0, 255, 162);
    public static final Color NEON_BLUE = new Color(0, 162, 255);
    public static final Color NEON_YELLOW = new Color(255, 255, 0);
    
    // Primary colors
    public static final Color PRIMARY = NEON_CYAN;
    public static final Color SECONDARY = NEON_PURPLE;
    public static final Color ACCENT = NEON_PINK;
    public static final Color SUCCESS = NEON_GREEN;
    
    // Text colors
    public static final Color TEXT_PRIMARY = WHITE;
    public static final Color TEXT_SECONDARY = new Color(200, 200, 200);
    public static final Color TEXT_MUTED = new Color(150, 150, 150);
    
    // Borders
    public static final Color BORDER_LIGHT = new Color(100, 100, 100);
    public static final Color BORDER_NEON = NEON_CYAN;
    
    // Fonts
    public static final Font FONT_MONO = new Font("Consolas", Font.PLAIN, 14);
    public static final Font FONT_LARGE = new Font("Consolas", Font.BOLD, 32);
    public static final Font FONT_TITLE = new Font("Consolas", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Consolas", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Consolas", Font.PLAIN, 14);
    
    // Spacing
    public static final int PADDING_SMALL = 8;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_LARGE = 24;
    public static final int PADDING_XL = 32;
    
    // Radius
    public static final int RADIUS_SMALL = 6;
    public static final int RADIUS_MEDIUM = 8;
    public static final int RADIUS_LARGE = 12;
    
    // Backward compatibility - Old naming conventions
    // Spacing (old names)
    public static final int spacingXS = PADDING_SMALL / 2;
    public static final int spacingS = PADDING_SMALL;
    public static final int spacingM = PADDING_MEDIUM;
    public static final int spacingL = PADDING_LARGE;
    public static final int spacingXL = PADDING_XL;
    public static final int spacingXXL = 48;
    
    // Radius (old names)
    public static final int radiusS = RADIUS_SMALL;
    public static final int radiusM = RADIUS_MEDIUM;
    public static final int radiusL = RADIUS_LARGE;
    public static final int radiusXL = 16;
    
    // Fonts (old names)
    public static final Font headingXL = FONT_LARGE;
    public static final Font headingL = new Font("Consolas", Font.BOLD, 28);
    public static final Font headingM = FONT_TITLE;
    public static final Font headingS = FONT_SUBTITLE;
    public static final Font bodyLarge = new Font("Consolas", Font.PLAIN, 16);
    public static final Font body = FONT_BODY;
    public static final Font bodySmall = new Font("Consolas", Font.PLAIN, 12);
    public static final Font button = FONT_BODY;
    public static final Font caption = new Font("Consolas", Font.PLAIN, 12);
    
    // Colors (old names)
    public static final Color bgPrimary = BG_PRIMARY;
    public static final Color bgSecondary = BG_SECONDARY;
    public static final Color bgCard = BG_CARD;
    public static final Color bgHover = BG_HOVER;
    public static final Color bgLight = new Color(25, 25, 25);
    public static final Color bgDark = new Color(5, 5, 5);
    
    public static final Color textPrimary = TEXT_PRIMARY;
    public static final Color textSecondary = TEXT_SECONDARY;
    public static final Color textMuted = TEXT_MUTED;
    public static final Color textInverse = WHITE;
    public static final Color textOnDark = WHITE;
    
    public static final Color primary = PRIMARY;
    public static final Color primaryDark = new Color(0, 200, 200);
    public static final Color primaryLight = new Color(100, 255, 255);
    public static final Color secondary = SECONDARY;
    public static final Color accent = ACCENT;
    
    public static final Color borderLight = BORDER_LIGHT;
    public static final Color borderMedium = new Color(150, 150, 150);
    public static final Color borderDark = new Color(50, 50, 50);
    
    // Glass effects (backward compatibility)
    public static final Color glassLight = new Color(255, 255, 255, 40); // Translucent white for glass effect
    public static final Color glassDark = new Color(20, 20, 20, 200); // Translucent dark for glass effect
    public static final Color glassOverlay = new Color(255, 255, 255, 30); // Overlay for glassmorphism
    
    // Gradient colors (backward compatibility)
    public static final Color gradientStart = NEON_CYAN;
    public static final Color gradientAccent = NEON_PURPLE;
    public static final Color gradientEnd = NEON_PINK;
    
    public static final Color rating = NEON_YELLOW;
    
    // Gradient helpers (old names)
    public static java.awt.GradientPaint getPrimaryGradient(int width, int height) {
        return new java.awt.GradientPaint(0, 0, NEON_CYAN, width, height, NEON_PURPLE);
    }
    
    public static java.awt.RadialGradientPaint getGlowGradient(int width, int height, Color centerColor) {
        return new java.awt.RadialGradientPaint(
            width / 2, height / 2, Math.max(width, height) / 2,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 180),
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 80),
                new Color(centerColor.getRed(), centerColor.getGreen(), centerColor.getBlue(), 0)
            }
        );
    }
    
    public static java.awt.LinearGradientPaint getVibrantGradient(int width, int height) {
        return new java.awt.LinearGradientPaint(
            0, 0, width, height,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{NEON_CYAN, NEON_PURPLE, NEON_PINK}
        );
    }
    
    public static java.awt.RadialGradientPaint getRadialGradient(int centerX, int centerY, int radius) {
        return new java.awt.RadialGradientPaint(
            centerX, centerY, radius,
            new float[]{0.0f, 0.7f, 1.0f},
            new Color[]{
                new Color(NEON_CYAN.getRed(), NEON_CYAN.getGreen(), NEON_CYAN.getBlue(), 255),
                new Color(NEON_PURPLE.getRed(), NEON_PURPLE.getGreen(), NEON_PURPLE.getBlue(), 150),
                new Color(NEON_PURPLE.getRed(), NEON_PURPLE.getGreen(), NEON_PURPLE.getBlue(), 0)
            }
        );
    }
    
    public static java.awt.LinearGradientPaint getHeroGradient(int width, int height) {
        return new java.awt.LinearGradientPaint(
            0, 0, width, height,
            new float[]{0.0f, 0.5f, 1.0f},
            new Color[]{
                new Color(NEON_CYAN.getRed(), NEON_CYAN.getGreen(), NEON_CYAN.getBlue(), 100),
                new Color(NEON_PURPLE.getRed(), NEON_PURPLE.getGreen(), NEON_PURPLE.getBlue(), 80),
                new Color(NEON_PINK.getRed(), NEON_PINK.getGreen(), NEON_PINK.getBlue(), 60)
            }
        );
    }
    
    public static java.awt.GradientPaint getCardGradient(int width, int height, Color startColor, Color endColor) {
        return new java.awt.GradientPaint(0, 0, startColor, width, height, endColor);
    }
    
    public static java.awt.GradientPaint getSubtleGradient(int width, int height) {
        return new java.awt.GradientPaint(
            0, 0, BG_CARD,
            width, height, new Color(BG_CARD.getRed() + 5, BG_CARD.getGreen() + 5, BG_CARD.getBlue() + 5)
        );
    }
}

