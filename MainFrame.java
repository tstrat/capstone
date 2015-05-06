import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class MainFrame extends JFrame{
	
	private final JDesktopPane window = new JDesktopPane();
	private List<SimpleBrowser> pages = new LinkedList<SimpleBrowser>();
	private int active = 0;
	private JButton add = new JButton(), close_all = new JButton();
	
	public MainFrame(){
		super();
		initialize();
	}
	
	public void initialize(){
		ImageIcon plus = new ImageIcon("Plus_sign.png");
		int width = 20;
		add.setIcon(new ImageIcon(plus.getImage().getScaledInstance(width,-1,java.awt.Image.SCALE_SMOOTH )));
		
		ImageIcon x = new ImageIcon("x_mark_red.png");
		close_all.setIcon(new ImageIcon(x.getImage().getScaledInstance(width,-1,java.awt.Image.SCALE_SMOOTH )));
		
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addBrowser();
			}
			
		});
		close_all.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int answer = JOptionPane.showConfirmDialog(getParent(), "Do you want to close all tabs?","Closing all tabs", JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.OK_OPTION){
					for (int i = 0; i < pages.size(); i++){
						if (pages.get(i).isInUse()){
							removeBrowser(pages.get(i));
						}
					}
				}
			}
			
		});
				
		
		
		JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        JPanel buttons = new JPanel(new BorderLayout(5, 0));
        buttons.add(add, BorderLayout.WEST);
        buttons.add(close_all, BorderLayout.EAST);
        
        //topBar.add(add, BorderLayout.EAST);
		topBar.add(buttons, BorderLayout.EAST);
		
		for(int i = 0; i < 35; i++){
			makePage(500);
		}
		setLayout(new BorderLayout());
		add(topBar, BorderLayout.NORTH);
		add(window, BorderLayout.CENTER);
		addBrowser();
		//pages.get(0).loadURL("facebook.com");
		
		setPreferredSize(new Dimension(1024, 600));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		
		
	}
	
	public void makePage(int size){
		SimpleBrowser browser = new SimpleBrowser(this);
        browser.setVisible(true);
        browser.loadURL("http://www.google.com");
        browser.setSize(size, size);
        browser.setLocation(-1000,-1000);
        browser.setResizable(true);
        browser.setMaximizable(true);
        browser.setIconifiable(true);
        pages.add(browser);
        window.add(browser);
        /* Adding a window adapter to see if it closed.  If It does, I would like
         * to remove it from everything (pages, window and the gui).
         */
	}
	
	public void addBrowser(){
		SimpleBrowser current;
		for (int i = 0; i < pages.size(); i++){
			current = pages.get(i);
			if (!current.isInUse()){
				current.toggleUse();
				current.setVisible(true);
				current.loadURL("https://google.com");
				current.setLocation(0, 0);
				//window.add(current);
				active++;
				break;
			}
		}
	}
	
	public void paintComponents(Graphics g){
		super.paintComponents(g);
	}
	
	/**
	 * removeBrowser - Takes a browser that is active and turns it off, removing it from
	 * the display window and prepping it for reuse.  In the event that no browsers are open,
	 * it opens one and puts it on screen.
	 * @param SimpleBrowser s - The SimpleBrowswer to be toggled off and removed from the window
	 */
	public void removeBrowser(SimpleBrowser s){
		//window.remove(s);
		window.setLocation(-1000, -1000);
		if (s.isIcon()){
			try {
				s.setIcon(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		if(s.isMaximum()){
			try {
				s.setMaximum(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s.toggleUse();
		
		active--;
		/*
		System.out.println(active);
		if (active== 0){
			System.out.println("adding 1");
			addBrowser();
			System.out.println(active);
		}*/
		repaint();
	}
	
	/* Start the program! */
	public static void main(String[]args){
		MainFrame start = new MainFrame();
		start.setVisible(true);
	}
}
