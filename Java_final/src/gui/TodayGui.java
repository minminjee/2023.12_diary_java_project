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
	// �Ϸ� �ϱ� �ۼ� �ϴ� Ŭ����
	Scanner in = new Scanner(System.in);
	DiaryDAO ddao = DiaryDAO.getInstance();
	DiaryDTO ddto = new DiaryDTO();
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

	JTextArea text = new JTextArea("������ ����ϼ���!", 30, 30); // ���� ���� ����
	JScrollPane textp = new JScrollPane(text);

	JPanel mainUp = new JPanel();
	JPanel mainCenter = new JPanel();
	JPanel mainDown = new JPanel();

	public TodayGui(Mood m) {
		this.nowMood = m;  //���� ���⿡�� ������ �̹���!
		
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
		setTitle("Today");
		// up
		// ���� ��¥ ��������..
		month.setText(today.getMonthS());
		month.setFont(new Font("����", Font.BOLD, 25));
		String a = String.valueOf(today.getDate()) + " / " + (today.getDay());
		// ��¥ ��ư�� ���� ������ ����
		date.setText(a);
		date.setPreferredSize(new Dimension(130, 20));
		date.setContentAreaFilled(false);
		date.setBorderPainted(false);
		// center
		// �� ���� �ֱ�..

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
		if (arg0.getSource().equals(text)) {
			text.setText(null);

		} else if (arg0.getSource().equals(exitBtn)) {
			// ���� ȭ�鰡��...
			new DiaryADM();

		} else if (arg0.getSource().equals(saveBtn)) {
			// db���� ������ ����
			if ((text.getText().equals("")) || (text.getText().equals("������ ����ϼ���!"))) {
				String result = null;
				result = JOptionPane.showInputDialog(this, "������ ����� �����̶�!");
				if (result != null) {
					ddto.setMemo(result);
					ddto.setDate(todayDB);
					ddto.setMood(nowMood.getMood());
					ddao.insert(ddto);
					System.out.println("����1");
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
