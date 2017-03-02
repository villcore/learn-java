package com.villcore.nio.com.villcore.demo.alert;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

/**
 * Created by WangTao on 2017/2/22.
 */
public class SimpleIduRecord implements Serializable{

    private String districtId;
    private String instanceId;
    private String id;

    private double AADeg;
    private double BADeg;
    private double CADeg;

    class Builder {
        String districtId;
        String instanceId;
        String id;

        double AADeg;
        double BADeg;
        double CADeg;

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public void setInstanceId(String instanceId) {
            this.instanceId = instanceId;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setAADeg(double AADeg) {
            this.AADeg = AADeg;
        }

        public void setBADeg(double BADeg) {
            this.BADeg = BADeg;
        }

        public void setCADeg(double CADeg) {
            this.CADeg = CADeg;
        }

        SimpleIduRecord build() {
            SimpleIduRecord simpleIduRecord = new SimpleIduRecord();
            simpleIduRecord.districtId = districtId;
            simpleIduRecord.instanceId = instanceId;
            simpleIduRecord.id = id;
            simpleIduRecord.AADeg = AADeg;
            simpleIduRecord.BADeg = BADeg;
            simpleIduRecord.CADeg = CADeg;

            return simpleIduRecord;
        }
    }

    private SimpleIduRecord() {

    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAADeg() {
        return AADeg;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleIduRecord that = (SimpleIduRecord) o;

        if (Double.compare(that.AADeg, AADeg) != 0) return false;
        if (Double.compare(that.BADeg, BADeg) != 0) return false;
        if (Double.compare(that.CADeg, CADeg) != 0) return false;
        if (!districtId.equals(that.districtId)) return false;
        if (!id.equals(that.id)) return false;
        if (!instanceId.equals(that.instanceId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = districtId.hashCode();
        result = 31 * result + instanceId.hashCode();
        result = 31 * result + id.hashCode();
        temp = Double.doubleToLongBits(AADeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(BADeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(CADeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SimpleIduRecord{" +
                "districtId='" + districtId + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", id='" + id + '\'' +
                ", AADeg=" + AADeg +
                ", BADeg=" + BADeg +
                ", CADeg=" + CADeg +
                '}';
    }
}
