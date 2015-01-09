package ClientServerMultithreading;

import java.net.*;
import java.io.*;
import java.util.*;


class InfoServerCls extends Thread
{
	private Socket serverSocket;
	public InfoServerCls(Socket sck)
	{
		serverSocket = sck;
	}


	public void run ()
	{
		BufferedReader inFromClient;
		DataOutputStream outToClient;
		String dataFromClient;
		
		try
		{
			/*buat inputan stream dar socket
			 * dan juga sekaligus konversi dari byte stram ke characterstream (InputStramReader)
			 * BuffereddReader akan memmudakna dalam perngolaha character*/
			inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			/*buat output stram ke socket*/
			outToClient = new DataOutputStream(serverSocket.getOutputStream());
			
			/*tulis selamat datang untuk client*/
			outToClient.writeBytes("InfoServer versi 0.1\n "+
									"hanya untuk Testing ..\n "+
									"silahkan Masukkan perintah TIME | NET | QUIT\n");
			
			/*lakukan perulangan sampai client memberikan perintah QUIT*/
			boolean isQUIT = false;
			while (!isQUIT)
			{
				/*baca data dari Client*/
				dataFromClient = inFromClient.readLine();
				if (dataFromClient.equalsIgnoreCase("TIME"))
				{
					outToClient.writeBytes(new Date().toString()+"\n");
				}else if (dataFromClient.equalsIgnoreCase("NET"))
				{
					outToClient.writeBytes(InetAddress.getByName("localhost").toString()+"\n");
				}else if (dataFromClient.equalsIgnoreCase("QUIT"))
				{
					outToClient.writeBytes("BYE !");
					isQUIT = true;
				}
			}
			outToClient.close();
			inFromClient.close();
			serverSocket.close();
			
		}catch(IOException ioe)
		{
			System.out.println("error : "+ ioe);
		}
		catch(Exception e)
		{
			System.out.println("error : "+ e);
		}
	}
}


public class InfoServerThread {

	private final static int INFO_PORT = 50000;
	
	public static void main(String[] args) 
	{
		try 
		{
			//bind port 50000 ke alamat lokal
			ServerSocket infoServer = new ServerSocket(INFO_PORT);
			System.out.println("Server Telah Siap ...");
			
			while (true)
			{
				//masuk ke mode listening
				//menerima permintaan dari client
				Socket sock = infoServer.accept();
				System.out.println("Ada Client Terkoneksi !");
				
				//jalankan thread untuk interaksi dengan client
				new InfoServerCls(sock).start();
			}
			
		}catch (Exception e)
		{
			System.out.println("error : "+ e);
		}

	}

}
