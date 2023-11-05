package ClientPackage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import OperationPackage.Operation;

public class Client {
	public static void main(String[] args) {
		try {
			
			//Connexion
			System.out.println("Je suis un client pas encore connecté...");
			InetAddress ia = InetAddress.getByName("192.168.0.8");
			InetSocketAddress isa = new InetSocketAddress(ia,1234);
			Socket socket = new Socket();
			socket.connect(isa);
			System.out.println("Je suis un client connecté");
			
			Scanner scanner = new Scanner(System.in);
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			// Flux de traitement
			ObjectOutputStream oos = new ObjectOutputStream(os);
			ObjectInputStream ois = new ObjectInputStream(is);
			
			//Envoie d'objet
			System.out.println("Taper Le premier entier");
			int nb1 = scanner.nextInt();
			System.out.println("Taper le deuxieme entier");
			int nb2 = scanner.nextInt();
			char op;
			do {
				System.out.println("Choix d'operation:");
				op = scanner.next().charAt(0);
				} while (!(op =='+') && !(op == '-') && !(op == '*')&& !(op == '/'));
						
			Operation O1= new Operation(nb1,nb2,op);
			oos.writeObject(O1);
						
			// Reçoit d'objet
			O1 = (Operation)ois.readObject();
			System.out.println("Résultat = "+O1.getResult());
			
			//Fermeture du Connexion
			socket.close();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}