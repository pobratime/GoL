package Model;

public class Grid {

    private final int height;
    private final int width;
    private final Cell[][] grid;

    // Grid constructor that takes two dimensions
    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    // Initialize grid with Cells
    private void initializeGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    // Height and Width getters
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // Get state of the requested cell at position (x, y)
    public boolean getState(int x, int y) {
        return grid[x][y].isAlive();
    }

    // Set state of the cell
    public void setState(int x, int y, boolean state) {
        grid[x][y].changeState(state);
    }

    // Update the current grid with the next state
    public void updateGrid(Grid nextState) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.grid[i][j].changeState(nextState.getState(i, j));
            }
        }
    }
}