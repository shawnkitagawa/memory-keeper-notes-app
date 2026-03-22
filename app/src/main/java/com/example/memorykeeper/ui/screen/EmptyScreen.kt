package com.example.memorykeeper.ui.screen

import android.service.quicksettings.Tile
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memorykeeper.R
import com.example.memorykeeper.ui.theme.Dimens
import com.example.memorykeeper.ui.theme.MemoryKeeperTheme

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    ) {
    Column(modifier = modifier)
    {
        titleTopBar(modifier = modifier)
        EmptyDesign()

    }


}
@Composable
fun titleTopBar(
    modifier: Modifier = Modifier,
)
{
    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 32.dp)
        .height(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier.weight(1f))
        Text(stringResource(R.string.selfora),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold)
    }

}
@Composable
fun EmptyDesign(modifier: Modifier = Modifier.padding(start = 16.dp, end = Dimens.paddingXL, top = Dimens.paddingXL, bottom = Dimens.paddingXL))
{
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        Image(
            painter = painterResource(R.drawable.industrial_designer_digital_art),
            contentDescription = "emptyDesignImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(bottom = Dimens.paddingLarge).height(350.dp).fillMaxWidth()
                .shadow(8.dp,RoundedCornerShape(16.dp)
                    ).clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingXL))
        Text(
            text = stringResource(R.string.discipline_creates_freedom),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingLarge))
        Text(
            text = stringResource(R.string.focus_build_repeat),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingLarge))
        Text(
            text = stringResource(R.string.designed_to_keep_you_consistent_each_day_becomes_a_step_toward_discipline_clarity_and_personal_growth),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingXL))
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingLarge))
        Button(
            onClick = {},
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(horizontal = Dimens.paddingLarge).height(50.dp).width(250.dp)
        )
        {
            Text("Start Discipline")
        }


    }
}

@Preview
@Composable
fun EmptyPreview()
{
    MemoryKeeperTheme() {
        EmptyDesign()
    }
}