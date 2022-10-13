package se.iths.Labb.Labb2;




import java.util.ArrayList;
import java.util.Scanner;

public class Labb2 {
    public static ArrayList<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        products = InventoryManager.readFromJSON();

        InventoryManager.firstMenuLoop(sc,products);
    }
}
