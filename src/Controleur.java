import javax.sound.sampled.Control;

public class Controleur 
{
	public Controleur() throws Exception
	{
		FrameBasique frame = new FrameBasique(this);
	}
	public static void main(String[] args) throws Exception
	{
		new Controleur();
	}
}
