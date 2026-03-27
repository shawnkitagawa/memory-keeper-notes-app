# Memory Keeper — Android Productivity App

Memory Keeper is an Android productivity application built with Kotlin, Jetpack Compose, and Supabase.
The app allows users to manage daily tasks and routines with a structured workflow and a clean, responsive interface.

---

## Features

* User authentication using Supabase (login and signup)
* Create, update, and delete tasks
* Categorize tasks (work, personal, health)
* Separate views for daily routines and today’s tasks
* Track progress based on completed tasks
* Real-time data persistence with Supabase
* Proper handling of loading, success, and error states

---

## Tech Stack

* Kotlin
* Jetpack Compose
* Material 3
* ViewModel and StateFlow
* Supabase (authentication and database)
* Kotlin coroutines

---

## Architecture

The project follows an MVVM architecture with a clear separation of concerns:

UI (Compose)
→ ViewModel (state management)
→ Repository (data handling)
→ Supabase backend

---

##  Screenshots
<img width="427" height="958" alt="image" src="https://github.com/user-attachments/assets/772d37a3-0c10-4238-8da4-06daa19f36a5" />
<img width="424" height="955" alt="image" src="https://github.com/user-attachments/assets/f2c8b429-36aa-4260-8cbe-16ed770fc5e6" />
<img width="430" height="960" alt="image" src="https://github.com/user-attachments/assets/7cfcf1aa-5792-4d5d-b869-641c9c07d93d" />
<img width="426" height="954" alt="image" src="https://github.com/user-attachments/assets/441d6709-bff7-4ec9-bd24-ef80fdc085fa" />
<img width="435" height="943" alt="image" src="https://github.com/user-attachments/assets/958a3d83-8ece-4bf9-89ad-0fa187931f24" />

---


## Getting Started

```bash
git clone https://github.com/shawnkitagawa/memory-keeper-notes-app.git
```

---

## Notes

This project focuses on handling asynchronous data flows, managing UI state with StateFlow, and integrating a backend service for authentication and persistence. Particular attention was given to resolving race conditions in authentication and ensuring consistent navigation behavior.

---

## Future Improvements

* Offline support and local caching
* Push notifications
* Improved multi-device synchronization
* Extended analytics or insights for task completion




