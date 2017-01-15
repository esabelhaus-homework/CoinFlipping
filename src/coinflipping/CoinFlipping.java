/*
 @AUTHOR: Associate Professor (Adjunct) Mark A. Wireman
 @COURSE: CMSC325, Intro to Game Development, UMUC
 @CREDITTO: Michael C. Semeniuk
 */
package coinflipping;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author Mark
 */
public class CoinFlipping extends JPanel implements ActionListener {

    JButton buttonFSM;
    JTextArea txtInstructions;
    JTextArea txtSeedMoves;
    JRadioButton rdoRandom;
    JRadioButton rdoProbability;
    JButton calc;
    StrategyRandom r1 = new StrategyRandom();
    StrategyProbabilistic p1 = new StrategyProbabilistic();
    CoinFlipsGui rFlipper = new CoinFlipsGui(r1);
    CoinFlipsGui pFlipper = new CoinFlipsGui(p1);
    
    
    /**
     * Creates coin flipping GUI
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public CoinFlipping() {
        super(new BorderLayout());
        
        txtInstructions = new JTextArea("Instructions");
        txtInstructions.setPreferredSize(new Dimension(600, 100));
        
        txtInstructions.setText("Select Random to create randomized probability data from 100 random flips\nOnce random data created, select Probability to flip 100 more coins based off random data");
        add(txtInstructions, BorderLayout.PAGE_START);

        rdoRandom = new JRadioButton("Random");
        add(rdoRandom, BorderLayout.LINE_START);
        rdoRandom.addActionListener(this);

        calc = new JButton("Calculate Odds");
        add(calc, BorderLayout.CENTER);
        calc.addActionListener(this);
        
        
        rdoProbability = new JRadioButton("Probability");
        add(rdoProbability, BorderLayout.LINE_END);
        rdoProbability.addActionListener(this);
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Flip Coins");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CoinFlipping();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CoinFlipping.createAndShowGUI();
            }
        });
    }

    public void infoBox(String infoMessage, String location) {
        JOptionPane.showMessageDialog(null, infoMessage, location, JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        Toolkit.getDefaultToolkit().beep();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switch (e.getActionCommand()) {
                    case "Random":
                        rFlipper.createAndShowGUI(r1);
                        break;
                    case "Probability":
                        String flips = "";
                        try {
                            flips = rFlipper.readFlips();
                            System.out.println(flips);
                        } catch (IOException ex) {
                            Logger.getLogger(CoinFlipping.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (flips.length() < 100) {
                            infoBox("lacking probability data from random flips!", e.getActionCommand());
                            return;
                        }
                        p1.setMoves(flips);
                        pFlipper = new CoinFlipsGui(p1);
                        pFlipper.createAndShowGUI(p1);
                        break;
                    case "Calculate Odds":
                        String rand = "";
                        String prob = "";
                        try {
                            rand = rFlipper.readFlips();
                            prob = pFlipper.readFlips();
                        } catch (IOException ex) {
                            Logger.getLogger(CoinFlipping.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        int oddsHeadsRandom = rand.length() - rand.replace("1", "").length();
                        int oddsTailsRandom = rand.length() - rand.replace("0", "").length();
                        
                        int oddsHeadsProbablistic = rand.length() - rand.replace("1", "").length();
                        int oddsTailsProbablistic = rand.length() - rand.replace("0", "").length();
                        
                        infoBox("Random probability:\nHEADS: " + 
                                oddsHeadsRandom + "\nTAILS: " + oddsTailsRandom + 
                                "\nProbablistic Probability:\nHEADS: " + oddsHeadsProbablistic +
                                "\nTAILS: " + oddsTailsProbablistic, e.getActionCommand());


                }
            }
        });
    }
}