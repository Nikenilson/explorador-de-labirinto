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
	//Vari�veis para armazenar a Entrada e Sa�da e print�-las ao final de toda a resolu��o.
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
					throw new Exception ("Tamanho inv�lido!");

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
		//Enquanto n�o encontrar a sa�da, percorrer� o labirinto nos modos progressivo e regressivo.
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

	protected void regressivo() throws Exception
	{
		boolean regressivo = true;
		while(regressivo)//Modo Regressivo
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
			}
			else if(possibilidades.isVazia())
					throw new Exception("N�o existe um caminho para a sa�da");
		}
	}

	protected void progressivo() throws Exception
	{
		//D� um "passo" at� um adjacente e guarda os outros na fila de possibilidades.
		atual = (Coordenada)fila.getUmItem();
		fila.jogueForaUmItem();


		//Verifica��o da saida.
		int xS = atual.getX();
		int yS = atual.getY();
		if(labirinto[xS][yS] == 'S')
		{
			saida = new Coordenada(xS,yS);
			achouSaida = true;
		}
		else
		{
			labirinto[atual.getX()][atual.getY()] = '*'; //Sinaliza��o de que j� passamos por ali.
			caminho.guarde(atual);
			possibilidades.guarde(fila);
		}

	}

	protected void printarResultado
}



