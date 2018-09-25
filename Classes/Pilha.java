import java.lang.reflect.*;


/**
* A classe Pilha<X> se define por ser uma classe genérica de Pilha (assim como ocorre para a classe genérica de Fila), ou seja, uma classe de armazenamento
* em pilha ("Last in, First Out") que pode receber qualquer valor, pois está adaptada para isso. Ela conta com métodos getters para que trabalhemos sem deturpar
* as variáveis originais, métodos de entrada e saída de dados da Fila e métodos boolean para checagem de capacidade (sem contar os métodos obrigatórios).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/



public class Pilha<X> implements Cloneable
{
    protected Object[] vetor;
    protected int qtd = 0;


/**
 * Este é o construtor da classe Pilha. Ele recebe, como parâmetro, a variável inteira que define
 * a capacidade e, a partir disso, cria uma pilha de tal capacidade. Se a capacidade for inferior
 * a 0, o que não é permitido a uma pilha, lançaremos uma excessão.
 *
 * @param int capacidade
 * @throws Exception "Capacidade inválida"
 */

    public Pilha (int capacidade) throws Exception
    {

        if (capacidade<0)
            throw new Exception ("Capacidade invalida");

        this.vetor = new Object [capacidade];
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método guarde recebe, como parâmetro, um item do tipo escolhido pelo usuário da classe para a pilha.
* O item entrará na última posição. O método lança excessões para caso o parâmetro seja null e para caso
* a pilha já esteja cheia.
*
* @param X x
* @throws Exception "Informação ausente", "Pilha cheia"
*/

    public void guarde (X x) throws Exception
    {
	if (x==null) //|| x.equals("")) para String
            throw new Exception ("Informacao ausente");

	if (this.isCheia())
            throw new Exception ("Pilha cheia");

	if(x instanceof Cloneable)
	this.vetor[this.qtd] = meuCloneDeX(x);

	else
		this.vetor[this.qtd] = x;
	this.qtd++;

    //this.vetor[this.qtd] = new X(x); Não podemos usar "new X"
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método getUmItem tem, como objetivo, retirar um dado da primeira posição da pilha.
* O método lança excessões para caso a pilha esteja vazia.
*
* @throws Exception "Nada a recuperar"
*/

    public X getUmItem () throws Exception
    {
        if (this.isVazia())
            throw new Exception ("Nada a recuperar");

		if(this.vetor[this.qtd-1] instanceof Cloneable)
			//return (X) this.vetor[this.qtd-1].clone(); //Vai dar pau, tem que contornar
			return meuCloneDeX((X)this.vetor[this.qtd-1]);
		else
			return (X) this.vetor[this.qtd-1];

       //return  new X(this.vetor[this.qtd-1]); Não podemos usar "new X"
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método jogueForaUmItem é o método que elimina um dado da pilha.
* O método lança excessões para caso a pilha esteja vazia.
*
* @throws Exception "Pilha vazia"
*/

    public void jogueForaUmItem () throws Exception
    {
	if (this.isVazia())
	{    Exception problema;
	     problema = new Exception ("Pilha vazia");
	     throw problema;
	}

        this.qtd--;
        this.vetor[this.qtd]=null;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método isCheia é um boolean que nos mostra quando a pilha está cheia ou não,
* ou seja, quando seu número de dados é semelhante ao da capacidade.
*/


    public boolean isCheia ()
    {
        return this.qtd==this.vetor.length;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método isVazia é um outro boolean. Ele nos mostra quando a pilha está vazia, isto é,
* quando sua quantidade de dados é 0.
*/

    public boolean isVazia ()
    {
        return this.qtd==0;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método toString nos traz quantos elementos temos na pilha e qual é o atual último deles.
* Ele é um dos métodos obrigatórios em Java.
*/

    public String toString ()
    {
		if (this.qtd==0)
		    return "Vazia";

		return this.qtd+" elementos, sendo o ultimo "+this.vetor[this.qtd-1];
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método equals é o responsável por comparar um elemento de pilha a um objeto de parâmetro
* Ele é um dos métodos obrigatórios em Java.
*
* @param Object obj
*/

	public boolean equals (Object obj)
	{
		if (this==obj)
		    return true;

		if (obj==null)
		    return false;

		if (this.getClass()!=obj.getClass())
		    return false;

		Pilha<X> pil = (Pilha<X>)obj;

		if (this.qtd!=pil.qtd)
		    return false;

		for (int i=0; i<this.qtd; i++)
		    if (!this.vetor[i].equals(pil.vetor[i]))
		        return false;

		return true;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método hashCode demonstra o código hash de cada elemento da pilha por meio de multiplicações e somas com outros números.
* Ele é um dos métodos obrigatórios em Java.
*/

	public int hashCode()
	{
		int ret = 666; //só não pode ser 0

		//Qualquer número primo

		ret = ret * 2  + new Integer(this.qtd).hashCode();

		for(int i = 0 ; i < this.qtd ;i++)
		//if (this.vetor[i]!=null)
			ret = ret * 13 + this.vetor[i].hashCode();



		return ret;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método clone é o método duplicador de objetos legítimos de Java.
* Ele é um dos métodos obrigatórios em Java.
*/

	public Object clone()
	{
		Pilha<X> ret = null;
		try
		{
			ret = new Pilha<X>(this);
		}
		catch(Exception erro)//Catch vazio em geral é porquice das grandes, mas nesse caso nos sabemos que não vai ter erro, pois "this" nunca pode ser nulo
		{}

		return ret;
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O construtor de cópia é como um clone, mas para aquilo que pertence à própria pilha.
* O propósito é evitar corrompimento de dados.
*
* @param Pilha modelo
* @throws Exception "Modelo ausente"
*/

	public Pilha (Pilha modelo) throws Exception
	{
		if(modelo==null)
			throw new Exception("Modelo ausente");

		this.qtd = modelo.qtd;

		this.vetor = new Object [modelo.vetor.length];

		for(int i = 0; i <= modelo.qtd; i++)
			this.vetor[i] = modelo.vetor[i]; //Devemos usar esse pois ele é mais economico e a classe Pilha não consegue alterar o que ela guarda
			//this.vetor[i] = modelo.vetor[i].clone();      //teriamos que clonar tambem o que guardamos
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O método cloneDeX define um clone do tipo X escolhido pelo usuário.
* Seu parâmetro para funcionalidade é o x.
*
* @param X x
*/

	protected X meuCloneDeX (X x)
	{
		X ret = null;
		//agora o que quero fazer de um Jeito Demoniaco é
		//return x.clone();
	try
	{
		Class<?> classe = x.getClass();
		Class<?>[] tiposDosParametrosFormais = null; //Pois "clone" tem 0 parâmetros
		Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
		Object[] parametrosReais = null; //Pois "clone" tem 0 parâmetros
		ret = (X)metodo.invoke(x,parametrosReais);
	}
	catch (NoSuchMethodException erro)
	{}
	catch (IllegalAccessException erro)
	{}
	catch (InvocationTargetException erro)
	{}

	return ret;
	}

}

