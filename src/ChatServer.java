import java.io.*;
import java.net.*;

public class EchoServer {
	private ServerSocket waiting;
	private	Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	public static void main(String[] args){
	
		if(args.length > 2){
			System.out.println("usage: java EchoServer <-debug> <port>");
			return;
		}else if(args.length == 2){
			try{
				int port = Integer.valueOf(args[1]);
				if(args[0].equals("-debug")){
					EchoServer debugServer = new EchoServer(port);
					debugServer.debug();
				} else {
					System.out.println("usage: java EchoServer <-debug> <port>");
					return;
				}
			} catch(NumberFormatException e) {
				System.out.println("usage: java EchoServer <-debug> <port> " + '\n' + "port must be a number");
			}
		}else if(args.length == 1){
			try{
				if(args[0].equals("-debug")){
					EchoServer debugServer = new EchoServer(5678);
					debugServer.debug();
				} else {
					int port = Integer.valueOf(args[0]);
					EchoServer server = new EchoServer(port);
					server.start();
				}
			} catch (NumberFormatException e) {
				System.out.println("usage: java EchoServer <-debug> <port> " + '\n' + "port must be a number");
			}
		}else{
			EchoServer server = new EchoServer(5678);
			server.start();
		}
	}

	// constructor
	public EchoServer(int port){
		try{ 
			this.waiting = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Standard IO error.");
			e.printStackTrace();
		}
		
	}
	
	// public method to run the server, waiting for client.
	public void start(){
		try{
			System.out.println("Server started, waiting for the client...");
			while(true){
				client = this.waiting.accept();
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
				boolean done = false;
				while(!done){
					try{
						String line = in.readLine();
						if (line == null || line.equals("quit")) {
							done = true;
							break;
						}
						out.println("Echo:" + " " + line);
					} catch(IOException ioe){
						System.out.println("Standart IO error.1");
						done = true;
					}
				}
			} 
		} catch(IOException ioe){
			System.out.println("Standart IO error.2");
			ioe.printStackTrace();
		} finally {
			try{
				in.close();
				out.close();
				if (client != null) {
					client.close();
				}	
			} catch (IOException e){
				System.out.println("Error by closing.");
			}
		}
	}
	
	// public method to run the server in debug mode.
	public void debug(){
		try{
			System.out.println("Server started, waiting for the client...");
			while(true){
				client = this.waiting.accept();
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
				boolean done = false;
				while(!done){
					try{
						String line = in.readLine();
						if (line == null || line.equalsIgnoreCase("quit")) {
							done = true;
							break;
						}
						System.out.println(line);
						out.println("Echo:" + " " + line);
					} catch(IOException ioe){
						System.out.println("Standart IO error.3");
						done = true;
					}
				}
			} 
		} catch(IOException ioe){
			System.out.println("Standart IO error.4");
			ioe.printStackTrace();
		} finally {
			try{
				in.close();
				out.close();
				if (client != null) {
					client.close();
				}
			} catch (IOException e){
				System.out.println("Error by closing.");
			}
		}
	}
}
