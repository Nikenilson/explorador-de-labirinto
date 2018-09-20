/*
Convenções de Nomenclatura

1) Pacotes (biblioteca)
   Tem seus nomes escritos totalmente com
   letras minusculas e as palavras sao
   separadas por . (ponto)

2) Classes e Projetos
   Palavras justapostas com iniciais
   maiúsculas e as demais letras
   minúsculas

3) Atributos, variaveis locais, parametros e métodos
   Segue a regra 2 com a seguinte adaptacao:
   a primeira palavra que forma o nome será
   totalmente minuscula

4) Constantes (final float PI=3.14f;)
   Tem seus nomes escritos totalmente com
   letras maiusculas e as palavras sao
   separadas por _ (underline)

Obs: palavras reservadas pela linguagem e
     tipos escalares sao totalmente minusculos
*/

/*
TIPOS ESCALARES		CLASSES WRAPPER
byte			Byte
short			Short
int			Integer
long			Long

float			Float
double			Double

char			Character

boolean			Boolean

			Ex.:
			public class Programa
			{
			    public static void main (String[] args)
			    {
			        int a, b=7, c=13;

			        a = (b+c)/2;

				System.out.println ("A media de "+b+" e "+c+" vale "+a);
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        Integer a, b=new Integer (7), c=new Integer (13);

			        a = new Integer ((b.intValue()+c.intValue())/2);

				System.out.println ("A media de "+b.intValue()+" e "+c.intValue()+" vale "+a.intValue());
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        Integer a, b=7, c=13;

			        a = (b+c)/2;

				System.out.println ("A media de "+b+" e "+c+" vale "+a);
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        String a, b=new String("COTUCA"), c=new String("UNICAMP");

			        a = b.concat(new String("/").concat(c));

				System.out.println (a.toString());
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        String a, b="COTUCA", c="UNICAMP"; // proprio de wrapper, mas nao é wrapper

			        a = b+"/"+c; // proprio de wrapper, mas nao é wrapper

				System.out.println (a); // proprio de wrapper, mas nao é wrapper
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        int i=13;

			        String s=""+i; // truque para tranformar em String; estou aplicando a um int, mas poderia ser aplicado a qualquer coisa, objetos ou escalares

			        ...
			    }
			}

			public class Programa
			{
			    public static void main (String[] args)
			    {
			        String s="3.14";

				float piEscalar=Float.parseFloat(s); // nao tem truque; só mesmo usando um método
                                Float piObjeto =new Float (s);

			        ...
			    }
			}

*/


/*
...
String str = "COTUCA";
char   chr = str.chatAt(2);
...
//o codigo acima é bem simples; suponham agora
// que queiramos SOFRER... o que fazer? como tornar
// DEMONIACO o codigo acima?
...

String str = "COTUCA";

Class<?> classe = str.getClass();
Integer parametroReal = 2; //2 porque quero usar o "2" como parâmetro do charAt
Class<?>[] tiposDosParametrosFormais = new Class<?>[1]; //1 Pois "charAt" tem 1 parâmetro
tiposDosParametrosFormais[0] = parametroReal.getClass();
Method metodo = classe.getMethod("charAt", tiposDosParametrosFormais);
Object[] parametrosReais = new Object[1] // 1 pq charAt tem 1 parâmetro
parametrosReais[0] = parametroReal;
char chr = ((Character)metodo.invoke(str,parametrosReais)).charValue; //Retorna um Object

...
Day portasAbertas = new Day(9, 22, 2018);

Dia 22/09/2018

...

*/
import java.lang.reflect.*;
public class Pilha<X> implements Cloneable
{
    private Object[] vetor;
    private int qtd = 0;


    //versao preventiva
    public Pilha (int capacidade) throws Exception
    {

        if (capacidade<0)
            throw new Exception ("Capacidade invalida");

        this.vetor = new Object [capacidade];
    }

    //versao remediadora
    /*
    public Pilha (int capacidade) throws Exception
    {
        try
        {
            this.vetor = new String [capacidade];
        }
        catch (NegativeArraySizeException erro)
        {
            throw new Exception ("Capacidade invalida");
        }
    }
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

    public boolean isCheia ()
    {
        return this.qtd==this.vetor.length;
    }

    public boolean isVazia ()
    {
        return this.qtd==0;
    }

    public String toString ()
    {
		if (this.qtd==0)
		    return "Vazia";

		return this.qtd+" elementos, sendo o ultimo "+this.vetor[this.qtd-1];
	}

	//compara this e obj
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

	public Object clone()
	{
		Pilha<X> ret = null;
		try
		{
			ret = new Pilha<X>(this);
		}
		catch(Exception erro)//Catch vazio em geral é porquise das grandes, mas nesse caso nos sabemos que não vai ter erro, pois "this" nunca pode ser nulo
		{}

		return ret;
	}

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

	private X meuCloneDeX (X x)
	{
		X ret = null;
		//agora o que quero fazer de um Jeito Demoniaco é
		//retuen x.clone();
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

