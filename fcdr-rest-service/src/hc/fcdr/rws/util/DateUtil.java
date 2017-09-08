package hc.fcdr.rws.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtil
{

    public static final Date add(Date when, TimeZone tz, int field, int amount)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();

        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        cal.add(field, amount);
        return cal.getTime();
    }

    final public static Date beginOfDate(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();

        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        cal.clear();
        cal.set(year, month, day);

        return cal.getTime();
    }

    final public static Date beginOfMonth()
    {
        return beginOfMonth(new Date(), null);
    }

    final public static Date beginOfMonth(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        cal.clear();
        cal.set(year, month, 1);
        return cal.getTime();
    }

    final public static Date beginOfYear()
    {
        return beginOfYear(new Date(), null);
    }

    final public static Date beginOfYear(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        final int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(year, Calendar.JANUARY, 1);
        return cal.getTime();
    }

    public static int compare(String date1, String date2)
    {
        java.sql.Timestamp ts1 = java.sql.Timestamp.valueOf(
                date1 + " 00:00:00");
        java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf(
                date2 + " 00:00:00");

        return ts1.compareTo(ts2);
    }

    public static final int dayMonthOfDate(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    final public static Date endOfDate(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();

        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());// don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        cal.clear();
        cal.set(year, month, day + 1);
        cal.setTimeInMillis(cal.getTimeInMillis() - 1);

        return cal.getTime();
    }

    final public static Date endOfMonth()
    {
        return endOfMonth(new Date(), null);
    }

    final public static Date endOfMonth(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int monthDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.clear();
        cal.set(year, month, monthDays + 1);
        cal.setTimeInMillis(cal.getTimeInMillis() - 1);
        return cal.getTime();
    }

    final public static Date endOfYear()
    {
        return endOfYear(new Date(), null);
    }

    final public static Date endOfYear(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        final int year = cal.get(Calendar.YEAR);
        cal.clear();
        cal.set(year + 1, Calendar.JANUARY, 1);
        cal.setTimeInMillis(cal.getTimeInMillis() - 1);
        return cal.getTime();
    }

    public static String getAlfrescoFolderCurrentDateString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat df2 = new DecimalFormat("00");
        DecimalFormat df4 = new DecimalFormat("0000");

        String runDay = df2.format(now.get(Calendar.DAY_OF_MONTH));
        String runMonth = df2.format(now.get(Calendar.MONTH) + 1);
        String runYear = df4.format(now.get(Calendar.YEAR));

        s += "Y" + runYear + ".M" + runMonth + ".D" + runDay;

        return s;
    }

    public static String getCurrentDateOfTodayString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat dfMonthDay = new DecimalFormat("00");
        DecimalFormat dfYear = new DecimalFormat("0000");
        String runMonth = dfMonthDay.format(now.get(Calendar.MONTH) + 1);
        String runDay = dfMonthDay.format(now.get(Calendar.DAY_OF_MONTH));
        String runYear = dfYear.format(now.get(Calendar.YEAR));
        s += runYear + runMonth + runDay;

        return s;
    }

    public static String getCurrentDateOfYesterdayString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat dfMonthDay = new DecimalFormat("00");
        DecimalFormat dfYear = new DecimalFormat("0000");

        now.add(Calendar.DAY_OF_MONTH, -1);

        String runMonth = dfMonthDay.format(now.get(Calendar.MONTH) + 1);
        String runDay = dfMonthDay.format(now.get(Calendar.DAY_OF_MONTH));
        String runYear = dfYear.format(now.get(Calendar.YEAR));
        s += runYear + runMonth + runDay;

        return s;
    }

    public static String getCurrentDateOfTomorrowString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat dfMonthDay = new DecimalFormat("00");
        DecimalFormat dfYear = new DecimalFormat("0000");

        now.add(Calendar.DAY_OF_MONTH, +1);

        String runMonth = dfMonthDay.format(now.get(Calendar.MONTH) + 1);
        String runDay = dfMonthDay.format(now.get(Calendar.DAY_OF_MONTH));
        String runYear = dfYear.format(now.get(Calendar.YEAR));
        s += runYear + runMonth + runDay;

        return s;
    }

    /**
     * Returns the current date in the 'YYYY-MM-DD' format.
     * 
     * @return a String value of current date in the 'YYYY-MM-DD' format.
     */
    public static String getCurrentDateString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat dfMonthDay = new DecimalFormat("00");
        DecimalFormat dfYear = new DecimalFormat("0000");
        String runMonth = dfMonthDay.format(now.get(Calendar.MONTH) + 1);
        String runDay = dfMonthDay.format(now.get(Calendar.DAY_OF_MONTH));
        String runYear = dfYear.format(now.get(Calendar.YEAR));
        s += runYear + "-" + runMonth + "-" + runDay;

        return s;
    }

    /**
     * Returns the current date in the 'YYYYMMDDHHMM' format.
     * 
     * @return a String value of current date in the 'YYYYMMDDHHMM' format.
     */
    public static String getCurrentDateTimeString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat df2 = new DecimalFormat("00");
        DecimalFormat df4 = new DecimalFormat("0000");

        String runMinutes = df2.format(now.get(Calendar.MINUTE));
        String runHours = df2.format(now.get(Calendar.HOUR_OF_DAY));
        String runDay = df2.format(now.get(Calendar.DAY_OF_MONTH));
        String runMonth = df2.format(now.get(Calendar.MONTH) + 1);
        String runYear = df4.format(now.get(Calendar.YEAR));

        s += runYear + runMonth + runDay + runHours + runMinutes;

        return s;
    }

    public static java.sql.Timestamp getCurrentTimeStamp()
    {
        return new java.sql.Timestamp(
                (Calendar.getInstance()).getTimeInMillis());
    }

    public static long getCurrentTimeStampInMillis()
    {
        return (Calendar.getInstance()).getTimeInMillis();
    }

    public static String getCurrentYearString()
    {
        String s = "";
        Calendar now = Calendar.getInstance();
        DecimalFormat dfYear = new DecimalFormat("0000");
        String runYear = dfYear.format(now.get(Calendar.YEAR));
        s += runYear;

        return s;
    }

    final public static boolean isBeginOfMonth(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        return day == 1;
    }

    final public static boolean isEndOfMonth(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime()); // don't call cal.setTime(Date)
        // which
        // will reset the TimeZone.

        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return day == maxDay;
    }

    public static final boolean isRounded(Date date, int precision)
    {
        return (date.getTime() % precision) == 0;
    }

    public static final int localizedYearOfDate(Date when, Locale locale,
            TimeZone tz)
    {
        final int year = yearOfDate(when, tz);
        if (locale.equals(Locale.TAIWAN))
            return year - 1911;
        return year;
    }

    public static final Date merge(Date datePart, Date timePart, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();

        final Calendar dateCal = Calendar.getInstance(tz);
        dateCal.setTimeInMillis(datePart.getTime());

        final Calendar timeCal = Calendar.getInstance(tz);
        timeCal.setTimeInMillis(timePart.getTime());

        final int hour = timeCal.get(Calendar.HOUR);
        final int minute = timeCal.get(Calendar.MINUTE);
        final int second = timeCal.get(Calendar.SECOND);
        final int msillisecond = timeCal.get(Calendar.MILLISECOND);

        dateCal.set(Calendar.HOUR, hour);
        dateCal.set(Calendar.MINUTE, minute);
        dateCal.set(Calendar.SECOND, second);
        dateCal.set(Calendar.MILLISECOND, msillisecond);

        return dateCal.getTime();
    }

    public static final int monthOfDate(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        return cal.get(Calendar.MONTH);
    }

    public static final int monthOfDatePlus1(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        return cal.get(Calendar.MONTH) + 1;
    }

    public static final Date now()
    {
        return new Date();
    }

    public static final Date now(int precision)
    {
        return new Date(round(System.currentTimeMillis(), precision));
    }

    final public static Date previousDate(Date when)
    {
        long time = when.getTime() - (24 * 60 * 60 * 1000);
        return new Date(time);
    }

    public static final Date round(Date date, int precision)
    {
        date.setTime(round(date.getTime(), precision));
        return date;
    }

    public static final long round(long time, int precision)
    {
        return time - (time % precision);
    }

    public static final long subtract(Date date2, TimeZone tz, int field,
            Date date1)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();

        boolean negative = false;
        if (date1.after(date2))
        {
            negative = true;
            final Date d = date1;
            date1 = date2;
            date2 = d;
        }

        final Calendar cal1 = Calendar.getInstance(tz);
        cal1.setTimeInMillis(date1.getTime());

        final Calendar cal2 = Calendar.getInstance(tz);
        cal2.setTimeInMillis(date2.getTime());

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        switch (field)
        {
            case Calendar.YEAR:
            {
                return negative ? (year1 - year2) : (year2 - year1);
            }
            case Calendar.MONTH:
            {
                int month1 = cal1.get(Calendar.MONTH);
                int month2 = cal2.get(Calendar.MONTH);
                int months = ((12 * (year2 - year1)) + month2) - month1;
                return negative ? -months : months;
            }
            case Calendar.HOUR:
            {
                long time1 = date1.getTime();
                long time2 = date2.getTime();
                long min1 = (time1 < 0 ? (time1 - ((1000 * 60 * 60) - 1))
                        : time1) / (1000 * 60 * 60);
                long min2 = (time2 < 0 ? (time2 - ((1000 * 60 * 60) - 1))
                        : time2) / (1000 * 60 * 60);
                return negative ? (min1 - min2) : (min2 - min1);
            }
            case Calendar.MINUTE:
            {
                long time1 = date1.getTime();
                long time2 = date2.getTime();
                long min1 = (time1 < 0 ? (time1 - ((1000 * 60) - 1)) : time1)
                        / (1000 * 60);
                long min2 = (time2 < 0 ? (time2 - ((1000 * 60) - 1)) : time2)
                        / (1000 * 60);
                return negative ? (min1 - min2) : (min2 - min1);
            }
            case Calendar.SECOND:
            {
                long time1 = date1.getTime();
                long time2 = date2.getTime();
                long sec1 = (time1 < 0 ? (time1 - (1000 - 1)) : time1) / 1000;
                long sec2 = (time2 < 0 ? (time2 - (1000 - 1)) : time2) / 1000;

                return negative ? (sec1 - sec2) : (sec2 - sec1);
            }
            case Calendar.MILLISECOND:
            {
                return negative ? (date1.getTime() - date2.getTime())
                        : (date2.getTime() - date1.getTime());
            }
            case Calendar.DATE:
            default: /* default, like -1 */
            {
                int day1 = cal1.get(Calendar.DAY_OF_YEAR);
                int day2 = cal2.get(Calendar.DAY_OF_YEAR);

                int maxDay1 = year1 == year2 ? 0
                        : cal1.getActualMaximum(Calendar.DAY_OF_YEAR);
                int days = (maxDay1 - day1) + day2;

                final Calendar cal = Calendar.getInstance(tz);
                for (int year = year1 + 1; year < year2; year++)
                {
                    cal.set(Calendar.YEAR, year);
                    days += cal.getActualMaximum(Calendar.DAY_OF_YEAR);
                }
                return negative ? -days : days;
            }
        }
    }

    public static final Date today()
    {
        return beginOfDate(new Date(), null);
    }

    final public static short twoMonthShort()
    {
        return twoMonthShort(new Date(), null);
    }

    final public static short twoMonthShort(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        final int month = ((cal.get(Calendar.MONTH) / 2) * 2) + 1;
        return (short) month;
    }

    public static final int yearOfDate(Date when, TimeZone tz)
    {
        if (tz == null)
            tz = TimeZones.getCurrent();
        final Calendar cal = Calendar.getInstance(tz);
        cal.setTimeInMillis(when.getTime());

        return cal.get(Calendar.YEAR);
    }

    public static boolean validateDates(String d1, String d2)
    {
        // If both dates are empty, then is ok
        if ((StringUtils.isBlank(d1) || StringUtils.isEmpty(d1))
                && (StringUtils.isBlank(d2) || StringUtils.isEmpty(d2)))
            return true;

        // If both dates are supplied, then further validate
        if ((!(StringUtils.isBlank(d1) || StringUtils.isEmpty(d1))
                && !(StringUtils.isBlank(d2) || StringUtils.isEmpty(d2))))

            if ((validateDate(d1) && validateDate(d2)))
                if (compare(d1, d2) <= 0)
                    return true;

        // Wrong date input
        return false;
    }

    public static boolean validateDate(String dateValue)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean rightFormated = true;

        if (StringUtils.isBlank(dateValue) || StringUtils.isEmpty(dateValue))
            rightFormated = false;
        else if (StringUtils.isNotBlank(dateValue)
                && StringUtils.isNotEmpty(dateValue))
        {
            sdf.setLenient(true);

            try
            {
                sdf.parse(dateValue.trim());
            }
            catch (Exception e)
            {
                rightFormated = false;
            }
            if (rightFormated)
            {
                String[] ds = dateValue.split("-");
                if (ds.length != 3)
                    rightFormated = false;
                else
                {
                    if (rightFormated)
                        rightFormated = checkDateFormat(ds[0], 4, 0);
                    if (rightFormated)
                        rightFormated = checkDateFormat(ds[1], 2, 1);
                    if (rightFormated)
                        rightFormated = checkDateFormat(ds[2], 2, 2);
                }
            }
        }
        return rightFormated;
    }

    private static boolean checkDateFormat(String ds, int requriedLength,
            int type)
    {
        boolean rightFormated = true;
        try
        {
            int d = Integer.parseInt(ds);
            if (ds.length() != requriedLength)
                rightFormated = false;
            if (type == 1)
            { // month
                if (d > 12)
                    rightFormated = false;
            }
            else if (type == 2)
                if (d > 31)
                    rightFormated = false;
        }
        catch (Exception e)
        {
            rightFormated = false;
        }

        return rightFormated;
    }

    private static List<SimpleDateFormat> dateFormats = new ArrayList<SimpleDateFormat>()
    {
        private static final long serialVersionUID = 1L;
        {
            add(new SimpleDateFormat("M/dd/yyyy"));
            add(new SimpleDateFormat("dd.M.yyyy"));
            add(new SimpleDateFormat("M/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.M.yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("dd.MMM.yyyy"));
            add(new SimpleDateFormat("dd-MMM-yyyy"));
            add(new SimpleDateFormat("yyyy-mm-dd"));
        }
    };

    /**
     * Convert String with various formats into java.util.Date
     * 
     * @param input
     *            Date as a string
     * @return java.util.Date object if input string is parsed successfully else returns null
     */
    public static Date convertToDate(String input)
    {
        Date date = null;
        if (null == input)
            return null;
        for (SimpleDateFormat format : dateFormats)
        {
            try
            {
                format.setLenient(false);
                date = format.parse(input);
            }
            catch (ParseException e)
            {
                ;
            }
            if (date != null)
                break;
        }

        return date;
    }

}

class TimeZones
{
    private static final InheritableThreadLocal<TimeZone> _thdTZone = new InheritableThreadLocal<TimeZone>();

    public static final TimeZone getCurrent()
    {
        final TimeZone l = _thdTZone.get();
        return l != null ? l : TimeZone.getDefault();
    }

    public static final TimeZone getThreadLocal()
    {
        return _thdTZone.get();
    }

    public static final TimeZone getTimeZone(int ofsmins)
    {
        final StringBuffer sb = new StringBuffer(8).append("GMT");
        if (ofsmins >= 0)
            sb.append('+');
        else
        {
            sb.append('-');
            ofsmins = -ofsmins;
        }
        final int hr = ofsmins / 60, min = ofsmins % 60;
        if (min == 0)
            sb.append(hr);
        else
        {
            if (hr < 10)
                sb.append('0');
            sb.append(hr).append(':');
            if (min < 10)
                sb.append('0');
            sb.append(min);
        }
        return TimeZone.getTimeZone(sb.toString());
    }

    public static final TimeZone setThreadLocal(TimeZone timezone)
    {
        final TimeZone old = _thdTZone.get();
        _thdTZone.set(timezone);
        return old;
    }

}
