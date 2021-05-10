package model;

import main.App;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private int stockQty;
    private String importedDate;

    public Product(int id, String name, double unitPrice, int stockQty, String importedDate) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQty = stockQty;
        this.importedDate = importedDate;
    }
    public Product(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public String getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(String importedDate) {
        this.importedDate = importedDate;
    }

    @Override
    public String toString() {
        return id + App.separator + name + App.separator + unitPrice + App.separator +stockQty + App.separator+ importedDate;
    }
}
