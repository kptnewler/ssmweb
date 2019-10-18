package pojo.foramt;

import org.springframework.format.Formatter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    private SimpleDateFormat dateFormat;

    public DateFormatter(String datePattern) {
        this.dateFormat = new SimpleDateFormat(datePattern);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        System.out.println("DateFormatter: 转换");
        java.util.Date date = dateFormat.parse(text);
        return new Date(date.getTime());
    }

    @Override
    public String print(Date date, Locale locale) {
        return dateFormat.format(date);
    }
}
