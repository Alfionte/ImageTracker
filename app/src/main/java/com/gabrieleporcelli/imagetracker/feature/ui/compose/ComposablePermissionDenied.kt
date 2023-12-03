@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalPermissionsApi::class)

package com.gabrieleporcelli.imagetracker.feature.ui.compose

import android.Manifest
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.application.theme.BrownDark
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
internal fun PermissionDenied(permissionState: PermissionState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.trk_location_icon),
                contentDescription = stringResource(R.string.missing_location),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.missing_location),
                textAlign = TextAlign.Center,
                color = BrownDark,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier.fillMaxWidth(), onClick = requestPermissions(permissionState)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.missing_location_button),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
private fun requestPermissions(permissionState: PermissionState) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val notificationPermissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
        ({
            notificationPermissionState.launchPermissionRequest()
            permissionState.launchPermissionRequest()
        })
    } else {
        {
            permissionState.launchPermissionRequest()
        }
    }