import java.io.*;
import fila.*;
import pilha.*;
import coordenada.*;

public class Labirinto
{
	protected char[][] labirinto;
	protected boolean achouSaida = false;

	protected String localArquivo;
	protected int qtdLinhas;
    protected int qtdColunas;
	//Variáveis para armazenar a Entrada e Saída e printá-las ao final de toda a resolução.
	protected Coordenada entrada;
	protected Coordenada saida;
	protected Coordenada atual;

	protected Fila<Coordenada> fila;
	protected Pilha<Coordenada> caminho;
	protected Pilha<Fila<Coordenada>> possibilidades;
	protected Pilha<Coordenada> inverso;

	int qtdCoordenadas;

	public Labirinto(String nomeDoArquivo) throws Exception
	{
		try
		{
			FileReader arq = new FileReader(localArquivo);
			BufferedReader lerArq = new BufferedReader(arq);

			qtdLinhas = Integer.parseInt(lerArq.readLine().trim());
			qtdColunas = Integer.parseInt(lerArq.readLine().trim());

			if ((qtdLinhas<=0 || qtdColunas<=0))
					throw new Exception ("Tamanho inválido!");

			labirinto = new char[qtdLinhas][qtdColunas];
			String linha;

			for(int e = 0; e < qtdLinhas; e++)
			{
				linha = lerArq.readLine();
				for(int i = 0; i < qtdColunas; i++)
				{
					labirinto[e][i] = linha.charAt(i);
					if (linha.charAt(i)=='E' || e < qtdLinhas-1 || e >= 0 || i < qtdColunas-1 || i >=0)
						this.entrada = new Coordenada(e, i);
					if (linha.charAt(i)=='S' || e < qtdLinhas-1 || e >= 0 || i < qtdColunas-1 || i >=0)
						this.saida = new Coordenada(e, i);
				}
			}
		if(this.entrada == null)
			throw new Exception("Labirinto sem entrada");
		if(this.saida == null)
			throw new Exception("Labirinto sem saida");

		arq.close();
		}
		catch (FileNotFoundException e1)
		{
			throw new Exception ("Arquivo do labirinto invalido");
        }
        catch (IOException e2)
		{
			throw new Exception ("Falha na leitura do arquivo");
		}

	}

	public void resolverLabirinto() throws Exception
	{
		qtdCoordenadas = qtdLinhas * qtdColunas;
		caminho = new Pilha<Coordenada>(qtdCoordenadas);
		possibilidades = new Pilha<Fila<Coordenada>> (qtdCoordenadas);

		boolean entrouReg = false;
		Fila<Coordenada> fila = null;

		atual = new Coordenada (entrada.getX(),entrada.getY());
		//Enquanto não encontrar a saída, percorrerá o labirinto nos modos progressivo e regressivo.
		while(!achouSaida)
		{
			if (!entrouReg)
			{
				acharAdjacentes();
			}
			else
			{
				entrouReg = false;
			}

			//Se nenhum adjacente for encontrado, o programa entra em modo regressivo.
			if(fila.isVazia())
			{
				regressivo();
				entrouReg = true;

			}
			else//Modo Progressivo.
			{
				progressivo();
			}
		}
	}



	protected void acharAdjacentes() throws Exception
	{
		int x = atual.getX();
		int y = atual.getY();

		//Verificação dos adjacentes.
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

	protected void regressivo() throws Exception
	{
		boolean regressivo = true;
		while(regressivo)//Modo Regressivo
		{
			atual = caminho.getUmItem();
			caminho.jogueForaUmItem();

			labirinto[atual.getX()][atual.getY()] = ' ';
			//Isto simboliza a volta do explorador, ou seja, é como se ele estivesse recolhendo o que deixou para sinalizar por onde já passou.

			fila = possibilidades.getUmItem();
			possibilidades.jogueForaUmItem();

			if(!fila.isVazia())
			{
				regressivo = false;
			}
			else if(possibilidades.isVazia())
					throw new Exception("Não existe um caminho para a saída");
		}
	}

	protected void progressivo() throws Exception
	{
		//Dá um "passo" até um adjacente e guarda os outros na fila de possibilidades.
		atual = (Coordenada)fila.getUmItem();
		fila.jogueForaUmItem();


		//Verificação da saida.
		int xS = atual.getX();
		int yS = atual.getY();
		if(labirinto[xS][yS] == 'S')
		{
			saida = new Coordenada(xS,yS);
			achouSaida = true;
		}
		else
		{
			labirinto[atual.getX()][atual.getY()] = '*'; //Sinalização de que já passamos por ali.
			caminho.guarde(atual);
			possibilidades.guarde(fila);
		}

	}

	protected void printarResultado
}



