import java.io.*;
import fila.*;
import pilha.*;
import coordenada.*;

/**
* Esta � nossa classe Programa do explorador de labirintos. Ela cont�m toda a codifica��o de vari�veis, m�todos
* e intera��es entre as classes Coordenada, Fila e Pilha necess�rias ao funcionamento do aplicativo. Conforme fizemos
* a codifica��o do projeto, n�s nos pusemos a escrever os passos mais relevantes (exemplo: significado das vari�veis
* e o funcionamento dos modos progressivo e regressivo).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/

public class Programa
{

	public static void main(String[] args)  throws Exception
	{
		try
		{
			Labirinto labirinto;

			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String nomeArquivo = teclado.readLine();

			labirinto = new Labirinto(nomeArquivo);
			labirinto.resolverLabirinto();

			System.out.println("Sa�da Encontrada!O caminho que leva at� ela e o labirinto resolvido foram guardados no arquivo: " + nomeArquivo + ".res.txt" );

			PrintStream resultado = new PrintStream(nomeArquivo.substring(0, nomeArquivo.length() - 4) + ".res.txt"); //O substring no nome do arquivo � pra retirar o.txt do final
			System.out.println("Deuses sao reais se vc acredita neles");
			resultado.println(labirinto.toString());
			System.out.println("Deuses sao reais se vc acredita neles");
			resultado.close();

		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}


	}

}