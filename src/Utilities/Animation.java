package Utilities;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * this class will add images so it looks like an animation.
 * found this class on Stackoverflow. 
 * 
 * @author Jordi Jaspers
 */
public class Animation {

	private final List<BufferedImage> list;
	private final long duration;
	private int currentFrame = 0;
	private long previousTime;

	public Animation(int duration) {
		this.duration = duration;
		list = new ArrayList<>();
		previousTime = 0;
	}

	public void updateFrame() {
		if (System.currentTimeMillis() - previousTime >= duration) {
			currentFrame++;
			if (currentFrame >= list.size()) {
				currentFrame = 0;
			}
			previousTime = System.currentTimeMillis();
		}
	}

	public void addFrame(BufferedImage image) {
		list.add(image);
	}

	public BufferedImage getFrame() {
		return list.get(currentFrame);
	}
}
