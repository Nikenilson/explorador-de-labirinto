package labirinto;
import java.io.*;
import fila.*;
import pilha.*;
import coordenada.*;

/**
* A classe Labirinto define uma classe para abarcar todas as propriedades pertencentes ao labirinto.
* Ela cont�m vari�veis que definem linhas e colunas, vari�veis que cetificam a sa�da e ainda as que
* relacionam �s outras classes.
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/



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
	protected String inversoString;

	int qtdCoordenadas;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O construtor da classe labirinto faz seu trabalho a partir do par�metro .txt que for enviado pelo usu�rio.
* Isso inclui atribui��o de dados �s vari�veis e uma prepara��o do ambiente para que o solucionador entre.
* A exce��o � disparada quando linhas ou colunas s�o menores ou iguais a 0, para quando n�o h� entrada, para
* quando n�o se encontrar o arquivo escolhido e para quando houve falha na leitura.
*
* @param String nomeDoArquivo
* @throws Exception "Tamanho Inv�lido", "Labirinto sem entrada", "Arquivo do labiritno invalido", "Falha na leitura do arquivo"
*/


	public Labirinto(String nomeDoArquivo) throws Exception
	{
		try
		{
			localArquivo = nomeDoArquivo;

			FileReader arq = new FileReader(localArquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			qtdLinhas = Integer.parseInt(lerArq.readLine().trim());
			qtdColunas = Integer.parseInt(lerArq.readLine().trim());

			if ((qtdLinhas<=0 || qtdColunas<=0))
					throw new Exception ("Tamanho inv�lido!");

			labirinto = new char[qtdLinhas][qtdColunas];
			String linha = "";

			for(int e = 0; e < qtdLinhas; e++)
			{
				linha = lerArq.readLine();
				for(int i = 0; i < qtdColunas; i++)
				{
					labirinto[e][i] = linha.charAt(i);
					if (linha.charAt(i)=='E')
					    this.entrada = new Coordenada(e, i);
				}
			}

			arq.close();
		if(this.entrada == null)
			throw new Exception("Labirinto sem entrada");

		}
		catch (FileNotFoundException e1)
		{
			throw new Exception ("Arquivo do labirinto invalido");
        }
        catch (IOException e2)
		{
			throw new Exception ("Falha na leitura do arquivo");
		}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	}

    public int getLinhas()
    {
		return this.qtdLinhas;
    }


    public int getColunas()
    {
		return this.qtdColunas;
    }


    public boolean getAchouSaida()
    {
		return this.achouSaida;
    }


    public String getLocalArquivo()
    {
		return this.localArquivo;
    }


    public char[][] getLabirinto()
    {
	    return this.labirinto;
    }


    public Coordenada getEntrada()
    {
		return this.entrada;
    }

    public Coordenada getSaida()
    {
		return this.saida;
    }


    public Coordenada getAtual()
    {
		return this.atual;
    }


    public Fila getFila()
    {
		return this.fila;
    }


    public Pilha getCaminho()
    {
		return this.caminho;
    }


    public Pilha getPossibilidades()
    {
		return this.possibilidades;
    }

    public Pilha getInverso()
    {
		return this.inverso;
    }

    public String getInversoString()
    {
		return this.inversoString;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void resolverLabirinto() throws Exception
	{
		qtdCoordenadas = qtdLinhas * qtdColunas;

		caminho = new Pilha<Coordenada>(qtdCoordenadas);
		possibilidades = new Pilha<Fila<Coordenada>> (qtdCoordenadas);

		boolean entrouReg = false;
		fila = new Fila<Coordenada>(3);

		atual = new Coordenada(entrada.getX() , entrada.getY());
		//Enquanto n�o encontrar a sa�da, percorrer� o labirinto nos modos progressivo e regressivo.
			while(!achouSaida)
			{
				if(!entrouReg)
					acharAdjacentes();
				else
					entrouReg = false;

				//Se nenhum adjacente for encontrado, o programa entra em modo regressivo.
				if(fila.isVazia())
				{
					regressivo();
					entrouReg = true;

				}
				else//Modo Progressivo.
					progressivo();
			}


		inversoString = " ";
		//Inverte o Caminho para deixa-lo na ordem correta
		inverso = new Pilha<Coordenada>(qtdLinhas * qtdColunas);
		while(!caminho.isVazia())
		{
			inverso.guarde(caminho.getUmItem());
			caminho.jogueForaUmItem();
		}
		while(!inverso.isVazia())
		{
			inversoString = inversoString + inverso.getUmItem().toString();
			inverso.jogueForaUmItem();
		}

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void regressivo() throws Exception
	{
		System.out.println("Entrou regressivo");
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected void progressivo() throws Exception
	{
		System.out.println("Entrou progressivo");
		//D� um "passo" at� um adjacente e guarda os outros na fila de possibilidades.
		System.out.println(";-;");
		atual = fila.getUmItem();
		System.out.println("Entrou batata progressivo");
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void escreverResultado() throws Exception
	{
		PrintStream resultado = new PrintStream(localArquivo.substring(0, localArquivo.length() - 4) + ".res.txt"); //O substring no nome do arquivo � pra retirar o.txt do final
		resultado.println(labirinto.toString());
		resultado.println(inversoString + "\n\r");
		resultado.println("Entrada: " + entrada.toString() + "\n\r");
		resultado.println("Sa�da: "+ saida.toString() + "\n\r");
		resultado.close();
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String toString()
	{
		String ret = " ";

		for(int e = 0; e < qtdLinhas; e++)
		{
			ret = ret + "\n\r";
			for(int i = 0; i < qtdColunas ; i++)
				ret = ret + labirinto[e][i];
		}

		if(achouSaida)
		{
			ret = ret + inversoString + "\n\r";

			ret = ret + "Entrada: " + entrada.toString() + "\n\r";
			ret = ret + "Sa�da: "+ saida.toString() + "\n\r";
		}

		return ret;
	}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		ret = ret * 2 + inversoString.hashCode();
		for(int x = 0; x < qtdLinhas; x++)
			for(int y = 0;y < qtdColunas; y++)
				ret = ret * 2 + labirinto[x][y];

		return ret;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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