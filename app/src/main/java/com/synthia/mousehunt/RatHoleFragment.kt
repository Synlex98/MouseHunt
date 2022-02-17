package com.synthia.mousehunt

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.synthia.mousehunt.databinding.FragmentRatholeBinding
import java.time.Year
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
    private lateinit var selectedView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        level = RatHoleFragmentArgs.fromBundle(requireArguments()).level
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        ratHoleFragment = FragmentRatholeBinding.inflate(layoutInflater)
        ratHoleList = mutableListOf()
        answers = mutableListOf()
        points= Points()
        points._points=0
      points._userId=3
        ratHolesAdapter = RatHolesAdapter(requireActivity(), ratHoleList, object : ClickListener {
            override fun onClick(view: View?, ratHole: RatHole?) {
                hole = ratHole!!
                selectedView=view!!
                populateAnswers()
            }
        })
        ratHoleFragment.ratHoles.adapter = ratHolesAdapter
        setUpHoles()
        return ratHoleFragment.root

    }

    private fun setUpHoles() {

        holes = if (level._levelNumber == 1) {
            2
        } else {
            level._levelNumber
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
        if (level._levelNumber > 5) {
            for (i in 0 until (level._levelNumber / 2)) {
                answers.add(validateRandomNumbers())
            }
        } else {
            answers.add(validateRandomNumbers())
        }
        checkAnswer()
    }

    private fun checkAnswer() {
       if (answers.contains(hole.id)){
           val player=MediaPlayer.create(requireActivity(),R.raw.fireworks)
           player.start()
           player.setOnCompletionListener {
               player.reset()
               player.release()
           }
ratHoleFragment.konfettiView.start(Presets.festive())

           val points=if (hole.isMain()){
10 }else{
               5
           }
           Toast.makeText(requireActivity(),"Congratulations! You got $points",Toast.LENGTH_LONG).show()
           addUserPoints(points)
       }else{
           Toast.makeText(requireActivity(),"OOPS! Try Again",Toast.LENGTH_LONG).show()
           YoYo.with(Techniques.Shake)
               .duration(1000)
               .playOn(selectedView)
       }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    private fun addUserPoints(points: Int) {
        AndroidNetworking.post("http://192.168.100.115:8080/ops/points/change")
            .addBodyParameter("points",points.toString())
            .addBodyParameter("userId",Utils.getInstance().getUser(requireActivity()).get_userId().toString())
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object: StringRequestListener {
                override fun onResponse(response:String) {

                    }

                override fun onError(anError: ANError?) {
                    anError?.printStackTrace()
                    Toast.makeText(requireActivity(),"Unable to update your points",Toast.LENGTH_LONG).show()
                }
            })
    }

}