package com.mad.e.dec2022twcapp.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun FollowButton(
    following: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onFollowChange: ((Boolean) -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    size: Dp = 32.dp,
    iconSize: Dp = size / 2,
    followingContentDescription: String? = null,
    notFollowingContentDescription: String? = null,
) {
    val background = if (following) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        backgroundColor
    }

    Box(
        modifier = modifier.followButton(onFollowChange, following, enabled, background, size),
        contentAlignment = Alignment.Center
    ) {
        if (following) {
            Icon(
                imageVector = Filled.Done,
                contentDescription = followingContentDescription,
                modifier = Modifier.size(iconSize)
            )
        } else {
            Icon(
                imageVector = Filled.Add,
                contentDescription = notFollowingContentDescription,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}

private fun Modifier.followButton(
    onFollowChange: ((Boolean) -> Unit)?,
    following: Boolean,
    enabled: Boolean,
    background: Color,
    size: Dp
): Modifier = composed {
    val boxModifier = if (onFollowChange != null) {
        val interactionSource = remember { MutableInteractionSource() }
        val ripple = rememberRipple(bounded = false, radius = 24.dp)
        this
            .toggleable(
                value = following,
                onValueChange = onFollowChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = ripple
            )
    } else {
        this
    }
    boxModifier
        .clip(CircleShape)
        .background(background)
        .size(size)
}
