package com.excample.noteapp.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.excample.noteapp.R
import com.excample.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    lateinit var binding: FragmentOnBoardPagingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                onText.text = "Очень удобный функционал"
                animationView.setAnimation(R.raw.lottie1)

            }
            1 -> {
                onText.text = "Быстрый, качественный продукт"
                animationView.setAnimation(R.raw.lottie2)
            }

            2 -> {
                onText.text = "Куча функций и интересных фишек"
                animationView.setAnimation(R.raw.lottie3)
            }


        }
    }

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}