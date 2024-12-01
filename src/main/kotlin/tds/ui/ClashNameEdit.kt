package tds.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

enum class Action(val text: String) {
    Game("Game"), Options("Options"), Exit("Exit"), Start("Start"),
    Refresh("Refresh")
}

@Composable
fun ClashNameEdit(
    action: Action,
    onCancel: ()-> Unit,
    onAction: (Number)-> Unit
) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(action.text) },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Clash Name") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onAction(when(action) {
                        Action.Start -> name.toInt()
                        else -> 0
                    })
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}
