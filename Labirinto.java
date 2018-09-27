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
		//Inverte o Caminho para deixalo na ordem correta
		inverso = new Pilha<Coordenada>(qtdCoordenadas);
		while(!caminho.isVazia())
		{
			inverso.guarde(caminho.getUmItem());
			caminho.jogueForaUmItem();
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

	public String toString()
	{
		String ret = " ";

		for(int e = 0; e < qtdLinhas; e++)
			for(int i = 0; i < qtdColunas ; i++)
				ret = ret + labirinto[e][i] + "\n\r";

		if(achouSaida)
		{
			while(!inverso.isVazia())
			{
				ret = ret + inverso.getUmItem().toString() + "\n\r";
				inverso.jogueForaUmItem();
			}

			ret = ret + "Entrada: " + entrada.toString() + "\n\r";
			ret = ret + "Saída: "+ saida.toString() + "\n\r";
		}

		return ret;
	}
	public int hashCode()
	{
		int ret = 666;

		ret = ret * 2 + new Integer(qtdLinhas).hashCode();
		ret = ret * 2 + new Integer(qtdColunas).hashCode();
		ret = ret * 2 + entrada.hashCode();
		ret = ret * 2 + saida.hashCode();
		ret = ret * 2 + atual.hashCode();
		ret = ret * 2 + fila.hashCode();
		ret = ret * 2 + caminho.hashCode();
		ret = ret * 2 + possibilidades.hashCode();
		ret = ret * 2 + inverso.hashCode();
		for(int x = 0; x < qtdLinhas; x++)
			for(int y = 0;y < qtdColunas; y++)
				ret = ret * 2 + labirinto[x][y];

		return ret;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
		return true;

		if(obj == null)
		return false;

		if(obj.getClass()!= this.getClass())
		return false;

		Labirinto lab = (Labirinto) obj;

		if(this.achouSaida != lab.achouSaida)
		return false;

		if(this.qtdLinhas != lab.qtdLinhas)
		return false;

		if(this.qtdColunas != lab.qtdColunas )
		return false;

		if(this.entrada != lab.entrada)
		return false;

		if(this.saida != lab.saida)
		return false;

		if(this.atual != lab.atual)
		return false;

		if(this.fila != lab.fila)
		return false;

		if(this.caminho != lab.caminho)
		return false;

		if(this.possibilidades != lab.possibilidades)
		return false;

		if(this.inverso != lab.inverso)
		return false;
		for(int x = 0; x < qtdLinhas; x++)
				for(int y = 0;y < qtdColunas; y++)
					if(this.labirinto[x][y] != lab.labirinto[x][y])
						return false;

		return true;

	}
}