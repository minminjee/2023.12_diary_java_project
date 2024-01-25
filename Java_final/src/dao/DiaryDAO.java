package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.DiaryDTO;
import dto.Mood;

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

	public DiaryDAO() {
		try { // 실행중에 발생하는 예외를 처리하여 프로그램 오류가 나지 않게 한다.
			Class.forName("oracle.jdbc.driver.OracleDriver"); // - 가
			System.out.println("드라이버 로드 성공"); // 가 코드가 정상적이면 실행된다.
		} catch (Exception e) {
			System.out.println("드라이버 로드 실패"); // 가 코드에서 드라이버를 로드못하면 실행
		}
	}

	public boolean getConn() {
		try {
			conn = DriverManager.getConnection(url, username, pass);
			System.out.println("컨넥션 성공");
			return true;
		} catch (Exception e) {
			System.out.println("컨넥션 실패");
		}
		return false;
	}

	public void insert(DiaryDTO d) { // 오늘의 기록하기
		if (getConn()) {
			try {
				PreparedStatement psmt = null;
				String sql = "insert into diary values(?,?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, d.getDate());
				psmt.setString(2, d.getMood());
				psmt.setString(3, d.getMemo());
				int rs = psmt.executeUpdate();
				System.out.println(rs + "건 완료");
			} catch (Exception e) {

			} finally {
				allClose();
			}
		}
	}

	public ArrayList<DiaryDTO> selectAll() {
		ResultSet rs = null;
		DiaryDTO ddto = null;
		ArrayList<DiaryDTO> dlist = new ArrayList<>();
		if (getConn()) {
			try {
				PreparedStatement psmt = null;
				String sql = "select wdate,memo,mood,mood.num" + " from diary inner "
						+ "join mood on diary.mood = mood.kind " + "order by wdate";
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while (rs.next()) {
					ddto = new DiaryDTO();
					ddto.setDate(rs.getString("wdate"));
					ddto.setMemo(rs.getString("memo"));
					ddto.setMood(rs.getString("mood"));
					ddto.setMoodNum(rs.getInt("num"));

					dlist.add(ddto);
				}
			} catch (Exception e) {

			} finally {
				allClose();
			}
		}
		return dlist;
	}

	public void update(DiaryDTO ddto) {
		if (getConn()) {
			try {
				PreparedStatement psmt = null;
				System.out.println("성공1");
				String sql = "update diary set memo=? where wdate=?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, ddto.getMemo());
				psmt.setString(2, ddto.getDate());
				int rs = psmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				allClose();
			}
		}
	}

	public ArrayList<Mood> moodCom() { // mood정보 가지고 오기
		ResultSet rs = null;
		Mood mood = null;
		ArrayList<Mood> mlist = new ArrayList<>();
		if (getConn()) {
			try {
				PreparedStatement psmt = null;
				String sql = "select * from mood";
				psmt = conn.prepareStatement(sql);
				rs = psmt.executeQuery();
				while (rs.next()) {
					mood = new Mood();
					mood.setMood(rs.getString("kind"));
					mood.setNum(rs.getInt("num"));
					mlist.add(mood);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				allClose();
			}
		}
		return mlist;
	}

	public void allClose() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
