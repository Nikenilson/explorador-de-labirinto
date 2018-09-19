public class Coordenada
{
	private int x;
	private int y;

	public Coordenada(int X, int Y) throws Exception
	{
		if(X < 0 || Y < 0)
			throw new Exception("Coordenadas invalidas");

		this.x = X;
		this.y = Y;
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

	public String toString() //Formata o conteúdo em String
	{
		String oX = (x<10?"0"+x: x + "");
		String oY = (y<10?"0"+y: y + "");
		return "("+ oX +"," + this.y + ")";
	}

	public int hashCode()
	{
		int ret = 666;

		ret = ret * 2 + new Integer(this.x).hashCode();
		ret = ret * 2 + new Integer(this.y).hashCode();

		return ret;
	}
}

