package com.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by paia on 8/8/2018.
 */
public class InstructionReport {

    public static void main(String[] args) {
        List<InstructionBuySell> instructionBuySellList = new ArrayList<>();

        instructionBuySellList.add(new InstructionBuySell("foo", "B", 0.50, "SGP", "01 Jan 2018", "02 Jan 2018", 200, 100.25));
        instructionBuySellList.add(new InstructionBuySell("foo", "S", 0.50, "SGP", "01 Jan 2018", "02 Jan 2018", 200, 100.25));
        instructionBuySellList.add(new InstructionBuySell("foo", "S", 0.50, "SGP", "04 Jan 2018", "06 Jan 2018", 200, 100.25));
        instructionBuySellList.add(new InstructionBuySell("bar", "S", 0.22, "AED", "01 Jan 2018", "02 Jan 2018", 450, 150.5));
        instructionBuySellList.add(new InstructionBuySell("bar", "B", 0.22, "AED", "05 Jan 2018", "07 Jan 2018", 250, 50.5));
        instructionBuySellList.add(new InstructionBuySell("bar", "B", 0.5, "GBP", "19 Jan 2018", "20 Jan 2018", 450, 50.5));
        instructionBuySellList.add(new InstructionBuySell("bar", "B", 0.5, "GBP", "25 Jan 2018", "28 Jan 2018", 250, 50.5));
        instructionBuySellList.add(new InstructionBuySell("bar", "S", 0.5, "GBP", "10 Jan 2018", "11 Jan 2018", 350, 50.5));
        instructionBuySellList.add(new InstructionBuySell("bar", "S", 0.5, "GBP", "15 Jan 2018", "16 Jan 2018", 650, 50.5));

        Map<Date, Double> incomingByDay = new HashMap<>();
        Map<Date, Double> outgoingByDay = new HashMap<>();
        Map<String, Double> incomingEntity = new HashMap<>();
        Map<String, Double> outgoingEntity = new HashMap<>();

        for( InstructionBuySell instructionBuySell : instructionBuySellList) {
            if (instructionBuySell.getBuyOrSell().equalsIgnoreCase("S")) {
                instructionBuySell.addAmountToMap(incomingByDay);
                instructionBuySell.addEntityToMap(incomingEntity);
            }
            if (instructionBuySell.getBuyOrSell().equalsIgnoreCase("B")) {
                instructionBuySell.addAmountToMap(outgoingByDay);
                instructionBuySell.addEntityToMap(outgoingEntity);
            }
        }

        Map<String, Double> incomingRanks = incomingEntity.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new)
        );
        Map<String, Double> outgoingRanks = outgoingEntity.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(
                Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new)
        );

        TreeMap<Date, Double> incomingByDaySorted = new TreeMap<>(incomingByDay);
        TreeMap<Date, Double> outgoingByDaySorted = new TreeMap<>(outgoingByDay);

        System.out.println("Incoming Everyday:");
        printDayWiseReport(incomingByDaySorted);

        System.out.println("Outgoing EveryDay:");
        printDayWiseReport(outgoingByDaySorted);

        System.out.println("Incoming Ranks:");
        printRankReport(incomingRanks);

        System.out.println("Outgoing Ranks:");
        printRankReport(outgoingRanks);

    }

    public static <K, V> void printDayWiseReport(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            System.out.println( dateFormat.format((Date)entry.getKey()) + "  => " + entry.getValue());
        }
    }

    public static <K, V> void printRankReport(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "  => " + entry.getValue());
        }
    }
}
