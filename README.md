# TV Broadcast Ops & Scheduling Suite

A comprehensive production operations and playlist verification suite designed for television broadcasting networks (**TV3, 8TV, TV9, NTV7, and DS**). This repository contains two synchronized tools:

1. 📱 **Checklist Ops (Android App)** — Modern native Android app built with Kotlin, Jetpack Compose, Material 3, and Room local persistence for shift operators to verify daily playlist parameters and master control operations.
2. 🌐 **TV Scheduling Checklist (Web App)** — Standalone responsive browser application for TV production planning, scheduling programmes, and tracking 6-step pre-production checklists with calendar and list views.

---

## 📱 1. Checklist Ops (Native Android App)

### Overview
**Checklist Ops** is engineered for broadcast shift operators and supervisors to review transmission items, check waktu solat, station IDs, commercial breaks, and technical parameter settings across 5 Malaysian television networks:
- **TV3**
- **8TV**
- **TV9**
- **NTV7**
- **DS**

### Key Features
- **Channel Navigation Tabs**: Seamlessly switch between networks with real-time reactive filtering.
- **Dynamic Quick Status Filter Pills**: Displays live item counts for `All`, `Completed` (`CHECKED`), `In review` (`REVIEW`), `Needs attention` (`ALERT`), and `Not started` (`PENDING`).
- **Interactive Status Cycling**: Tap the status pill on any card to cycle states directly (`Completed` → `In review` → `Needs attention` → `Not started`).
- **Structured Parameter Setting Detail View**:
  - Converts channel-specific parameter strings into a clean, spreadsheet-style bordered list.
  - Separate rows for **Programme / Condition** and **Required Setting**.
  - Distinct **Operational Note** badges for instructions without arrows.
  - Visual status color-coding:
    - 🌸 **Light Rose** (`#FFF1F2`): Standard programme-setting rules.
    - 🔷 **Light Blue** (`#EFF6FF`): Rules containing `NO SETTING`.
    - 🟡 **Light Amber** (`#FFFBEB`): Rules containing `LOGO ONLY`.
    - ⚪ **Light Grey** (`#F8FAFC`): Operational shift notes.
  - **Sticky Header**: Channel badge, title, and current status remain pinned at the top while parameter rows scroll.
- **Calendar Date Picker Modal**: Select and navigate schedule dates with month navigation (`<`, `>`), "Today" jumper, and selection confirmations.
- **Activity & Compliance Audit Log**: Real-time chronological audit trail of all operator modifications with timestamps.
- **Channels Overview**: Multi-channel progress bars and batch completion verification.
- **Offline Persistence**: Powered by Android **Room Database** (`SQLite`) with automated migration and pre-seeded network data.

### Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Database**: Room Database (KSP) + Kotlin Coroutines & Flow
- **Unit Testing**: Robolectric JVM unit test suite

---

## 🌐 2. TV Scheduling Checklist (Web App)

### Overview
A zero-dependency, single-file web application designed for TV production teams, executive producers, and line producers to plan daily programmes and track pre-broadcast readiness tasks.

### File Location
- **Root**: `index.html`
- **Web directory**: `web/index.html`

### Key Features
- **Dashboard Overview**:
  - Live metric cards: Total Scheduled Programmes, In Progress, Ready / Approved, On Air (pulsing live indicator), and Overall Checklist Completion Rate %.
- **Dual Visual Modes**:
  - **List View**: Chronological cards showing time slots, channel badge, programme title, PIC, live status switcher, interactive 6-step checklist, and progress bar.
  - **Calendar View**: Interactive month grid with day counts and scheduled programme breakdown per date.
- **Interactive 6-Step Checklist**:
  1. Script completed
  2. Talent confirmed
  3. Studio booked
  4. Video / assets ready
  5. Approved
  6. Published / On Air
- **Multi-Dimensional Filters**:
  - Search by programme title, notes, or PIC.
  - Filter by Channel tabs (`All`, `TV3`, `8TV`, `TV9`, `NTV7`, `DS`).
  - Filter by Date, Person in Charge (PIC), and Status (`Draft`, `In Progress`, `Ready`, `On Air`, `Completed`).
