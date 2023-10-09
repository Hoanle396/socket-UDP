package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Client extends JFrame {
	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 8888;
	private static final int BUFFER_SIZE = 1024;
	private static final int REQUEST_INTERVAL_MS = 1000; // 1 giây

	public void start() {
		try (DatagramSocket socket = new DatagramSocket()) {
			InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);

			while (true) {
				// Send request to server
				byte[] requestData = "Request".getBytes();
				DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress,
						SERVER_PORT);
				socket.send(requestPacket);

				byte[] buffer = new byte[BUFFER_SIZE];
				DatagramPacket responsePacket = new DatagramPacket(buffer, BUFFER_SIZE);

				// Receive response from server
				socket.receive(responsePacket);
				String[] responseMessage = new String(responsePacket.getData(), 0, responsePacket.getLength()).split(";");
	            String tokyo= responseMessage[0];
	            String paris= responseMessage[1];
	            String seoul= responseMessage[2];
	            String date= responseMessage[3];
	            System.out.println(tokyo+""+paris+""+seoul+""+date);           
               

				// Sleep for the specified interval before sending the next request
				Thread.sleep(REQUEST_INTERVAL_MS);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Client client = new Client();
			client.start();
		});
	}
}