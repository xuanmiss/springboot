package com.example.redisspringboot.entity;

import java.io.Serializable;

public class HrSalary implements Serializable {
    private int salaryId;
    private int employeeId;
    private String employeeName;
    private String employeeNo;
    private double realSalary;
    private double baseSalary;
    private double incomeTax;
    private double wealSalary;
    private double allowance;
    private double expense;
    private double reduceSalary;
    private String salaryDate;

    public int getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(int salaryId) {
        this.salaryId = salaryId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public double getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(double realSalary) {
        this.realSalary = realSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public double getWealSalary() {
        return wealSalary;
    }

    public void setWealSalary(double wealSalary) {
        this.wealSalary = wealSalary;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getReduceSalary() {
        return reduceSalary;
    }

    public void setReduceSalary(double reduceSalary) {
        this.reduceSalary = reduceSalary;
    }

    public String getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(String salaryDate) {
        this.salaryDate = salaryDate;
    }

    @Override
    public String toString() {
        return "HrSalary{" +
                "salaryId=" + salaryId +
                ", employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", employeeCode='" + employeeNo + '\'' +
                ", realSalary=" + realSalary +
                ", baseSalary=" + baseSalary +
                ", incomeTax=" + incomeTax +
                ", wealSalary=" + wealSalary +
                ", allowance=" + allowance +
                ", expense=" + expense +
                ", reduceSalary=" + reduceSalary +
                ", salaryDate='" + salaryDate + '\'' +
                '}';
    }
}
