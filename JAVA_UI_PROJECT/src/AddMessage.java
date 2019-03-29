import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMessage extends JFrame {

    public JPanel mainPanel = new JPanel();
    public JPanel upPanel = new JPanel();
    public JPanel downPanel = new JPanel();


    private JButton exitButton = new JButton("Exit");
    private JButton addMessageBtn = new JButton("Add new message");


    public AddMessage() {

        setUp();

        buttonDesign();

        panelDesign();

        addMessageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NumberOfMessages numberOfMessages = new NumberOfMessages();
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });


    }


    private void panelDesign() {
        upPanel.setBorder(new EmptyBorder(50, 100, 30, 100));
        downPanel.setBorder(new EmptyBorder(30, 100, 50, 100));

        upPanel.setBackground(Color.LIGHT_GRAY);
        downPanel.setBackground(Color.LIGHT_GRAY);

        upPanel.setLayout(new GridLayout(1, 1));
        upPanel.add(addMessageBtn);

        downPanel.setLayout(new GridLayout(1, 1));
        downPanel.add(exitButton);
    }

    private void buttonDesign() {
        exitButton.setFont(new Font("Arial", Font.PLAIN, 40));
        addMessageBtn.setFont(new Font("Arial", Font.PLAIN, 40));

        exitButton.setBackground(Color.WHITE);
        addMessageBtn.setBackground(Color.WHITE);

        exitButton.setFocusPainted(false);
        addMessageBtn.setFocusPainted(false);

        exitButton.setBorderPainted(false);
        addMessageBtn.setBorderPainted(false);
    }

    private void setUp() {
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(upPanel);
        mainPanel.add(downPanel);
        this.setTitle("Menu");
        this.add(mainPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        positionMiddleScreen();
    }

    private void positionMiddleScreen() {
        Dimension screenSize, frameSize;
        int x, y;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frameSize = getSize();
        x = (screenSize.width - frameSize.width) / 2;
        y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }
}