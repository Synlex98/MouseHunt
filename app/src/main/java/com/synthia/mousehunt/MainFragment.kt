package com.synthia.mousehunt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.synthia.mousehunt.databinding.ActivityMainBinding
import com.synthia.mousehunt.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMainBinding.inflate(layoutInflater)
        binding.play.setOnClickListener{
            findNavController().navigate(MainFragmentDirections.actionMouseHoleToLevelsFragment())

        }
        return binding.root
    }


}