package uz.texnopos.texnoposedufinance.ui.main.course.add

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.android.viewmodel.ext.android.viewModel
import uz.texnopos.texnoposedufinance.R
import uz.texnopos.texnoposedufinance.core.BaseFragment
import uz.texnopos.texnoposedufinance.core.ResourceState
import uz.texnopos.texnoposedufinance.core.extentions.enabled
import uz.texnopos.texnoposedufinance.core.extentions.onClick
import uz.texnopos.texnoposedufinance.core.extentions.visibility
import uz.texnopos.texnoposedufinance.databinding.ActionBar2Binding

import uz.texnopos.texnoposedufinance.databinding.FragmentAddCoursesBinding

class AddCoursesFragment : BaseFragment(R.layout.fragment_add_courses){

    private val viewModel: AddCoursesViewModel by viewModel()
    lateinit var binding: FragmentAddCoursesBinding
    lateinit var bindingActBar: ActionBar2Binding
    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAddCoursesBinding.bind(view)
        bindingActBar = ActionBar2Binding.bind(view)
        bindingActBar.actionBarTitle.text = view.context.getString(R.string.addCourse)

        navController = Navigation.findNavController(view)
        setUpObserversCourse()

        bindingActBar.btnHome.onClick {
            navController.popBackStack()
        }
        bindingActBar.apply {
            binding.apply {
                btnSave.onClick {
                    if (!name.text.isNullOrEmpty() &&
                        !price.text.isNullOrEmpty() && !duration.text.isNullOrEmpty()
                    ) {
                        val name = name.text.toString()
                        val price: Double = price.text.toString().toDouble()
                        val period = duration.text.toString().toInt()
                        viewModel.createCourse(name, period, price).toString()
                        clear()
                    } else {
                        if (name.text.isNullOrEmpty()) name.error =
                            view.context.getString(R.string.fillField)
                        if (price.text.isNullOrEmpty()) price.error =
                            view.context.getString(R.string.fillField)
                        if (duration.text.isNullOrEmpty()) duration.error =
                            view.context.getString(R.string.fillField)
                    }
                }
            }
        }

    }

    private fun setUpObserversCourse() {
        binding.apply{
            viewModel.createCourse.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.LOADING -> {
                        loading.visibility(true)
                    }

                    ResourceState.SUCCESS -> {
                        loading.visibility(false)
                        toastLNCenter("Доваблен новый курс")
                        navController.popBackStack()
                    }
                    ResourceState.ERROR -> {
                        loading.visibility(false)
                        toastLN(it.message)
                        btnSave.enabled(true)

                    }
                }
            })
        }
    }
    private fun clear(){
        binding.apply {
            name.text!!.isEmpty()
            price.text!!.isEmpty()
            duration.text!!.isEmpty()
            btnSave.enabled(false)
        }
    }
}
