package com.example.shahzad_afridi.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.shahzad_afridi.R
import com.example.shahzad_afridi.databinding.ActivityMainBinding
import com.example.shahzad_afridi.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TAG: String = "MainActivity"
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        binding.run.setOnClickListener {
            viewModel.onGetBlogContent()
        }
    }

    private fun observer(){

        viewModel.truecaller10thCharacter.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.textView1.text = getString(R.string.loading)
                }
                is UiState.Failure -> {
                    Log.e(TAG,state.error ?: getString(R.string.error))
                }
                is UiState.Success -> {
                    binding.textView1.text = state.data.toString()
                }
            }
        }

        viewModel.truecallerEvery10thCharacter.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.textView2.text = getString(R.string.loading)
                }
                is UiState.Failure -> {
                    Log.e(TAG,state.error ?: getString(R.string.error))
                }
                is UiState.Success -> {
                    binding.textView2.text = state.data
                }
            }
        }

        viewModel.truecallerWordCounter.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.textView3.text = getString(R.string.loading)
                }
                is UiState.Failure -> {
                    Log.e(TAG,state.error ?: getString(R.string.error))
                }
                is UiState.Success -> {
                    binding.textView3.text = state.data
                }
            }
        }

        viewModel.error.observe(this){
            Log.e(TAG,String.format("%s has exception with message %s",it.first,it.second))
        }
    }
}
