package com.mad.e.twccomposeplanner.core.ui.component


import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.mad.e.dec2022twcapp.core.ui.component.TwcButtonDefaults
import com.mad.e.dec2022twcapp.core.ui.component.TwcOutlinedButton
import com.mad.e.dec2022twcapp.core.ui.icon.TwcIcons

/**
 * Now in Android filter chip with included leading checked icon as well as text content slot.
 *
 * @param checked Whether the chip is currently checked.
 * @param onCheckedChange Called when the user clicks the chip and toggles checked.
 * @param modifier Modifier to be applied to the chip.
 * @param enabled Controls the enabled state of the chip. When `false`, this chip will not be
 * clickable and will appear disabled to accessibility services.
 * @param text The text label content.
 */
@Composable
fun TwcFilterChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit
) {
    // TODO: Replace with Chip when available in Compose Material 3: b/197399111
    TwcOutlinedButton(
        onClick = { onCheckedChange(!checked) },
        modifier = Modifier
            .toggleable(value = checked, enabled = enabled, role = Role.Button, onValueChange = {})
            .then(modifier),
        enabled = enabled,
        small = true,
        border = TwcButtonDefaults.outlinedButtonBorder(
            enabled = enabled,
            disabledColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = if (checked) {
                    TwcButtonDefaults.DisabledButtonContentAlpha
                } else {
                    TwcButtonDefaults.DisabledButtonContainerAlpha
                }
            )
        ),
        colors = TwcButtonDefaults.outlinedButtonColors(
            containerColor = if (checked) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                Color.Transparent
            },
            disabledContainerColor = if (checked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = TwcButtonDefaults.DisabledButtonContainerAlpha
                )
            } else {
                Color.Transparent
            }
        ),
        text = text,
        leadingIcon = if (checked) {
            {
                Icon(
                    imageVector = TwcIcons.Check,
                    contentDescription = null
                )
            }
        } else {
            null
        }
    )
}
