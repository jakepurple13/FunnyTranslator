package com.gabb.funnytranslator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
                trailingIcon = {
                    AnimatedVisibility(
                        visible = translatorViewModel.text.isNotBlank(),
                    ) {
                        IconButton(
                            onClick = { translatorViewModel.text = "" },
                        ) { Icon(Icons.Default.Clear, null) }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
                    .fillMaxWidth()
            )

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

@OptIn(ExperimentalMaterial3Api::class)
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
            var showTranslatorDialog by remember { mutableStateOf(false) }

            ElevatedAssistChip(
                onClick = { showTranslatorDialog = true },
                label = { Text(translatorViewModel.chosenTranslator) },
            )

            if (showTranslatorDialog) {
                ModalBottomSheet(
                    onDismissRequest = { showTranslatorDialog = false },
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState()),
                    ) {
                        translatorViewModel.translatorList.forEach { translator ->
                            val translatorName = translator.toString()
                            val icon = when {
                                translatorName.contains("Cat")
                                        || translatorName.contains("Dog") -> Icons.Default.Pets

                                translatorName.contains("Yoda")
                                        || translatorName.contains("Shakespeare")
                                        || translatorName.contains("Valley Girl") -> Icons.Default.Translate

                                translatorName.contains("Morse")
                                        || translatorName.contains("Leet") -> Icons.Default.Code

                                translatorName.contains("Groot")
                                        || translatorName.contains("Pirate")
                                        || translatorName.contains("Minionese") -> Icons.Default.FormatQuote

                                else -> Icons.Default.Mood
                            }

                            Card(
                                onClick = {
                                    translatorViewModel.currentTranslator = translator
                                    showTranslatorDialog = false
                                }
                            ) {
                                ListItem(
                                    headlineContent = { Text(text = translatorName) },
                                    leadingContent = { Icon(imageVector = icon, contentDescription = null) },
                                    trailingContent = {
                                        if (translator == translatorViewModel.currentTranslator) {
                                            Icon(imageVector = Icons.Default.Check, contentDescription = null)
                                        }
                                    }
                                )
                            }

                            HorizontalDivider()
                        }
                    }
                }
            }

            Row {
                IconButton(
                    onClick = { translatorViewModel.text = translatorViewModel.translatedText },
                    enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
                ) { Icon(Icons.Default.SwapVert, null) }

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
