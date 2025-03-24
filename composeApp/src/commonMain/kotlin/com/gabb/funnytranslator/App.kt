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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(initialText: String = "") {
    MaterialTheme(getColorScheme()) {
        TranslatorContent(
            translatorViewModel = viewModel { TranslatorViewModel(initialText) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslatorContent(
    translatorViewModel: TranslatorViewModel,
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
                value = translatorViewModel.text,
                onValueChange = { translatorViewModel.text = it },
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
                onClick = { translatorViewModel.text = translatorViewModel.translatedText },
                enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
            ) { Icon(Icons.Default.ArrowUpward, null) }

            TranslatedContent(
                translatorViewModel = translatorViewModel,
                clipboard = clipboard,
                scope = scope,
                snackbarHostState = snackbarHostState
            )

            Text(
                getPlatform().name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

@Composable
private fun TranslatedContent(
    translatorViewModel: TranslatorViewModel,
    clipboard: ClipboardManager,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    Card(
        onClick = {
            translatorViewModel.copyToClipboard(clipboard)
            scope.launch {
                snackbarHostState.showSnackbar("Copied to clipboard")
            }
        },
        enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
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
                    label = { Text(translatorViewModel.chosenTranslator) },
                )

                DropdownMenu(
                    expanded = showTranslatorDialog,
                    onDismissRequest = { showTranslatorDialog = false }
                ) {
                    translatorViewModel.translatorList.forEach { translator ->
                        DropdownMenuItem(
                            text = { Text(translator.toString()) },
                            onClick = {
                                translatorViewModel.currentTranslator = translator
                                showTranslatorDialog = false
                            }
                        )
                    }
                }
            }
            Row {
                ShareButton(
                    translatedText = translatorViewModel::translatedText,
                )
                IconButton(
                    onClick = {
                        translatorViewModel.copyToClipboard(clipboard)
                        scope.launch {
                            snackbarHostState.showSnackbar("Copied to clipboard")
                        }
                    },
                    enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
                ) { Icon(Icons.Default.CopyAll, contentDescription = "Copy") }
            }
        }

        Text(
            translatorViewModel.translatedText,
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
}
