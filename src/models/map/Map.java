package models.map;

public class Map {
    public Cell[][] cell = new Cell[5][9];

    public Map() {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 9; j++) {
                cell[i][j] = new Cell(i,j);
            }
        }
    }
}
