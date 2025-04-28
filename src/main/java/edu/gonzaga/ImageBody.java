package edu.gonzaga;

import org.dyn4j.dynamics.BodyFixture;
import java.awt.*;

// A SimulationBody that uses an image instead of a color as the fill
public class ImageBody extends SimulationBody{
    
    // the image used as the fill for the body
    private Image image;

    // constructor - sets the image
    public ImageBody(Image image){

        this.image = image;
    }

    // overriding renderFixture to render an image instead of a color as the fill
    @Override
		protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture, Color color) {
			// do we need to render an image?
			if (this.image != null) {
                // use the image as the fill
                // fsr the image gets flipped upside down, so I flipped them in the files instead of debugging
                g.drawImage(this.image, 0, 0, 55, 55, null);
			} else {
				// default rendering
				super.renderFixture(g, scale, fixture, color);
			}
		}
}
