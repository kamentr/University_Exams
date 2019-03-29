import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Notebook {

    private String title;
    private String textToDisplay;
    private LocalDate currentDate;
    private LocalDate validationDate;
    private LocalDate activationDate;
    private LocalTime timeToActivate;
    private String purposeOfMessage;


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String getTitle() {
        return title;
    }

    public String getTextToDisplay() {
        return textToDisplay;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public LocalTime getTimeToActivate() {
        return timeToActivate;
    }

    public String getPurposeOfMessage() {
        return purposeOfMessage;
    }

    public Notebook(String title, String textToDisplay, LocalDate currentDate, LocalDate validationDate, LocalDate activationDate, LocalTime timeToActivate, String purposeOfMessage) {
        this.title = title;
        this.textToDisplay = textToDisplay;
        this.currentDate = currentDate;
        this.validationDate = validationDate;
        this.activationDate = activationDate;
        this.timeToActivate = timeToActivate;
        this.purposeOfMessage = purposeOfMessage;
    }

    public String PrintAll() {
        return  title + "; " +
                textToDisplay + "; " +
                currentDate.format(formatter) + "; " +
                validationDate.format(formatter) + "; " +
                activationDate.format(formatter) + "; " +
                timeToActivate + " h.; " +
                purposeOfMessage;
    }

    @Override
    public String toString() {
        return title + "; " +
                textToDisplay + "; " +
                currentDate.format(formatter) + "; " +
                validationDate.format(formatter);
    }
}
