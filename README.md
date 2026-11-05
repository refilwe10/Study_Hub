Study Hub 

Group Members

Name Student Number
Refilwe Phore ST10458818
Ntembeko Ndayi ST10180342

Study Hub is an Android study and productivity application designed to help students organise their academic work, learning materials, and study sessions in one place.

1. Purpose of the Application

The purpose of Study Hub is to combine academic planning, learning, and productivity features into one simple Android application.

The application was designed based on the research completed in Part 1 of the assessment. The main ideas were influenced by existing study applications such as MyStudyLife, Quizlet, and Forest. Study Hub combines these ideas into one application rather than requiring students to use several separate applications.

2. Main Features & Part 3 Innovations

Study Hub includes the following core features:

· User registration and login (Secure authentication via Supabase)
· Timetable management and Exam Planner
· Assignment Manager
· Flashcards and Quiz creation
· Practice quizzes and Quiz scoring
· Focus Timer and Study session recording

Innovative Features Added in Part 3:

· Real-time Push Notifications: Implemented using Firebase Cloud Messaging (FCM) to send alerts to the user's device when a study session is completed or an assignment deadline is approaching.
· Multi-language Support: The application UI natively supports English and isiZulu (or [insert your 2nd language here]), allowing users to switch languages in the Settings screen.
· Offline Mode & Sync: Implemented local caching (via RoomDB/SharedPreferences) so users can add flashcards and notes without internet access. The app automatically synchronizes with the Supabase REST API once the connection is restored.

3. Technology Used

· Language: Kotlin
· UI Framework: Jetpack Compose, Material 3
· Backend/Database: Supabase, Supabase Auth, PostgreSQL (Cloud), RoomDB (Local)
· Networking: Supabase PostgREST REST API
· Push Notifications: Firebase Cloud Messaging (FCM)
· Testing & CI/CD: JUnit, GitHub, GitHub Actions
· IDE: Android Studio

4. Application Design & User Interface

The application uses Jetpack Compose to create a modern, responsive user interface. The design focuses on keeping the application simple, user-friendly, and easy to navigate. The Home screen provides access to the main study features.

The app uses ViewModels to separate application logic from the UI, keeping database operations and authentication logic organised. Extensive input validation is included to prevent invalid information from being submitted and to ensure the app handles user errors gracefully without crashing.

5. Authentication and Security

Study Hub uses Supabase Auth for user registration and login. Passwords are encrypted and not stored directly by the application; authentication and password storage are handled securely by Supabase.

The application communicates with Supabase over the network using a publishable client key. Private database passwords and service-role keys are not included in the application source code. The project also uses Row Level Security (RLS) policies so that authenticated users can only access their own data.

6. REST API and Database

Study Hub uses the Supabase PostgREST API to communicate with the hosted PostgreSQL database. The application stores information such as timetable entries, assignments, exams, flashcards, quiz questions, and completed study sessions. Each user's records are associated with their authenticated user ID.

7. GitHub and Version Control

The project is maintained in a GitHub repository. Git was used throughout development to keep track of changes. The repository contains the full application source code, automated test suites, the CI/CD GitHub Actions workflow, and is tagged as Final_PoE for final submission.

8. Running the Application Locally

To run the project:

1. Clone the repository from GitHub.
2. Open the project in Android Studio.
3. Allow Gradle to synchronise.
4. Configure the required Supabase connection properties in local.properties (URL and publishable key).
5. Connect a physical Android device or start an emulator.
6. Run the application.

9. Demonstration Video

Below is the link to the demonstration video showcasing the Study Hub prototype running on a physical Android device. The video includes a voice-over explaining the REST API, database, authentication, and the new Part 3 features.

Video Link: [Insert your YouTube or Google Drive video link here]

10. Play Store Preparation

(Add a screenshot of your app's home screen here)

The application has been prepared for final publication, including a custom app icon, final image assets, and a user-friendly interface.

11. Project Structure

The project is organised into clean architectural layers using Kotlin files for screens and ViewModels. Examples include:

· AuthViewModel.kt, TimetableViewModel.kt, AssignmentViewModel.kt
· ExamViewModel.kt, FlashcardViewModel.kt, QuizViewModel.kt
· StudySessionViewModel.kt, SettingsScreen.kt

12. Conclusion

Study Hub provides a single Android application for managing academic planning, learning, and study productivity. The prototype demonstrates secure authentication, REST API communication, offline sync, push notifications, automated testing, and CI/CD pipeline integration. The application was developed iteratively and tested on a physical device throughout the process.
