package interfaces;

import java.util.List;

public interface IReportGeneration {
    void generateMonthlyReport();
    void generateAnnualReport();
    void generateCategoryReport(String category);
    void generateCharts();
    <Transaction> List<Transaction> getTransactions();
}
