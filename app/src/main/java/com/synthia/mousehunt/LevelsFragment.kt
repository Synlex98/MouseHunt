package com.synthia.mousehunt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.synthia.mousehunt.databinding.FragmentLevelsBinding


class LevelsFragment : Fragment() {
    private lateinit var levelsFragment:FragmentLevelsBinding
private lateinit var levelList: MutableList<Level>
private lateinit var levelsAdapter: LevelsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        levelsFragment= FragmentLevelsBinding.inflate(layoutInflater)
        levelList= mutableListOf()
        levelsAdapter= LevelsAdapter(requireActivity(), levelList,object :ClickListener{
            override fun onClick(view: View?, level: Level?) {
                findNavController().navigate(LevelsFragmentDirections.actionLevelsFragmentToRatHoleFragment(level!!))
            }
        })
        levelsFragment.levels.adapter=levelsAdapter
        setUpLevels()
        return levelsFragment.root
    }

    private fun setUpLevels() {
        for (i in 0 until 20){
            val level=Level(i+1,R.drawable.bg_normal_level)
            levelList.add(level)
            levelsAdapter.notifyDataSetChanged()
        }

    }


}