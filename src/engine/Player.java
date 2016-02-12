package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class Player {

	private int x;
	private int y;
	public SpriteSheet sheet;
	protected int currentFrame;

	protected boolean up;
	protected boolean down;
	protected boolean left;
	protected boolean right;
	public boolean clipped = false;
	public String coordClipped = "";
	public boolean moving;
	public int Xs;
	public int Ys;
    private Game game;
	private float colDist = 20;
	private Graphics2D g2d;
	public Player(int x, int y, SpriteSheet sheet) {
		moving = false;
		down = true;
		this.x = x;
		this.y = y;
		this.sheet = sheet;

		currentFrame = 0;
	}

	public void move() {
		if (moving && checkCollision()) {
			if (left) {

				if (x + Client.getGame().i * 8 > 0 + 70) { // MANAGING COLLISION

					Client.getGame().i -= 2;

				} else {

				}
			} else if (right) {
				if (x + Client.getGame().i * 8 < Client.getGame().grid[1].length * Client.getGame().terrain.getXDim() - 70)
					Client.getGame().i += 2;
			} else if (up) {

				if (y + Client.getGame().j * 8 > 0 + 70) {
					Client.getGame().j -= 2;
				} else {

				}
			} else if (down) {
				if (y + Client.getGame().j * 8 < (Client.getGame().grid.length * Client.getGame().terrain.getYDim()) - 100) {
					Client.getGame().j += 2;
				} else {

				}
			}
		}
	}

	private boolean checkCollision() {
		int xC = 0;
		int yC = 0;
		if(right)
		{
			xC = (int)((float)(this.getX()+((Client.getGame().i)*8.0f)+colDist)/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY()+((Client.getGame().j)*8.0f))/((float)Client.getGame().terrain.getYDim()));
		}
		else if(left)
		{
			xC = (int)((float)(this.getX()+((Client.getGame().i)*8.0f)-colDist)/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY()+((Client.getGame().j)*8.0f))/((float)Client.getGame().terrain.getYDim()));
		}
		else if(up)
		{
			xC = (int)((float)(this.getX()+((Client.getGame().i)*8.0f))/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY()+((Client.getGame().j)*8.0f)-colDist )/((float)Client.getGame().terrain.getYDim()));
		}
		else if(down)
		{
			xC = (int)((float)(this.getX()+((Client.getGame().i)*8.0f))/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY()+((Client.getGame().j)*8.0f)+colDist)/((float)Client.getGame().terrain.getYDim()));
		}
		
	    int tile = Client.map.getTile(xC, yC);
	    if(tile==3 || tile==4 || tile==5 || tile ==6)
	    {
	    	return false;
	    }
		return true;
	}

	public void draw(Graphics g) {
		g2d = (Graphics2D)g;
		g2d.setFont(new Font("Consolas", Font.BOLD, 18));
		g2d.setColor(Color.GREEN);
		if (moving) {
			if (up) {
				if (currentFrame == 9) {
					currentFrame = 10;
				} else {
					currentFrame = 9;
				}
			} else if (down) {
				switch (currentFrame) {
				case 0:
					currentFrame = 1;
					break;
				case 1:
					currentFrame = 2;
					break;
				case 2:
					currentFrame = 0;
					break;
				default:
					currentFrame = 0;
					break;
				}
			} else if (right) {
				switch (currentFrame) {
				case 3:
					currentFrame = 4;
					break;
				case 4:
					currentFrame = 3;
					break;
				default:
					currentFrame = 3;
					break;
				}
			} else if (left) {
				switch (currentFrame) {
				case 6:
					currentFrame = 7;
					break;
				case 7:
					currentFrame = 6;
					break;
				default:
					currentFrame = 6;
					break;
				}
			}
		}

		else {
			if (up) {
				if (clipped) {
					currentFrame = 12;
				} else {
					currentFrame = 11;
				}
			} else if (down) {
				if (clipped) {
					currentFrame = 13;
				} else {
					currentFrame = 0;
				}
			} else if (left) {
				currentFrame = 8;
			} else if (right) {
				currentFrame = 5;
			}
		}
		Xs = x + (Client.getGame().i * Client.scale); // why does this work
		Ys = y + (Client.getGame().j * Client.scale);
		sheet.drawFrame(g, currentFrame, x, y); 
		g.drawString(Client.title,this.getX(),this.getY()-(sheet.getYDim()/2));//inefficient
	}

	public void stopOtherDirections() {
		if (up)
			up = false;
		if (left)
			left = false;
		if (right)
			right = false;
		if (down)
			down = false;
	}

	public void setClipped(String coords) {
		coordClipped = coords;
		clipped = true;
	}

	public void goUp() {
		up = true;
	}

	public void goDown() {
		down = true;
	}

	public void goLeft() {
		left = true;
	}

	public void goRight() {
		right = true;
	}

	public void setMoving(boolean b) {
		moving = b;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
