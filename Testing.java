/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Testing.java
 *
 * Created on Jun 16, 2010, 2:43:17 PM
 */

/**
 *
 * @author Administrator
 */
//import java.io.File;
public class Testing extends javax.swing.JFrame {
public String dbpath=null;
    /** Creates new form Testing */
    public Testing() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setBackground(new java.awt.Color(204, 255, 255));
        jTextField1.setEditable(false);
        jTextField1.setText("Select Matching Feature");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Texture Features", "Global Features", "Grid Distance Features", "Grid Angle Features" }));
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Start");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(204, 255, 255));
        jTextField2.setEditable(false);
        jTextField2.setText("Select  K");
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println(jComboBox1.getSelectedIndex());
        System.out.println((String)jComboBox1.getSelectedItem());

        //System.out.println(evt.toString());

    }

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
         System.out.println(jComboBox1.getSelectedIndex());
        System.out.println((String)jComboBox1.getSelectedItem());
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        // System.out.println((String)jComboBox1.getSelectedItem());
         //System.out.println("Testing.");
         String match_method=(String)jComboBox1.getSelectedItem();

            if(match_method.equals("Texture Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Texture pp=new MAIN_FeatureMatching_Texture();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
         if(match_method.equals("Global Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Global pp=new MAIN_FeatureMatching_Global();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
          if(match_method.equals("Grid Distance Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Grid_Distance pp=new MAIN_FeatureMatching_Grid_Distance();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }

         if(match_method.equals("Grid Angle Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Grid_Angle pp=new MAIN_FeatureMatching_Grid_Angle();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
             dispose();


    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
     String match_method=(String)jComboBox1.getSelectedItem();

            if(match_method.equals("Texture Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Texture pp=new MAIN_FeatureMatching_Texture();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
         if(match_method.equals("Global Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Global pp=new MAIN_FeatureMatching_Global();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
          if(match_method.equals("Grid Distance Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Grid_Distance pp=new MAIN_FeatureMatching_Grid_Distance();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }

         if(match_method.equals("Grid Angle Features"))
            {System.out.println((String)jComboBox1.getSelectedItem());
                    MAIN_FeatureMatching_Grid_Angle pp=new MAIN_FeatureMatching_Grid_Angle();
                    try{
                    pp.mainf(jComboBox2.getSelectedIndex()+1);
                    }catch(Exception E){}
            }
             dispose();
    }

    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
    * @param args the command line arguments
    */
   
    public void mainf() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Testing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration

}