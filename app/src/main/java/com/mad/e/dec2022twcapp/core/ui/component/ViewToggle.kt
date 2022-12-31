package com.mad.e.dec2022twcapp.core.ui.component

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mad.e.dec2022twcapp.core.ui.component.TwcTextButton
import com.mad.e.dec2022twcapp.core.ui.icon.TwcIcons

/**
 * Now in Android view toggle button with included trailing icon as well as compact and expanded
 * text label content slots.
 *
 * @param expanded Whether the view toggle is currently in expanded mode or compact mode.
 * @param onExpandedChange Called when the user clicks the button and toggles the mode.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button. When `false`, this button will not be
 * clickable and will appear disabled to accessibility services.
 * @param compactText The text label content to show in expanded mode.
 * @param expandedText The text label content to show in compact mode.
 */
@Composable
fun TwcViewToggleButton(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compactText: @Composable () -> Unit,
    expandedText: @Composable () -> Unit
) {
    TwcTextButton(
        onClick = { onExpandedChange(!expanded) },
        modifier = modifier,
        enabled = enabled,
        text = if (expanded) expandedText else compactText,
        trailingIcon = {
            Icon(
                imageVector = if (expanded) TwcIcons.ViewDay else TwcIcons.ShortText,
                contentDescription = null
            )
        }
    )
}
