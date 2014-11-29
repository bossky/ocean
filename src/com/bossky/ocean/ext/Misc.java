package com.bossky.ocean.ext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Misc {
	public static final Logger _Logger = LoggerFactory.getLogger(Misc.class);
	public static final boolean DebugEnabled = _Logger.isDebugEnabled();

	public static final boolean TraceEnabled = _Logger.isTraceEnabled();

	public static final boolean InfoEnabled = _Logger.isInfoEnabled();

	public static final boolean WarnEnabled = _Logger.isWarnEnabled();
	public static final char UNICODE_REPLACEMENT_CHAR = 65533;
	public static final String UNICODE_REPLACEMENT_STRING = String
			.valueOf(65533);

	public static final String[] _nilStrings = new String[0];

	private static Random a = new Random();

	public static final int TIMEZONE_OFFSET = TimeZone.getDefault()
			.getRawOffset();
	public static final int HOUR_MILLS = 3600000;
	public static final int DAY_MILLS = 86400000;
	public static final Date GMT1970 = new ReadonlyDate(0L);

	public static final Date FAR_FUTURE = new ReadonlyDate(
			System.currentTimeMillis() + 31536000000000L);

	public static final char[] _hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final char[] _HEXDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private static int[] b = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1,
			10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11,
			12, 13, 14, 15 };
	private static SimpleDateFormat c;
	private static SimpleDateFormat d;
	private static SimpleDateFormat e;
	private static SimpleDateFormat f;
	private static SimpleDateFormat g;
	private static SimpleDateFormat h;
	private static SimpleDateFormat i;
	private static SimpleDateFormat j;
	private static TimeZone k;
	private static Calendar l;
	public static final String TABLE_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String TABLE_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	public static final String TABLE_NUMERAL = "0123456789";
	public static final String TABLE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	public static final String TABLE_ALL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static int n;

	static {
		new SimpleDateFormat("yyyyMMddHHmmss");

		c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		d = new SimpleDateFormat("yyyy-M-d H:m:s");

		e = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		f = new SimpleDateFormat("yyyy-MM-dd");

		g = new SimpleDateFormat("HH:mm:ss");

		h = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);

		i = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);

		j = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		l = Calendar.getInstance(Misc.k = TimeZone.getTimeZone("GMT"));

		h.setCalendar(Calendar.getInstance(k));

		n = toInt(System.getProperty("Misc.InternMaxLength", "100"));
	}

	public static final String toUintString(int paramInt) {
		return String.valueOf(paramInt);
	}

	public static final String toString(Object paramObject) {
		if (paramObject == null) {
			return "";
		}
		return paramObject.toString().trim();
	}

	public static final String toString(String paramString) {
		if (paramString == null) {
			return "";
		}
		return paramString;
	}

	public static final boolean isEmpty(String paramString) {
		return ((paramString == null) || (paramString.length() == 0));
	}

	public static final boolean isLetter(char paramChar) {
		return (((paramChar > '@') && (paramChar < '[')) || ((paramChar > '`') && (paramChar < '{')));
	}

	public static final boolean isLetter(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return false;
		}
		for (int i1 = paramString.length() - 1; i1 >= 0; --i1) {
			if (!(isLetter(paramString.charAt(i1)))) {
				return false;
			}
		}
		return true;
	}

	public static final boolean isNumber(char paramChar) {
		return ((paramChar > '/') && (paramChar < ':'));
	}

	public static final boolean isNumber(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return false;
		}
		for (int i1 = paramString.length() - 1; i1 >= 0; --i1) {
			if (!(isNumber(paramString.charAt(i1)))) {
				return false;
			}
		}
		return true;
	}

	public static final boolean isNumber(char[] paramArrayOfChar,
			int paramInt1, int paramInt2) {
		paramInt2 += paramInt1;
		for (paramInt1 = paramInt1; paramInt1 < paramInt2; ++paramInt1) {
			if (!(isNumber(paramArrayOfChar[paramInt1]))) {
				return false;
			}
		}
		return true;
	}

	public static final String simpleHash(String paramString) {
		return String.valueOf(paramString);
	}

	public static final int toIntFirst(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return 0;
		}
		int i1 = ('-' == paramString.charAt(0)) ? 1 : 0;
		int i2 = 0;
		int i3 = paramString.length();

		for (; i1 < i3; ++i1) {
			char c1;
			if (((c1 = paramString.charAt(i1)) < '0') || (c1 > '9'))
				break;
			i2 = i2 * 10 + Character.digit(c1, 10);
		}

		if ('-' == paramString.charAt(0))
			return (-i2);
		return i2;
	}

	public static final int toInt(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return 0;
		}

		int i1 = paramString.charAt(0);
		if ((48 == i1) && (paramString.length() > 2)) {
			i1 = paramString.charAt(1);
			if ((120 == i1) || (88 == i1))
				return Integer.parseInt(paramString.substring(2), 16);
		} else if ((paramString.length() > 1) && (((120 == i1) || (88 == i1)))) {
			return Integer.parseInt(paramString.substring(1), 16);
		}
		return Integer.parseInt(paramString);
	}

	public static int toInt(String paramString, int paramInt) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return paramInt;
		}
		try {
			int i1 = paramString.charAt(0);
			if ((48 == i1) && (paramString.length() > 2)) {
				i1 = paramString.charAt(1);
				if ((120 == i1) || (88 == i1))
					return Integer.parseInt(paramString.substring(2), 16);
			} else if ((paramString.length() > 1)
					&& (((120 == i1) || (88 == i1)))) {
				return Integer.parseInt(paramString.substring(1), 16);
			}
			return Integer.parseInt(paramString);
		} catch (NumberFormatException localNumberFormatException) {
		}
		return paramInt;
	}

	public static double toDouble(String paramString, double paramDouble) {
		if ((paramString == null) || (paramString.length() == 0))
			return paramDouble;
		try {
			return Double.parseDouble(paramString);
		} catch (NumberFormatException e) {
		}
		return paramDouble;
	}

	public static String md5Hash(byte[] paramArrayOfByte) {
		try {
			MessageDigest localMessageDigest;
			(localMessageDigest = MessageDigest.getInstance("MD5"))
					.update(paramArrayOfByte);
			return encodeHex(localMessageDigest.digest());
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		}
		return null;
	}

	public static String encodeHex(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null) {
			return null;
		}
		char[] arrayOfChar = new char[paramArrayOfByte.length << 1];
		int i1 = 0;
		for (int i2 = 0; i2 < paramArrayOfByte.length; ++i2) {
			int i3 = paramArrayOfByte[i2];
			arrayOfChar[(i1++)] = _hexDigits[(i3 >> 4 & 0xF)];
			arrayOfChar[(i1++)] = _hexDigits[(i3 & 0xF)];
		}
		return new String(arrayOfChar);
	}

	public static StringBuilder toHexFixed(int paramInt,
			StringBuilder paramStringBuilder) {
		if (paramStringBuilder == null) {
			paramStringBuilder = new StringBuilder(8);
		}
		if ((paramInt < 0) || (paramInt >= 268435456)) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 28 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 24 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 16777216) {
			paramStringBuilder.append('0');
			paramStringBuilder.append(_hexDigits[(paramInt >> 24 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 1048576) {
			paramStringBuilder.append("00");
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 65536) {
			paramStringBuilder.append("000");
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 4096) {
			paramStringBuilder.append("0000");
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 256) {
			paramStringBuilder.append("00000");
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 16) {
			paramStringBuilder.append("000000");
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt > 0) {
			paramStringBuilder.append("0000000");
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else {
			paramStringBuilder.append("00000000");
			return paramStringBuilder;
		}
		return paramStringBuilder;
	}

	public static StringBuilder toHexFixed(short paramShort,
			StringBuilder paramStringBuilder) {
		if (paramStringBuilder == null) {
			paramStringBuilder = new StringBuilder(4);
		}
		if ((paramShort < 0) || (paramShort >= 4096)) {
			paramStringBuilder.append(_hexDigits[(paramShort >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort & 0xF)]);
		} else if (paramShort >= 256) {
			paramStringBuilder.append('0');
			paramStringBuilder.append(_hexDigits[(paramShort >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort & 0xF)]);
		} else if (paramShort >= 16) {
			paramStringBuilder.append("00");
			paramStringBuilder.append(_hexDigits[(paramShort >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramShort & 0xF)]);
		} else if (paramShort > 0) {
			paramStringBuilder.append("000");
			paramStringBuilder.append(_hexDigits[(paramShort & 0xF)]);
		} else {
			paramStringBuilder.append("0000");
			return paramStringBuilder;
		}
		return paramStringBuilder;
	}

	public static StringBuilder toHexFixed(byte paramByte,
			StringBuilder paramStringBuilder) {
		paramStringBuilder.append(_hexDigits[(paramByte >> 4 & 0xF)]);
		paramStringBuilder.append(_hexDigits[(paramByte & 0xF)]);
		return paramStringBuilder;
	}

	public static String toHex32(int paramInt) {
		if (paramInt == 0) {
			return "00000000";
		}
		return toHexFixed(paramInt, new StringBuilder(8)).toString();
	}

	public static String toHex16(short paramShort) {
		if (paramShort == 0) {
			return "0000";
		}
		return toHexFixed(paramShort, new StringBuilder(4)).toString();
	}

	public static StringBuilder toHex(int paramInt,
			StringBuilder paramStringBuilder) {
		if ((paramInt < 0) || (paramInt >= 268435456)) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 28 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 24 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 16777216) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 24 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 1048576) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 65536) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 4096) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 256) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 16) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt > 0) {
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else {
			paramStringBuilder.append("0");
			return paramStringBuilder;
		}
		return paramStringBuilder;
	}

	public static String toHex(int paramInt) {
		return toHex(paramInt, new StringBuilder(8)).toString();
	}

	public static StringBuilder toHex(long paramLong,
			StringBuilder paramStringBuilder) {
		int i1;
		if ((i1 = (int) (paramLong >> 32)) != 0) {
			toHex(i1, paramStringBuilder);

			toHexFixed(i1 = (int) paramLong, paramStringBuilder);
			return paramStringBuilder;
		}

		toHex(i1 = (int) paramLong, paramStringBuilder);
		return paramStringBuilder;
	}

	public static String toHex(long paramLong) {
		return toHex(paramLong, new StringBuilder(16)).toString();
	}

	public static final int charAsHex(char paramChar) {
		if ((paramChar < 0) || (paramChar >= b.length) || (-1 == b[paramChar])) {
			return -1;
		}
		return b[paramChar];
	}

	public static final String formatDateTime(Date paramDate) {
		if (paramDate == null) {
			return "";
		}
		synchronized (c) {
			return c.format(paramDate);
		}
	}

	public static final String formatDate(Date paramDate) {
		synchronized (f) {
			return f.format(paramDate);
		}
	}

	public static final String formatTime(Date paramDate) {
		synchronized (g) {
			return g.format(paramDate);
		}
	}

	public static final String formatUTC(Date paramDate) {
		synchronized (j) {
			return j.format(paramDate);
		}
	}

	public static final String formatGMT(Date paramDate) {
		synchronized (h) {
			return h.format(paramDate);
		}
	}

	public static final String formatCompactGMT(Date paramDate) {
		return formatCompactGMT(paramDate, new StringBuilder(18)).toString();
	}

	public static final StringBuilder formatCompactGMT(Date paramDate,
			StringBuilder paramStringBuilder) {
		synchronized (l) {
			Calendar localCalendar;
			(localCalendar = l).setTime(paramDate);
			append4(paramStringBuilder, localCalendar.get(1));
			append2(paramStringBuilder, 1 + localCalendar.get(2));
			append2(paramStringBuilder, localCalendar.get(5));
			paramStringBuilder.append('T');
			append2(paramStringBuilder, localCalendar.get(11));
			append2(paramStringBuilder, localCalendar.get(12));
			append2(paramStringBuilder, localCalendar.get(13));
			append3(paramStringBuilder, localCalendar.get(14));
		}
		return paramStringBuilder;
	}

	public static final String formatCompactDateGMT(Date paramDate) {
		return formatCompactDateGMT(paramDate, new StringBuilder(8)).toString();
	}

	public static final StringBuilder formatCompactDateGMT(Date paramDate,
			StringBuilder paramStringBuilder) {
		synchronized (l) {
			Calendar localCalendar;
			(localCalendar = l).setTime(paramDate);
			append4(paramStringBuilder, localCalendar.get(1));
			append2(paramStringBuilder, 1 + localCalendar.get(2));
			append2(paramStringBuilder, localCalendar.get(5));
		}
		return paramStringBuilder;
	}

	public static final StringBuilder formatYyyyMmDdHhGMT(Date paramDate,
			StringBuilder paramStringBuilder) {
		synchronized (l) {
			Calendar localCalendar;
			(localCalendar = l).setTime(paramDate);
			append4(paramStringBuilder, localCalendar.get(1));
			append2(paramStringBuilder, 1 + localCalendar.get(2));
			append2(paramStringBuilder, localCalendar.get(5));
			append2(paramStringBuilder, localCalendar.get(11));
		}
		return paramStringBuilder;
	}

	public static final StringBuilder formatYyyyMmDdHhGMT(long paramLong,
			StringBuilder paramStringBuilder) {
		synchronized (l) {
			Calendar localCalendar;
			(localCalendar = l).setTimeInMillis(paramLong);
			append4(paramStringBuilder, localCalendar.get(1));
			append2(paramStringBuilder, 1 + localCalendar.get(2));
			append2(paramStringBuilder, localCalendar.get(5));
			append2(paramStringBuilder, localCalendar.get(11));
		}
		return paramStringBuilder;
	}

	public static final String formatYyyyMmDdHhGMT(Date paramDate) {
		return formatYyyyMmDdHhGMT(paramDate, new StringBuilder(10)).toString();
	}

	public static final String formatCompactDate(Date paramDate) {
		return formatCompactDate(paramDate, new StringBuilder(8)).toString();
	}

	public static final StringBuilder formatCompactDate(Date paramDate,
			StringBuilder paramStringBuilder) {
		Calendar localCalendar;
		(localCalendar = Calendar.getInstance()).setTime(paramDate);
		append4(paramStringBuilder, localCalendar.get(1));
		append2(paramStringBuilder, 1 + localCalendar.get(2));
		append2(paramStringBuilder, localCalendar.get(5));
		return paramStringBuilder;
	}

	public static final void append4(StringBuilder paramStringBuilder,
			int paramInt) {
		if (paramInt < 10)
			paramStringBuilder.append("000");
		else if (paramInt < 100)
			paramStringBuilder.append("00");
		else if (paramInt < 1000) {
			paramStringBuilder.append("0");
		}
		paramStringBuilder.append(paramInt);
	}

	public static final void append3(StringBuilder paramStringBuilder,
			int paramInt) {
		if (paramInt < 10)
			paramStringBuilder.append("00");
		else if (paramInt < 100) {
			paramStringBuilder.append("0");
		}
		paramStringBuilder.append(paramInt);
	}

	public static final void append2(StringBuilder paramStringBuilder,
			int paramInt) {
		if (paramInt < 10) {
			paramStringBuilder.append("0");
		}
		paramStringBuilder.append(paramInt);
	}

	public static void escapeXML(String paramString,
			StringBuilder paramStringBuilder) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return;
		}
		for (int i1 = 0; i1 < paramString.length(); ++i1) {
			int i2;
			if ((((i2 = paramString.charAt(i1)) >= 0) && (i2 < 32))
					|| ((i2 >= 127) && (i2 <= 255)))
				paramStringBuilder.append("&#").append(i2).append(';');
			else
				switch (i2) {
				case 62:
					paramStringBuilder.append("&gt;");
					break;
				case 60:
					paramStringBuilder.append("&lt;");
					break;
				case 34:
					paramStringBuilder.append("&quot;");
					break;
				case 38:
					paramStringBuilder.append("&amp;");
					break;
				default:
					paramStringBuilder.append(paramString.charAt(i1));
				}
		}
	}

	public static String escapeXML(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return "";
		}
		StringBuilder localStringBuilder = new StringBuilder(
				paramString.length() << 1);
		escapeXML(paramString, localStringBuilder);
		return localStringBuilder.toString();
	}

	public static String getFilename(String paramString) {
		if (paramString == null) {
			return "";
		}
		int i1 = paramString.lastIndexOf(92);
		if (-1 == i1)
			return paramString;
		return paramString.substring(i1 + 1);
	}

	public static final String genRandomString(int paramInt, String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			paramString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		}

		char[] arrayOfChar = new char[paramInt];
		int i1 = paramString.length();

		for (int i2 = 0; i2 < paramInt; ++i2) {
			arrayOfChar[i2] = paramString.charAt(a.nextInt(i1));
		}
		return new String(arrayOfChar);
	}

	public static String shiftEncrypt(String paramString, int paramInt) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return "";
		}
		if ((paramInt < 0) || (paramInt >= 16)) {
			paramInt = 0;
		}
		StringBuilder localStringBuilder = new StringBuilder();
		int i1 = paramString.length();
		for (int i2 = 0; i2 < i1; ++i2) {
			int i3 = paramString.charAt(i2);

			int i4 = (0xFFFF & i3) << paramInt;

			int i5 = 0;
			switch (paramInt) {
			case 1:
				i5 = 1;
				break;
			case 2:
				i5 = 3;
				break;
			case 3:
				i5 = 7;
				break;
			case 4:
				i5 = 15;
				break;
			case 5:
				i5 = 31;
				break;
			case 6:
				i5 = 63;
				break;
			case 7:
				i5 = 127;
				break;
			case 8:
				i5 = 255;
				break;
			case 9:
				i5 = 511;
				break;
			case 10:
				i5 = 1023;
				break;
			case 11:
				i5 = 2047;
				break;
			case 12:
				i5 = 4095;
				break;
			case 13:
				i5 = 8191;
				break;
			case 14:
				i5 = 16383;
				break;
			case 15:
				i5 = 32767;
				break;
			case 16:
				i5 = 65535;
			}

			i4 = (i4 = i4 & (0xFFFF0000 | i5 ^ 0xFFFFFFFF)) | i4 >> 16;
			toHexFixed((short) (0xFFFF & i4), localStringBuilder);

			paramInt = 0xF & i3;
		}
		return localStringBuilder.toString();
	}

	public static final void encodeUrl(int paramInt,
			StringBuilder paramStringBuilder) {
		paramStringBuilder.append('%');
		paramStringBuilder.append(_HEXDigits[(paramInt >> 4 & 0xF)]);
		paramStringBuilder.append(_HEXDigits[(paramInt & 0xF)]);
	}

	public static final StringBuilder encodeUrl(String paramString,
			StringBuilder paramStringBuilder) {
		if (paramString == null) {
			if (paramStringBuilder == null)
				return new StringBuilder(0);
			return paramStringBuilder;
		}
		if (paramStringBuilder == null) {
			paramStringBuilder = new StringBuilder(paramString.length() * 3);
		}
		int i1 = paramString.length();
		for (int i2 = 0; i2 < i1; ++i2) {
			int i3;
			if (((i3 = paramString.charAt(i2)) < 0) || (i3 > 127)) {
				if (((i3 = (i3 < 0) ? i3 + 65536 : i3) >= 128) && (i3 < 2047)) {
					encodeUrl(0xC0 | 0x1F & i3 >> 6, paramStringBuilder);
					encodeUrl(0x80 | 0x3F & i3, paramStringBuilder);
				} else if (i3 >= 2048) {
					encodeUrl(0xE0 | 0xF & i3 >> 12, paramStringBuilder);
					encodeUrl(0x80 | 0x3F & i3 >> 6, paramStringBuilder);
					encodeUrl(0x80 | 0x3F & i3, paramStringBuilder);
				}

			} else if ((46 == i3) || (45 == i3) || (95 == i3) || (42 == i3)
					|| ((i3 >= 48) && (i3 <= 57)) || ((i3 >= 65) && (i3 <= 90))
					|| ((i3 >= 97) && (i3 <= 122))) {
				paramStringBuilder.append(i3);
			} else {
				encodeUrl(i3, paramStringBuilder);
			}
		}

		return paramStringBuilder;
	}

	public static final String encodeUrl(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)) {
			return "";
		}
		return encodeUrl(paramString, null).toString();
	}

	public static int compareTo(byte[] paramArrayOfByte1,
			byte[] paramArrayOfByte2) {
		if (paramArrayOfByte1.length != paramArrayOfByte2.length) {
			return (paramArrayOfByte1.length - paramArrayOfByte2.length);
		}
		return compareTo(paramArrayOfByte1, paramArrayOfByte2,
				paramArrayOfByte1.length);
	}

	public static int compareTo(byte[] paramArrayOfByte1,
			byte[] paramArrayOfByte2, int paramInt) {
		int i1 = 0;
		for (int i2 = 0; i2 < paramInt; ++i2) {
			if (paramArrayOfByte1[i2] != paramArrayOfByte2[i2]) {
				i1 = paramArrayOfByte1[i2] - paramArrayOfByte2[i2];
			}
		}
		return i1;
	}

	public static int compareTo(Date paramDate1, Date paramDate2) {
		if (paramDate1 == paramDate2) {
			return 0;
		}
		if (paramDate1 == null) {
			return -1;
		}
		if (paramDate2 == null)
			return 1;
		long l1;
		if ((l1 = paramDate1.getTime() - paramDate2.getTime()) > 0L)
			return 1;
		if (l1 < 0L)
			return -1;
		return 0;
	}

	public static StringBuilder escapeFileName(String paramString,
			StringBuilder paramStringBuilder) {
		int i1 = paramString.length();
		if (paramStringBuilder == null) {
			paramStringBuilder = new StringBuilder(i1);
		}
		for (int i2 = 0; i2 < i1; ++i2) {
			char c1;
			switch (c1 = paramString.charAt(i2)) {
			case ':':
				paramStringBuilder.append("%3a");
				break;
			case '\\':
				paramStringBuilder.append("%5c");
				break;
			case '/':
				paramStringBuilder.append("%2f");
				break;
			case '*':
				paramStringBuilder.append("%2a");
				break;
			case '<':
				paramStringBuilder.append("%3c");
				break;
			case '>':
				paramStringBuilder.append("%3e");
				break;
			case '?':
				paramStringBuilder.append("%3f");
				break;
			case '"':
				paramStringBuilder.append("%22");
				break;
			case '|':
				paramStringBuilder.append("%7c");
				break;
			case '%':
				paramStringBuilder.append("%25");
				break;
			case '.':
				paramStringBuilder.append("%2e");
				break;
			case '\t':
				paramStringBuilder.append("%09");
				break;
			case '\n':
				paramStringBuilder.append("%0A");
				break;
			case '\r':
				paramStringBuilder.append("%0D");
				break;
			case '\'':
				paramStringBuilder.append("%27");
				break;
			default:
				if (((c1 >= 0) && (c1 < ' ')) || ((c1 >= '') && (c1 <= 255))) {
					continue;
				}
				paramStringBuilder.append(c1);
			}

		}

		return paramStringBuilder;
	}

	public static String escapeFileName(String paramString) {
		return escapeFileName(paramString, null).toString();
	}

	public static final StringBuilder encodeHexx(int paramInt,
			StringBuilder paramStringBuilder) {
		if ((paramInt < 0) || (paramInt >= 16777216)) {
			paramStringBuilder.append(_hexDigits[(paramInt >> 28 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 24 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 1048576) {
			paramStringBuilder.append('n');
			paramStringBuilder.append(_hexDigits[(paramInt >> 20 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 65536) {
			paramStringBuilder.append('m');
			paramStringBuilder.append(_hexDigits[(paramInt >> 16 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 4096) {
			paramStringBuilder.append('l');
			paramStringBuilder.append(_hexDigits[(paramInt >> 12 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 256) {
			paramStringBuilder.append('k');
			paramStringBuilder.append(_hexDigits[(paramInt >> 8 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else if (paramInt >= 16) {
			paramStringBuilder.append('j');
			paramStringBuilder.append(_hexDigits[(paramInt >> 4 & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		} else {
			paramStringBuilder.append('i');
			paramStringBuilder.append(_hexDigits[(paramInt & 0xF)]);
		}
		return paramStringBuilder;
	}

	public static final StringBuilder encodeHexx(char paramChar,
			StringBuilder paramStringBuilder) {
		if ((paramChar < 0) || (paramChar >= 256)) {
			paramStringBuilder.append(_hexDigits[(paramChar >> '\f' & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramChar >> '\b' & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramChar >> '\4' & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramChar & 0xF)]);
		} else if (paramChar >= '\16') {
			paramStringBuilder.append('j');
			paramStringBuilder.append(_hexDigits[(paramChar >> '\4' & 0xF)]);
			paramStringBuilder.append(_hexDigits[(paramChar & 0xF)]);
		} else {
			paramStringBuilder.append('i');
			paramStringBuilder.append(_hexDigits[(paramChar & 0xF)]);
		}
		return paramStringBuilder;
	}

	public static final StringBuilder encodeHexx(String paramString,
			StringBuilder paramStringBuilder) {
		if (!(isEmpty(paramString))) {
			for (int i1 = 0; i1 < paramString.length(); ++i1) {
				encodeHexx(paramString.charAt(i1), paramStringBuilder);
			}
		}
		return paramStringBuilder;
	}

	public static final StringBuilder encodeAlphameric(String paramString,
			StringBuilder paramStringBuilder) {
		if (!(isEmpty(paramString))) {
			for (int i1 = 0; i1 < paramString.length(); ++i1) {
				char c1;
				if ((((c1 = paramString.charAt(i1)) >= '0') && (c1 <= '9'))
						|| ((c1 >= 'a') && (c1 < 'y'))
						|| ((c1 >= 'A') && (c1 <= 'Z'))) {
					paramStringBuilder.append(c1);
				} else if (('z' == c1) || ('y' == c1)) {
					paramStringBuilder.append('z');
					paramStringBuilder.append(c1);
				} else if ((c1 > 0) && (c1 <= 255)) {
					paramStringBuilder.append('y');
					paramStringBuilder.append(_hexDigits[(c1 >> '\4' & 0xF)]);
					paramStringBuilder.append(_hexDigits[(c1 & 0xF)]);
				} else {
					paramStringBuilder.append('z');
					paramStringBuilder.append(_hexDigits[(c1 >> '\f' & 0xF)]);
					paramStringBuilder.append(_hexDigits[(c1 >> '\b' & 0xF)]);
					paramStringBuilder.append(_hexDigits[(c1 >> '\4' & 0xF)]);
					paramStringBuilder.append(_hexDigits[(c1 & 0xF)]);
				}
			}
		}
		return paramStringBuilder;
	}

	public static String getAbsolutePath(String paramString1,
			String paramString2) {
		int i1 = ((paramString1 = toString(paramString1)).length() > 0) ? paramString1
				.charAt(0) : 32;

		if ((47 != i1)
				&& (92 != i1)
				&& (((paramString1.length() < 2) || (paramString1.charAt(1) != ':')))) {
			if (paramString2 == null)
				paramString2 = System.getProperty("user.dir");
			String str;
			if (((str = System.getProperty("file.separator")) == null)
					|| (str.length() == 0)) {
				str = "/";
			}
			i1 = paramString2.charAt(paramString2.length() - 1);
			if ((47 == i1) || (92 == i1) || (str.charAt(0) == i1)) {
				paramString2 = paramString2.substring(0,
						paramString2.length() - 1);
			}

			while (paramString1.startsWith("../")) {
				i1 = paramString2.lastIndexOf(str);
				if (-1 == i1)
					break;
				paramString2 = paramString2.substring(0, i1);

				paramString1 = paramString1.substring(3, paramString1.length());
			}
			paramString1 = paramString2 + str + paramString1;
		}
		return paramString1;
	}

	public static final int getSecondsFromTime(Date paramDate) {
		if (paramDate == null) {
			return 0;
		}
		return (int) (paramDate.getTime() / 1000L);
	}

	public static final int getSecondsFromTime(long paramLong) {
		return (int) (paramLong / 1000L);
	}

	public static final Date getTimeFromSeconds(int paramInt) {
		if (paramInt == 0) {
			return GMT1970;
		}
		return new Date(paramInt * 1000L);
	}

	public static final boolean eq(String paramString1, String paramString2) {
		if (paramString1 == paramString2) {
			return true;
		}
		return ((paramString1 != null) && (paramString1.equals(paramString2)));
	}

	public static final int compareTo(String paramString1, String paramString2) {
		if (paramString1 == paramString2) {
			return 0;
		}
		if (paramString1 == null) {
			return -1;
		}
		if (paramString2 == null) {
			return 1;
		}
		return paramString1.compareTo(paramString2);
	}

	public static final String intern(String paramString) {
		if ((paramString == null) || (paramString.length() == 0)
				|| (paramString.length() > n))
			return paramString;
		try {
			return paramString.intern();
		} catch (OutOfMemoryError localOutOfMemoryError) {
			_Logger.warn(localOutOfMemoryError.toString(),
					localOutOfMemoryError);
		}
		return paramString;
	}

	public static class ReadonlyDate extends Date {
		public ReadonlyDate(long paramLong) {
			super(paramLong);
		}

		public void setDate(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setHours(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setMinutes(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setMonth(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setSeconds(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setTime(long paramLong) {
			throw new UnsupportedOperationException("Date object is read-only");
		}

		public void setYear(int paramInt) {
			throw new UnsupportedOperationException("Date object is read-only");
		}
	}

	public static Date parseDate(String string) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}
}