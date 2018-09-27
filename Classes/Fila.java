import java.lang.reflect.*;

/**
* A classe Fila<X> se define por ser uma classe gen�rica de Fila, ou seja, uma classe de armazenamento em fila ("First In, First Out)
* que pode receber qualquer valor, pois est� adaptada para isso. Ela conta com m�todos getters para que trabalhemos sem deturpar as
* vari�veis originais, m�todos de entrada e sa�da de dados da Fila e m�todos boolean para checagem de capacidade (sem contar os m�todos
* obrigat�rios).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/




public class Fila<X> implements Cloneable
{
	    protected Object[] vetor;  //private String[] vetor --> ainda n�o tem tamanho
	    protected int qtd = 0;
	    protected int inicio = 0;
	    protected int fim = 0;



/**
 * Este � o construtor da classe Fila. Ele recebe, como par�metro, a vari�vel inteira que define
 * a capacidade e, a partir disso, cria uma fila de tal capacidade. Se a capacidade for inferior
 * a 0, o que n�o � permitido a uma pilha, lan�aremos uma excess�o.
 *
 * @param int capacidade
 * @throws Exception "Capacidade inv�lida"
 */

	    public Fila(int capacidade) throws Exception
	    {
			if(capacidade < 0)
			   throw new Exception("Capacidade inv�lida");

			this.vetor = new Object[capacidade];
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo cloneDeX define um clone do tipo X escolhido pelo usu�rio.
* Seu par�metro para funcionalidade � o x.
*
* @param X x
*/

		protected X meuCloneDeX(X x)
		{
			X ret = null;
			try
			{
				Class<?> classe = x.getClass();
				Class<?>[] tiposDeParametrosFormais = null;
				Method metodo = classe.getMethod("clone", tiposDeParametrosFormais);
				Object[] tiposDeParametrosReais = null;
				ret = (X)metodo.invoke(x,tiposDeParametrosReais);
			}
			catch(NoSuchMethodException erro)
			{}
			catch(IllegalAccessException erro)
			{}
			catch(InvocationTargetException erro)
			{}

			return ret;
		}



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo guarde recebe, como par�metro, um item do tipo escolhido pelo usu�rio da classe para a fila.
* O item entrar� na �ltima posi��o. O m�todo lan�a excess�es para caso o par�metro seja null e para caso
* a fila j� esteja cheia.
*
* @param X s
* @throws Exception "Informa��o ausente", "Fila cheia"
*/

	    public void guarde(X s) throws Exception
	    {
			if(s==null) // s.equals antes n�o daria certo, pois se ele for null vai dar errado j� que n�o se pode chamar m�todo para objeto null
			   throw new Exception("Informa��o ausente");

			if(this.isCheia())
			   throw new Exception("Fila cheia");

			if(s instanceof Cloneable)
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = meuCloneDeX(s);
				}
				//this.vetor[this.qtd] = (Horario)s.clone();
				else
					this.vetor[this.fim++] = s;
			}
			else
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = s;
				}
				//this.vetor[this.qtd] = (Horario)s.clone();
				else
					this.vetor[this.fim++] = s;
			}
			this.qtd++;
	    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo getUmItem tem, como objetivo, retirar um dado da primeira posi��o da fila.
* O m�todo lan�a excess�es para caso a fila esteja vazia.
*
* @throws Exception "Nada a recuperar"
*/

	    public X getUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Nada a recuperar");

	        if(this.vetor[this.inicio] instanceof Cloneable)
	        	return meuCloneDeX((X)this.vetor[this.inicio]);
	        return (X)this.vetor[this.inicio];
	    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo jogueForaUmItem � o m�todo que elimina um dado da fila.
* O m�todo lan�a excess�es para caso a fila esteja vazia.
*
* @throws Exception "Fila vazia"
*/


	    public void jogueForaUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Fila vazia");

			this.vetor[this.inicio] = null;

			if(this.inicio == this.vetor.length-1)
			   inicio = 0;
			else
				inicio++;

	        qtd--;
	    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo isCheia � um boolean que nos mostra quando a fila est� cheia ou n�o,
* ou seja, quando seu n�mero de dados � semelhante ao da capacidade.
*/


	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo isVazia � um outro boolean. Ele nos mostra quando a fila est� vazia, isto �,
* quando sua quantidade de dados � 0.
	Nos tivemos um problema com o isVazia Original, entao substituimos por essa versao, nao sabemos o que estava acontecendo de errado com o outro, mas comprometia o funcionamento da classe
*/

	    public boolean isVazia()
	    {
			boolean estaVazio = true;
			  for (int i = 0; i < this.vetor.length; i++)
			   	if (this.vetor[i] != null)
			       {
			          estaVazio = false;
			          break;
			       }
            return estaVazio;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo toString nos traz quantos elementos temos na fila e qual � o atual primeiro deles.
* Ele � um dos m�todos obrigat�rios em Java.
*/


		public String toString()
		{
			if(this.qtd==0)
			   return "Vazia";

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo equals � o respons�vel por comparar um elemento de fila a um objeto de par�metro
* Ele � um dos m�todos obrigat�rios em Java.
*
* @param Object obj
*/


		public boolean equals (Object obj) //compara this e obj
		{
			if(this==obj) //dispens�vel, mas deixa m�todo mais r�pido
			   return true;

			if(obj == null)
			   return false;

			if(this.getClass()!=obj.getClass())
			   return false;

			Fila<X> fila = (Fila<X>)obj; // java enxerga que existe uma Fila chamada fila (que � o mesmo obj)

	        if(this.qtd!=fila.qtd)
	           return false;

	        for(int i = 0,
	                posThis=this.inicio,
	                posFila=fila.inicio;

	            i < this.qtd;

	            i++,
	            posThis=(posThis<this.vetor.length-1?posThis+1:0),
	            posFila=(posFila<fila.vetor.length-1?posFila+1:0))

	           if(!this.vetor[posThis].equals(fila.vetor[posFila]))
	              return false;

	        return true;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo hashCode demonstra o c�digo hash de cada elemento da fila por meio de multiplica��es e somas com outros n�meros.
* Ele � um dos m�todos obrigat�rios em Java.
*/


		public int hashCode()
		{
			int ret = 1; //s� n�o pode ser 0

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O construtor de c�pia � como um clone, mas para aquilo que pertence � pr�pria fila.
* O prop�sito � evitar corrompimento de dados.
*
* @param Fila<X> modelo
* @throws Exception "Modelo ausente"
*/

		public Fila (Fila<X> modelo) throws Exception
		{
			if(modelo == null)
		    	throw new Exception("Modelo ausente");

			this.qtd = modelo.qtd;

			this.inicio = modelo.inicio;

			this.fim = modelo.fim;

			this.vetor = new Object[modelo.vetor.length];

			for(int i=0; i<modelo.vetor.length; i++)
		    	this.vetor[i] = modelo.vetor[i];
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo clone � o m�todo duplicador de objetos leg�timos de Java.
* Ele � um dos m�todos obrigat�rios em Java.
*/


		public Object clone()
		{
			Fila<X> ret = null;
			try
			{
				ret = new Fila<X>(this);
			}
			catch(Exception erro)
			{}

			return ret;
		}

}