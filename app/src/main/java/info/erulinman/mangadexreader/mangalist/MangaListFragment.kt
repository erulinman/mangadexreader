package info.erulinman.mangadexreader.mangalist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Lazy
import info.erulinman.mangadexreader.App
import info.erulinman.mangadexreader.MainActivity
import info.erulinman.mangadexreader.databinding.FragmentMangaListBinding
import javax.inject.Inject

class MangaListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: Lazy<MangaListViewModel.Factory>

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory.get()).get(MangaListViewModel::class.java)
    }

    private val mangaListAdapter by lazy { MangaListAdapter() }

    private lateinit var binding: FragmentMangaListBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as App).appComponent.inject(this)
    }

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
            viewModel.fetchData(title)
            (requireActivity() as MainActivity).closeKeyboard(binding.root)
        }
    }

    private fun observeViewModel(adapter: MangaListAdapter) = viewModel.apply {
        mangaList.observe(viewLifecycleOwner) { mangaList ->
            mangaList?.let {
                Log.i(TAG, "Manga: $it")
                adapter.setMangaList(it)
                adapter.notifyDataSetChanged()
            }
        }
        authors.observe(viewLifecycleOwner) { authorList ->
            authorList?.let {
                Log.i(TAG, "Author: $it")
                adapter.setAuthorList(it)
                adapter.notifyDataSetChanged()
            }
        }
        loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                val visibility = if (it) View.VISIBLE else View.GONE
                binding.progressBar.visibility = visibility
            }
        }
    }

    companion object {
        const val TAG = "MangaListFragment.TAG"
    }
}