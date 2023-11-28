package hu.elte.inf.szofttech2023.team3.spacewar.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.SoftReference;

import javax.imageio.ImageIO;

import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;

public class BoardItem implements Displayable {
    
    private final String imageName;
    
    private final boolean selected;
    
    private SoftReference<Image> imageReference = new SoftReference<>(null);

    protected BoardItem(String imageName, boolean selected) {
        this.imageName = imageName;
        this.selected = selected;
    }
    
    @Override
    public Image getImage(int width, int height) {
        Image image = imageReference.get();
        if (image == null) {
            image = applySelection(loadImage());
            imageReference = new SoftReference<>(image);
        }
        return image;
    }
    
    private Image applySelection(Image baseImage) {
        if (!selected) {
            return baseImage;
        }

        int width = baseImage.getWidth(null);
        int height = baseImage.getHeight(null);
        BufferedImage selectedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) selectedImage.getGraphics();
        g2d.setColor(new Color(255, 170, 0, 150));
        g2d.setStroke(new BasicStroke(70));
        int pad = (int) (Math.min(width, height) * 0.15);
        g2d.drawOval(pad, pad, width - (pad * 2), height - (pad * 2));
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(baseImage, 0, 0, width, height, 0, 0, width, height, null);
        g2d.fillRect(0, 0, width, height);
        return selectedImage;
    }

    private Image loadImage() {
        try {
            return loadImageThrowing();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private Image loadImageThrowing() throws IOException {
        return ImageIO.read(getClass().getResource("/hu/elte/inf/szofttech2023/team3/spacewar/view/" + imageName + ".png"));
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return imageName;
    }
    
}
