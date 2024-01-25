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
	// �Ϸ� �ϱ� �ۼ� �ϴ� Ŭ����
	Scanner in = new Scanner(System.in);
	DiaryDAO ddao = DiaryDAO.getInstance();
	DiaryDTO ddto = null;
	Today today = new Today();
	String todayDB = String.valueOf(today.getYear()) + "/" + (today.getMonthV()) + "/" + (today.getDate());

	Mood nowMood = null;
	JButton saveBtn = null;
	JButton exitBtn = null;;
	JButton date = new JButton(); // ���ó�¥ ������ �ͼ� �ֱ�..

	JLabel month = new JLabel();
	JPanel monthp = new JPanel();
	JPanel mainUpbox1 = new JPanel();
	JPanel mainUpbox2 = new JPanel();

	JLabel Lbl_img = null; // �̹���
	JLabel Lbl_word = new JLabel(); // �����̸�
	boolean chk = false;

	// ���� ���̿��ٰ�.. Ŭ���� �ؽ�Ʈ �ʵ�� ����..

	JTextField text = new JTextField(); // ���� ���� ����
	JScrollPane textp = new JScrollPane(text);

	JPanel mainUp = new JPanel();
	JPanel mainCenter = new JPanel();
	JPanel mainDown = new JPanel();

	public SelectDayGui(DiaryDTO ddto) {
		this.ddto = ddto; // ���� ���⿡�� ������ �̹���!
		nowMood = new Mood();
		nowMood.setMood(ddto.getMood());
		nowMood.setNum(ddto.getMoodNum());
		System.out.println(ddto.getDate());
		// ��¥ �����ؼ� ���� �ޱ�..
		today = today.selday(ddto.getDate());
		text.setText(ddto.getMemo());
		text.setEditable(false);

		init();
		setDisplay();
		addListener();
		showFrame();
		System.out.println(todayDB);
	}

	// �⺻�� ���������� ��¥�� ������ �ش� ��¥ �����ֱ�..

	private void init() {
		// ����â
		setSize(250, 400);
		this.setLocationRelativeTo(null);
		setTitle("diary");
		// up
		// ���� ��¥ ��������..
		month.setText(today.getMonthS());
		month.setFont(new Font("����", Font.BOLD, 25));
		// ��¥ ��ư�� ���� ������ ����
		String a = String.valueOf(today.getDate()) + " / " + (today.getDay());
		date.setText(a);
		date.setPreferredSize(new Dimension(130, 20));
		date.setContentAreaFilled(false);
		date.setBorderPainted(false);
		// center
		// �� ���� �ֱ�..
		// text�� db�ֱ�..

		Lbl_img = new JLabel() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon image = new ImageIcon(Main.class.getResource("../image/" + nowMood.getNum() + ".png"));
				g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		Lbl_word.setText("'" + nowMood.getMood() + "'");
		Lbl_word.setFont(new Font("����", Font.BOLD, 20));
		Lbl_word.setHorizontalAlignment(JLabel.CENTER);

		text.setFont(new Font("����", Font.PLAIN, 15));
		text.setSize(100, 100);
		monthp.setBackground(Color.white);
		mainUp.setBackground(Color.white);
		mainCenter.setBackground(Color.white);
		mainUpbox1.setBackground(Color.white);
		mainUpbox2.setBackground(Color.white);

		exitBtn = new JButton() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon plus = new ImageIcon(Main.class.getResource("../image/" + "����" + ".png"));
				g.drawImage(plus.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		exitBtn.setPreferredSize(new Dimension(30, 30));

		saveBtn = new JButton() {
			public void paint(Graphics g) {
				Dimension d = getSize();
				ImageIcon plus = new ImageIcon(Main.class.getResource("../image/" + "����" + ".png"));
				g.drawImage(plus.getImage(), 0, 0, d.width, d.height, null);
			}
		};
		saveBtn.setPreferredSize(new Dimension(30, 30));
	}

	private void setDisplay() {
		setLayout(new BorderLayout());
		mainUp.setLayout(new GridLayout(2, 1)); // ��� ��
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
