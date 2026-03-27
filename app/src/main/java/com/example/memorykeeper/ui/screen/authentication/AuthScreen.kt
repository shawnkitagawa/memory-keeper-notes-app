package com.example.memorykeeper.ui.screen.authentication


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.memorykeeper.R
import com.example.memorykeeper.ui.theme.Dimens
import com.example.memorykeeper.ui.uistate.AuthScreenState
import com.example.memorykeeper.ui.uistate.AuthUiState
import com.example.memorykeeper.ui.viewmodel.AuthViewModel


@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    navToSignUp: () -> Unit,
    uiState: AuthScreenState
)
{

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp)
        .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(stringResource(R.string.selfora),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = Dimens.paddingMedium))
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingXL))
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingXL))

        Text(text = stringResource(R.string.login_to_your_account)
            ,textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(start = 32.dp),
            style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingXL))
        TextField(
            value = uiState.email,
            onValueChange = {viewModel.updateEmail(it)},
            label = {Text(text = "Email", style = MaterialTheme.typography.titleMedium)},
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .padding(start = 32.dp, end = 32.dp, bottom = Dimens.paddingMedium)
                .shadow(2.dp,RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingLarge))
        TextField(
            value = uiState.password,
            onValueChange = {viewModel.updatePass(it)},
            label = {Text(text = "Password", style = MaterialTheme.typography.titleMedium)},
            modifier = Modifier.fillMaxWidth().height(65.dp)
                .padding(start = 32.dp, end = 32.dp, bottom = Dimens.paddingMedium)
                .shadow(2.dp,RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingLarge))
        Button(
            onClick = {viewModel.SignIn(email = uiState.email, password = uiState.password)},
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(62.dp)
                .padding(start = 32.dp, end = 32.dp, bottom = Dimens.paddingMedium)
                .shadow(3.dp, RoundedCornerShape(8.dp)))
            {
                Text(text = "Sign in", style = MaterialTheme.typography.titleMedium)
            }
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingSmall))

        Row()
        {
            Text(text = "Don't have an account? ",
                style = MaterialTheme.typography.titleSmall)
            Text(text = "Sign Up",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    onClick = navToSignUp
                ))

        }
        Spacer(modifier = Modifier.padding(bottom = Dimens.paddingMedium))

        when(uiState.authState)
        {
             is AuthUiState.Error -> Text(text = ((uiState.authState as AuthUiState.Error).message), color = Color.Red)
            else -> {}
        }

    }
}