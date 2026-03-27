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
<img width="427" height="958" alt="image" src="https://github.com/user-attachments/assets/772d37a3-0c10-4238-8da4-06daa19f36a5" />
<img width="424" height="955" alt="image" src="https://github.com/user-attachments/assets/f2c8b429-36aa-4260-8cbe-16ed770fc5e6" />
<img width="430" height="960" alt="image" src="https://github.com/user-attachments/assets/7cfcf1aa-5792-4d5d-b869-641c9c07d93d" />
<img width="426" height="954" alt="image" src="https://github.com/user-attachments/assets/441d6709-bff7-4ec9-bd24-ef80fdc085fa" />
<img width="435" height="943" alt="image" src="https://github.com/user-attachments/assets/958a3d83-8ece-4bf9-89ad-0fa187931f24" />



---

##  Getting Started

```bash
git clone https://github.com/shawnkitagawa/memory-keeper-notes-app.git


