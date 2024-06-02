package com.moderngame.tictactoebasic.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.domain.navigation.Destination
import com.moderngame.tictactoebasic.presentation.ui.component.AvatarPlayerComponent
import com.moderngame.tictactoebasic.presentation.ui.component.TicTacToeDefautButton
import com.moderngame.tictactoebasic.presentation.ui.home.component.AvatarSelectionDialog
import com.moderngame.tictactoebasic.presentation.ui.home.component.SelectLevelDialog
import com.moderngame.tictactoebasic.presentation.ui.theme.BackgroundBoardGameColor
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.utils.GameLevels
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController, onBackPress: () -> Unit) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.uiState.collectAsState()
    HomeScreen(
        modifier = Modifier
            .fillMaxSize(),
        onStartGame = {
            homeViewModel.playSoundClick()
            homeViewModel.onClickStartGame()
        },
        highScore = uiState.highScore,
        isShowDialogChooseAvatar = uiState.isShowDialogChooseAvatar,
        onSaveChooseAvatar = {
            homeViewModel.playSoundClick()
            homeViewModel.setAvatarId(it)
        },
        onChooseAvatar = {
            homeViewModel.playSoundClick()
            homeViewModel.showDialogChooseAvatar()
        },
        avatarId = uiState.avatarId,
        isShowDialogSelectLevel = uiState.isShowDialogSelectLevel,
        onSelectedEasyLevel = {
            homeViewModel.playSoundClick()
            homeViewModel.onDismissDialogLevels()
            navController.navigate(Destination.TicTacToeScreen(GameLevels.EASY.name))
        },
        onSelectedEvilLevel = {
            homeViewModel.playSoundClick()
            homeViewModel.onDismissDialogLevels()
            navController.navigate(Destination.TicTacToeScreen(GameLevels.EVIL.name))
        },
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    highScore: Int = 0,
    onStartGame: () -> Unit = {},
    onChooseAvatar: () -> Unit = {},
    avatarId: Int = R.drawable.img_1,
    isShowDialogChooseAvatar: Boolean = false,
    onSaveChooseAvatar: (Int) -> Unit = {/* no-op */ },
    isShowDialogSelectLevel: Boolean = false,
    onSelectedEasyLevel: () -> Unit = {/* no-op */ },
    onSelectedEvilLevel: () -> Unit = {/* no-op */ },
) {
    Image(
        painter = painterResource(id = R.drawable.img_background_home),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
    ConstraintLayout(
        modifier = modifier
            .systemBarsPadding(),
    ) {
        val (header, body) = createRefs()
        Box(
            modifier = Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            AvatarPlayerComponent(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.CenterStart),
                onClick = onChooseAvatar,
                resId = avatarId
            )
            TicTacToeDefautButton(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth(0.5f)
                    .align(Alignment.Center),
                color = BackgroundBoardGameColor,
                paddingEffect = 5.dp,
                onClick = {/* no-op */ },
                enabled = false
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.label_game_won),
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = highScore.toString(),
                        color = Color.White,
                        fontSize = 30.sp
                    )
                }
            }
        }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TicTacToeDefautButton(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(horizontal = 100.dp),
            color = BackgroundBoardGameColor,
            paddingEffect = 5.dp,
            onClick = onStartGame
        ) {
            Text(
                text = stringResource(id = R.string.label_start_game),
                fontSize = 24.sp,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

    AvatarSelectionDialog(
        isShowDialog = isShowDialogChooseAvatar,
        onClickChooseAvatar = onSaveChooseAvatar,
        avatarIdFirst = avatarId
    )

    SelectLevelDialog(
        isShowDialog = isShowDialogSelectLevel,
        onClickEasyLevel = onSelectedEasyLevel,
        onClickEvilLevel = onSelectedEvilLevel,
    )

}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview() {
    TicTacToeTheme {
        HomeScreen(
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}