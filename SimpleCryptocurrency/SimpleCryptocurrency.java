import java.util.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SimpleCryptocurrency {
    private List<Block> blockchain;
    private List<Transaction> pendingTransactions;
    private Set<String> nodes;

    public SimpleCryptocurrency() {
        this.blockchain = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        this.nodes = new HashSet<>();
        // Create genesis block
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        Block genesisBlock = new Block(0, "0", new ArrayList<>());
        genesisBlock.mineBlock();
        blockchain.add(genesisBlock);
    }

    public void registerNode(String node) {
        nodes.add(node);
    }

    public boolean isValidChain(List<Block> chain) {
        Block currentBlock;
        Block previousBlock = chain.get(0);
        int currentIndex = 1;

        while (currentIndex < chain.size()) {
            currentBlock = chain.get(currentIndex);

            if (!currentBlock.getPreviousHash().equals(previousBlock.calculateHash())) {
                return false;
            }

            if (!currentBlock.isValid()) {
                return false;
            }

            previousBlock = currentBlock;
            currentIndex++;
        }

        return true;
    }

    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public void minePendingTransactions(String minerAddress) {
        Block block = new Block(blockchain.size(), blockchain.get(blockchain.size() - 1).getHash(), pendingTransactions);
        block.mineBlock();
        blockchain.add(block);

        Transaction rewardTransaction = new Transaction("0", minerAddress, 100);
        pendingTransactions = new ArrayList<>();
        pendingTransactions.add(rewardTransaction);
    }

    public static void main(String[] args) {
        SimpleCryptocurrency cryptocurrency = new SimpleCryptocurrency();

        // Register nodes
        cryptocurrency.registerNode("Node1");
        cryptocurrency.registerNode("Node2");

        // Add transactions
        Transaction transaction1 = new Transaction("Alice", "Bob", 50);
        Transaction transaction2 = new Transaction("Bob", "Charlie", 30);
        cryptocurrency.addTransaction(transaction1);
        cryptocurrency.addTransaction(transaction2);

        // Mine pending transactions
        cryptocurrency.minePendingTransactions("Miner");

        // Display blockchain
        for (Block block : cryptocurrency.blockchain) {
            System.out.println(block);
        }
    }
}

class Block {
    private int index;
    private String previousHash;
    private List<Transaction> transactions;
    private long timestamp;
    private int nonce;
    private String hash;

    public Block(int index, String previousHash, List<Transaction> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timestamp = new Date().getTime();
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String data = index + previousHash + transactions.toString() + timestamp + nonce;
        return applySha256(data);
    }

    public void mineBlock() {
        while (!hash.substring(0, 4).equals("0000")) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined: " + hash);
    }

    public boolean isValid() {
        return hash.equals(calculateHash());
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", previousHash='" + previousHash + '\'' +
                ", transactions=" + transactions +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                ", hash='" + hash + '\'' +
                '}';
    }

    private String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class Transaction {
    private String sender;
    private String recipient;
    private double amount;

    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", amount=" + amount +
                '}';
    }
}
