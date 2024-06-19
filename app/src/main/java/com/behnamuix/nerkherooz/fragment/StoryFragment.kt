package com.behnamuix.nerkherooz.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.databinding.FragmentStoryBinding

class StoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryBinding
    var story_progress = 0

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentStoryBinding.inflate(inflater)
        binding.tvPart.setText("0/4")
        binding.btnPhone.setOnClickListener {

            findNavController().navigate(R.id.action_storyFragment2_to_phoneFragment)

        }
        startChatAnimation1()
        story_progress+=1
        binding.tvPart.setText("$story_progress/4")

        binding.pbStory.progress=story_progress
        binding.tvSkip.alpha = 1f
        binding.tvSkip.setOnClickListener {
            story_progress++
            when (story_progress) {
                2 -> startChatAnimation2()
                3 -> startChatAnimation3()
                4 -> startChatAnimation4()


            }

        }


        return binding.root
    }
    private fun startChatAnimation1(){
        val animator1 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 3000
            startDelay = 2000
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
        binding.pbStory.progress=story_progress
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
        binding.pbStory.progress=story_progress
        binding.tvPart.setText("$story_progress/4")


    }

    private fun startChatAnimation4() {
        val animator4 = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 2000
            startDelay = 800
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.constraintLayout8.alpha = progress
            }
        }
        animator4.start()
        binding.pbStory.progress=story_progress
        binding.tvPart.setText("$story_progress/4")


    }


}


