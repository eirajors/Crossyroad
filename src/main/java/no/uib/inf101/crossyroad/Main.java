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
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new java.awt.BorderLayout());
		//frame.setSize(400, 400);
		
		IGrid grid = new Grid(15, 20, ' ');
		GameModel model = new GameModel(grid);

		GameView view = new GameView();
		GameController controller = new GameController(model, view);
		controller.updateView();

		frame.add(view);
		frame.pack();

		frame.setVisible(true);


	}

}
