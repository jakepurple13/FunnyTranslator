package com.gabb.funnytranslator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.animateColorScheme
import com.materialkolor.rememberDynamicColorScheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Constants used throughout the UI
 */
private object AppConstants {
    const val APP_TITLE = "Funny Translator"
    const val TEXT_FIELD_LABEL = "Text to translate"
    const val COPIED_MESSAGE = "Copied to clipboard"
    const val ANIMATION_DURATION = 500
    val DEFAULT_PADDING = 16.dp
}

/**
 * Main app composable that sets up the theme and content.
 * The theme color is dynamically based on the selected translator.
 *
 * @param initialText Initial text to be translated
 * @param translatorViewModel ViewModel that manages the translator state
 */
@Composable
@Preview
fun App(
    initialText: String = "",
    translatorViewModel: TranslatorViewModel = viewModel { TranslatorViewModel(initialText) },
) {
    MaterialTheme(
        animateColorScheme(
            when (val translator = translatorViewModel.currentTranslator) {
                null -> getColorScheme()
                else -> rememberDynamicColorScheme(
                    primary = translator.getColor(),
                    isDark = isSystemInDarkTheme(),
                    isAmoled = false,
                    style = PaletteStyle.Fidelity
                )
            },
            tween(durationMillis = AppConstants.ANIMATION_DURATION)
        )
    ) {
        TranslatorContent(
            translatorViewModel = translatorViewModel
        )
    }
}

/**
 * Main content of the translator app.
 * Contains the input field, translator selection, and translated output.
 *
 * @param translatorViewModel ViewModel that manages the translator state
 */
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
                title = { Text(AppConstants.APP_TITLE) },
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
                label = { Text(AppConstants.TEXT_FIELD_LABEL) },
                shape = MaterialTheme.shapes.medium,
                trailingIcon = {
                    AnimatedVisibility(
                        visible = translatorViewModel.text.isNotBlank(),
                    ) {
                        IconButton(
                            onClick = { translatorViewModel.text = "" },
                        ) { Icon(Icons.Default.Clear, contentDescription = "Clear text") }
                    }
                },
                modifier = Modifier
                    .padding(AppConstants.DEFAULT_PADDING)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(AppConstants.DEFAULT_PADDING)
                    .fillMaxWidth()
            )

            TranslatedContent(
                translatorViewModel = translatorViewModel,
                onCopy = {
                    scope.launch {
                        clipboard.setText(AnnotatedString(translatorViewModel.translatedText))
                        snackbarHostState.showSnackbar(AppConstants.COPIED_MESSAGE)
                    }
                }
            )

            Text(
                getPlatform().name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }
    }
}

/**
 * Displays the translated text and provides controls for selecting translators and copying text.
 *
 * @param translatorViewModel ViewModel that manages the translator state
 * @param onCopy Callback for when the user wants to copy the translated text
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslatedContent(
    translatorViewModel: TranslatorViewModel,
    onCopy: () -> Unit,
) {
    Card(
        onClick = onCopy,
        enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
        modifier = Modifier.padding(AppConstants.DEFAULT_PADDING)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppConstants.DEFAULT_PADDING)
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
                            Card(
                                onClick = {
                                    translatorViewModel.currentTranslator = translator
                                    showTranslatorDialog = false
                                }
                            ) {
                                ListItem(
                                    headlineContent = { Text(text = translator.toString()) },
                                    leadingContent = {
                                        Icon(
                                            imageVector = translator.getIcon(),
                                            contentDescription = "Translator icon"
                                        )
                                    },
                                    trailingContent = {
                                        if (translator == translatorViewModel.currentTranslator) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected translator"
                                            )
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
                ) { Icon(Icons.Default.SwapVert, contentDescription = "Use translation as input") }

                ShareButton(
                    translatedText = translatorViewModel::translatedText,
                )
                IconButton(
                    onClick = onCopy,
                    enabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null,
                ) { Icon(Icons.Default.CopyAll, contentDescription = "Copy translation") }
            }
        }

        Text(
            translatorViewModel.translatedText,
            modifier = Modifier
                .padding(AppConstants.DEFAULT_PADDING)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(AppConstants.DEFAULT_PADDING)
                .fillMaxWidth()
        )
    }
}
