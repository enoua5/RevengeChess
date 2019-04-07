import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class Frame extends JFrame implements KeyListener
{
    public Canvas c;
    JLabel label;
    
    public int[] AILevel=new int[]{0,0};
    public int theme=0;
    
    public Frame()
    {
        super("Revenge Chess");
        c=new Canvas(0.6);
        c.setPreferredSize(new Dimension(1000, 600));
        this.add(c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        
        JMenuBar menuBar=new JMenuBar();
        JMenu options=new JMenu("Options");
        
        JMenuItem reset_button=new JMenuItem(new AbstractAction("Reset"){
            public void actionPerformed(ActionEvent e)
            {
                Main.f.reset();
            }
        });
        JMenu w_player_menu=new JMenu("White Player");
        ButtonGroup w_player_group = new ButtonGroup();
        JMenu b_player_menu=new JMenu("Black Player");
        ButtonGroup b_player_group = new ButtonGroup();
        
        String[] player_options=new String[]{"Human", "AI level 1", "AI level 2", "AI level 3", "AI level 4", "AI level 5",};
        for(int i=0; i<player_options.length; i++)
        {
            JRadioButtonMenuItem w=new JRadioButtonMenuItem(player_options[i], i==0);
            w.addActionListener(new ValueAction(i){
                public void actionPerformed(ActionEvent e)
                {
                    Main.f.AILevel[1]=value;
                }
            });
            JRadioButtonMenuItem b=new JRadioButtonMenuItem(player_options[i], i==0);
            b.addActionListener(new ValueAction(i){
                public void actionPerformed(ActionEvent e)
                {
                    Main.f.AILevel[0]=value;
                }
            });
            
            w_player_menu.add(w);
            w_player_group.add(w);
            b_player_menu.add(b);
            b_player_group.add(b);
        }
        
        JMenu theme_menu=new JMenu("Theme");
        ButtonGroup theme_group = new ButtonGroup();
        String[] theme_options=new String[]{"Cyan", "Blue", "Magenta", "Red", "Green", "Yellow"};
        for(int i=0; i<theme_options.length; i++)
        {
            JRadioButtonMenuItem t=new JRadioButtonMenuItem(theme_options[i], i==0);
            t.addActionListener(new ValueAction(i){
                public void actionPerformed(ActionEvent e)
                {
                    Main.f.theme=value;
                }
            });
            
            theme_menu.add(t);
            theme_group.add(t);
        }
        
        options.add(reset_button);
        options.add(w_player_menu);
        options.add(b_player_menu);
        options.add(theme_menu);
        menuBar.add(options);
        this.setJMenuBar(menuBar);
        
        this.pack();
        centerFrame();
        this.setVisible(true);
    }
    private void centerFrame()
    {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        setLocation(dx, dy);
    }
    public void reset()
    {
        this.remove(c);
        c=new Canvas(0.6);
        c.setPreferredSize(new Dimension(1000, 600));
        this.add(c);
        this.pack();
    }
    // https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyPressed(KeyEvent e)
    {
        /*
        int k=e.getKeyCode();
        if(k==122)
        {
            GraphicsDevice fullscreenDevice=
                GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();
            dispose();
            if(isUndecorated())
            {
                fullscreenDevice.setFullScreenWindow(null);
                setUndecorated(false);
            }
            else
            {
                setUndecorated(true);
                fullscreenDevice.setFullScreenWindow(this);
            }
            setVisible(true);
            repaint();
        }
        */
    }
    @Override
    public void keyReleased(KeyEvent e){}
}
