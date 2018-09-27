package coordenada;
/**
* A classe Coordenada define uma classe para, especificamente, administrar as posi��es X (eixo horizontal)
* e Y (eixo vertical) atuais de nosso objeto explorador de labirintos. Ela conta com vari�veis inteiras para
* se referir a cada eixo e m�todos getters para trabalhar com os valores sem corromp�-los (sem contar os m�todos obrigat�rios).
*
* @author Samuel Gomes de Lima Dias e Victor Botin Avelino
* @since 2018
*/
public class Coordenada
{
	protected int x;
	protected int y;

	/**
	 * Este � o construtor da classe Coordenada. Ele recebe, como par�metro, duas vari�veis inteiras que definem
	 * as coordenadas X e Y. Se o X ou o Y for menor que 0, que n�o � valido para uma coordenada, lan�aremos uma excess�o.
	 *
	 * @param int xCoordenada
	 * @param int yCoordenada
	 * @throws Exception "Capacidade inv�lida"
 */

	public Coordenada(int xCoordenada, int yCoordenada) throws Exception
	{
		if(xCoordenada < 0 || yCoordenada < 0)
			throw new Exception("Coordenadas invalidas");

		this.x = xCoordenada;
		this.y = yCoordenada;
	}

	/**
	* O m�todo getX tem, como objetivo, resgatar a coordenada X.
*/

	public int getX()
	{
		return this.x;
	}

	/**
	* O m�todo getX tem, como objetivo, resgatar a coordenada Y.
*/

	public int getY()
	{
		return this.y;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	* O m�todo equals � o respons�vel por comparar um elemento de pilha a um objeto de par�metro
	* Ele � um dos m�todos obrigat�rios em Java.
	*
	* @param Object obj
*/

	public boolean equals(Object obj)
	{
		if(this == obj)
		return true;

		if(obj == null)
		return false;

		if(obj.getClass() != this.getClass())
		return false;

		Coordenada c = (Coordenada) obj;

		if(this.x != c.x)
		return false;

		if(this.y != c.y)
		return false;

		return true;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
* O m�todo toString nos traz a coordenada X e Y formatadas.
* Ele � um dos m�todos obrigat�rios em Java.
*/
	public String toString() //Formata o conte�do em String
	{
		String oX = (x<10?"0"+x: x + "");
		String oY = (y<10?"0"+y: y + "");
		return "("+ oX +"," + oY + ")";
	}

	public int hashCode()
	{
		int ret = 666;

		ret = ret * 2 + new Integer(this.x).hashCode();
		ret = ret * 2 + new Integer(this.y).hashCode();

		return ret;
	}

}

