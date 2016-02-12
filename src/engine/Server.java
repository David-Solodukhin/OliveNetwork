package engine;

import java.net.*;
import java.util.*;
import java.io.*;

public class Server {
	static int numPlayers = -1;
	static ArrayList<String> names = new ArrayList<String>();
	static boolean running = true;
	static boolean sendInfoStage = false;
	static boolean initialSetupStage = true;
	static String pInfo = "start";
	static String commands = "";
	static boolean error = false;
	static ArrayList<sThread> test = new ArrayList<sThread>();

	public static void main(String... args) throws InterruptedException, UnknownHostException, IOException {
		Socket conn = null;
		ServerSocket server = new ServerSocket(2000);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Number of Players");
		while (numPlayers < 1) {
			
			    String input = scanner.nextLine();
			    if(input.replaceAll("[\\D]", "").length()!=0)
			    {
			    	numPlayers = Integer.parseInt(input.replaceAll("[\\D]", ""));
			    }
			    else  {
					System.out.println("The number of players must be an integer >0");

				}
			
		}
		System.out.println("Server Launching... Waiting for Players");
		for (int i = 0; i < numPlayers; i++) {

		}

		// scanner.close();
		int connectedPlayers = 0;
		while (test.size() < numPlayers) {
			conn = server.accept();
			sThread st = new sThread(conn);
			connectedPlayers++;
			System.out.println("Player connected " + connectedPlayers + "/" + numPlayers);
			st.start();
			test.add(st);

		}
		for (int i = 0; i < test.size(); i++)// getting all the names
		{
			System.out.println(test.get(i).getName());
			String temp = "";
			temp = test.get(i).getData();
			if (pInfo.contains(temp)) {
				test.get(i).sendData("420");
				System.out.println("what");
				temp += "*";
			}// fix same names
			pInfo += temp + ":";
			System.out.println(pInfo);
		}
        Thread.sleep(2000);
		System.out.println("--------------");
		for (int i = 0; i < test.size(); i++) {
			test.get(i).sendData(pInfo);
			System.out.println(test.get(i).getName());
		}
		while (running) {

			for (int i = 0; i < test.size(); i++) {
				commands += test.get(i).getData() + ((i != test.size() - 1) ? "-" : "");

			}
			for (int i = 0; i < test.size(); i++) {
				test.get(i).sendData(commands);
			}
			commands = "";
		}

	}
}
