package com.example.cocktailsreciepesv2.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailsreciepesv2.R
import com.example.cocktailsreciepesv2.databinding.FragmentDrinkListBinding
import com.example.cocktailsreciepesv2.domain.model.DrinkListElement
import com.example.cocktailsreciepesv2.presentation.adapter.DrinkListAdapter
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DrinkListFragment : Fragment(), SearchView.OnQueryTextListener{

    //region Properties
    private val drinkListViewModel: DrinkListViewModel by viewModel()
    private var _binding: FragmentDrinkListBinding? = null
    private lateinit var drinkListAdapter: DrinkListAdapter
    private val binding get() = _binding!!
    //endregion

    //region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDrinkListBinding.inflate(inflater, container, false)

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(binding.menuToolbarDrinkList)
        appCompatActivity.title = getString(R.string.app_name)
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            drinkListViewModel.search(query)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drink_list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupOutputsViewModel()

        drinkListAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putInt("drinkId", it)
            }
            findNavController().navigate(
                R.id.action_drinkListFragment_to_drinkInfoFragment,
                bundle
            )
        }

        binding.swipeRefresh.setOnRefreshListener {
            drinkListViewModel.updateDrinks()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    //endregion

    //region ViewModel
    private fun setupOutputsViewModel() {
        drinkListViewModel.drinkListLiveData.observe(viewLifecycleOwner) {
            viewDrinkList(it)
        }

        drinkListViewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_LONG).show()
        }

        drinkListViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvCocktailsList.visibility = View.GONE
            } else {
                binding.rvCocktailsList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    //endregion

    //region UI
    private fun initRecyclerView() {
        binding.rvCocktailsList.layoutManager = LinearLayoutManager(context)
        drinkListAdapter = DrinkListAdapter(arrayListOf())
        binding.rvCocktailsList.adapter = drinkListAdapter
    }

    private fun viewDrinkList(drinkList: List<DrinkListElement>) {
        retrieveList(drinkList)
    }

    private fun retrieveList(drinkList: List<DrinkListElement>) {
        drinkListAdapter.apply {
            setList(drinkList)
            notifyDataSetChanged()
        }
        binding.swipeRefresh.isRefreshing = false
    }
    //endregion

}