
package svg;

import com.graphbuilder.math.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import audio.AudioPlayer;

class MyCustomFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        return file.isDirectory() || file.getAbsolutePath().endsWith(".svg");
    }

    @Override
    public String getDescription() {
        return "Vector Graphics (*.svg)";
    }
}

public class MainClass extends javax.swing.JFrame {

    public MainClass() throws FileNotFoundException, IOException {

        FileInputStream fs = new FileInputStream("config.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        String input = br.readLine();
        String[] input2 = input.split("=");

        int fontsize = Integer.parseInt(input2[1]);

        input = br.readLine();
        input2 = input.split("=");

        int linethickness = Integer.parseInt(input2[1]);

        initComponents();
        myInitComponents(fontsize, linethickness);  
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        Grp = new javax.swing.ButtonGroup();
        fileChooser2 = new javax.swing.JFileChooser();
        toolbar = new javax.swing.JToolBar();
        diag = new javax.swing.JToggleButton();
        chem = new javax.swing.JToggleButton();
        function = new javax.swing.JToggleButton();
        menu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Print = new javax.swing.JMenuItem();
        Save = new javax.swing.JMenuItem();
        SaveAs = new javax.swing.JMenuItem();

        fileChooser.setControlButtonsAreShown(false);
        fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\Mrinal\\Documents\\Academics\\MTP"));
        fileChooser.setFileFilter(abc);

        fileChooser2.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooser2.setCurrentDirectory(new java.io.File("C:\\Users\\Mrinal\\Documents\\Academics\\MTP"));
        fileChooser2.setFileFilter(abc);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edutactile");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        toolbar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        Grp.add(diag);
        diag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_square-dashed-512.png"))); // NOI18N
        diag.setText("Diagram Conversion");
        diag.setBorder(null);
        diag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        diag.setMaximumSize(new java.awt.Dimension(140, 80));
        diag.setMinimumSize(new java.awt.Dimension(140, 80));
        diag.setPreferredSize(new java.awt.Dimension(140, 80));
        diag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        diag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diagActionPerformed(evt);
            }
        });
        toolbar.add(diag);

        Grp.add(chem);
        chem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_1tumblr_m4c9wrrklq1rw1p5qo1_1280.png"))); // NOI18N
        chem.setText("Chemical Equation");
        chem.setBorder(null);
        chem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chem.setMaximumSize(new java.awt.Dimension(140, 80));
        chem.setMinimumSize(new java.awt.Dimension(140, 80));
        chem.setPreferredSize(new java.awt.Dimension(140, 80));
        chem.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chemActionPerformed(evt);
            }
        });
        toolbar.add(chem);

        Grp.add(function);
        function.setIcon(new javax.swing.ImageIcon(getClass().getResource("/svg/resources/rsz_1picture1.png"))); // NOI18N
        function.setText("Function");
        function.setBorder(null);
        function.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        function.setMaximumSize(new java.awt.Dimension(140, 80));
        function.setMinimumSize(new java.awt.Dimension(140, 80));
        function.setPreferredSize(new java.awt.Dimension(140, 80));
        function.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        function.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functionActionPerformed(evt);
            }
        });
        toolbar.add(function);

        jMenu1.setText("File");
        jMenu1.setFocusPainted(true);
        jMenu1.setFocusTraversalPolicyProvider(true);

        Print.setText("Print");
        Print.setFocusPainted(true);
        Print.setFocusTraversalPolicyProvider(true);
        Print.setFocusable(true);
        Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintActionPerformed(evt);
            }
        });
        jMenu1.add(Print);

        Save.setText("Save");
        Save.setFocusPainted(true);
        Save.setFocusable(true);
        jMenu1.add(Save);

        SaveAs.setText("Save As...");
        SaveAs.setFocusable(true);
        jMenu1.add(SaveAs);

        menu.add(jMenu1);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 359, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Actions Performed">
    // <editor-fold defaultstate="collapsed" desc="Menu Actions Performed">

    private void diagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diagActionPerformed

        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 1;
        this.setTitle("Edutactile");
        panel.repaint();
        diagchoice();
    }//GEN-LAST:event_diagActionPerformed

    private void chemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chemActionPerformed
        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 2;
        this.setTitle("Edutactile");
        panel.repaint();
        chemchoice();
    }//GEN-LAST:event_chemActionPerformed

    private void functionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functionActionPerformed
        panel.removeAll();
        panel.updateUI();
        panel.zero();
        r1.setSelectedIndex(0);
        r2.setSelectedIndex(0);
        r3.setSelectedIndex(0);
        r4.setSelectedIndex(0);
        p1.setSelectedIndex(0);
        p2.setSelectedIndex(0);
        p3.setSelectedIndex(0);
        p4.setSelectedIndex(0);
        txtbx.setText("");
        funbx.setText("");
        panel.value = 3;
        this.setTitle("Edutactile");
        panel.repaint();
        funchoice();
    }//GEN-LAST:event_functionActionPerformed

    private void PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintActionPerformed
        PrinterJob job = PrinterJob.getPrinterJob();
        switch (panel.value) {
            case 1:
                ImageConverter imageconverter = new ImageConverter(panel);
                job.setPrintable(imageconverter);
                boolean ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    } catch (PrinterException ex) {
                    }
                }
                break;

            case 2:
                ChemicalEquation chemicalequation = new ChemicalEquation(panel);
                job.setPrintable(chemicalequation);
                ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    } catch (PrinterException ex) {
                    }
                }
                break;
            case 3:
                MathFunction mathfunction = new MathFunction(panel);
                job.setPrintable(mathfunction);
                ok = job.printDialog();
                if (ok) {
                    try {
                        job.print();
                    } catch (PrinterException ex) {
                    }
                }
                break;

        }

    }//GEN-LAST:event_PrintActionPerformed

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Converter Actions Performed">
    private void OpenActionPerformed(java.awt.event.ActionEvent evt) {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            panel.converted.clear();
            panel.setFile(file, file.getName());
            this.setTitle(file.getName() + " - Edutactile");
            panel.repaint();
        } else {
            System.out.println("File access cancelled by user.");
        }
    }

    private void textureRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        panel.texturetype = 0;
    }

    private void textureRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        panel.texturetype = 1;

    }

    private void textureRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        panel.texturetype = 2;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Option Actions Performed">
    private void box1ActionPerformed(java.awt.event.ActionEvent evt) {
        int pop = box1.getSelectedIndex();
        int rop = (pop + 1) * 2;
        panel.repaint();

    }

    private void box2ActionPerformed(java.awt.event.ActionEvent evt) {
        int pop = box2.getSelectedIndex();
        panel.repaint();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Chemical Actions Performed">
    private void p1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p1nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p1nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p1nam = pop;
        panel.repaint();
        ppanel1.chemical = pop;
        ppanel1.repaint();

    }

    private void p2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p2nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p2nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p2nam = pop;
        panel.repaint();
        ppanel2.chemical = pop;
        ppanel2.repaint();
    }

    private void p3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p3nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p3nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p3nam = pop;
        panel.repaint();
        ppanel3.chemical = pop;
        ppanel3.repaint();
    }

    private void p4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) p4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.p4nam.equals("")) {
            } else {
                panel.nop = panel.nop - 1;
            }
        } else {
            if (panel.p4nam.equals("")) {
                panel.nop = panel.nop + 1;
            }
        }
        panel.p4nam = pop;
        panel.repaint();
        ppanel4.chemical = pop;
        ppanel4.repaint();
    }

    private void r1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r1nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r1nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r1nam = pop;
        panel.repaint();
        rpanel1.chemical = pop;
        rpanel1.repaint();
    }

    private void r2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r2nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r2nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r2nam = pop;
        panel.repaint();
        rpanel2.chemical = pop;
        rpanel2.repaint();
    }

    private void r3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r3nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r3nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r3nam = pop;
        panel.repaint();
        rpanel3.chemical = pop;
        rpanel3.repaint();
    }

    private void r4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) r4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.r4nam.equals("")) {
            } else {
                panel.nor = panel.nor - 1;
            }
        } else {
            if (panel.r4nam.equals("")) {
                panel.nor = panel.nor + 1;
            }
        }
        panel.r4nam = pop;
        panel.repaint();
        rpanel4.chemical = pop;
        rpanel4.repaint();
    }

    private void c1ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c1.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c1nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c1nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c1nam = pop;
        panel.repaint();
        cpanel1.chemical = pop;
        cpanel1.repaint();
    }

    private void c2ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c2.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c2nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c2nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c2nam = pop;
        panel.repaint();
        cpanel2.chemical = pop;
        cpanel2.repaint();
    }

    private void c3ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c3.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c3nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c3nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c3nam = pop;
        panel.repaint();
        cpanel3.chemical = pop;
        cpanel3.repaint();
    }

    private void c4ActionPerformed(java.awt.event.ActionEvent evt) {
        String pop = (String) c4.getSelectedItem();
        if (pop.equals("")) {
            if (panel.c4nam.equals("")) {
            } else {
                panel.noc = panel.noc - 1;
            }
        } else {
            if (panel.c4nam.equals("")) {
                panel.noc = panel.noc + 1;
            }
        }
        panel.c4nam = pop;
        panel.repaint();
        cpanel4.chemical = pop;
        cpanel4.repaint();
    }

    private void doneActionPerformed(java.awt.event.ActionEvent evt) {
        String pres = pressurebx.getText();
        String pres2 = tempbx.getText();

        panel.pressure = pres;
        panel.temp = pres2;
        panel.repaint();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Language Actions Performed">
    private void TextActionPerformed(java.awt.event.ActionEvent evt) {
        String a = txtbx.getText();
        panel.txt = a;
        panel.repaint();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Calculator Actions Performed">
    private void FunActionPerformed(java.awt.event.ActionEvent evt) {

        String a = funbx.getText();
        String[] a2 = a.split(";");
        panel.fm.loadDefaultFunctions();

        ArrayList<Expression> fun = new ArrayList<>();

        for (int i = 0; i < a2.length; i++) {
            Expression func = ExpressionTree.parse(a2[i]);
            fun.add(func);
        }
        
        panel.fun = fun;
        this.setTitle("Edutactile");
        panel.repaint();
        
        Thread audioPlayer = new Thread(new AudioPlayer());
        audioPlayer.start();
                  
        txtbx.setText("This is a (y = " + funbx.getText() + ") graph" );    
       

    }

    private void funmediumActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 1;
        panel.repaint();
    }

    private void funthinActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 0;
        panel.repaint();
    }

    private void funthickActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinethickness = 2;
        panel.repaint();
    }

    private void funnormalActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinetype = 0;
        panel.repaint();
    }

    private void fundashedActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinetype = 1;
        panel.repaint();
    }

    private void fundotActionPerformed(java.awt.event.ActionEvent evt) {
        panel.flinetype = 2;
        panel.repaint();
    }

    // </editor-fold>
    // </editor-fold>
    public static void main(String args[]) {

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainClass.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainClass().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Variable Declarations">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup Grp;
    private javax.swing.JMenuItem Print;
    private javax.swing.JMenuItem Save;
    private javax.swing.JMenuItem SaveAs;
    private javax.swing.JToggleButton chem;
    private javax.swing.JToggleButton diag;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileChooser2;
    private javax.swing.JToggleButton function;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menu;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables

//Custom variables declaration
    IconPanel panel = new IconPanel();
    JPanel options, chemequ, textenter, diagconv, functions, diagsettings;
    MyCustomFilter abc = new MyCustomFilter();
    JComboBox box1, box2;
    JLabel pagesize, orientation;
    JButton txtbutton;
    NoTabJTextArea txtbx;

    // <editor-fold defaultstate="collapsed" desc="Diagram Conversion Variable Declarations">
    JRadioButton textureRadioButton1, textureRadioButton2, textureRadioButton3, textureRadioButton4, textureRadioButton5;
    JLabel texturelabel;
    JButton open;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Mathematical Functions Variable Declarations">
    JTextField funbx;
    JButton fungo;
    JLabel funthickness, funlinetype;
    JRadioButton funthin, funmedium, funthick, funnormal, fundashed, fundot;
    JPanel funsettings;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Chemical Equations Variable Declarations">
    JPanel reactantpanel, productpanel, catalystpanel, conditionpanel;
    IconPanel rpanel1, rpanel2, rpanel3, rpanel4, ppanel1, ppanel2, ppanel3, ppanel4, cpanel1, cpanel2, cpanel3, cpanel4;
    JComboBox r1, r2, r3, r4, p1, p2, p3, p4, c1, c2, c3, c4;
    JLabel temptext, pressuretxt;
    JTextField tempbx, pressurebx;
    JButton donebtn;
    // </editor-fold>

    // </editor-fold>
    private void myInitComponents(int fontsize, int linethickness) {

        //----------------------------------------------------------------------------------------------   
        //Screen fitting to monitor---------------------------------------------------------------------
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        int taskBarHeight = scrnSize.height - winSize.height;
        this.setSize(width, height - taskBarHeight);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //End screen fitting------------------------------------------------------------------------------
        //-----------------------------------------------------------------------------------------------
        // <editor-fold defaultstate="collapsed" desc="Assignments">
        panel.linethickness = linethickness;
        panel.fontsize = fontsize;
        orientation = new JLabel();
        pagesize = new JLabel();
        options = new JPanel();
        chemequ = new JPanel();
        diagconv = new JPanel();
        textenter = new JPanel();
        functions = new JPanel();
        box1 = new JComboBox();
        box2 = new JComboBox();
        txtbutton = new JButton();
        txtbutton.getAccessibleContext().setAccessibleDescription("Print the entered desciption on the page ");
        txtbx = new NoTabJTextArea();
        txtbx.getAccessibleContext().setAccessibleDescription("Edit default description here");
        texturelabel = new JLabel();

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Textures">
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                String s = "";
                for (int i = 0; i < panel.converted.size(); i++) {
                    if (panel.converted.get(i).s == null) {
                        s = s + "2";
                    } else {
                        if (panel.converted.get(i).s.contains(e.getX(), e.getY())) {
                            s = s + "1";
                        } else {
                            s = s + "0";
                        }
                    }
                }
                if (!s.equals("") && Integer.parseInt(s) != 0) {
                    Texture b = new Texture();
                    b.code = s;

                    for (int j = 0; j < panel.textures.size(); j++) {
                        if (s.equals(panel.textures.get(j).code)) {
                            panel.textures.remove(j);
                        }
                    }

                    if (panel.texturetype != 0) {
                        b.texturetype = panel.texturetype;
                        panel.textures.add(b);
                    }
                }

                panel.repaint();
            }
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Diagram Conversion Assignments">
        diagsettings = new javax.swing.JPanel();

        open = new javax.swing.JButton();

        textureRadioButton1 = new JRadioButton();
        textureRadioButton2 = new JRadioButton();
        textureRadioButton3 = new JRadioButton();
        textureRadioButton4 = new JRadioButton();
        textureRadioButton5 = new JRadioButton();

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Mathematical Function Assignments">
        funsettings = new javax.swing.JPanel();

        fungo = new javax.swing.JButton();
        fungo.getAccessibleContext().setAccessibleDescription("Draw the graph");
        funbx = new javax.swing.JTextField();
        funbx.getAccessibleContext().setAccessibleDescription("Enter function here");
        funthickness = new javax.swing.JLabel();
        funthin = new JRadioButton();
        funmedium = new JRadioButton();
        funthick = new JRadioButton();
        funlinetype = new JLabel();
        funnormal = new JRadioButton();
        fundashed = new JRadioButton();
        fundot = new JRadioButton();

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Chemical Equation Assignments">
        reactantpanel = new javax.swing.JPanel();
        rpanel1 = new IconPanel();
        r1 = new JComboBox();
        r2 = new JComboBox();
        rpanel2 = new IconPanel();
        r3 = new JComboBox();
        rpanel3 = new IconPanel();
        rpanel4 = new IconPanel();
        r4 = new JComboBox();
        conditionpanel = new JPanel();
        temptext = new JLabel();
        tempbx = new JTextField();
        pressuretxt = new JLabel();
        pressurebx = new JTextField();
        donebtn = new JButton();
        productpanel = new JPanel();
        ppanel1 = new IconPanel();
        p1 = new JComboBox();
        p2 = new JComboBox();
        ppanel2 = new IconPanel();
        p3 = new JComboBox();
        ppanel3 = new IconPanel();
        ppanel4 = new IconPanel();
        p4 = new JComboBox();
        catalystpanel = new JPanel();
        cpanel1 = new IconPanel();
        c1 = new JComboBox();
        c2 = new JComboBox();
        cpanel2 = new IconPanel();
        c3 = new JComboBox();
        cpanel3 = new IconPanel();
        cpanel4 = new IconPanel();
        c4 = new JComboBox();

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Panel backgrounds">
        panel.setBackground(new java.awt.Color(255, 255, 255));
        options.setBackground(new java.awt.Color(240, 240, 240));
        chemequ.setBackground(new java.awt.Color(240, 240, 240));
        diagconv.setBackground(new java.awt.Color(240, 240, 240));
        diagsettings.setBackground(new java.awt.Color(240, 240, 240));
        functions.setBackground(new java.awt.Color(240, 240, 240));
        textenter.setBackground(new java.awt.Color(240, 240, 240));
        funsettings.setBackground(new java.awt.Color(240, 240, 240));
        reactantpanel.setBackground(new java.awt.Color(240, 240, 240));
        productpanel.setBackground(new java.awt.Color(240, 240, 240));
        catalystpanel.setBackground(new java.awt.Color(240, 240, 240));
        conditionpanel.setBackground(new java.awt.Color(240, 240, 240));
        rpanel1.setBackground(new java.awt.Color(240, 240, 240));
        rpanel2.setBackground(new java.awt.Color(240, 240, 240));
        rpanel3.setBackground(new java.awt.Color(240, 240, 240));
        rpanel4.setBackground(new java.awt.Color(240, 240, 240));
        ppanel1.setBackground(new java.awt.Color(240, 240, 240));
        ppanel2.setBackground(new java.awt.Color(240, 240, 240));
        ppanel3.setBackground(new java.awt.Color(240, 240, 240));
        ppanel4.setBackground(new java.awt.Color(240, 240, 240));
        cpanel1.setBackground(new java.awt.Color(240, 240, 240));
        cpanel2.setBackground(new java.awt.Color(240, 240, 240));
        cpanel3.setBackground(new java.awt.Color(240, 240, 240));
        cpanel4.setBackground(new java.awt.Color(240, 240, 240));

        rpanel1.value = 4;
        rpanel2.value = 4;
        rpanel3.value = 4;
        rpanel4.value = 4;
        ppanel1.value = 4;
        ppanel2.value = 4;
        ppanel3.value = 4;
        ppanel4.value = 4;
        cpanel1.value = 4;
        cpanel2.value = 4;
        cpanel3.value = 4;
        cpanel4.value = 4;

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Image panel">
        javax.swing.GroupLayout playout = new javax.swing.GroupLayout(panel);
        panel.setLayout(playout);
        playout.setHorizontalGroup(
                playout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, (3 * width / 5) - 37, Short.MAX_VALUE));
        playout.setVerticalGroup(
                playout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, (height - taskBarHeight) - 46 - 82 - 12, Short.MAX_VALUE));

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Options panel">
        options.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        box1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"A2", "A4", "A6"}));
        box1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box1ActionPerformed(evt);
            }
        });

        box1.setSelectedIndex(2);

        box2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Portrait", "Landscape"}));
        box2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box2ActionPerformed(evt);
            }
        });

        pagesize.setText("Page Size");
        orientation.setText("Orientation");

        javax.swing.GroupLayout olayout = new javax.swing.GroupLayout(options);
        options.setLayout(olayout);
        olayout.setHorizontalGroup(
                olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, olayout.createSequentialGroup()
                        .addContainerGap((2 * width / 5) - 148, Short.MAX_VALUE)
                        .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(olayout.createSequentialGroup()
                                        .addComponent(orientation)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(olayout.createSequentialGroup()
                                        .addComponent(pagesize)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(5, 5, 5)));
        olayout.setVerticalGroup(
                olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(olayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(pagesize)
                                .addComponent(box1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(olayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(orientation)
                                .addComponent(box2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Chemical Equations panel">
        chemequ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        String[] chemicals = new String[]{"", "Phenol", "Benzene", "Benzoic Acid", "Bromine", "Bromobenzene", "Aniline", "O-Bromoaniline", "M-Bromoaniline", "P-Bromoaniline"};
        Arrays.sort(chemicals);

        p1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        p4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        r4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c1.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c2.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c3.setModel(new javax.swing.DefaultComboBoxModel(chemicals));
        c4.setModel(new javax.swing.DefaultComboBoxModel(chemicals));

        p1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1ActionPerformed(evt);
            }
        });
        p2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p2ActionPerformed(evt);
            }
        });
        p3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p3ActionPerformed(evt);
            }
        });
        p4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p4ActionPerformed(evt);
            }
        });
        r1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r1ActionPerformed(evt);
            }
        });
        r2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r2ActionPerformed(evt);
            }
        });
        r3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                r3ActionPerformed(evt);
            }
        });
        r4.addActionListener(new java.awt.event.ActionListener() {
         @Override
       public void actionPerformed(java.awt.event.ActionEvent evt) {
              r4ActionPerformed(evt);
            }
        });
        c1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });
        c2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
        });
        c3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
        });
        c4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c4ActionPerformed(evt);
            }
        });

        // <editor-fold defaultstate="collapsed" desc="Reactants panel">
        reactantpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Reactants"));

        rpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rpanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout rpanel1Layout = new javax.swing.GroupLayout(rpanel1);
        rpanel1.setLayout(rpanel1Layout);
        rpanel1Layout.setHorizontalGroup(
                rpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel1Layout.setVerticalGroup(
                rpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout rpanel2Layout = new javax.swing.GroupLayout(rpanel2);
        rpanel2.setLayout(rpanel2Layout);
        rpanel2Layout.setHorizontalGroup(
                rpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel2Layout.setVerticalGroup(
                rpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        rpanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout rpanel3Layout = new javax.swing.GroupLayout(rpanel3);
        rpanel3.setLayout(rpanel3Layout);
        rpanel3Layout.setHorizontalGroup(
                rpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel3Layout.setVerticalGroup(
                rpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        rpanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout rpanel4Layout = new javax.swing.GroupLayout(rpanel4);
        rpanel4.setLayout(rpanel4Layout);
        rpanel4Layout.setHorizontalGroup(
                rpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        rpanel4Layout.setVerticalGroup(
                rpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout reactantpanelLayout = new javax.swing.GroupLayout(reactantpanel);
        reactantpanel.setLayout(reactantpanelLayout);
        reactantpanelLayout.setHorizontalGroup(
                reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(r2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(r3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(rpanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                               .addComponent(r4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)));
        reactantpanelLayout.setVerticalGroup(
                reactantpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                        .addComponent(rpanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(r4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                        .addComponent(rpanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(r3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                        .addComponent(rpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(r2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reactantpanelLayout.createSequentialGroup()
                        .addComponent(rpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(r1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Products panel">
        productpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Products"));

        ppanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel1Layout = new javax.swing.GroupLayout(ppanel1);
        ppanel1.setLayout(ppanel1Layout);
        ppanel1Layout.setHorizontalGroup(
                ppanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel1Layout.setVerticalGroup(
                ppanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel2Layout = new javax.swing.GroupLayout(ppanel2);
        ppanel2.setLayout(ppanel2Layout);
        ppanel2Layout.setHorizontalGroup(
                ppanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel2Layout.setVerticalGroup(
                ppanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel3Layout = new javax.swing.GroupLayout(ppanel3);
        ppanel3.setLayout(ppanel3Layout);
        ppanel3Layout.setHorizontalGroup(
                ppanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel3Layout.setVerticalGroup(
                ppanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        ppanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout ppanel4Layout = new javax.swing.GroupLayout(ppanel4);
        ppanel4.setLayout(ppanel4Layout);
        ppanel4Layout.setHorizontalGroup(
                ppanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        ppanel4Layout.setVerticalGroup(
                ppanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout productpanelLayout = new javax.swing.GroupLayout(productpanel);
        productpanel.setLayout(productpanelLayout);
        productpanelLayout.setHorizontalGroup(
                productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                        .addContainerGap(34, Short.MAX_VALUE)
                        .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ppanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ppanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ppanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ppanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)));
        productpanelLayout.setVerticalGroup(
                productpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                        .addComponent(ppanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                        .addComponent(ppanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                        .addComponent(ppanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productpanelLayout.createSequentialGroup()
                        .addComponent(ppanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Catalyst panel">
        catalystpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Catalysts"));

        cpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel1Layout = new javax.swing.GroupLayout(cpanel1);
        cpanel1.setLayout(cpanel1Layout);
        cpanel1Layout.setHorizontalGroup(
                cpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel1Layout.setVerticalGroup(
                cpanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));
        c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });

        cpanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel2Layout = new javax.swing.GroupLayout(cpanel2);
        cpanel2.setLayout(cpanel2Layout);
        cpanel2Layout.setHorizontalGroup(
                cpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel2Layout.setVerticalGroup(
                cpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        cpanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel3Layout = new javax.swing.GroupLayout(cpanel3);
        cpanel3.setLayout(cpanel3Layout);
        cpanel3Layout.setHorizontalGroup(
                cpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel3Layout.setVerticalGroup(
                cpanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        cpanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout cpanel4Layout = new javax.swing.GroupLayout(cpanel4);
        cpanel4.setLayout(cpanel4Layout);
        cpanel4Layout.setHorizontalGroup(
                cpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 0, Short.MAX_VALUE));
        cpanel4Layout.setVerticalGroup(
                cpanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 119, Short.MAX_VALUE));

        javax.swing.GroupLayout catalystpanelLayout = new javax.swing.GroupLayout(catalystpanel);
        catalystpanel.setLayout(catalystpanelLayout);
        catalystpanelLayout.setHorizontalGroup(
                catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                        .addContainerGap(34, Short.MAX_VALUE)
                        .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cpanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cpanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cpanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(85, 85, 85)));
        catalystpanelLayout.setVerticalGroup(
                catalystpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                        .addComponent(cpanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                        .addComponent(cpanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                        .addComponent(cpanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, catalystpanelLayout.createSequentialGroup()
                        .addComponent(cpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Conditions panel">
        conditionpanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Conditions"));

        temptext.setText("Temperature");

        pressuretxt.setText("Pressure");

        donebtn.setText("Done");

        donebtn.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout conditionpanelLayout = new javax.swing.GroupLayout(conditionpanel);
        conditionpanel.setLayout(conditionpanelLayout);
        conditionpanelLayout.setHorizontalGroup(
                conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(conditionpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(temptext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tempbx, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pressuretxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pressurebx, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(donebtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        conditionpanelLayout.setVerticalGroup(
                conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(conditionpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(conditionpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(temptext)
                                .addComponent(tempbx, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pressuretxt)
                                .addComponent(pressurebx, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(donebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)));

        // </editor-fold>     
        javax.swing.GroupLayout cLayout = new javax.swing.GroupLayout(chemequ);
        chemequ.setLayout(cLayout);
        cLayout.setHorizontalGroup(
                cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(conditionpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(reactantpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(productpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(catalystpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        cLayout.setVerticalGroup(
                cLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(cLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reactantpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(catalystpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(conditionpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Diagram Conversion panel">
        diagconv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        open.setText("Open");
        open.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenActionPerformed(evt);
            }
        });

        // <editor-fold defaultstate="collapsed" desc="Settings panel buttons">
        diagsettings.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        texturelabel.setText("Textures");

        textureRadioButton1.setText("No Texture");
        textureRadioButton2.setText("Texture1");
        textureRadioButton3.setText("Texture2");
        textureRadioButton4.setText("Texture3");
        textureRadioButton5.setText("Texture4");

        ButtonGroup texturegroup = new ButtonGroup();

        texturegroup.add(textureRadioButton1);
        texturegroup.add(textureRadioButton2);
        texturegroup.add(textureRadioButton3);
        texturegroup.add(textureRadioButton4);
        texturegroup.add(textureRadioButton5);
        textureRadioButton1.setSelected(true);

        textureRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textureRadioButton1ActionPerformed(evt);
            }
        });

        textureRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textureRadioButton2ActionPerformed(evt);
            }
        });

        textureRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textureRadioButton3ActionPerformed(evt);
            }
        });

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Settings panel">
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(diagsettings);
        diagsettings.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textureRadioButton5)
                                .addComponent(textureRadioButton4)
                                .addComponent(textureRadioButton3)
                                .addComponent(textureRadioButton2)
                                .addComponent(textureRadioButton1)
                                .addComponent(texturelabel))
                        .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(texturelabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textureRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textureRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textureRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textureRadioButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textureRadioButton5)
                        .addContainerGap(471, Short.MAX_VALUE))
        );

        // </editor-fold>
        javax.swing.GroupLayout dLayout = new javax.swing.GroupLayout(diagconv);
        diagconv.setLayout(dLayout);
        dLayout.setHorizontalGroup(
                dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(diagsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(dLayout.createSequentialGroup()
                                        .addComponent(open)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        dLayout.setVerticalGroup(
                dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(open)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(diagsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Mathematical Functions panel">
        functions.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Font bigFont = funbx.getFont().deriveFont(Font.PLAIN, 20f);
        funbx.setFont(bigFont);

        fungo.setText("Draw");
        fungo.setMaximumSize(new java.awt.Dimension(70, 23));
        fungo.setMinimumSize(new java.awt.Dimension(70, 23));
        fungo.setPreferredSize(new java.awt.Dimension(70, 23));
        fungo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FunActionPerformed(evt);
            }
        });

        // <editor-fold defaultstate="collapsed" desc="Settings panel buttons">
        funsettings.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));

        ButtonGroup group = new ButtonGroup();

        group.add(funthin);
        group.add(funmedium);
        group.add(funthick);
        funthin.setSelected(true);

        ButtonGroup group2 = new ButtonGroup();

        group2.add(funnormal);
        group2.add(fundashed);
        group2.add(fundot);
        funnormal.setSelected(true);

        funthickness.setText("Thickness");

        funthin.setText("Thin");
        funthin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funthinActionPerformed(evt);
            }
        });

        funmedium.setText("Medium");
        funmedium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funmediumActionPerformed(evt);
            }
        });

        funthick.setText("Thick");
        funthick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funthickActionPerformed(evt);
            }
        });

        funlinetype.setText("Line Type");

        funnormal.setText("Normal");
        funnormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funnormalActionPerformed(evt);
            }
        });

        fundashed.setText("Dashed");
        fundashed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundashedActionPerformed(evt);
            }
        });

        fundot.setText("Dotted");
        fundot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fundotActionPerformed(evt);
            }
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Settings panel">
        javax.swing.GroupLayout funsettingsLayout = new javax.swing.GroupLayout(funsettings);
        funsettings.setLayout(funsettingsLayout);
        funsettingsLayout.setHorizontalGroup(
                funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(funsettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(funthickness)
                                .addComponent(funthin)
                                .addComponent(funmedium)
                                .addComponent(funthick))
                        .addGap(26, 26, 26)
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fundot)
                                .addComponent(fundashed)
                                .addComponent(funnormal)
                                .addComponent(funlinetype))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        funsettingsLayout.setVerticalGroup(
                funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(funsettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(funthickness)
                                .addComponent(funlinetype))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(funthin)
                                .addComponent(funnormal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(funmedium)
                                .addComponent(fundashed))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(funsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(funthick)
                                .addComponent(fundot))
                        .addContainerGap(509, Short.MAX_VALUE)));
        // </editor-fold>

        javax.swing.GroupLayout flayout = new javax.swing.GroupLayout(functions);
        functions.setLayout(flayout);
        flayout.setHorizontalGroup(
                flayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(flayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(flayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(funsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(flayout.createSequentialGroup()
                                        .addComponent(funbx)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(fungo)))
                        .addContainerGap()));
        flayout.setVerticalGroup(
                flayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(flayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(flayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(fungo, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                .addComponent(funbx))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(funsettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap()));

        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Text enter panel">
        textenter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        //txtbx.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Font bigFont2 = txtbx.getFont().deriveFont(Font.PLAIN, 20f);
        txtbx.setFont(bigFont2);
        txtbx.setLineWrap(true);
        txtbx.setWrapStyleWord(true);

        txtbutton.setText("Print");
        txtbutton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tlayout = new javax.swing.GroupLayout(textenter);
        textenter.setLayout(tlayout);

        tlayout.setHorizontalGroup(
                tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tlayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtbx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbutton)
                        .addContainerGap()));
        tlayout.setVerticalGroup(
                tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tlayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(tlayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(tlayout.createSequentialGroup()
                                        .addComponent(txtbutton)
                                        .addGap(0, 80, Short.MAX_VALUE))
                                .addComponent(txtbx, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, (height - taskBarHeight), Short.MAX_VALUE)));

        // </editor-fold>
    }

    public void chemchoice() {

        this.remove(diagconv);
        this.remove(functions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chemequ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)));

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(chemequ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)));
    }

    public void diagchoice() {

        this.remove(chemequ);
        this.remove(functions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(diagconv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)));

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(diagconv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)));
    }

    public void funchoice() {

        this.remove(chemequ);
        this.remove(diagconv);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(options, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(functions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textenter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)));

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(options, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(functions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(textenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5)));
    }
}
