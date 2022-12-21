package com.excample.noteapp.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.excample.noteapp.R
import com.excample.noteapp.adapters.OnBoardViewPagerAdapter
import com.excample.noteapp.databinding.FragmentOnBoardBinding
import com.excample.noteapp.utils.PreferenceHelper
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }
        initialize()
        onPage()
        setupListener()
        openSignUp()
    }

    private fun initialize() {
        binding.viewPager.adapter = OnBoardViewPagerAdapter(this@OnBoardFragment)
        PreferenceHelper.unit(requireContext())
    }

    private fun setupListener() = with(binding.viewPager) {
        binding.homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_signUpFragment)
        }
        binding.nextButton.setOnClickListener {
            if (currentItem < 2) {
                setCurrentItem(currentItem + 1, true)
            }
        }
    }

    private fun onPage() = with(binding) {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        nextButton.isVisible = true
                        homeButton.isVisible = false
                    }
                    1 -> {
                        nextButton.isVisible = true
                        homeButton.isVisible = false
                    }
                    2 -> {
                        nextButton.isVisible = false
                        homeButton.isVisible = true
                    }
                }
                super.onPageSelected(position)
            }

        })
    }

    private fun openSignUp() {
        PreferenceHelper.name = true
    }
}