package engine;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private int cols;
	private int rows;
	private int xDim;
	private int yDim;
	private String path;
	
	BufferedImage[] sprites;
	
	public SpriteSheet(int cols, int rows, int xDim, int yDim, String path){
		this.cols = cols;
		this.rows = rows;
		this.xDim = xDim;
		this.yDim = yDim;
		this.path = path;
		
		sprites = new BufferedImage[rows*cols];
		setupSheet();
	}
	
	private void setupSheet(){
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				sprites[(i*cols)+j] = bi.getSubimage(j * xDim, i * yDim, xDim, yDim);
			}
		}
	}
	
	/* Draw a map to the screen
	 * @param 	g			the instance of the Graphics class used to draw the map	
	 * 			grid[][]	an integer representation of the map to be drawn
	 * 			x			the initial x position of the map
	 * 			y			the initial y position of the map
	 */
	public void drawMap(Graphics g, int[][] grid, int x, int y){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[i].length; j++){
				g.drawImage(sprites[grid[i][j]], x+(j*xDim), y+(i*yDim), null);
			}
		}
	}
	
	/* Draw a frame to the screen
	 * @param 	g			the instance of the Graphics class used to draw the frame
	 * 			frame		the frame of the sheet to be drawn
	 * 			x			the initial x position of the frame
	 * 			y			the initial y position of the frame
	 */
	public void drawFrame(Graphics g, int frame, int x, int y){
		g.drawImage(sprites[frame], x-(xDim/2), y-(yDim/2), null);
	}
	
	public int getXDim(){
		return xDim;
	}
	
	public int getYDim(){
		return yDim;
	}
}
