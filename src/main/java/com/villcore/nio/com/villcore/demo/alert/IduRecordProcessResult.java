package com.villcore.nio.com.villcore.demo.alert;

import java.io.Serializable;

/**
 * Created by WangTao on 2017/2/22.
 */
public class IduRecordProcessResult implements Serializable {

    private SimpleIduRecord firstRecord;
    private SimpleIduRecord secondRecord;
    private boolean normal = true;

    public IduRecordProcessResult() {
    }

    public IduRecordProcessResult(SimpleIduRecord firstRecord, SimpleIduRecord secondRecord, boolean normal) {
        this.firstRecord = firstRecord;
        this.secondRecord = secondRecord;
        this.normal = normal;
    }

    public SimpleIduRecord getFirstRecord() {
        return firstRecord;
    }

    public void setFirstRecord(SimpleIduRecord firstRecord) {
        this.firstRecord = firstRecord;
    }

    public SimpleIduRecord getSecondRecord() {
        return secondRecord;
    }

    public void setSecondRecord(SimpleIduRecord secondRecord) {
        this.secondRecord = secondRecord;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean status) {
        this.normal = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IduRecordProcessResult that = (IduRecordProcessResult) o;

        if (normal != that.normal) return false;
        if (!firstRecord.equals(that.firstRecord)) return false;
        if (!secondRecord.equals(that.secondRecord)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstRecord.hashCode();
        result = 31 * result + secondRecord.hashCode();
        result = 31 * result + (normal ? 1 : 0);
        return result;
    }
}
