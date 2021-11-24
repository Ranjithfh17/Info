package com.fh.info.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fh.info.R
import com.fh.info.databinding.FragmentDirectoriesBinding
import com.fh.info.utils.FileUtil
import com.fh.info.utils.TextViewUtils.makeLinks


class Directories : Fragment(R.layout.fragment_directories) {

    private lateinit var binding: FragmentDirectoriesBinding
    private val args by navArgs<DirectoriesArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDirectoriesBinding.bind(view)

        binding.apk.text = args.applicationInfo.applicationInfo.sourceDir
        binding.data.text = args.applicationInfo.applicationInfo.dataDir


        binding.data.makeLinks(Pair(binding.data.text.toString(), View.OnClickListener {
            kotlin.runCatching {
                FileUtil.openFile(requireContext(), binding.data.text.toString())

            }.getOrElse {
                it.printStackTrace()
            }
        }))


    }

}