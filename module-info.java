import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        try {
            generateSalesReport("salesmen.txt", "products.txt", "salesman_info.txt", "sales_report.csv", "product_report.csv");
            System.out.println("Archivos de reporte generados exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al generar archivos de reporte: " + e.getMessage());
        }
    }

    public static void generateSalesReport(String salesmenFile, String productsFile, String salesmanInfoFile,
            String salesReportFile, String productReportFile) throws IOException {
        Map<String, Double> salesBySalesman = calculateSalesBySalesman(salesmenFile, productsFile);
        writeSalesReport(salesmanInfoFile, salesBySalesman, salesReportFile);
        writeProductReport(productsFile, productReportFile);
    }

    public static Map<String, Double> calculateSalesBySalesman(String salesmenFile, String productsFile)
            throws IOException {
        Map<String, Double> salesBySalesman = new HashMap<>();
        try (BufferedReader salesmenReader = new BufferedReader(new FileReader(salesmenFile));
                BufferedReader productsReader = new BufferedReader(new FileReader(productsFile))) {
            String line;
            while ((line = salesmenReader.readLine()) != null) {
                String[] salesmanInfo = line.split(";");
                String salesmanId = salesmanInfo[0] + ";" + salesmanInfo[1];
                salesBySalesman.put(salesmanId, 0.0);
            }
            while ((line = productsReader.readLine()) != null) {
                String[] productInfo = line.split(";");
                double price = Double.parseDouble(productInfo[2]);
                salesBySalesman.replaceAll((k, v) -> v + price); // Increment sales for all salesmen
            }
        }
        return salesBySalesman;
    }

    public static void writeSalesReport(String salesmanInfoFile, Map<String, Double> salesBySalesman,
            String salesReportFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(salesmanInfoFile));
                FileWriter writer = new FileWriter(salesReportFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] salesmanInfo = line.split(";");
                String salesmanId = salesmanInfo[0] + ";" + salesmanInfo[1];
                String name = salesmanInfo[2] + " " + salesmanInfo[3];
                Double sales = salesBySalesman.getOrDefault(salesmanId, 0.0);
                writer.write(name + ";" + sales + "\n");
            }
        }
    }

    public static void writeProductReport(String productsFile, String productReportFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(productsFile));
                FileWriter writer = new FileWriter(productReportFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productInfo = line.split(";");
                String productName = productInfo[1];
                double price = Double.parseDouble(productInfo[2]);
                writer.write(productName + ";" + price + "\n");
            }
        }
    }

    // Métodos para generar archivos de prueba

    public static void createSalesMenFile(int randomSalesCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 0; i < randomSalesCount; i++) {
                String typeDocumento = "TIPO" + random.nextInt(100);
                String numeroDocumento = String.valueOf(random.nextLong());
                writer.write(typeDocumento + ";" + numeroDocumento + "\n");
            }
        }
    }

    public static void createProductsFile(int productsCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 0; i < productsCount; i++) {
                String productId = "ID" + random.nextInt(100);
                String productName = "Product " + (i + 1);
                double price = random.nextDouble() * 100;
                writer.write(productId + ";" + productName + ";" + price + "\n");
            }
        }
    }

    public static void createSalesManInfoFile(int salesmanCount, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            Random random = new Random();
            for (int i = 0; i < salesmanCount; i++) {
                String typeDocumento = "TIPO" + random.nextInt(100);
                String numeroDocumento = String.valueOf(random.nextLong());
                String nombres = "Nombres" + random.nextInt(100);
                String apellidos = "Apellidos" + random.nextInt(100);
                writer.write(typeDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos + "\n");
            }
        }
    }
}
