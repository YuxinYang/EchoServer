import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private ServerSocket serverSocket;
	private static boolean isDebug = false;

	public void startSocket(int port) throws IOException {

		serverSocket = new ServerSocket(port);

		while (true) {
			Socket socket = serverSocket.accept();
			new EchoThread(socket).start();
		}

	}

	public static void main(String[] args) {
		EchoServer echoServer = new EchoServer();
		int port = 5678;
		if (args.length == 1) {
			port = Integer.valueOf(args[0]);
		} else if (args.length == 2) {
			if (args[0] != null && "-debug".equals(args[0])) {
				echoServer.isDebug = true;
			}
			port = Integer.valueOf(args[1]);
		}

		try {
			echoServer.startSocket(port);
		} catch (IOException e) {
			// Nothing
		}

	}

	public static class EchoThread extends Thread {
		final Socket mSocket;

		EchoThread(Socket socket) {
			mSocket = socket;
		}

		public void run() {

			InputStream in;
			try {
				in = mSocket.getInputStream();

				OutputStream out = mSocket.getOutputStream();
				for (int read = in.read(); read != -1; read = in.read()) {
					if (isDebug) {
						System.out.print((char) read);
						System.out.flush();
					}
					out.write(read);
				}

				in.close();
				out.flush();
				out.close();
				mSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
