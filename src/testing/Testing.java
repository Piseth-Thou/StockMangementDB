package testing;

import controller.Connection;
import helper.Validator;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Testing {
    private static HashMap<Integer, Product> products;
    private static Scanner scanner = new Scanner(System.in);
    private static int numOfRows = 5;
    private static int currentPage = 1;
    private static Table table;

    public static void main(String[] args) {
//        generateData();
        getData();
        do switch (printMenu()) {
            case "*":
                gotoPage(currentPage);
                break;
            case "w":
                writeData();
                break;
            case "r":
                readData();
                break;
            case "u":
                System.out.println("Update");
                break;
            case "d":
                System.out.println("Delete");
                break;
            case "f":
                goFirst();
                break;
            case "p":
                goPrevious();
                break;
            case "n":
                goNext();
                break;
            case "l":
                goLast();
                break;
            case "s":
                System.out.println("search");
                break;
            case "g":
                gotoPage(Validator.readInt("Input page number(1-" + getTotalPage() + ") : ", 1, getTotalPage()));
                break;
            case "se":
                setRow();
                break;
            case "sa":
                System.out.println("Save");
                break;
            case "re":
                System.out.println("Restore");
                break;
            case "h":
                System.out.println("Help");
                break;
            case "e":
                System.exit(0);
                break;
        } while (true);
    }

    private static String printMenu() {
        Table tableMenu = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        String[] menu = {"*) Display", "W) Write", "R) Read",
                "U) Update", "D) Delete", "F) First", "P) Previous",
                "N) Next", "L) Last", "S) Search", "G) Goto", "Se) Set",
                "Sa) Save", "Ba) Back up", "Re) Restore", "H) Help", "E) Exit"};
        for (String s : menu) {
//            tableMenu.addCell(s);
            tableMenu.addCell(s);
        }
        System.out.println(tableMenu.render());
        System.out.print("Command--> ");
        return scanner.nextLine().toLowerCase();
    }

    private static void initTable() {
        table = new Table(5);
        table.addCell("ID");
        table.addCell("NAME");
        table.addCell("Unit Price");
        table.addCell("Qty");
        table.addCell("Imported Date");
    }

    private static int selectChoice() {
        System.out.println("1. Set row");
        System.out.println("2. First");
        System.out.println("3. previous");
        System.out.println("4. Next");
        System.out.println("5. Last");
        System.out.println("6. Goto");
        System.out.println("7. Display");
        int choice = Validator.readInt("Please select a choice :");
        return choice;
    }

    private static void getData() {
        long startTime = System.nanoTime();
        products = new HashMap<>();
//        Connection.getProducts(products);
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    private static void readData() {
        int id = Validator.readInt("Read by ID :");
        Product product = products.get(id);
        if (product != null) {
            Table tableReadData = new Table(2);
            tableReadData.addCell("ID");
            tableReadData.addCell("" + product.getId());
            tableReadData.addCell("Name");
            tableReadData.addCell(product.getName());
            tableReadData.addCell("Price");
            tableReadData.addCell("" + product.getUnitPrice());
            tableReadData.addCell("Imported Date");
            tableReadData.addCell(product.getImportedDate());
            System.out.println(tableReadData.render());
        } else {
            System.out.println("Product id is not exist!");
        }
    }

    private static void generateData() {
        new Thread(() -> {
            String message = "Please wait....";
            int i = 0;
            while (i < message.length()) {
                System.out.print(message.charAt(i++));
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        long startTime = System.nanoTime();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Product.txt", false))) {
            int flush = 0;
            for (int i = 1; i <= 1_000; i++) {
                Product product = new Product(i, "Coca Cola", 10d, 1000, getDate());
                bufferedWriter.write(product.toString());
                bufferedWriter.newLine();
                if (i == 5000 + flush) {
                    flush += 5000;
                    bufferedWriter.flush();
                }
            }
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");

    }

    private static void setRow() {
        System.out.print("Number of row : ");
        numOfRows = scanner.nextInt();
        scanner.nextLine();
    }

    private static void goNext() {
        if (currentPage != getTotalPage())
            gotoPage(++currentPage);
        else gotoPage(getTotalPage());
    }

    private static void goPrevious() {
        if (currentPage != 1)
            gotoPage(--currentPage);
        else
            gotoPage(1);
    }

    private static void gotoPage(int pageNum) {
        initTable();
        currentPage = pageNum;
        int start = numOfRows * (currentPage - 1) + 1;
        if (pageNum == getTotalPage()){
            int remainRows = products.size() - start;
            for (int i = start; i <= start + remainRows; i++){
                addRowTable(i);
            }
        }else {
            for (int i = start; i < start + numOfRows; i++) {
                addRowTable(i);
            }
        }

        System.out.println(table.render());
        printPageSummary();
    }

    private static int getTotalPage() {
        return products.size() % numOfRows == 0 ? products.size() / numOfRows : products.size() / numOfRows + 1;
    }

    private static void goFirst() {
        currentPage = 1;
        initTable();
        for (int i = 1; i <= numOfRows; i++) {
            addRowTable(i);
        }
        System.out.println(table.render());
        printPageSummary();
    }

    private static void printPageSummary() {
        System.out.println("Page : " + currentPage + " of " + getTotalPage() + "\t\t\tTotal record : " + products.size());
    }

    private static void goLast() {
        initTable();
        currentPage = getTotalPage();
        for (int i = products.size() - (numOfRows - 1); i <= products.size(); i++) {
            addRowTable(i);
        }
        System.out.println(table.render());
        printPageSummary();
    }

    private static void addRowTable(int i) {
        Product product = products.get(i);
        table.addCell("" + product.getId());
        table.addCell(product.getName());
        table.addCell("" + product.getUnitPrice());
        table.addCell("" + product.getStockQty());
        table.addCell(product.getImportedDate());
    }

    private static void saveUpdate() {
        long startTime = System.nanoTime();
        FileWriter fileWriter = null;
        int bufferSize = 8 * 1024;
        try {
            fileWriter = new FileWriter("Product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufferSize);
        System.out.println(products.size());
        for (int i = 1; i <= products.size(); i++) {
            try {
                bufferedWriter.write(products.get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
//        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return dateFormat.format(date);
    }

    private static void writeData() {
        Product lastProduct = products.get(products.size());
        int lastId = lastProduct.getId();
        System.out.println("Product ID : " + (lastId + 1));
        System.out.print("Product's Name : ");
        String name = scanner.nextLine();
        double price = Validator.readDouble("Product's Price : ");
        int qty = Validator.readInt("Product's Qty : ", 1, 1_000_000);

        Table tableWriteData = new Table(2);
        tableWriteData.addCell("ID");
        tableWriteData.addCell("" + (lastId + 1));
        tableWriteData.addCell("Name");
        tableWriteData.addCell(name);
        tableWriteData.addCell("Price");
        tableWriteData.addCell("" + price);
        tableWriteData.addCell("Imported Date");
        tableWriteData.addCell(getDate());
        System.out.println(tableWriteData.render());
        char answer;
        System.out.print("Are you sure to add record? [Y/y] or [N/n]:");
        answer = Character.toLowerCase(scanner.next().charAt(0));
        if (answer == 'y')
            products.put(lastId + 1, new Product(lastId + 1, name, price, qty, getDate()));
        scanner.nextLine();

    }


}
