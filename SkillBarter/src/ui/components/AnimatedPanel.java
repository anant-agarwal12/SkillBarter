package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel with smooth hover animations using Timer for microinteractions
 */
public class AnimatedPanel extends JPanel {
    private float hoverProgress = 0.0f;
    private boolean hovered = false;
    private Timer animationTimer;
    private static final int ANIMATION_DURATION = 150; // milliseconds
    private static final int FRAMES_PER_SECOND = 30; // Reduced from 60 for better performance
    
    public AnimatedPanel() {
        setOpaque(false);
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                hovered = true;
                startAnimation();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                hovered = false;
                startAnimation();
            }
        });
    }
    
    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        final long startTime = System.currentTimeMillis();
        animationTimer = new Timer(1000 / FRAMES_PER_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - startTime;
                float progress = Math.min(elapsed / (float) ANIMATION_DURATION, 1.0f);
                
                // Smooth easing function (ease-in-out)
                float easedProgress;
                if (progress < 0.5f) {
                    easedProgress = 2 * progress * progress;
                } else {
                    easedProgress = 1 - (float) Math.pow(-2 * progress + 2, 2) / 2;
                }
                
                hoverProgress = hovered ? easedProgress : 1.0f - easedProgress;
                
                if (progress >= 1.0f) {
                    hoverProgress = hovered ? 1.0f : 0.0f;
                    animationTimer.stop();
                }
                
                repaint();
            }
        });
        
        animationTimer.start();
    }
    
    public float getHoverProgress() {
        return hoverProgress;
    }
    
    public boolean isHovered() {
        return hovered;
    }
}

