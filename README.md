  Memory Keeper — Android Notes App

Memory Keeper is a modern Android notes application built with Kotlin, Jetpack Compose, and Material 3.  
It allows users to create, edit, search, and manage notes with a clean and responsive UI.

---

##  Features

- Create new notes
- Edit existing notes
- Delete notes with confirmation dialog
- Search notes by text
- Display last edited timestamp
- Save notes locally using Room
- Empty state UI when no notes exist
- Clean and simple Material 3 UI

---

##  Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- ViewModel + StateFlow
- Room (local database)

---

##  Architecture

This project follows a simple layered architecture:

UI (Compose)
↓
ViewModel
↓
Repository
↓
Room Database

### Layers

- **UI Layer**
  - Built with Jetpack Compose
  - Displays notes and handles user interactions

- **ViewModel Layer**
  - Manages UI state using StateFlow
  - Handles business logic for notes

- **Repository Layer**
  - Acts as a bridge between ViewModel and data sources

- **Local Data Layer**
  - Uses Room database to persist notes

---

##  Screenshots


---

##  Getting Started

```bash
git clone https://github.com/shawnkitagawa/memory-keeper-notes-app.git


