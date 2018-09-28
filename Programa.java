import java.io.*;
import fila.*;
import pilha.*;
import coordenada.*;
import labirinto.*;

/**
* Esta é nossa classe Programa do explorador de labirintos. Ela contém toda a codificação de variáveis, métodos
* e interações entre as classes Coordenada, Fila e Pilha necessárias ao funcionamento do aplicativo. Conforme fizemos
* a codificação do projeto, nós nos pusemos a escrever os passos mais relevantes (exemplo: significado das variáveis
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
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String nomeArquivo = teclado.readLine();

			Labirinto labirinto = new Labirinto(nomeArquivo);
			labirinto.resolverLabirinto();//Erro aqui
			labirinto.escreverResultado();
			System.out.println("Saída Encontrada!O caminho que leva até ela e o labirinto resolvido foram guardados no arquivo: " + nomeArquivo + ".res.txt" );


		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}


	}

}