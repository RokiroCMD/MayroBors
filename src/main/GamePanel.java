package main;

import enums.TileSpriteManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    int screenWidth;
    int screenHeight;

    int timeLeft;
    long nextTime;

    Clip musica;
    Clip muerte;
    int maxWorldX;
    int maxWorldY;
    TileSpriteManager tileSpriteManager;
    public Player player;
    ArrayList<Coin> coins = new ArrayList<>();
    ArrayList<Tile> tiles = new ArrayList<>();
    ArrayList<Goomba> goombas = new ArrayList<>();
    ArrayList<LuckyBlock> luckyblocks = new ArrayList<>();
    boolean cancionInicia;
    boolean cancionFinal;
    Timer gameTimer;
    boolean win;

    public GamePanel() throws IOException {
        screenWidth = 64 * 18;
        screenHeight = 64 * 10;
        maxWorldX = 48 * 75;
        maxWorldY = 680;
        nextTime = System.nanoTime() + 1000000000;
        timeLeft = 90;
        win = false;
        tileSpriteManager = new TileSpriteManager();
        player = new Player(48 * 1, 48 * 10, this, "Mario");
        makeTiles();
        makeLuckyBlocks();
        makeCoins();
        makeGoomba();
        makeFlag();
        cancionInicia = false;
        cancionFinal = false;
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!player.restart | !(player.lives < 0)) {
                    if (!win) {
                        try {
                            if (!player.game_over && System.nanoTime() > nextTime) {
                                timeLeft--;
                                nextTime = System.nanoTime() + 1000000000;
                            }
                            if (timeLeft < 0) {
                                player.game_over = true;
                            }

                            player.set();
                            for (int i = 0; i < goombas.size(); i++) {
                                Goomba goomba = goombas.get(i);
                                if (goomba.remove) {
                                    goombas.remove(i);
                                } else {

                                    goomba.update();
                                }
                                if (!cancionInicia) {
                                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/sounds/cancion.wav").getAbsoluteFile());
                                    musica = AudioSystem.getClip();
                                    musica.open(audioInputStream);
                                    musica.start();
                                    cancionInicia = true;
                                }
                            }
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (LineUnavailableException ex) {
                            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    repaint();
                }else{
                    musica.stop();
                    musica.close();
                    gameTimer.cancel();
                }
            }
         ;

        }, 0, 17);
    }

    private void capa1() {
        for (int i = 1; i <= 3; i++) {
            tiles.add(new Tile(48 * 15, 48 * (8 + i), 48, 1, tileSpriteManager.GRAY_GRASS_LEFT(), this, false, false));
            tiles.add(new Tile(48 * 16, 48 * (8 + i), 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, false, false));
            tiles.add(new Tile(48 * 17, 48 * (8 + i), 48, 1, tileSpriteManager.GRAY_GRASS_RIGHT(), this, false, false));
        }

        for (int i = 1; i <= 4; i++) {
            tiles.add(new Tile(48 * 8, 48 * (7 + i), 48, 1, tileSpriteManager.GRAY_GRASS_LEFT(), this, false, false));
            tiles.add(new Tile(48 * 9, 48 * (7 + i), 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, false, false));
            tiles.add(new Tile(48 * 10, 48 * (7 + i), 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, false, false));
            tiles.add(new Tile(48 * 11, 48 * (7 + i), 48, 1, tileSpriteManager.GRAY_GRASS_RIGHT(), this, false, false));
        }

        tiles.add(new Tile(48 * 7, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_LEFT(), this, false, false));
        tiles.add(new Tile(48 * 8, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, false, false));
        tiles.add(new Tile(48 * 9, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, false, false));
        tiles.add(new Tile(48 * 10, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_RIGHT(), this, false, false));

        tile3x1Bot(24, 6);

        tile3x1Bot(45, 5);

        tile3x1Bot(46, 9);

        tile2x1(55, 11);

    }

    private void capa2() {
        for (int i = 0; i < 100; i++) {
            if (i >= 27 && i <= 50) {
                if (i == 27) {
                    tiles.add(new Tile(48 * i, 48 * 12, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));
                    tiles.add(new Tile(48 * i, 48 * 13, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, true, false));
                }
                if (i == 50) {
                    tiles.add(new Tile(48 * i, 48 * 12, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
                    tiles.add(new Tile(48 * i, 48 * 13, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, true, false));
                }
            } else {
                tiles.add(new Tile(48 * i, 48 * 12, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
                tiles.add(new Tile(48 * i, 48 * 13, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, true, false));
            }
        }

        tiles.add(new Tile(48 * 16, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * 17, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * 18, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * 20, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * 21, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * 22, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));
        tiles.add(new Tile(48 * 20, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_LEFT(), this, true, false));
        tiles.add(new Tile(48 * 21, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, true, false));
        tiles.add(new Tile(48 * 22, 48 * 11, 48, 1, tileSpriteManager.GRAY_GRASS_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * 15, 48 * 8, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * 16, 48 * 8, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * 17, 48 * 8, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * 7, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, true));
        tiles.add(new Tile(48 * 8, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, true));
        tiles.add(new Tile(48 * 9, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, true));
        tiles.add(new Tile(48 * 10, 48 * 10, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, true));

        tiles.add(new Tile(48 * 8, 48 * 7, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, true));
        tiles.add(new Tile(48 * 9, 48 * 7, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, true));
        tiles.add(new Tile(48 * 10, 48 * 7, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, true));
        tiles.add(new Tile(48 * 11, 48 * 7, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, true));

        tile3x1(20, 6);

        tile3x1(30, 5);

        tile2x1(35, 9);

        tile4x1(40, 7);

        tile4x1Solid(60, 10);
    }

    private void capa3() {

    }

    public void makeTiles() {
        capa1();
        capa2();
        capa3();
    }

    public void makeCoins() throws IOException {
        coins.add(new Coin(48 * 7, 48 * 9, this));
        coins.add(new Coin(48 * 8, 48 * 9, this));
        coins.add(new Coin(48 * 9, 48 * 9, this));
        coins.add(new Coin(48 * 10, 48 * 9, this));

        coins.add(new Coin(48 * 9, 48 * 6, this));
        coins.add(new Coin(48 * 10, 48 * 6, this));

        coins.add(new Coin(48 * 20, 48 * 5, this));
        coins.add(new Coin(48 * 21, 48 * 5, this));
        coins.add(new Coin(48 * 22, 48 * 5, this));

        coins.add(new Coin(48 * 35, 48 * 8, this));
        coins.add(new Coin(48 * 36, 48 * 8, this));

        coins.add(new Coin(48 * 40, 48 * 6, this));
        coins.add(new Coin(48 * 41, 48 * 6, this));
        coins.add(new Coin(48 * 42, 48 * 6, this));
        coins.add(new Coin(48 * 43, 48 * 6, this));

        coins.add(new Coin(48 * 61, 48 * 9, this));
        coins.add(new Coin(48 * 62, 48 * 9, this));
    }

    public void makeGoomba() throws IOException {
        goombas.add(new Goomba(48 * 13, 48 * 11, this));
        goombas.add(new Goomba(48 * 15, 48 * 7, this));
        goombas.add(new Goomba(48 * 52, 48 * 11, this));
        goombas.add(new Goomba(48 * 55, 48 * 10, this));

    }

    public void makeLuckyBlocks() throws IOException {
        tiles.add(new LuckyBlock(32 * 5, 48 * 9, this));
        tiles.add(new LuckyBlock(48 * 16, 48 * 5, this));
        tiles.add(new LuckyBlock(48 * 61, 48 * 7, this));

        tiles.add(new LuckyBlock(48 * 46, 48 * 3, this));
    }

    public void makeFlag() throws IOException {
        tiles.add(new Flag(48 * 67, 48 * 11, this));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!player.restart | !(player.lives < 0)) {
            Graphics2D g2d = (Graphics2D) g;

            if (win) {
                g.setColor(Color.white);
            }
            if (!player.game_over & !player.death & !win) {
                g.setColor(Color.cyan);
            } else if (player.game_over | player.death) {
                g2d.setColor(Color.black);
            }
            g.fillRect(0, 0, screenWidth, screenHeight);

            for (Tile tile : tiles) {
                tile.draw(g2d);
            }
            for (Coin coin : coins) {
                try {
                    coin.draw(g2d);
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            for (Goomba goomba : goombas) {
                try {
                    if (goomba != null) {
                        goomba.draw(g2d);
                    }
                } catch (IOException ex) {
                }
            }
            try {
                player.draw(g2d);
            } catch (IOException ex) {
            }

            if (!player.game_over) {
                drawHUD(g);
            }
        }
        if (player.lives < 0) {
            if (!cancionFinal) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/sounds/gameover.wav").getAbsoluteFile());
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                    
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    System.out.println("Error al reproducir el sonido.");
                }
                cancionFinal = true;
            }
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth, screenHeight);
            g.setColor(Color.white);
            Font font = new Font("Arial", Font.BOLD, 35);
            g.setFont(font);
            g.drawString("GAME OVER", (screenWidth / 2) - 100, screenHeight / 2);
        }
        if (win) {
            g.setColor(Color.black);
            Font font = new Font("Arial", Font.BOLD, 35);
            g.setFont(font);
            g.drawString("WINNER!", (screenWidth / 2) - 100, screenHeight / 2);
        }

    }

    private void drawHUD(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(10, 17, 33, 255));
        g2.fillRect(0, 0, screenWidth, 48);

        g.setColor(Color.white);
        Font font3 = new Font("Arial", Font.BOLD, 20);
        g.setFont(font3);
        g.drawString("Puntos: " + player.score, 24, 32);
        g.drawString("Vidas: " + player.lives, screenWidth - 128, 32);
        String tiempo = "";
        if (timeLeft >= 1000) {
            tiempo = "" + timeLeft;
        } else if (timeLeft >= 100) {
            tiempo = "0" + timeLeft;
        } else if (timeLeft >= 10 && timeLeft <= 99) {
            tiempo = "00" + timeLeft;
        } else if (timeLeft <= 9) {
            tiempo = "000" + timeLeft;
        }
        g.drawString("Time: " + tiempo, screenWidth / 2 - 64, 32);

    }

    void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.keyLeft = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.keyRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.keyUp = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            player.keyDown = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.shoot = true;
        }
    }

    void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            player.keyLeft = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player.keyRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player.keyUp = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            player.keyDown = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.shoot = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void reset() throws IOException {
        if (player.restart) {
            player.worldX = 48 * 1;
            player.worldY = 48 * 10;
            nextTime = System.nanoTime() + 1000000000;
            timeLeft = 90;
            player.death = false;
            player.game_over = false;
            player.restart = false;
            player.xOffset = -16;
            player.yOffset = -4;
            player.speed = 6;
            player.shoot = false;
            player.jumpForce = 8;
            player.score = 0;
            player.lives--;
            player.canPickupCoin = true;
            player.onAir = false;

            player.frame = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_idle.png"));

            player.frameNum = 1;
            player.finAnimacion = false;
            player.frameAnimacion = 1;
            player.nextTime = System.nanoTime() + 80000000;

            coins.clear();
            tiles.clear();
            goombas.clear();
            luckyblocks.clear();
            makeTiles();
            makeCoins();
            makeGoomba();
            makeFlag();
            makeLuckyBlocks();
            cancionInicia = false;
            player.restart = false;
        }
    }

    public int CalculateScreenXPosition(int worldX) {
        int screenX;
        screenX = worldX - player.worldX + player.screenX;

        if (player.screenX > player.worldX) {
            screenX = worldX;
        }
        int rightOffset = screenWidth - player.screenX;
        if (rightOffset > maxWorldX - player.worldX) {
            screenX = screenWidth - (maxWorldX - worldX);
        }

        return screenX;
    }

    public int CalculateScreenYPosition(int worldY) {
        int screenY;
        screenY = worldY - player.worldY + player.screenY;

        if (player.screenY > player.worldY) {
            screenY = worldY;
        }
        int bottomOffset = screenHeight - player.screenY;
        if (bottomOffset > maxWorldY - player.worldY) {
            screenY = screenHeight - (maxWorldY - worldY);
        }
        return screenY;
    }

    private void tile2xBot() {

    }

    private void tile2x1(int x, int y) {
        tiles.add(new Tile(48 * x, 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * (x), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_LEFT(), this, false, false));
        tiles.add(new Tile(48 * (x + 1), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_RIGHT(), this, false, false));
    }

    private void tile3x1Bot(int x, int y) {
        tiles.add(new Tile(48 * x, 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 2), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));
        while (y < 13) {
            y++;
            tiles.add(new Tile(48 * (x), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_LEFT(), this, true, false));
            tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_CENTER(), this, true, false));
            tiles.add(new Tile(48 * (x + 2), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_RIGHT(), this, true, false));
        }
    }

    private void tile3x1(int x, int y) {
        tiles.add(new Tile(48 * x, 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 2), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * (x), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_LEFT(), this, false, false));
        tiles.add(new Tile(48 * (x + 1), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM(), this, false, false));
        tiles.add(new Tile(48 * (x + 2), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_RIGHT(), this, false, false));

    }

    private void tile4x1(int x, int y) {
        tiles.add(new Tile(48 * x, 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 2), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 3), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * (x), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_LEFT(), this, false, false));
        tiles.add(new Tile(48 * (x + 1), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM(), this, false, false));
        tiles.add(new Tile(48 * (x + 2), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM(), this, false, false));
        tiles.add(new Tile(48 * (x + 3), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_RIGHT(), this, false, false));
    }

    private void tile4x1Solid(int x, int y) {
        tiles.add(new Tile(48 * x, 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 2), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP(), this, true, false));
        tiles.add(new Tile(48 * (x + 3), 48 * y, 48, 1, tileSpriteManager.GRAY_GRASS_UP_RIGHT(), this, true, false));

        tiles.add(new Tile(48 * (x), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_LEFT(), this, true, false));
        tiles.add(new Tile(48 * (x + 1), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM(), this, true, false));
        tiles.add(new Tile(48 * (x + 2), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM(), this, true, false));
        tiles.add(new Tile(48 * (x + 3), 48 * (y + 1), 48, 1, tileSpriteManager.GRAY_GRASS_BOTTOM_RIGHT(), this, true, false));
    }

    public void ReproducirSonido(String nombreSonido) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido.");
        }
    }

}
