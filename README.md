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
* 상속 및 인터페이스 구현
  JFrame을 상속 받아서 사용했으며 listener 등은 여러개가 필요하여서 인터페이스로 구현받아서 재정의하여 사용하였다.
```
import javax.swing.JFrame;

public class DiaryADM extends JFrame implements ActionListener, MouseListener
```
----
* 객체간 주소 값 복사 구현
클래스간의 값 전달시 주소값 복사로 구현하였다. 예를 들어 오늘의 감정을 고른 후 그 이미지와 함께 페이지로 넘어가 텍스트를 작성해야한다. 
```
public class SelectDayGui extends JFrame implements ActionListener, MouseListener {
	// 하루 일기 작성 하는 클래스
	Scanner in = new Scanner(System.in);
	DiaryDAO ddao = DiaryDAO.getInstance();
	DiaryDTO ddto = null;
	Today today = new Today();
	String todayDB = String.valueOf(today.getYear()) + "/" + (today.getMonthV()) + "/" + (today.getDate());

	public SelectDayGui(DiaryDTO ddto) {
		this.ddto = ddto; // 감정 고르기에서 선택한 이미지!
		nowMood = new Mood();
		nowMood.setMood(ddto.getMood());
		nowMood.setNum(ddto.getMoodNum());
	}
```
-----
* 싱글톤으로 객체 사용하기
객체를 여러 곳에서 만들게 되면 DAO에서 가져온 db가 계속 중복되거나 데이터가 변경되는 시점을 알 수 가 없다

그래서 DAO 단은 객체를 한번만 만들고 다른 곳에서는 메서드 호출을 통해 구현 받도록 만들었다.

**문제**

아무 이유 없이 싱글 톤이 좋다고 만들 필요는 없다

이번 프로젝트에서 메인 페이지는 내용이 업데이트 되면 변경된 정보를 가진 객체가 생성되어야 했다. 

그런데 계속 같은 주소 값을 갖고 있다면 내용이 바뀌지 않기 때문에 싱글톤으로 하면 안됨 .

```
public class DiaryDAO { // 일정들 db저장 및 가지고 오는 클래스

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String username = "system";
	String pass = "1111";
	Connection conn = null;
	// 싱글톤 사용하기
	public static DiaryDAO self = null;

	public static DiaryDAO getInstance() {
		if (self == null) {
			self = new DiaryDAO();
		}
		return self;
	}
```

