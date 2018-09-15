import java.io;
publib class Programa
{

	public static void main(String[] args)
	{
		char[][] labirinto;
		Pilha<Cordenada> caminho;
		Pilha<Fila<Coordenada>> possibilidades;

		try
		{

			try
			{
		      FileReader arq = new FileReader(nome);
		      BufferedReader lerArq = new BufferedReader(arq);

		      int qtdLinhas = lerArq.readLine();
		      int qtdColunas = lerArq.readLine();

		      labirinto = new char[qtdColunas][qtdLinhas];

			  String linha;

			  for(int e = 0; e < qtdColunas; e++)
			  {
				linha = lerArq.readLine();
		      	for(int i = 0; i < qtdLinhas; i++)
		      	labirinto[i][e] linha.charAt(i);
		      }

		      arq.close();
		    }
		    catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }

		    System.out.println();
  }


		}
		catch(){}


	}


}