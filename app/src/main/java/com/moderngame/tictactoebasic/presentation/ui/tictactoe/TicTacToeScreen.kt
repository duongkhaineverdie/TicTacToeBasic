package com.moderngame.tictactoebasic.presentation.ui.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.domain.navigation.Destination
import com.moderngame.tictactoebasic.presentation.ui.component.TicTacToeDefautButton
import com.moderngame.tictactoebasic.presentation.ui.component.VictoryDialog
import com.moderngame.tictactoebasic.presentation.ui.tictactoe.components.AvatarCardComponent
import com.moderngame.tictactoebasic.presentation.ui.tictactoe.components.BoardTicTacToeGame
import com.moderngame.tictactoebasic.presentation.ui.theme.BackgroundBoardGameColor
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.presentation.ui.theme.DarkBackGroundCardAvatar
import com.moderngame.tictactoebasic.utils.TicTacToe
import org.koin.androidx.compose.koinViewModel

@Composable
fun TicTacToeScreen(navController: NavHostController) {
    val ticTacToeViewModel: TicTacToeViewModel = koinViewModel()
    val uiState by ticTacToeViewModel.stateFlow.collectAsState()

    TicTacToeScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF1A2B97),
                        Color(0xFF0C1452)
                    )
                )
            )
            .systemBarsPadding(),
        timeAlive = uiState.timeAlive,
        totalAlive = uiState.totalAlive,
        onClickBoard = {
            ticTacToeViewModel.playSoundClick()
            ticTacToeViewModel.selectBoard(it)
        },
        winner = uiState.winner,
        board = uiState.board,
        onClickReplay = {
            ticTacToeViewModel.playSoundClick()
            ticTacToeViewModel.onClickReplay()
        },
        currentPlayer = uiState.currentPlayer,
        realPlayer = uiState.realPlayer,
        onBack = {
            ticTacToeViewModel.playSoundClick()
            navController.navigateUp()
        },
        onClickSetting = {
            ticTacToeViewModel.playSoundClick()
            navController.navigate(Destination.SettingScreen.fullRoute)
        },
        avatarPlayerId = uiState.avatarPlayerId
    )

}

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    timeAlive: Long = 5000,
    totalAlive: Long = 5000,
    onBack: () -> Unit = {/* no-op */ },
    onClickReplay: () -> Unit = {/* no-op */ },
    onClickSetting: () -> Unit = {/* no-op */ },
    onClickBoard: (Int) -> Unit = {/* no-op */ },
    winner: TicTacToe? = null,
    currentPlayer: TicTacToe = TicTacToe.O,
    realPlayer: TicTacToe = TicTacToe.X,
    board: MutableList<TicTacToe>,
    avatarPlayerId: Int = R.drawable.img_1,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TicTacToeDefautButton(
                modifier = Modifier.size(40.dp),
                color = DarkBackGroundCardAvatar,
                paddingEffect = 3.dp,
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            TicTacToeDefautButton(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .weight(1f),
                color = DarkBackGroundCardAvatar,
                paddingEffect = 3.dp,
                onClick = {/* no-op */ },
                enabled = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.label_time_left),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.time_left_regex, timeAlive / 1000),
                        modifier = Modifier,
                        color = Color.White
                    )
                }
            }
            TicTacToeDefautButton(
                modifier = Modifier.size(40.dp),
                color = DarkBackGroundCardAvatar,
                paddingEffect = 3.dp,
                onClick = onClickSetting
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AvatarCardComponent(
                modifier = Modifier.weight(1f),
                sizeAvatar = 100.dp,
                sizeTicTacToe = 30.dp,
                sizeInsideTicTacToe = 10.dp,
                marginPerItem = 10.dp,
                sizeCircleProcessAvatar = 8.dp,
                isTurn = currentPlayer == realPlayer,
                ticTacToe = realPlayer,
                processAvatar = timeAlive / totalAlive.toFloat(),
                namePlayer = stringResource(id = R.string.name_player_real),
                turnOf = stringResource(id = R.string.label_your_turn),
                resId = avatarPlayerId
            )
            AvatarCardComponent(
                modifier = Modifier.weight(1f),
                sizeAvatar = 100.dp,
                sizeTicTacToe = 30.dp,
                sizeInsideTicTacToe = 10.dp,
                marginPerItem = 10.dp,
                sizeCircleProcessAvatar = 8.dp,
                paddingImageAvatar = 3.dp,
                isTurn = currentPlayer != realPlayer,
                ticTacToe = if (realPlayer == TicTacToe.X) TicTacToe.O else TicTacToe.X,
                namePlayer = stringResource(id = R.string.name_player_computer),
                processAvatar = timeAlive / totalAlive.toFloat(),
                turnOf = stringResource(id = R.string.label_computer_turn),
                resId = R.drawable.img_avatar_robot
            )
        }
        BoardTicTacToeGame(
            modifier = Modifier
                .padding(10.dp)
                .shadow(
                    5.dp,
                    RoundedCornerShape(20.dp)
                )
                .background(BackgroundBoardGameColor)
                .aspectRatio(1f),
            onClickItem = onClickBoard,
            ticTacToeList = board,
            isPlayer = currentPlayer == realPlayer
        )
    }

    VictoryDialog(
        isShowDialog = winner != null,
        onClickReplay = onClickReplay,
        resId = if (winner == realPlayer) avatarPlayerId else R.drawable.img_avatar_robot
    )
}

@Composable
@Preview(name = "TrueColorGame", showSystemUi = true)
private fun NumberGuessingGameScreenPreview() {
    TicTacToeTheme {
        TicTacToeScreen(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF1A2B97),
                            Color(0xFF0C1452)
                        )
                    )
                ),
            board = arrayListOf(),
        )
    }
}

