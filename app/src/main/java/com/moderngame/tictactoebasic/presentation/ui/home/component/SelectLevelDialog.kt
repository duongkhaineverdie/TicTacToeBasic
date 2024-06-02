package com.moderngame.tictactoebasic.presentation.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.presentation.ui.component.CustomDialog
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.utils.bounceClick

@Composable
fun SelectLevelDialog(
    isShowDialog: Boolean = true,
    onDismissRequest: () -> Unit = {/* no-op */ },
    onClickEasyLevel: () -> Unit = {/* no-op */ },
    onClickEvilLevel: () -> Unit = {/* no-op */ },
) {
    CustomDialog(isShowDialog = isShowDialog, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(Color.Transparent)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.img_background_dialog_levels),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(id = R.string.label_levels),
                    fontSize = 25.sp,
                    letterSpacing = 3.sp,
                    modifier = Modifier
                        .align(
                            Alignment.TopCenter
                        )
                        .padding(top = 20.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.6f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .bounceClick {
                            onClickEasyLevel()
                        }, contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.img_button_first),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.label_easy),
                            fontSize = 25.sp,
                            letterSpacing = 3.sp
                        )
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .bounceClick {
                            onClickEvilLevel()
                        }, contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.img_button_second),
                            contentDescription = null,
                        )
                        Text(
                            text = stringResource(id = R.string.label_evil),
                            fontSize = 25.sp,
                            letterSpacing = 3.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "AvatarSelectionDialog")
@Composable
private fun PreviewAvatarSelectionDialog() {
    TicTacToeTheme {
        SelectLevelDialog(
            isShowDialog = true,
        )
    }
}