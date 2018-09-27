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
			char[][] labirinto = null;
			Pilha<Coordenada> caminho;
			Pilha<Fila<Coordenada>> possibilidades;
			boolean achouSaida = false;
			//Vari�veis para armazenar a Entrada e Sa�da e print�-las ao final de toda a resolu��o.
			Coordenada entrada = null;
			Coordenada saida = null;

			//Leitura do Arquivo
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)) ;
			System.out.println("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();

    		int qtdLinhas = 0;
    		int qtdColunas = 0;

			FileReader arq = new FileReader(localArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

		    qtdLinhas = Integer.parseInt(lerArq.readLine().trim());
		    qtdColunas = Integer.parseInt(lerArq.readLine().trim());

		    labirinto = new char[qtdLinhas][qtdColunas];

			String linha;

			for(int e = 0; e < qtdLinhas; e++)
			{
				linha = lerArq.readLine();

		      	for(int i = 0; i < qtdColunas; i++)
		      	labirinto[e][i] = linha.charAt(i);
		    }

		    arq.close();

		    //Fim da Leitura do arquivo.
			int qtdDeCoordenadas = qtdLinhas * qtdColunas;

		    caminho = new Pilha<Coordenada>(qtdDeCoordenadas);
		    possibilidades = new Pilha<Fila<Coordenada>>(qtdDeCoordenadas);

			//Busca pela entrada 'E' do labirinto
		    Coordenada atual = null;
			boolean achou = false;

			for(int lin = 0 ;lin < qtdLinhas; lin++)
				for(int col = 0; col < qtdColunas; col++)
					if(lin == 0 || col == 0 || lin == qtdLinhas - 1 || col == qtdColunas - 1)
						if(labirinto[lin][col] == 'E')
						{
							atual = new Coordenada(lin,col);
							entrada = new Coordenada(lin,col);
							break;
						}
			if(atual == null)
				throw new Exception("Labirinto sem entrada");


		boolean entrouReg = false;
		Fila<Coordenada> fila = null;
		//Enquanto n�o encontrar a sa�da, percorrer� o labirinto nos modos progressivo e regressivo.
		while(!achouSaida)
		{
			if (!entrouReg)
			{
			fila = new Fila<Coordenada>(3);

			int x = atual.getX();
			int y = atual.getY();

			//Verifica��o dos adjacentes.
			if(y - 1 >= 0)
				if(labirinto[x][y - 1] == ' ' || labirinto[x][y - 1] == 'S')
					fila.guarde(new Coordenada(x, y - 1));
			if(x + 1 < qtdLinhas)
				if(labirinto[x + 1][y] == ' ' || labirinto[x + 1][y] == 'S')
					fila.guarde(new Coordenada(x + 1 , y));

			if(y + 1 < qtdColunas)
				if(labirinto[x][y + 1] == ' ' || labirinto[x][y + 1]== 'S')
					fila.guarde(new Coordenada(x , y + 1));

			if(x - 1 >= 0)
				if(labirinto[x - 1][y] == ' ' || labirinto[x - 1][y]== 'S')
					fila.guarde(new Coordenada(x - 1 , y));

			}
			else
			{
				entrouReg = false;
			}

			//Se nenhum adjacente for encontrado, o programa entra em modo regressivo.
			if(fila.isVazia())
			{
				boolean regressivo = true;
				while(regressivo)//Modo Regressivo(2)
				{
					atual = caminho.getUmItem();
					caminho.jogueForaUmItem();

					labirinto[atual.getX()][atual.getY()] = ' ';
					//Isto simboliza a volta do explorador, ou seja, � como se ele estivesse recolhendo o que deixou para sinalizar por onde j� passou.

					fila = possibilidades.getUmItem();
					possibilidades.jogueForaUmItem();

					if(!fila.isVazia())
					{
						regressivo = false;
						entrouReg = true;
					}
					else if(possibilidades.isVazia())
								throw new Exception("N�o existe um caminho para a sa�da");
					}

			}
			else//Modo Progressivo.
			{
				//D� um "passo" at� um adjacente e guarda os outros na fila de possibilidades.
				atual = (Coordenada )fila.getUmItem();
				fila.jogueForaUmItem();


				//Verifica��o da saida.
				int xS = atual.getX();
				int yS = atual.getY();
				if(labirinto[xS][yS] == 'S')
				{
					saida = new Coordenada(xS,yS);
					achouSaida = true;
					break;
				}
				else
				{
					labirinto[atual.getX()][atual.getY()] = '*'; //Sinaliza��o de que j� passamos por ali.
					caminho.guarde(atual);
					possibilidades.guarde(fila);

					//Mostra o Labirinto passo a passo. OBS:AUMENTA DEMAIS A DEMORA DO PROGRAMA
					//Por isso ele s� printa se o numero de coordenadas for menor que 1000
					if(qtdDeCoordenadas < 1000)
					{
						for(int linhaP = 0; linhaP < qtdLinhas;linhaP++)
						{
							System.out.println();
							for(int colunaP = 0; colunaP < qtdColunas;colunaP++)
								System.out.print(labirinto[linhaP][colunaP]);

						}
						System.out.println();
					}

				}

			}
		}

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