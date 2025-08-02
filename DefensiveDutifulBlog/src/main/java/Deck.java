import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// represents a collection of flashcards
public class Deck {
    private String title;
    private String description;
    private List<Flashcard> flashcards;
    private LocalDateTime lastUsed;

    // creates a new deck
    public Deck(String title, String description) {
        this.title = title;
        this.description = description;
        this.flashcards = new ArrayList<>(); // empty list for cards
        this.lastUsed = null;                // no last used date yet
    }

    // get the deck title
    public String getTitle() {
        return title;
    }

    // get the deck description
    public String getDescription() {
        return description;
    }

    // get the list of flashcards (a copy)
    public List<Flashcard> getFlashcards() {
        return new ArrayList<>(flashcards);
    }

    // get the last used date
    public LocalDateTime getLastUsed() {
        return lastUsed;
    }

    // set the deck title
    public void setTitle(String title) {
        this.title = title;
    }

    // set the deck description
    public void setDescription(String description) {
        this.description = description;
    }

    // set the last used date
    public void setLastUsed(LocalDateTime lastUsed) {
        this.lastUsed = lastUsed;
    }

    // adds a flashcard to the deck
    public void addFlashcard(Flashcard card) {
        if (card != null) {
            this.flashcards.add(card);
        }
    }

    // removes a flashcard from the deck
    public boolean removeFlashcard(Flashcard card) {
        return this.flashcards.remove(card);
    }

    // get the number of flashcards in the deck
    public int getNumberOfFlashcards() {
        return this.flashcards.size();
    }

    // string representation of the deck
    @Override
    public String toString() {
        String lastUsedStr = (lastUsed != null) ? lastUsed.toString() : "Never";
        return "Deck [Title: \"" + title + "\", Description: \"" + description +
               "\", Cards: " + flashcards.size() + ", Last Used: " + lastUsedStr + "]";
    }
}