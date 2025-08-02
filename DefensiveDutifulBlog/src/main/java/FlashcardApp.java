import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Main JavaFX application for the Flashcard App.
 * This class integrates the Flashcard, Deck, and UserStats classes
 * to provide a simple graphical user interface for studying flashcards.
 */
public class FlashcardApp extends Application {

    // --- Core Data Models ---
    private Deck currentDeck;       // The deck currently being studied
    private UserStats userStats;    // User's study statistics
    private List<Flashcard> shuffledCards; // Shuffled list of cards for the current session
    private int currentCardIndex;   // Index of the current card being displayed

    // --- UI Components ---
    private Label cardDisplayLabel; // Displays the front or back of the flashcard
    private Button flipButton;      // button to flip the card
    private Button correctButton;   // Button to mark card as correct
    private Button incorrectButton; // Button to mark card as incorrect
    private Button nextButton;      // Bbutton to move to the next card
    private Label statsLabel;       // Displays current study statistics

    private boolean showingFront;   // True if the front of the card is showing, false for back

    @Override
    public void start(Stage primaryStage) {
        // --- 1. Initialize Data Models ---
        initializeData();

        // --- 2. Setup UI Components ---
        cardDisplayLabel = new Label();
        cardDisplayLabel.setWrapText(true); // allow text to wrap
        cardDisplayLabel.setMinHeight(150); // Minimum height for the card display
        cardDisplayLabel.setMaxWidth(400);  // Max width for the card display
        cardDisplayLabel.setStyle("-fx-font-size: 24px; -fx-background-color: #f0f0f0; " +
                                  "-fx-border-color: #cccccc; -fx-border-width: 1px; " +
                                  "-fx-padding: 20px; -fx-alignment: center; -fx-background-radius: 10; -fx-border-radius: 10;");
        cardDisplayLabel.setAlignment(Pos.CENTER);

        flipButton = new Button("Flip Card");
        correctButton = new Button("Correct");
        incorrectButton = new Button("Incorrect");
        nextButton = new Button("Next Card");

        statsLabel = new Label("Stats: Decks: 0, Cards: 0, Correct: 0, Incorrect: 0, Acc: 0.00%");
        statsLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // --- 3. Setup Layouts ---
        VBox cardBox = new VBox(20, cardDisplayLabel, flipButton);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(20));

        HBox actionButtons = new HBox(15, correctButton, incorrectButton, nextButton);
        actionButtons.setAlignment(Pos.CENTER);
        actionButtons.setPadding(new Insets(10));

        VBox root = new VBox(20, cardBox, actionButtons, statsLabel);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #e0f2f7;"); // Light blue background

        // --- 4. Attach Event Handlers ---
        flipButton.setOnAction(e -> flipCard());
        correctButton.setOnAction(e -> markCard(true));
        incorrectButton.setOnAction(e -> markCard(false));
        nextButton.setOnAction(e -> showNextCard());

        // --- 5. Initial Display ---
        displayCurrentCard();
        updateStatsDisplay();

