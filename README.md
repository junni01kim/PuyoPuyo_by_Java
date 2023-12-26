# PuyoPuyo_by_Java
PuyoPuyo_by_C를 객체지향언어(Java Swing)로 수정한 프로그램 입니다.

## 객체구조도
![PuyoPuyoOOP](https://github.com/junni01kim/PuyoPuyo_by_Java/assets/127941871/89d9ad24-91f4-40a2-91f0-5fc9012ad16c)

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
