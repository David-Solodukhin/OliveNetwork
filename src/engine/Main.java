package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	static boolean started = false;
	JButton b1;
	JLabel l1;
	Ass doop = new Ass();
	private static void createAndShowGUI()  {
		System.out.println(""); 
		
       
    }
	class Ass extends JPanel
	{
		public Ass()
		{
			
		}
		 protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        BufferedImage myImage = null;
				try {
					myImage = ImageIO.read(new File("res/menu.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        g.drawImage(myImage, 0, 0, this);
		    }
	}
	public Main() throws IOException
	{
		 setTitle("Olive_Network");
		    setSize(1080,720);
		    setLocationRelativeTo(null);
		    setDefaultCloseOperation(EXIT_ON_CLOSE);
		    setVisible(true);
		    setLayout(new BorderLayout());
		    setLayout(new FlowLayout());
		    setContentPane(doop);
		    getContentPane().setBackground(Color.decode("#8C946B"));
		    
		    
		    
	}
	public static void main(String... argz) throws InterruptedException, UnknownHostException, IOException {

		/* javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	        */
	    
		Main doop = new Main();
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Host a server or connect as a client?: Enter 'h' for server and 'c' for client");

		while (!started) {
			String line = scanner.nextLine();
			if (line.equals("h")) {
				Server.main(argz);
				started = true;
			} else if (line.equals("c")) {
				Client.main(argz);
				started = true;
			} else if(line.equals("d"))
			{
				Server.main(argz);
				Client.main(argz);
				started = true;
			}
			else {
				System.out.println("can u read");
			}
		}

	}
}
