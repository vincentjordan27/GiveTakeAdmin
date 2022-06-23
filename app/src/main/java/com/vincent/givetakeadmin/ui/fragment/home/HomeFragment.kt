package com.vincent.givetakeadmin.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.databinding.FragmentHomeBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.utils.Result

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var rewardAdapter: RewardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setObserver()

        rewardAdapter = RewardAdapter()
        viewModel.getAllRewards()

        binding.rv.adapter = rewardAdapter
        binding.rv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rv.setHasFixedSize(true)

        binding.btnAdd.setOnClickListener {
            Toast.makeText(context, "Add Reward", Toast.LENGTH_SHORT).show()

        }
    }

    private fun setObserver() {
        viewModel.allRewardsResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    if (it.data?.data?.size!! <= 0) {
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.rv.visibility = View.GONE
                    }else {
                        binding.tvNoData.visibility = View.GONE
                        binding.rv.visibility = View.VISIBLE
                        rewardAdapter.setData(it.data.data)
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.rv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pg.visibility = View.VISIBLE
        } else {
            binding.pg.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}