package com.moderngame.tictactoebasic.presentation.ui.tictactoe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moderngame.tictactoebasic.presentation.ui.theme.BackgroundBoardGameColor
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.utils.TicTacToe
import com.moderngame.tictactoebasic.utils.bounceClick

@Composable
fun BoardTicTacToeGame(
    modifier: Modifier = Modifier,
    ticTacToeList: MutableList<TicTacToe> = arrayListOf(
        TicTacToe.EMPTY,
        TicTacToe.O,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.X,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY,
        TicTacToe.EMPTY
    ),
    paddingEachItem: Dp = 10.dp,
    paddingInsideItem: Dp = 15.dp,
    sizeOfObject: Dp = 20.dp,
    onClickItem: (Int) -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    isPlayer: Boolean = true,
) {
    Box(modifier.padding(paddingEachItem)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(paddingEachItem),
            horizontalArrangement = Arrangement.spacedBy(paddingEachItem)
        ) {
            itemsIndexed(ticTacToeList) {index, ticTacToe ->
                ItemTicTacToe(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .bounceClick(
                            enabled = ticTacToe == TicTacToe.EMPTY && isPlayer,
                        ) {
                            onClickItem(index)
                        }
                        .clip(shape)
                    ,
                    ticTacToe = ticTacToe,
                    sizeOfObject = sizeOfObject,
                    isWin = false,
                    paddingInsideItem = paddingInsideItem
                )
            }
        }
    }
}

@Preview(name = "BoardTicTacToeGame", showSystemUi = true)
@Composable
private fun PreviewBoardTicTacToeGame() {
    TicTacToeTheme {
        BoardTicTacToeGame(
            modifier = Modifier
                .padding(20.dp)
                .shadow(
                    5.dp,
                    RoundedCornerShape(20.dp)
                )
                .background(BackgroundBoardGameColor)
                .aspectRatio(1f),
            onClickItem = {/* no-op */}
        )
    }
}