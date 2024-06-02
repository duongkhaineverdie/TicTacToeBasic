package com.moderngame.tictactoebasic.presentation.ui.matchgame.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.moderngame.tictactoebasic.R
import com.moderngame.tictactoebasic.presentation.ui.theme.DarkProcessAvatar
import com.moderngame.tictactoebasic.presentation.ui.theme.TicTacToeTheme
import com.moderngame.tictactoebasic.presentation.ui.theme.YellowProcessAvatar

@Composable
fun AvatarComponent(
    modifier: Modifier = Modifier,
    sizeCircleProcessAvatar: Dp = 20.dp,
    paddingImage: Dp = 2.dp,
    processAvatar: Float = 0.8f,
    isTurn: Boolean = true,
    resId: Int = R.drawable.img_avatar_example
) {
    val drawImage = ImageBitmap.imageResource(id = resId)
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val width = size.width
        val circlePath = Path().apply {
            addOval(
                Rect(
                    center = Offset(
                        centerX,
                        centerY
                    ),
                    radius = centerX - sizeCircleProcessAvatar.toPx() - paddingImage.toPx()
                ),
            )
        }

        if (isTurn){
            drawCircle(
                color = DarkProcessAvatar,
                radius = centerX,
                center = Offset(
                    centerX,
                    centerY
                ),
                style = Fill
            )
        }
        clipPath(circlePath, clipOp = ClipOp.Intersect) {
            drawImage(
                drawImage,
                dstSize = IntSize(
                    centerX.toInt() * 2 + sizeCircleProcessAvatar.toPx().toInt(),
                    centerX.toInt() * 2 + sizeCircleProcessAvatar.toPx().toInt(),
                ),
                filterQuality = FilterQuality.Medium,
                dstOffset = IntOffset(
                    0 - sizeCircleProcessAvatar.toPx().toInt() / 2,
                    0
                ),
            )
        }

        if (isTurn) {
            drawArc(
                color = YellowProcessAvatar,
                startAngle = 270f,
                sweepAngle = -(360f * processAvatar),
                useCenter = false,
                topLeft = Offset(
                    width / 2 - centerX + sizeCircleProcessAvatar.toPx() / 2,
                    0f + sizeCircleProcessAvatar.toPx() / 2
                ),
                size = Size(
                    centerX * 2 - sizeCircleProcessAvatar.toPx(),
                    centerX * 2 - sizeCircleProcessAvatar.toPx()
                ),
                style = Stroke(
                    width = sizeCircleProcessAvatar.toPx(),
                    cap = StrokeCap.Butt
                )
            )
        }

    }
}

@Preview(name = "AvatarCardComponent", showSystemUi = true)
@Composable
private fun PreviewAvatarCardComponent() {
    TicTacToeTheme {
        AvatarComponent(
            modifier = Modifier.aspectRatio(1f)
        )
    }
}