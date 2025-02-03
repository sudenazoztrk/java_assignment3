import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.*;

/**
 * This class represents the graphical scene for a mining game.
 * It sets up the environment, including soil, stone, gems, and lava tiles,
 * to create an assets.assets.underground landscape for the game.
 */
public class Background extends Application{
    /**
     * Root pane for the JavaFX scene.
     */
    protected static Pane root = new Pane();
    /**
     * The JavaFX scene that contains all graphical elements.
     */
    protected static Scene scene;
    /**
     * List of gems that have been placed in the scene.
     */
    protected static List<Gems> UsedGems = new ArrayList<>();
    /**
     * Set of coordinates where lava tiles have been placed.
     */
    protected static Set<String> lavaCoordinates = new HashSet<>();
    /**
     * Player's fuel reserve.
     */
    protected static double Fuel = 10000.0;
    /**
     * Value of the player's haul (accumulated gems).
     */
    protected static double Haul;
    /**
     * Player's total money.
     */
    protected static double Money;
    /**
     * A temporary gem object used for random gem placement.
     */
    protected static Gems randomGemObject;
    /**
     * Set of coordinates where stones have been placed.
     */
    protected static Set<String> stoneCoordinates = new HashSet<>();
    /**
     * Set of coordinates where gems have been placed.
     */
    protected static Set<String> gemsCoordinates = new HashSet<>();

    /**
     * Initializes and starts the JavaFX stage with the game scene.
     *
     * @param stage the primary stage for this JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        //Create the sky background
        Rectangle sky = new Rectangle(900, 110, Color.DARKBLUE); //add sky for background
        root.getChildren().add(sky);

        int tileSize = 50; // Block size for each item in the game

        // Set up the soil, stone, gems, and lava
        soilTiling(tileSize);
        gemAndLavaTiling(tileSize);
        stoneTiling(tileSize);


        // Create the scene with specified dimensions
        scene = new Scene(root, 900, 900); // scene : 900-900
        stage.setTitle("HU-Load");
        stage.setScene(scene);
        stage.show();

        // Start the game playground
        MiningGame.playGround(stage);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command-line arguments for the JavaFX application.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Places gems and lava tiles in the assets.assets.underground scene with random placement.
     *
     * @param tileSize the size of each tile.
     */
    public static void gemAndLavaTiling(int tileSize) {
        // Upload images
        Image lava1 = new Image("/assets/underground/lava_01.png");
        Image lava2 = new Image("/assets/underground/lava_02.png");
        Image lava3 = new Image("/assets/underground/lava_03.png");
        Image amazonite = new Image("/assets/underground/valuable_amazonite.png");
        Image bronzium = new Image("/assets/underground/valuable_bronzium.png");
        Image diamond = new Image("assets/underground/valuable_diamond.png");
        Image einsteinium = new Image("assets/underground/valuable_einsteinium.png");
        Image emerald = new Image("assets/underground/valuable_emerald.png");
        Image goldium = new Image("assets/underground/valuable_goldium.png");
        Image ironium = new Image("assets/underground/valuable_ironium.png");
        Image platinum = new Image("assets/underground/valuable_platinum.png");
        Image ruby = new Image("assets/underground/valuable_ruby.png");
        Image silverium = new Image("assets/underground/valuable_silverium.png");


        Image[] lavaImages = {lava1, lava2, lava3}; // Array of lava images, we will use this to randomly select a lava image to put assets.assets.underground
        Image[] gemImages = {amazonite, bronzium, diamond, einsteinium, emerald, goldium, ironium, platinum, ruby, silverium}; // Array of gem images, we will use this to randomly select a gem to put assets.assets.underground

        Random random = new Random();
        double GemPercent = 0.07;  // Probability of placing a gem
        double LavaPercent = 0.07;   // Probability of placing a gem

        // Iterate through x and y coordinates to place gems and lava
        for (int x = 50; x < 800; x += tileSize) {
            for (int y = 200; y < 800; y += tileSize) {
                if (random.nextDouble() < GemPercent) {
                    int randomIndex = random.nextInt(gemImages.length); // Randomly select a gem
                    if (gemImages[randomIndex] == amazonite) {
                        randomGemObject = new Gems( 500000, 120);
                    } else if (gemImages[randomIndex] == bronzium) {
                        randomGemObject = new Gems( 60, 10);
                    } else if (gemImages[randomIndex] == diamond) {
                        randomGemObject = new Gems( 100000, 100);
                    } else if (gemImages[randomIndex] == einsteinium) {
                        randomGemObject = new Gems( 2000, 40);
                    } else if (gemImages[randomIndex] == emerald) {
                        randomGemObject = new Gems( 5000, 60);
                    } else if (gemImages[randomIndex] == goldium) {
                        randomGemObject = new Gems( 250, 20);
                    } else if (gemImages[randomIndex] == ironium) {
                        randomGemObject = new Gems( 30, 10);
                    } else if (gemImages[randomIndex] == platinum) {
                        randomGemObject = new Gems( 750, 30);
                    } else if (gemImages[randomIndex] == ruby) {
                        randomGemObject = new Gems( 20000, 80);
                    } else if (gemImages[randomIndex] == silverium) {
                        randomGemObject = new Gems( 100, 10);
                    }
                    // Set gem coordinates and add to list of used gems
                    randomGemObject.setxCoordinate(x);
                    randomGemObject.setyCoordinate(y);
                    UsedGems.add(randomGemObject);
                    String coordinate = (double) x + "," + (double) y;
                    gemsCoordinates.add(coordinate);

                    // Create and place gem tile
                    ImageView gemTile = new ImageView(gemImages[randomIndex]);
                    gemTile.setFitWidth(tileSize);
                    gemTile.setFitHeight(tileSize);
                    gemTile.setX(x);
                    gemTile.setY(y);
                    root.getChildren().add(gemTile);

                } else if (random.nextDouble() < LavaPercent) {
                    int randomIndex = random.nextInt(lavaImages.length); // Randomly select a lava tile
                    Image randomLava = lavaImages[randomIndex];
                    String coordinate = (double) x + "," + (double) y;
                    lavaCoordinates.add(coordinate); // Store lava coordinates in the set

                    // Create and place lava tile
                    ImageView LavaTile = new ImageView(randomLava);
                    LavaTile.setFitWidth(tileSize);
                    LavaTile.setFitHeight(tileSize);
                    LavaTile.setX(x);
                    LavaTile.setY(y);
                    root.getChildren().add(LavaTile);
                }
            }
        }
    }

