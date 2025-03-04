import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

// Interface for ATM operations
interface ATMOperations {
    void checkBalance();
    void deposit(double amount);
    void withdraw(double amount);
}

// Class to represent the user's bank account
class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount. Please enter a positive value.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Insufficient funds. Your current balance is $" + String.format("%.2f", balance));
        } else {
            JOptionPane.showMessageDialog(null, "Invalid withdrawal amount. Please enter a positive value.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

// Class to represent the ATM machine, implementing the ATMOperations interface
class ATM implements ATMOperations {
    BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    @Override
    public void checkBalance() {
        JOptionPane.showMessageDialog(null, "Your current balance is $" + String.format("%.2f", account.getBalance()));
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount);
        JOptionPane.showMessageDialog(null, "Deposit successful. New balance: $" + String.format("%.2f", account.getBalance()));
    }

    @Override
    public void withdraw(double amount) {
        double previousBalance = account.getBalance();
        account.withdraw(amount);
        if (previousBalance != account.getBalance()) {
            JOptionPane.showMessageDialog(null, "Withdrawal successful. New balance: $" + String.format("%.2f", account.getBalance()));
        }
    }
}

// Class to create a GUI for the ATM
class ATMGUI extends JFrame {
    private ATM atm;
    private JTextField amountField;
    private JLabel balanceLabel;
    private Color primaryColor = new Color(44, 62, 80);
    private Color accentColor = new Color(41, 128, 185);
    private Color textColor = Color.WHITE;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        setTitle("ATM Simulation");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createUI();
    }

    private void createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(primaryColor);

        // Balance display panel
        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(primaryColor);
        balanceLabel = new JLabel("Current Balance: $" + String.format("%.2f", atm.account.getBalance()));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setForeground(textColor);
        balancePanel.add(balanceLabel);
        panel.add(balancePanel, BorderLayout.NORTH);

        // Center panel for input field
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(primaryColor);
        JLabel label = new JLabel("Amount: $");
        label.setForeground(textColor);
        amountField = new JTextField(10);
        centerPanel.add(label);
        centerPanel.add(amountField);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(primaryColor);

        JButton checkBalanceButton = createStyledButton("Check Balance");
        JButton depositButton = createStyledButton("Deposit");
        JButton withdrawButton = createStyledButton("Withdraw");
        JButton exitButton = createStyledButton("Exit");

        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        checkBalanceButton.addActionListener(e -> {
            atm.checkBalance();
            updateBalanceLabel();
        });

        depositButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                atm.deposit(amount);
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
        });

        withdrawButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                atm.withdraw(amount);
                updateBalanceLabel();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
        });

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Thank you for using the ATM. Goodbye!");
            System.exit(0);
        });

        add(panel);
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(accentColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setForeground(accentColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(accentColor);
                button.setForeground(textColor);
            }
        });

        return button;
    }

    // Update the balance display
    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: $" + String.format("%.2f", atm.account.getBalance()));
    }
}

// Main class to run the ATM simulation with a GUI
public class ATMSimulationWithInterfaceTask3 {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000.00);
        ATM atm = new ATM(userAccount);

        SwingUtilities.invokeLater(() -> {
            ATMGUI gui = new ATMGUI(atm);
            gui.setVisible(true);
        });
    }
}
