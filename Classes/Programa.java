import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
public class Programa
{

	public static void main(String[] args)  throws Exception
	{
		char[][] labirinto = null;
		Pilha<Coordenada> caminho;
		Pilha<Fila<Coordenada>> possibilidades;
		boolean achouSaida = false;
		int modo = 1; //Modo de funcionamento do programa, 1 para progressivo, 2 para regressivo
		//Variaveis para armazenar a Entrada e Saída e printa las no final
		Coordenada entrada;
		Coordenada saida = null;

		try
		{
			//Leitura do Arquivo
			Scanner ler = new Scanner(System.in);
			System.out.printf("Informe o nome de arquivo texto:\n");
    		String nome = ler.nextLine();
    		int qtdLinhas = 0;
    		int qtdColunas = 0;
			try
			{
		    	FileReader arq = new FileReader(nome);
		      	BufferedReader lerArq = new BufferedReader(arq);

		      	qtdLinhas = Integer.parseInt(lerArq.readLine());
		      	qtdColunas = Integer.parseInt(lerArq.readLine());

		      	labirinto = new char[qtdColunas][qtdLinhas];

			  	String linha;

			  	for(int e = 0; e < qtdColunas; e++)
			  	{
					linha = lerArq.readLine();
		      		for(int i = 0; i < qtdLinhas; i++)
		      		labirinto[i][e] = linha.charAt(i);
		      	}

		      	arq.close();
		    }
		    catch (IOException e)
		    {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
		    //Fim da Leitura do arquivo
			int qtdDeCoordenadas = qtdLinhas * qtdColunas;

		    caminho = new Pilha<Coordenada>(qtdDeCoordenadas);
		    possibilidades = new Pilha<Fila<Coordenada>>(qtdDeCoordenadas);

			//Busca pela entrada 'E' do labirinto ///Adicionar algo mpara tratar mais de uma Entrada
		    Coordenada atual = null;
			boolean achou = false;
			for(int Xi = 0; Xi < qtdColunas; Xi++)
		    	if(labirinto != null || labirinto[Xi][0] == 'E')
		    	{
		   	 		achou = true;
		   	 		atual = new Coordenada(Xi,0);
		    		break;
				}
			else if(!achou)
			for(Xi = 0; Xi < qtdColunas; Xi++)
				if(labirinto[Xi][qtdLinhas - 1] == 'E')
				{
					achou = true;
					atual = new Coordenada(Xi,qtdLinhas - 1);
					break;
				}
			else if(!achou)
			for(int Yi = 1; Yi < qtdLinhas - 1; Yi++) //Começa em 1 e termina em qtdLinhas - 1 pois o primeiro e o ultimo ja foram verificados
				if(labirinto[0][Yi] == 'E')
				{
					achou = true;
					atual = new Coordenada(0,Yi);
					break;
				}
			else if(!achou)
			for(Yi = 1; Yi < qtdLinhas - 1; Yi++) //Começa em 1 e termina em qtdLinhas - 1 pois o primeiro e o ultimo ja foram verificados
				if(labirinto[qtdColunas - 1][Yi] == 'E')
				{
					achou = true;
					atual = new Coordenada(qtdColunas - 1,Yi);
					break;
				}
				entrada = atual;
			 if(!achou)
				throw new Exception("Labirinto sem entrada valida!");
		//Enquanto não achar a saída, percorre o libirintoi no modo progressivo e regressivo
		while(!achouSaida)
		{
			while(modo == 1)//Modo Progressivo (1)
			{
				Fila<Coordenada> fila = new Fila<Coordenada>(3);

				int X = atual.getX();
				int Y = atual.getY();

				//Verificação dos adjacentes
				if(labirinto[X][Y - 1] == ' ')
					fila.guarde(new Coordenada(X, Y - 1));

				if(labirinto[X + 1][Y] == ' ')
					fila.guarde(new Coordenada(X + 1 , Y));

				if(labirinto[X][Y + 1] == ' ')
					fila.guarde(new Coordenada(X , Y + 1));

				if(labirinto[X - 1][Y] == ' ')
					fila.guarde(new Coordenada(X , Y - 1));

				//Verificação de se a saída foi encontrada
				if(labirinto[X][Y - 1] == 'S')
				{
					saida = new Coordenada(X,Y-1);
					achouSaida = true;
				}
			 	else if(labirinto[X + 1][Y] == 'S')
			 	{
					saida = new Coordenada(X+1 , Y);
					achouSaida = true;
				}
			 	else if(labirinto[X][Y + 1] == 'S')
			 	{
					saida = new Coordenada(X ,Y+1);
					achouSaida = true;
				}
	         	else if(labirinto[X - 1][Y] == 'S')
	         	{
					saida = new Coordenada(X - 1,Y);
					achouSaida = true;
				}
				//Se nenhum adjacente foi encontrado, fila entra em modo regressivo
				if(fila == null)
					modo = 2;

				//Da um "passo" até um adjacente, e guarda os outros na fila de possibilidades
				atual = fila.getUmItem();
				labirinto[atual.getX()][atual.getY()] = '*';
				caminho.guarde(atual);
				possibilidades.guarde(fila);
			}

			while(modo == 2)//Modo Regressivo(2)
			{
				int X = atual.getX();
				int Y = atual.getY();

				//Verificação dos adjacentes para o modo regressivo
				if(labirinto[X][Y - 1] == '*')
				fila.guarde(new Coordenada(X, Y - 1));

				if(labirinto[X + 1][Y] == '*')
				fila.guarde(new Coordenada(X + 1 , Y));

				if(labirinto[X][Y + 1] == '*')
				fila.guarde(new Coordenada(X , Y + 1));

				if(labirinto[X - 1][Y] == '*')
			    fila.guarde(new Coordenada(X , Y - 1));


			}

		}

		System.out.println("Saída Encontrada!O caminho que leva até ela é:");
		while(!caminho.isVazia())
			System.out.print(caminho.getUmItem().toString());
		System.out.println("Entrada: " + entrada.toString());
		System.out.println("Saída: "+ saida.toString());


		}


		catch(Exception e)
		{}


	}

}