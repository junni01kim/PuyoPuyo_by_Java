import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Puyo extends JLabel{
	private GameGround gameGround;
	
	//뿌요는 0.초록 1.빨강 2.노랑 3.파랑 4.보라 5.방해이다.
	private int type;
	
	
	//뿌요의 좌표
	private int indexXToPixel(int indexX) { return 20+indexX*60; }
	private int indexYToPixel(int indexY) { return 10+indexY*60; }
	private int PixelXToindex() {return (getX()-20)/60;}
	private int PixelYToindex() {return (getY()-10)/60;}
	
	public void setType(int type) {this.type = type;}
	public int getType() {return type;}
	
	public Puyo(GameGround gameGround, int type, int indexX, int indexY){
		super();
		System.out.println("뿌요생성자 시작");
		
		this.type = type;
		setLocation(indexXToPixel(indexX), indexYToPixel(indexY));	
		setSize(60,60);
		setIcon(gameGround.getPuyoIcon()[type]);
		
		System.out.println("뿌요생성자 종료");
	}
}
