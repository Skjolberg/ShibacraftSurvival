package net.shibacraft.util;

import java.util.regex.Pattern;

public class StringUtil {

    // Check if it has colors
    public static final Pattern STRIP_AMPERSAND_COLORS = Pattern.compile("(?i)&[0-9A-FK-ORX]");
    public static final Pattern STRIP_AMPERSAND_FORMATS = Pattern.compile("(?i)&[K-O]");
    // Check if it has length between 3 and 15
    public static final Pattern OUT_OF_BOUNDS = Pattern.compile("^.{0,2}$|^.{16,}$");




}
