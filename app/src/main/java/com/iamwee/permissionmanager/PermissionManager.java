package com.iamwee.permissionmanager;


import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    private static PermissionManager instance;

    public static PermissionManager getInstance() {
        if (instance == null) instance = new PermissionManager();
        return instance;
    }

    public void init(Context context) {
        Dexter.initialize(context);
    }

    public void requestPermission(List<String> permissionNameList, final PermissionCallback callback) {
        if (Dexter.isRequestOngoing()) return;
        Dexter.checkPermissions(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                List<Permission> permissions = new ArrayList<>();
                permissions.addAll(getPermissionGrantedList(report.getGrantedPermissionResponses()));
                permissions.addAll(getPermissionDeniedList(report.getDeniedPermissionResponses()));
                if (callback != null)
                    callback.onPermissionResult(new PermissionResult(permissions));
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                           PermissionToken token) {
                token.continuePermissionRequest();
            }
        }, permissionNameList);
    }

    private List<Permission> getPermissionDeniedList(List<PermissionDeniedResponse> responses) {
        List<Permission> permissions = new ArrayList<>();
        if (responses != null && responses.size() > 0) {
            for (PermissionDeniedResponse response : responses) {
                permissions.add(new Permission(response.getPermissionName(), false));
            }
        }
        return permissions;
    }

    private List<Permission> getPermissionGrantedList(List<PermissionGrantedResponse> responses) {
        List<Permission> permissions = new ArrayList<>();
        if (responses != null && responses.size() > 0) {
            for (PermissionGrantedResponse response : responses) {
                permissions.add(new Permission(response.getPermissionName(), true));
            }
        }
        return permissions;
    }


    public interface PermissionCallback {
        void onPermissionResult(PermissionResult result);
    }
}