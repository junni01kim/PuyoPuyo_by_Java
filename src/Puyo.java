
import javax.swing.JLabel;

public class Puyo extends JLabel{
	private GameGround gameGround;
	
	//뿌요는 0.초록 1.빨강 2.노랑 3.파랑 4.보라 5.방해이다.
	private int type;
	
	
	//뿌요의 좌표
	public int indexXToPixel(int indexX) { return 20+indexX*60; }
	public int indexYToPixel(int indexY) { return 10+indexY*60; }
	public int PixelXToindex() {return (getX()-20)/60;}
	public int PixelYToindex() {return (getY()-10)/60;}
	
	public void setType(int type) {this.type = type;}
	public int getType() {return type;}
	
	// 위치를 지정하지 않는 생성자
	public Puyo(GameGround gameGround, int type){
		super();
		System.out.println("뿌요생성자");
		
		this.type = type;
		
		setSize(60,60);
		setIcon(gameGround.getPuyoIcon()[type]);
	}
	
	// 위치를 생성하는 지정자
	public Puyo(GameGround gameGround, int type, int indexX, int indexY){
		super();
		System.out.println("뿌요생성자");
		
		this.type = type;
		setLocation(indexXToPixel(indexX), indexYToPixel(indexY));	
		setSize(60,60);
		setIcon(gameGround.getPuyoIcon()[type]);
	}
}
