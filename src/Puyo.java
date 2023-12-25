import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Puyo extends JLabel{
	private GameGround gameGround;
	
	//»Ñ¿ä´Â 0.ÃÊ·Ï 1.»¡°­ 2.³ë¶û 3.ÆÄ¶û 4.º¸¶ó 5.¹æÇØÀÌ´Ù.
	private int type;
	
	
	//»Ñ¿äÀÇ ÁÂÇ¥
	public int indexXToPixel(int indexX) { return 20+indexX*60; }
	public int indexYToPixel(int indexY) { return 10+indexY*60; }
	public int PixelXToindex() {return (getX()-20)/60;}
	public int PixelYToindex() {return (getY()-10)/60;}
	
	public void setType(int type) {this.type = type;}
	public int getType() {return type;}
	
	public Puyo(GameGround gameGround, int type, int indexX, int indexY){
		super();
		System.out.println("»Ñ¿ä»ý¼ºÀÚ");
		
		this.type = type;
		setLocation(indexXToPixel(indexX), indexYToPixel(indexY));	
		setSize(60,60);
		setIcon(gameGround.getPuyoIcon()[type]);
	}
}
