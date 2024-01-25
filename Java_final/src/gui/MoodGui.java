package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.Mood;
import main.DiaryADM;
import main.Main;

public class MoodGui extends JFrame implements ActionListener {
	TodayGui tlink = null;
	int selNum = 0;
	ArrayList<Mood> mlist = null;
	
	JButton btn;
	JPanel mainCenter;
	ArrayList<JButton> btns = new ArrayList<>();
	JLabel title = null;

	public MoodGui(ArrayList<Mood> mlist) {
		this.mlist = mlist;
		init();
		setDisplay();
		addListener();
		
	}

	public void init() { // 구성..
		this.setSize(400, 500);
		this.setLocationRelativeTo(null);
		setTitle("Mood");
		title=new JLabel("오늘의 기분");
		//title.setBackground(Color.white); 해결 못함
		title.setFont(new Font("돋움", Font.BOLD, 20));
		title.setHorizontalAlignment(JLabel.CENTER);
		ImageIcon moodIcon = null;
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		for (int i = 0; i < mlist.size(); i++) {
			int k = mlist.get(i).getNum();
			moodIcon = new ImageIcon(Main.class.getResource("../image/" + k + ".png"));
			btn = new JButton(moodIcon);
			btn.setPreferredSize(new Dimension(95,95));
			btns.add(btn);

		}

	}

	public void setDisplay() { // 배치
		this.setLayout(new BorderLayout());
		mainCenter = new JPanel(new FlowLayout());
		mainCenter.setBackground(Color.white);
		// btnsList을 for문 돌면서 flow에 배치..
		for (int i = 0; i < btns.size(); i++) {
			mainCenter.add(btns.get(i));
		}
		// 현재 메인창에서 배치..
		this.add(title, "North");
		this.add(mainCenter, "Center");

	}

	public void addListener() { // 모든 버튼에 행동 붙이기..
		for (JButton btn : btns) {
			btn.addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent arg0) { //
		// btns 중 하나 버튼 눌렀을때
		for (int i = 0; i < btns.size(); i++) {
			if (btns.get(i).equals(arg0.getSource())) {
				selNum = i;
			}
		}
		Mood n = mlist.get(selNum);
		tlink = new TodayGui(n); // 입력쪽에 mooddto 1개보냄
		this.setVisible(false);
	}

}
