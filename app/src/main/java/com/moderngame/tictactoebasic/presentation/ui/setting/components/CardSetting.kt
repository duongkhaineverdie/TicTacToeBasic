package com.moderngame.tictactoebasic.presentation.ui.setting.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme

@Composable
fun CardSetting(
    modifier: Modifier = Modifier,
    isShowUnderDivider: Boolean = false,
    titleCard: String = stringResource(id = R.string.app_name),
    content: @Composable () -> Unit,
) {
    Box(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = titleCard,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                )
                content()
            }
            if (isShowUnderDivider) {
                HorizontalDivider()
            }
        }
    }
}

@Preview(name = "CardSettingComponent", showSystemUi = true)
@Composable
private fun PreviewCardSettingComponent() {
    TicTacToeTheme {
        CardSetting(
            modifier = Modifier.fillMaxWidth(),
        ) {

        }
    }
}