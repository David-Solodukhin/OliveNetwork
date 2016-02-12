package engine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
class sThread extends Thread{  

    String line="";
    BufferedReader  is = null;
    PrintWriter os=null;
    Socket s=null;
    boolean test = false;
    DataInputStream dis;
    DataOutputStream dos;

    public sThread(Socket s) throws IOException{
        this.s=s;
        dos = new DataOutputStream(s.getOutputStream());
		dis = new DataInputStream(s.getInputStream());
    }

    public void run() {
           while(true)
           {
               //keep thread alive
           }
}
public String getData()
{
    try{
    //System.out.println("fetching data..."+this.getName());
    float i = 0;
    while(dis.available()<1){i+=0.01f;if(i>20000){System.out.println("player disconnected:Server shutting off");System.exit(1);}}
    line =dis.readUTF();
    return line;
}
catch(Exception e)
{
   System.out.println("something went really wrong");
}
return "";
}
public void sendData(String data)
{
    try{
dos.writeUTF(data);
}
catch(IOException q)
{
    System.out.println("some player disconnected");
    Server.error = true;
}
}
}