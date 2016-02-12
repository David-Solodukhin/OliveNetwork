package engine;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Game extends JPanel{

    private static final long serialVersionUID = 1L;

    public SpriteSheet terrain;
    public int[][] grid;
    
    private SpriteSheet player;
    private Player p;
    private BufferedImage menu;
    private BufferedImage house;
    public ArrayList<OtherPlayer> ais = new  ArrayList<OtherPlayer>();
    int i = 0;
    int j = 0;
    float opacity = 0.0f;
	private Graphics2D g2d;
	private boolean fadeIn = true;
    public Game(SpriteSheet terrain, SpriteSheet player, Map map ){
        this.terrain = terrain;
        this.grid = map.grid;
        
        this.player = player;
        p = new Player(Client.getWidth()/2, Client.getHeight()/2, player);
        menu = null;
        try {
            menu = ImageIO.read(new File("res/menu.png"));
            house = ImageIO.read(new File("res/house.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void paint(Graphics g){
    	g2d = (Graphics2D) g;
    	if(fadeIn  && opacity<1.0f)
    	{
    		
    		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
    		opacity +=0.1f;
    		
    	}
    	else{fadeIn = false;}
    	
        terrain.drawMap(g, grid, 0-(i*Client.scale), 0-(j*Client.scale));//10
        
        g2d.drawImage(house, Client.getWidth()/2-(i*Client.scale), -house.getHeight()-(j*Client.scale), house.getWidth()*2,house.getHeight()*2,null);
        
        
        for(int i = 0; i < ais.size(); i++){
            ais.get(i).move();
            ais.get(i).draw(g);
        }
        
        p.draw(g);
        
        
        
        if(Client.getOnMenu()){
            
            g.drawImage(menu, 0, 0, null);
        }
        
        
    }
    
    public Player getPlayer(){
        return p;
    }
    
    public SpriteSheet getPlayerSheet(){
        return player;
    }
    
    public SpriteSheet getMapSheet(){
        return terrain;
    }
    
    
    public void addPlayer(OtherPlayer a){
        ais.add(a);
    }
    public void modifyGrid(int x,int y)
    {
       
        grid[y][x] = 0;
        
        
    }
}
