package com.behnamuix.nerkherooz

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.behnamuix.nerkherooz.databinding.ActivityFullscreenBinding
import com.behnamuix.nerkherooz.fragment.HomeFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        checkNet()


    }

    private fun checkNet() {
        if (isNetworkConnected()) {
            binding.imgRef.visibility = View.INVISIBLE
            showProgress()
        } else {
            showSnack("کاربر گرامی اینترنت شما متصل نیست!پس از متصل شدنبه اینترنت بر روی دکمه زیر بزنید")
            binding.imgRef.visibility = View.VISIBLE
            checkNet()
        }
    }

    private fun loadLogo() {
        val URl = "https://behnamuix2024.com/img/logoo.png"
        Glide
            .with(this)
            .load(URl)
            .centerCrop()
            .into(binding.imgLogo)
    }

    private fun showProgress() {
        // Random()
        Thread {
            for (i in 0..100 step 10) {
                Thread.sleep(1000)
                runOnUiThread {
                    if (i >= 100) {
                        finish()
                        val sharedPreferences =
                            getSharedPreferences("show_login", Context.MODE_PRIVATE)
                        val signupShown = sharedPreferences.getBoolean("show_login", false)
                        if (signupShown) {
                            startActivity(Intent(this, HomeFragment::class.java))
                        } else {
                            // انجام فعالیت بعدی
                            startActivity(Intent(this, SplashActivity::class.java))
                        }

                    }
                }
            }
        }.start()
    }

    fun showSnack(str: String) {
        var snack = Snackbar.make(binding.frameMainn, str, Snackbar.LENGTH_SHORT)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.setBackgroundTint(resources.getColor(R.color.teal_700))
        snack.setTextColor(Color.WHITE)

        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snack.show()
    }

    fun isNetworkConnected(): Boolean {
        var state = false
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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