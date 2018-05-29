package hc.fcdr.rws.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PatternUtilities
{
    public static void main(final String[] args)
    {
        // String toBeConverted = "a bc&#947;&#948;xy z"; // gamma,delta
        final String toBeConverted =
                "Example_file_áâãäåæçèéêëìíîïðñóòô__PiÃ¯Â¿Â½`'(C__c{os[me_éäÿ{a©_&#945;&#946;&#947;&#948;&#949;&#950;&#951;&#952;&#953;.pdf";
        final String convertedString = convertDecimalNCR2String(toBeConverted);
        System.out.println("toBeConverted = " + toBeConverted);
        System.out.println("    Converted = " + convertedString);

        final String toBeDecoded = "%CE%B1"; // alpha
        final String decodedString = decodeUTF8(toBeDecoded);
        System.out.println("toBeDecoded = "
                + toBeDecoded + ", decoded = " + decodedString);

        final String toBeEncoded = "β";
        final String encodedString = encodeUTF8(toBeEncoded);
        System.out.println("toBeEncoded = "
                + toBeEncoded + ", encoded = " + encodedString);
    }

    /**
     * The method replaces decimal ncr code in the format of {@code &#000;} with the corresponding character in the
     * given input string. Used mainly to fix greek characters.
     *
     * @param s
     *            String to be converted
     * @return the converted string
     */
    public static String convertDecimalNCR2String(final String s)
    {
        if ((s == null) || (s.trim().length() == 0))
            return "";

        final String decimalNCRRegex = "&#(\\d)+;";
        final Pattern pattern = Pattern.compile(decimalNCRRegex);

        // Extract the decimal ncr code using regex.
        final Matcher matcher = pattern.matcher(s);
        final StringBuffer sb = new StringBuffer();

        while (matcher.find())
        {
            final String dNCR = matcher.group();
            final String codePoint = convertDecNCR2CP(dNCR);
            final String convertedUTF8 = convertCP2UTF8(codePoint);
            final String convertedChar = decodeUTF8(convertedUTF8);
            matcher.appendReplacement(sb, convertedChar);
        }

        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String convertDecNCR2CP(String textString)
    {
        String outputString = "";
        textString = textString.replace(" ", "");
        final String[] listArray = textString.split(";");

        for (int i = 0; i < listArray.length; i++)
        {
            if (i > 0)
                outputString += " ";

            final int n =
                    Integer.parseInt(
                            listArray[i].substring(2, listArray[i].length()),
                            10);
            outputString += Integer.toHexString(n);
        }

        return (outputString);
    }

    private static String convertCP2UTF8(String textString)
    {
        String outputString = "";
        textString = textString.trim();

        if (textString.length() == 0)
            return "";

        final String[] listArray = textString.split(" ");

        for (int i = 0; i < listArray.length; i++)
        {
            final int n = Integer.parseInt(listArray[i], 16);

            if (i > 0)
                outputString += " ";

            if (n <= 0x7F)
                outputString += toPercentUTF8(Integer.toHexString(n));
            else if (n <= 0x7FF)
                outputString +=
                        toPercentUTF8(Integer
                                .toHexString(0xC0 | ((n >> 6) & 0x1F))
                                + " " + Integer.toHexString(0x80 | (n & 0x3F)));
            else if (n <= 0xFFFF)
                outputString +=
                        toPercentUTF8(Integer
                                .toHexString(0xE0 | ((n >> 12) & 0x0F))
                                + " "
                                + Integer.toHexString(0x80 | ((n >> 6) & 0x3F))
                                + " " + Integer.toHexString(0x80 | (n & 0x3F)));
            else if (n <= 0x10FFFF)
                outputString +=
                        toPercentUTF8(Integer
                                .toHexString(0xF0 | ((n >> 18) & 0x07))
                                + " "
                                + Integer.toHexString(0x80 | ((n >> 12) & 0x3F))
                                + " "
                                + Integer.toHexString(0x80 | ((n >> 6) & 0x3F))
                                + " " + Integer.toHexString(0x80 | (n & 0x3F)));
            else
                outputString +=
                        "Cannot convert code point to utf8 "
                                + Integer.toHexString(n) + "!";
        }

        return (outputString);
    }

    private static String toPercentUTF8(final String rawUTF8)
    {
        final String[] listArray = rawUTF8.split(" ");
        final StringBuffer sb = new StringBuffer();

        for (final String element : listArray)
            sb.append("%" + element);

        return sb.toString();
    }

    public static String encodeUTF8(final String target)
    {
        try
        {
            return URLEncoder.encode(target, "UTF-8");
        }
        catch (final UnsupportedEncodingException uee)
        {
            System.out.println("Unable to encode to UTF-8:" + uee.getMessage());
            throw new RuntimeException("Unable to encode to UTF-8:", uee);
        }
        catch (final Exception e)
        {
            System.out.println("Invalid target string. Cannot encode");
        }

        return target;
    }

    public static String decodeUTF8(final String target)
    {
        try
        {
            return URLDecoder.decode(target, "UTF-8");
        }
        catch (final UnsupportedEncodingException uee)
        {
            System.out.println("Unable to decode to UTF-8:" + uee.getMessage());
            throw new RuntimeException("Unable to decode to UTF-8:", uee);
        }
        catch (final Exception e)
        {
            System.out.println("Invalid target string. Cannot decode");
        }

        return target;
    }

    public static String maskNumber(final String number, final String mask)
    {
        int index = 0;
        final StringBuilder masked = new StringBuilder();
        for (int i = 0; (index < number.length()) && (i < mask.length()); i++)
        {
            final char c = mask.charAt(i);
            if (c == '#')
            {
                masked.append(number.charAt(index));
                index++;
            }
            else if (c == 'x')
            {
                masked.append(c);
                index++;
            }
            else
                masked.append(c);
        }
        return masked.toString();
    }
}
