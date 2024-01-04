
public class PlayGame extends Thread{
	
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
