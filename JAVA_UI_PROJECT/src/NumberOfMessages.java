import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberOfMessages extends JFrame {
    private JPanel numberOfMessagesForm;
    private JButton nextButton;
    private JButton backButton;
    private JLabel hintJLabel;
    private JSpinner numberSelector;
    private int numberOfMessages;


    public NumberOfMessages() {

        setUp();

        //Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMessage addMessage = new AddMessage();
                dispose();
            }
        });

        //Next button action
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (((int) numberSelector.getValue() > 100) || (int) (numberSelector.getValue()) <= 0) {

                    JOptionPane.showMessageDialog(new NumberOfMessages(), "The number must be between 1 an 100");

                } else {

                    numberOfMessages = (int) numberSelector.getValue();
                    MessageInput messageInput = new MessageInput(numberOfMessages);
                    dispose();

                }

            }
        });
    }

    private void setUp() {

        //this.setLocationRelativeTo(null);
        this.add(numberOfMessagesForm);
        this.setTitle("Set number of messages");
        this.setVisible(true);
        this.setSize(500, 350);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hintJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        numberSelector.setValue(1);

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



    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }
}
