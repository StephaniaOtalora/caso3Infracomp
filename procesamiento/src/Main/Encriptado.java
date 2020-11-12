package Main;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class Encriptado {



	public static char[] set = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
	
	public Encriptado() {
		// TODO Auto-generated constructor stub
	}

	public static byte[] generar_codigo(String mensaje, String codigo) {

		byte[] mensajeBytes= mensaje.getBytes();
		byte[] hash = null;

		try {
			MessageDigest md = MessageDigest.getInstance(codigo);
			md.update(mensajeBytes);
			hash = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		}

		return hash;

	}

	public static String identificar_entrada(byte[] hash, String codigo, int numThreads) {

		EncriptadoThread[] threads= new EncriptadoThread[numThreads];
		ArrayList<char[]> aux = new ArrayList<>(); 
		if (numThreads==1){
			aux.add(set);
		}	
		else if (numThreads==2) {
			aux.add("abcdefghijklmn".toCharArray());
			aux.add("ñopqrstuvwxyz".toCharArray());
		}
		else if (numThreads==4) {
			aux.add("abcdefg".toCharArray());
			aux.add("hijklmn".toCharArray());
			aux.add("ñopqrst".toCharArray());
			aux.add("uvwxyz".toCharArray());
		}
		else {
			aux.add("abc".toCharArray());
			aux.add("def".toCharArray());
			aux.add("ghi".toCharArray());
			aux.add("jkl".toCharArray());
			aux.add("mnño".toCharArray());
			aux.add("pqrs".toCharArray());
			aux.add("tuvw".toCharArray());
			aux.add("xyz".toCharArray());
		}
		for(int j = 0; j<threads.length; j++)
		{
			threads[j] = new EncriptadoThread(j+1,aux.get(j), hash, codigo);
			threads[j].start();			
		}
		for(int j = 0; j<threads.length; j++)
		{
			try {
				threads[j].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return threads[0].respuesta;
		
	}
}
