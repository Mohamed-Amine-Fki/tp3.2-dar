package ServerPackage;
import java.io.*;
import java.net.*;

import OperationPackage.Operation;



public class Server extends Thread{
	int ord;
	public static void main(String[] args) {
		new Server().start();
		
	}
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Démarrage du Server");
			while(true) {
				Socket s = ss.accept();
				new ClientProcess(s, ++ord).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public class ClientProcess extends Thread {
		Socket socket;
		private int numClient;
		public ClientProcess(Socket socket,int numClient) {
			super();
			this.socket = socket;
			this.numClient = numClient; 
		}
		public void run() {
			try {
				
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				ObjectInputStream ois = new ObjectInputStream(is);
				ObjectOutputStream oos = new ObjectOutputStream(os);

				Operation o =(Operation)ois.readObject();
				switch (o.getOperation()) {
				case '+':
					o.setResult(o.getOp1() + o.getOp2());
					break;
				case '-':
					o.setResult(o.getOp1() - o.getOp2());
					break;
				case '*':
					o.setResult(o.getOp1() * o.getOp2());
					break;
				case '/':
					o.setResult(o.getOp1() / o.getOp2());
					break;
					

				default:
					break;
				}
				// Envoie d'objet
				oos.writeObject(o);
				socket.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}