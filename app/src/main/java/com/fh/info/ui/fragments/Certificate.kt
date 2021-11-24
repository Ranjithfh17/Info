package com.fh.info.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentCertificateBinding
import com.fh.info.ui.adapter.AdapterCertificate
import com.fh.info.utils.AppResource
import com.fh.info.viewmodel.InfoItemViewModel
import com.fh.info.viewmodelfactory.InfoViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect


class Certificate : Fragment(R.layout.fragment_certificate) {
    private lateinit var binding:FragmentCertificateBinding
    private lateinit var viewModel: InfoItemViewModel
    private lateinit var adapterCertificate:AdapterCertificate
    private val args by navArgs<CertificateArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCertificateBinding.bind(view)

        val infoViewModelProvider=InfoViewModelProvider(requireActivity().application,args.applicationInfo.applicationInfo)
        viewModel=ViewModelProvider(this,infoViewModelProvider).get(InfoItemViewModel::class.java)

        setUpRecyclerview()
        getCertificateInfo()
        binding.navigateBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpRecyclerview(){
        adapterCertificate= AdapterCertificate()
        binding.certificateRecyclerView.apply {
            adapter=adapterCertificate
        }
    }

    private fun getCertificateInfo(){
        lifecycleScope.launchWhenStarted {
            viewModel.certificateFlow.collect { certificate ->
               when(certificate){
                   is AppResource.Success ->{
                       adapterCertificate.differ.submitList(certificate.data)
                   }
                   is AppResource.Error ->{
                       Snackbar.make(binding.root,"Failed to get Certificate data",Snackbar.LENGTH_LONG).show()
                       delay(1000)
                       findNavController().popBackStack()
                   }
                   else->{
                       /*NO-OP*/
                   }
               }
            }
        }
    }





}