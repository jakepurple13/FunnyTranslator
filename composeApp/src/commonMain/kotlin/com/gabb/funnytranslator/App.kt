package com.gabb.funnytranslator

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
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
    const val TEXT_FIELD_LABEL = "Enter Text"
    const val COPIED_MESSAGE = "Copied to clipboard"
    const val NO_TRANSLATOR_SELECTED = "No translator selected"
    const val NO_TRANSLATION = "No translation"
    const val TRANSLATION_ERROR_PREFIX = "Translation error: "
    const val TRANSLATING_TO = "Translating to "
    const val MAX_TEXT_LENGTH = 500
    const val ANIMATION_DURATION = 500
    const val CORNER_RADIUS = 16
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
    ) { TranslatorContent(translatorViewModel = translatorViewModel) }
}

/**
 * Main content of the translator app.
 * This is the primary UI container that organizes all the app's components:
 * - Input text field with character limit and clear button
 * - Animation showing the current translator when translating
 * - Bottom section with translator selection and translated output
 * - Platform indicator at the bottom
 *
 * The UI adapts to different states (translating, empty input, etc.) with animations
 * and provides a cohesive user experience across all platforms.
 *
 * @param translatorViewModel ViewModel that manages the translator state and handles translation logic
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
                    topStart = AppConstants.CORNER_RADIUS.dp,
                    topEnd = AppConstants.CORNER_RADIUS.dp,
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
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = translatorViewModel.text,
                onValueChange = translatorViewModel::setTextToTranslate,
                label = { Text(AppConstants.TEXT_FIELD_LABEL) },
                shape = MaterialTheme.shapes.medium,
                supportingText = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("${translatorViewModel.text.length}/${AppConstants.MAX_TEXT_LENGTH}")

                        // Display the current platform name at the bottom of the screen
                        // This helps identify which platform the app is running on
                        // (Android, iOS, Desktop, etc.)
                        Text(getPlatform().name)
                    }
                },
                trailingIcon = {
                    AnimatedVisibility(
                        visible = translatorViewModel.text.isNotBlank(),
                        enter = fadeIn() + expandIn(expandFrom = Alignment.CenterStart),
                        exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.CenterStart),
                    ) {
                        IconButton(
                            onClick = { translatorViewModel.setTextToTranslate("") },
                        ) { Icon(Icons.Default.Clear, contentDescription = "Clear input text") }
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
                AnimatedContent(
                    translatorViewModel.currentTranslator
                ) { target ->
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
                            "${AppConstants.TRANSLATING_TO}${target.toString()}...",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            val isEnabled by remember(
                translatorViewModel.text,
                translatorViewModel.currentTranslator,
                translatorViewModel.isTranslating
            ) {
                derivedStateOf {
                    translatorViewModel.text.isNotBlank() &&
                            translatorViewModel.currentTranslator != null &&
                            !translatorViewModel.isTranslating
                }
            }

            AnimatedVisibility(isEnabled) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(AppConstants.DEFAULT_PADDING)
                    )

                    Text(
                        "Translated text: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = OutlinedTextFieldDefaults.colors()
                            .focusedLabelColor,//MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.padding(horizontal = AppConstants.DEFAULT_PADDING)
                    )

                    SelectionContainer {
                        Text(
                            translatorViewModel.translatedText,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(horizontal = AppConstants.DEFAULT_PADDING)
                                .fillMaxWidth()
                        )
                    }
                }
            }

        }
    }
}

/**
 * Displays the translated text and provides controls for selecting translators and interacting with the translation.
 * This composable includes:
 * - A dropdown to select the translator
 * - A button to use the translation as input (swap)
 * - A button to share the translation (platform-specific)
 * - A button to copy the translation to clipboard
 * - The translated text display area
 *
 * @param translatorViewModel ViewModel that manages the translator state and translation logic
 * @param onCopy Callback for when the user wants to copy the translated text to clipboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TranslatedContent(
    translatorViewModel: TranslatorViewModel,
    onCopy: () -> Unit,
) {
    val isEnabled by remember(
        translatorViewModel.text,
        translatorViewModel.currentTranslator,
        translatorViewModel.isTranslating
    ) {
        derivedStateOf {
            translatorViewModel.text.isNotBlank() &&
                    translatorViewModel.currentTranslator != null &&
                    !translatorViewModel.isTranslating
        }
    }

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
                // Display the currently selected translator or a default message
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
                                },
                                shape = RectangleShape
                            ) {
                                ListItem(
                                    headlineContent = { Text(text = translator.toString()) },
                                    leadingContent = {
                                        Icon(
                                            imageVector = translator.getIcon(),
                                            contentDescription = "$translator translator icon"
                                        )
                                    },
                                    trailingContent = {
                                        if (translator == translatorViewModel.currentTranslator) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Currently selected translator"
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

            ActionButton(
                enabled = isEnabled,
                onClick = { translatorViewModel.setTextToTranslate(translatorViewModel.translatedText) },
                imageVector = Icons.Default.SwapVert,
                contentDescription = "Use translation as input",
                modifier = Modifier.weight(.25f)
            )

            ShareButton(
                translatedText = translatorViewModel::translatedText,
                enabled = isEnabled,
                modifier = Modifier.weight(.25f)
            )

            ActionButton(
                enabled = isEnabled,
                onClick = onCopy,
                imageVector = Icons.Default.CopyAll,
                contentDescription = "Copy translation",
                modifier = Modifier.weight(.25f)
            )
        }

        /*AnimatedVisibility(isEnabled) {
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
        }*/
    }
}

/**
 * A reusable button component with an icon that performs an action when clicked.
 * The button is styled as a card with the app's secondary container color.
 *
 * @param enabled Whether the button is enabled and can be clicked
 * @param imageVector The icon to display in the button
 * @param contentDescription Accessibility description for the icon
 * @param modifier Modifier to be applied to the button
 * @param onClick Callback to be invoked when the button is clicked
 */
@Composable
fun ActionButton(
    enabled: Boolean,
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer
                .copy(alpha = 0.38f)
                .compositeOver(MaterialTheme.colorScheme.surface),
        ),
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(AppConstants.DEFAULT_PADDING)
                .align(Alignment.CenterHorizontally)
        )
    }
}