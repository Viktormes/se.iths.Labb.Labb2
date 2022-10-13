package se.iths.Labb.Labb2;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class Product {
    private final String nameOfProduct;
    private final BigDecimal price;
    private final int eaNumber;
    private final String categoryName;
    public Product(String nameOfProduct, BigDecimal price, int eaNumber, String categoryName) {
        this.nameOfProduct = nameOfProduct;
        this.price = price;
        this.eaNumber = eaNumber;
        this.categoryName = categoryName;
    }
    @Override
    public String toString() {
        return "" +
                categoryName + " | " +
                 nameOfProduct  +
                "| Price = " + price +
                "kr| EAN = " + eaNumber +
                "";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (eaNumber != product.eaNumber) return false;
        if (!Objects.equals(nameOfProduct, product.nameOfProduct))
            return false;
        return Objects.equals(price, product.price);
    }
    @Override
    public int hashCode() {
        int result = nameOfProduct != null ? nameOfProduct.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + eaNumber;
        return result;
    }
    public String getNameOfProduct() {
        return nameOfProduct;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public int getEaNumber() {
        return eaNumber;
    }
    public final static ArrayList<Product> arrayOfProducts = new ArrayList<>();
}






