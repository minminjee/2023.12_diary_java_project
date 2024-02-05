감정 일기장 : gui를 활용한 기록 관리형 다이어리
========

#### 사용법
* 오늘의 감정 아이콘을 고른다.
* 오늘을 기록한다. 날짜는 자동 지정되며 중복 장석은 불가하다.
* 월별로 일기에 대한 정보를 확인할 수 있다.
* 기록된 정보를 수정/ 삭제가 가능하다.

### 흐름도
<img src ="https://github.com/minminjee/2023.12_diary_java_project/assets/157664207/d270cfbe-0c15-4c13-a497-155f941c032a">

### 시연 영상
<img src ="https://github.com/minminjee/diary_java_project_23.12/assets/157664207/42527757-e8a1-438e-89ff-344530df7c56">


### 기술 스택
JFrame을 상속 받아서 사용했으며 listener 등은 여러개가 필요하여서 인터페이스로 구현받아서 재정의하여 사용하였다.
```
import javax.swing.JFrame;

public class DiaryADM extends JFrame implements ActionListener, MouseListener
```
