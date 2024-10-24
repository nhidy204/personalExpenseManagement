package interfaces;

public interface IUserManagement {
    void registerUser(String username, String password);
    void loginUser(String username, String password);
    double getUserBalance();
    void viewReports();
}

