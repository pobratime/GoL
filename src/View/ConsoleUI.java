package View;

import Model.Grid;

public class ConsoleUI implements UI {

    @Override
    public void displayGrid(Grid grid) {
        int height = grid.getHeight();
        int width = grid.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid.getState(i, j)) {
                    System.out.print("X ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}