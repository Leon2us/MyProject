import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {
    HashMap<Integer, Client> dataBase = new HashMap<>();
    Scanner scannerLine = new Scanner(System.in);
    Scanner scannerInt = new Scanner(System.in);
    Scanner scannerDouble = new Scanner(System.in);
    Gson gson = new Gson();
    Type gsonType = new TypeToken<HashMap>() {
    }.getType();

    public void runMenu() {
        String bufferReaderCheck = null;
        try {
            BufferedReader readerCheck = new BufferedReader(new FileReader("DataOfClients.json"));
            bufferReaderCheck = readerCheck.readLine();
            readerCheck.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bufferReaderCheck == null) {
            System.out.println("Welcome, do you have an opened account with us?\nYes or No or End");
            String enterAnswer = scannerLine.nextLine();
            if (enterAnswer.equals("Yes")) {
                accountPresentMenu();
            } else if (enterAnswer.equals("No")) {
                accountNotPresentMenu();
            } else {
                System.out.println("End of program.");
            }
        } else if (dataBase.isEmpty()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("DataOfClients.json"));
                String bufferReader = reader.readLine();

                dataBase = new Gson().fromJson(
                        bufferReader, new TypeToken<HashMap<Integer, Client>>() {
                        }.getType()
                );
                reader.close();

                for (int i = 101; i < dataBase.size() + 101; i++) {
                    int max = 0;
                    if (dataBase.get(i).getId() > max) {
                        max = dataBase.get(i).getId();
                    }
                    Client.hashKey = max;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Welcome@, do you have an opened account with us?\nYes or No or End");
            String enterAnswer = scannerLine.nextLine();
            if (enterAnswer.equals("Yes")) {
                accountPresentMenu();
            } else if (enterAnswer.equals("No")) {
                accountNotPresentMenu();
            } else {
                System.out.println("End of program.");
            }
        }
    }

    public void runMenuSecondTime() {
        System.out.println("\nWelcome, do you have an opened account with us?\nYes or No or End");
        String enterAnswer = scannerLine.nextLine();
        if (enterAnswer.equals("Yes")) {
            accountPresentMenu();
        } else if (enterAnswer.equals("No")) {
            accountNotPresentMenu();
        } else {
            System.out.println("End of program.");
        }
    }

    public void runMenuOfClient(int id) {
        System.out.println("\nMenu of " + dataBase.get(id).getFirstName() + " " + dataBase.get(id).getLastName());
        System.out.println("Press 1 to access to manage your deposit.\nPress 2 to change your password.\nPress 3 to show your information." +
                "\nPress 4 to to delete your account.\nPress 5 to to exit from your account.");
        int enter = scannerInt.nextInt();
        switch (enter) {
            case 1:
                System.out.println("Transfering to Manage Deposit.");
                manageDeposit(id);
                break;
            case 2:
                System.out.println("Transfering to Change Password.");
                changePassword(id);
                break;
            case 3:
                System.out.println("Transfering to your information.");
                showInfo(id);
                break;
            case 4:
                System.out.println("Transfering to Account Deletion.");
                closeAccount(id);
                break;
            case 5:
                System.out.println("Transfering to Main Menu.");
                runMenuSecondTime();
            default:
                System.out.println("Something went wrong!");
                break;
        }

    }

    public void accountPresentMenu() {
        System.out.println("\nPlease enter your Login and Password.");
        System.out.println("Login:");
        String login = scannerLine.nextLine();
        System.out.println("Password:");
        String password = scannerLine.nextLine();
        System.out.println("Special code:");
        int id = scannerInt.nextInt();

        if (dataBase.get(id).getClientLogin().equals(login) && dataBase.get(id).getClientPassword().equals(password)) {
            System.out.println("You accessed to your account.");
            runMenuOfClient(id);
        } else {
            System.out.println("Wrong credentials, please try again.");
            runMenuSecondTime();
        }
    }

    public void accountNotPresentMenu() {
        System.out.println("Please enter your First Name/Last Name.");
        System.out.println("First Name:");
        String firstName = scannerLine.nextLine();
        System.out.println("Last Name:");
        String lastName = scannerLine.nextLine();

//            try {
//                if (dataBase.equals(null)) {
//                    continue;
//                } else {
//                    BufferedReader reader = new BufferedReader(new FileReader("DataOfClients.json"));
//                    String bufferReader = reader.readLine();
//                    dataBase = new Gson().fromJson(
//                            bufferReader, new TypeToken<HashMap<Integer, Client>>() {
//                            }.getType()
//                    );
//                    reader.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        dataBase.put(Client.counterForKey(), new Client(firstName, lastName));

        String OLDdata = gson.toJson(dataBase, gsonType);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DataOfClients.json"));
            writer.write(OLDdata);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Your password is " + dataBase.get(Client.hashKey).getClientPassword());
        System.out.println("Your ID is " + Client.hashKey);
        System.out.println("\nDo you want to add another one? Yes/No");
        String answer = scannerLine.nextLine();
        if (answer.equals("Yes")) {
            accountNotPresentMenu();
        } else if (answer.equals("No")) {
            runMenuSecondTime();
        } else {
            System.out.println("Something went wrong.");
        }
    }

    public void manageDeposit(int id) {
        System.out.println("\nYou are in Manage Deposit menu.\nDo you want to add/withdraw from your account? add/withdraw");
        String accFinishedAsk = scannerLine.nextLine();
        System.out.println("How much do you want to " + accFinishedAsk + " from/to your account?\nYour balance is " +
                "$" + dataBase.get(id).getAmountOnAccount() + ".");
        Double amount = scannerDouble.nextDouble();

        Deposit deposit = new Deposit();
        double set;
        if (accFinishedAsk.equals("add")) {
            set = deposit.calculate(dataBase.get(id).getAmountOnAccount(), amount, "add");
            dataBase.get(id).setAmountOnAccount(set);
            System.out.println("Your new balance is " + dataBase.get(id).getAmountOnAccount() + ".");
        } else if (accFinishedAsk.equals("withdraw")) {
            if ((dataBase.get(id).getAmountOnAccount() - amount) < 0) {
                System.out.println("You don't have enough of money on your account.\nEnter another amount.");
                manageDeposit(id);
            }
            set = deposit.calculate(dataBase.get(id).getAmountOnAccount(), amount, "withdraw");
            dataBase.get(id).setAmountOnAccount(set);
            System.out.println("Your new balance is " + dataBase.get(id).getAmountOnAccount() + ".");
        } else {
            System.out.println("Something went wrong.");
        }

        System.out.println("Do you want to continue with Managing you deposit? y/n");
        accFinishedAsk = scannerLine.nextLine();
        if (accFinishedAsk.equals("y")) {
            manageDeposit(id);
        } else {
            String changedDataInDeposit = gson.toJson(dataBase, gsonType);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("DataOfClients.json"));
                writer.write(changedDataInDeposit);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            runMenuOfClient(id);
        }
    }

    public void changePassword(int id) {
        System.out.println("You are in Change Password menu.\nEnter your new password.");
        String changePass = scannerLine.nextLine();
        dataBase.get(id).setClientPassword(changePass);
        System.out.println("Your password was changed to '" + changePass + "'");
        String changedDataInPassword = gson.toJson(dataBase, gsonType);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DataOfClients.json"));
            writer.write(changedDataInPassword);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runMenuOfClient(id);
    }

    public void showInfo(int id) {
        System.out.println("Here is your information:\nFirst Name: " + dataBase.get(id).getFirstName() +
                "\nLast Name: " + dataBase.get(id).getLastName() + "\nYour Login is: " +
                dataBase.get(id).getClientLogin() + "\nYour temp Pass is: " +
                dataBase.get(id).getClientPassword() + "\nYour special code is: " + dataBase.get(id).getId() +
                "\nYour balance is: " + dataBase.get(id).getAmountOnAccount());
        runMenuOfClient(id);
    }

    public void closeAccount(int id){
        dataBase.remove(id);
        System.out.println("Your account has been deleted.");
        String deleteClient = gson.toJson(dataBase, gsonType);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DataOfClients.json"));
            writer.write(deleteClient);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        runMenuSecondTime();
    }
}