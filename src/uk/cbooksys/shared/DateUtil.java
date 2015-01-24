package uk.cbooksys.shared;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;

public  class DateUtil {
	DateUtil(){}
	private static final String D_M_YYYY = "dd-MM-yyyy";
	private static final String DATE_TIME = "dd-MM-yyyy-HH-mm-ss";
	public static final String TIME_ONLY = "HH-mm-ss";

	public static final String DAY_TIME = "ccc HH:mm";

	public static final String TIME = "HH:mm";
	public static final String DATE_SHORT = "ccc, dd MMM yy";

	private static final String DATE_SEPARATOR = "-";

	public static DateTimeFormat logDateFormatter = DateTimeFormat.getFormat("dd MMM yy HH:mm:ss");

	public static String getLogDate(){
		return logDateFormatter.format(new Date());
	}

	public static String getDayTime(Date date){
		return DateTimeFormat.getFormat(DAY_TIME).format(date);
	}

	public static String getShortTime(Date date){
		return DateTimeFormat.getFormat(TIME).format(date);
	}
	public static String getShortDate(Date date){
		return DateTimeFormat.getFormat(DATE_SHORT).format(date);
	}

	public static Date getDate(Integer dd, Integer mm, Integer yyyy) {
		if (dd == null || mm == null || yyyy == null)
			return null;

		Date retVal = null;
		try {
			retVal = DateTimeFormat.getFormat(D_M_YYYY).parse(dd + DATE_SEPARATOR + mm + DATE_SEPARATOR + yyyy);
		} catch (Exception e) {
			retVal = null;
		}

		return retVal;
	}

	public static Date getDate(String yy, String mm, String dd, String HH, String MM, String SS) {
		if (dd == null || mm == null || yy == null || HH == null || MM == null || SS == null)
			return null;

		Date retVal = null;
		try {
			//GWT.log(yy+"-"+mm+"-"+dd+"-"+HH+"-"+MM+"-"+SS);
			retVal = DateTimeFormat.getFormat(DATE_TIME).parse(dd + DATE_SEPARATOR + mm + DATE_SEPARATOR + yy + DATE_SEPARATOR + HH + DATE_SEPARATOR + MM + DATE_SEPARATOR + SS);
			//GWT.log(retVal.toString());
		} catch (Exception e) {
			retVal = null;
		}

		return retVal;
	}

	public static Date resetTime(Date date){
		return getDate(getYearAsString(date),getMonthAsString(date),getDayAsString(date),"00","00","00");
	}

	public static String getDayAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[0];
	}

	public static String getMonthAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[1];
	}

	public static String getYearAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[2];
	}
	public static String getHoursAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[3];
	}
	public static String getMinutesAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[4];
	}
	public static String getSecondsAsString(Date date) {
		return (date == null) ? null : DateTimeFormat.getFormat(DATE_TIME).format(date).split(DATE_SEPARATOR)[5];
	}

	public static boolean isValidDate(Integer dd, Integer mm, Integer yyyy) {
		boolean isvalidDate = true;

		try {
			String transformedInput = DateTimeFormat.getFormat(D_M_YYYY).format(getDate(dd, mm, yyyy));
			String originalInput = dd + DATE_SEPARATOR + mm + DATE_SEPARATOR + yyyy;

			isvalidDate = transformedInput.equals(originalInput);
		} catch (Exception e) {
			isvalidDate = false;
		}

		return isvalidDate;
	}

	public static Date addDays(Date dateIn, int numDays) {
		return add(dateIn, numDays, 86400000);
	}

	public static Date addMM(Date dateIn, int numMM) {
		return add(dateIn, numMM, 60 * 1000);
	}

	public static Date addMin(Date dateIn, int numMin) {
		return add(dateIn, 0, numMin * 1000);
	}

	private static Date add(Date dateIn, int num, long numMili) {
		long dateInMilis = dateIn.getTime();
		dateInMilis = dateInMilis + (num * numMili);
		return new Date(dateInMilis);
	}


	public static int timeCompare(Date d1, Date d2)
	{
		int     t1;
		int     t2;

		t1 = (int) (d1.getTime() % (24*60*60*1000L));
		t2 = (int) (d2.getTime() % (24*60*60*1000L));
		return (t1 - t2);
	}
}