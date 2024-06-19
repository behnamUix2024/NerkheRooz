package com.behnamuix.nerkherooz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentVerifyCodeBinding
import com.goodiebag.pinview.Pinview


class VerifyCodeFragment : Fragment() {
    private lateinit var binding:FragmentVerifyCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentVerifyCodeBinding.inflate(inflater)

        binding.pinview.currentFocus
        binding.pinview.setPinViewEventListener(object :Pinview.PinViewEventListener{
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                if(fromUser){
                    if (pinview != null) {
                        Toast.makeText(context,pinview.value,Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
        return binding.root
    }


}