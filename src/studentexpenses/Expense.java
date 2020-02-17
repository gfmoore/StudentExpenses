/*
 * File         Student.java
 * Author       Gordon Moore
 * Date         17 February 2020
 * Description  A class to represent a student's expenses

 * Version history
 * v0.0.0       Initial version
*/
package studentexpenses;

public class Expense {
  //private members (fields)
  private String eDate;
  private String eType;
  private double eAmount;

  //constructor
  public Expense(String eDate, String eType, double eAmount) {
    this.eDate = eDate;
    this.eType = eType;
    this.eAmount = eAmount;
  }

  //setters and getters
  public void setDate(String eDate) {
    this.eDate = eDate;
  }

  public void setType(String eType) {
    this.eType = eType;
  }

  public void setAmount(double eAmount) {
    this.eAmount = eAmount;
  }

  public String getDate() {
    return this.eDate;
  }

  public String getType() {
    return this.eType;
  }

  public double getAmount() {
    return this.eAmount;
  }
}