package store.utils;

import store.domain.Product;
import store.domain.Promotion;

import java.util.List;
import java.util.stream.Collectors;

public class ProductLoader {

    public static List<Product> loadProductsFromFile(String filePath) {
        List<String> lines = FileReaderUtility.readLines(filePath);

        return lines.stream()
                .skip(1)
                .map(ProductLoader::parseProduct)
                .collect(Collectors.toList());
    }

    private static Product parseProduct(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int stockQuantity = Integer.parseInt(parts[2]);
        String promotion = parts[3];
        return new Product(name, price, stockQuantity, promotion);
    }
}
