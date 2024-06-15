package com.typewritergame;

import javax.swing.*;
import java.awt.*;

public class TypewriterGame extends JFrame {

    public TypewriterGame() {
        initUI();
    }

    private void initUI() {
        setTitle("Typewriter Game");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new GamePanel());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            TypewriterGame game = new TypewriterGame();
            game.setVisible(true);
        });
    }
}
