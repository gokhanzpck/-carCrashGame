package com.gokhanzopcuk.arabaoyunukotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gokhanzopcuk.arabaoyunukotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
      binding.button.setOnClickListener{
       val intent= Intent(this@MainActivity,OyunActivity::class.java)
        startActivity(intent)
    }
    }
}