
import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;

		try {
			server = new ServerSocket(1782);
			System.out.println("Port bound. Accepting Connections");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		while (!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;

			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();

				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);

				String clientInput = new String(data, StandardCharsets.UTF_8);

				// Shutdown
				if (clientInput.equalsIgnoreCase("shutdown")) {
					System.out.println("Shutting down....");
					shutdown = true;
					break;
				}

				System.out.println("Client said: " + clientInput);

				// Prime number logic =====================================
				int num = Integer.parseInt(clientInput);
				int i = 2;

				boolean flag = false;
				while (i <= num / 2) {

					if (num % i == 0) {
						flag = true;
						break;
					}

					++i;
				}

				if (num == 0 || num == 1) {
					String serverResponse = ("Not prime. \n");
					output.write(serverResponse.getBytes());
				} else if (!flag) {

					String serverResponse = ("Prime. \n");
					output.write(serverResponse.getBytes());
				} else {

					String serverResponse = ("Not prime. \n");
					output.write(serverResponse.getBytes());
				}

				client.close();

			} catch (IOException e) {

				e.printStackTrace();
				continue;
			}
		}

	}// end main

}