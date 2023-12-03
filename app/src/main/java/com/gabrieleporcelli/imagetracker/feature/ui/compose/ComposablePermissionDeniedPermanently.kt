package com.gabrieleporcelli.imagetracker.feature.ui.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrieleporcelli.imagetracker.R
import com.gabrieleporcelli.imagetracker.application.theme.BrownDark

@Composable
internal fun PermissionDeniedPermanently() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current

        Column {
            Image(
                painter = painterResource(id = R.drawable.no_location_icon),
                contentDescription = stringResource(R.string.missing_location_permanent),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.missing_location_permanent),
                textAlign = TextAlign.Center,
                color = BrownDark,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { context.openSettings() }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.missing_location_permanent_button),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

private fun Context.openSettings() {
    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    })
}