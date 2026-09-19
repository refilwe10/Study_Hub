package com.example.study_hub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    val id: String? = null,
    val user_id: String,
    val title: String,
    val subject: String,
    val due_date: String,
    val description: String? = null
)

class AssignmentViewModel : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _assignments =
        MutableStateFlow<List<Assignment>>(emptyList())

    val assignments: StateFlow<List<Assignment>> =
        _assignments.asStateFlow()

    fun loadAssignments() {

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            try {

                val user = supabase.auth.currentUserOrNull()

                if (user == null) {
                    _message.value = "You must be logged in."
                    return@launch
                }

                val result = supabase
                    .from("assignments")
                    .select {
                        filter {
                            eq("user_id", user.id)
                        }
                    }
                    .decodeList<Assignment>()

                _assignments.value = result

            } catch (e: Exception) {

                _message.value =
                    e.message ?: "Failed to load assignments."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun saveAssignment(
        title: String,
        subject: String,
        dueDate: String,
        description: String
    ) {

        if (title.isBlank()) {
            _message.value = "Please enter an assignment title."
            return
        }

        if (subject.isBlank()) {
            _message.value = "Please enter a subject."
            return
        }

        if (dueDate.isBlank()) {
            _message.value = "Please enter a due date."
            return
        }

        viewModelScope.launch {

            _isLoading.value = true
            _message.value = ""

            try {

                val user = supabase.auth.currentUserOrNull()

                if (user == null) {
                    _message.value = "You must be logged in."
                    return@launch
                }

                val assignment = Assignment(
                    user_id = user.id,
                    title = title,
                    subject = subject,
                    due_date = dueDate,
                    description = description.ifBlank { null }
                )

                supabase
                    .from("assignments")
                    .insert(assignment)

                _message.value =
                    "Assignment saved successfully!"

                loadAssignments()

            } catch (e: Exception) {

                _message.value =
                    e.message ?: "Failed to save assignment."

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun clearMessage() {
        _message.value = ""
    }
}