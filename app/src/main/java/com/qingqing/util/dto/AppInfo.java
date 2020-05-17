package com.qingqing.util.dto;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo implements Parcelable {
    private String packageName;
    private Bitmap appIcon;
    private String appName;

    public AppInfo(String pageName, Bitmap appIcon, String appName) {
        this.packageName = pageName;
        this.appIcon = appIcon;
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public AppInfo() {

    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Bitmap getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Bitmap image) {
        this.appIcon = image;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeParcelable(this.appIcon, flags);
        dest.writeString(this.appName);
    }

    protected AppInfo(Parcel in) {
        this.packageName = in.readString();
        this.appIcon = in.readParcelable(Drawable.class.getClassLoader());
        this.appName = in.readString();
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
