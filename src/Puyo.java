
public class Puyo {
	//�ѿ�� 0.�ʷ� 1.���� 2.��� 3.�Ķ� 4.���� 5.�����̴�.
	private int type;
	//�ѿ��� ��ǥ
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
