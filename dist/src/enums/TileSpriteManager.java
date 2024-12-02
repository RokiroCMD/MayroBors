package enums;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileSpriteManager {

    public BufferedImage GRAY_GRASS_UP() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_up.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_UP_LEFT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_upleft.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_UP_RIGHT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_upright.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_LEFT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_left.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_CENTER() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_center.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_RIGHT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_right.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_BOTTOM() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_bottom.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_BOTTOM_LEFT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_bottomleft.png"));
        } catch (IOException ex) {
        }
        return null;
    }
    public BufferedImage GRAY_GRASS_BOTTOM_RIGHT() {
        try {
            return ImageIO.read(getClass().getResource("/resources/tiles/tile_gray_grass_bottomright.png"));
        } catch (IOException ex) {
        }
        return null;
    }

}
