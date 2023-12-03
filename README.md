# Bitcoin Block Explorer App

Sample app to explore bitcoin blocks and transactions.

## Build & Run Locally

- Use the latest Android Studio version: **Android Studio Hedgehog | 2023.1.1**

- Use **Java 11+**, it should it should be the default on the last Androd Studio.

  If not, in Android Studio go to Settings → Build, Exe… → Build Tools → Gradle
  and make sure the Gradle JDK is version 11 or higher. 
  Click the current value to select/install the required JDK runtime if needed.

- Use/install **Android SDK Build-Tools 34** via
  Android Studio → Settings → search & click "Android SDK" → 
  SDK Tools tab.

- Tested on emulator Pixel 7 Pro API level 34

## How it works

The app pulls real-time data from the [Mempool Space](https://mempool.space/api/) API, both from REST and WebSocket endpoints.

Upon start, it opens a connection to the websocket endpoint and keeps it alive for as long  as the app's UI is retained by the Android OS.

On the Home Screen you will see a list of the most recent confirmed blocks on the upper half, while below a list of the most recent transactions.

### Functionality

- Clicking on a block card will open the block detail view, where its most recent transactions are listed.
  - Here you can click on multiple transaction cards to expand them and inspect the adresses involved, with a little more details.
  - Navigating back will return to the Home Screen.

## Architecture

### UI

UI is built with Jetpack Compose and minimal customization, some components may feel unpolished. This was a deliberate decision to focus more time on code quality.

The UI/presentation architecture follows the UDF (Unidirectional Data Flow) principles, while UI state is provided via flows exposed by ViewModels.

Navigation is handled with Compose Navigation library, and there is only one Activity as suggested by the [Modern Android App Architecture](https://developer.android.com/topic/architecture#modern-app-architecture) guidelines.

### Concurrency

(Structured) Concurrency is handled with flows and coroutines, while there isn't much multithreading involved except what comes already with OkHttp (the Http client library), it should be fairly straightforward to delegate work to the recommended dispatchers (``Dispatchers.Default` for CPU intensive work/ `Dispatchers.IO` for network calls, etc.), as long as the [Best Practices for Coroutines in Android](https://developer.android.com/codelabs/advanced-kotlin-coroutines#10) are followed.

Both flows (via `flowOn`) and coroutines (via `withContext`) allow for multithreading optimisations with minimal effort.

### Business Logic

The app omits the [domain layer](https://developer.android.com/topic/architecture/domain-layer), as permitted by the Modern Android Architecture guidelines in case of simple/POC/limited-time buget apps.

There's was a deliberate case in modeling both the data and presentation layer classes to reduce unnecessary logic, preferring straightforward and simple operations to reduce the need of unit testing if possible.

### Dependency Injection

The app uses Hilt for Dependency Injection, there is only one module for now: `DataModule` which provides dependencies related to the data layer.

## Areas of improvements

1. Implement the failing unit tests
   - `MempoolWebSocketClient` may need further optimisations to permit testability. However it may be reconsidered if some of its listed tests really make sense.
2. Run computations on the default dispatcher and io work to the IO dispatcher, first wire in dependency injection support to provide these dispatchers.
3. While OkHttp already handles HTTP caching by default, db persistence would be a great improvement.
4. Some data models are passed directly to the UI. This should not be the case.
5. There should be at least a simple scaffold Compose UI implemented, to make it feel more like an Android app.
6. WebSocket connection should be reopened if it drops unexpectedly.
7. WebSocket updates should be paused/resumed when app is in background to not drain user battery unnecessarily.
8. Raw texts in UI should be moved to resources to allow i18n
9. Bitcoin prices should be fixed (there is an potential issue I ignored fixing for now)
10. App should display USD prices (descoped)
