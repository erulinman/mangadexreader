package info.erulinman.mangadexreader.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import info.erulinman.mangadexreader.MDRApp
import info.erulinman.mangadexreader.MainActivity
import info.erulinman.mangadexreader.databinding.FragmentMangaListBinding
import info.erulinman.mangadexreader.ui.MangaListAdapter
import info.erulinman.mangadexreader.viewmodels.MangaListViewModel
import info.erulinman.mangadexreader.viewmodels.ViewModelFactory

class MangaListFragment : Fragment() {
    private val viewModel by viewModels<MangaListViewModel> {
        val repository = (requireContext().applicationContext as MDRApp).repository
        ViewModelFactory(repository)
    }

    private val mangaListAdapter by lazy { MangaListAdapter() }

    private lateinit var binding: FragmentMangaListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMangaListBinding.inflate(inflater, container, false)
        setupRecyclerView()
        setListeners()
        observeViewModel(mangaListAdapter)
        return binding.root
    }

    private fun setupRecyclerView() = binding.apply {
        recyclerView.apply {
            adapter = mangaListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setListeners() = binding.apply {
        searchButton.setOnClickListener {
            val title = binding.editMangaTitle.text.toString()
            viewModel.getMangaListByTitle(title)
            (requireActivity() as MainActivity).closeKeyboard(binding.root)
        }
    }


    private fun observeViewModel(adapter: MangaListAdapter) = viewModel.apply {
        mangaList.observe(viewLifecycleOwner) { mangaList ->
            mangaList?.let {
                adapter.submitList(it)
                adapter.bi
            }
        }
        authors.observe(viewLifecycleOwner) { authorList ->
            authorList?.let {
                Log.i(TAG, "Authors in observe: $it")
                adapter.loadAuthors(it)
            }

        }
        loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let { loading ->
                val visibility = if (loading) View.VISIBLE else View.GONE
                binding.progressBar.visibility = visibility
            }
        }
    }

    companion object {
        const val TAG = "MangaListFragment.TAG"
    }
}