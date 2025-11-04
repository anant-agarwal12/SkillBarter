# UI Components Directory

This directory contains various UI components for the SkillBarter application.

## Currently Used Components

### Core Components (Active)
- **`ProfessionalCard.java`** - Clean, professional card component used throughout the application
- **`ModernButton.java`** - Modern button with multiple styles (PRIMARY, SECONDARY, OUTLINE, GHOST) and emoji support
- **`AnimatedPanel.java`** - Base panel with smooth hover animations
- **`StatCard.java`** - Card component for displaying statistics
- **`SkillCard.java`** - Card component for displaying skill information
- **`CalendarDialog.java`** - Calendar picker dialog
- **`LogoPanel.java`** - Custom logo component
- **`LegacyUserAuthDialog.java`** - User authentication dialog

## Unused/Deprecated Components

The following components exist but are **not currently used** in any panels:

### Alternative Components (Not in Use)
- **`GlassCard.java`** - Glassmorphism-style card with translucent effects
  - **Status**: Created for advanced UI but not integrated
  - **Reason**: Replaced by `ProfessionalCard` for consistency
  
- **`FloatingCard.java`** - Floating card with animated shadows and glow
  - **Status**: Created for animated UI but not integrated
  - **Reason**: Replaced by `ProfessionalCard` for performance
  
- **`VibrantButton.java`** - Vibrant button with glow effects
  - **Status**: Created for bold UI but not integrated
  - **Reason**: Replaced by `ModernButton` for consistency
  
- **`HeroPanel.java`** - Hero section panel with gradient backgrounds
  - **Status**: Created for landing page but not integrated
  - **Reason**: Not needed in current panel structure
  
- **`NeonCard.java`** - Neon-themed card with glowing borders
  - **Status**: Created for neon theme but not integrated
  - **Reason**: Theme changed to professional design
  
- **`NeonButton.java`** - Neon-themed button with glow effects
  - **Status**: Created for neon theme but not integrated
  - **Reason**: Theme changed to professional design
  
- **`PremiumSkillCard.java`** - Premium skill card with advanced styling
  - **Status**: Created but not integrated
  - **Reason**: Using `SkillCard` for consistency
  
- **`EnhancedSkillCard.java`** - Enhanced skill card with additional features
  - **Status**: Created but not integrated
  - **Reason**: Using `SkillCard` for consistency

## Recommendation

If these unused components are not needed for future features, consider:
1. **Removing them** to reduce codebase complexity
2. **Moving them** to a `deprecated/` or `legacy/` folder
3. **Documenting** their intended use case if they're planned for future use

## Component Usage in Panels

All panels (`DashboardPanel`, `SessionsPanel`, `MarketplacePanel`, `WalletPanel`, `ProfilePanel`) use:
- `ProfessionalCard` for card containers
- `ModernButton` for buttons
- Standard Swing components for other UI elements

