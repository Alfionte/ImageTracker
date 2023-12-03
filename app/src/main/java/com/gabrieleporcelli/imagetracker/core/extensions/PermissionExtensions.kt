package com.gabrieleporcelli.imagetracker.core.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasFineLocationPermission(): Boolean =
    isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION) && isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)

private fun Context.isPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED