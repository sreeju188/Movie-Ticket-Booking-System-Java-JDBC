package com.movieticket;

import java.util.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class For_User {

    Scanner sc = new Scanner(System.in);
    Connection conn = Sql.getConnection();

    // ---------- SEARCH MOVIES ----------
    public void search() {

        boolean find = true;

        while (find) {
        	System.out.println("Enter the serial number to select the option");
            System.out.println("1. To search by Movie Name");
            System.out.println("2. To search by Theatre Name");
            System.out.println("3. To view All available movies");
            System.out.println("4. To Exit");
            

            int n = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (n) {

                // ---- SEARCH BY MOVIE NAME ----
                case 1:
                    System.out.println("Enter Movie Name: ");
                    String moviename = sc.nextLine();

                    System.out.println("\nShowing available Theatres for " + moviename + " movie:");

                    String st = "SELECT id, theatername, showtime, price " +
                                "FROM movies WHERE moviename='" + moviename + "'";

                    try {
                        int temp = 0;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(st);

                        while (rs.next()) {
                            if (temp == 0) {
                                System.out.println("ID  Theater  ShowTime  Price");
                            }
                            temp++;
                            System.out.println(
                                rs.getInt("id") + " " +
                                rs.getString("theatername") + " " +
                                rs.getTime("showtime") + " " +
                                rs.getInt("price")
                            );
                        }

                        if (temp == 0) {
                            System.out.println("No Theaters Available for this Movie");
                        }
                        System.out.println();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;

                // ---- SEARCH BY THEATRE NAME ----
                case 2:
                    System.out.println("Enter Theatre Name :");
                    String theatername = sc.nextLine();

                    System.out.println("\nDisplaying All Movies in " + theatername + " theatre");

                    String st1 = "SELECT id, moviename, showtime, price " +
                                 "FROM movies WHERE theatername='" + theatername + "'";

                    try {
                        int temp = 0;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(st1);

                        while (rs.next()) {
                            if (temp == 0) {
                                System.out.println("ID  Movie  ShowTime  Price");
                            }
                            temp++;
                            System.out.println(
                                rs.getInt("id") + " " +
                                rs.getString("moviename") + " " +
                                rs.getTime("showtime") + " " +
                                rs.getInt("price")
                            );
                        }

                        if (temp == 0) {
                            System.out.println("No Movies Available in this Theatre");
                        }
                        System.out.println();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;

                // ---- VIEW ALL MOVIES ----
                case 3:
                    System.out.println("\nDisplaying all Shows:");

                    String st2 = "SELECT * FROM movies";

                    try {
                        int temp = 0;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(st2);

                        while (rs.next()) {
                            if (temp == 0) {
                            	System.out.printf("%-5s %-10s %-15s %-10s %-8s %-6s%n",
                            	        "ID", "Theater", "MovieName", "ShowTime", "Seats", "Price");
                            	System.out.println("---------------------------------------------------------------");

                            }
                            temp++;
                            System.out.printf("%-5d %-10s %-15s %-10s %-8d %-6d%n",
                                    rs.getInt("id"),
                                    rs.getString("theatername"),
                                    rs.getString("moviename"),
                                    rs.getTime("showtime"),
                                    rs.getInt("totalseats"),
                                    rs.getInt("price")
                            );
                        }

                        if (temp == 0) {
                            System.out.println("No movies available!");
                        }
                        System.out.println();

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;

                case 4:
                    System.out.println("Exiting Search....");
                    find = false;
                    break;

                default:
                    System.out.println("Invalid Input !!");
            }
        }
    }

    // ---------- BOOK TICKETS ----------
    public void book(String user) {

        System.out.println("Enter Movie ID to Book Ticket!");
        int id = sc.nextInt();
        sc.nextLine();

        String st3 = "SELECT * FROM movies WHERE id=" + id;

        String mn = "";
        String tn = "";
        String time = "";
        int totalseats = 0;
        int price = 0;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(st3);

            if (!rs.next()) {
                System.out.println("No Movie is available with id " + id);
                return;
            }

            mn = rs.getString("moviename");
            tn = rs.getString("theatername"); // ✅ FIXED
            time = rs.getString("showtime");
            totalseats = rs.getInt("totalseats");
            price = rs.getInt("price");

        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Enter No. of Tickets You Need?");
        int need = sc.nextInt();
        sc.nextLine();

        if (need <= totalseats) {

            System.out.println("Enter Seat Numbers (A1,A2...)");
            String seats = sc.nextLine();

            String st4 = "INSERT INTO booking VALUES ('" +
                         user + "','" + mn + "','" + tn + "','" +
                         time + "','" + seats + "')";

            try {
                Statement stmt1 = conn.createStatement();
                stmt1.executeUpdate(st4);

                System.out.println("Tickets Booked Successfully!!");
                System.out.println("Your Total Cost is : " + (price * need));

                int remaining = totalseats - need;
                stmt1.executeUpdate(
                    "UPDATE movies SET totalseats=" + remaining + " WHERE id=" + id
                );

            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            System.out.println("Sorry! Only " + totalseats + " tickets are available!!");
        }
    }

    // ---------- VIEW BOOKINGS ----------
    public void view(String user) {

        String st5 = "SELECT * FROM booking WHERE user='" + user + "'";

        try {
            int temp = 0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(st5);

            while (rs.next()) {
                if (temp == 0) {
                    System.out.println("MovieName  Theatre  ShowTime  Seats");
                }
                temp++;
                System.out.println(
                    rs.getString("moviename") + " " +
                    rs.getString("theater") + " " +   
                    rs.getTime("showtime") + " " +
                    rs.getString("seats")
                );
            }

            if (temp == 0) {
                System.out.println("No Booking History!");
            }
            System.out.println();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
