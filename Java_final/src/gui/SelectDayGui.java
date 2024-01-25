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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import dao.DiaryDAO;
import dto.DiaryDTO;
import dto.Mood;
import dto.Today;
import main.DiaryADM;
import main.Main;

public class SelectDayGui extends JFrame implements ActionListener, MouseListener {
	// 하루 일기 작성 하는 클래스
	Scanner in = new Scanner(System.in);
	DiaryDAO ddao = DiaryDAO.getInstance();
	DiaryDTO ddto = null;
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
	boolean chk = false;

	// 원래 라벨이였다가.. 클릭시 텍스트 필드로 변경..

	JTextField text = new JTextField(); // 오늘 내용 적기
	JScrollPane textp = new JScrollPane(text);

	JPanel mainUp = new JPanel();
	JPanel mainCenter = new JPanel();
	JPanel mainDown = new JPanel();

	public SelectDayGui(DiaryDTO ddto) {
		this.ddto = ddto; // 감정 고르기에서 선택한 이미지!
		nowMood = new Mood();
		nowMood.setMood(ddto.getMood());
		nowMood.setNum(ddto.getMoodNum());
		System.out.println(ddto.getDate());
		// 날짜 수정해서 리턴 받기..
		today = today.selday(ddto.getDate());
		text.setText(ddto.getMemo());
		text.setEditable(false);

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
		setTitle("diary");
		// up
		// 오늘 날짜 가져오기..
		month.setText(today.getMonthS());
		month.setFont(new Font("돋움", Font.BOLD, 25));
		// 날짜 버튼에 내용 사이즈 설정
		String a = String.valueOf(today.getDate()) + " / " + (today.getDay());
		date.setText(a);
		date.setPreferredSize(new Dimension(130, 20));
		date.setContentAreaFilled(false);
		date.setBorderPainted(false);
		// center
		// 고른 사진 넣기..
		// text에 db넣기..

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
		if (exitBtn == arg0.getSource()) {
			new DiaryADM();
			this.setVisible(false);

		} else if (saveBtn == arg0.getSource() && !chk) {
			text.setEditable(true);
			chk = true;

		} else if (saveBtn == arg0.getSource() && chk) {
			ddto.setMemo(text.getText());
			ddao.update(ddto);
			this.setVisible(false);
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
