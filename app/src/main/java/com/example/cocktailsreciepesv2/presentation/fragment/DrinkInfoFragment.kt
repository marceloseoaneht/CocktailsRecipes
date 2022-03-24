package com.example.cocktailsreciepesv2.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.databinding.FragmentDrinkInfoBinding
import com.example.cocktailsreciepesv2.domain.model.DrinkInfo
import com.example.cocktailsreciepesv2.presentation.adapter.DrinkIngredientAdapter
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrinkInfoFragment : Fragment() {

    //region Properties
    private val drinkInfoViewModel: DrinkInfoViewModel by viewModel()
    private var drinkId: Int? = null
    private var _binding: FragmentDrinkInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var ingredientsAdapter: DrinkIngredientAdapter
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //to place menu items in to menu
        setHasOptionsMenu(true)
        drinkId = arguments?.getInt("drinkId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDrinkInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOutputsViewModel()
        drinkInfoViewModel.getDrinkInfo(drinkId!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //overrides the back arrow behaviour
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region ViewModel
    private fun setupOutputsViewModel() {
        drinkInfoViewModel.drinkInfo.observe(viewLifecycleOwner) {
            showDrinkInfo(it)
        }

        drinkInfoViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        drinkInfoViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar2.visibility = View.VISIBLE
                binding.cvDrinkInfo.visibility = View.GONE
            } else {
                binding.progressBar2.visibility = View.GONE
                binding.cvDrinkInfo.visibility = View.VISIBLE
            }
        }
    }
    //endregion

    //region UI
    private fun showDrinkInfo(drinkInfo: DrinkInfo) {
        binding.ivCocktailImageDetail.load(drinkInfo.image) {
            placeholder(R.drawable.list_cocktail_image_placeholder)
            error(R.drawable.list_cocktail_image_placeholder)
            transformations(RoundedCornersTransformation(4F))
        }

        binding.rvIngredientsList.layoutManager = LinearLayoutManager(context)
        ingredientsAdapter = DrinkIngredientAdapter(drinkInfo.ingredients)
        binding.rvIngredientsList.adapter = ingredientsAdapter

        binding.tvInstructionsDetail.text = drinkInfo.instructions

        //back arrow setup
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.menuToolbarDrinkInfo)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.title = drinkInfo.name
    }
    //endregion

}