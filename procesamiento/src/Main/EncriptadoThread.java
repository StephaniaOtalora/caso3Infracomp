package Main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;



public class EncriptadoThread extends Thread{

	public static char[] set = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
	char[] inicio;
	String codigo;
	byte[] hash;
	public static String respuesta = null;
	long diff;
	int id;

	public EncriptadoThread(int id, char[] p, byte[]hash, String codigo) {
		// TODO Auto-generated constructor stub
		this.inicio = p;
		this.codigo = codigo;
		this.hash = hash;
		this.diff = 0;
		this.id=id;
	}

	@Override
	public void run() {
		Date t1 = Calendar.getInstance().getTime();
		for (int i=0;i<inicio.length;i++) {
			verificar(this.inicio[i]+"");
		}
		for(int i=1; i<7; i++) {
			for(int j=0;j<inicio.length;j++) {
				printAllKLengthRec(inicio[j]+"", set.length, i);	
			}
		}
		Date t2 = Calendar.getInstance().getTime();
		diff = t2.getTime()-t1.getTime();
		System.out.println("Thread con id "+ this.id + " revisó: "+String.valueOf(inicio)+ ". Tiempo: " +this.diff +" ms");
	}

	// Código adaptado de https://www.geeksforgeeks.org/print-all-combinations-of-given-length/
	public synchronized void printAllKLengthRec( String prefix, int n, int k) 
	{ 

		// Si el prefijo es cero termina
		if (k == 0)  
		{ 
			 
			return; 
		} 
		
		// One by one add all characters  
		// from set and recursively  
		// call for k equals to k-1 
		for (int i = 0; i < n && respuesta==null; ++i) 
		{ 
			// Next character of input added 
			String newPrefix = prefix + set[i];
			System.out.println(newPrefix);
			verificar(newPrefix);

			// k is decreased, because  
			// we have added a new character 
			printAllKLengthRec(newPrefix, n, k - 1);  
		} 
	} 

	public void verificar(String m)
	{
		byte[] mensajeBytes= m.getBytes();
		byte[] hash1 = null;

		try {
			MessageDigest md = MessageDigest.getInstance(codigo);
			md.update(mensajeBytes);
			hash1 = md.digest();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return;
		}

		if(Arrays.equals(hash, hash1))
		{
			this.respuesta=m;
		}
	}

}
