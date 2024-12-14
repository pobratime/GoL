package Model.Configuration;

import Model.Grid;

public class GliderConfig implements Config {

    @Override
    public void configure(Grid grid) {
        int[][] gliderPattern = {
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1}
        };

        int n = gliderPattern.length;
        int m = gliderPattern[0].length;

        int startY = grid.getHeight() / 2 - n / 2;
        int startX = grid.getWidth() / 2 - m / 2;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid.setState(startY + i, startX + j, gliderPattern[i][j] == 1);
            }
        }
    }
}