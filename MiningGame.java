import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a JavaFX-based game where the player controls a drill to mine resources
 * in an assets.assets.underground scene. The game includes elements like fuel, gems, and lava, along with gravity effects and boundary checks.
 */
public class MiningGame extends Background{
    /**
     * Animation timer to manage the game's continuous update loop.
     */
    protected static AnimationTimer timer;
    /**
     * ImageView representing the player's assets.assets.drill.
     */
    protected static ImageView drill;
    /**
     * Speed of the assets.assets.drill movement.
     */
    protected static double speed = 50.0;

    /**
     * Initial x-coordinate of the assets.assets.drill.
     */
    protected static double drillX = 800;
    /**
     * Initial y-coordinate of the assets.assets.drill.
     */
    protected static double drillY = 50;
    /**
     * Set of coordinates representing already painted areas in the assets.assets.underground scene.
     */
    protected static Set<String> paintedAreas = new HashSet<>();
    /**
     * Map of gem locations based on their coordinates.
     */
    protected static Map<String, Gems> gemLocations = new HashMap<>();
    /**
     * Last recorded key press time in milliseconds.
     */
    protected static long lastKeyPressTime = 0;
    /**
     * Delay time for gravity effects after key presses.
     */
    protected static long gravityDelay = 700;


    /**
     * Initializes the game playground, setting up text displays, handling keyboard movements, and starting the animation timer.
     *
     * @param stage the primary stage for this JavaFX application.
     */
    public static void playGround(Stage stage) {
        // Set up text displays for fuel, haul, and money
        Text fuelText = new Text(10, 30, "Fuel: " + Fuel);
        fuelText.setFill(Color.WHITE);
        fuelText.setFont(Font.font("Verdana",20));
        root.getChildren().add(fuelText);

        Text haulText = new Text(10, 60, "Haul: " + Haul);
        haulText.setFill(Color.WHITE);
        haulText.setFont(Font.font("Verdana",20));
        root.getChildren().add(haulText);

        Text moneyText = new Text(10, 90, "Money: " + Money);
        moneyText.setFill(Color.WHITE);
        moneyText.setFont(Font.font("Verdana",20));
        root.getChildren().add(moneyText);

        // Drill images for different directions
        Image drillImageLeft = new Image("/assets/drill/drill_01.png");
        Image drillImageRight = new Image("/assets/drill/drill_60.png");
        Image drillImageUp = new Image("/assets/drill/drill_24.png");
        Image drillImageDown = new Image("/assets/drill/drill_40.png");

        // Initialize the assets.assets.drill ImageView
        drill = new ImageView(drillImageLeft);
        drill.setFitWidth(50);
        drill.setFitHeight(60);
        drill.setX(drillX); // Initial x-axis coordinate
        drill.setY(drillY); // Initial y-axis coordinate
        root.getChildren().add(drill);

        // Populate the gemLocations map with used gem coordinates
        for (Gems gem : UsedGems) {
            String coordinate = gem.getxCoordinate() + "," + gem.getyCoordinate();
            gemLocations.put(coordinate, gem);
        }

        // Create the animation timer for continuous game updates
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis(); // Get the current system time
                boolean isDrillMoving = (currentTime- lastKeyPressTime) < 500; // Check if the assets.assets.drill is moving

                checkGravity(drillImageUp); // Apply gravity effects if necessary

                // Manage fuel consumption based on assets.assets.drill movement
                if(isDrillMoving){
                    Fuel -= 1.0;
                }else{
                    Fuel -= 0.5;
                }

                // Check for game over due to fuel exhaustion
                if(Fuel <= 0.0){
                    GameOverScreen(stage, "green");
                    timer.stop();
                }

                // Update fuel text display
                fuelText.setText("Fuel: " + Fuel);




                // Boundary checks to ensure the assets.assets.drill doesn't go out of bounds
                if (drill.getX() < 50) {
                    drill.setX(50);
                }
                if (drill.getX() > 800) {
                    drill.setX(800);
                }
                if (drill.getY() < 0) {
                    drill.setY(0);
                }
                if (drill.getY() > 800) {
                    drill.setY(800);
                }

                // Handle interactions with gems, lava, and painted areas
                String coordinate = drill.getX() + "," + drill.getY();// collect painted box for not trying to paint it again
                if (!paintedAreas.contains(coordinate) && drill.getY() >= 100 && !stoneCoordinates.contains(coordinate) ) {// Check if the area is not yet painted and area is not contain stone
                    if (gemLocations.containsKey(coordinate)) { // If it's a gem, collect it
                        Gems foundGem = gemLocations.get(coordinate);
                        Money += foundGem.getWorth();
                        Haul += foundGem.getWeight();
                        gemLocations.remove(coordinate);

                        // Update text displays for haul and money
                        haulText.setText("Haul: " + Haul);
                        moneyText.setText("Money: " + Money);
                    } else if (lavaCoordinates.contains(coordinate)) {
                        GameOverScreen(stage,"red");
                        timer.stop();
                    }

                    // Paint the area with a brown square to indicate it's mined
                    Rectangle brownSquare = new Rectangle(drill.getX(), drill.getY(), 50, 50);
                    brownSquare.setFill(Color.SIENNA);
                    root.getChildren().add(brownSquare);
                    paintedAreas.add(coordinate); // Mark as painted
                }
                root.getChildren().remove(drill); // Remove and re-add to keep the assets.assets.drill on top
                root.getChildren().add(drill);

                if(gemLocations.isEmpty()){
                    GameOverScreen(stage,"yellow");
                    timer.stop();
                }
            }

        };
        timer.start(); // start animation

        // Add keyboard event handling for movement
        scene.setOnKeyPressed(e -> handleKeyPress(e, drillImageLeft, drillImageRight, drillImageDown, drillImageUp));
    }

    /**
     * Handles key press events for controlling the assets.assets.drill's movement in different directions.
     *
     * @param event           the KeyEvent representing the key press.
     * @param drillImageLeft  the image used when the assets.assets.drill moves left.
     * @param drillImageRight the image used when the assets.assets.drill moves right.
     * @param drillImageDown  the image used when the assets.assets.drill moves downward.
     * @param drillImageUp    the image used when the assets.assets.drill moves upward.
     */
    private static void handleKeyPress(KeyEvent event, Image drillImageLeft, Image drillImageRight, Image drillImageDown, Image drillImageUp) {
        lastKeyPressTime = System.currentTimeMillis(); // Record key press time

        // Move the assets.assets.drill based on key press
        if (event.getCode() == KeyCode.RIGHT) {
            if(stoneCoordinates.contains((drill.getX()+speed) + "," + drill.getY())){
                return;
            }
            drill.setImage(drillImageRight);
            drill.setX(drill.getX() + speed);
        } else if (event.getCode() == KeyCode.LEFT) {
            if(stoneCoordinates.contains((drill.getX()-speed) + "," + drill.getY())){
                return;
            }
            drill.setImage(drillImageLeft);
            drill.setX(drill.getX() - speed);
        } else if (event.getCode() == KeyCode.DOWN) {
            if(stoneCoordinates.contains(drill.getX() + "," + (drill.getY()+speed))){
                return;
            }
            drill.setImage(drillImageDown);
            drill.setY(drill.getY() + speed);
        } else if (event.getCode() == KeyCode.UP) {// Handle upward movement with additional checks
            if(stoneCoordinates.contains(drill.getX() + "," + (drill.getY()-speed))){
                return;
            }
            if(drill.getY() == drillY) {
                drill.setImage(drillImageUp);
                drill.setY(drill.getY() - speed);
            } else if (drill.getY() == 100.0) {
                drill.setImage(drillImageUp);
                drill.setY(drill.getY() - speed);
            } else if (paintedAreas.contains(drill.getX() + "," + ( drill.getY() - speed))) {
                drill.setImage(drillImageUp);
                drill.setY(drill.getY() - speed);
            }
            else if(!paintedAreas.contains(drill.getX()+ "," + (drill.getY()- speed))){
                drill.setImage(drillImageUp);
            }
        }
    }

    /**
     * Displays the game over screen with the specified color, indicating the reason for game over (e.g., fuel exhaustion or lava hit).
     *
     * @param stage the primary stage for the game.
     * @param color the color indicating the reason for game over ("green" for fuel exhaustion, "red" for lava hit).
     */
    public static void GameOverScreen(Stage stage,String color){
        Pane root = new Pane(); // New root pane for the game over screen
        if (color.equals("green")){
            Rectangle backGround = new Rectangle(900, 900, Color.GREEN); // Green background for fuel exhaustion
            Text gameOverText = new Text(300,300,"GAME OVER");
            Text collectedMoney = new Text(150,400,"Collected Money: " + Money);
            gameOverText.setFont(Font.font("Verdana", 50));
            gameOverText.setFill(Color.WHITE);
            collectedMoney.setFont(Font.font("Verdana", 50));
            collectedMoney.setFill(Color.WHITE);

            root.getChildren().add(backGround);
            root.getChildren().add(gameOverText);
            root.getChildren().add(collectedMoney);
        } else if (color.equals("red")) {
            Rectangle backGround = new Rectangle(900, 900, Color.DARKRED);  // Dark red for lava hit
            Text gameOverText = new Text(290,425,"GAME OVER");
            gameOverText.setFont(Font.font("Verdana", 50));
            gameOverText.setFill(Color.WHITE);

            root.getChildren().add(backGround);
            root.getChildren().add(gameOverText);
        }else if(color.equals("yellow")){
            Rectangle backGround = new Rectangle(900,900,Color.GOLD);
            Text gameOverText = new Text(300,375,"Well Done!");
            Text gameOverText2 = new Text(125,475,"You collected all the gems!");
            gameOverText.setFont(Font.font("Verdana",50));
            gameOverText.setFill(Color.WHITE);
            gameOverText2.setFont(Font.font("Verdana",50));
            gameOverText2.setFill(Color.WHITE);

            root.getChildren().add(backGround);
            root.getChildren().add(gameOverText);
            root.getChildren().add(gameOverText2);

        }

        Scene gameOverScene = new Scene(root, 900, 900); // New game over scene
        stage.setScene(gameOverScene); // Set the game over scene
        stage.show(); // Display the game over screen
    }

    /**
     * Checks and applies gravity effects to the assets.assets.drill based on key press delay and existing painted areas.
     *
     * @param drillImageUp the image used when the assets.assets.drill moves downward.
     */
    private static void checkGravity(Image drillImageUp) {
        long currentTime = System.currentTimeMillis(); // Get the current system time
        String belowCoordinate = drill.getX() + "," + (drill.getY()+ speed); // Coordinate below the assets.assets.drill
        if((currentTime - lastKeyPressTime > gravityDelay) && (drill.getY() == 0.0 || paintedAreas.contains(belowCoordinate))) {  // This if block check if the assets.assets.drill try to fly in the earth surface, gravity will affect the assets.assets.drill
            drill.setImage(drillImageUp); // Change assets.assets.drill image to downward
            drill.setY(drill.getY() + 50.0); // Move the assets.assets.drill downward
        }
    }
}
