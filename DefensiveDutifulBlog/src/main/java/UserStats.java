import java.util.ArrayList;
import java.util.List;

// tracks user's study statistics
public class UserStats {
    private int totalDecksStudied;
    private int totalCardsReviewed;
    private int totalCorrect;
    private int totalIncorrect;
    private List<Flashcard> mostMissedCards;

    // creates new user stats, all starting at zero
    public UserStats() {
        this.totalDecksStudied = 0;
        this.totalCardsReviewed = 0;
        this.totalCorrect = 0;
        this.totalIncorrect = 0;
        this.mostMissedCards = new ArrayList<>();
    }

    // get total decks studied
    public int getTotalDecksStudied() {
        return totalDecksStudied;
    }

    // get total cards reviewed
    public int getTotalCardsReviewed() {
        return totalCardsReviewed;
    }

    // get total correct answers
    public int getTotalCorrect() {
        return totalCorrect;
    }

    // get total incorrect answers
    public int getTotalIncorrect() {
        return totalIncorrect;
    }

    // get list of most missed cards (a copy)
    public List<Flashcard> getMostMissedCards() {
        return new ArrayList<>(mostMissedCards);
    }

    // records a study session's results
    public void recordStudySession(Deck deck, List<Flashcard> correctCards, List<Flashcard> incorrectCards) {
        if (deck != null) {
            this.totalDecksStudied++;
            deck.setLastUsed(java.time.LocalDateTime.now());
        }

        if (correctCards != null) {
            this.totalCorrect += correctCards.size();
            this.totalCardsReviewed += correctCards.size();
        }

        if (incorrectCards != null) {
            this.totalIncorrect += incorrectCards.size();
            this.totalCardsReviewed += incorrectCards.size();
            for (Flashcard card : incorrectCards) {
                if (!this.mostMissedCards.contains(card)) {
                    this.mostMissedCards.add(card);
                }
            }
        }
    }

    // calculates accuracy percentage
    public double getAccuracy() {
        if (totalCardsReviewed == 0) {
            return 0.0;
        }
        return (double) totalCorrect / totalCardsReviewed;
    }

    // gets a full string of all stats
    public String getTotalStats() {
        return "--- User Study Statistics ---\n" +
               "Total Decks Studied: " + totalDecksStudied + "\n" +
               "Total Cards Reviewed: " + totalCardsReviewed + "\n" +
               "Total Correct: " + totalCorrect + "\n" +
               "Total Incorrect: " + totalIncorrect + "\n" +
               String.format("Accuracy: %.2f%%\n", getAccuracy() * 100) +
               "Most Missed Cards (" + mostMissedCards.size() + " unique cards):\n" +
               (mostMissedCards.isEmpty() ? "  none yet!" : formatMissedCards());
    }

    // helper to format missed cards for display
    private String formatMissedCards() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mostMissedCards.size(); i++) {
            sb.append("  - ").append(mostMissedCards.get(i).getFrontText());
            if (i < mostMissedCards.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}