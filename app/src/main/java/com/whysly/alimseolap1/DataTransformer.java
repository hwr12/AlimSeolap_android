package com.whysly.alimseolap1;

import androidx.room.TypeConverter;

import java.util.Date;

public class DataTransformer {
    @TypeConverter
    public static Long fromDate(Date date) {
        if (date == null)
            return(null);

        return(date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch == null)
            return(null);

        return(new Date(millisSinceEpoch));
    }

}