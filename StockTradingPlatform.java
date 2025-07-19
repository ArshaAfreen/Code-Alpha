import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();

    public void buyStock(String symbol, int quantity) {
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + quantity);
    }

    public boolean sellStock(String symbol, int quantity) {
        if (holdings.containsKey(symbol) && holdings.get(symbol) >= quantity) {
            holdings.put(symbol, holdings.get(symbol) - quantity);
            if (holdings.get(symbol) == 0) {
                holdings.remove(symbol);
            }
            return true;
        }
        return false;
    }

    public void displayPortfolio(Map<String, Stock> stockMarket) {
        System.out.println("\n--- Portfolio ---");
        double totalValue = 0;
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double price = stockMarket.get(symbol).price;
            double value = qty * price;
            totalValue += value;
            System.out.printf("%s - Qty: %d @ $%.2f = $%.2f%n", symbol, qty, price, value);
        }
        System.out.printf("Total Portfolio Value: $%.2f\n", totalValue);
    }
}

class User {
    String name;
    double balance;
    Portfolio portfolio;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new Portfolio();
    }

    public void buy(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (balance >= cost) {
            balance -= cost;
            portfolio.buyStock(stock.symbol, quantity);
            System.out.printf("Bought %d shares of %s for $%.2f\n", quantity, stock.symbol, cost);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void sell(Stock stock, int quantity) {
        if (portfolio.sellStock(stock.symbol, quantity)) {
            double revenue = stock.price * quantity;
            balance += revenue;
            System.out.printf("Sold %d shares of %s for $%.2f\n", quantity, stock.symbol, revenue);
        } else {
            System.out.println("You don't have enough shares to sell.");
        }
    }

    public void displayBalance() {
        System.out.printf("Cash Balance: $%.2f\n", balance);
    }
}

public class StockTradingPlatform {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Stock> stockMarket = new HashMap<>();

    public static void main(String[] args) {
        initializeMarket();

        User user = new User("Trader Joe", 10000.0);

        while (true) {
            System.out.println("\n===== Stock Trading Menu =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1 -> viewMarket();
                case 2 -> buyStock(user);
                case 3 -> sellStock(user);
                case 4 -> user.portfolio.displayPortfolio(stockMarket);
                case 5 -> user.displayBalance();
                case 6 -> {
                    System.out.println("Thank you for trading. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeMarket() {
        stockMarket.put("AAPL", new Stock("AAPL", 190.5));
        stockMarket.put("GOOG", new Stock("GOOG", 2800.0));
        stockMarket.put("MSFT", new Stock("MSFT", 350.0));
        stockMarket.put("AMZN", new Stock("AMZN", 130.0));
    }

    private static void viewMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : stockMarket.values()) {
            System.out.printf("%s - $%.2f\n", stock.symbol, stock.price);
        }
    }

    private static void buyStock(User user) {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.nextLine().toUpperCase();
        if (stockMarket.containsKey(symbol)) {
            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            user.buy(stockMarket.get(symbol), qty);
        } else {
            System.out.println("Stock not found.");
        }
    }

    private static void sellStock(User user) {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();
        if (stockMarket.containsKey(symbol)) {
            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();
            user.sell(stockMarket.get(symbol), qty);
        } else {
            System.out.println("Stock not found.");
        }
    }
}
