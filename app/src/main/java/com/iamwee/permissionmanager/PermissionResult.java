package com.iamwee.permissionmanager;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class PermissionResult implements Parcelable{

    private List<Permission> permissions;

    public PermissionResult(List<Permission> permissions) {
        this.permissions = permissions;
    }

    protected PermissionResult(Parcel in) {
        permissions = in.createTypedArrayList(Permission.CREATOR);
    }

    public static final Creator<PermissionResult> CREATOR = new Creator<PermissionResult>() {
        @Override
        public PermissionResult createFromParcel(Parcel in) {
            return new PermissionResult(in);
        }

        @Override
        public PermissionResult[] newArray(int size) {
            return new PermissionResult[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(permissions);
    }

    public boolean areAllPermissionGranted() {
        if (isPermissionAvailable()) {
            for (Permission permission : permissions) {
                if (!permission.isPermissionGranted()) return false;
            }
            return true;
        }
        return true;
    }

    public boolean isAnyPermissionDenied() {
        if (isPermissionAvailable()) {
            for (Permission permission : permissions) {
                if (permission.isPermissionDenied()) return true;
            }
            return false;
        }
        return false;
    }

    public List<Permission> getPermissionList() {
        return permissions;
    }

    public List<String> getPermissionDeniedList() {
        List<String> permissionList = new ArrayList<>();
        if (isPermissionAvailable()) {
            for (Permission permission : permissions) {
                if (!permission.isPermissionGranted())
                    permissionList.add(permission.getPermissionName());
            }
        }
        return permissionList;
    }

    private boolean isPermissionAvailable() {
        return permissions != null && permissions.size() > 0;
    }
}