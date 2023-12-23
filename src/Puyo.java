import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Puyo extends JLabel{
	private GameGround gameGround;
	
	//�ѿ�� 0.�ʷ� 1.���� 2.��� 3.�Ķ� 4.���� 5.�����̴�.
	private int type;
	
	
	//�ѿ��� ��ǥ
	private int indexXToPixel(int indexX) { return 20+indexX*60; }
	private int indexYToPixel(int indexY) { return 10+indexY*60; }
	private int PixelXToindex() {return (getX()-20)/60;}
	private int PixelYToindex() {return (getY()-10)/60;}
	
	public void setType(int type) {this.type = type;}
	public int getType() {return type;}
	
	public Puyo(GameGround gameGround, int type, int indexX, int indexY){
		super();
		System.out.println("�ѿ������ ����");
		
		this.type = type;
		setLocation(indexXToPixel(indexX), indexYToPixel(indexY));	
		setSize(60,60);
		setIcon(gameGround.getPuyoIcon()[type]);
		
		System.out.println("�ѿ������ ����");
	}
}
