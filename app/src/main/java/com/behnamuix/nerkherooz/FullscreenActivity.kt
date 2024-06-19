package com.behnamuix.nerkherooz

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.behnamuix.nerkherooz.databinding.ActivityFullscreenBinding


class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showProgress()
    }

    private fun startAnim() {
        val rotateAnimation = ObjectAnimator.ofFloat(binding.imgLogo, "rotation", 0f, 360f)
        rotateAnimation.duration = 1000

        rotateAnimation.interpolator = AccelerateInterpolator()
        rotateAnimation.repeatMode = ValueAnimator.RESTART

        val animatorSet = AnimatorSet()
        animatorSet.play(rotateAnimation)


        animatorSet.start()
    }

    private fun showProgress() {
        // Random()
        Thread {
            for (i in 0..100 step 10) {
                Thread.sleep(800)
                runOnUiThread {
                    binding.tvProg.text = "%$i"
                    binding.pbSplash.progress = i
                    if (i >= 100) {
                        finish()
                        // انجام فعالیت بعدی
                        startActivity(Intent(this, SplashActivity::class.java))
                    }
                }
            }
        }.start()
    }


}