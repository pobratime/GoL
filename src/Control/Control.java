package Control;

import Model.Grid;

public class Control {

    public void advance(Grid grid) {
        int height = grid.getHeight();
        int width = grid.getWidth();
        Grid nextState = new Grid(height, width);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int surr = count(grid, i, j);

                if (grid.getState(i, j)) {
                    if (surr < 2 || surr > 3) {
                        nextState.setState(i, j, false);
                    } else {
                        nextState.setState(i, j, true);
                    }
                } else {
                    if (surr == 3) {
                        nextState.setState(i, j, true);
                    }
                }
            }
        }

        grid.updateGrid(nextState);
    }

    private int count(Grid grid, int x, int y) {
        int count = 0;

        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < grid.getHeight() && newY >= 0 && newY < grid.getWidth() && grid.getState(newX, newY)) {
                count++;
            }
        }
        return count;
    }
}