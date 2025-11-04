import javax.swing.*;
import java.awt.*;

public class Main {
    /**
     * Get a Unicode-compatible font that supports emojis
     */
    private static Font getUnicodeFont(String preferredFont, int style, int size) {
        // Try Unicode-compatible fonts (Windows)
        String[] unicodeFonts = {
            "Segoe UI Emoji",      // Best for emojis
            "Segoe UI Symbol",     // Good Unicode support
            "Segoe UI",            // Fallback
            "Arial Unicode MS",    // Universal Unicode
            "Lucida Sans Unicode", // Alternative
            "Dialog"               // System default
        };
        
        for (String fontName : unicodeFonts) {
            try {
                Font font = new Font(fontName, style, size);
                // Test if font can display common emojis
                if (font.canDisplay('📊') && font.canDisplay('💰') && font.canDisplay('👤')) {
                    return font;
                }
            } catch (Exception e) {
                // Try next font
            }
        }
        
        // Fallback to preferred font
        return new Font(preferredFont, style, size);
    }
    
    /**
     * Set UIManager defaults for Unicode-compatible fonts
     */
    private static void setupUnicodeFonts() {
        Font defaultFont = getUnicodeFont("Segoe UI", Font.PLAIN, 14);
        Font labelFont = getUnicodeFont("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = getUnicodeFont("Segoe UI", Font.PLAIN, 14);
        
        // Set default fonts for all Swing components
        UIManager.put("Label.font", labelFont);
        UIManager.put("Button.font", buttonFont);
        UIManager.put("TextField.font", defaultFont);
        UIManager.put("TextArea.font", defaultFont);
        UIManager.put("ComboBox.font", defaultFont);
        UIManager.put("List.font", defaultFont);
        UIManager.put("TabbedPane.font", defaultFont);
        UIManager.put("Menu.font", defaultFont);
        UIManager.put("MenuItem.font", defaultFont);
        UIManager.put("ToolTip.font", defaultFont);
        
        // Set default font for all components
        UIManager.put("defaultFont", defaultFont);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        // Setup Unicode-compatible fonts BEFORE creating UI
        setupUnicodeFonts();
        
        SwingUtilities.invokeLater(() -> {
            ui.MainWindow window = new ui.MainWindow();
            window.setVisible(true);
            
            // Show login dialog first
            if (!window.userManager.isLoggedIn()) {
                ui.components.AuthDialog authDialog = new ui.components.AuthDialog(window, window.userManager);
                authDialog.setVisible(true);
                
                // If user didn't login, close the app
                if (!window.userManager.isLoggedIn()) {
                    System.exit(0);
                }
            }
        });
    }
}
