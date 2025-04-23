package com.gabb.funnytranslator

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.materialkolor.PaletteStyle
import com.materialkolor.ktx.animateColorScheme
import com.materialkolor.rememberDynamicColorScheme
import funnytranslator.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Constants used throughout the UI
 */
internal object AppConstants {
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun TranslatorContent(
    translatorViewModel: TranslatorViewModel,
) {
    val clipboard = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                ),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .windowInsetsPadding(BottomAppBarDefaults.windowInsets)
                        .imePadding(),
                ) {
                    TranslatedContent(
                        translatorViewModel = translatorViewModel,
                        onCopy = {
                            scope.launch {
                                clipboard.setText(AnnotatedString(translatorViewModel.translatedText))
                                snackbarHostState.showSnackbar(AppConstants.COPIED_MESSAGE)
                            }
                        }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = translatorViewModel.text,
                onValueChange = { translatorViewModel.text = it.take(500) },
                label = { Text(AppConstants.TEXT_FIELD_LABEL) },
                shape = MaterialTheme.shapes.medium,
                supportingText = { Text("${translatorViewModel.text.length}/500") },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = translatorViewModel.text.isNotBlank(),
                    ) {
                        IconButton(
                            onClick = { translatorViewModel.text = "" },
                        ) { Icon(Icons.Default.Clear, contentDescription = "Clear text") }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(
                translatorViewModel.isTranslating,
                enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center),
            ) {
                AnimatedContent(translatorViewModel.currentTranslator) { target ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (target != null) {
                            val lottie by rememberLottieComposition(target) {
                                LottieCompositionSpec.JsonString(
                                    Res.readBytes("files/${target.lottiePath}")
                                        .decodeToString()
                                )
                            }

                            Image(
                                painter = rememberLottiePainter(
                                    composition = lottie,
                                    iterations = Compottie.IterateForever
                                ),
                                null,
                                modifier = Modifier.size(200.dp)
                            )
                        }

                        Text(
                            "Translating to ${target.toString()}...",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

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
    val isEnabled = translatorViewModel.text.isNotBlank() && translatorViewModel.currentTranslator != null
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppConstants.DEFAULT_PADDING)
        ) {
            var showTranslatorDialog by remember { mutableStateOf(false) }

            Card(
                onClick = { showTranslatorDialog = true },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.weight(.75f)
            ) {
                Text(
                    text = translatorViewModel.chosenTranslator,
                    modifier = Modifier.padding(AppConstants.DEFAULT_PADDING)
                )
            }

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

            Card(
                onClick = { translatorViewModel.text = translatorViewModel.translatedText.take(500) },
                enabled = isEnabled,
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        .copy(alpha = 0.38f)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                ),
                modifier = Modifier.weight(.25f)
            ) {
                Icon(
                    Icons.Default.SwapVert,
                    contentDescription = "Use translation as input",
                    modifier = Modifier
                        .padding(AppConstants.DEFAULT_PADDING)
                        .align(Alignment.CenterHorizontally)
                )
            }

            ShareButton(
                translatedText = translatorViewModel::translatedText,
                enabled = isEnabled,
                modifier = Modifier.weight(.25f)
            )

            Card(
                onClick = onCopy,
                enabled = isEnabled,
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                        .copy(alpha = 0.38f)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                ),
                modifier = Modifier.weight(.25f)
            ) {
                Icon(
                    Icons.Default.CopyAll,
                    contentDescription = "Copy translation",
                    modifier = Modifier
                        .padding(AppConstants.DEFAULT_PADDING)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        AnimatedVisibility(isEnabled && !translatorViewModel.isTranslating) {
            Text(
                translatorViewModel.translatedText,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(horizontal = AppConstants.DEFAULT_PADDING)
                    .padding(bottom = AppConstants.DEFAULT_PADDING)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(AppConstants.DEFAULT_PADDING)
                    .fillMaxWidth()
            )
        }
    }
}
