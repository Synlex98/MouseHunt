package com.synthia.mousehunt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.synthia.mousehunt.databinding.ActivityMainBinding
import com.synthia.mousehunt.databinding.FragmentAuthBinding
import com.synthia.mousehunt.databinding.FragmentMainBinding


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAuthBinding.inflate(layoutInflater,container,false)
        binding.btnLogin.setOnClickListener{
           val userId=binding.userIdInput.text.toString()
            if (userId.isEmpty() || !userId.isDigitsOnly()){
                Toast.makeText(requireActivity(),"Provide a valid userId",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            loginUser(userId)

        }
        binding.btnRegister.setOnClickListener {
            registerUser()
        }
        return binding.root
    }

    private fun registerUser() {
        binding.progress.show()
        AndroidNetworking.post("http://192.168.100.115:8080/ops/user/create")
            .setPriority(Priority.HIGH)
            .build()
            .getAsObject(User::class.java,object: ParsedRequestListener<User>{
                override fun onResponse(response: User?) {
                    binding.progress.hide();
                    Utils.getInstance().saveUser(requireActivity(),response)
                    findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToMouseHole())
                    }

                override fun onError(anError: ANError?) {
                    binding.progress.hide();
                    anError?.printStackTrace()
                    Toast.makeText(requireActivity(),"Unable to create user",Toast.LENGTH_LONG).show()
                }
            })
    }


    private fun loginUser(userId:String) {
binding.progress.show()
AndroidNetworking.post("http://192.168.100.115:8080/ops/user/get")
    .addBodyParameter("userId",userId)
    .setPriority(Priority.HIGH)
    .build()
    .getAsObject(User::class.java,object: ParsedRequestListener<User>{
        override fun onResponse(response: User?) {
            binding.progress.hide();
            Utils.getInstance().saveUser(requireActivity(),response)
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToMouseHole())
        }

        override fun onError(anError: ANError?) {
            binding.progress.hide();
            anError?.printStackTrace()
            Toast.makeText(requireActivity(),"Unable to login user",Toast.LENGTH_LONG).show()
        }
    })
    }

}