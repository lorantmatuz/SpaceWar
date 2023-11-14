package hu.elte.inf.szofttech2023.team3.spacewar.view;

import java.awt.Image;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.ref.SoftReference;

import javax.imageio.ImageIO;

import hu.elte.inf.szofttech2023.team3.spacewar.display.Displayable;

public class BoardItem implements Displayable {
    
    private final String imageName;
    
    private final Runnable action;
    
    private SoftReference<Image> imageReference = new SoftReference<>(null);

    protected BoardItem(String imageName, Runnable action) {
        this.imageName = imageName;
        this.action = action;
    }
    
    @Override
    public Image getImage(int width, int height) {
        Image image = imageReference.get();
        if (image == null) {
            image = loadImage();
            imageReference = new SoftReference<>(image);
        }
        return image;
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

    @Override
    public Runnable getAction() {
        return action;
    }

    @Override
    public String toString() {
        return imageName;
    }
    
}
