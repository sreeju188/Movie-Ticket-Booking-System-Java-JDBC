

package com.movieticket;

import java.util.*;
public class Main_Program {
        static Admin admin = new Admin();
        static User user = new User();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean end=true;
        while(end){
            System.out.println("Welcome To Movie Ticket Booking System!!");
            System.out.println("----------------------------------------");
            System.out.println("Enter 1 to Login ");
            System.out.println("Enter 2 to Signup");
            System.out.println("Enter 3 to exit");

            int key=sc.nextInt();
            switch(key){
                case 1:
                	System.out.println("Enter 1 to Login ");
                    System.out.println("Enter 2 to Signup");
                    System.out.println("Enter 3 to exit");
                    int mode=sc.nextInt();
                    switch(mode){
                        case 1:
                            admin.login();
                            break;
                        case 2:
                            admin.signup();
                            break;
                        case 3:
                            System.out.println("Exiting from Movie Ticket Booking System!!");
                            System.out.println("Bye Bye");
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Enter 1 to Login ");
                    System.out.println("Enter 2 to Signup");
                    System.out.println("Enter 3 to exit");
                    int mode1=sc.nextInt();
                    switch(mode1){
                        case 1:
                            user.login();
                            break;
                        case 2:
                            user.signup();
                            break;
                        default:
                            System.out.println("Exiting from Movie Ticket Booking System!!");
                            System.out.println("Bye Bye");
                            break;
                    }
                    break;
                default:
                    System.out.println("Exiting from Movie Ticket Booking System!!");
                    System.out.println("Bye Bye");
                    end=false;
                    break;
            }
        }
    }
}
