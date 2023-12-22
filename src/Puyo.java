
public class Puyo {
	//»Ñ¿ä´Â 0.ÃÊ·Ï 1.»¡°­ 2.³ë¶û 3.ÆÄ¶û 4.º¸¶ó 5.¹æÇØÀÌ´Ù.
	private int type;
	//»Ñ¿äÀÇ ÁÂÇ¥
	private int x;
	private int y;
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public int getX() {return x;}
	public int getY() {return y;}
	
	public Puyo(int type){
		this.type = type;
	}
}
