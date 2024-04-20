import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlappyBirdGame extends JFrame implements ActionListener, KeyListener {

    private final int WIDTH = 800, HEIGHT = 600;
    private int birdX = WIDTH / 2, birdY = HEIGHT / 2;
    private int birdVelocity = 0;
    private final int GRAVITY = 2;
    private final int PIPE_WIDTH = 100, PIPE_GAP = 200;
    private List<Integer> pipeXs = new ArrayList<>();
    private List<Integer> pipeYs = new ArrayList<>();
    private Timer timer;
    private Random random = new Random();
    private boolean isGameOver = false;

    public FlappyBirdGame() {
        setTitle("Flappy Bird Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();

        pipeXs.add(WIDTH);
        pipeYs.add(random.nextInt(HEIGHT - 200));
        pipeXs.add(WIDTH + PIPE_WIDTH + PIPE_GAP);
        pipeYs.add(random.nextInt(HEIGHT - 200));

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.GREEN);
        for (int i = 0; i < pipeXs.size(); i++) {
            g.fillRect(pipeXs.get(i), 0, PIPE_WIDTH, pipeYs.get(i));
            g.fillRect(pipeXs.get(i), pipeYs.get(i) + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeYs.get(i) - PIPE_GAP);
        }

        g.setColor(Color.RED);
        g.fillRect(birdX, birdY, 50, 50);

        if (isGameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < pipeXs.size(); i++) {
            pipeXs.set(i, pipeXs.get(i) - 5);
        }

        if (pipeXs.get(0) < -PIPE_WIDTH) {
            pipeXs.remove(0);
            pipeYs.remove(0);
            pipeXs.add(WIDTH);
            pipeYs.add(random.nextInt(HEIGHT - 200));
        }

        birdVelocity += GRAVITY;
        birdY += birdVelocity;

        checkCollision();

        repaint();
    }

    private void checkCollision() {
        Rectangle birdRect = new Rectangle(birdX, birdY, 50, 50);

        for (int i = 0; i < pipeXs.size(); i++) {
            Rectangle pipeRectTop = new Rectangle(pipeXs.get(i), 0, PIPE_WIDTH, pipeYs.get(i));
            Rectangle pipeRectBottom = new Rectangle(pipeXs.get(i), pipeYs.get(i) + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeYs.get(i) - PIPE_GAP);

            if (birdRect.intersects(pipeRectTop) || birdRect.intersects(pipeRectBottom) || birdY > HEIGHT || birdY < 0) {
                isGameOver = true;
                timer.stop();
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
            birdVelocity = -20;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && isGameOver) {
            resetGame();
        }
    }

    private void resetGame() {
        birdX = WIDTH / 2;
        birdY = HEIGHT / 2;
        birdVelocity = 0;
        pipeXs.clear();
        pipeYs.clear();
        pipeXs.add(WIDTH);
        pipeYs.add(random.nextInt(HEIGHT - 200));
        pipeXs.add(WIDTH + PIPE_WIDTH + PIPE_GAP);
        pipeYs.add(random.nextInt(HEIGHT - 200));
        isGameOver = false;
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new FlappyBirdGame();
    }
}
