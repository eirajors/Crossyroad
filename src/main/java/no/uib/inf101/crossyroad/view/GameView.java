package no.uib.inf101.crossyroad.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridCell;
import no.uib.inf101.grid.IGrid;

public class GameView extends JPanel{

    private ViewableGameModel model; 
    private ColorTheme farger;
    private final int OUTERMARGIN = 20;
    private final int INNERMARGIN = 2;
    private Character color;

    //konstruktør
    public GameView(ViewableGameModel model){
        this.model = model;
        this.farger = new ColorTheme();

        //this.setPreferredSize(new Dimension(400, 400));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawGrid(g2);
    }

    private void drawGrid(Graphics2D g2) {

        double width = getWidth() - (OUTERMARGIN * 2);
        double height = getHeight() - (OUTERMARGIN * 2);

        double x = OUTERMARGIN;
        double y = OUTERMARGIN;

        Rectangle2D bakgrunn = new Rectangle2D.Double(x, y, width, height);

        g2.setColor(Color.GRAY);
        g2.fill(bakgrunn);

        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(bakgrunn, model.getDimension(), INNERMARGIN);

        drawCells(g2, converter, model);
    }

    private void drawCells(Graphics2D g2, CellPositionToPixelConverter converter, ViewableGameModel model) {
        for(GridCell cell : model.getTiles()){
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
        int width  = model.getDimension().cols() * cellSize + OUTERMARGIN * 2;
        int height = model.getDimension().rows() * cellSize + OUTERMARGIN * 2;

        return new Dimension(width, height);
    }
    
  }


