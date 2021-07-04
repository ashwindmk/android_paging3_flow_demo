package com.ashwin.android.paging3flowdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashwin.android.paging3flowdemo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dogAdapter: DogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = dogAdapter.withLoadStateHeaderAndFooter(
                    header = LoadAdapter({ dogAdapter.retry() }),
                    footer = LoadAdapter({ dogAdapter.retry() })
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.getAllDogs().collectLatest {
                binding.apply {
                    recyclerview.isVisible = true
                    progressBar.isVisible = false
                }

                dogAdapter.submitData(it)
            }
        }
    }
}
