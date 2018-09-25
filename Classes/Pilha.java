import java.lang.reflect.*;


/**
* A classe Pilha<X> se define por ser uma classe gen�rica de Pilha (assim como ocorre para a classe gen�rica de Fila), ou seja, uma classe de armazenamento
* em pilha ("Last in, First Out") que pode receber qualquer valor, pois est� adaptada para isso. Ela conta com m�todos getters para que trabalhemos sem deturpar
* as vari�veis originais, m�todos de entrada e sa�da de dados da Fila e m�todos boolean para checagem de capacidade (sem contar os m�todos obrigat�rios).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/



public class Pilha<X> implements Cloneable
{
    protected Object[] vetor;
    protected int qtd = 0;


/**
 * Este � o construtor da classe Pilha. Ele recebe, como par�metro, a vari�vel inteira que define
 * a capacidade e, a partir disso, cria uma pilha de tal capacidade. Se a capacidade for inferior
 * a 0, o que n�o � permitido a uma pilha, lan�aremos uma excess�o.
 *
 * @param int capacidade
 * @throws Exception "Capacidade inv�lida"
 */

    public Pilha (int capacidade) throws Exception
    {

        if (capacidade<0)
            throw new Exception ("Capacidade invalida");

        this.vetor = new Object [capacidade];
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo guarde recebe, como par�metro, um item do tipo escolhido pelo usu�rio da classe para a pilha.
* O item entrar� na �ltima posi��o. O m�todo lan�a excess�es para caso o par�metro seja null e para caso
* a pilha j� esteja cheia.
*
* @param X x
* @throws Exception "Informa��o ausente", "Pilha cheia"
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

    //this.vetor[this.qtd] = new X(x); N�o podemos usar "new X"
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo getUmItem tem, como objetivo, retirar um dado da primeira posi��o da pilha.
* O m�todo lan�a excess�es para caso a pilha esteja vazia.
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

       //return  new X(this.vetor[this.qtd-1]); N�o podemos usar "new X"
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo jogueForaUmItem � o m�todo que elimina um dado da pilha.
* O m�todo lan�a excess�es para caso a pilha esteja vazia.
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
* O m�todo isCheia � um boolean que nos mostra quando a pilha est� cheia ou n�o,
* ou seja, quando seu n�mero de dados � semelhante ao da capacidade.
*/


    public boolean isCheia ()
    {
        return this.qtd==this.vetor.length;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo isVazia � um outro boolean. Ele nos mostra quando a pilha est� vazia, isto �,
* quando sua quantidade de dados � 0.
*/

    public boolean isVazia ()
    {
        return this.qtd==0;
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo toString nos traz quantos elementos temos na pilha e qual � o atual �ltimo deles.
* Ele � um dos m�todos obrigat�rios em Java.
*/

    public String toString ()
    {
		if (this.qtd==0)
		    return "Vazia";

		return this.qtd+" elementos, sendo o ultimo "+this.vetor[this.qtd-1];
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo equals � o respons�vel por comparar um elemento de pilha a um objeto de par�metro
* Ele � um dos m�todos obrigat�rios em Java.
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
* O m�todo hashCode demonstra o c�digo hash de cada elemento da pilha por meio de multiplica��es e somas com outros n�meros.
* Ele � um dos m�todos obrigat�rios em Java.
*/

	public int hashCode()
	{
		int ret = 666; //s� n�o pode ser 0

		//Qualquer n�mero primo

		ret = ret * 2  + new Integer(this.qtd).hashCode();

		for(int i = 0 ; i < this.qtd ;i++)
		//if (this.vetor[i]!=null)
			ret = ret * 13 + this.vetor[i].hashCode();



		return ret;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo clone � o m�todo duplicador de objetos leg�timos de Java.
* Ele � um dos m�todos obrigat�rios em Java.
*/

	public Object clone()
	{
		Pilha<X> ret = null;
		try
		{
			ret = new Pilha<X>(this);
		}
		catch(Exception erro)//Catch vazio em geral � porquice das grandes, mas nesse caso nos sabemos que n�o vai ter erro, pois "this" nunca pode ser nulo
		{}

		return ret;
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O construtor de c�pia � como um clone, mas para aquilo que pertence � pr�pria pilha.
* O prop�sito � evitar corrompimento de dados.
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
			this.vetor[i] = modelo.vetor[i]; //Devemos usar esse pois ele � mais economico e a classe Pilha n�o consegue alterar o que ela guarda
			//this.vetor[i] = modelo.vetor[i].clone();      //teriamos que clonar tambem o que guardamos
	}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo cloneDeX define um clone do tipo X escolhido pelo usu�rio.
* Seu par�metro para funcionalidade � o x.
*
* @param X x
*/

	protected X meuCloneDeX (X x)
	{
		X ret = null;
		//agora o que quero fazer de um Jeito Demoniaco �
		//return x.clone();
	try
	{
		Class<?> classe = x.getClass();
		Class<?>[] tiposDosParametrosFormais = null; //Pois "clone" tem 0 par�metros
		Method metodo = classe.getMethod("clone", tiposDosParametrosFormais);
		Object[] parametrosReais = null; //Pois "clone" tem 0 par�metros
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