        // --- 6. Setup Scene and Stage ---
        Scene scene = new Scene(root, 600, 500); // Set initial window size
        primaryStage.setTitle("JavaFX Flashcard App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes the sample data (Flashcards, Deck) and UserStats.
     */
    private void initializeData() {
        // Create some sample flashcards
        Flashcard card1 = new Flashcard("What is the capital of France?", "Paris");
        Flashcard card2 = new Flashcard("What is 2 + 2?", "4");
        Flashcard card3 = new Flashcard("Who painted the Mona Lisa?", "Leonardo da Vinci");
        Flashcard card4 = new Flashcard("What is the largest planet in our solar system?", "Jupiter");
        Flashcard card5 = new Flashcard("What is the chemical symbol for water?", "H2O");
        Flashcard card6 = new Flashcard("What is the square root of 81?", "9");

        // Create a deck and add flashcards
        currentDeck = new Deck("General Knowledge", "A mix of various facts.");
        currentDeck.addFlashcard(card1);
        currentDeck.addFlashcard(card2);
        currentDeck.addFlashcard(card3);
        currentDeck.addFlashcard(card4);
        currentDeck.addFlashcard(card5);
        currentDeck.addFlashcard(card6);

        // Shuffle the cards for the study session
        shuffledCards = new ArrayList<>(currentDeck.getFlashcards());
        Collections.shuffle(shuffledCards, new Random()); // Use Random for reproducible shuffling if needed
        currentCardIndex = 0;
        showingFront = true;

        // Initialize user statistics
        userStats = new UserStats();
    }

    /**
     * Displays the current flashcard (front or back) on the UI.
     */
    private void displayCurrentCard() {
        if (shuffledCards.isEmpty()) {
            cardDisplayLabel.setText("No cards in this deck!");
            setButtonsDisabled(true);
            return;
        }

        if (currentCardIndex < shuffledCards.size()) {
            Flashcard currentCard = shuffledCards.get(currentCardIndex);
            if (showingFront) {
                cardDisplayLabel.setText(currentCard.getFrontText());
                flipButton.setText("Flip to Rear");
            } else {
                cardDisplayLabel.setText(currentCard.getRearText());
                flipButton.setText("Flip to Front");
            }
            setButtonsDisabled(false); // Enable buttons when a card is displayed
        } else {
            // End of deck
            cardDisplayLabel.setText("Deck completed! Review your stats.");
            setButtonsDisabled(true);
            flipButton.setText("No More Cards");
            // Optionally, restart the deck or show a summary screen
        }
    }

    /**
     * Toggles between showing the front and back of the current flashcard.
     */
    private void flipCard() {
        showingFront = !showingFront;
        displayCurrentCard();
    }

    /**
     * Marks the current card as correct or incorrect and records the session.
     * Then moves to the next card.
     * @param isCorrect True if the card was answered correctly, false otherwise.
     */
    private void markCard(boolean isCorrect) {
        if (currentCardIndex >= shuffledCards.size()) {
            return; // No more cards to mark
        }

        Flashcard card = shuffledCards.get(currentCardIndex);
        card.setLastMarkedCorrect(isCorrect); // Update the card's individual status

        List<Flashcard> correctList = new ArrayList<>();
        List<Flashcard> incorrectList = new ArrayList<>();

        if (isCorrect) {
            correctList.add(card);
        } else {
            incorrectList.add(card);
        }

        // Record this single card's outcome in user stats
        // Note: For simplicity, we record each card individually here.
        // In a real app, you might collect all results of a session and record once.
        userStats.recordStudySession(null, correctList, incorrectList); // Pass null for deck to avoid re-incrementing deck count per card
        updateStatsDisplay();
        showNextCard();
    }

    /**
     * Advances to the next card in the shuffled deck.
     */
    private void showNextCard() {
        currentCardIndex++;
        showingFront = true; // Always show front of new card
        displayCurrentCard();
    }

    /**
     * Updates the statistics display label with current user stats.
     */
    private void updateStatsDisplay() {
        statsLabel.setText(
            String.format("Stats: Decks: %d, Cards: %d, Correct: %d, Incorrect: %d, Accuracy: %.2f%%",
                userStats.getTotalDecksStudied(),
                userStats.getTotalCardsReviewed(),
                userStats.getTotalCorrect(),
                userStats.getTotalIncorrect(),
                userStats.getAccuracy() * 100
            )
        );
    }

    /**
     * Helper method to enable/disable action buttons.
     * @param disable True to disable, false to enable.
     */
    private void setButtonsDisabled(boolean disable) {
        flipButton.setDisable(disable);
        correctButton.setDisable(disable);
        incorrectButton.setDisable(disable);
        nextButton.setDisable(disable);
    }

    public static void main(String[] args) {
        launch(args);
    }
}