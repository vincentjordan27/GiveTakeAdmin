package com.vincent.givetakeadmin.ui.fragment.reward

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.givetakeadmin.databinding.FragmentRewardBinding
import com.vincent.givetakeadmin.factory.RewardRepoViewModelFactory
import com.vincent.givetakeadmin.utils.Result


class RewardFragment : Fragment() {

    private var _binding: FragmentRewardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RewardRequestViewModel
    private lateinit var rewardRequestListAdapter: RewardRequestListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRewardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = RewardRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[RewardRequestViewModel::class.java]
        rewardRequestListAdapter = RewardRequestListAdapter()

        setObserver()
        viewModel.getAllRewardsRequest()

        binding.rv.adapter = rewardRequestListAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.setHasFixedSize(true)
    }

    private fun setObserver() {
        viewModel.allRewardRequestResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    if (it.data?.data?.isEmpty()!!) {
                        showData(false)
                    } else {
                        showData(true)
                        rewardRequestListAdapter.setData(it.data.data)
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showData(isHaveData: Boolean) {
        if (isHaveData) {
            binding.rv.visibility = View.VISIBLE
            binding.txtNoData.visibility = View.GONE
        } else {
            binding.rv.visibility = View.GONE
            binding.txtNoData.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pg.visibility = View.VISIBLE
        } else {
            binding.pg.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllRewardsRequest()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}