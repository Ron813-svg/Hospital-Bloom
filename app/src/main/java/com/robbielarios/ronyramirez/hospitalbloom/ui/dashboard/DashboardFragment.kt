package com.robbielarios.ronyramirez.hospitalbloom.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robbielarios.ronyramirez.hospitalbloom.databinding.FragmentDashboardBinding
import RecyclerViewHelpers.Adaptador
import viewModel.SharedViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rcvPacientes: RecyclerView = binding.rcvPacientes
        rcvPacientes.layoutManager = LinearLayoutManager(requireContext())

        sharedViewModel.pacientes.observe(viewLifecycleOwner) { datosPacientes ->
            val adaptador = Adaptador(datosPacientes)
            rcvPacientes.adapter = adaptador
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
