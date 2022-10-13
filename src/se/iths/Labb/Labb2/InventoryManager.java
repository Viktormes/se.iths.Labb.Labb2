package se.iths.Labb.Labb2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class InventoryManager {


    private static void menuChoices() {
        System.out.println("""
                 
                 Viktors Animal Accessories
                ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                1. Add or remove product
                2. Inventory balance
                3. Search
                4. File management
                5. Exit""");
    }

    private static void productMenuChoices() {
        System.out.println("""
                 
                 Make a choice:
                1. Add product
                2. Remove product
                3. Main menu""");
    }

    private static void searchMenuChoices() {
        System.out.println("""
                 
                 Make a choice:
                1. Search by name
                2. Search by EAN
                3. Main menu""");
    }
    private static void fileManagementMenu() {
        System.out.println("""
                 
                 Make a choice:
                1. Save to file
                2. Load from file
                3. Main menu""");
    }
    static void firstMenuLoop(Scanner sc, ArrayList<Product> products) {
        boolean loop = true;
        while (loop) {
            System.out.println();

            menuChoices();
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> productMenuLoop(sc, true, products);
                case "2" -> printTheProducts(products);
                case "3" -> searchMenuLoop(sc, true, products);
                case "4" -> fileManagementMenuLoop(sc,true,products);
                case "5" -> {jsonAdder(products); loop = false;}
                default -> printCommandAlternatives();
            }
        }
    }
    private static void productMenuLoop(Scanner sc, boolean loop, ArrayList<Product> products) {
        while (loop) {
            productMenuChoices();
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addingProduct(products);
                case "2" -> removeProduct(products,sc);
                case "3" -> loop = false;
                default -> printCommandAlternatives();
            }
        }
    }
    private static void fileManagementMenuLoop(Scanner sc, boolean loop, ArrayList<Product> products){
        while (loop){
            fileManagementMenu();
            String choice = sc.nextLine();
            switch (choice){
                case "1" -> jsonAdder(products);
                case "2" -> readFromJSON();
                case "3" -> loop = false;
                default -> printCommandAlternatives();
            }
        }
    }
    private static void searchMenuLoop(Scanner sc, boolean loop, ArrayList<Product> products) {
        while (loop) {
            searchMenuChoices();
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> productNameSearch(sc, products);
                case "2" -> productEANSearch(sc, products);
                case "3" -> loop = false;
                default -> printCommandAlternatives();
            }
        }
    }
    private static void addingProduct(ArrayList<Product> product) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the product name: ");
        String addProduct = getProductName(sc);
        Product product1 = new Product(addProduct
                , getProductPrice(sc, addProduct)
                , getProductEAN(sc, addProduct)
                , addToCategory(sc));
        if (product1.getEaNumber() != -1 && !Objects.equals(product1.getPrice(), BigDecimal.valueOf(-1))) {
            product.add(product1);
        } else {
            System.out.println("Please enter a number for price and a number for EAN.");
        }
    }
    static String getProductName(Scanner sc) {

        return sc.nextLine().toLowerCase();
    }
    static BigDecimal getProductPrice(Scanner sc, String productName) {
        System.out.print("Enter the price of " + productName + " in SEK: ");
        String productPrice = sc.nextLine();
        try {
            return new BigDecimal(productPrice);
        } catch (NumberFormatException e) {
            return BigDecimal.valueOf(-1);
        }
    }
    static int getProductEAN(Scanner sc, String productName) {
        System.out.print("Add EAN for " + productName + ": ");
        String productEAN = sc.nextLine();
        try {
            return Integer.parseInt(productEAN);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private static void printTheProducts(ArrayList<Product> products) {
        System.out.println(products);
    }
    private static List<Product> findProductByName(ArrayList<Product> allProducts, String input) {
        return allProducts.stream()
                .filter(product -> product.getNameOfProduct().equals(input))
                .toList();
    }
    private static List<Product> findEANbyName(ArrayList<Product> allProducts, int input) {
        return allProducts.stream()
                .filter(product -> product.getEaNumber() == (input))
                .toList();
    }
    private static void productNameSearch(Scanner sc, ArrayList<Product> products) {
        System.out.print("Enter the name of the product you want to see: ");
        String inputString = sc.nextLine().toLowerCase();
        System.out.println(findProductByName(products, inputString));
    }
    private static void productEANSearch(Scanner sc, ArrayList<Product> products) {
        System.out.print("Enter the EAN of the product you want to see: ");
        int inputInt = sc.nextInt();
        sc.nextLine();
        System.out.println(findEANbyName(products, inputInt));
    }
    private static void printCommandAlternatives() {
        System.out.println("Please choose one of the alternatives!");
    }
    private static void removeProduct(ArrayList<Product> products, Scanner sc) {
        System.out.print("Enter the name of the product you want to delete: ");
        String productDeleter = sc.nextLine();
        products.removeIf(o -> o.getNameOfProduct().equals(productDeleter));
    }
    static String addToCategory(Scanner sc) {
        System.out.print("Enter the name of the category of the product: ");
        return sc.nextLine().toLowerCase();
    }
    private static void jsonAdder(ArrayList<Product> products){
        System.out.println("Saving to file..");
        Gson gson = new Gson();
        String json = gson.toJson(products);
        String homeFolder = System.getProperty("user.home");

        Path products1 = Path.of(homeFolder, "products", "products.json");
        if (Files.exists(Path.of(homeFolder))) {
            try{
                Files.writeString(products1, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try{
                Files.createDirectory(Path.of(homeFolder, "products"));
                Files.writeString(products1, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static ArrayList<Product> readFromJSON() {

        System.out.println("Loading from file..");
        Gson gson = new Gson();
        String homeFolder = System.getProperty("user.home");
        try {
            String jsonText =  Files.readString(Path.of(homeFolder, "products","products.json"));
            System.out.println(jsonText);
            return gson.fromJson(jsonText, new TypeToken<ArrayList<Product>>() {
            }.getType());



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

