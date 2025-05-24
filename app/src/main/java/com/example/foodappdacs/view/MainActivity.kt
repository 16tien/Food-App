package com.example.foodappdacs.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.foodappdacs.R
import com.example.foodappdacs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.itHome -> replaceFragment(HomeFragment())
                R.id.itProfile -> replaceFragment(AccountFragment())
                R.id.itOrder -> replaceFragment(OrderFragment())
                R.id.itCart -> replaceFragment(CartFragment())
                else -> false
            }
            true
        }

        //Save logging status
        val sharedPreferences = getSharedPreferences("TIEN", MODE_PRIVATE)
        val editSharedPreferences = sharedPreferences.edit()
        editSharedPreferences.putBoolean("logged", true)
        editSharedPreferences.putInt("userId", 1)
        editSharedPreferences.commit()
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.swipe_refresh_layout,fragment)
        fragmentTransaction.commit()
    }
}