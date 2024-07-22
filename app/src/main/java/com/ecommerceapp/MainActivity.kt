package com.ecommerceapp

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ecommerceapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private lateinit var navController: NavController
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        navController = navHostFragment?.findNavController()
            ?: throw IllegalStateException("NavHostFragment not found")
        initView()
        setContentView(binding!!.root)
    }

    private fun initView() {


        binding!!.imageViewSettings.setOnClickListener {
            binding!!.main.openDrawer(GravityCompat.START)
        }
        binding?.cart?.setOnClickListener {
            Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        binding!!.navView.setNavigationItemSelectedListener(this)

        setBottomNavigationListener()
        binding!!.home.setOnClickListener { navController.navigate(R.id.homeFragment) }
        binding!!.category.setOnClickListener { navController.navigate(R.id.catogeryFragment) }
        binding!!.cart.setOnClickListener { navController.navigate(R.id.favouriteFragment) }
        binding!!.account.setOnClickListener { navController.navigate(R.id.profileFragment) }
    }

    /*private fun setBottomNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding?.homeIcon?.setColorFilter(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.white,
                                null
                            )
                            )




                    binding?.txtHome?.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,

                            null
                        )
                    )



                    binding?.categoryIcon?.setColorFilter(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.white,
                            null
                        )
                    )

                    binding?.categoryTxt?.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.gray,
                            null
                        )
                    )


                    binding?.imgCart?.setColorFilter(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.gray,
                            null
                        )
                    )

                    binding?.txtCart?.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.gray,
                            null
                        )
                    )

                    binding?.accountIcon?.setColorFilter(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.gray,
                            null
                        )
                    )

                    binding?.txtAccount?.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.gray,
                            null
                        )
                    )


                }


            }

            when (destination.id) {

                R.id.homeFragment, R.id.catogeryFragment, R.id.favouriteFragment, R.id.profileFragment -> {
                    binding?.bottomNav?.visibility = View.VISIBLE
                    //binding.search.visibility = View.VISIBLE
                    // binding.back.visibility = View.GONE
                    binding?.imageViewSettings?.visibility = View.VISIBLE

                   // binding?.title?.text = getString(R.string.app_name)
                }

                *//*else -> {
                    binding?.title?.text = when (destination.id) {
//                        R.id.profile_fragment -> getString(R.string.profile)
//                        R.id.player_fragment -> getString(R.string.add_player)
//                        R.id.add_team_fragment -> getString(R.string.add_team)
//                        R.id.add_series_fragment -> getString(R.string.add_tournament)
//                        R.id.upcoming_match_detail -> getString(R.string.match_detail)
                        //else -> getString(R.string.start_match)
                    }

                    binding.search.visibility = View.INVISIBLE
                    binding.bottomNav.visibility = View.GONE
                    binding.back.visibility = View.VISIBLE
                    binding.imageViewMenu.visibility = View.INVISIBLE*//*
            }

        }

    }*/
    private fun setBottomNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateBottomNavigation(destination.id)
            updateTopBar(destination.id)
        }
    }

    private fun updateBottomNavigation(destinationId: Int) {
        val selectedColor = ResourcesCompat.getColor(resources, R.color.colorselected, null)
        val defaultColor = ResourcesCompat.getColor(resources, R.color.white, null)

        fun setIconAndTextColor(icon: ImageView, text: TextView, isSelected: Boolean) {
            val color = if (isSelected) selectedColor else defaultColor
            icon.setColorFilter(color)
            text.setTextColor(color)
        }

        setIconAndTextColor(binding!!.homeIcon, binding!!.txtHome, destinationId == R.id.homeFragment)
        setIconAndTextColor(binding!!.categoryIcon, binding!!.categoryTxt, destinationId == R.id.catogeryFragment)
        setIconAndTextColor(binding!!.cart, binding!!.txtCart, destinationId == R.id.favouriteFragment)
        setIconAndTextColor(binding!!.accountIcon, binding!!.txtAccount, destinationId == R.id.profileFragment)

    }

    private fun updateTopBar(destinationId: Int) {
        when (destinationId) {
            R.id.homeFragment, R.id.catogeryFragment, R.id.favouriteFragment, R.id.profileFragment  -> {
                binding?.bottomNav?.visibility = View.VISIBLE
                /*                binding.search.visibility = View.VISIBLE
                binding.back.visibility = View.GONE
                binding.imageViewMenu.visibility = View.VISIBLE
                binding.textViewTop.text = getString(R.string.app_name)
            }*/
            }
           /* else -> {
                binding.textViewTop.text = when (destinationId) {
                    R.id.profile_fragment -> getString(R.string.profile)
                    R.id.player_fragment -> getString(R.string.add_player)
                    R.id.add_team_fragment -> getString(R.string.add_team)
                    R.id.add_series_fragment -> getString(R.string.add_tournament)
                    R.id.upcoming_match_detail -> getString(R.string.match_detail)
                    R.id.updateProfile -> getString(R.string.update_profile)
                    else -> getString(R.string.start_match)
                }
                binding.search.visibility = View.INVISIBLE
                binding.bottomNav.visibility = View.GONE
                binding.back.visibility = View.VISIBLE
                binding.imageViewMenu.visibility = View.INVISIBLE
            }*/
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}
