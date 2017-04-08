package com.iamwee.permissionmanager;

import android.os.Parcel;
import android.os.Parcelable;

public class Permission implements Parcelable {

    private String permissionName;
    private boolean permissionGranted;

    public Permission(String permissionName, boolean permissionGranted) {
        this.permissionName = permissionName;
        this.permissionGranted = permissionGranted;
    }

    protected Permission(Parcel in) {
        permissionName = in.readString();
        permissionGranted = in.readByte() != 0;
    }

    public static final Creator<Permission> CREATOR = new Creator<Permission>() {
        @Override
        public Permission createFromParcel(Parcel in) {
            return new Permission(in);
        }

        @Override
        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(permissionName);
        dest.writeByte((byte) (permissionGranted ? 1 : 0));
    }

    public String getPermissionName() {
        return permissionName;
    }

    public boolean isPermissionGranted() {
        return permissionGranted;
    }

    public boolean isPermissionDenied() {
        return !permissionGranted;
    }
}