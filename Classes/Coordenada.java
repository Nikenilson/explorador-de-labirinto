
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

	public Coordenada(int xis, int ipsilon) throws Exception
	{
		if(xis < 0 || ipsilon < 0)
			throw new Exception("Coordenadas invalidas");

		this.x = xis;
		this.y = ipsilon;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

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

