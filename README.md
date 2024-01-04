# PuyoPuyo_by_Java
PuyoPuyo_by_C를 객체지향언어(Java Swing)로 수정한 프로그램 입니다.

## 객체구조도
![PuyoPuyoOOP](https://github.com/junni01kim/PuyoPuyo_by_Java/assets/127941871/89d9ad24-91f4-40a2-91f0-5fc9012ad16c)

## 실제 실행화면
![image](https://github.com/junni01kim/PuyoPuyo_by_Java/assets/127941871/573698a0-ca61-44d8-ba59-3860dbeaa528)


## 파일 설명
해당 프로그램은 다음과 같은 구조로 되어 있습니다.
1. 게임 코드 제작에 필요한 객체를 만드는 클래스
2. 객체 클래스를 운용하는 함수로 구성된 클래스
3. 운용 클래스를 이용해 애플릿을 만드는 Swing 도구 클래스

## 프로그램 설명(제작중)
해당 프로그램에 이용된 코드들은 다음과 같습니다.
  - 3개의 클래스로 구성되어 있습니다.
    - 1개의 프레임 3개의 패널이 존재합니다.

#### \- 1일차 -  
뿌요뿌요 플레이 화면 화면 비율구성  
뿌요뿌요가 플레이될 공간의 맵 크기를 할당하였다.

#### \- 2일차 -
RoundThread, PlayerThread 구현 (틀만)
makePuyoLogic 구현: 한 라운드간 순회할 뿌요 묶음 순서를 정한다.

#### \- 3일차 -
2일차 내용 스레드 정상 작동확인
makePuyoLogic 정상작동 확인(swing)
nextPuyo 구현: 다음 뿌요를 지정하고 드랍 위치를 초기화
dropPuyo 구현: 바닥에 닿으면 nextPuyo 호출

#### \- 4일차 -
3일차 구현 요소 스윙 graphics를 이용하여 시각화
Puyo 수정: JLabel을 상속받으며, 뿌요 타입에 따라 생성자가 그래픽과 위치를 지정하여 생성해준다.
dropPuyo 수정: 
  1) 다른 뿌요가 설치되어도, nextPuyo를 호출한다.
  2) 바닥에 닿으면, 뿌요가 그 자리에 남아있는다.

#### \- 5일차 -
controlPuyo 구현: 하좌우 방향키 기능 구현(예외처리X)

#### \- 6일차 -
controlPuyo 완성: 5일차 최적화 및 상 방향키 기능 구현  
dropAnotherPuyo 구현: 한 뿌요만 바닥에 닿았을 때, 다른 뿌요의 위치를 지정한다.

#### \- 7일차 -
initializeCheckNumberOfSamePuyoVariable 구현: CheckNumberOfSamePuyo와 deletePuyos에 필요한 변수들을 초기화 하는 함수
checkNumberOfSamePuyo 구현: 놓여진 뿌요 주변에 같은 색상 뿌요가 몇개있는지 계산하는 함수
deletePuyos 구현: checkNumberOfSamePuyo에서 구한 뿌요들을 한번에 제거하는 함수
dropPuyos 구현: 뿌요가 삭제된 후 남은 블록을 밑으로 내리는 함수, (다시 drop되면 한번 더 체크하는 조건 미구현)

#### \- 8일차 -
dropPuyos 완성: 7일차의 미구현 부분 완성
scanNumberOfSamePuyo 구현: deletePuyo 이후 뿌요의 연결된 양을 체크하기 위한 함수

#### \- 9일차 -
changeNextPuyo 구현: 다음 뿌요를 scorePanel에 출력한다.

### \- 10일차 -
printScore 구현: 라운드 당 점수 구현, scanNumberOfSamePuyo()에서 점수를 연산한다.
    점수 계산 기준: (연쇄 마다) 없어진 뿌요 수 x (연쇄 보너스 + 연결 보너스 + 색수 보너스) x 10
연쇄 뿌요 시각표현 구현: 뿌요가 연쇄로 부서지는 과정을 보여준다.

### \- 11일차 -
changeRound 구현: 3판 2선승제로 구성. 매 게임마다 platerThread가 초기화된다.

### \- 12일차 -
garbagePuyo 구현(미완): 일정 점수를 넘기면 상대방에게 garBagePuyo가 전달된다.
    - 오류가 존재하여 garbagePuyoIcon이 바로 출력되지 않는다.
    - 추가필요: garbagePuyo 폭발 함수 및, 전달될 방해뿌요 양 출력

### \- 13일차 -
splashSarbagePuyo 구현: 뿌요가 폭발하면 주변 방해뿌요도 함께 폭발

### \- 14일차(완성) -
garbagePuyo 완성: 방해뿌요 전달 개수 출력(그래픽 구현 X)

### 이후
버그 수정

### \- 느낀점 -
1. 함수들은 Thread보다는 각 Component에 작성하는 것에 코딩하고 관리하기에 편하다.  
    - 각 클래스에 자기자신의 승패를 구분하는 코드들이 별도로 추가되었다.
    - 오류: splashPuyo()와 함께 puyoMap[indexX]의 라인 한줄이 같이 사라지는 현상 발생
