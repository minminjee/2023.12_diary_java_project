package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dao.DiaryDAO;
import dto.DiaryDTO;
import dto.Mood;
import dto.Today;
import gui.MoodGui;
import gui.SelectDayGui;

public class DiaryADM extends JFrame implements ActionListener, MouseListener {
	// 싱글톤으로 만들어놓기
//	public static DiaryADM self = null;
//	public static DiaryADM getInstance() {
//		if (self == null) {
//			self = new DiaryADM();
//		}
//		return self;
//	}
	Scanner in = new Scanner(System.in);
	Today today = new Today();
	DiaryDTO ddto = null;
	DiaryDAO ddao = DiaryDAO.getInstance();
	ArrayList<DiaryDTO> dlist = ddao.selectAll();
	ArrayList<Mood> mlist = ddao.moodCom();
	// gui 부분
	JButton btnPlus = null;
	JButton btntoday = new JButton();

	// today들
	ArrayList<JButton> btndays = new ArrayList<>();
	JPanel mainCenter = new JPanel();
	JButton btnday = null;

	public DiaryADM() { // 야들 호출해서 쓰기!!
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	private void init() { // 구성

		// 오늘의 연도,월 표시

		btntoday.setText(today.getYear() + "." + today.getMonthV() + "월");
		btntoday.setContentAreaFilled(false);
		btntoday.setBorderPainted(false);
		btntoday.setFont(new Font("고딕", Font.BOLD, 15));
		// 밑에 + 버튼
		ImageIcon plus = new ImageIcon(Main.class.getResource("../image/" + "플러스" + ".png"));
		btnPlus = new JButton(plus);
		btnPlus.setContentAreaFilled(false);
		btnPlus.setBorderPainted(false);

		// maincenter 쪽 버튼들..
		// 전체 db가지고 오기..

		for (int i = 0; i < dlist.size(); i++) {
			int imgNum = dlist.get(i).getMoodNum();
			ImageIcon img = new ImageIcon(Main.class.getResource("../image/" + imgNum + ".png"));
			btnday = new JButton(img);
			btnday.setPreferredSize(new Dimension(75, 75));
			mainCenter.add(btnday);
			btndays.add(btnday);
		}

	}

	private void setDisplay() { // 배치
		this.setLayout(new BorderLayout());
		mainCenter.setLayout(new FlowLayout());

		this.add(btntoday, "North");
		this.add(mainCenter, "Center");
		this.add(btnPlus, "South");

	}

	private void addListener() { // 행동 ?? 추가??
		btnPlus.addActionListener(this);
		for(JButton b : btndays) {
			b.addMouseListener(this);
		}
	}

	private void showFrame() { // 프레임 설정
		// 메인창
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) { //
		// bp버튼 눌렀을때

		if (btnPlus.equals(arg0.getSource())) {
			// ddto = new DiaryDTO();
			// 날짜는 자동으로 오늘 날짜반영
			// db가지고 오기..
			MoodGui moodgui = new MoodGui(mlist);
			this.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// 이미지중 하나 선택시 해당 일기로 가기..
		for (int i = 0; i < btndays.size(); i++) {

			if (btndays.get(i) == m.getSource()) {
				DiaryDTO ddto = dlist.get(i);
				new SelectDayGui(ddto);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
