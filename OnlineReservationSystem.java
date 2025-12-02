import java.sql.*;
import java.util.*;

class DB {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/reservation_system",
                    "root",
                    "your_password"    // <-- Change your MySQL password
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class OnlineReservationSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("========= ONLINE RESERVATION SYSTEM =========\n");

        if (!login()) {
            System.out.println("\nInvalid login! Access Denied.");
            return;
        }

        System.out.println("\nLogin Successful!\n");

        while (true) {
            System.out.println("1. Make Reservation");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Exit");
            System.out.print("Select Option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    makeReservation();
                    break;

                case 2:
                    cancelReservation();
                    break;

                case 3:
                    System.out.println("\nThank you for using the system!");
                    return;

                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }
    }

    // LOGIN
    public static boolean login() {
        System.out.print("Enter Login ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        try (Connection con = DB.getConnection()) {

            String sql = "SELECT * FROM users WHERE login_id=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, id);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            return rs.next(); // true if login exists

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // MAKE RESERVATION
    public static void makeReservation() {

        System.out.print("Passenger Name: ");
        String name = sc.nextLine();

        System.out.print("Train Number: ");
        String trainNo = sc.nextLine();

        System.out.print("Train Name: ");
        String trainName = sc.nextLine();

        System.out.print("Class Type (Sleeper/AC/General): ");
        String classType = sc.nextLine();

        System.out.print("Date of Journey (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("From: ");
        String from = sc.nextLine();

        System.out.print("To: ");
        String to = sc.nextLine();

        String pnr = "PNR" + new Random().nextInt(999999);

        try (Connection con = DB.getConnection()) {

            String sql = "INSERT INTO reservations VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, pnr);
            pst.setString(2, name);
            pst.setString(3, trainNo);
            pst.setString(4, trainName);
            pst.setString(5, classType);
            pst.setString(6, date);
            pst.setString(7, from);
            pst.setString(8, to);

            pst.executeUpdate();

            System.out.println("\nReservation Successful!");
            printTicket(pnr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PRINT TICKET
    public static void printTicket(String pnr) {

        try (Connection con = DB.getConnection()) {

            String sql = "SELECT * FROM reservations WHERE pnr=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                System.out.println("\n---- Ticket Details ----");
                System.out.println("PNR Number       : " + rs.getString("pnr"));
                System.out.println("Passenger Name   : " + rs.getString("passenger_name"));
                System.out.println("Train Number     : " + rs.getString("train_number"));
                System.out.println("Train Name       : " + rs.getString("train_name"));
                System.out.println("Class Type       : " + rs.getString("class_type"));
                System.out.println("Date of Journey  : " + rs.getString("date_of_journey"));
                System.out.println("From             : " + rs.getString("from_station"));
                System.out.println("To               : " + rs.getString("to_station"));
                System.out.println("-------------------------\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CANCEL RESERVATION
    public static void cancelReservation() {

        System.out.print("Enter PNR number to cancel: ");
        String pnr = sc.nextLine();

        try (Connection con = DB.getConnection()) {

            // Check if PNR exists
            String check = "SELECT * FROM reservations WHERE pnr=?";
            PreparedStatement pst = con.prepareStatement(check);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                System.out.println("\nInvalid PNR! No record found.");
                return;
            }

            printTicket(pnr);

            System.out.print("Confirm Cancellation? (yes/no): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {

                String delete = "DELETE FROM reservations WHERE pnr=?";
                PreparedStatement pst2 = con.prepareStatement(delete);
                pst2.setString(1, pnr);
                pst2.executeUpdate();

                System.out.println("\nYour reservation has been cancelled successfully.");

            } else {
                System.out.println("\nCancellation Aborted.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}