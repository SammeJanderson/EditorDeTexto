import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main extends JFrame implements ActionListener {
    private JFrame window;
    public JTextArea textArea;
    private JFileChooser jFileChooser;
    private final int WIDTH = 500;
    private final int HEIGHT = 500;


    public Main() {
        //Cria janela
        window = new JFrame("Editor");

        textArea = new JTextArea();
        textArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        textArea.setLineWrap(true);


        JMenuBar menuBar = new JMenuBar();
        JMenuBar editBar = new JMenuBar();


        //File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem printMenuItem = new JMenuItem("Print");

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);

        //Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        //Adicionar menus a barra de menus
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        ImageIcon imageIcon = new ImageIcon("icon.png");

        //adiciona componentes
        window.setJMenuBar(menuBar);
        window.add(textArea);


        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
        window.setIconImage(imageIcon.getImage());
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();


        switch (action) {
            case "Cut" -> textArea.cut();
            case "Copy" -> textArea.copy();
            case "Paste" -> textArea.paste();
            case "Save" -> {
//               cria um objeto da classe JfileChooser
                jFileChooser = new JFileChooser("f: ");

//                invoca a função para mostrar o dialogo de salvar
                int r = jFileChooser.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(jFileChooser.getSelectedFile().getAbsolutePath()+".txt");

                    try {
                        FileWriter fileWriter = new FileWriter(file, false);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                        bufferedWriter.write(textArea.getText());

                        bufferedWriter.flush();
                        bufferedWriter.close();
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(window, evt.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(window, "Operação Cancelada!!");
                }
            }
            case "Print" -> {
                try {
                    //Imprime arquivo
                    textArea.print();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(window, evt.getMessage());
                }
            }
            case "Open" -> {
                JFileChooser fileChooser = new JFileChooser("f: ");
                int r = fileChooser.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    try {
                        String s1 = "", sl = "";

                        FileReader fileReader = new FileReader(file);

                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        sl = bufferedReader.readLine();

                        while ((s1 = bufferedReader.readLine()) != null) {
                            sl = sl + "\n" + s1;
                        }

                        textArea.setText(sl);
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(window, evt.getMessage());
                    }
                } else JOptionPane.showMessageDialog(window, "Operação Cancelada!");
            }
            case "New" -> textArea.setText("");
        }


    }

    public static void main(String[] args) {
        new Main();
    }

}
