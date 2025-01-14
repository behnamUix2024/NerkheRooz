package com.behnamuix.nerkherooz.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentPhoneBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random


class PhoneFragment : Fragment() {
    private lateinit var binding: FragmentPhoneBinding
    private val URL = "https://www.behnamuix2024.com/api/sendsms.php?"
    val name=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhoneBinding.inflate(inflater)
        showSnack(getString(R.string.note_phone))
        binding.etPhone.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val phoneN = p0
                    validPhoneNumber(phoneN)
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {}


            }
        )

        binding.btnPhone.setOnClickListener() {

            if (!isNetworkConnected()) {
                showSnack("اتصال اینترنت شما برقرار نیست لطفا بعد چک کردن اتصال دوباره امتجان کنید")
            } else {
                if (!binding.etPhone.text.isEmpty()) {

                    val phone = binding.etPhone.text.toString()
                    val name = binding.editTextText.text.toString()
                    //add this lib in gradle(project):
                    // id "androidx.navigation.safeargs.kotlin" version "2.5.1" apply false
                    val code = Random.nextInt(1000, 10000)
                    var txt = " نرخ روز$code"
                    Log.d("alpha",code.toString())
                    sendCodeToPhoneNumber(phone, txt)
                    addTodb(name,phone)
                    val sharedPreferences =
                        requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("name", name).apply()
                    editor.putString("phone", phone).apply()
                    findNavController().navigate(R.id.action_phoneFragment_to_verifyCodeFragment)


                } else {
                    binding.etPhone.setError("این فیلد نمیتواند خالی باشد")
                }

            }


        }


        return binding.root
    }


    fun sendCodeToPhoneNumber(phone: String, text: String) {
        val queue = Volley.newRequestQueue(activity?.baseContext)


        val stringRequest = object : StringRequest(Method.POST,
            "https://www.behnamuix2024.com/api/sendsms.php?phone=$phone&text=$text",
            Response.Listener<String> { response ->
                // Handle the response
                Log.d("test", response)


            },
            Response.ErrorListener { error ->
                // Handle the error
                Log.e("test", error.toString())

            }) {

        }
        queue.add(stringRequest)


    }

    fun showSnack(str: String) {
        var snack = Snackbar.make(binding.frameMain, str, Snackbar.LENGTH_SHORT)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.setBackgroundTint(resources.getColor(R.color.teal_700))
        snack.setTextColor(Color.WHITE)

        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snack.show()
    }

    private fun validPhoneNumber(phoneN: CharSequence?) {
        if (phoneN?.length!! < 11 || !phoneN.startsWith("09")) {
            binding.etPhone.setError("شماره تلفن اشتباه است")
        } else {

        }
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


    private fun addTodb(name:String,phone: String) {
        var queue2 = Volley.newRequestQueue(context)

        var url = "https://behnamuix2024.com/api/register.php?name=$name"+"&"+"phone=$phone"
        Log.d("debug", url)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                Log.d("debug", "Response=>$response")

            },
            { Log.d("debug", "error!")  })
        queue2.add(stringRequest)
    }
}