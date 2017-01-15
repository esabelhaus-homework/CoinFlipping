/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coinflipping;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emsabel
 */
public class CoinFlipsGui extends JPanel implements ActionListener {

    JButton heads;
    JButton tails;
    JTextArea howto;
    Strategy flipper;
    String flips = "";

    int movesLeft = 100;
    
    /*
     * Define base coin flip gui
     * accepts strategy only
     */
    CoinFlipsGui(Strategy s1) {
        // How to text
        howto = new JTextArea("Instructions");
        howto.setText("Pick between HEADS or TAILS to guess what the coin flip will be. Flip until you get to 100.");
        add(howto, BorderLayout.CENTER);

        heads = new JButton("HEADS");
        heads.setName("HEADS");
        heads.setPreferredSize(new Dimension(180, 30));
        heads.addActionListener(this);
        add(heads, BorderLayout.LINE_START);

        tails = new JButton("TAILS");
        tails.setName("TAILS");
        tails.setPreferredSize(new Dimension(180, 30));
        tails.addActionListener(this);
        add(tails, BorderLayout.LINE_END);

        this.flipper = s1;
    }

    public static void createAndShowGUI(Strategy s1) {
        //Create and set up the window.
        JFrame frame = new JFrame("Flipping Coins");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CoinFlipsGui(s1);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent e) {
        
        if (flips.length() == 100) {
            try {
                // simple way to access flip data
                writeFlips();
            } catch (IOException ex) {
                Logger.getLogger(CoinFlipsGui.class.getName()).log(Level.SEVERE, null, ex);
            }
            infoBox("You've reached 100. Here are your results:\n" + flips,e.getActionCommand());
            return;
        }
        
        flipTheCoin();
        
        JButton button = (JButton)e.getSource();
        String name = button.getName();
        
        if ("HEADS" == name) {
            if (1 == flipper.getMyLastMove()) {
                infoBox("You win! The coin landed heads\nFlip again!", e.getActionCommand());
            } else {
                infoBox("You lose. The coin landed tails\nFlip again!", e.getActionCommand());
            }
        } 
        if ("TAILS" == name) {
            if (0 == flipper.getMyLastMove()) {
                infoBox("You win! The coin landed tails\nFlip again!", e.getActionCommand());
            } else {
                infoBox("You lose! The coin landed heads\nFlip again!", e.getActionCommand());
            }
        }
        
        if (flips.length() < 100) {
            String remains = Integer.toString(100 - flips.length());
            infoBox(remains + " flips remaining... Keep flipping!",e.getActionCommand());
        } 
       
    }
    
    public void infoBox(String infoMessage, String location) {
        JOptionPane.showMessageDialog(null, infoMessage, location, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void flipTheCoin() {
        flipper.saveMyMove(flipper.nextMove());
        flips += Integer.toString(flipper.getMyLastMove());
    }
    
    public String readFlips() throws IOException {
        String data = "";
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(flipper.getName()+"_data.txt"));
        while((line = br.readLine()) != null) {
             if (line.length() == 100) {
                 System.out.println(line);
                 data = line;
             } else {
                 return "";
             }
        }

        return data;
        //Files.write(Paths.get("rand_data.txt"), flips.getBytes());
    }
    
    private void writeFlips() throws IOException {
        Files.write(Paths.get(flipper.getName()+"_data.txt"), flips.getBytes());
    }
}
