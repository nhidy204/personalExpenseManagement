package interfaces;

public interface ITransactionManagement {
    void addTransaction(String type, double amount, String category);
    void editTransaction(int transactionId, String type, double amount, String category);
    void deleteTransaction(int transactionId);
    void listTransactions();
}