package com.callmesahril.tokobadminton.ui.screen.detail


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.callmesahril.tokobadminton.R
import com.callmesahril.tokobadminton.di.Injection
import com.callmesahril.tokobadminton.ui.ViewModelFactory
import com.callmesahril.tokobadminton.ui.common.UiState
import com.callmesahril.tokobadminton.ui.components.OrderButton
import com.callmesahril.tokobadminton.ui.components.ProductCounter
import com.callmesahril.tokobadminton.ui.theme.TokoBadmintonTheme
import com.callmesahril.tokobadminton.utils.formatAsCurrency

@Composable
fun DetailScreen(
    rewardId: Long,
    viewModel: DetailBadmintonViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBadmintonById(rewardId)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.badminton.image,
                    data.badminton.title,
                    data.badminton.desc,
                    data.badminton.price,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.badminton, count)
                        navigateToCart()
                    }
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    @DrawableRes image: Int,
    title: String,
    desc: String,
    basePoint: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                Image(
                    painter = painterResource(image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Rp ${formatAsCurrency(basePoint)}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(10.dp))
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = "Description",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                )
                Spacer(modifier = modifier.height(20.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify,
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(LightGray)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPoint = basePoint * orderCount
            OrderButton(
                text = "Tambah ke Keranjang : Rp ${formatAsCurrency(totalPoint)}",
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    TokoBadmintonTheme {
        DetailContent(
            R.drawable.product_4,
            "Jaket Hoodie Dicoding",
            "ONEX Badminton Frame Astrox 88 D PLAY (4UG5) - Camel Gold\n" +
                    "\n" +
                    "FRAME ONLY\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Astrox 88D Play badminton racket is built using Rotational Generator System. The counter balanced head adapts to each shot, helping you to control the drive and attack the opposition with increased acceleration, steeper angle and power on the smash. This racquet helps you to lead the attack with increased power and control.\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Technology\n" +
                    "\n" +
                    "ISOMETRIC™ , Rotational Generator System, AERO+BOX Frame, Slim Shaft\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Material\n" +
                    "\n" +
                    "Flex: Medium\n" +
                    "\n" +
                    "Frame: Graphite\n" +
                    "\n" +
                    "Shaft: Graphite\n" +
                    "\n" +
                    "Length: 10 mm longer\n" +
                    "\n" +
                    "Weight / Grip: 4U (Avg. 83g) G5,6\n" +
                    "\n" +
                    "Stringing Advice: 4U: 20 - 28 lbs\n" +
                    "\n" +
                    "Color(s)Camel Gold\n" +
                    "\n" +
                    "2021\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Made in China",
            7500,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}