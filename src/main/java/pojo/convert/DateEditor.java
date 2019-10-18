package pojo.convert;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        System.out.println("DateEditor执行"+ text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(text);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            setValue(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
