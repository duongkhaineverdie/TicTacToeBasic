package com.moderngame.tictactoebasic.presentation.ui.tictactoe.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.presentation.ui.theme.DarkBackGroundCardAvatar
import com.moderngame.tictactoebasic.presentation.ui.theme.GreenTicTacToeO
import com.moderngame.tictactoebasic.presentation.ui.theme.RedTicTacToeX
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.utils.TicTacToe

@Composable
fun AvatarCardComponent(
    modifier: Modifier = Modifier,
    namePlayer: String = "Player 1",
    ticTacToe: TicTacToe = TicTacToe.X,
    processAvatar: Float = 0.3f,
    sizeAvatar: Dp = 150.dp,
    sizeCircleProcessAvatar: Dp = 10.dp,
    sizeTicTacToe: Dp = 50.dp,
    sizeInsideTicTacToe: Dp = 15.dp,
    marginPerItem: Dp = 15.dp,
    fontSize: TextUnit = 20.sp,
    paddingImageAvatar: Dp = 2.dp,
    isTurn: Boolean = true,
    turnOf: String = "",
    resId: Int = R.drawable.img_avatar_example
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_arrow_down))
    val scaleAnimation: Float by animateFloatAsState(
        targetValue = if (isTurn) 1f else 0.9f,
        label = "ScaleAnimation",
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )
    val colorDarkAnimation: Float by animateFloatAsState(
        targetValue = if (isTurn) 1f else 0.7f,
        label = "ColorDarkAnimation",
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        modifier = modifier
            .scale(scaleAnimation)
            .alpha(colorDarkAnimation),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .alpha(colorDarkAnimation),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = sizeAvatar / 2)
                    .alpha(colorDarkAnimation),
                colors = CardDefaults.cardColors(
                    containerColor = DarkBackGroundCardAvatar
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = marginPerItem)
                        .alpha(colorDarkAnimation),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(marginPerItem)
                ) {
                    Text(
                        text = namePlayer,
                        modifier = Modifier
                            .padding(top = sizeAvatar / 2 + marginPerItem)
                            .alpha(colorDarkAnimation),
                        fontSize = fontSize
                    )
                    Canvas(
                        modifier = Modifier
                            .size(sizeTicTacToe)
                            .alpha(colorDarkAnimation)
                    ) {
                        val centerX = size.width / 2
                        val centerY = size.height / 2
                        val height = size.height
                        val width = size.width

                        when (ticTacToe) {
                            TicTacToe.X -> {
                                val pathFirst = Path()
                                pathFirst.moveTo(0f, 0f)
                                pathFirst.lineTo(0f + sizeInsideTicTacToe.toPx(), 0f)
                                pathFirst.lineTo(width, height)
                                pathFirst.lineTo(width - sizeInsideTicTacToe.toPx(), height)

                                val pathSecond = Path()
                                pathSecond.moveTo(0f, height)
                                pathSecond.lineTo(0f + sizeInsideTicTacToe.toPx(), height)
                                pathSecond.lineTo(width, 0f)
                                pathSecond.lineTo(width - sizeInsideTicTacToe.toPx(), 0f)

                                drawPath(pathFirst, color = RedTicTacToeX)
                                drawPath(pathSecond, color = RedTicTacToeX)

                            }

                            TicTacToe.O -> {
                                drawCircle(
                                    color = GreenTicTacToeO,
                                    style = Stroke(
                                        width = sizeInsideTicTacToe.toPx(),
                                    ),
                                    radius = centerY - sizeInsideTicTacToe.toPx() / 2
                                )
                            }

                            else -> {/* no-op */
                            }

                        }
                    }
                }
            }
            AvatarComponent(
                modifier = Modifier
                    .size(sizeAvatar)
                    .alpha(colorDarkAnimation),
                sizeCircleProcessAvatar = sizeCircleProcessAvatar,
                processAvatar = processAvatar,
                paddingImage = paddingImageAvatar,
                isTurn = isTurn,
                resId = resId
            )
        }

        if (isTurn) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = turnOf)
            LottieAnimation(
                modifier = Modifier.size(80.dp),
                composition = composition,
                reverseOnRepeat = true,
                iterations = Int.MAX_VALUE
            )
        }
    }
}

@Preview(name = "AvatarCardComponent", showSystemUi = true, backgroundColor = 0xFF456545)
@Composable
private fun PreviewAvatarCardComponent() {
    TicTacToeTheme {
        AvatarCardComponent(
            modifier = Modifier.fillMaxSize()
        )
    }
}