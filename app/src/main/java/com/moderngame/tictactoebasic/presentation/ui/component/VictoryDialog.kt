package com.moderngame.tictactoebasic.presentation.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.presentation.ui.theme.BackgroundBoardGameColor
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VictoryDialog(
    isShowDialog: Boolean,
    onDismissRequest: () -> Unit = {/* no-op */},
    onClickReplay: () -> Unit = {/* no-op */},
    resId: Int = R.drawable.img_avatar_example
) {
    val drawImage = ImageBitmap.imageResource(id = resId)
    CustomDialog(isShowDialog = isShowDialog, onDismissRequest = { /*TODO*/ }) {
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
                    painter = painterResource(id = R.drawable.img_dialog_victory),
                    contentDescription = null,
                )
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .height(120.dp)
                    ) {
                        val size = size
                        val radius = minOf(size.width, size.height) / 2f
                        val centerX = size.width / 2f
                        val centerY = size.height / 2f

                        val path = Path().apply {
                            // Pre-calculate all corner coordinates
                            val angles = FloatArray(6)
                            for (i in 0 until 6) {
                                angles[i] = (Math.PI.toFloat() * (i * 60 / 180.0)).toFloat()
                            }

                            moveTo(
                                (centerX + radius * cos(angles[0].toDouble())).toFloat(),
                                (centerY + radius * sin(angles[0].toDouble())).toFloat()
                            )
                            for (i in 1 until 6) {
                                lineTo(
                                    (centerX + radius * cos(angles[i].toDouble())).toFloat(),
                                    (centerY + radius * sin(angles[i].toDouble())).toFloat()
                                )
                            }
                            close()
                        }
                        rotate(90f){
                            clipPath(path, clipOp = ClipOp.Intersect) {
                                rotate(-90f){
                                    drawImage(
                                        drawImage,
                                        dstSize = IntSize(
                                            radius.toInt() * 2,
                                            radius.toInt() * 2,
                                        ),
                                        filterQuality = FilterQuality.Medium,
                                        dstOffset = IntOffset(
                                            centerX.toInt() - radius.toInt(),
                                            0
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
                TicTacToeDefautButton(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 40.dp)
                        .align(Alignment.BottomCenter),
                    color = BackgroundBoardGameColor,
                    paddingEffect = 5.dp,
                    onClick = onClickReplay
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = stringResource(id = R.string.label_replay),
                        fontSize = 24.sp,
                    )
                }
            }
        }
    }
}

@Preview(name = "VictoryDialog")
@Composable
private fun PreviewVictoryDialog() {
    TicTacToeTheme {
        VictoryDialog(
            isShowDialog = true
        )
    }
}