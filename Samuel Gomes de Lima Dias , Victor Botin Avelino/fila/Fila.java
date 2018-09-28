package fila;

import java.lang.reflect.*;

/**
 * Classe que armazena itens e os retira com o principio FIFO:
 * First in, first out. Isso significa que os primeiros itens armazenados serao os primeiros retirados.
 * @param <X> Classe dos itens armazenados na fila
 */
public class Fila<X> implements Cloneable
{
		/** Vetor que armazena os itens da fila.*/
		protected Object[] vetor;
		/** Quantidade de itens na fila. */
		protected int qtd = 0;
		/** Inicio real da fila. */
		protected int inicio = 0;
		/** Fim real da fila. */
	    protected int fim = 0;

		/**
		 * Construtor da classe 
		 * @param capacidade Quantidade maxima de itens armazenaveis na fila
		 * @throws Exception Capacidade invalida (negativa)
		 */
	    public Fila(int capacidade) throws Exception
	    {
			if(capacidade < 0)
			   throw new Exception("Capacidade invalida");

			this.vetor = new Object[capacidade];
		}

		/**
		 * Clona o objeto x chamando o clone() indiretamente, ja que ele e generico.
		 * @param x Objeto a ser clonado
		 * @return Clone de x
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
				ret = (X)metodo.invoke(x, tiposDeParametrosReais);
			}
			catch(NoSuchMethodException erro)
			{}
			catch(IllegalAccessException erro)
			{}
			catch(InvocationTargetException erro)
			{}

			return ret;
		}

		/**
		 * Guarda um item na fila.
		 * @param s Item a ser guardado.
		 * @throws Exception Se o item passado for nulo.
		 * @throws Exception Se a fila estiver cheia.
		 */
	    public void guarde(X s) throws Exception
	    {
			if(s==null) // s.equals antes não daria certo, pois se ele for null vai dar errado já que não se pode chamar método para objeto null
			   throw new Exception("Informacao ausente");
			if(this.isCheia())
			   throw new Exception("Fila cheia");

			if(s instanceof Cloneable)
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = meuCloneDeX(s);
				}
				else
					this.vetor[this.fim++] = meuCloneDeX(s);
			}
			else
			{
				if(fim == this.vetor.length-1)
				{
				   fim = 0;
				   this.vetor[this.fim] = s;
				}
				else
					this.vetor[this.fim++] = s;
			}
			this.qtd++;
	    }

		/**
		 * Retorna o primeiro item da fila.
		 * @return primeiro item armazenado na fila.
		 * @throws Exception Se a fila estiver vazia.
		 */
	    public X getUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Nada a recuperar");

	        if(this.vetor[this.inicio] instanceof Cloneable)
	        	return meuCloneDeX((X)this.vetor[this.inicio]);

	        return (X)this.vetor[this.inicio];
	    }

		/**
		 * Joga fora o primeiro item da fila.
		 * @throws Exception Se a fila estiver vazia
		 */
	    public void jogueForaUmItem() throws Exception
	    {
			if(this.isVazia())
			   throw new Exception("Pilha vazia");

			this.vetor[this.inicio] = null;

			if(this.inicio == this.vetor.length-1)
			   inicio = 0;
			else
				inicio++;

	        this.qtd--;
	    }

		/**
		 * Testa se a fila esta cheia.
		 * @return true se a fila estiver cheia ou false se nao estiver.
		 */
	    public boolean isCheia()
	    {
			return this.qtd == this.vetor.length;
		}

		/**
		 * Testa se a fila esta vazia.
		 * @return true se a fila estiver vazia ou false se nao estiver.
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

		/**
		 * Retorna uma representacao textual da fila, informando a quantidade de elementos e
		 * o proximo elemento a ser retirado ou retornado, que e o primeiro da fila.
		 * @return Representacao textual da fila
		 */
		public String toString()
		{
			if(this.qtd==0)
			   return "Vazia";

			return this.qtd+" elementos, sendo o primeiro "+this.vetor[inicio];
		}

		/**
		 * Compara o objeto atual com um outro objeto passado como parametro.
		 * @param obj Objeto a ser comparado com este.
		 * @return Resultado boolean da comparacao.
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

		/**
		 * Retorna um valor de hashCode para a fila, possibilitando a utilizacao em hashtables.
		 * @return Valor do hashCode do objeto.
		 */
		public int hashCode()
		{
			int ret = 1; //só não pode ser 0

			ret = ret * 2 + new Integer(this.qtd).hashCode();

			for(int i=0, pos=inicio; i<this.qtd; i++, pos=(pos<vetor.length-1?pos+1:0))
				ret = ret*2 + this.vetor[pos].hashCode();

			return ret;
		}

		/**
		 * Construtor de copia. Retorna a copia de uma fila ja existente passada como parametro.
		 * @param modelo Fila a ser copiada.
		 * @throws Exception Se o modelo for nulo.
		 */
		public Fila (Fila<X> modelo) throws Exception
		{
			if(modelo == null)
		    	throw new Exception("Modelo ausente");

			this.qtd = modelo.qtd;

			this.inicio = modelo.inicio;

			this.fim = modelo.fim;

			this.vetor = new Object[modelo.vetor.length];

			for(int i=0; i<modelo.vetor.length-1; i++)
		    	this.vetor[i] = modelo.vetor[i];
		}

		/**
		 * Retorna um clone desta fila.
		 * @return Clone da fila.
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