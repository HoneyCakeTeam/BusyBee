package com.example.busybee.ui.home.teamtask.todo

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentTeamToDoBinding
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.ui.details.DetailsFragment
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.validator.Validator
import com.example.busybee.utils.validator.ValidatorImpl
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>(), TeamToDoView,
    TeamToDoAdapter.TeamToDoTaskInteractionListener {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var todos: List<TeamToDo>
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
    private val validator: Validator by lazy { ValidatorImpl(requireContext()) }
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val presenter by lazy {
        TeamToDoPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this, validator
        )
    }

    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getToDos()
        addCallBacks()
        adapter = TeamToDoAdapter(todos, this)
        binding.recyclerToDo.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.to_do)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, todos.size)
        setToDoColorBasedOnTheme()
        showPlaceHolder(todos)
    }

    private fun showPlaceHolder(toDo: List<TeamToDo>) {
        if (toDo.isEmpty()) {
            binding.textNoTasksTeamToDo.visibility = View.VISIBLE
            binding.recyclerToDo.visibility = View.GONE
            binding.imagePlaceholderTeamToDo.visibility = View.VISIBLE
        } else {
            binding.textNoTasksTeamToDo.visibility = View.GONE
            binding.recyclerToDo.visibility = View.VISIBLE
            binding.imagePlaceholderTeamToDo.visibility = View.GONE
        }
    }

    private fun setToDoColorBasedOnTheme() {
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
            }

            UiModeManager.MODE_NIGHT_YES -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_todo_dark)
            }

            else -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
            }
        }
    }

    private fun addCallBacks() {
        binding.buttonAddNewTeamTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        bottomSheet = BottomSheetDialog(
            requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )
        sheetCreateTaskBinding = BottomSheetCreateTaskBinding.inflate(layoutInflater)

        sheetCreateTaskBinding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        sheetCreateTaskBinding.buttonCreateTask.setOnClickListener {
            val title = sheetCreateTaskBinding.textTaskName.text.toString()
            val description = sheetCreateTaskBinding.textContent.text.toString()
            val assign = sheetCreateTaskBinding.textAssignee.text.toString()
            presenter.validateTeamTodo(title, description, assign)
        }
        bottomSheet.setContentView(sheetCreateTaskBinding.root)
        bottomSheet.show()
    }

    private fun getToDos() {
        presenter.getLocalTeamTodos()
    }

    override fun createTeamToDo(title: String, description: String, assignee: String) {
        showLoading()
        presenter.teamCreateToDo(
            title, description, assignee
        )
    }

    override fun addNewToDo(response: TeamToDo) {
        activity?.runOnUiThread {
            hideLoading()
            setListAndUpdateUi(response)
            hideFieldsAndShowDone()
            showPlaceHolder(todos)
        }
    }

    private fun hideFieldsAndShowDone() {
        with(sheetCreateTaskBinding) {
            buttonCreateTask.text = getString(R.string.ok)
            textCreateTask.visibility = View.GONE
            inputLayoutAssignee.visibility = View.GONE
            inputLayoutContent.visibility = View.GONE
            inputLayoutTaskName.visibility = View.GONE
            textCreatedSuccessfully.visibility = View.VISIBLE
            buttonCreateTask.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
        sheetCreateTaskBinding.lottieCreatedSuccessfully.visibility = View.VISIBLE
    }

    private fun setListAndUpdateUi(response: TeamToDo) {
        presenter.addTeamToDo(response)
        getToDos()
        adapter.setItems(todos)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, todos.size)
    }

    override fun showErrorMsg(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                "Try Again! ${error.message} ",
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }
    private fun hideLoading() {
        binding.lottieLoading.visibility = View.GONE
        binding.recyclerToDo.visibility = View.VISIBLE
    }
    private fun showLoading() {
        binding.lottieLoading.visibility = View.VISIBLE
        binding.recyclerToDo.visibility = View.GONE
    }

    override fun getLocalTeamTodos(todos: List<TeamToDo>) {
        this.todos = todos
    }

    override fun showValidationError(
        titleErrorMessage: String?,
        descriptionErrorMessage: String?,
        assigneeErrorMessage: String?
    ) {
        with(sheetCreateTaskBinding) {
            inputLayoutTaskName.error = titleErrorMessage
            inputLayoutContent.error = descriptionErrorMessage
            inputLayoutAssignee.error = assigneeErrorMessage
        }
    }

    override fun hideValidationErrorThenCreateTeamTodo(
        title: String,
        description: String,
        assignee: String
    ) {
        hideError()
        createTeamToDo(title, description, assignee)
    }

    private fun hideError() {
        with(sheetCreateTaskBinding) {
            inputLayoutTaskName.isErrorEnabled = false
            inputLayoutContent.isErrorEnabled = false
            inputLayoutAssignee.isErrorEnabled = false
        }
    }

    override fun onTasKClicked(flag: TaskType, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}