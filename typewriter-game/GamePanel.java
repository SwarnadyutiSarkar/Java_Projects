package com.typewritergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    private final int GAME_TIME = 60; // game time in seconds
    private final Timer timer;
    private int timeLeft;
    private int score;
    private String currentWord;
    private final JTextField inputField;
    private final JLabel wordLabel;
    private final JLabel timerLabel;
    private final JLabel scoreLabel;
    private final WordGenerator wordGenerator;

    public GamePanel() {
        setLayout(null);

        wordGenerator = new WordGenerator();

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Serif", Font.PLAIN, 32));
        wordLabel.setBounds(300, 200, 200, 50);
        add(wordLabel);

        inputField = new JTextField();
        inputField.setFont(new Font("Serif", Font.PLAIN, 32));
        inputField.setBounds(300, 300, 200, 50);
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                checkInput();
            }
        });
        add(inputField);

        timerLabel = new JLabel("Time left: " + GAME_TIME, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        timerLabel.setBounds(50, 20, 200, 50);
        add(timerLabel);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        scoreLabel.setBounds(550, 20, 200, 50);
        add(scoreLabel);

        timer = new Timer(1000, this);
        resetGame();
    }

    private void resetGame() {
        timeLeft = GAME_TIME;
        score = 0;
        updateTimerLabel();
        updateScoreLabel();
        newWord();
        timer.start();
    }

    private void newWord() {
        currentWord = wordGenerator.generateWord();
        wordLabel.setText(currentWord);
        inputField.setText("");
    }

    private void checkInput() {
        if (inputField.getText().equalsIgnoreCase(currentWord)) {
            score++;
            updateScoreLabel();
            newWord();
        }
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time left: " + timeLeft);
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (timeLeft > 0) {
            timeLeft--;
            updateTimerLabel();
        } else {
            timer.stop();
            wordLabel.setText("Game Over!");
            inputField.setEnabled(false);
        }
    }
}
