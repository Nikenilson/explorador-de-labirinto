import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			int modo = 1; //Modo de funcionamento do programa, 1 para progressivo, 2 para regressivo e 3 para quando a Saida for encontrada
			//Variaveis para armazenar a Entrada e Saída e printa las no final
			Coordenada entrada;
			Coordenada saida = null;

			//Leitura do Arquivo
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)) ;
			System.out.println("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();

    		int qtdLinhas = 0;
    		int qtdColunas = 0;
			try
			{
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

				int x = atual.getX();
				int y = atual.getY();

				//Verificação dos adjacentes
				if(y - 1 >= 0 && labirinto[x][y - 1] == ' ')
					fila.guarde(new Coordenada(x, y - 1));

				if(x + 1 <= qtdLinhas && labirinto[x + 1][y] == ' ')
					fila.guarde(new Coordenada(x + 1 , y));

				if(y + 1 >= qtdColunas && labirinto[x][y + 1] == ' ')
					fila.guarde(new Coordenada(x , y + 1));

				if(x - 1 >= 0 && labirinto[x - 1][y] == ' ')
					fila.guarde(new Coordenada(x , y - 1));

				//Verificação de se a saída foi encontrada
				if(y - 1 >= 0 && labirinto[x][y - 1] == 'S')
				{
					saida = new Coordenada(x,y-1);
					achouSaida = true;
					modo = 3; //Achou a saida
					break;
				}
			 	else if(x + 1 <= qtdLinhas && labirinto[x + 1][y] == 'S')
			 	{
					saida = new Coordenada(x+1 , y);
					achouSaida = true;
					modo = 3;
					break;
				}
			 	else if(y + 1 >= qtdColunas && labirinto[x][y + 1] == 'S')
			 	{
					saida = new Coordenada(x ,y+1);
					achouSaida = true;
					modo = 3;
					break;
				}
	         	else if(x - 1 >= 0 && labirinto[x - 1][y] == 'S')
	         	{
					saida = new Coordenada(x - 1,y);
					achouSaida = true;
					modo = 3;
					break;
				}
				//Se nenhum adjacente for encontrado, fila entrará em modo regressivo
				if(fila.isVazia())
				{
					modo = 2;
					break;
				}
				//Dá um "passo" até um adjacente, e guarda os outros na fila de possibilidades
				atual = fila.getUmItem();
				fila.jogueForaUmItem();

				labirinto[atual.getX()][atual.getY()] = '*';
				caminho.guarde(atual);
				possibilidades.guarde(fila);
			}


System.out.println("God helps who foudaci");



			while(modo == 2)//Modo Regressivo(2)
			{
				System.out.println("God_1");

					Fila<Coordenada> fila = new Fila<Coordenada>(3);

	                atual = caminho.getUmItem();
	                caminho.jogueForaUmItem();

					System.out.println("God_2");
	                labirinto[atual.getX()][atual.getY()] = ' ';

	                fila = possibilidades.getUmItem();
	                possibilidades.jogueForaUmItem();

					System.out.println("God_3");
	                if(!fila.isVazia())
	                {//Como deveriamos continuar do passo 7, e o while do modo 1 começa do 6, fizemos uma "semi" repetição do modo progressivo antes de voltarmos realmente para ele
						System.out.println("entrou no if aee porra");
						atual = fila.getUmItem();
						fila.jogueForaUmItem();

						labirinto[atual.getX()][atual.getY()] = '*';
						caminho.guarde(atual);
						possibilidades.guarde(fila);

	                	modo = 1;
					}
			}


		}

		System.out.println("Saída Encontrada!O caminho que leva até ela é:");
		while(!caminho.isVazia())
			System.out.print(caminho.getUmItem().toString());
		System.out.println("Entrada: " + entrada.toString());
		System.out.println("Saída: "+ saida.toString());


		}


		catch(Exception e)
		{System.out.println(e.getMessage());}


	}

}