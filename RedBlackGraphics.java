import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RedBlackGraphics extends JPanel
{
	private RedBlackTree tree;
	private JFrame frame;
	private JTextField jt;
	private JButton jb;
	private JButton jb2;
	
	public RedBlackGraphics() throws FileNotFoundException
	{
		tree = new RedBlackTree();
		/*
		Scanner scan = new Scanner(new File("RBFile.txt"));
		while(scan.hasNext())
			try
			{
				tree.add(new RedBlackNode(Integer.parseInt(scan.next())));
			}
			catch(Exception c)
			{
				tree.add(new RedBlackNode(scan.next()));
			}
		*/
		setUpGraphics();
	}
	public void setUpGraphics()
	{
		frame = new JFrame();
		setSize(1920, 1080);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		
		Insets frameInsets = getInsets();
		int w = getWidth() + (frameInsets.left + frameInsets.right);
		int l = getHeight() + (frameInsets.top + frameInsets.bottom);
		
		frame.setPreferredSize(new Dimension(w, l));
		frame.setLayout(null);
		frame.add(this);
		frame.pack();
		
		jt = new JTextField(30);
		this.add(jt);
		jb = new JButton("Insert");
		this.add(jb);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					tree.add(new RedBlackNode(Integer.parseInt(jt.getText())));
				}
				catch(Exception c)
				{
					tree.add(new RedBlackNode(jt.getText()));
				}
				repaint();
			}
		});
		jb2 = new JButton("Delete");
		this.add(jb2);
		jb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					tree.remove(Integer.parseInt(jt.getText()));
				}
				catch(Exception c)
				{
					tree.remove(jt.getText());
				}
				repaint();
			}
		});
		
		frame.dispose();
		frame.setVisible(true);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.GRAY);
		g.setFont(new Font("papyrus", Font.BOLD, 25));
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		if(tree != null && !tree.isEmpty())
		{
			String[] lines = tree.fullLevelOrder(tree.getNumLevels() + 1).split(",");
			Coord[][] coords = new Coord[lines.length][];
			for(int i = 0; i < lines.length; i++)
			{
				String[] nodes = lines[i].trim().split(" ");
				coords[i] = new Coord[(int) Math.pow(2, i)];
				for(int j = 0; j < nodes.length; j++)
				{
					coords[i][j] = new Coord((int)(1920/(Math.pow(2, i) + 1)) * (j+1) - 50, i * 125 + 100);
					if(!nodes[j].equals("null"))
					{
						g.setColor(Color.BLACK);
						if(i > 0)
							g2.drawLine(coords[i][j].getX() + 50, coords[i][j].getY() + 50, coords[i-1][j/2].getX() + 50, coords[i-1][j/2].getY() + 100);
						RedBlackNode temp = null;
						try{temp = tree.getNode(tree.getRoot(), Integer.parseInt(nodes[j]));}
						catch(Exception e){temp = tree.getNode(tree.getRoot(), nodes[j]);}
						Color color;
						if(temp.getColor() == ColorNode.RED)
							color = Color.RED;
						else
							color = Color.BLACK;
						g.setColor(color);
						g.fillOval(coords[i][j].getX(), coords[i][j].getY(), 100, 100);
						g.setColor(Color.BLACK);
						g2.drawOval(coords[i][j].getX(), coords[i][j].getY(), 100, 100);
						g.setFont(new Font("default", Font.BOLD, 20));
						g.setColor(Color.WHITE);
						g.drawString(nodes[j], coords[i][j].getX() + 7, coords[i][j].getY() + 60);
					}
					else if(!lines[i-1].split(" ")[j/2].equals("null")) 
					{
						g.setColor(Color.BLACK);
						g2.drawLine(coords[i][j].getX() + 50, coords[i][j].getY() + 50, coords[i-1][j/2].getX() + 50, coords[i-1][j/2].getY() + 100);
						g.fillRect(coords[i][j].getX() + 25, coords[i][j].getY() + 25, 50, 50);
					}
				}
			}
		} 
		
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		new RedBlackGraphics();
	}
}

class Coord
{
	private int x, y;
	public Coord(int x, int y) {this.x = x; this.y = y;}
	public int getX() {return x;}
	public int getY() {return y;}
}
