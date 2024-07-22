package com.ecommerceapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerceapp.AppViewModel
import com.ecommerceapp.adapters.ChilditemAdapter
import com.ecommerceapp.adapters.ImageAdapter
import com.ecommerceapp.adapters.SpecialOfferAdapter
import com.ecommerceapp.databinding.FragmentAllBinding
import com.google.firebase.firestore.FirebaseFirestore

class AllFragment : Fragment() {
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var specialOfferAdapter: SpecialOfferAdapter

    //private lateinit var firestore: FirebaseFirestore
    private val mViewModel: AppViewModel by lazy {
        ViewModelProvider(requireActivity())[AppViewModel::class.java]
    }
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()

    }
    private lateinit var navController: NavController
    private lateinit var adapter: ChilditemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        imageAdapter = ImageAdapter(arrayListOf(), requireContext())
        binding.circleRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        binding.circleRecyclerView.adapter = imageAdapter
        binding.circleRecyclerView.setHasFixedSize(true)

        adapter = ChilditemAdapter(arrayListOf(), requireContext())
        binding.mainRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)//LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.setHasFixedSize(true)
//this line for 3rd adapter
       specialOfferAdapter =SpecialOfferAdapter(arrayListOf(), requireContext())
        binding.grideRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
        binding.grideRecyclerView.adapter = specialOfferAdapter
        binding.grideRecyclerView.setHasFixedSize(true)




        mViewModel.images.observe(viewLifecycleOwner) { images ->
            imageAdapter.updateList(images)

        }


        mViewModel.product.observe(viewLifecycleOwner) { products ->
            adapter.updateList(products)

        }
        mViewModel.grideproduct.observe(viewLifecycleOwner) { grideproduct ->
            specialOfferAdapter.updateList(grideproduct)

        }


        mViewModel.errorMessage.observe(viewLifecycleOwner) {
            // Handle the error message, if needed
        }

        // Add dummy products to Firestore and fetch them
        mViewModel.getProductData()
        mViewModel.geImageData()
        mViewModel.geTgrideproductData()
    }

    companion object {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}