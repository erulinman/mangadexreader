package info.erulinman.mangadexreader.mangalist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import info.erulinman.mangadexreader.App
import info.erulinman.mangadexreader.MainActivity
import info.erulinman.mangadexreader.R
import info.erulinman.mangadexreader.databinding.FragmentMangaListBinding
import info.erulinman.mangadexreader.utils.DataState
import javax.inject.Inject

class MangaListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: Lazy<MangaListViewModel.Factory>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get()).get(MangaListViewModel::class.java)
    }

    private var _adapter: MangaListAdapter? = null
    private val adapter: MangaListAdapter get() {
        checkNotNull(_adapter)
        return _adapter as MangaListAdapter
    }

    private var _binding: FragmentMangaListBinding? = null
    private val binding: FragmentMangaListBinding get() {
        checkNotNull(_binding)
        return _binding as FragmentMangaListBinding
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _adapter = MangaListAdapter()
        _binding = FragmentMangaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.setOnClickListener {
            val title = binding.editMangaTitle.text.toString()
            viewModel.fetchManga(title)
            (requireActivity() as MainActivity).closeKeyboard(binding.root)
        }
        binding.recyclerView.adapter = adapter
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _adapter = null
        _binding = null
    }

    private fun observeViewModel() = viewModel.apply {
        dataState.observe(viewLifecycleOwner) { dataState ->
            if (dataState == null) return@observe
            when (dataState) {
                is DataState.Empty -> binding.apply {
                    progressBar.isVisible = false
                    message.setText(R.string.tv_empty_list_message)
                    message.isVisible = true
                    recyclerView.isVisible = false
                }
                is DataState.Loading -> binding.apply {
                    progressBar.isVisible = true
                    message.isVisible = false
                }
                is DataState.Error -> binding.apply {
                    progressBar.isVisible = false
                    message.text = dataState.msg
                    message.isVisible = true
                    recyclerView.isVisible = false
                }
                is DataState.Loaded -> binding.apply {
                    progressBar.isVisible = false
                    message.isVisible = false
                    recyclerView.isVisible = true
                    adapter.submitList(dataState.data)
                }
            }
        }

    }
}