package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dao.DiaryDAO;
import dto.DiaryDTO;
import dto.Mood;
import dto.Today;
import main.DiaryADM;
import main.Main;

public class TodayGui extends JFrame implements ActionListener, MouseListener {
	// 하루 일기 작성 하는 클래스
	Scanner in = new Scanner(System.in);
	DiaryDAO ddao = DiaryDAO.getInstance();
	DiaryDTO ddto = new DiaryDTO();
	Today today = new Today();
	String todayDB = String.valueOf(today.getYear()) + "/" + (today.getMonthV()) + "/" + (today.getDate());

	Mood nowMood = null;
	JButton saveBtn = null;
	JButton exitBtn = null;;
	JButton date = new JButton(); // 오늘날짜 가지고 와서 넣기..

	JLabel month = new JLabel();
	JPanel monthp = new JPanel();
	JPanel mainUpbox1 = new JPanel();
	JPanel mainUpbox2 = new JPanel();

	JLabel Lbl_img = null; // 이미지
	JLabel Lbl_word = new JLabel(); // 감정이름

	JTextArea text = new JTextArea("오늘을 기록하세요!", 30, 30); // 오늘 내용 적기
	JScrollPane textp = new JScrollPane(text);

	JPanel mainUp = new JPanel();
	JPanel mainCenter = new JPanel();
	JPanel mainDown = new JPanel();

	public TodayGui(Mood m) {
		this.nowMood = m;  //감정 고르기에서 선택한 이미지!
		
		init();
		setDisplay();
		addListener();
		showFrame();
		System.out.println(todayDB);
	}
	
	
	// 기본은 오늘이지만 날짜가 들어오면 해당 날짜 보여주기..
	
	private void init() {
		// 메인창
		setSize(250, 400);
		this.setLocationRelativeTo(null);
		setTitle("Today");
		// up
		// 오늘 날짜 가져오기..
		month.setText(today.getMonthS());
		month.setFont(new Font("돋움", Font.BOLD, 25));
		String a = String.valueOf(today.getDate()) + " / " + (today.getDay());
		// 날짜 버튼에 내용 사이즈 설정
		date.setText(a);
		date.setPreferredSize(new Dimension(130, 20));
		date.setContentAreaFilled(false);
		date.setBorderPainted(false);
		// center
		// 고른 사진 넣기..

		Lbl_img = new JLabel() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon image = new ImageIcon(Main.class.getResource("../image/" + nowMood.getNum() + ".png"));
				g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		Lbl_word.setText("'" + nowMood.getMood() + "'");
		Lbl_word.setFont(new Font("돋움", Font.BOLD, 20));
		Lbl_word.setHorizontalAlignment(JLabel.CENTER);
		text.setFont(new Font("돋움", Font.PLAIN, 15));
		text.setSize(100, 100);
		monthp.setBackground(Color.white);
		mainUp.setBackground(Color.white);
		mainCenter.setBackground(Color.white);
		mainUpbox1.setBackground(Color.white);
		mainUpbox2.setBackground(Color.white);

		exitBtn = new JButton() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon plus = new ImageIcon(Main.class.getResource("../image/" + "엑스" + ".png"));
				g.drawImage(plus.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		exitBtn.setPreferredSize(new Dimension(30, 30));

		saveBtn = new JButton() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon plus = new ImageIcon(Main.class.getResource("../image/" + "저장" + ".png"));
				g.drawImage(plus.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		saveBtn.setPreferredSize(new Dimension(30, 30));
	}

	private void setDisplay() {
		setLayout(new BorderLayout());
		mainUp.setLayout(new GridLayout(2, 1)); // 행과 열
		mainUpbox1.setLayout(new BorderLayout());
		mainUpbox2.setLayout(new BorderLayout());
		mainCenter.setLayout(new GridLayout(3, 1));

		// up
		mainUpbox1.add(exitBtn, "West");
		monthp.add(month);
		mainUpbox1.add(monthp, "Center");
		mainUpbox1.add(saveBtn, "East");

		mainUpbox2.add(date, "West");

		// UP
		mainUp.add(mainUpbox1);
		mainUp.add(mainUpbox2);

		// center
		mainCenter.add(Lbl_img);
		mainCenter.add(Lbl_word);
		mainCenter.add(textp);

		// thisJFrams
		add(mainUp, "North");
		add(mainCenter, "Center");

	}

	private void addListener() {
		text.addMouseListener(this);
		exitBtn.addMouseListener(this);
		saveBtn.addMouseListener(this);
	}

	private void showFrame() {

		setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource().equals(text)) {
			text.setText(null);

		} else if (arg0.getSource().equals(exitBtn)) {
			// 메인 화면가기...
			new DiaryADM();

		} else if (arg0.getSource().equals(saveBtn)) {
			// db에게 보내고 종료
			if ((text.getText().equals("")) || (text.getText().equals("오늘을 기록하세요!"))) {
				String result = null;
				result = JOptionPane.showInputDialog(this, "오늘의 기분을 한줄이라도!");
				if (result != null) {
					ddto.setMemo(result);
					ddto.setDate(todayDB);
					ddto.setMood(nowMood.getMood());
					ddao.insert(ddto);
					System.out.println("성공1");
				}
				new DiaryADM();
			} else {
				ddto.setMemo(text.getText());
				ddto.setDate(todayDB);
				ddto.setMood(nowMood.getMood());
				ddao.insert(ddto);
				new DiaryADM();
			}
		
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
