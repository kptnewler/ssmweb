package pojo.convert;

import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import pojo.foramt.DateFormatter;

public class MyFormatterRegistrar implements FormatterRegistrar {
    private DateFormatter dateFormatter;

    public MyFormatterRegistrar(DateFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(dateFormatter);
    }
}
