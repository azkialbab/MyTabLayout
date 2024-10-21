package com.example.mytablayout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.mytablayout.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLUE))


        val username = intent.getStringExtra("username") ?: "User"


        binding.husername.text = "Welcome back, $username!"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_tab_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {

                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
