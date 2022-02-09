package com.synthia.mousehunt

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.synthia.mousehunt.databinding.FragmentRatholeBinding
import java.util.*

class RatHoleFragment : Fragment() {
    private lateinit var ratHoleFragment: FragmentRatholeBinding
    private lateinit var ratHoleList: MutableList<RatHole>
    private lateinit var answers: MutableList<Int>
    private lateinit var ratHolesAdapter: RatHolesAdapter
    private lateinit var level: Level
    private var holes: Int? = null
    private lateinit var hole: RatHole
    private lateinit var points: Points

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        level = RatHoleFragmentArgs.fromBundle(requireArguments()).level
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ratHoleFragment = FragmentRatholeBinding.inflate(layoutInflater)
        ratHoleList = mutableListOf()
        answers = mutableListOf()
        points= Points()
        points.setPoints(0)
      points.setUserId(3)
        ratHolesAdapter = RatHolesAdapter(requireActivity(), ratHoleList, object : ClickListener {
            override fun onClick(view: View?, ratHole: RatHole?) {
                hole = ratHole!!
                populateAnswers()
            }
        })
        ratHoleFragment.ratHoles.adapter = ratHolesAdapter
        setUpHoles()
        return ratHoleFragment.root

    }

    private fun setUpHoles() {

        if (level.number == 1) {
            holes = 2
        } else {
            holes = level.number
        }
        for (i in 0 until holes!!) {
            val ratHole = RatHole(i + 1, false)
            ratHoleList.add(ratHole)
            ratHolesAdapter.notifyDataSetChanged()
        }
    }

    private fun generateRandomNumbers(): Int {
        return Random().nextInt(holes!!)
    }

    private fun validateRandomNumbers(): Int {
        var randomNumbers = 0
        while (randomNumbers == 0) {
            randomNumbers = generateRandomNumbers()
        }
        return randomNumbers
    }

    private fun populateAnswers() {
        answers.clear()
        if (level.number > 5) {
            for (i in 0 until (level.number / 2).toInt()) {
                answers.add(validateRandomNumbers())
            }
        } else {
            answers.add(validateRandomNumbers())
        }
        checkAnswer()
    }

    private fun checkAnswer() {
        val message: String = if (answers.contains(hole.id)) {
            "you got it right"
        } else {
            "oups!"
        }
        MaterialAlertDialogBuilder(requireActivity()).setMessage(message).create().show()


    }
}