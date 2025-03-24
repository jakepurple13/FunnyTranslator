# Funny Translator

A multiplatform application that translates text into various funny styles. Whether you want to sound like Shakespeare,
a pirate, or even a cat, Funny Translator has got you covered!

## Features

- **Multiple Translation Styles**: Choose from a variety of fun translation styles
- **Copy to Clipboard**: Easily copy translated text
- **Share Functionality**: Share your translations with friends
- **Cross-Platform**: Works on Android, iOS, Desktop, and Web
- **Modern UI**: Built with Material 3 design

## Available Translators

- **Uwuify**: Transforms text into cute UwU speak
- **Pirate**: Translates text into pirate speak, arr!
- **LeetSpeak**: C0nv3r75 73x7 1n70 l337 5p34k
- **Groot**: I am Groot! (Everything becomes "I am Groot")
- **Morse Code**: Translates text into dots and dashes
- **Cat Translator**: Meow meow meow!
- **Dog Translator**: Woof woof!
- **Minionese**: Translates text into Minion language
- **Shakespeare**: Transforms text into Shakespearean English
- **Valley Girl**: Like, totally transforms your text into valley girl speak!

## Supported Platforms

- **Android**: Run as a native Android app
- **iOS**: Run as a native iOS app
- **Desktop**: Run as a native desktop application (Windows, macOS, Linux)
- **Web**: Run in a web browser using WebAssembly

## How to Build and Run

### Prerequisites

- JDK 11 or newer
- Android Studio or IntelliJ IDEA
- Xcode (for iOS builds)
- Gradle

### Android

```bash
./gradlew :composeApp:assembleDebug
```

### iOS

```bash
./gradlew :composeApp:iosDeployIPhone # Deploy to connected iPhone
# or
./gradlew :composeApp:iosDeployIPad # Deploy to connected iPad
```

### Desktop

```bash
./gradlew :composeApp:run
```

### Web

```bash
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

## How to Use

1. Enter the text you want to translate in the input field
2. Select a translator style from the dropdown menu
3. View the translated text in the output area
4. Copy the translated text to clipboard or share it using the provided buttons
5. Optionally, you can use the "Up" button to replace your input with the translated text for further translation

## Technologies Used

- **Kotlin Multiplatform**: For sharing code across platforms
- **Compose Multiplatform**: For building the UI
- **Material 3**: For modern design components
- **Kotlin Coroutines**: For asynchronous programming
- **Kotlin Serialization**: For JSON serialization/deserialization

## Project Structure

- `/composeApp`: Contains the shared code for all platforms
  - `commonMain`: Code shared across all platforms
  - `androidMain`: Android-specific code
  - `iosMain`: iOS-specific code
  - `desktopMain`: Desktop-specific code
  - `wasmJsMain`: Web-specific code
- `/iosApp`: iOS application entry point

## License

This project is open source and available under the [MIT License](LICENSE).