    /**
     * Places soil tiles in the scene to represent the assets.assets.underground soil.
     *
     * @param tileSize the size of each soil tile.
     */
    public static void soilTiling(int tileSize) {
        // Upload soil images
        Image soilImage = new Image("/assets/underground/soil_01.png");
        Image TopSoilImage = new Image("/assets/underground/top_01.png");

        // Create top soil tiles
        for (int x = 0; x < 900; x += tileSize) {
            ImageView TopSoilTile = new ImageView(TopSoilImage);
            TopSoilTile.setFitWidth(tileSize);
            TopSoilTile.setFitHeight(tileSize);
            TopSoilTile.setX(x);
            TopSoilTile.setY(100);
            root.getChildren().add(TopSoilTile);
        }

        int soilStartY = 150;

        // Create assets.assets.underground soil tiles
        for (int x = 50; x < 850; x += tileSize) {
            for (int y = soilStartY; y < 850; y += tileSize) {
                ImageView soilTile = new ImageView(soilImage);
                soilTile.setFitWidth(tileSize);
                soilTile.setFitHeight(tileSize);
                soilTile.setX(x);
                soilTile.setY(y);
                root.getChildren().add(soilTile);
            }
        }
    }

    /**
     * Places stone tiles at the scene boundaries to represent obstacles.
     *
     * @param tileSize the size of each stone tile.
     */
    public static void stoneTiling(int tileSize) {
        // Upload stone images
        Image stone1 = new Image("/assets/underground/obstacle_01.png");
        Image stone2 = new Image("/assets/underground/obstacle_02.png");
        Image stone3 = new Image("/assets/underground/obstacle_03.png");

        Image[] stoneImages = {stone1, stone2, stone3}; // Array of stone images, we will use this to randomly select a stone image to put boundries

        // Place stones along the left boundary
        for (int y = 150; y < 900; y += tileSize) {
            Random random = new Random();
            int randomIndex = random.nextInt(stoneImages.length);
            Image randomStone = stoneImages[randomIndex]; //pick a random stone image

            ImageView stoneTile = new ImageView(randomStone);
            stoneTile.setFitWidth(tileSize);
            stoneTile.setFitHeight(tileSize);
            stoneTile.setX(0);
            stoneTile.setY(y);
            root.getChildren().add(stoneTile);
        }

        // Place stones along the bottom boundary
        for (int x = 50; x < 850; x += tileSize) {
            Random random = new Random();
            int randomIndex = random.nextInt(stoneImages.length);
            Image randomStone = stoneImages[randomIndex]; //pick a random stone image

            ImageView stoneTile = new ImageView(randomStone);
            stoneTile.setFitWidth(tileSize);
            stoneTile.setFitHeight(tileSize);
            stoneTile.setX(x);
            stoneTile.setY(850);
            root.getChildren().add(stoneTile);
        }

        // Place stones along the right boundary
        for (int y = 150; y < 900; y += tileSize) {
            Random random = new Random();
            int randomIndex = random.nextInt(stoneImages.length);
            Image randomStone = stoneImages[randomIndex]; // Pick a random stone image

            ImageView stoneTile = new ImageView(randomStone);
            stoneTile.setFitWidth(tileSize);
            stoneTile.setFitHeight(tileSize);
            stoneTile.setX(850);
            stoneTile.setY(y);
            root.getChildren().add(stoneTile);
        }

        Random random = new Random();
        double StonePercent = 0.03;  // Stone will be less than gems and lavas

        for (int x = 50; x < 800; x += tileSize) {
            for (int y = 200; y < 800; y += tileSize) {
                String coordinate = (double) x + "," + (double) y;
                if(!gemsCoordinates.contains(coordinate) && !lavaCoordinates.contains(coordinate)){ // If the coordinate does not fill with gem or lava you can add stone
                    if (random.nextDouble() < StonePercent){
                        ImageView stoneTile = new ImageView(stone3);
                        stoneTile.setFitWidth(tileSize);
                        stoneTile.setFitHeight(tileSize);
                        stoneTile.setX(x);
                        stoneTile.setY(y);
                        root.getChildren().add(stoneTile);
                        stoneCoordinates.add(coordinate);
                    }
                }
            }
        }
    }
}
