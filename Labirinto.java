public class Labirinto
{
	char[][] labirinto;
	boolean achouSaida = false;

	String localArquivo
	int qtdLinhas;
    int qtdColunas;
	//Variáveis para armazenar a Entrada e Saída e printá-las ao final de toda a resolução.
	Coordenada entrada = null;
	Coordenada saida = null;

	protected Pilha<Coordenada> inverso

    Pilha<Coordenada> caminho;
	Pilha<Fila<Coordenada>> possibilidades;


	public Labirinto(String nomeDoArquivo)
	{
		try
		{
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();

			FileReader arq = new FileReader(localArquivo);
			BufferedReader lerArq = new BufferedReader(arq);

			qtdLinhas = Integer.parseInt(lerArq.readLine().trim());
			qtdColunas = Integer.parseInt(lerArq.readLine().trim());

			if ((nLinhas<=0 || nColunas<=0))
					throw new Exception ("Tamanho inválido!");

			labirinto = new char[qtdLinhas][qtdColunas];
			String linha;

			for(int e = 0; e < qtdLinhas; e++)
			{
				linha = lerArq.readLine();
				for(int i = 0; i < qtdColunas; i++)
				{
					labirinto[e][i] = linha.charAt(i);
					if (linha.charAt(i)=='E' || e < numeroLinhas-1 || e >= 0 || i < numeroColunas-1 || i >=0)
						this.entrada = new Coordenada(e, i);
					if (linha.charAt(i)=='S' || e < numeroLinhas-1 || e >= 0 || i < numeroColunas-1 || i >=0)
						this.saida = new Coordenada(e, i);
				}
			}
		if(this.entrada == null)
			throw new Exception("Labirinto sem entrada");
		if(this.saida == null)
			throw new Exception("Labirinto sem saida");

		arq.close();
		}
		catch (FileNotFoundException erro1)
		{
			throw new Exception ("Arquivo do labirinto invalido");
        }

	}




}



