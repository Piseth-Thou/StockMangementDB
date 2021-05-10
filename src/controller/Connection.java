package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Connection {
    public static ArrayList<String> getProducts(ArrayList<String> products){
        try (BufferedReader buffer = new BufferedReader(new FileReader("Product.txt"))
        ) {
            long length = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                length++;
                products.add(line);
            }
            System.out.println("Array length:" + products.size());
            System.out.println("Read length: " + length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
