/*
 * File         StudentExpenses.java
 * Author       Gordon Moore
 * Date         3 February 2020
 * Description  A program to control and monitor a student's expenses

 * Version history
 * v0.0.0       Initial version
 * v0.0.1       17 February 2019 Created Objects version
*/

package studentexpenses;

import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class StudentExpenses {

  //global variables
  static Expense[] expenses = new Expense[20];
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
      else if (option.equals("Q")) { 
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
        expenses[i] = new Expense(breakup[0].replace("\"",""), breakup[1].replace("\"",""), Double.parseDouble(breakup[2].replace("\"","")));
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
        if (expenses[i] != null) {
          pw.print("\"" + expenses[i].getDate() + "\",");
          pw.print("\"" + expenses[i].getType() + "\",");
          pw.print("\"" + expenses[i].getAmount() + "\"\n");
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
      if ( expenses[i] != null ) {
        System.out.printf("%2d %10s %-20s %5.2f %n", i+1, expenses[i].getDate(), expenses[i].getType(), expenses[i].getAmount());
      }
      
      i++;
    }
    
    System.out.println("");
  }
  
  public static void addExpense() {
    String d, t, a;
    int i;
    
    //input the date or exit
    System.out.print("Please enter the date (yyyy-mm-dd) or press enter to quit : ");
    d = input.nextLine();
    if (d.equals("")) return;

    //find an empty slot in expenses
    i = 0;
    while (i < 20) {
      if (expenses[i] == null) break;
      i++;
    }
    if (i >= 20) {
      System.out.println("No more space - fixit!");
      return;
    }

    System.out.print("Please enter the 'type' of expense : ");
    t = input.nextLine();
    System.out.printf("Please enter the amount : ");
    a = input.nextLine();
    expenses[i] = new Expense(d, t, Double.parseDouble(a));

    System.out.println("\nNew expense added! \n");
  }
  
  public static void deleteExpense() {
    String s;
    int i, j, k;
    
    listExpenses();
    System.out.println("Which item would you like to delete? (or press enter to quit) : ");
  
    s = input.nextLine();
    if (s.equals("")) return;
    
    //scan through list till get to the selected item
    j = Integer.parseInt(s) - 1; //should check if s a number!
    i = 0;
    for (k = 0; k < 20; k++) {
      if (expenses[k] != null) {
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
      System.out.printf("Is this the record? %s %s %s. %n", expenses[k].getDate(), expenses[k].getType(), expenses[k].getAmount());
      System.out.print("Press Y to delete, otherwise enter to cancel : ");
      s = input.nextLine();
      if (s.toUpperCase().equals("Y")) {
        expenses[k] = null;
        
        //Compact the rest of the data
        while (k < 19) {
          expenses[k] = expenses[k+1];
          k++;
        }
        expenses[k] = null;
      }
      else {
        return;
      }     
    }
    System.out.println("\nExpense deleted!\n");
  }
  
  public static void editExpense() {
    String s;
    int i, j, k;

    listExpenses();
    System.out.print("Which item would you like to edit? (or press enter to quit) : ");
    s = input.nextLine();
    if (s.equals("")) return;

    //scan through list till get to the selected item    
    j = Integer.parseInt(s) - 1; //should check if s a number!
    i = 0;
    for (k = 0; k < 20; k++) {
      if (expenses[k] != null) {
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
      System.out.printf("Is this the record? %s %s %s. %n", expenses[k].getDate(), expenses[k].getType(), expenses[k].getAmount());
      System.out.print("Press Y to edit, otherwise 'enter' to cancel.");
      s = input.nextLine();
      if (s.toUpperCase().equals("Y")) {
        System.out.println("Please enter a new value or enter to leave as is.");

        System.out.printf("Date %s : ", expenses[k].getDate());
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k].setDate(s);
        }
        
        System.out.printf("Type %s : ", expenses[k].getType());
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k].setType(s);
        }
        
        System.out.printf("Amount %s : ", expenses[k].getAmount());
        s = input.nextLine();
        if (!s.equals("")) {
          expenses[k].setAmount(Double.parseDouble(s));
        }
        
        System.out.println("\nExpense updated! \n");
      }
      else {
        return;
      }     

    }
  }
  
  public static void totalExpenses() {
    double total = 0.0;
    for (int i = 0; i < expenses.length; i++) {
      if (expenses[i] != null) {
        total += expenses[i].getAmount();
      }
    }
    
    System.out.printf("%nThe total expenses are £%6.2f %n%n", total);

  }

}
