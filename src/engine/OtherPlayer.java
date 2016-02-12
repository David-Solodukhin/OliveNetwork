package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class OtherPlayer {

	public int x;
	public int y;
	public int Xf;
	public int Yf;
	private SpriteSheet sheet;
	protected int currentFrame;
	boolean clipped;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
    private String name;
	protected int frame = 0;
    public JLabel test;
    private Graphics2D g2d;
	protected boolean moving;
	private float colDist=20.0f;

	public OtherPlayer(int x, int y, SpriteSheet sheet, String name) {
		moving = false;
		down = true;
		this.x = x;
		this.y = y;
		this.sheet = sheet;
        this.name = name;
		currentFrame = 0;
	}

	public void move() {
		if (moving && checkCollision()) {
			//	if (left && x - Client.getGame().i * 8 > 0 + 70) {
			//x -= 2 * Client.scale;
			if (left) {
				x -= 2 * Client.scale;
			} else if (right ) { //&& x - Client.getGame().i * 8 < Client.getGame().grid[1].length * Client.getGame().terrain.getXDim() - 70
				x += 2 * Client.scale;
			} else if (up) { //&& y - Client.getGame().j * 8 > 0 + 70
				y -= 2 * Client.scale;
			} else if (down ) { //&& y - Client.getGame().j * 8 < (Client.getGame().grid.length * Client.getGame().terrain.getYDim()) - 100
				y += 2 * Client.scale;
			}
		}

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
		} else {
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
		if (Xf != x || Yf != y) {
			if (!moving && !Client.getGame().getPlayer().moving && clipped == false) {
				x = Xf;
				y = Yf;
			}
		}

		sheet.drawFrame(g, currentFrame, x - (Client.getGame().i * Client.scale), y - (Client.getGame().j * Client.scale));
		g.drawString(name,x - (Client.getGame().i * Client.scale), y - (sheet.getYDim() / 2) - (Client.getGame().j * Client.scale));
		
	
	}
	private boolean checkCollision() {
		int xC = 0;
		int yC = 0;
		if(right)
		{
			
			xC = (int)((float)(this.getX()-(Client.getGame().i*8.0f)+colDist)/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY()-(Client.getGame().j)*8.0f)/((float)Client.getGame().terrain.getYDim()));
		}
		else if(left)
		{
			//(Client.getGame().i * Client.scale)
			xC = (int)((float)(x - colDist)/((float)Client.getGame().terrain.getXDim()));
		    yC = (int)((float)(this.getY())/((float)Client.getGame().terrain.getYDim())); //-(Client.getGame().j)*8.0f
					}
		else if(up)
		{
			xC = (int)((float)(this.getX())/((float)Client.getGame().terrain.getXDim())); //-(Client.getGame().i*8.0f)
		    yC = (int)((float)(this.getY()-colDist )/((float)Client.getGame().terrain.getYDim())); //-(Client.getGame().j*8.0f)
		}
		else if(down)
		{
			xC = (int)((float)(this.getX())/((float)Client.getGame().terrain.getXDim())); //-(Client.getGame().i*8.0f)
		    yC = (int)((float)(this.getY()+colDist)/((float)Client.getGame().terrain.getYDim()));  //-(Client.getGame().j*8.0f)
		}
		
	    int tile = Client.map.getTile((int)xC, (int)yC);
	    if(tile==3 || tile==4 || tile==5 || tile ==6)
	    {
	    	return false;
	    }
		return true;
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
