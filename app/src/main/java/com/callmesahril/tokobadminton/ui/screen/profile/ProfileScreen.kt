package com.callmesahril.tokobadminton.ui.screen.profile


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.callmesahril.tokobadminton.R
import com.callmesahril.tokobadminton.ui.theme.TokoBadmintonTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Image(
                painter = painterResource(R.drawable.img_user),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(text = stringResource(R.string.nama_sahril))
            Spacer(modifier = modifier.height(10.dp))
            Text(text = stringResource(R.string.email_sahrilr165_gmail_com))

        }

    }

}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    TokoBadmintonTheme {
        ProfileScreen()
    }
}