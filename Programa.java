import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			labirinto = new Labirinto(teclado.readLine());

			labirinto.resolverLabirinto();


			PrintWriter resultado =
			new PrintWriter(
			new File Writer(nomeArquivo + ".res.txt", "UTF-8");
			resultado.println(this.labirintoResolvido());
			resultado.close();








		for(int linhaP = 0; linhaP < qtdLinhas;linhaP++)
		{
			System.out.println();
			for(int colunaP = 0; colunaP < qtdColunas;colunaP++)
				System.out.print(labirinto[linhaP][colunaP]);

		}
		System.out.println();
		System.out.println();
		System.out.println("Sa�da Encontrada!O caminho que leva at� ela �:");
		Pilha<Coordenada> inverso = new Pilha<Coordenada>(qtdDeCoordenadas);
		while(!caminho.isVazia())
		{
			inverso.guarde(caminho.getUmItem());
			caminho.jogueForaUmItem();
		}
		System.out.print(entrada.toString());
		while(!inverso.isVazia())
		{
			System.out.print(inverso.getUmItem());
			inverso.jogueForaUmItem();
		}
		System.out.print(saida.toString());
		System.out.println();
		System.out.println("Entrada: " + entrada.toString());
		System.out.println("Sa�da: "+ saida.toString());
		}


		catch(Exception e)
		{
			System.out.println(e.getMessage() + e.toString());
		}


	}

}