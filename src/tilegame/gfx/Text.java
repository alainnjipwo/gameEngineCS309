package tilegame.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
/**
 * This class is responsible for drawing text onto the screen.
 * @author Kenneth Lange
 *
 */
public class Text {
	/**
	 * This method draws the text to the screen and chooses whether to center it of not and which font to use.
	 * @param g
	 * @param text
	 * @param xPos
	 * @param yPos
	 * @param center
	 * @param c
	 * @param font
	 */
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		g.drawString(text, x, y);
	}

}
