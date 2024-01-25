package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.DiaryDTO;
import dto.Mood;

public class DiaryDAO { // ������ db���� �� ������ ���� Ŭ����

	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String username = "system";
	String pass = "1111";
	Connection conn = null;
	// �̱��� ����ϱ�
	public static DiaryDAO self = null;

	public static DiaryDAO getInstance() {
		if (self == null) {
			self = new DiaryDAO();
		}
		return self;
	}

	public DiaryDAO() {
		try { // �����߿� �߻��ϴ� ���ܸ� ó���Ͽ� ���α׷� ������ ���� �ʰ� �Ѵ�.
			Class.forName("oracle.jdbc.driver.OracleDriver"); // - ��
			System.out.println("����̹� �ε� ����"); // �� �ڵ尡 �������̸� ����ȴ�.
		} catch (Exception e) {
			System.out.println("����̹� �ε� ����"); // �� �ڵ忡�� ����̹��� �ε���ϸ� ����
		}
	}

	public boolean getConn() {
		try {
			conn = DriverManager.getConnection(url, username, pass);
			System.out.println("���ؼ� ����");
			return true;
		} catch (Exception e) {
			System.out.println("���ؼ� ����");
		}
		return false;
	}

	public void insert(DiaryDTO d) { // ������ ����ϱ�
		if (getConn()) {
			try {
				PreparedStatement psmt = null;
				String sql = "insert into diary values(?,?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, d.getDate());
				psmt.setString(2, d.getMood());
				psmt.setString(3, d.getMemo());
				int rs = psmt.executeUpdate();
				System.out.println(rs + "�� �Ϸ�");
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
				System.out.println("����1");
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

	public ArrayList<Mood> moodCom() { // mood���� ������ ����
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
