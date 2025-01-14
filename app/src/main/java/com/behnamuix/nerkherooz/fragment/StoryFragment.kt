package com.behnamuix.nerkherooz.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentStoryBinding


class StoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryBinding
    var story_progress = 0
    var storySeen: Boolean = false

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sharedPreferences =
            requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE)

        binding = FragmentStoryBinding.inflate(inflater)
        val value = sharedPreferences.getString("storyseen", "false")
        if (value.toBoolean()) {
            findNavController().navigate(R.id.action_storyFragment2_to_homeFragment)
        } else {

            if (!isNetworkConnected()) {
                binding.tvAlert.visibility = View.VISIBLE

            } else {
                binding.tvAlert.visibility = View.GONE

            }
            alertAnimation()
            binding.imgCloseAlert.setOnClickListener() {
                binding.tvAlert.visibility = View.GONE


            }
            binding.tvPart.setText("0/4")
            binding.btnPhone1.setOnClickListener {
                findNavController().navigate(R.id.action_storyFragment2_to_phoneFragment)
            }
            startChatAnimation1()
            story_progress += 1
            if (story_progress == 4) {

            }
            binding.tvPart.setText("$story_progress/4")

            binding.pbStory.progress = story_progress
            binding.tvSkip.alpha = 1f
            binding.tvSkip.setOnClickListener {
                story_progress++
                when (story_progress) {
                    2 -> startChatAnimation2()
                    3 -> startChatAnimation3()
                    4 -> startChatAnimation4()


                }
                if (story_progress > 4) {
                    Toast.makeText(activity, "داستان تمام شد", Toast.LENGTH_SHORT).show()
                    storySeen = true
                    sharedPreferences.edit().putString("storyseen", storySeen.toString()).apply()

                }

            }


          
        }
        return binding.root
    }



    private fun alertAnimation() {
        val animFadein = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.fade
        )
        animFadein.fillAfter = true
        binding.tvAlert.startAnimation(animFadein)
    }

    private fun startChatAnimation1() {
        val animator1 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            startDelay = 1000
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.constraintLayout2.alpha = progress

            }
        }
        animator1.start()
    }

    private fun startChatAnimation2() {
        val animator2 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            startDelay = 800
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.constraintLayout3.alpha = progress
            }
        }
        animator2.start()
        binding.pbStory.progress = story_progress
        binding.tvPart.setText("$story_progress/4")


    }

    private fun startChatAnimation3() {
        val animator3 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            startDelay = 800
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.constraintLayout4.alpha = progress
            }
        }
        animator3.start()
        binding.pbStory.progress = story_progress
        binding.tvPart.setText("$story_progress/4")


    }

    private fun startChatAnimation4() {
        val animator4 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000
            startDelay = 800
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.constraintLayout8.alpha = progress
            }
        }
        animator4.start()
        binding.pbStory.progress = story_progress
        binding.tvPart.setText("$story_progress/4")


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


