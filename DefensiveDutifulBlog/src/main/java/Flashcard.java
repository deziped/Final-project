import java.time.LocalDate;

// represents a single flashcard
public class Flashcard {
    private String frontText;
    private String rearText;
    private LocalDate createdOn;
    private boolean lastTimeCorrect;

    // creates a new flashcard
    public Flashcard(String frontText, String backText) {
        this.frontText = frontText;
        this.rearText = backText;
        this.createdOn = LocalDate.now(); // set creation date to today
        this.lastTimeCorrect = false;     // default to incorrect
    }

    // get the front text
    public String getFrontText() {
        return frontText;
    }

    // get the back text
    public String getRearText() {
        return rearText;
    }

    // get the creation date
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    // check if last answered correctly
    public boolean isLastMarkedCorrect() {
        return lastTimeCorrect;
    }

    // set the front text
    public void setFrontText(String text) {
        this.frontText = text;
    }

    // set the back text
    public void setRearText(String text) {
        this.rearText = text;
    }

    // set if the card was last correct
    public void setLastMarkedCorrect(boolean correct) {
        this.lastTimeCorrect = correct;
    }

    // string representation of the flashcard
    @Override
    public String toString() {
        return "Flashcard [Front: \"" + frontText + "\", Rear: \"" + rearText +
               "\", Created On: " + createdOn + ", Last Correct: " + lastTimeCorrect + "]";
    }
}
