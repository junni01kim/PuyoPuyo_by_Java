# PuyoPuyo_by_Java 리뉴얼

### 1. 프로젝트의 주요 기능과 목적
#### 프로젝트 목적
  1. 2024 한성대학교 네트워크 프로그래밍 기말 프로젝트 대비 작업
  2. 기존 [v1.0.0](docs/v1.0.0.md)의 코드 가독성 및 규칙 수립
  3. 싱글톤(singleton)과 상태패턴(state)을 적용하고 적용해봄 
    
#### 프로젝트 기능
  1. 고전 오락 PuyoPuyo 게임 제작하기
  2. 멀티 유저 게임을 제작하여, 배틀 기능 구현
  3. 게임 규칙은 Sega의 PuyoPuyo Tetris을 기반으로 제작 PuyoPuyo_by_Java 리뉴얼
  4. **동작 알고리즘은 모두 해당 프로젝트에서 직접 제작**
  5. 이후 `v3.0.0` (네트워크 통신)을 위해 플레이어 동작 모듈화
      
#### 아키텍처
  1. 클래스 다이어그램
      1. 이용상황: 기존에 만들어둔 프로젝트 v1.0.0의 알고리즘과 Ui가 존재하는 상황
      2. 사용이유: 개발에 적합 새로운 흐름도 제작보단, 타 인원(v3.0.0 팀원) 이해를 위한 규칙 작성 및 코드 구조 가독성 향상이 중요하다 판단
![메뉴 구성도](https://github.com/user-attachments/assets/a8fc1aba-3170-4e42-9e72-0c302d35e792)

### 2. 작동 영상
  - 영상 링크

### 3. 설치 방법
  1. 실행파일 링크 ※ `Java Virtual Machine` 설치 필수 ※
  2. git clone 후 __`IntelliJ`__ 에서 프로젝트 실행 

### 4. 문제 해결 방법
  1. 화면 생성(그래픽스): Java Swing을 이용한 그래픽 디자인
  2. Ui 디자인: `PuyoPuyo Tetris`의 이미지 참고, 메뉴 배치는 자체 디자인. ※ 저작권 위배 시 원격 깃 private로 변경 예정  
  3. 알고리즘:
      1. 게임 진행:
          1. 게임 시작 시 각 Round를 담당하는 `GameThread`를 배치, 각 라운드는 Player마다 새로운 `RoundThread`를 가진다.
          2. 게임 이벤트는 `RoundThread`의 메서드에서 관리되며, 상대 puyopuyo.Player 데이터 접근은 `RoundThread`를 통해서 전달된다.
          3. `RoundThread`의 시각적인 부분은 `GroundService`를 통해서만 접근이 가능하다. 상대방 Ui는 `RoundThread`에서 MapService를 통해 접근된다.  
            ※ 자세한 사항은 _의존성 주입_ 에서 설명
      2. 주변 뿌요 탐색: `Flood Fill` 알고리즘
          1. 6*12 사이즈의 이중 배열 화면과 픽셀 영역 채우기에 사용하는 `Flood Fill` 알고리즘의 작동 방식이 유사하다 판단
          2. 최하단까지 내려운 뿌요의 4면에 자신과 동일한 뿌요가 몇 개 있는지 판단한다. (알고리즘 1회 사용)
          3. 동일한 뿌요가 4개 이상 존재한다면, 뿌요 삭제 함수를 작동한다. (알고리즘 추가 1회 사용) <- __수정 예정__
      3. 졈수 계산:
          1. 다음 사이트를 참고하여 제작하였다. [득점 계산](https://puyopuyo.fandom.com/ko/wiki/%EB%93%9D%EC%A0%90_%EA%B3%84%EC%82%B0)
          2. player `dropPuyos()`가 작동된 횟수를 Combo로 판단한다.

### 5. 지원방법
  - 자바 Swing 이용
  - 이미지...

### 6. 라이선스, API 정보
  - 해당 없음

### 7. 심화 자료와 문서 링크
#### 버전 별 ReadMe.md
  - [\[PuyoPuyo_by_Java\] v1.0.0](docs/v1.0.0.md) `Eclips`에서 작동하는 버전 (프로토타입)
  - [PuyoPuyo_by_Java] v2.0.0 `IntelliJ`에서 작동하는 버전 (2인용) **현재 버전**
  - [PuyoPuyo_by_Java] v3.0.0 `IntelliJ`에서 작동하는 버전 (네트워크 통신 적용)
#### 참고 자료
  - [명품 JAVA Programming (개정4판)](https://www.booksr.co.kr/product/%EB%AA%85%ED%92%88-java-programming/)
  - [자바 Swing 공식 문서](https://docs.oracle.com/javase/tutorial/uiswing/index.html)
  - [득점 계산](https://puyopuyo.fandom.com/ko/wiki/%EB%93%9D%EC%A0%90_%EA%B3%84%EC%82%B0)

### 8. 사전 요구사항
  - `Java Virtual Machine`
