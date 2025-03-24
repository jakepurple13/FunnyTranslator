package com.gabb.funnytranslator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabb.funnytranslator.translators.*
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicColorScheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    initialText: String = "",
    translatorViewModel: TranslatorViewModel = viewModel { TranslatorViewModel(initialText) },
) {
    MaterialTheme(
        when (val translator = translatorViewModel.currentTranslator) {
            null -> getColorScheme()
            else -> rememberDynamicColorScheme(
                primary = translator.getColor(),
                isDark = isSystemInDarkTheme(),
                isAmoled = false,
                style = PaletteStyle.Fidelity
            )
        }.animate()
    ) {
        TranslatorContent(
            translatorViewModel = translatorViewModel
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
                onCopy = {
                    scope.launch {
                        clipboard.setText(AnnotatedString(translatorViewModel.translatedText))
                        snackbarHostState.showSnackbar("Copied to clipboard")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslatedContent(
    translatorViewModel: TranslatorViewModel,
    onCopy: () -> Unit,
) {
    Card(
        onClick = onCopy,
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
                            val icon = when (translator) {
                                is CatTranslator, is DogTranslator -> Icons.Default.Pets
                                is YodaTranslator -> Icons.Default.RocketLaunch
                                is ShakespeareTranslator -> Icons.Default.HistoryEdu
                                is MorseCode -> Icons.AutoMirrored.Filled.ListAlt
                                is LeetSpeak -> Icons.Default.Code
                                is Pirate -> Icons.Default.Sailing
                                is ValleyGirlTranslator -> Icons.Default.LocalFlorist
                                is GrootTranslator -> Icons.Default.Park
                                is Minionese -> Icons.Default.Groups3
                                is Uwuify -> Icons.Default.EmojiEmotions
                                is SurferDudeTranslator -> Icons.Default.Surfing
                                else -> Icons.Default.CatchingPokemon
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
                    onClick = onCopy,
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

@Composable
fun Color.animate(label: String = "") = animateColorAsState(this, label = label)

@Composable
private fun ColorScheme.animate() = copy(
    primary.animate().value,
    onPrimary.animate().value,
    primaryContainer.animate().value,
    onPrimaryContainer.animate().value,
    inversePrimary.animate().value,
    secondary.animate().value,
    onSecondary.animate().value,
    secondaryContainer.animate().value,
    onSecondaryContainer.animate().value,
    tertiary.animate().value,
    onTertiary.animate().value,
    tertiaryContainer.animate().value,
    onTertiaryContainer.animate().value,
    background.animate().value,
    onBackground.animate().value,
    surface.animate().value,
    onSurface.animate().value,
    surfaceVariant.animate().value,
    onSurfaceVariant.animate().value,
    surfaceTint.animate().value,
    inverseSurface.animate().value,
    inverseOnSurface.animate().value,
    error.animate().value,
    onError.animate().value,
    errorContainer.animate().value,
    onErrorContainer.animate().value,
    outline.animate().value,
)
