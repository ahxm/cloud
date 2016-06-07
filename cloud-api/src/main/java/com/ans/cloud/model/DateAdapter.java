package com.ans.cloud.model;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期适配器
 * Created by anzhen on 16-1-27.
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    public static final String UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");
    public static final ThreadLocal<DateFormat> local = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat(UTC_FORMAT);
            sdf.setTimeZone(UTC_ZONE);
            return sdf;
        }
    };

    public static DateFormat get() {
        return local.get();
    }

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null || v.isEmpty()) {
            return null;
        }
        return local.get().parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        if (v == null) {
            return null;
        }
        return local.get().format(v);
    }
}
