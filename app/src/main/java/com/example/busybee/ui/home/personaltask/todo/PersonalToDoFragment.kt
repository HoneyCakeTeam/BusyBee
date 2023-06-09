package com.example.busybee.ui.home.personaltask.todo

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
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

class PersonalToDoFragment : BaseFragment<FragmentPersonalToDoBinding>(),
    PersonalToDoView,
    PersonalToDoAdapter.PersonalToDoTaskInteractionListener {
    private lateinit var adapter: PersonalToDoAdapter
    private lateinit var todos: List<PersonalToDo>
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
        PersonalToDoPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this, validator
        )
    }
    override val TAG = this::class.java.simpleName.toString()

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

            presenter.validatePersonalTodo(title, description)
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
        showLoading()
        presenter.personalCreateToDo(
            title, description
        )
    }

    override fun onSuccessResponse(response: BaseResponse<PersonalToDo>) {
        activity?.runOnUiThread {
            hideLoading()
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

    private fun hideLoading() {
        binding.lottieLoading.visibility = View.GONE
        binding.recyclerToDo.visibility = View.VISIBLE
    }
    private fun showLoading() {
        binding.lottieLoading.visibility = View.VISIBLE
        binding.recyclerToDo.visibility = View.GONE
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

    override fun showValidationError(titleErrorMessage: String?, descriptionErrorMessage: String?) {
        with(sheetCreateTaskBinding) {
            inputLayoutTaskName.error = titleErrorMessage
            inputLayoutContent.error = descriptionErrorMessage
        }
    }

    override fun hideValidationErrorThenCreatePersonalTodo(title: String, description: String) {
        hideError()
        personalCreateToDo(title, description)
    }

    private fun hideError() {
        with(sheetCreateTaskBinding) {
            inputLayoutTaskName.isErrorEnabled = false
            inputLayoutContent.isErrorEnabled = false
        }
    }

    override fun onTasKClicked(flag: TaskType, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}
