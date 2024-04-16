package ru.itmo.application.model;

import java.util.Map;
import java.util.TreeMap;

public record BankFinancialTerms(
        Integer percentsForDebit,
        Long creditCommission,
        Long unverifiedAccountLimit,
        TreeMap<Long, Integer> depositPercents) {
}
