package no.uib.inf101.crossyroad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.crossyroad.model.GameState;
import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.GridDimension;

public class GameView extends JPanel {

    private ColorTheme farger;
    private final int OUTERMARGIN = 20;
    private final int INNERMARGIN = 0;
    private Iterable<GridCell> tiles;
    private Iterable<GridCell> backgroundTiles;
    private GridDimension dimension;
    private GameState gameState;

    // private Character color;

    // konstruktør
    public GameView() {
        this.farger = new ColorTheme();
    }

    public void update(Iterable<GridCell> tiles, Iterable<GridCell> backgroundTiles, GridDimension dimension, GameState gameState) {
        this.tiles = tiles;
        this.dimension = dimension;
        this.gameState = gameState;
        this.backgroundTiles = backgroundTiles; 

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tiles == null || dimension == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        drawGrid(g2);

        if (gameState == GameState.WON) {
            g2.setColor(new Color(0, 0, 0, 128));
            Rectangle2D rect = new Rectangle2D.Double(0, 0, this.getWidth(), this.getHeight());
            g2.fill(rect);
            drawMessage(g2, "Du vant!");
        }
    }

    private void drawGrid(Graphics2D g2) {

        double width = getWidth() - (OUTERMARGIN * 2);
        double height = getHeight() - (OUTERMARGIN * 2);

        double x = OUTERMARGIN;
        double y = OUTERMARGIN;

        Rectangle2D bakgrunn = new Rectangle2D.Double(x, y, width, height);

        //g2.setColor(Color.GRAY);
        //g2.fill(bakgrunn);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(bakgrunn, dimension, INNERMARGIN);

        drawCells(g2, converter, backgroundTiles);
        drawCells(g2, converter, tiles);
    }

    private void drawCells(Graphics2D g2, CellPositionToPixelConverter converter, Iterable<GridCell> tiles) {
        for (GridCell cell : tiles) {
            
            CellPosition cellPos = cell.pos();
            Rectangle2D cellArea = converter.getBoundsOfCell(cellPos);

            Character colorSymbol = cell.symbol();

            Color colorCell = farger.getCellColor(colorSymbol);

            g2.setColor(colorCell);
            g2.fill(cellArea);
        }
    }

    public Dimension getPreferredSize() {
        int cellSize = 40;
        int width = dimension.cols() * cellSize + OUTERMARGIN * 2;
        int height = dimension.rows() * cellSize + OUTERMARGIN * 2;

        return new Dimension(width, height);
    }

    private void drawMessage(Graphics2D g2, String message) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Dialoginput", Font.BOLD, 20));
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, (this.getWidth() / 2) - (textWidth / 2), this.getHeight() / 2 + 40);

    }
}
