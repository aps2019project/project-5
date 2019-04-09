package models.map;

public class Map {
    private Cell[][] cells = new Cell[5][9];

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }
}
