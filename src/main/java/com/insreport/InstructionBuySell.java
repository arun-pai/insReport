package com.insreport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by paia on 8/8/2018.
 */
public class InstructionBuySell {

    private String entity;

    private String buyOrSell; // B or S

    private double agreedFx;

    private String currency;

    private Date instructionDate;

    private Date settlementDate;

    private long units;

    private double pricePerUnit;

    private double amount;

    private static final String C_INSTRUCTION_BUY = "B";
    private static final String C_INSTRUCTION_SELL = "S";

    InstructionBuySell (String entity, String buyOrSell, double agreedFx, String currency,
                        String instructionDate, String settlementDate, long units, double pricePerUnit) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        this.entity = entity;
        this.buyOrSell = buyOrSell;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.units = units;
        this.pricePerUnit = pricePerUnit;

        this.amount = pricePerUnit * units * agreedFx;

        try {
            this.instructionDate = dateFormat.parse(instructionDate);
            this.settlementDate = dateFormat.parse(settlementDate);
        } catch (Exception e) {
            System.out.println("Error occurred while parsing the date" + e);
        }

        adjustSettlementDateToWorkinDay();
    }

    private void adjustSettlementDateToWorkinDay() {
        Calendar settlementCal = Calendar.getInstance();
        settlementCal.setTime(settlementDate);

        if (currency.equalsIgnoreCase("AED") || currency.equalsIgnoreCase("SAR")) {
            if (settlementCal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                settlementCal.add(Calendar.DATE, 2);
            }
            if (settlementCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                settlementCal.add(Calendar.DATE, 1);
            }
        } else {
            if (settlementCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                settlementCal.add(Calendar.DATE,2);
            }
            if (settlementCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                settlementCal.add(Calendar.DATE,1);
            }
        }

        settlementDate = settlementCal.getTime();
    }

    public void addAmountToMap(Map<Date, Double> map) {
        if (!map.containsKey(settlementDate)) {
            map.put(settlementDate,amount);
        } else {
            map.put(settlementDate,map.get(settlementDate) + amount);
        }
    }

    public void addEntityToMap(Map<String, Double> map) {
        if(!map.containsKey(entity)){
            map.put(entity,amount);
        } else {
            map.put(entity,map.get(entity) + amount);
        }
    }

    public boolean isInstructionBuy() {
        return (buyOrSell.equalsIgnoreCase(C_INSTRUCTION_BUY));
    }

    public boolean isInstructionSell() {
        return (buyOrSell.equalsIgnoreCase(C_INSTRUCTION_SELL));
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getBuyOrSell() {
        return buyOrSell;
    }

    public void setBuyOrSell(String buyOrSell) {
        this.buyOrSell = buyOrSell;
    }

    public double getAgreedFx() {
        return agreedFx;
    }

    public void setAgreedFx(double agreedFx) {
        this.agreedFx = agreedFx;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public long getUnits() {
        return units;
    }

    public void setUnits(long units) {
        this.units = units;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

}
