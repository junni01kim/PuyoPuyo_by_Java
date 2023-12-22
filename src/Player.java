
public class Player {
	// 뿌요가 가로 6 세로 12개 들어갈 수 있는 배열(맵) 생성 
	Puyo puyoMap[][] = new Puyo[6][12];
	Puyo controlPuyo[] = new Puyo[2];
	
	// 방해뿌요 전달 및 전송
	int nextTimeGetObsturctPuyo=0;
	int nextTimePassObstructPuyo=0;
	
	// 뿌요 객체를 아래로 떨어트리는 함수이다. 
	void dropPuyo() {
		
	}
}
