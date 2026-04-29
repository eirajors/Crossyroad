package no.uib.inf101.crossyroad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.crossyroad.model.obstacles.Car;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class GameView extends JPanel {
    //fjerne outermargin
    private IconTheme imageIcons; 
    private final int OUTERMARGIN = 0;
    private final int INNERMARGIN = 0;
    private ViewableGameModel model; 
    private final GridDimension dimension; 

   
    // konstruktør
    public GameView(ViewableGameModel model) {
        this.model = model; 
        this.dimension = model.getDimension();

        this.imageIcons = new IconTheme();      
    }

    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (model.getTiles() == null || model.getDimension() == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        drawGrid(g2);
        drawCars(g2);

        if (model.getGameState() == GameState.ACTIVE) {
            drawActiveGame(g2);
        }

        if (model.getGameState() == GameState.HOMESCREEN) {
            drawHomeScreen(g2);
        }

        if (model.getGameState() == GameState.PAUSE) {
            drawPauseScreen(g2);
        }

        if (model.getGameState() == GameState.GAMEOVER) {
            drawGameOver(g2);
        }
    }

    private void drawGrid(Graphics2D g2) {

        double width = getWidth() - (OUTERMARGIN * 2);
        double height = getHeight() - (OUTERMARGIN * 2);

        double x = OUTERMARGIN;
        double y = OUTERMARGIN;

        Rectangle2D bakgrunn = new Rectangle2D.Double(x, y, width, height);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(bakgrunn, model.getDimension(), INNERMARGIN);

        drawCellsImages(g2, converter, model.getBackgroundTiles());
        drawCellsImages(g2, converter, model.getTiles());
    }

   
    private void drawCellsImages(Graphics2D g2, CellPositionToPixelConverter converter, Iterable<GridCell> tiles) {
        for (GridCell cell : tiles) {
            
            CellPosition cellPos = cell.pos();
            Rectangle2D cellArea = converter.getBoundsOfCell(cellPos);

            Character pixelSymbol = cell.symbol();

            Image pixelArt = imageIcons.getCellImage(pixelSymbol);
            if (pixelArt == null) continue;

            g2.drawImage(pixelArt, (int) cellArea.getX(),(int) cellArea.getY(), (int)cellArea.getWidth(), (int)cellArea.getWidth(), this);
        }
    }

    public Dimension getPreferredSize() {
        int cellSize = 40;
        int width = dimension.cols() * cellSize + OUTERMARGIN * 2;
        int height = dimension.rows() * cellSize + OUTERMARGIN * 2;

        return new Dimension(width, height);
    }

    private void drawCars(Graphics2D g2) {
        int carWidth = (getWidth() - (OUTERMARGIN * 2))/ dimension.cols();
        int carHeight = (getHeight() - (OUTERMARGIN * 2)) / dimension.rows();

        for (Car car : model.getCars()) {
            if (car.getSpeed() > 0) {
                Image carImage = imageIcons.getCellImage('l');
                g2.drawImage(carImage, (int) car.getX(), (int) car.getY(), carWidth, carHeight, this);
            }
            else {
                Image carImage = imageIcons.getCellImage('n');
                g2.drawImage(carImage, (int) car.getX(), (int) car.getY(), carWidth, carHeight, this);
            }
        }
    }

    private void drawActiveGame(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
        String message = "Level: " + String.valueOf(model.getLevel());
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, (textWidth / 2), this.getHeight() /model.getDimension().cols());

        Image pause = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/pause.png")).getImage();
        g2.drawImage(pause, (this.getWidth() - (this.getWidth()/dimension.cols()))-10, (this.getHeight() / dimension.rows()), 
            this.getWidth() / dimension.cols(), this.getHeight() / dimension.rows(), this);
    }

    private void drawHomeScreen(Graphics2D g2) {
        Image logo = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/logo.png")).getImage();
        Image playerGif = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/playerGif.gif")).getImage();
        Image pressGif = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/pressUp.gif")).getImage();
        Image backgroundHS = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/streetHomescreen.png")).getImage();


        g2.drawImage(backgroundHS, 0, 0, this.getWidth(), this.getHeight(), this);

        g2.drawImage(playerGif, this.getWidth()/3, 50, 315, 315, this);
        g2.drawImage(logo, this.getWidth()/2-175, 300, 350, 150, this);


        g2.drawImage(pressGif, getWidth()/2-150, getHeight()/2 + 180, 270, 70, this);

    }

    private void drawPauseScreen(Graphics2D g2) {
        Image playerGif = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/playerGif.gif")).getImage();

        g2.setColor(new Color(255, 255, 204, 100));
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.drawImage(playerGif, 20, -40, this.getWidth() - 20, this.getHeight()- 20, this);
        
        Image pause = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/resume.png")).getImage();
        g2.drawImage(pause, (this.getWidth() - (this.getWidth()/dimension.cols()))-10, (this.getHeight() / dimension.rows()), 
        this.getWidth() / dimension.cols(), this.getHeight() / dimension.rows(), this);

        Image restart = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/restart.png")).getImage(); 
        g2.drawImage(restart, (this.getWidth() / 2)-100, (this.getHeight() /2)+150, 
        (this.getWidth() / dimension.cols())+150, this.getHeight() / dimension.rows(), this);

        Image quit = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/quit.png")).getImage(); 
        g2.drawImage(quit, (this.getWidth() / 2)-100, (this.getHeight() /2)+200, 
        (this.getWidth() / dimension.cols())+150, this.getHeight() / dimension.rows(), this);

    }

    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 128));
        Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
        g2.fill(rect);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
        int textWidth = g2.getFontMetrics().stringWidth("You reached level:" + this.model.getLevel());
        g2.drawString("You reached level:" + this.model.getLevel(), (this.getWidth() / 2) - (textWidth / 2), 40);

        Image gameOver = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/gameOver.png")).getImage(); 
        g2.drawImage(gameOver, (this.getWidth() / 2)-200, (this.getHeight() /10), 
        400, 400, this);


        Image restart = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/restart.png")).getImage(); 
        g2.drawImage(restart, (this.getWidth() / 2)-100, (this.getHeight() /2)+150, 
        (this.getWidth() / dimension.cols())+150, this.getHeight() / dimension.rows(), this);

        Image quit = new ImageIcon(getClass().getResource("/no/uib/inf101/crossyroad/pictures/quit.png")).getImage(); 
        g2.drawImage(quit, (this.getWidth() / 2)-100, (this.getHeight() /2)+200, 
        (this.getWidth() / dimension.cols())+150, this.getHeight() / dimension.rows(), this);
    }


}
