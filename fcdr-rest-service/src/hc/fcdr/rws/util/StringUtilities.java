package hc.fcdr.rws.util;

public class StringUtilities
{
    private static final int      PAD_LIMIT = 8192;
    private static final String[] PADDING   = new String[Character.MAX_VALUE];

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @return Returned data
     */
    public static String center(final String str, final int size)
    {
        return center(str, size, ' ');
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @param padChar
     *            Placeholder
     * @return Returned data
     */
    public static String center(String str, final int size, final char padChar)
    {
        if ((str == null) || (size <= 0))
            return str;

        final int strLen = str.length();
        final int pads = size - strLen;

        if (pads <= 0)
            return str;

        str = rightJustify(str, strLen + (pads / 2), padChar);
        str = leftJustify(str, size, padChar);

        return str;
    }

    /**
     * @param a
     *            Placeholder
     * @param b
     *            Placeholder
     * @return Returned data
     */
    public static int indexOf(final StringBuffer a, final String b)
    {
        return StringUtilities.indexOf(a, b, 0);
    }

    /**
     * @param a
     *            Placeholder
     * @param b
     *            Placeholder
     * @param pos
     *            Placeholder
     * @return Returned data
     */
    public static int indexOf(final StringBuffer a, final String b,
            final int pos)
    {
        int c = pos;

        while (c <= (a.length() - b.length()))
        {
            final String sub = a.substring(c, c + b.length());
            if (sub.equals(b))
                return c;
            c++;
        }

        return -1;
    }

    /**
     * @param str
     *            Placeholder
     * @return Returned data
     */
    public static boolean isAlpha(final String str)
    {
        if (str == null)
            return false;

        final int sz = str.length();

        for (int i = 0; i < sz; i++)
            if (Character.isLetter(str.charAt(i)) == false)
                return false;

        return true;
    }

    /**
     * @param str
     *            Placeholder
     * @return Returned data
     */
    public static boolean isAlphaOrSpace(final String str)
    {
        if (str == null)
            return false;

        final int sz = str.length();

        for (int i = 0; i < sz; i++)
            if ((Character.isLetter(str.charAt(i)) == false)
                    && (Character.isWhitespace(str.charAt(i)) == false))
                return false;

        return true;
    }

    /**
     * @param str
     *            Placeholder
     * @return Returned data
     */
    public static boolean isEmpty(final String str)
    {
        return ((str == null) || (str.length() == 0));
    }

    /**
     * Verifies that the given string is of len length.
     * 
     * @param s
     *            Placeholder
     * @param len
     *            Placeholder
     * @return Returned data
     */
    public static boolean isNumericL(final String s, final int len)
    {
        return (isNumeric(s) && isLength(s, len));
    }

    /**
     * @param str
     *            Placeholder
     * @return Returned data
     */
    public static boolean isNumeric(final String str)
    {
        if ((str == null) || (str.trim().length() == 0))
            return false;

        final int sz = str.length();

        for (int i = 0; i < sz; i++)
            if (Character.isDigit(str.charAt(i)) == false)
                return false;

        return true;
    }

    public static boolean isLength(final String s, final int len)
    {
        if (s.trim().length() == len)
            return true;
        return false;
    }

    /**
     * @param str
     *            Placeholder
     * @return Returned data
     */
    public static boolean isValidName(final String str)
    {
        if (str == null)
            return false;

        final int sz = str.length();

        for (int i = 0; i < sz; i++)
            if (!((Character.isLetter(str.charAt(i)) == true)
                    || (Character.isWhitespace(str.charAt(i)) == true)
                    || ((str.charAt(i)) == '.') || ((str.charAt(i)) == '-')
                    || ((str.charAt(i)) == '(') || ((str.charAt(i)) == ')')
                    || ((str.charAt(i)) == '\'')))
                return false;

        return true;
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @return Returned data
     */
    public static String leftJustify(final String str, final int size)
    {
        return leftJustify(str, size, ' ');
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @param padChar
     *            Placeholder
     * @return Returned data
     */
    public static String leftJustify(final String str, final int size,
            final char padChar)
    {
        if (str == null)
            return null;

        final int pads = size - str.length();

        if (pads <= 0)
            return str; // returns original String when possible

        if (pads > PAD_LIMIT)
            return leftJustify(str, size, String.valueOf(padChar));

        return str.concat(padding(pads, padChar));
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @param padStr
     *            Placeholder
     * @return Returned data
     */
    public static String leftJustify(final String str, final int size,
            String padStr)
    {
        if (str == null)
            return null;

        if (isEmpty(padStr))
            padStr = " ";

        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;

        if (pads <= 0)
            return str; // returns original String when possible

        if ((padLen == 1) && (pads <= PAD_LIMIT))
            return leftJustify(str, size, padStr.charAt(0));

        if (pads == padLen)
            return str.concat(padStr);
        else if (pads < padLen)
            return str.concat(padStr.substring(0, pads));
        else
        {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++)
                padding[i] = padChars[i % padLen];

            return str.concat(new String(padding));
        }
    }

    /**
     * @param str
     *            Placeholder
     * @param overlay
     *            Placeholder
     * @param start
     *            Placeholder
     * @param end
     *            Placeholder
     * @return Returned data
     */
    public static String overlay(final String str, String overlay, int start,
            int end)
    {
        if (str == null)
            return null;

        if (overlay == null)
            overlay = "";

        final int len = str.length();
        if (start < 0)
            start = 0;

        if (start > len)
            start = len;

        if (end < 0)
            end = 0;

        if (end > len)
            end = len;

        if (start > end)
        {
            final int temp = start;
            start = end;
            end = temp;
        }

        return new StringBuffer(((len + start) - end) + overlay.length() + 1)
                .append(str.substring(0, start)).append(overlay)
                .append(str.substring(end)).toString();
    }

    /**
     * @param repeat
     *            Placeholder
     * @param padChar
     *            Placeholder
     * @return Returned data
     */
    private static String padding(final int repeat, final char padChar)
    {
        // be careful of synchronization in this method
        // we are assuming that get and set from an array index is atomic
        String pad = PADDING[padChar];

        if (pad == null)
            pad = String.valueOf(padChar);

        while (pad.length() < repeat)
            pad = pad.concat(pad);

        PADDING[padChar] = pad;

        return pad.substring(0, repeat);
    }

    /**
     * @param str
     *            Placeholder
     * @param repeat
     *            Placeholder
     * @return Returned data
     */
    public static String repeat(final String str, final int repeat)
    {
        if (str == null)
            return null;

        if (repeat <= 0)
            return "";

        final int inputLength = str.length();

        if ((repeat == 1) || (inputLength == 0))
            return str;

        if ((inputLength == 1) && (repeat <= PAD_LIMIT))
            return padding(repeat, str.charAt(0));

        final int outputLength = inputLength * repeat;
        switch (inputLength)
        {
            case 1:
                final char ch = str.charAt(0);
                final char[] output1 = new char[outputLength];
                for (int i = repeat - 1; i >= 0; i--)
                    output1[i] = ch;
                return new String(output1);
            case 2:
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char[] output2 = new char[outputLength];
                for (int i = (repeat * 2) - 2; i >= 0; i--, i--)
                {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default:
                final StringBuffer buf = new StringBuffer(outputLength);
                for (int i = 0; i < repeat; i++)
                    buf.append(str);
                return buf.toString();
        }
    }

    /**
     * @param a
     *            Placeholder
     * @param b
     *            Placeholder
     * @param c
     *            Placeholder
     * @return Returned data
     */
    public static String replaceAll(final String a, final String b,
            final String c)
    {
        final StringBuffer d = new StringBuffer(a);
        StringUtilities.replaceAll(d, b, c);
        return d.toString();
    }

    /**
     * @param a
     *            Placeholder
     * @param b
     *            Placeholder
     * @param c
     *            Placeholder
     * @return Returned data
     */
    public static int replaceAll(final StringBuffer a, final String b,
            final String c)
    {
        int pos = StringUtilities.indexOf(a, b);

        while (pos != -1)
        {
            a.delete(pos, pos + b.length());
            a.insert(pos, c);
            pos = StringUtilities.indexOf(a, b, pos + c.length());
        }

        return 0;
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @return Returned data
     */
    public static String rightJustify(final String str, final int size)
    {
        return rightJustify(str, size, ' ');
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @param padChar
     *            Placeholder
     * @return Returned data
     */
    public static String rightJustify(final String str, final int size,
            final char padChar)
    {
        if (str == null)
            return null;

        final int pads = size - str.length();

        if (pads <= 0)
            return str; // returns original String when possible

        if (pads > PAD_LIMIT)
            return rightJustify(str, size, String.valueOf(padChar));

        return padding(pads, padChar).concat(str);
    }

    /**
     * @param str
     *            Placeholder
     * @param size
     *            Placeholder
     * @param padStr
     *            Placeholder
     * @return Returned data
     */
    public static String rightJustify(final String str, final int size,
            String padStr)
    {
        if (str == null)
            return null;

        if (isEmpty(padStr))
            padStr = " ";

        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;

        if (pads <= 0)
            return str; // returns original String when possible

        if ((padLen == 1) && (pads <= PAD_LIMIT))
            return rightJustify(str, size, padStr.charAt(0));

        if (pads == padLen)
            return padStr.concat(str);
        else if (pads < padLen)
            return padStr.substring(0, pads).concat(str);
        else
        {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++)
                padding[i] = padChars[i % padLen];

            return new String(padding).concat(str);
        }
    }

    /**
     * @param aa
     *            Placeholder
     * @param b
     *            Placeholder
     * @return Returned data
     */
    public static String[] split(final String aa, final String b)
    {
        final StringBuffer a = new StringBuffer(aa);
        final java.util.Vector<String> s = new java.util.Vector<String>();
        int prev = 0;
        int pos = StringUtilities.indexOf(a, b);

        while (pos != -1)
        {
            s.add(a.substring(prev, pos /*- 1 */));
            prev = pos + 1;
            pos = StringUtilities.indexOf(a, b, prev);
        }

        if (prev < aa.length())
            s.add(a.substring(prev, aa.length()));

        final String ss[] = new String[s.size()];

        for (int i = 0; i < s.size(); i++)
            ss[i] = s.get(i);

        return ss;
    }
}
