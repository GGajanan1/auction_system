import java.util.*;

class User {
    private String username;
    private String password;
    private List<Item> itemsForSale;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.itemsForSale = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Item> getItemsForSale() {
        return itemsForSale;
    }

    public void addItemForSale(Item item) {
        itemsForSale.add(item);
    }
}

class Item {
    private String itemName;
    private String description;
    private double startingBid;

    public Item(String itemName, String description, double startingBid) {
        this.itemName = itemName;
        this.description = description;
        this.startingBid = startingBid;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingBid() {
        return startingBid;
    }
}

class Auction {
    private Item item;
    private double currentBid;
    private User seller;
    private boolean isOpen;

    public Auction(Item item, double startingBid, User seller) {
        this.item = item;
        this.currentBid = startingBid;
        this.seller = seller;
        this.isOpen = true;
    }

    public Item getItem() {
        return item;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public User getSeller() {
        return seller;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void placeBid(double amount, User bidder) {
        if (amount > currentBid) {
            currentBid = amount;
            System.out.println(bidder.getUsername() + " placed a bid of $" + amount + " on " + item.getItemName());
        } else {
            System.out.println("Bid must be higher than the current bid.");
        }
    }

    public void closeAuction() {
        isOpen = false;
        System.out.println("Auction for " + item.getItemName() + " closed.");
    }
}

public class AuctionSystem {
    private List<User> users;
    private List<Auction> auctions;

    public AuctionSystem() {
        this.users = new ArrayList<>();
        this.auctions = new ArrayList<>();
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
        System.out.println("User " + username + " registered successfully.");
    }

    public void loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Welcome, " + username + "!");
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    public void createAuction(String itemName, String description, double startingBid, String sellerUsername) {
        User seller = getUserByUsername(sellerUsername);
        if (seller != null) {
            Item item = new Item(itemName, description, startingBid);
            Auction auction = new Auction(item, startingBid, seller);
            auctions.add(auction);
            seller.addItemForSale(item);
            System.out.println("Auction created for " + itemName + ".");
        } else {
            System.out.println("Seller not found.");
        }
    }

    public void listActiveAuctions() {
        for (Auction auction : auctions) {
            if (auction.isOpen()) {
                System.out.println("Item: " + auction.getItem().getItemName() +
                        ", Current Bid: $" + auction.getCurrentBid() +
                        ", Seller: " + auction.getSeller().getUsername());
            }
        }
    }

    public void placeBid(String bidderUsername, String itemName, double amount) {
        User bidder = getUserByUsername(bidderUsername);
        if (bidder != null) {
            for (Auction auction : auctions) {
                if (auction.isOpen() && auction.getItem().getItemName().equals(itemName)) {
                    auction.placeBid(amount, bidder);
                    return;
                }
            }
            System.out.println("Auction not found or already closed.");
        } else {
            System.out.println("Bidder not found.");
        }
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        AuctionSystem auctionSystem = new AuctionSystem();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            try {
                // Registering users
                for (int i = 1; i <= 3; i++) {
                    System.out.print("Enter username for user" + i + ": ");
                    String username = sc.nextLine();
                    System.out.print("Enter password for user" + i + ": ");
                    String password = sc.nextLine();
                    auctionSystem.registerUser(username, password);
                }

                // Logging in first user
                System.out.print("\nEnter username for login: ");
                String loginUsername = sc.nextLine();
                System.out.print("Enter password for login: ");
                String loginPassword = sc.nextLine();
                auctionSystem.loginUser(loginUsername, loginPassword);

                // First user creates an auction
                System.out.print("\nEnter item name: ");
                String itemName = sc.nextLine();
                System.out.print("Enter item description: ");
                String itemDescription = sc.nextLine();
                System.out.print("Enter starting bid: ");
                double startingBid;
                try {
                    startingBid = Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for starting bid. Please enter a valid number.");
                    continue;
                }
                auctionSystem.createAuction(itemName, itemDescription, startingBid, loginUsername);

                // Listing active auctions
                System.out.println("\nListing active auctions...");
                auctionSystem.listActiveAuctions();

                // Second user places a bid
                System.out.print("\nEnter your username to place a bid: ");
                String bidderUsername1 = sc.nextLine();
                System.out.print("Enter item name to bid on: ");
                String itemToBidOn = sc.nextLine();
                System.out.print("Enter bid amount: ");
                double bidAmount1;
                try {
                    bidAmount1 = Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for bid amount. Please enter a valid number.");
                    continue;
                }
                auctionSystem.placeBid(bidderUsername1, itemToBidOn, bidAmount1);

                // Third user places a bid
                System.out.print("\nEnter your username to place a bid: ");
                String bidderUsername2 = sc.nextLine();
                System.out.print("Enter item name to bid on: ");
                itemToBidOn = sc.nextLine();
                System.out.print("Enter bid amount: ");
                double bidAmount2;
                try {
                    bidAmount2 = Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input for bid amount. Please enter a valid number.");
                    continue;
                }
                auctionSystem.placeBid(bidderUsername2, itemToBidOn, bidAmount2);

                // Ask if the user wants to continue or exit
                System.out.print("\nDo you want to perform another action? (yes/no): ");
                String continueChoice = sc.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    exit = true;
                }

            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
            sc.close();
        }
    }
}