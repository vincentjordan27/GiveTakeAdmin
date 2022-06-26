package com.vincent.givetakeadmin.ui.fragment.advice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vincent.givetakeadmin.R
import com.vincent.givetakeadmin.databinding.FragmentAdviceBinding
import com.vincent.givetakeadmin.factory.AdviceRepoViewModelFactory
import com.vincent.givetakeadmin.utils.Result

class AdviceFragment : Fragment() {

    private var _binding: FragmentAdviceBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdviceViewModel
    private lateinit var adviceListAdapter: AdviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdviceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = AdviceRepoViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[AdviceViewModel::class.java]
        adviceListAdapter = AdviceListAdapter()

        setObserver()

        viewModel.getAllAdvice()

        binding.rv.adapter = adviceListAdapter
        binding.rv.layoutManager = LinearLayoutManager(requireActivity())
        binding.rv.setHasFixedSize(true)
    }

    private fun setObserver() {
        viewModel.allAdviceResult.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    if (it.data?.data?.isEmpty()!!) {
                        binding.rv.visibility = View.GONE
                        binding.txtNoData.visibility = View.VISIBLE
                    } else {
                        binding.rv.visibility = View.VISIBLE
                        binding.txtNoData.visibility = View.GONE
                        adviceListAdapter.setData(it.data.data)
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                    Snackbar.make(binding.txtError, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllAdvice()
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