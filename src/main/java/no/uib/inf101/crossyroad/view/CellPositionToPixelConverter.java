package no.uib.inf101.crossyroad.view;

import java.awt.geom.Rectangle2D;

import no.uib.inf101.grid.CellPosition;
import no.uib.inf101.grid.GridDimension;

public class CellPositionToPixelConverter {

    private final Rectangle2D box;
    private final GridDimension gd;
    private final int margin;

    public CellPositionToPixelConverter(Rectangle2D box, GridDimension gd, int margin){

        this.box = box;
        this.gd = gd;
        this.margin = margin;
    }

    /**
     * Calculates the width and height of a gridcell given a cellposition
     * 
     * @param pos the Cellposition where the cell is supposed to be drawn 
     * @return a new rectangle in the given cellposition
     */
    public Rectangle2D getBoundsOfCell(CellPosition pos){
        double totWidth = box.getWidth();
        double totHeight = box.getHeight();

        int cols = gd.cols();
        int rows = gd.rows();

        double cellWidth = (totWidth - (margin * (cols - 1))) / cols;
        double cellHeight = (totHeight - (margin * (rows - 1))) / rows;

        double cellX = box.getX() + (cellWidth + margin) * pos.col();
        double cellY = box.getY() + (cellHeight + margin) * pos.row();

        return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
    }
}
