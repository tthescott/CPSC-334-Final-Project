package edu.gonzaga;

// class representing the player playing the game
public class Player {
    
    // each player has a name and a balance (money in da bank)
    private Integer playerID;
    private String name;
    private Double balance;
    private Double gains;

    // default constructor
    public Player(Integer playerID , String name) {
        this(playerID, name, 10000.0, 0.0);
    }

    public Player(Integer playerID, String name, Double balance, Double gains) {
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        this.gains = gains;
    }

    // update player attributes to user specifications
    public void setName(String newName){
        this.name = newName;
    }

    public void credit(Double deposit) {
        this.balance += deposit;
    }

    public void debit(Double withdraw) throws InsufficientFundsException {
        if(balance >= withdraw) {
            this.balance -= withdraw;
        }
        else {
            throw new InsufficientFundsException("You're broke, watch an ad to get more money to keep gambling");
        }
    }

    public void watchAdRevenue(){
        credit(25.0);
    }

    public Double getGains() {
        return gains;
    }

    /**
     *
     * @param winnings - positive when player hits a slot with a multiplier greater than 1
     *                 - negative when player hits a slot with a multiplier less than 1
     */
    public void recordWinnings(Double winnings) {
        if(winnings > 0) {
            credit(winnings);
        }
        else {
            debit(-winnings); // if it is negative, we need to subtract
        }
        this.gains += winnings;
    }

    public Double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public Integer getPlayerID() {
        return playerID;
    }
}