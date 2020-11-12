package Main;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main {

	public final static String MD5 = "MD5";

	public final static String SHA256 = "SHA-256";

	public final static String SHA384 = "SHA-384";

	public final static String SHA512 = "SHA-512";

	private static Monitor MyMonitor= new Monitor();

	private static String filename = "./docs/rendimiento.txt";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Inicializando");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Ingrese el mensaje");
			String textoClaro;
			textoClaro = br.readLine();
			System.out.println("Ingrese el número de Threads");
			int numThreads = Integer.parseInt(br.readLine());
			String al=null;
			while (al==null) {
				System.out.println("Dijite el número del algoritmo que quiere usar:\n 1. MD5\n 2. SHA256\n 3. SHA512");
				int algoritmo;
				algoritmo = Integer.parseInt(br.readLine());
				if(algoritmo==1)
				{
					al = MD5;
				}
				else if (algoritmo==2) {
					al = SHA256;				
				}
				else if (algoritmo==3) {
					al = SHA512;
				}
				else {
					System.out.println("No se escogió un algoritmo correcto");
				}
			}
			MyMonitor.start();
			Thread.sleep(1000);
			System.out.println("Generando Hash");
			byte[] hash = Encriptado.generar_codigo(textoClaro, al);
			String hex = imprimirHexa(hash);
			System.out.println("Hash generado: "+hex);
			System.out.println("Empieza a revisar");
			Date t1 = Calendar.getInstance().getTime();
			String mensaje_final = Encriptado.identificar_entrada(hash, al,numThreads);
			Date t2 = Calendar.getInstance().getTime();
			System.out.println("Terminó de revisar");
			System.out.println("El mensaje que generó el hash fue :"+mensaje_final);
			long diff = t2.getTime()-t1.getTime();
			System.out.println("El tiempo de inferencia fue: " + diff +" ms");
			imprimirRendimientos();
			MyMonitor.setApagar(true);
			
			
		} catch (Exception ex) {

		}
	}

	private static void imprimirRendimientos() {
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		{	
			ArrayList<String> rendimientos = MyMonitor.getRendimientos();
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAA"+ rendimientos.get(0));
			fichero = new FileWriter(filename);
			pw = new PrintWriter(fichero);
			System.out.println("ENTRO");
			for (int i = 0; i <rendimientos.size() ; i++) {
				pw.println(rendimientos.get(i));
				System.out.println(rendimientos.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para 
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	private static String imprimirHexa(byte[] hash) {
		String out = "";
		for (int i = 0; i < hash.length; i++) {
			if((hash[i] & 0xff)<= 0xf) {
				out+="0";
			}
			out+= Integer.toHexString(hash[i]& 0xff).toLowerCase();
		}
		return out;
	}

}
