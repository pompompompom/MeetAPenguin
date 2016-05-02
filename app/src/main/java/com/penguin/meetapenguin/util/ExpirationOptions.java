package com.penguin.meetapenguin.util;

/**
 * This Class hold the expiration options that I user can chosse when sharing his information with another contact.
 */
public enum ExpirationOptions {
    ONE_MONTH(0, 1),
    THREE_MONTHS(1, 3),
    SIX_MONTHS(2, 6),
    ONE_YEAR(3, 12),
    TWO_YEARS(4, 24);

    private final int value;
    private final int months;

    private ExpirationOptions(int value, int months) {
        this.value = value;
        this.months = months;
    }

    public int getIndexValue() {
        return value;
    }

    public int getMonths() {
        return months;
    }
}
