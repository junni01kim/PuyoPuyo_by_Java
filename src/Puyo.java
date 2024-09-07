
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Puyo extends JLabel{
	/** 뿌요는 0.초록 1.빨강 2.노랑 3.파랑 4.보라 5.방해이다. */
	private int type;


	/**
	 * 뿌요의 맵 인덱스를 좌표로 바꿔준다. 
	 * 
	 * @param indexX 인덱스 x
	 * @return ImageIcon이 배치될 좌표 값
	 */
	static public int indexXToPixel(int indexX) { return 20+indexX*60; }
	/**
	 * 뿌요의 맵 인덱스를 좌표로 바꿔준다.
	 *
	 * @param indexY 인덱스 y
	 * @return ImageIcon이 배치될 좌표 값
	 */
	static public int indexYToPixel(int indexY) { return 10+indexY*60; }

	/**
	 * 뿌요의 좌표를 맵 인덱스로 바꿔준다.
	 *
	 * @return ImageIcon이 배치될 맵 인덱스 값
	 */
	public int PixelXToindex() {return (getX()-20)/60;}

	/**
	 * 뿌요의 좌표를 맵 인덱스로 바꿔준다.
	 *
	 * @return ImageIcon이 배치될 맵 인덱스 값
	 */
	public int PixelYToindex() {return (getY()-10)/60;}
	
	public void setType(int type) {this.type = type;}
	public int getType() {return type;}
	public static ImageIcon[] getPuyoIcon() {return GameImageIcon.puyoIcon;}

	/**
	 * 위치를 지정하지 않는 생성자
	 *
	 * @param type 생성할 뿌요 색
	 */
	public Puyo(int type){
		super();
		
		this.type = type;
		
		setSize(60,60);
		setIcon(GameImageIcon.puyoIcon[type]);
	}

	/**
	 * 위치를 생성하는 지정자
	 *
	 * @param type 생성할 뿌요 색
	 * @param indexX 배치할 뿌요의 위치
	 * @param indexY 배치할 뿌요의 위치
	 */
	public Puyo(int type, int indexX, int indexY){
		super();
		
		this.type = type;
		setLocation(indexXToPixel(indexX), indexYToPixel(indexY));	
		setSize(60,60);
		setIcon(GameImageIcon.puyoIcon[type]);
	}
}
