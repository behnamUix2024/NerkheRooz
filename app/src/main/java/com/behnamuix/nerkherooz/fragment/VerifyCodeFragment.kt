package com.behnamuix.nerkherooz.fragment


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentVerifyCodeBinding
import com.goodiebag.pinview.Pinview
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class VerifyCodeFragment : Fragment() {
    private lateinit var binding: FragmentVerifyCodeBinding
    private lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentVerifyCodeBinding.inflate(inflater)

        timerPin()

        binding.btnVerify.setOnClickListener() {
            if(binding.pinview.value.isEmpty()){
                showSnack("کاربر گرامی این فیلد نمیتواند خالی باشد")
                binding.pinview.currentFocus
            }else{
                checkVerify(binding.pinview.value)

            }



        }
        binding.pinview.currentFocus
        binding.pinview.setPinViewEventListener(object : Pinview.PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {
                if (fromUser) {
                    if (pinview != null) {
                        Toast.makeText(context, pinview.value, Toast.LENGTH_LONG).show()
                    }
                }
            }

        })

        return binding.root
    }



    fun showSnack(str: String) {
        var snack = Snackbar.make(binding.frameMain, str, Snackbar.LENGTH_LONG)

        snack.setBackgroundTint(Color.BLACK)
        snack.setTextColor(Color.YELLOW)
        snack.animationMode = BaseTransientBottomBar.ANIMATION_MODE_SLIDE
        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snack.show()
    }

    private fun checkVerify(code: String) {

        var pin = binding.pinview.value
        Log.d("test", "code:" + code + "/" + "pin:" + pin)
        if (pin == code) {
            showSnack("Ok,success")

            findNavController().navigate(R.id.action_verifyCodeFragment_to_homeFragment)

        } else {
            showSnack("Oh,error")

        }
    }




    private fun timerPin() {
        binding.tvTimer.visibility = View.VISIBLE
        timer = MyCountDownTimer(70000, 1000)
        timer.start()
    }

    inner class MyCountDownTimer(millisInMillis: Long, countDownInterval: Long) :
        CountDownTimer(millisInMillis, countDownInterval) {
        override fun onTick(millisUntilFinishid: Long) {
            val min = millisUntilFinishid / 60000
            val sec = (millisUntilFinishid % 60000) / 1000
            binding.tvTimer.text = "  مدت زمان باقی مانده تا ارسال مجدد کد: \n $min:$sec"

        }

        override fun onFinish() {


        }

    }




}