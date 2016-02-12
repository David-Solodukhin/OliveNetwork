package engine;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

	private Player p;
	
	public KeyHandler(Player play){
		p = play;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			p.setMoving(true);
			p.stopOtherDirections();
			p.goUp();
		}else if(e.getKeyCode() == KeyEvent.VK_S){
			p.setMoving(true);
			p.stopOtherDirections();
			p.goDown();
		}else if(e.getKeyCode() == KeyEvent.VK_A){
			p.setMoving(true);
			p.stopOtherDirections();
			p.goLeft();
		}else if(e.getKeyCode() == KeyEvent.VK_D){
			p.setMoving(true);
			p.stopOtherDirections();
			p.goRight();
	    }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
	        int x = (int)((float)(p.getX()+(Client.getGame().i*8.0f))/((float)Client.getGame().terrain.getXDim()));
 	        int y = (int)((float)(p.getY()+(Client.getGame().j*8.0f))/((float)Client.getGame().terrain.getYDim()));
	        System.out.println(Client.getGame().terrain.getXDim());
			Client.getGame().modifyGrid(x,y);
			try{
			    p.setClipped(x+"q" +y);
			//Client.dos.writeUTF();
			 
            }
			catch(Exception q){}
		
		}else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(Client.getOnMenu()){
			    System.out.println("enter");
				Client.setOnMenu(false);
			}
		}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			//if(!Client.getOnMenu()){
				//Client.setOnMenu(true);
			//}
			System.exit(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e){
		p.setMoving(false);
		p.clipped = false;
		try{
		//Client.dos.flush();
}
catch(Exception q)
{
}
	}

}
