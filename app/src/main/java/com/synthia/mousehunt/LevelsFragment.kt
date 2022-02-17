package com.synthia.mousehunt

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.synthia.mousehunt.databinding.FragmentLevelsBinding


class LevelsFragment : Fragment() {
    private lateinit var binding:FragmentLevelsBinding
private lateinit var levelList: MutableList<Level>
private lateinit var levelsAdapter: LevelsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player=MediaPlayer.create(requireActivity(),R.raw.sprinkle)
        player.start()
        player.setOnCompletionListener {
            player.reset()
            player.release()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentLevelsBinding.inflate(layoutInflater)
        levelList= mutableListOf()
        levelsAdapter= LevelsAdapter(requireActivity(), levelList,object :ClickListener{
            override fun onClick(view: View?, level: Level?) {
                val user=Utils.getInstance().getUser(requireActivity())
                if (user._level._levelNumber<level!!._levelNumber){
                    if (user._points._points<(level._levelNumber*100)){
                        MaterialAlertDialogBuilder(requireActivity()).setMessage("Get ${(level._levelNumber*100)} points to qualify for the next level")
                            .setTitle("OOPS!")
                            .create()
                            .show()
                    }
                    YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .playOn(view)
                    return
                }
                view!!.isClickable=false
                val player=MediaPlayer.create(requireActivity(),R.raw.pop)
                player.start()
                player.setOnCompletionListener {
                    player.reset()
                    player.release()
                    findNavController().navigate(LevelsFragmentDirections.actionLevelsFragmentToRatHoleFragment(level!!))
view.isClickable=true
                    upDateLevel(level._levelNumber)
                }
            }
        })
        binding.levels.adapter=levelsAdapter
        setUpLevels()
        return binding.root
    }

    private fun setUpLevels() {
        val user=Utils.getInstance().getUser(requireActivity())
        for (i in 0 until 20){
            val level=Level(i+1,R.drawable.bg_normal_level,user._userId)
            levelList.add(level)
            levelsAdapter.notifyDataSetChanged()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
getCurrentLevel(Utils.getInstance().getUser(requireActivity()).get_userId().toString())
    }

    private fun getCurrentLevel(userId:String) {
        binding.progress.show()
        AndroidNetworking.post("http://192.168.100.115:8080/ops/levels/get")
            .addBodyParameter("userId",userId)
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(Level::class.java,object: ParsedRequestListener<Level> {
                override fun onResponse(response:Level) {
                    binding.progress.hide();
                    Utils.CURRENT_LEVEL=response._levelNumber
                }

                override fun onError(anError: ANError?) {
                    binding.progress.hide();
                    anError?.printStackTrace()
                    Toast.makeText(requireActivity(),"Unable to get user level", Toast.LENGTH_LONG).show()
               requireActivity().onBackPressed()
                }
            })
    }

    private fun upDateLevel(level: Int) {
        AndroidNetworking.post("http://192.168.100.115:8080/ops/level/change")
            .addBodyParameter("level",level.toString())
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