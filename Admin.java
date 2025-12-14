package com.movieticket;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Admin extends Sql {

    Scanner sc = new Scanner(System.in);
    For_Admin admin = new For_Admin();

    // ---------- CHECK USER IN DATABASE ----------
    private String dbContains(String user, String pass) {

        String theaterName = "";

        try {
            Connection conn = Sql.getConnection();
            Statement stmt = conn.createStatement();

            // IMPORTANT: column names must match DB
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM admin WHERE user='" + user + "'"
            );

            if (rs.next()) { // user exists
                String dbPass = rs.getString("pass");

                if (dbPass.equals(pass)) { // password matches
                    theaterName = rs.getString("theatername");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return theaterName; // empty if login fails
    }

    // ---------- LOGIN ----------
    public void login() {

        System.out.println("Enter Username: ");
        String user = sc.nextLine().trim();

        System.out.println("Enter Password: ");
        String pass = sc.nextLine().trim();

        String theatre = dbContains(user, pass);

        if (theatre.equals("")) {
            System.out.println("Invalid Login Credentials!");
        } else {
            System.out.println("Login Successful");
            Operations(theatre);
        }
    }

    // ---------- SIGNUP ----------
    public void signup() {

        boolean signup = true;

        while (signup) {

            System.out.println("Enter Username: ");
            String user = sc.nextLine().trim();

            System.out.println("Enter Password: ");
            String pass = sc.nextLine().trim();

            if (!dbContains(user, pass).equals("")) {
                System.out.println("User already Registered!");
                System.out.println("Do you want to signup again? (y/n)");
                char ch = sc.nextLine().charAt(0);
                if (ch == 'n')
                    signup = false;
            } else {

                if (strongness(pass)) {

                    System.out.println("Enter Your Theatre Name: ");
                    String thname = sc.nextLine().trim();

                    String sql = "INSERT INTO admin VALUES ('"
                            + user + "','" + pass + "','" + thname + "')";

                    try {
                        Connection conn = Sql.getConnection();
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate(sql);

                        System.out.println("Account Created Successfully!!");
                        signup = false;

                        System.out.println("Logging You in...");
                        Operations(thname);

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else {
                    System.out.println("Password is Not Strong!");
                    System.out.println("Do you want to signup again? (y/n)");
                    char ch = sc.nextLine().charAt(0);
                    if (ch == 'n')
                        signup = false;
                }
            }
        }
    }

    // ---------- PASSWORD STRENGTH ----------
    private boolean strongness(String pass) {

        if (pass == null)
            return false;

        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        return Pattern.matches(regex, pass);
    }

    // ---------- ADMIN OPERATIONS ----------
    private void Operations(String thname) {

        boolean login = true;

        while (login) {
        	System.out.println("Enter the serial number to select the option");
            System.out.println("1. Add Movies!");
            System.out.println("2. Remove Movies!");
            System.out.println("3. View Bookings!");
            System.out.println("4. Logout!");
            

            int n = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (n) {
                case 1:
                    admin.addMovie(thname);
                    break;
                case 2:
                    admin.removeMovie();
                    break;
                case 3:
                    admin.myTheatreBookings(thname);
                    break;
                case 4:
                    System.out.println("Logout Successful");
                    login = false;
                    break;
                default:
                    System.out.println("Invalid Input");
            }
        }
    }
}
