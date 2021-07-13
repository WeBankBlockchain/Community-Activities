package scut.healthcode.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegionInfo {

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    private String regionName;

    public int getIsDangerous() {
        return isDangerous;
    }

    public void setIsDangerous(int isDangerous) {
        this.isDangerous = isDangerous;
    }

    private int isDangerous;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    @Override
    public String toString() {
        return "RegionInfo{" +
                "regionName='" + regionName + '\'' +
                ", isDangerous=" + isDangerous +
                ", date='" + date + '\'' +
                '}';
    }
}
