import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main extends JFrame implements ActionListener {
    private final JFrame window;
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

        //File menu
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem newMenuItem = new JMenuItem("Novo");
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        JMenuItem saveMenuItem = new JMenuItem("Salvar");
        JMenuItem printMenuItem = new JMenuItem("Imprimir");

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(printMenuItem);

        //Edit menu
        JMenu editMenu = new JMenu("Editar");
        JMenuItem cutMenuItem = new JMenuItem("Cortar");
        JMenuItem copyMenuItem = new JMenuItem("Copiar");
        JMenuItem pasteMenuItem = new JMenuItem("Colar");

        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        //Sobre Menu
        JMenu aboutMenu = new JMenu("Sobre");
        JLabel aboutMeLabel = new JLabel();
        JButton profileButton = new JButton();


        aboutMeLabel.setText("Criado por ");
        aboutMenu.add(aboutMeLabel);
        aboutMenu.add(profileButton);


        profileButton.setText("Samme Janderson");
        profileButton.setHorizontalAlignment(SwingConstants.LEFT);
        profileButton.setBorderPainted(false);
        profileButton.setOpaque(false);
        profileButton.setToolTipText("https://github.com/SammeJanderson");

        profileButton.setBackground(Color.WHITE);


        //Adicionar menus a barra de menus
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);

        ImageIcon imageIcon = new ImageIcon("icon.png");

        //adiciona componentes
        window.setJMenuBar(menuBar);
        window.add(textArea);



        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
        window.setIconImage(imageIcon.getImage());
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ToolTipManager.sharedInstance().setInitialDelay(100);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Cortar" -> textArea.cut();
            case "Copiar" -> textArea.copy();
            case "Colar" -> textArea.paste();
            case "Salvar" -> {
//               cria um objeto da classe JfileChooser
                jFileChooser = new JFileChooser("f: ");

//                invoca a função para mostrar o dialogo de salvar
                int r = jFileChooser.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".txt");

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
            case "Imprimir" -> {
                try {
                    //Imprime arquivo
                    textArea.print();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(window, evt.getMessage());
                }
            }
            case "Abrir" -> {
                JFileChooser fileChooser = new JFileChooser("f: ");
                int r = fileChooser.showOpenDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                    try {
                        String s1;
                        StringBuilder sl;

                        FileReader fileReader = new FileReader(file);

                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        sl = new StringBuilder(bufferedReader.readLine());

                        while ((s1 = bufferedReader.readLine()) != null) {
                            sl.append("\n").append(s1);
                        }

                        textArea.setText(sl.toString());
                    } catch (Exception evt) {
                        JOptionPane.showMessageDialog(window, evt.getMessage());
                    }
                } else JOptionPane.showMessageDialog(window, "Operação Cancelada!");
            }
            case "Novo" -> textArea.setText("");
        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
