# PuyoPuyo_by_Java
PuyoPuyo_by_C를 객체지향언어(Java Swing)로 수정한 프로그램 입니다.

## 객체구조도
![PuyoPuyoOOP](https://github.com/junni01kim/PuyoPuyo_by_Java/assets/127941871/89d9ad24-91f4-40a2-91f0-5fc9012ad16c)

## 실제 실행화면
![image](https://github.com/junni01kim/PuyoPuyo_by_Java/assets/127941871/573698a0-ca61-44d8-ba59-3860dbeaa528)


## 파일 설명
해당 프로그램은 다음과 같은 구조로 되어 있습니다.
1. 게임 코드 제작에 필요한 객체를 만드는 클래스
2. 객체 클래스를 운용하는 함수로 구성된 Thread 클래스
3. 운용 클래스를 이용해 애플릿을 만드는 Swing 도구 클래스

## 객체화 된 프로그램


### 이후
버그 수정  
    - 각 클래스에 자기자신의 승패를 구분하는 코드들이 별도로 추가되었다.
    - 오류: splashPuyo()와 함께 puyoMap[indexX]의 라인 한줄이 같이 사라지는 현상 발생
gameGround에 있던 puyoIcon이 Puyo객체에 포함되는게 올바르다 생각하여 Puyo 클래스로 이동 후 static으로 객체없이 호출 가능하게 함 or static으로 구현하는게 유리한 함수 함께 수정
repaint()를 통해 다 완전하게 그려지지 않거나 그래픽이 반만 그려진 뿌요들을 다시 그려지게 함
    
### \- 느낀점 -
1. 함수들은 Thread보다는 각 Component에 작성하는 것에 코딩하고 관리하기에 편하다.  
2. Thread를 이용할 때는 거의 필수적으로 synchronized를 사용해야 한다.
