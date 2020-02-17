/*
 * File         StudentExpenses.java
 * Author       Gordon Moore
 * Date         3 February 2020
 * Description  A program to control and monitor a student's expenses

 * Version history
 * v0.0.0       Initial version
*/

package studentexpenses;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class StudentExpenses {

  //global variables
  static String[][] expenses = new String[20][3];
  static Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    System.out.println("Student Expenses");
    System.out.println("================\n");
    
    Scanner input = new Scanner(System.in);
    boolean doMenu = true;
    String option;

    //import expenses.csv
    importExpenses();
    
    //keep running the program till the use keys in Q for quit
    while (doMenu) {
      displayMenu();
            
      //get the user input
      option = input.nextLine();
      System.out.println("");  //to get the extra line
      option = option.toUpperCase();
      
      if (option.equals("L")) {
        listExpenses();
      }
      else if (option.equals("A")) {
        addExpense();
      }
      else if (option.equals("D")) {
        deleteExpense();
      }
      else if (option.equals("E")) {
        editExpense();
      }
      else if (option.equals("T")) {
        totalExpenses();
      }
      else if (option.equals("Q")) {   //equivalent to option == "Q" 
        doMenu = false;
      }
      else {
        System.out.println("Please enter a valid option!!\n");
      }
    }
    
    exportExpenses();

    input.close();
    System.out.println("\n\nProgram finished...");
  }

  public static void importExpenses() {

    try {
      FileReader reader = new FileReader("./expenses.csv");
      Scanner fileIn = new Scanner(reader);
      int i = 0;
      String[] breakup;
      String expense;

      while (fileIn.hasNext()) {
        expense = fileIn.nextLine();
        breakup = expense.split(",");
        
        //load into array expenses
        expenses[i][0] = breakup[0].replace("\"","");  //the date
        expenses[i][1] = breakup[1].replace("\"","");  //the item
        expenses[i][2] = breakup[2].replace("\"","");  //the amount

        i++;
        if (i == 20) {
          System.out.println("Too many items in list!\nFix it!");
          break;
        }
      }
      fileIn.close();
    }
    catch(Exception e) {
      System.out.println("Cannot find expenses.csv file!\n");
    }

    System.out.println("Expenses.csv loaded.\n");
  }
  
  public static void exportExpenses() {
    try {
      PrintWriter pw = new PrintWriter("expenses.csv");
      int i = 0;
      while (i < expenses.length) {
        if (expenses[i][0] != null && !expenses[i][0].equals("")) {
          pw.print("\"" + expenses[i][0] + "\",");
          pw.print("\"" + expenses[i][1] + "\",");
          pw.print("\"" + expenses[i][2] + "\"\n");
        }
        i++;
      }
      pw.close();
    }
    catch (Exception e) {
      System.out.println(e);
      System.out.println("File write exception!");
    }
    System.out.println("Expenses.csv saved.");
  }

  public static void displayMenu() {
    //display the menu
    System.out.println("+------------------------+");
    System.out.println("| L List the expenses.   |");
    System.out.println("| A Add a new expense.   |");
    System.out.println("| D Delete an expense.   |");
    System.out.println("| E Edit an expense.     |");
    System.out.println("| T Total the expenses.  |");
    System.out.println("|                        |");
    System.out.println("| Q Quit.                |");
    System.out.println("+------------------------+\n");
    System.out.println("Please input an option letter : ");
  }
  
  public static void listExpenses() {
    int i = 0;
    
    //System.out.println("List the Expenses");
    //System.out.println("-----------------");
    System.out.println("   Date       Type                 Amount");
    System.out.println("   ---------- -------------------- ------");
    
    while (i < 20) {     
      if ( expenses[i][0] != null ) {
        System.out.printf("%2d %10s %-20s %5.2f %n", i+1, expenses[i][0], expenses[i][1], Double.parseDouble(expenses[i][2]));
      }
      
      i++;
    }
    
    System.out.println("");
  }
  
  public static void addExpense() {
    System.out.println("Add an Expense");
    System.out.println("--------------");
    
    String s;
    int i;
    
    //input the date or exit
    System.out.print("Please enter the date (yyyy-mm-dd) or press enter to quit : ");
    s = input.nextLine();
    if (s.equals("")) return;
    //find an empty slot in expenses
    i = 0;
    while (i < 20) {
      if (expenses[i][0] == null) break;
      i++;
    }
    if (i >= 20) {
      System.out.println("No more space - fixit!");
    }
    expenses[i][0] = s;
    System.out.print("Please enter the 'type' of expense : ");
    s = input.nextLine();
    expenses[i][1] = s;
    System.out.printf("Please enter the amount : ");
    s = input.nextLine();
    expenses[i][2] = s;
    System.out.println("\nNew expense added! \n");
  }
  
  public static void deleteExpense() {
//    System.out.println("Delete an Expense");
//    System.out.println("-----------------");
    String s;
    int i, j, k;
    
    listExpenses();
    System.out.println("Which item would you like to delete? (or press enter to quit) : ");
  
    s = input.nextLine();
    if (s.equals("")) return;
    
    j = Integer.parseInt(s); //should check if s a number!
    //scan through list till get to the selected item
    i = 0;
    for (k = 0; k < 20; k++) {
      if (expenses[k][0] != null) {
        if (i == j) break;
        i++;
      }
    }
    //k holds the actual record number
    //display contents of k
    if (k >= 20) {
      System.out.println("No such record, retry.");
    }  
    else {
      k = k-1; //adjust the index
      System.out.printf("Is this the record? %s %s %s. %n", expenses[k][0], expenses[k][1], expenses[k][2]);
      System.out.print("Press Y to delete, otherwise enter to cancel : ");
      s = input.nextLine();
      if (s.toUpperCase().equals("Y")) {
        //Compact the rest of the data
        while (k < 19) {
          expenses[k][0] = expenses[k+1][0];
          expenses[k][1] = expenses[k+1][1];
          expenses[k][2] = expenses[k+1][2];
          k++;
        }
        expenses[k][0] = null;
        expenses[k][1] = null;
        expenses[k][2] = null;
      }
      else {
        return;
      }     

    }
  
  }
  
  public static void editExpense() {
    String s;
    int i, j, k;

    listExpenses();
    System.out.print("Which item would you like to edit? (or press enter to quit) : ");
    s = input.nextLine();
    if (s.equals("")) return;
    
        j = Integer.parseInt(s); //should check if s a number!
    //scan through list till get to the selected item
    i = 0;
    for (k = 0; k < 20; k++) {
      if (expenses[k][0] != null) {
        if (i == j) break;
        i++;
      }
    }
    //k holds the actual record number
    //display contents of k
    if (k >= 20) {
      System.out.println("No such record, please retry.");
    }  
    else {
      k = k-1; //adjust the index
      System.out.printf("Is this the record? %s %s %s. %n", expenses[k][0], expenses[k][1], expenses[k][2]);
      System.out.print("Press Y to edit, otherwise 'enter' to cancel.");
      s = input.nextLine();
      if (s.toUpperCase().equals("Y")) {
        System.out.println("Please enter a new value or enter to leave as is.");

        System.out.printf("Date %s : ", expenses[k][0]);
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k][0] = s;
        }
        
        System.out.printf("Type %s : ", expenses[k][1]);
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k][1] = s;
        }
        
        System.out.printf("Amount %s : ", expenses[k][2]);
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k][2] = s;
        }
        
        System.out.println("\nRecord updated! \n");
      }
      else {
        return;
      }     

    }

    System.out.println("");
  }
  
  public static void totalExpenses() {
    double total = 0.0;
    for (int i = 0; i < expenses.length; i++) {
      if (expenses[i][0] != null) {
        total += Double.parseDouble(expenses[i][2]);
      }
    }
    
    System.out.printf("The total expenses are £%6.2f %n%n", total);
    
    System.out.println("");
  }


}
