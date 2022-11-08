import java.util.Random;

public class Client extends Deposit {
    private String firstName;
    private String lastName;
    final private int id;
    private String clientLogin;
    private String clientPassword;
    static int count = 100;
    static int hashKey = 100;

    public Client() {
        this.firstName = "John";
        this.lastName = "Doe";
        this.id = counterForClientID();
        this.clientPassword = passwordGenerator();
        this.clientLogin = "jdoe";
    }

    public Client(String FirstName, String LastName) {
        this.firstName = FirstName;
        this.lastName = LastName;
        this.id = counterForClientID();
        this.clientPassword = passwordGenerator();
        this.clientLogin = loginGenerator(FirstName, LastName);
    }

    public static int counterForClientID() {
        count++;
        return count;
    }

    public static int counterForKey() {
        hashKey++;
        return hashKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public int getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String passwordGenerator() {
        String lowerLetters = "abcdefghijklmnopqrstuvwxyz"; //26
        String upperLetters = "ABCDEFGJKLMNPRSTUVWXYZ"; //22
        String numbers = "0123456789"; //10
        String symbols = "^$?!@#%&"; //8
        int changer;
        Random randomNum = new Random();
        StringBuilder newPassword = new StringBuilder();

        for (int i = 0; i < 1; i++) {
            changer = randomNum.nextInt(0, 22);
            newPassword.append(lowerLetters.charAt(changer));

            changer = randomNum.nextInt(0, 21);
            newPassword.append(upperLetters.charAt(changer));

            changer = randomNum.nextInt(0, 9);
            newPassword.append(numbers.charAt(changer));

            changer = randomNum.nextInt(0, 7);
            newPassword.append(symbols.charAt(changer));

        }
        return newPassword.toString();
    }

    private String loginGenerator(String first, String last) {
        String login = first.charAt(0) + last;
        login = login.toLowerCase();
        return login;
    }

    public String getClientLogin() {
        return clientLogin;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientLogin(String clientLogin) {
        this.clientLogin = clientLogin;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }
}
