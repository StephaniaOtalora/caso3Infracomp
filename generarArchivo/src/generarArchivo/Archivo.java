package generarArchivo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Archivo {
	
	public static final String archivo= "./docs/combinaciones.json";
	private static JSONArray combinaciones;
	public static char[] set = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
	
	public Archivo() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void printAllKLengthRec( String prefix, int n, int k) 
	{ 

		// Si el prefijo es cero termina
		if (k == 0)  
		{ 			 
			return; 
		} 
		
		// One by one add all characters  
		// from set and recursively  
		// call for k equals to k-1 
		for (int i = 0; i < n; ++i) 
		{ 
			// Next character of input added 
			String newPrefix = prefix + set[i];
			System.out.println(newPrefix);
			combinaciones.add(newPrefix);

			// k is decreased, because  
			// we have added a new character 
			printAllKLengthRec(newPrefix, n, k - 1);  
		} 
	} 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		combinaciones = new JSONArray();
		
		for(int j = 0; j<set.length; j++)
		{
			printAllKLengthRec(set[j]+"", set.length, 6);
			
		}
		
		try {
			JSONObject obj = new JSONObject();
			obj.put("combinaciones", combinaciones);
			System.out.println(combinaciones);
			FileWriter file = new FileWriter(archivo);
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			//manejar error
		}


	}

}
