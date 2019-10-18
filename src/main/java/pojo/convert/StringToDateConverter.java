package pojo.convert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, java.sql.Date> {
    private String datePattern;

    @Override
    public java.sql.Date convert(String source) {
        java.sql.Date sqlDate = null;
        SimpleDateFormat simpleFormatter = new SimpleDateFormat(datePattern);
        try {
           Date date =  simpleFormatter.parse(source);
           sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("转换器：");

        return sqlDate;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
}
