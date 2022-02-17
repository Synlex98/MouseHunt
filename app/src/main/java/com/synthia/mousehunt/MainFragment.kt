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
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.synthia.mousehunt.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMainBinding.inflate(layoutInflater,container,false)
        binding.playView.setOnClickListener{
            val player=MediaPlayer.create(requireActivity(),R.raw.pop)
            YoYo.with(Techniques.BounceInUp)
                .duration(2000)
                .onStart {
                    binding.playView.isClickable=false
                    player.start()
                }
                .onEnd {
                   if (player.isPlaying){
                       player.stop()
                   }
                    player.reset()
                    player.release()
                    binding.playView.isClickable=true
                    findNavController().navigate(MainFragmentDirections.actionMouseHoleToLevelsFragment())
                }.playOn(it)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Utils.getInstance().userExists(requireActivity())){
val user=Utils.getInstance().getUser(requireActivity())
            getFromNear(user);
        }else{
findNavController().navigate(MainFragmentDirections.actionMouseHoleToAuthFragment())
        }
    }

    private fun getFromNear(user: User) {
binding.progress.show()
AndroidNetworking.post("http://192.168.100.115:8080/ops/user/get")
    .addBodyParameter("userId",user.get_userId().toString())
    .setPriority(Priority.HIGH)
    .build()
    .getAsObject(User::class.java,object: ParsedRequestListener<User>{
        override fun onResponse(response: User?) {
            binding.progress.hide();
            binding.userIdView.text="UserID : ${response!!._userId}"
            binding.levelView.text="Current Level : ${response._level._levelNumber}"
            binding.pointsView.text="Points : ${response._points._points}"
        }

        override fun onError(anError: ANError?) {
            binding.progress.hide();
            anError?.printStackTrace()
            Toast.makeText(requireActivity(),"Unable to retrieve user",Toast.LENGTH_LONG).show()
        }
    })
    }

}