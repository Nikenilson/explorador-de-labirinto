import java.lang.reflect.*;

/**
* A classe Fila<X> se define por ser uma classe genérica de Fila, ou seja, uma classe de armazenamento em fila ("First In, First Out)
* que pode receber qualquer valor, pois está adaptada para isso. Ela conta com métodos getters para que trabalhemos sem deturpar as
* variáveis originais, métodos de entrada e saída de dados da Fila e métodos boolean para checagem de capacidade (sem contar os métodos
* obrigatórios).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/




public class Fila<X> implements Cloneable
{
	    protected Object[] vetor;  //private String[] vetor --> ainda não tem tamanho
	    protected int qtd = 0;
	    protected int inicio = 0;
	    protected int fim = 0;



/**
 * Este é o construtor da classe Fila. Ele recebe, como parâmetro, a variável inteira que define
 * a capacidade e, a partir disso, cria uma fila de tal capacidade. Se a capacidade for inferior
 * a 0, o que não é permitido a uma pilha, lançaremos uma excessão.
 *
 * @param int capacidade
 * @throws Exception "Capacidade inválida"
 */

	    public Fila(int capacidade) throws Exception
	    {
			if(capacidade < 0)
			   throw new Exception("Capacidade inválida");

			this.vetor = new Object[capacidade];
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método cloneDeX define um clone do tipo X escolhido pelo usuário.
* Seu parâmetro para funcionalidade é o x.
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
* O método guarde recebe, como parâmetro, um item do tipo escolhido pelo usuário da classe para a fila.
* O item entrará na última posição. O método lança excessões para caso o parâmetro seja null e para caso
* a fila já esteja cheia.
*
* @param X s
* @throws Exception "Informação ausente", "Fila cheia"
*/

	    public void guarde(X s) throws Exception
	    {
			if(s==null) // s.equals antes não daria certo, pois se ele for null vai dar errado já que não se pode chamar método para objeto null
			   throw new Exception("Informação ausente");

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
* O método getUmItem tem, como objetivo, retirar um dado da primeira posição da fila.
* O método lança excessões para caso a fila esteja vazia.
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
* O método jogueForaUmItem é o método que elimina um dado da fila.
* O método lança excessões para caso a fila esteja vazia.
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
* O método isCheia é um boolean que nos mostra quando a fila está cheia ou não,
* ou seja, quando seu número de dados é semelhante ao da capacidade.
*/


	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método isVazia é um outro boolean. Ele nos mostra quando a fila está vazia, isto é,
* quando sua quantidade de dados é 0.
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
* O método toString nos traz quantos elementos temos na fila e qual é o atual primeiro deles.
* Ele é um dos métodos obrigatórios em Java.
*/


		public String toString()
		{
			if(this.qtd==0)
			   return "Vazia";

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método equals é o responsável por comparar um elemento de fila a um objeto de parâmetro
* Ele é um dos métodos obrigatórios em Java.
*
* @param Object obj
*/


		public boolean equals (Object obj) //compara this e obj
		{
			if(this==obj) //dispensável, mas deixa método mais rápido
			   return true;

			if(obj == null)
			   return false;

			if(this.getClass()!=obj.getClass())
			   return false;

			Fila<X> fila = (Fila<X>)obj; // java enxerga que existe uma Fila chamada fila (que é o mesmo obj)

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
* O método hashCode demonstra o código hash de cada elemento da fila por meio de multiplicações e somas com outros números.
* Ele é um dos métodos obrigatórios em Java.
*/


		public int hashCode()
		{
			int ret = 1; //só não pode ser 0

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O construtor de cópia é como um clone, mas para aquilo que pertence à própria fila.
* O propósito é evitar corrompimento de dados.
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
* O método clone é o método duplicador de objetos legítimos de Java.
* Ele é um dos métodos obrigatórios em Java.
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