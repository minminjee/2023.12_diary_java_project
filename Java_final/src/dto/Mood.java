package dto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.DiaryADM;
import main.Main;

public class Mood {
	
	String mood = null;
	int Num = 0;
	ArrayList<JButton> btns = new ArrayList<>();
	JLabel title = new JLabel("¿À´Ã ÇÏ·ç´Â?");
	DiaryADM alink = null;
	int selNum = 0;

	public Mood() {
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

	public void prt() {
		System.out.println(mood);
	}

}
