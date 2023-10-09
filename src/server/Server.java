package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Random;

public class Server {
	private static final int PORT = 8888;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			System.out.println("Server is running...");

			while (true) {
				byte[] buffer = new byte[BUFFER_SIZE];
				DatagramPacket requestPacket = new DatagramPacket(buffer, BUFFER_SIZE);

				socket.receive(requestPacket);
				InetAddress clientAddress = requestPacket.getAddress();
				int clientPort = requestPacket.getPort();

				Random random = new Random();
				int tokyo = random.nextInt(100);
				int paris = random.nextInt(100);
				int seoul = random.nextInt(100);

				@SuppressWarnings("deprecation")
				String currentDate = new Date().toGMTString();

				// Prepare response message
				String responseMessage = tokyo + ";" + paris + ";" + seoul + ";" + currentDate;

				// Send response to client
				byte[] responseData = responseMessage.getBytes();
				DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress,
						clientPort);
				socket.send(responsePacket);

				System.out.println("Response sent to " + clientAddress.getHostAddress() + ":" + clientPort);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}