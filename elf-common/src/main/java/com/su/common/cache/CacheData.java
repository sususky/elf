package com.su.common.cache;

/**
 * @author surongyao
 * @date 2018-06-05 18:31
 * @desc
 */
public class CacheData {

    long time;
    Object value;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
