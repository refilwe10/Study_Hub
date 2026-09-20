package com.example.study_hub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.study_hub.ui.theme.Study_HubTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Study_HubTheme {
                StudyHubApp()
            }
        }
    }
}

@Composable
fun StudyHubApp() {

    val authViewModel: AuthViewModel = viewModel()
    val timetableViewModel: TimetableViewModel = viewModel()
    val assignmentViewModel: AssignmentViewModel = viewModel()
    val examViewModel: ExamViewModel = viewModel()
    val flashcardViewModel: FlashcardViewModel = viewModel()
    val quizViewModel: QuizViewModel = viewModel()
    val studySessionViewModel: StudySessionViewModel = viewModel()

    var showRegister by remember { mutableStateOf(false) }
    var showHome by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var showTimetable by remember { mutableStateOf(false) }
    var showAssignments by remember { mutableStateOf(false) }
    var showExams by remember { mutableStateOf(false) }
    var showFlashcards by remember { mutableStateOf(false) }
    var showQuiz by remember { mutableStateOf(false) }
    var showTakeQuiz by remember { mutableStateOf(false) }
    var showFocusTimer by remember { mutableStateOf(false) }

    when {

        showTakeQuiz -> {
            TakeQuizScreen(
                viewModel = quizViewModel,
                onBack = {
                    showTakeQuiz = false
                }
            )
        }

        showQuiz -> {
            QuizScreen(
                viewModel = quizViewModel,
                onBack = {
                    showQuiz = false
                }
            )
        }

        showFlashcards -> {
            FlashcardsScreen(
                viewModel = flashcardViewModel,
                onBack = {
                    showFlashcards = false
                }
            )
        }

        showAssignments -> {
            AssignmentScreen(
                viewModel = assignmentViewModel,
                onBack = {
                    showAssignments = false
                }
            )
        }

        showExams -> {
            ExamScreen(
                viewModel = examViewModel,
                onBack = {
                    showExams = false
                }
            )
        }

        showTimetable -> {
            TimetableScreen(
                viewModel = timetableViewModel,
                onBack = {
                    showTimetable = false
                }
            )
        }

        showFocusTimer -> {
            FocusTimerWithSessionsScreen(
                viewModel = studySessionViewModel,
                onBack = {
                    showFocusTimer = false
                }
            )
        }

        showSettings -> {
            SettingsScreen(
                onBack = {
                    showSettings = false
                }
            )
        }

        showRegister -> {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    showRegister = false
                    showHome = true
                },
                onBack = {
                    showRegister = false
                }
            )
        }

        showHome -> {
            HomeScreen(
                onTimetable = {
                    showTimetable = true
                },
                onAssignments = {
                    showAssignments = true
                },
                onExams = {
                    showExams = true
                },
                onFlashcards = {
                    showFlashcards = true
                },
                onQuiz = {
                    showQuiz = true
                },
                onTakeQuiz = {
                    showTakeQuiz = true
                },
                onFocusTimer = {
                    showFocusTimer = true
                },
                onSettings = {
                    showSettings = true
                },
                onLogout = {
                    authViewModel.logout {
                        showHome = false
                    }
                }
            )
        }

        else -> {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    showHome = true
                },
                onRegister = {
                    showRegister = true
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val message by viewModel.message.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Study Hub",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Login to your account"
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        Button(
            onClick = {
                viewModel.login(
                    email = email,
                    password = password,
                    onSuccess = onLoginSuccess
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account")
        }
    }
}

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    val message by viewModel.message.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Full Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            label = {
                Text("Confirm Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (message.isNotEmpty()) {
            Text(
                text = message,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        Button(
            onClick = {
                viewModel.register(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    onSuccess = onRegisterSuccess
                )
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isLoading) {
                    "Creating Account..."
                } else {
                    "Register"
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBack,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Composable
fun HomeScreen(
    onTimetable: () -> Unit,
    onAssignments: () -> Unit,
    onExams: () -> Unit,
    onFlashcards: () -> Unit,
    onQuiz: () -> Unit,
    onTakeQuiz: () -> Unit,
    onFocusTimer: () -> Unit,
    onSettings: () -> Unit,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Study Hub",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Plan. Learn. Focus."
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = onTimetable,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Timetable")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onAssignments,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Assignment Manager")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onExams,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Exam Planner")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onFlashcards,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Flashcards")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Quiz")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onTakeQuiz,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Practice Quiz")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onFocusTimer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Focus Timer")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Settings")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}