package com.moderngame.tictactoebasic.presentation.ui.tictactoe.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moderngame.tictactoebasic.presentation.ui.theme.DarkBackgroundItem
import com.moderngame.tictactoebasic.presentation.ui.theme.GreenTicTacToeO
import com.moderngame.tictactoebasic.presentation.ui.theme.RedTicTacToeX
import com.moderngame.tictactoebasic.utils.TicTacToe

@Composable
fun ItemTicTacToe(
    modifier: Modifier = Modifier,
    sizeOfObject: Dp = 50.dp,
    isWin: Boolean = true,
    ticTacToe: TicTacToe = TicTacToe.EMPTY,
    paddingInsideItem: Dp = 10.dp,
) {
    val containerColor = if (isWin)
        GreenTicTacToeO
    else
        DarkBackgroundItem
    Box(
        modifier
            .background(containerColor)
            .padding(paddingInsideItem)

    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2
            val centerX = size.width / 2
            val centerY = size.height / 2
            val width = size.width
            val height = size.height

            when (ticTacToe) {
                TicTacToe.O -> {
                    drawCircle(if (isWin) Color.White else GreenTicTacToeO, radius = radius)
                    drawCircle(containerColor, radius = centerY - sizeOfObject.toPx())
                }

                TicTacToe.X -> {
                    val pathFirst = Path()
                    pathFirst.moveTo(0f, 0f)
                    pathFirst.lineTo(0f + sizeOfObject.toPx(), 0f)
                    pathFirst.lineTo(width, height)
                    pathFirst.lineTo(width - sizeOfObject.toPx(), height)
                    pathFirst.close()
                    val pathSecond = Path()
                    pathSecond.moveTo(0f, height)
                    pathSecond.lineTo(0f + sizeOfObject.toPx(), height)
                    pathSecond.lineTo(width, 0f)
                    pathSecond.lineTo(width - sizeOfObject.toPx(), 0f)
                    pathSecond.close()
                    drawPath(
                        path = pathFirst,
                        color = RedTicTacToeX,
                    )
                    drawPath(
                        path = pathSecond,
                        color = RedTicTacToeX,
                    )
                }

                TicTacToe.EMPTY -> {}
            }
        }


    }
}

@Preview(name = "ItemTicTacToe", showSystemUi = true)
@Composable
private fun PreviewItemTicTacToe() {
    ItemTicTacToe(
        modifier = Modifier
            .aspectRatio(1f)
    )
}