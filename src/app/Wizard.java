package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wizard 
{
	private static Scanner s = new Scanner(System.in);
	
	private static String[] WORDS;
	
	private static final String[] ALFABETO = { 
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
		"q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
	};
	
	public static void main(String[] args) 
	{
		iniciarArray();
		
		// Iniciando variaveis
		String[] palavras;
		String palavra;
		List<String> letrasUsadas = new ArrayList<String>();
		String letra;
		String auxPalavra;
		String posicoes;
		String[] pl;
		
		// Pedindo numero da palavra
		System.out.print( "\n------------------------------ BEM-VINDO AO JOGO PARZIVAL ------------------------------\n\nPense uma palavra:\nQuantas letras tem a palavra? \n--> " );
		int tam = s.nextInt();
		// Buscando palavras compatíveis com esse numero
		palavras = verificarLetras(tam);
		// Listando palavra
		palavra = "";
		for (int i = 0; i < tam; i++) 
		{
			palavra += ".";
		}
		
		System.out.println("\n" + palavra + "\n");
		
		while (!seGanhou(palavra, palavras)) 
		{			
			do 
			{
				// Pega a letra nais comum entre as palavas
				letra = letraMaisComum(palavras, letrasUsadas);
				// Pergunta se a letra mais comum está na palavra do usuário
				pl = perguntarLetra(letra, tam, palavra);
				auxPalavra = pl[0];
				posicoes = pl[1];
				
			} while(auxPalavra.isEmpty());
			
			palavras = verificarPalavrasPorLetra(palavras, letra, posicoes.split(",")) ;
			
			palavra = auxPalavra;
		}
		
		System.out.println("Sua palavra é: " + palavras[0] + "\n");
	}
	
	static String[] verificarLetras(int tam) 
	{
		List<String> palavras = new ArrayList<String>();
		
		for (String palavra: WORDS) 
		{
			if (palavra.length() == tam) 
			{
				palavras.add(palavra);
			}
		}
		
		String[] palavrasArray = new String[palavras.size()];
		
		for (int i = 0; i < palavrasArray.length; i++) 
		{
			palavrasArray[i] = palavras.get(i);
		}
		
		return palavrasArray;
	}
	
	static String[] perguntarLetra(String letra, int tam, String palavra) 
	{
		String palavraNova = "";
		String posicoes = "";
		char[] charAr = palavra.toCharArray();
		
		System.out.print( "A palavra tem a letra " + letra.toUpperCase() + "? (s/n):\n--> ");
		String res = s.next();
		
		if (res.equalsIgnoreCase("s")) 
		{
			System.out.print( "Qual as posições da letra " + letra.toUpperCase() + "? (1 à "+ tam +", separe por vírgulas):\n--> ");
			posicoes = s.next();
			String[] posicoesArray = posicoes.trim().split(",");
			
			for (int i = 0; i < palavra.length(); i++) 
			{
				for (int j = 0; j < posicoesArray.length; j++) 
				{
					int p = Integer.parseInt(posicoesArray[j]) - 1;
					
					if (i == p)
					{
						charAr[i] = letra.charAt(0);
					}
				}
				palavraNova += charAr[i];
			}
		}
		
		return new String[]{ palavraNova, posicoes };
	}
	
	static String[] verificarPalavrasPorLetra(String[] palavras, String letra, String[] positions) 
	{
		List<String> listPalavras = new ArrayList<String>();
		
		for (String palavra: palavras) 
		{
			boolean has = true;
			for (int i = 0; i < positions.length; i++) 
			{
				int position = Integer.parseInt(positions[i]) -1;
				if (!palavra.substring(position, position + 1).equalsIgnoreCase(letra))
				{
					has = false;
				}
			}
			
			if (has)
				listPalavras.add(palavra);
		}
		
		String[] palavrasArray = new String[listPalavras.size()];
		
		for (int i = 0; i < palavrasArray.length; i++) {
			palavrasArray[i] = listPalavras.get(i);
		}
		
		return palavrasArray;
	}

	static String letraMaisComum(String[] palavras, List<String> letrasUsadas) 
	{
		int[] maior = new int[ALFABETO.length];
		
		for (int i = 0; i < ALFABETO.length; i++) 
		{
			// Verifica se ela já foi usada
			boolean usada = false;
			for (int j = 0; j < letrasUsadas.size(); j++) 
			{
				if (letrasUsadas.get(j).equalsIgnoreCase(ALFABETO[i]))
				{
					usada = true;
				}
			}
			
			if (!usada)
			{		
				// Verifica se existe em alguma palavra
				for (int j = 0; j < palavras.length; j++) 
				{
					for (int x = 0; x < palavras[j].length(); x++) 
					{
						if (palavras[j].substring(x, x + 1).equalsIgnoreCase(ALFABETO[i])) 
						{
							maior[i] = maior[i] + 1;
						}
					}
				}
			}
		}
		
		// Verifica a que mais tem
		String letra = "";
		int maiorLetra = Integer.MIN_VALUE;
		
		for (int i = 0; i < maior.length; i++) 
		{
			if (maior[i] > maiorLetra)
			{
				letra = ALFABETO[i];
				maiorLetra = maior[i];
			}
		}
		
		letrasUsadas.add(letra);
		
		return letra;
	}
	
	static boolean seGanhou(String palavra, String[] palavras) 
	{
		if (palavras.length == 1) 
		{
			return true;
		}
		
		for (int i = 0; i < palavra.length(); i++) 
		{
			if (palavra.substring(i, i + 1).equalsIgnoreCase(".")) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	static void iniciarArray() 
	{
		try 
		{
			FileReader arq = new FileReader("words.txt");
			BufferedReader lerArq = new BufferedReader(arq);
	 
			String linha = lerArq.readLine(); 
			int size = 0;
			while (linha != null) 
			{
				size++;
		 
				linha = lerArq.readLine(); 
			}
	      
			setArray(size);
	      
			arq.close();
	    } 
		catch (IOException e) 
		{
			System.err.printf("Erro na abertura do arquivo: %s.\n",
			e.getMessage());
		}
	}
	
	static void setArray(int size) {
		try 
		{
			FileReader arq = new FileReader("words.txt");
			BufferedReader lerArq = new BufferedReader(arq);
		 
			String linha = lerArq.readLine(); 
			WORDS = new String[size];
			int index = 0;
			while (linha != null) 
			{
				WORDS[index] = linha;
				linha = lerArq.readLine(); 
				index++;
			}
			arq.close();
		} 
		catch (IOException e) 
		{
			System.err.printf("Erro na abertura do arquivo: %s.\n",
			e.getMessage());
		}
	}
}


