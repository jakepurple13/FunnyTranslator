package com.gabb.funnytranslator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabb.funnytranslator.translators.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(initialText: String = "") {
    MaterialTheme(getColorScheme()) {
        TranslatorContent(
            translators = viewModel { Translators(initialText) }
        )
    }
}

class Translators(
    initialText: String,
) : ViewModel() {
    val translatorList = listOf(
        Uwuify,
        Pirate,
        LeetSpeak,
        GrootTranslator,
        MorseCode,
        CatTranslator,
        DogTranslator,
        Minionese,
        ShakespeareTranslator,
        ValleyGirlTranslator,
        YodaTranslator,
    )

    var currentTranslator by mutableStateOf<Translator?>(translatorList.random())

    val chosenTranslator by derivedStateOf {
        currentTranslator?.toString() ?: "No translator selected"
    }

    var text by mutableStateOf(initialText)

    val translatedText by derivedStateOf {
        val string = if (text.isBlank()) null else translate(text)
        string ?: "No translation"
    }

    fun translate(text: String): String? = currentTranslator?.translate(text)

    fun copyToClipboard(clipboard: ClipboardManager) {
        translatedText.let { clipboard.setText(AnnotatedString(it)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorContent(
    translators: Translators,
) {
    val clipboard = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Funny Translator") },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = translators.text,
                onValueChange = { translators.text = it },
                label = { Text("Text to translate") },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            FilledTonalIconButton(
                onClick = { translators.text = translators.translatedText },
                enabled = translators.text.isNotBlank() && translators.currentTranslator != null,
            ) { Icon(Icons.Default.ArrowUpward, null) }

            Card(
                onClick = {
                    translators.copyToClipboard(clipboard)
                    scope.launch {
                        snackbarHostState.showSnackbar("Copied to clipboard")
                    }
                },
                enabled = translators.text.isNotBlank() && translators.currentTranslator != null,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column {
                        var showTranslatorDialog by remember { mutableStateOf(false) }

                        ElevatedAssistChip(
                            onClick = { showTranslatorDialog = true },
                            label = { Text(translators.chosenTranslator) },
                        )

                        DropdownMenu(
                            expanded = showTranslatorDialog,
                            onDismissRequest = { showTranslatorDialog = false }
                        ) {
                            translators.translatorList.forEach { translator ->
                                DropdownMenuItem(
                                    text = { Text(translator.toString()) },
                                    onClick = {
                                        translators.currentTranslator = translator
                                        showTranslatorDialog = false
                                    }
                                )
                            }
                        }
                    }
                    Row {
                        ShareButton(
                            translatedText = translators::translatedText,
                        )
                        IconButton(
                            onClick = {
                                translators.copyToClipboard(clipboard)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Copied to clipboard")
                                }
                            },
                            enabled = translators.text.isNotBlank() && translators.currentTranslator != null,
                        ) { Icon(Icons.Default.CopyAll, contentDescription = "Copy") }
                    }
                }

                Text(
                    translators.translatedText,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

            Text(
                getPlatform().name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}
