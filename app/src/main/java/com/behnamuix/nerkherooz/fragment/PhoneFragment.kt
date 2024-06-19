package com.behnamuix.nerkherooz.fragment

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentPhoneBinding
import com.behnamuix.nerkherooz.databinding.FragmentStoryBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhoneBinding.inflate(inflater)
        showSnack(getString(R.string.note_phone))
        val context = requireContext()

        binding.btnPhone.setOnClickListener() {
            if (binding.etPhone.text.isEmpty()) {
                binding.etPhone.setError("این فیلد نمیتواند خالی باشد")
            }
            if (!isNetworkConnected()) {
                showSnack("اتصال اینترنت شما برقرا نیست لطفا بعد چک کردن اتصال دوباره امتجان کنید")
            } else {
                val bundle=Bundle()
                val phone=binding.etPhone.text.toString().trim()
                bundle.putString("phoneNo",phone)
                sendToPhoneNumber(phone)



                findNavController().navigate(R.id.action_phoneFragment_to_verifyCodeFragment,bundle)
            }


        }






        return binding.root
    }

    private fun sendToPhoneNumber(phone:String) {
        generateCode()
    }

    private fun generateCode(): String {
        return "f"
    }


    fun showSnack(str: String) {
        var snack = Snackbar.make(binding.frameMain, str, Snackbar.LENGTH_LONG)

        snack.setBackgroundTint(Color.BLACK)
        snack.setTextColor(Color.YELLOW)
        snack.animationMode = ANIMATION_MODE_SLIDE
        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snack.show()
    }

    fun isNetworkConnected(): Boolean {
        var state = false
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var nc = cm.activeNetwork ?: return false
            var an = cm.getNetworkCapabilities(nc) ?: return false
            state = when {
                an.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                an.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                an.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val ni = cm.activeNetworkInfo
            state = when (ni?.type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
        return state
    }

}