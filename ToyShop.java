import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ToyShop {
    private List<Toy> toys;
    private List<Toy> prizePool;
    private Random random;

    public ToyShop() {
        toys = new ArrayList<>();
        prizePool = new ArrayList<>();
        random = new Random();
    }

    public void addToy(int id, String name, int quantity, double probability) {
        Toy toy = new Toy(id, name, quantity, probability);
        toys.add(toy);
    }

    public void play() {
        for (Toy toy : toys) {
            double randomValue = random.nextDouble() * 100;
            if (randomValue < toy.getProbability()) {
                if (toy.getQuantity() > 0) {
                    prizePool.add(toy);
                    toy.setQuantity(toy.getQuantity() - 1);
                    System.out.println("Congratulations! You won a " + toy.getName());
                    saveToysToFile(toy);
                    return;
                } else {
                    System.out.println("Sorry, " + toy.getName() + " is out of stock.");
                }
            }
        }
        System.out.println("No toy won this time.");
    }

    private void saveToysToFile(Toy toy) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("prize_pool.txt", true))) {
            writer.write("Prize: " + toy.getName());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyShop toyShop = new ToyShop();

        
        toyShop.addToy(1, "Teddy Bear", 10, 30);
        toyShop.addToy(2, "Car", 5, 20);
        toyShop.addToy(3, "Doll", 8, 50);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Press enter to play or type 'exit' to quit:");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            toyShop.play();
        }

        scanner.close();
    }
}