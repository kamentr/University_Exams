import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class MessageInput extends JFrame {
    private JPanel InputPanel;

    private JTextField titleTextField;
    private JTextField contentTextField;
    private JTextField currentDateTextField;
    private JTextField validationDateTextField;
    private JTextField activationDateTextField;
    private JTextField reminderTextField;
    private JTextField messagePurposeTextField;

    private JButton submitButton;
    private JButton setRandomButton;
    private JButton backButton;
    private JButton displayButton;
    private JButton searchByDateButton;
    private JTextArea contentArea;
    private JLabel messagesLeftJLabel;
    private JButton searchForExpiredMessagesButton;
    private JLabel validationJLabel;

    private final String TITLE_TEXT_FIELD_HINT = "No longer than 25 characters";
    private final String CONTENT_TEXT_FIELD_HINT = "No longer than 250 characters";
    private final String DATE_TEXT_FIELD_HINT = "Use dd.MM.yyyy format";
    private final String TIME_TEXT_FIELD_HINT = "Use HH:mm format";
    private final String MESSAGE_PURPOSE_TEXT_FIELD_HINT = "No longer than 15 characters";

    private int messagesLeft;

    private ArrayList<Notebook> messages = new ArrayList<>();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public MessageInput(int numberOfMessages) {

        messagesLeft = numberOfMessages;

        setUp();

        setTextFieldsColor(Color.GRAY);

        initialiseTextFields();

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddMessage addMessage = new AddMessage();
                dispose();

            }
        });


        //Submit button action
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LocalDate currentDate = null;
                LocalDate validationDate = null;
                LocalDate activationDate = null;
                LocalTime reminder = null;

                Boolean isValid = true;

                String title = titleTextField.getText().trim();
                String text = contentTextField.getText().trim();
                String messagePurpose = messagePurposeTextField.getText().trim();

                if (title.length() > 25 || text.length() > 250 || messagePurpose.length() > 15) {

                    isValid = false;

                }

                try {

                    currentDate = LocalDate.parse(currentDateTextField.getText().trim(), formatter);
                    validationDate = LocalDate.parse(validationDateTextField.getText(), formatter);
                    activationDate = LocalDate.parse(activationDateTextField.getText().trim(), formatter);
                    reminder = LocalTime.parse(reminderTextField.getText().trim(), timeFormatter);

                } catch (Exception validationException) {

                    isValid = false;

                }


                if (isValid) {

                    validationJLabelSet(new Color(0, 153, 0), "Successful submit!");

                    Notebook notebook = new Notebook(title, text, currentDate, validationDate, activationDate, reminder, messagePurpose);
                    messages.add(notebook);

                    if (messagesLeft == 1) {

                        noMessagesLeft();

                    }

                    messagesLeftJLabel.setText("Messages left: " + --messagesLeft);

                } else {

                    validationJLabelSet(Color.red, "Invalid format");

                }
            }
        });


        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                messages.sort(Comparator.comparing(Notebook::getTitle));

                contentArea.setText("");

                for (Notebook notebook : messages) {

                    contentArea.append(notebook.toString() + System.lineSeparator());

                }

            }
        });


        searchByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                messages.sort(Comparator.comparing(Notebook::getTimeToActivate));

                LocalDate dateToSearch;

                try {

                    dateToSearch = LocalDate.parse(JOptionPane.showInputDialog("Write a date in a dd.mm.yyyy format").trim(), formatter); // Ask for user input

                } catch (Exception exception) {


                    JOptionPane.showMessageDialog(null, "Invalid format correct format is dd.mm.yyyy"); // Show error box if validation fails and reset the value of the input
                    dateToSearch = null;
                }


                if (dateToSearch != null) {
                    contentArea.setText("List of messages with " + dateToSearch + " as an activation date, sorted by the reminder time:\n" +
                            "-----------------------------------------------------------------------------------------------\n");

                    for (Notebook message : messages) {


                        if (message.getActivationDate().equals(dateToSearch) && message.getPurposeOfMessage().toLowerCase().contains("private")) {
                            contentArea.append(message.PrintAll() + System.lineSeparator());

                        }

                    }
                }
            }
        });


        setRandomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setTextFieldsToRandom();

            }
        });


        searchForExpiredMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LocalDate expirationDate = null;

                try {

                    expirationDate = LocalDate.parse(JOptionPane.showInputDialog("Write a date in a dd.mm.yyyy format").trim(), formatter); //Ask for user input

                } catch (Exception exception) {

                    JOptionPane.showMessageDialog(null, "Invalid format, correct format is dd.mm.yyyy");
                    expirationDate = null;
                }

                if (expirationDate != null) {

                    Comparator<Notebook> comparator = Comparator.comparing(x -> x.getCurrentDate());
                    comparator = comparator.thenComparing(x -> x.getTitle());

                    messages.sort(comparator);

                    contentArea.setText("");

                    for (Notebook message : messages) {

                        if (message.getValidationDate().isBefore(expirationDate)) {

                            long difference = ChronoUnit.DAYS.between(message.getValidationDate(), expirationDate);

                            contentArea.append(message.PrintAll() + "; Expired before: " + difference + " days" + System.lineSeparator());

                        }

                    }
                }
            }
        });


        //Focus listeners
        //When a text field gets focus the hint is removed and font color is set to black
        //When text field loses focus and it is empty the hint text is set again and the font color gets gray

        titleTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(titleTextField, TITLE_TEXT_FIELD_HINT);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                if (titleTextField.getText().equals("")) {

                    titleTextField.setForeground(Color.GRAY);
                    titleTextField.setText(TITLE_TEXT_FIELD_HINT);

                }

            }


        });

        contentTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(contentTextField, CONTENT_TEXT_FIELD_HINT);

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(contentTextField, CONTENT_TEXT_FIELD_HINT);

            }
        });

        currentDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(currentDateTextField, DATE_TEXT_FIELD_HINT);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(currentDateTextField, DATE_TEXT_FIELD_HINT);

            }
        });

        validationDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(validationDateTextField, DATE_TEXT_FIELD_HINT);

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(validationDateTextField, DATE_TEXT_FIELD_HINT);

            }
        });

        activationDateTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(activationDateTextField, DATE_TEXT_FIELD_HINT);

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(activationDateTextField, DATE_TEXT_FIELD_HINT);

            }
        });
        reminderTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(reminderTextField, TIME_TEXT_FIELD_HINT);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(reminderTextField, TIME_TEXT_FIELD_HINT);
            }
        });
        messagePurposeTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

                focusGain(messagePurposeTextField, MESSAGE_PURPOSE_TEXT_FIELD_HINT);

            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);

                focusLoss(messagePurposeTextField, MESSAGE_PURPOSE_TEXT_FIELD_HINT);
            }
        });
    }

    private void focusLoss(JTextField textField, String hint) {
        if (textField.getText().equals("")) {

            textField.setForeground(Color.GRAY);
            textField.setText(hint);

        }
    }

    private void focusGain(JTextField textField, String hint) {
        if (textField.getText().equals(hint)) {
            textField.setForeground(Color.black);
            textField.setText("");

        }
    }

    private void setTextFieldsToRandom() {
        setTextFieldsColor(Color.black);

        String[] RandomTitles = {"Meeting Event", "Lunch Break", "Everyone at the office", "Exam tomorrow", "Exam preparation"};
        String[] RandomTexts = {"At school", "Everyone has to wear uniforms", "Bring your rulers tomorrow", "You will receive further information in your emails", "There will be no school tomorrow"};
        String[] RandomDates = {"01.02.2000", "01.02.2000", "06.10.2001", "23.04.1999", "06.07.1999", "04.05.2001", "09.01.1999", "02.12.1999"};
        String[] RandomHours = {"15:30", "20:10", "15:00", "02:00", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30"};
        String[] RandomPurpose = {"Private", "To inform", "Grab attention"};

        Random random = new Random();

        titleTextField.setText(RandomTitles[random.nextInt(RandomTitles.length)]);
        contentTextField.setText(RandomTexts[random.nextInt(RandomTexts.length)]);
        currentDateTextField.setText(RandomDates[random.nextInt(RandomDates.length)]);
        validationDateTextField.setText(RandomDates[random.nextInt(RandomDates.length)]);
        activationDateTextField.setText(RandomDates[random.nextInt(RandomDates.length)]);
        reminderTextField.setText(RandomHours[random.nextInt(RandomHours.length)]);
        messagePurposeTextField.setText(RandomPurpose[random.nextInt(RandomPurpose.length)]);
    }

    private void noMessagesLeft() {
        submitButton.setBackground(Color.DARK_GRAY);
        submitButton.setEnabled(false);

        validationJLabel.setForeground(new Color(153, 0, 0));
        validationJLabel.setText("No messages left");
    }

    private void validationJLabelSet(Color color, String message) {
        validationJLabel.setForeground(color);
        validationJLabel.setText(message);
    }

    private void initialiseTextFields() {

        titleTextField.setText(TITLE_TEXT_FIELD_HINT);

        contentTextField.setText(CONTENT_TEXT_FIELD_HINT);

        currentDateTextField.setText(DATE_TEXT_FIELD_HINT);

        activationDateTextField.setText(DATE_TEXT_FIELD_HINT);

        validationDateTextField.setText(DATE_TEXT_FIELD_HINT);

        reminderTextField.setText(TIME_TEXT_FIELD_HINT);

        messagePurposeTextField.setText(MESSAGE_PURPOSE_TEXT_FIELD_HINT);

    }

    private void setUp() {
        this.add(InputPanel);
        this.setTitle("Message Input");
        this.setVisible(true);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        messagesLeftJLabel.setText("Messages left: " + messagesLeft);

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

    private void setTextFieldsColor(Color color) {

        titleTextField.setForeground(color);

        contentTextField.setForeground(color);

        currentDateTextField.setForeground(color);

        activationDateTextField.setForeground(color);

        validationDateTextField.setForeground(color);

        reminderTextField.setForeground(color);

        messagePurposeTextField.setForeground(color);
    }
}
