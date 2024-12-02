package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {

    GamePanel panel;
    int worldX;
    int worldY;
    int widht;
    int height;

    int xOffset;
    int yOffset;
    double xspeed;
    double yspeed;

    Rectangle hitBox;
    Rectangle feetBox;
    Rectangle headBox;
    Rectangle stompBox;

    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;
    boolean shoot;
    boolean onAir;
    int speed;
    int jumpForce;

    BufferedImage frame;
    int frameNum;

    String name;
    int score;
    int lives;
    boolean game_over;
    boolean death;
    boolean restart;
    boolean lookingRight;
    boolean finAnimacion;
    int frameAnimacion;

    Long nextTime;

    boolean canPickupCoin;

    public final int screenX;
    public final int screenY;

    BufferedImage caminar1;
    BufferedImage caminar2;
    BufferedImage caminar3;
    BufferedImage caminar4;
    BufferedImage caminar5;
    BufferedImage caminar6;
    BufferedImage caminar7;
    BufferedImage caminar8;
    BufferedImage caminar9;
    BufferedImage caminar10;
    BufferedImage caminar11;
    BufferedImage caminar12;
    
    BufferedImage salto;
    
    BufferedImage idle;
    
    BufferedImage muerte;
    
    
    public Player(int x, int y, GamePanel panel, String name) throws IOException {

        this.panel = panel;
        
        screenX = panel.screenWidth / 2 - (48);
        screenY = panel.screenHeight / 2 - (48);

        worldX = x;
        worldY = y;
        widht = 20;
        height = 40;
        hitBox = new Rectangle(worldX + 16, worldY + 8, widht, height);
        headBox = new Rectangle(worldX+12, worldY - 5, widht/2, 5);
        feetBox = new Rectangle(worldX + 16, worldY + 38, widht, 10);
        stompBox = new Rectangle(worldX - 30, worldY + 48, widht + 30, 10);
        xOffset = -16;
        yOffset = -4;
        speed = 6;
        shoot = false;
        jumpForce = 8;
        this.name = name;
        score = 0;
        lives = 3;
        canPickupCoin = true;
        onAir = false;
        game_over = false;
        death = false;
        restart = false;

        frame = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_idle.png"));
        
        caminar1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run1.png"));
        caminar2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run2.png"));
        caminar3 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run3.png"));
        caminar4 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run4.png"));
        caminar5 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run5.png"));
        caminar6 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run6.png"));
        caminar7 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run7.png"));
        caminar8 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run8.png"));
        caminar9 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run9.png"));
        caminar10 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run10.png"));
        caminar11 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run11.png"));
        caminar12 = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_run12.png"));
        
        salto = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_jump.png"));
        
        idle = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_idle.png"));
        
        muerte = ImageIO.read(getClass().getResourceAsStream("/resources/player/mario_death.png"));

        frameNum = 1;
        finAnimacion = false;
        frameAnimacion = 1;
        nextTime = System.nanoTime() + 80000000;

    }

    public void set() throws IOException {

        if (death) {
            if (frameNum > 0) {
                frame = muerte;
                panel.musica.stop();
                panel.ReproducirSonido("src/resources/sounds/death.wav");
                frameNum = -1;
            }
            if (!finAnimacion && System.nanoTime() > nextTime) {
                if (frameAnimacion > 30 && frameAnimacion < 60) {
                    worldY -= 3;
                } else if (frameAnimacion >= 100 && frameAnimacion <= 150) {
                    worldY += 8;
                } else if (frameAnimacion >= 250) {
                    finAnimacion = true;
                    restart=true;
                    panel.reset();
                }
                frameAnimacion++;
                nextTime = System.nanoTime() + (long) (1000000000 * 0.001);
            }

            return;
        }

        if (onAir) {
            frame = salto;
            frameNum = 1;
        }

        if (shoot) {

        }

        move();

        physics();

        if (worldY > 800) {
                death = true;
                game_over = true;

        }

    }

    private void physics() {
        if (xspeed > 0 && xspeed < 0.75) {
            xspeed = 0;
        }
        if (xspeed < 0 && xspeed > -0.75) {
            xspeed = 0;
        }

        if (xspeed > speed) {
            xspeed = speed;
        }
        if (xspeed < -speed) {
            xspeed = -speed;
        }
        if (worldX <= 0) {
            xspeed = 0;
            worldX++;
        }
        if (worldX >= panel.maxWorldX) {
            xspeed = 0;
            worldX--;
        }

        yspeed += 0.3;

        hitBox.x += xspeed;
        for (Tile tile : panel.tiles) {
            if (tile.canCollide) {
                if (tile.hitBox.intersects(hitBox)) {
                    hitBox.x -= xspeed;
                    while (!tile.hitBox.intersects(hitBox)) {
                        hitBox.x += Math.signum(xspeed);
                    }
                    hitBox.x -= Math.signum(xspeed);
                    worldX = hitBox.x;
                    xspeed = 0;
                }
            }
        }

        hitBox.y += yspeed;
        for (Tile tile : panel.tiles) {
            if (tile.canCollide) {
                if (tile.hitBox.intersects(hitBox)) {
                    hitBox.y -= yspeed;
                    while (!tile.hitBox.intersects(hitBox)) {
                        hitBox.y += Math.signum(yspeed);
                    }
                    onAir = false;
                    hitBox.y -= Math.signum(yspeed);
                    yspeed = 0;
                    worldY = hitBox.y;

                }
            }
        }

        worldX += xspeed;
        worldY += yspeed;

        hitBox.x = worldX;
        hitBox.y = worldY;

        headBox.x = worldX+12;
        headBox.y = worldY - 5;
        feetBox.x = worldX + 16;
        feetBox.y = worldY + 38;
        stompBox.x = worldX - 30;
        stompBox.y = worldY + 48;

        for (int i = 0; i < panel.coins.size(); i++) {
            Coin coin = panel.coins.get(i);
            if (coin.hitBox.intersects(hitBox)) {
                panel.coins.remove(i);
                score += coin.points;
                panel.ReproducirSonido("src/resources/sounds/coin.wav");
            }

        }

        for (int i = 0; i < panel.goombas.size(); i++) {
            Goomba goomba = panel.goombas.get(i);
            if (goomba.deathbox.intersects(hitBox) && !goomba.death) {
                death = true;
                game_over = true;
            }
        }
        for (int i = 0; i < panel.goombas.size(); i++) {
            Goomba goomba = panel.goombas.get(i);
            if (goomba.deathbox.intersects(stompBox) && !goomba.death) {
                goomba.death = true;
                panel.ReproducirSonido("src/resources/sounds/goomba_stomp.wav");
                yspeed -= jumpForce / 1.5;
            }

        }
        for (int i = 0; i < panel.tiles.size(); i++) {
            if (panel.tiles.get(i) instanceof LuckyBlock) {
                LuckyBlock lb = (LuckyBlock) panel.tiles.get(i);
                if (lb.hitBox.intersects(headBox) && !lb.off && canPickupCoin) {
                    lb.off = true;
                    score++;
                    panel.ReproducirSonido("src/resources/sounds/coin.wav");
                    canPickupCoin = false;
                }
            }
            if (panel.tiles.get(i) instanceof Flag) {
                Flag flag = (Flag) panel.tiles.get(i);
                if (flag.hitBox.intersects(hitBox)) {
                    if (flag.win == false) {
                        panel.musica.stop();
                        panel.ReproducirSonido("src/resources/sounds/win.wav");
                        flag.win = true;
                    }
                }
            }

        }

    }

    private void move() throws IOException {
        if (keyLeft & keyRight || !keyLeft & !keyRight) {
            xspeed *= 0.8;
            if (!onAir) {
                if (frameNum >= 1) {

                    frame = idle;
                }
                frameNum = 1;
            }

        } else if (keyLeft && !keyRight) {
            xspeed--;
            lookingRight = false;
            if (!onAir) {
                if (System.nanoTime() >= nextTime) {
                    if (frameNum >= 12) {
                        frameNum = 1;
                    }
                    String ruta = "/resources/player/mario_run" + frameNum + ".png";
                    frame = ImageIO.read(getClass().getResourceAsStream(ruta));

                    frameNum++;
                    nextTime = System.nanoTime() + 80000000;
                }
            }

        } else if (keyRight && !keyLeft) {
            xspeed++;
            lookingRight = true;
            if (!onAir) {
                if (System.nanoTime() >= nextTime) {

                    if (frameNum >= 12) {
                        frameNum = 1;
                    }
                    
                    switch (frameNum) {
                        case 1:
                        frame = caminar1;
                            break;
                        case 2:
                        frame = caminar2;
                            break;
                        case 3:
                        frame = caminar3;
                            break;
                        case 4:
                        frame = caminar4;
                            break;
                        case 5:
                        frame = caminar5;
                            break;
                        case 6:
                        frame = caminar6;
                            break;
                        case 7:
                        frame = caminar7;
                            break;
                        case 8:
                        frame = caminar8;
                            break;
                        case 9:
                        frame = caminar9;
                            break;
                        case 10:
                        frame = caminar10;
                            break;
                        case 11:
                        frame = caminar11;
                            break;
                        case 12:
                        frame = caminar12;
                            break;
                    }

                    frameNum++;
                    nextTime = System.nanoTime() + 80000000;
                }
            }
        }
        if (keyUp && !onAir) {
            boolean Colision = false;
            for (Tile tile : panel.tiles) {
                if (tile.hitBox.intersects(feetBox)) {
                    if (tile.canCollide) {
                        Colision = true;
                        
                    }
                }
            }
            if (Colision) {
                yspeed = -jumpForce;
                panel.ReproducirSonido("src/resources/sounds/jump.wav");
                canPickupCoin = true;
                onAir = true;
            }
            
        }
    }

    public void draw(Graphics2D g2d) throws IOException {
        if (panel.win) {
            worldX++;
            worldY =  (48 * 11)+10;
            if (System.nanoTime() >= nextTime) {

                    if (frameNum >= 12) {
                        frameNum = 1;
                    }
                    
                    switch (frameNum) {
                        case 1:
                        frame = caminar1;
                            break;
                        case 2:
                        frame = caminar2;
                            break;
                        case 3:
                        frame = caminar3;
                            break;
                        case 4:
                        frame = caminar4;
                            break;
                        case 5:
                        frame = caminar5;
                            break;
                        case 6:
                        frame = caminar6;
                            break;
                        case 7:
                        frame = caminar7;
                            break;
                        case 8:
                        frame = caminar8;
                            break;
                        case 9:
                        frame = caminar9;
                            break;
                        case 10:
                        frame = caminar10;
                            break;
                        case 11:
                        frame = caminar11;
                            break;
                        case 12:
                        frame = caminar12;
                            break;
                    }

                    frameNum++;
                    nextTime = System.nanoTime() + 80000000;
                }
        }
        int x = screenX;
        int y = screenY;

        if (screenX > worldX) {
            x = worldX;
        }
        if (screenY > worldY) {
            y = worldY;
        }

        int rightOffset = panel.screenWidth - screenX;
        if (rightOffset > panel.maxWorldX - worldX) {
            x = panel.screenWidth - (panel.maxWorldX - worldX);
        }

        int bottomOffset = panel.screenHeight - screenY;
        if (bottomOffset > panel.maxWorldY - worldY) {
            y = panel.screenHeight - (panel.maxWorldY - worldY);
        }

        if (keyLeft) {
            g2d.drawImage(frame, x + 48 + xOffset, y + yOffset, -48, 48, null);
        } else if (lookingRight) {
            g2d.drawImage(frame, x + xOffset, y + yOffset, 48, 48, null);
        } else {
            g2d.drawImage(frame, x + 48 + xOffset, y + yOffset, -48, 48, null);
        }
        {
        }

        if (game_over) {
            death = true;
        }
    }

}
