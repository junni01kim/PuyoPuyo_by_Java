
public class PlayGame extends Thread{
	private GamePanel gamePanel;
	
	public boolean endFlag = false;
	
	public boolean getEndFlag() {return endFlag;}
	
	public void endGame() {
		endFlag = true;
		System.out.println("end Game!");
	}
	
	public void run() {
		while(true) {
			
			if(endFlag == true)
				break;
		}
		System.out.println("PlayGame.run");
	}
}
