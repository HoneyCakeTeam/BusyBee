package com.example.busybee.ui.home.personaltask.todo.view

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.personaltask.todo.presenter.PersonalToDoPresenter
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>(),
    PersonalToDoViewInterface,
    PersonalToDoAdapter.PersonalToDoTaskInteractionListener {
    private lateinit var adapter: PersonalToDoAdapter
    private lateinit var todos: List<PersonalToDo>
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
    override val TAG = this::class.java.simpleName.toString()
    private val presenter by lazy {
        PersonalToDoPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils(requireContext())
            ), this
        )
    }

    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        presenter.getLocalPersonalTodos()
        addCallBacks()
        adapter = PersonalToDoAdapter(todos, this)
        binding.recyclerToDo.adapter = adapter
        binding.headerToDo.textTodoStatus.text = getString(R.string.to_do)
        binding.headerToDo.taskCount.text = getString(R.string.tasks, todos.size)
        setToDoColorBasedOnTheme()
        showPlaceHolder(todos)
    }

    private fun showPlaceHolder(todo: List<PersonalToDo>) {
        if (todo.isEmpty()) {
            binding.textNoTasksPersonalToDo.visibility = View.VISIBLE
            binding.recyclerToDo.visibility = View.GONE
            binding.imagePlaceholderPersonalToDo.visibility = View.VISIBLE
        } else {
            binding.textNoTasksPersonalToDo.visibility = View.GONE
            binding.recyclerToDo.visibility = View.VISIBLE
            binding.imagePlaceholderPersonalToDo.visibility = View.GONE
        }
    }

    private fun addCallBacks() {
        binding.buttonAddNewPersonalTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        sheetCreateTaskBinding = BottomSheetCreateTaskBinding.inflate(layoutInflater)
        sheetCreateTaskBinding.inputLayoutAssignee.visibility = View.GONE

        bottomSheet = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetStyle
        )

        sheetCreateTaskBinding.buttonCreateTask.setOnClickListener {
            val title = sheetCreateTaskBinding.textTaskName.text.toString()
            val description = sheetCreateTaskBinding.textContent.text.toString()

            personalCreateToDo(title, description)
        }

        sheetCreateTaskBinding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(sheetCreateTaskBinding.root)
        bottomSheet.show()
    }

    override fun getLocalPersonalTodos(todos: List<PersonalToDo>) {
        this.todos = todos
    }


    private fun personalCreateToDo(title: String, description: String) {
        presenter.personalCreateToDo(
            title, description
        )
    }

    override fun onSuccessResponse(response: BaseResponse<PersonalToDo>) {
        activity?.runOnUiThread {
            setListAndUpdateUi(response)
            hideFieldsAndShowDone()
            showPlaceHolder(todos)
        }
    }

    private fun setListAndUpdateUi(response: BaseResponse<PersonalToDo>) {
        presenter.addPersonalToDo(response.value)
        presenter.getLocalPersonalTodos()
        adapter.setItems(todos)
        binding.headerToDo.taskCount.text = getString(R.string.tasks, todos.size)
        setToDoColorBasedOnTheme()
    }

    private fun setToDoColorBasedOnTheme() {
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.headerToDo.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
            }
            UiModeManager.MODE_NIGHT_YES -> {
                binding.headerToDo.textTodoStatus.setBackgroundResource(R.drawable.shape_todo_dark)
            }
            else -> {
                binding.headerToDo.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
            }
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


    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                "Try Again! ${error.message} ",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onTasKClicked(flag: TaskType, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}
