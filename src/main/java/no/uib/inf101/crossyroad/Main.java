package no.uib.inf101.crossyroad;

import javax.swing.JFrame;

import no.uib.inf101.grid.Grid;
import no.uib.inf101.grid.IGrid;
import no.uib.inf101.crossyroad.controller.GameController;
import no.uib.inf101.crossyroad.model.GameModel;
import no.uib.inf101.crossyroad.view.GameView;


public class Main {

	public static void main(String[] args) {
  	
		JFrame frame = new JFrame("Cross the grid");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new java.awt.BorderLayout());

		int rows = 15; 
		int cols = 20;
		
		IGrid grid = new Grid(rows, cols, ' ');
		IGrid backgroundGrid = new Grid(rows, cols, ' ');
		GameModel model = new GameModel(grid, backgroundGrid);

		GameView view = new GameView(model);
		GameController controller = new GameController(model, model, view);
		controller.updateView();

		frame.add(view);
		frame.pack();

		frame.setVisible(true);


	}

}