- **Data Persistence**:
  - All additions, edits, deletions, and checkbox states automatically persist in browser `localStorage`.
  - Includes realistic pre-seeded broadcast data and a "Reload Sample Data" reset button.
- **Design & Responsiveness**:
  - Dark navy and blue production aesthetic (`#0B1728`, `#10213A`, `#1E3A5F`, `#0284C7`).
  - Fully responsive across desktop, tablet, and mobile devices.

---

## 🚀 Getting Started

### Running the Web Application
No installation, Node.js server, or external build tool is required:
1. Open `index.html` (or `web/index.html`) directly in any modern browser (Chrome, Edge, Safari, Firefox).
2. Start planning programmes, toggling checklist items, and filtering by channel.

### Running the Android Application
1. Open this repository in **Android Studio** (Koala or newer recommended).
2. Allow Gradle to sync the dependencies configured in `build.gradle.kts` and `gradle/libs.versions.toml`.
3. Select an Android emulator (API 34+ / API 35+) or connect a physical Android device with USB debugging enabled.
4. Click **Run** (`Shift + F10`) to build and launch `Checklist Ops`.

### Running Android Unit Tests
To execute the automated Robolectric test suite:
```bash
gradle :app:testDebugUnitTest
```

---

## 📂 Project Structure

```text
├── index.html                           # Standalone TV Scheduling Checklist Web App
├── web/
│   ├── index.html                       # Companion copy of the Web App
│   └── README.md                        # Web App quick start guide
├── metadata.json                        # Google AI Studio platform metadata
├── build.gradle.kts                     # Root Gradle build configuration
├── settings.gradle.kts                  # Project settings & dependency resolution
├── app/
│   ├── build.gradle.kts                 # Android app build configuration & dependencies
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml      # Android app manifest & adaptive launcher icon config
│   │   │   ├── java/com/example/
│   │   │   │   ├── MainActivity.kt      # Main entry point & ViewModel injection
│   │   │   │   ├── data/
│   │   │   │   │   ├── db/              # Room database & DAO definitions
│   │   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   │   └── ChecklistDao.kt
│   │   │   │   │   ├── model/           # Entity models & ItemStatus enum
│   │   │   │   │   │   └── ChecklistItem.kt
│   │   │   │   │   └── repository/      # Repository & default broadcast seed data
│   │   │   │   │       ├── ChecklistRepository.kt
│   │   │   │   │       └── DefaultChecklistData.kt
│   │   │   │   └── ui/
│   │   │   │       ├── components/      # Modular UI components
│   │   │   │       │   ├── AppBottomBar.kt
│   │   │   │       │   ├── CalendarDatePickerDialog.kt
│   │   │   │       │   ├── ChannelTabsBar.kt
│   │   │   │       │   ├── ChecklistFilterBar.kt
│   │   │   │       │   ├── ChecklistHeader.kt
│   │   │   │       │   ├── ChecklistItemCard.kt
│   │   │   │       │   ├── ItemDetailDrawer.kt    # Structured spreadsheet parameter view
│   │   │   │       │   └── ScheduleDateCard.kt
│   │   │   │       ├── screens/         # Screen composables
│   │   │   │       │   ├── ActivityScreen.kt
│   │   │   │       │   ├── ChannelsScreen.kt
│   │   │   │       │   ├── ChecklistScreen.kt
│   │   │   │       │   ├── HistoryScreen.kt
│   │   │   │       │   └── MainScreen.kt
│   │   │   │       ├── theme/           # Color palettes, typography & Material 3 theme
│   │   │   │       └── viewmodel/       # ChecklistViewModel & Filter state flows
│   │   │   └── res/                     # Vector drawables & string resources
│   │   └── test/java/com/example/
│   │       └── ExampleRobolectricTest.kt # Robolectric unit tests for UI & parsing logic
└── README.md                            # Main project documentation (this file)
```

---

## 🔒 Security & Privacy
- **Zero External Tracking**: Both the Android and Web apps run completely client-side without collecting telemetry or transmitting personal data.
- **Local Persistence Only**: All data is stored in the local SQLite database (Android) or browser `localStorage` (Web).
- **No API Keys Required**: No third-party API keys or external services are needed to run either application.

---

## 📄 License
This project is open-source and intended for broadcast operations and television production management.
