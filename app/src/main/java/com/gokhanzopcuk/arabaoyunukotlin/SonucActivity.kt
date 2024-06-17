package com.gokhanzopcuk.arabaoyunukotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gokhanzopcuk.arabaoyunukotlin.databinding.ActivityOyunBinding
import com.gokhanzopcuk.arabaoyunukotlin.databinding.ActivitySonucBinding

class SonucActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySonucBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySonucBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
      val number=  intent.getIntExtra("number",0)
         binding.skor.text=number.toString()
        val sp=getSharedPreferences("Sonuc",Context.MODE_PRIVATE)
        val es=sp.getInt("enYüksekSkor",0)
        if (number>es){
            val editor=sp.edit()
            editor.putInt("enYüksekSkor",number)
            editor.commit()
            binding.enyKsekskor.text=number.toString()
        }else{
            binding.enyKsekskor.text=es.toString()
        }
        binding.buttonTekrar.setOnClickListener {
            val intent=Intent(this@SonucActivity,OyunActivity::class.java)
            startActivity(intent)
        }

    }
}