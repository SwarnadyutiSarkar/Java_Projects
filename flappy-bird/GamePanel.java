import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private int birdY = 250;
    private int birdVelocity = 0;
    private final int GRAVITY = 1;
    private final int JUMP_STRENGTH = 15;
    private final int BIRD_SIZE = 20;

    private ArrayList<Rectangle2D> pipes;
    private Random random;
    private int pipeGap = 200;
    private int pipeWidth = 50;
    private int pipeSpeed = 5;
    private int score = 0;

    public GamePanel() {
        timer = new Timer(20, this);
        pipes = new ArrayList<>();
        random = new Random();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    birdVelocity = -JUMP_STRENGTH;
                }
            }
        });
        setFocusable(true);
    }

    public void startGame() {
        generatePipes();
        timer.start();
    }

    private void generatePipes() {
        for (int i = 0; i < 4; i++) {
            int pipeHeight = 100 + random.nextInt(200);
            pipes.add(new Rectangle2D.Double(800 + i * 200, 0, pipeWidth, pipeHeight));
            pipes.add(new Rectangle2D.Double(800 + i * 200, pipeHeight + pipeGap, pipeWidth, 600 - pipeHeight - pipeGap));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        birdY += birdVelocity;
        birdVelocity += GRAVITY;

        for (int i = 0; i < pipes.size(); i++) {
            Rectangle2D pipe = pipes.get(i);
            pipe.setRect(pipe.getX() - pipeSpeed, pipe.getY(), pipe.getWidth(), pipe.getHeight());
        }

        if (pipes.get(0).getX() + pipeWidth < 0) {
            pipes.remove(0);
            pipes.remove(0);

            int pipeHeight = 100 + random.nextInt(200);
            pipes.add(new Rectangle2D.Double(800, 0, pipeWidth, pipeHeight));
            pipes.add(new Rectangle2D.Double(800, pipeHeight + pipeGap, pipeWidth, 600 - pipeHeight - pipeGap));
            score++;
        }

        if (birdY > 600 || birdY < 0 || checkCollision()) {
            timer.stop();
        }

        repaint();
    }

    private boolean checkCollision() {
        Ellipse2D bird = new Ellipse2D.Double(100, birdY, BIRD_SIZE, BIRD_SIZE);
        for (Rectangle2D pipe : pipes) {
            if (pipe.intersects(bird.getBounds2D())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 0, 800, 600);

        g2d.setColor(Color.ORANGE);
        g2d.fill(new Ellipse2D.Double(100, birdY, BIRD_SIZE, BIRD_SIZE));

        g2d.setColor(Color.GREEN);
        for (Rectangle2D pipe : pipes) {
            g2d.fill(pipe);
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + score, 10, 20);
    }
}
