package maps;

public class Cell {
    private String celltype;

    public Cell(String celltype) {
        this.celltype = celltype;
    }

    public String getCelltype() {
        return celltype;
    }

    public void setCelltype(String celltype) {
        this.celltype = celltype;
    }

    // Добавляем метод isEmpty()
    public boolean isEmpty() {
        return celltype.equals("-");
    }
}
