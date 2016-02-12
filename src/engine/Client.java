package engine;

import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import java.util.*;
import java.awt.event.*;

public class Client {

	private static Game g;
	static JFrame f;
	public static int scale = 8;
	private static SpriteSheet terrain;
	private static SpriteSheet player;
	private static SpriteSheet ai;
	public static Hashtable<String, OtherPlayer> Players;
	private static boolean running;
	public static boolean start = false;
	private static int[][] grid;
	public static Map map;
	public static ArrayList<String> names1 = new ArrayList<String>();
	static ArrayList<JLabel> names = new ArrayList<JLabel>();
	private static final int WIDTH = 1080;// 720
	private static final int HEIGHT = 720;// 480
	public static String title = "host";
	private static boolean onMenu;
	static ServerSocket server;
	static Socket conn;
	static DataInputStream dis;
	static DataOutputStream dos;

	public static void main(String args[]) throws InterruptedException, UnknownHostException, IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name");
		title = scanner.nextLine();
		System.out.println("Searching for Lan Server...");
		try {
			conn = new Socket(InetAddress.getByName("10.0.0.2"), 2000);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println("ERROR, NO SERVER DETECTED. PLEASE RESTART APPLICATION AND SERVER");
			System.exit(0);
		}
		System.out.println("Sending Player info to Server");
		try {
			dos = new DataOutputStream(conn.getOutputStream());
			dis = new DataInputStream(conn.getInputStream());
			dos.writeUTF(title);
			Thread.sleep(1000);
			if(dis.available()>0)
			{
				//dos.writeUTF(title+"*");
				System.out.println(dis.readUTF());
				title+="*";
			}
			
			
			
		} catch (Exception e) {
			System.out.println("Something happened clientside");
		}
		scanner.close();
		try {
			System.out.println("waiting for read statement");
			String string;
			while (dis.available() < 1) {
            //waits until there are bytes to be taken from stack
			}
			string = dis.readUTF();
			System.out.println(string + "first command received");
			if (string.contains("start")) {
				start = true;
			//	if(string.contains(title+"*"))
			//	{
			//		title+="*";
			//	}
				
				String[] nms = string.substring(5).split(":"); //5 is the length of "start"
				
				for (int i = 0; i < nms.length; i++) { //adds all player names
					if (nms[i].equals(title)) {
						continue;
					} else {
						names1.add(nms[i]);
						System.out.println("added name of new");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(title);
		running = false;

		setupGame();

	
		f = new JFrame();
		f.setSize(WIDTH, HEIGHT);
		f.setTitle("Olive");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setBackground(Color.decode("#8C946B"));
		f.setLocationRelativeTo(null);
		f.addKeyListener(new KeyHandler(g.getPlayer()));
		
		
		/*JLabel label = new JLabel(title);
		label.setFont(new Font("Consolas", Font.BOLD, 18));
		label.setForeground(Color.green);
		label.setSize(180, 30);
		label.setLocation(g.getPlayer().getX(),g.getPlayer().getY()-(player.getYDim()/2)-10);
		f.add(label);
		names.add(label);
		*/
		//g.getPlayer().name = label;
		for (int i = 0; i < names1.size(); i++) // names1 doesn't have client's
												// name. names has everyone's
												// label with client being index
												// 0
		{
			
			/*JLabel label2 = new JLabel(names1.get(i));
			label2.setFont(new Font("Consolas", Font.BOLD, 12));
			label2.setSize(180, 30);
			
			f.add(label2);
			
			names.add(label2);
			*/
		//	g.ais.get(i).name = label2;
		}
		Players = new Hashtable<String, OtherPlayer>();
		for (int i = 0; i < names1.size(); i++) {
			Players.put(names1.get(i), g.ais.get(i));
		}

		running = true;
		onMenu = false;

		f.add(g);

		f.setVisible(true);

		while (running) {
			if (!onMenu) {
				try {

					if (g.getPlayer().moving == false) {

						if (g.getPlayer().clipped == true) {
							dos.writeUTF(title + "|" + g.getPlayer().coordClipped);
						} else {
							dos.writeUTF(title + "|X" + g.getPlayer().Xs + " " + g.getPlayer().Ys); // 0
							dos.flush();
						}
					} else if (g.getPlayer().up == true) {
						dos.writeUTF(title + "|w");
						dos.flush();
					} else if (g.getPlayer().down == true) {
						dos.writeUTF(title + "|s");
						dos.flush();
					} else if (g.getPlayer().left == true) {
						dos.writeUTF(title + "|a");
						dos.flush();
					} else if (g.getPlayer().right == true) {
						dos.writeUTF(title + "|d");
						dos.flush();
					}

				} catch (Exception e1) {
				}

				try {
					String string = dis.readUTF();
					String[] actions = string.split("-");

					for (int i = 0; i < actions.length; i++) {

						String playerName = actions[i].substring(0, actions[i].indexOf("|"));
						String command = actions[i].substring(actions[i].indexOf("|") + 1);
						System.out.println(playerName);
						System.out.println(command);

						if (Players.containsKey(playerName)) {
							OtherPlayer tempPlayer = Players.get(playerName);
							if (command.equals("w")) {
								tempPlayer.setMoving(true);
								tempPlayer.stopOtherDirections();
								tempPlayer.goUp();
							} else if (command.equals("s")) {
								tempPlayer.setMoving(true);
								tempPlayer.stopOtherDirections();
								tempPlayer.goDown();
							} else if (command.equals("a")) {
								tempPlayer.setMoving(true);
								tempPlayer.stopOtherDirections();
								tempPlayer.goLeft();
							} else if (command.equals("d")) {
								tempPlayer.setMoving(true);
								tempPlayer.stopOtherDirections();
								tempPlayer.goRight();
							} else if (command.contains("X") || command.contains("q")) {
								tempPlayer.setMoving(false);//optimize this
								if (!command.contains("q")) {
									tempPlayer.Xf = Integer.parseInt(command.substring(command.indexOf("X") + 1, command.indexOf(" ")));
									tempPlayer.Yf = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
								}
								tempPlayer.clipped = false;
							}
							if (command.contains("q")) {
								int coordX = Integer.parseInt(command.split("q")[0]);
								int coordY = Integer.parseInt(command.split("q")[1]);
								tempPlayer.clipped = true;
								g.modifyGrid(coordX, coordY);

							}

						}
					}

				} catch (Exception e1) {

				}

				Thread.sleep(70); // have to fix this

				g.getPlayer().move();

				for (int i = 0; i < names1.size(); i++) {
					    
						//names.get(i+1).setLocation(g.ais.get(i).getX() - (g.i * scale), (g.ais.get(i).getY() - player.getYDim() / 2) - 10 - (g.j * scale));	
                        //this lags but allows for customizable text. idk how to fix it right now
				}

				f.repaint();

			} else {
				System.out.println("");
			}

		}
	}

	private static void setupGame() {
		map = new Map(2);
		terrain = new SpriteSheet(7, 1, 80, 80, "res/tilesheet[beta].png"); //80x80 are the individual subimage frames
		player = new SpriteSheet(8, 4, 80, 80, "res/charsheet.png");

		g = new Game(terrain, player,map);
		for (int i = 0; i < names1.size(); i++) {
			g.addPlayer(new OtherPlayer(Client.getWidth() / 2, Client.getHeight() / 2, player,names1.get(i)));
		}
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static Game getGame() {
		return g;
	}

	public static boolean getOnMenu() {
		return onMenu;
	}

	public static void setOnMenu(boolean b) {
		onMenu = b;
	}

}
