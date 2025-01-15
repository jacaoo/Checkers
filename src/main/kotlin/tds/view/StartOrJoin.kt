package tds.view

import androidx.compose.material.*
import androidx.compose.runtime.*
import tds.model.Name

enum class InputName(val txt: String) {
    Start("start")
}


@Composable
fun StartDialog(
    type: InputName,
    onCancel: ()->Unit,
    onAction: (Name)->Unit
) {
    var name by remember { mutableStateOf("") }  // Nome do Jogo
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = "Name of Game",
            style = MaterialTheme.typography.h5
        ) },
        text = { OutlinedTextField(value = name,
            onValueChange = { name = it },
            label = { Text("Name of game") }
        ) },
        confirmButton = {
            TextButton(enabled = Name.isValid(name),
                onClick = { onAction(Name(name)) }
            ) { Text("Start") }
        },
        dismissButton = {
            TextButton(onClick = onCancel){ Text("Cancel") }
        }
    )
}