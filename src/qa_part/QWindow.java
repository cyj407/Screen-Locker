package qa_part;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QWindow {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800,548);
		frame.setResizable(false);				// set fixed window size
		frame.setLocationRelativeTo(null);		// put frame in the middle of the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel q_window = new JPanel();
        q_window.setLayout(new GridBagLayout());
		q_window.setSize(800,548);
		ImageIcon question_bg = new ImageIcon(".\\src\\qa_part\\_backgroundQuestion.png");
		q_window.setOpaque(true);  
		
		JLabel label = new JLabel(question_bg);
		label.setOpaque(true);
		q_window.add(label);
		
		frame.add(q_window);
		frame.setVisible(true);
	}
}
