package app;

import ents.Sale;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Entre com caminho do arquivo: ");
        String caminhoArquivo = sc.nextLine();
        System.out.println();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            List<Sale> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                int month = Integer.parseInt(fields[0]);
                int year = Integer.parseInt(fields[1]);
                String seller = fields[2];
                int items = Integer.parseInt(fields[3]);
                double total = Double.parseDouble(fields[4]);

                Sale sale = new Sale(month, year, seller, items, total);
                list.add(sale);
            }

            System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
            list.stream()
                    .filter(sale -> sale.getYear() == 2016)
                    .sorted(Comparator.comparing(Sale::averagePrice).reversed())
                    .limit(5)
                    .forEach(System.out::println);

            System.out.println();
            double total = list.stream()
                    .filter(sale -> sale.getSeller().equals("Logan"))
                    .filter(sale -> sale.getMonth() == 1 || sale.getMonth() == 7)
                    .map(Sale::getTotal)
                    .reduce(0.0, Double::sum);

            System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = %.2f%n", total);
        }
        catch (IOException e) {
            System.out.println("Erro: " + caminhoArquivo + " (O sistema não pode encontrar o arquivo especificado)");
        }

        sc.close();
    }
}
