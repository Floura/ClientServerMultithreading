package ClientServerMultithreading;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class InfoClient2 {
	private final int INFO_PORT=50000;
	private final String TargetHost = "localhost";
	private final String QUIT = "QUIT";

	//buat instance infoClient
	public InfoClient2()
	{
		try
		{
			//menyiapkan inputan client dari keyboard
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
			//buat koneksi socket ke server dan port yang dituju
			Socket clientSocket = new Socket(TargetHost, INFO_PORT);
			
			//siapkan output stream ke socket
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			//siapkan input Stream dari Socket
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//tampilkan informasi selamat datang dari server
			//oleh karena method readLine() akan membaca satu baris String
			//yang diakhiri dengan karakter ENTER
			//maka perlu diberikan 3 pemanggil method readLine() untuk membaca 3 pesan dari server
			System.out.println(inFromServer.readLine());
			System.out.println(inFromServer.readLine());
			System.out.println(inFromServer.readLine());
			System.out.println(" ");
			
			boolean isQUIT = false;
			while (!isQUIT)
			{
				//menunggu masukan perintah dari user
				System.out.println("Perintah Anda : ");
				String cmd = inFromUser.readLine();
				
				//konversi ke UPPER dan Cek
				cmd = cmd.toUpperCase();
				if (cmd.equals(QUIT))
				{
					isQUIT = true;
				}
			
			
			//kirim perintah ke server dan akhiri dengan perintah ENTER.
			//karena di client dibaca dengan method readLine() 
			//untuk membaca satu baris string diakhiri dengan ENTER.
			
			outToServer.writeBytes(cmd +"\n");
			
			//block reading...
			//client harus menunggu balasan dari server.
			String result = inFromServer.readLine();
			System.out.println("Dari server : " + result);
			
			}
			//tutup semua stream dan koneksi socket
			outToServer.close();
			inFromServer.close();
			clientSocket.close();
		}catch (IOException ioe)
		{
			System.out.println("error : "+ ioe);
		}
		catch (Exception e)
		{
			System.out.println("error : "+ e);
		}
	}
	
	public static void main(String[] args) {
		new InfoClient();

	}

}
