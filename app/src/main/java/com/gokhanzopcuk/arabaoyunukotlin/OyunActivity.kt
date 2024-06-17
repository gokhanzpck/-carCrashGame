package com.gokhanzopcuk.arabaoyunukotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.gokhanzopcuk.arabaoyunukotlin.databinding.ActivityMainBinding
import com.gokhanzopcuk.arabaoyunukotlin.databinding.ActivityOyunBinding
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunActivity : AppCompatActivity() {
    private var dokunmaKontrl=false
    private var baslangicKontrol=false
    private var anaKarakterGenisligi=0
    private var anaKarakterYuksekligi=0
    private var anakarakterX=0.0f
    private var anakarakterY=0.0f
    private val timer= Timer()
    private var ekranGenisligi=0
    private var ekranYuksekligi=0
    private var skor=0
    private var laX=0.0f
    private var laY=0.0f
    private var saX=0.0f
    private var saY=0.0f
    private var paX=0.0f
    private var paY=0.0f
    var number=0
    var runnable:Runnable=Runnable{}
    var handler:Handler= Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityOyunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOyunBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
     binding.basla.setOnClickListener{
         binding.bittiButton.visibility=View.INVISIBLE
         number=0
         runnable=object :Runnable{
             override fun run() {
                 number+=1
                 binding.TextSkor.text="Skor: ${number}"
                 handler.postDelayed(runnable,1000)

             }
         }
         handler.post(runnable)
        binding.cl.setOnTouchListener(object :View.OnTouchListener{

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            binding.basla.visibility=View.INVISIBLE
           if (baslangicKontrol){
               if (event?.action==MotionEvent.ACTION_DOWN){
                   Log.e("MotionEvent","ACTİON_DOWN : eKRANA DOKUNDU")
                 dokunmaKontrl=true
               }
               if (event?.action==MotionEvent.ACTION_UP){
                   Log.e("MotionEvent","ACTİON_UP : eKRANI BIRAKTI")
                   //ekranı bıraktıgımızda burda işlem yapacagız
                   dokunmaKontrl=false
               }
            } else{

               baslangicKontrol=true
               anaKarakterGenisligi=binding.anaAraba.width
               anaKarakterYuksekligi=binding.anaAraba.height
               ekranGenisligi=binding.cl.width
               ekranYuksekligi=binding.cl.height
               anakarakterX=binding.anaAraba.x
               anakarakterY=binding.anaAraba.y
               timer.schedule(0,20){
                  Handler(Looper.getMainLooper()).post{
                anaKarakterHareketEttirme()
                      cisimleriHareketEttirme()
                      carpismaKontrol()


                  }
               }
           }
                return true
            }
        })
    }
    }
    fun stop(view: View){
        binding.TextSkor.text=number.toString()
        handler.removeCallbacks(runnable)
    }

    fun anaKarakterHareketEttirme(){
        val anakarakterHiz=ekranYuksekligi/150.0f
        if(dokunmaKontrl){
            anakarakterY-=anakarakterHiz
        }else{
            anakarakterY+=anakarakterHiz
        }
        if (anakarakterY<=0.0f){
            anakarakterY=0.0f
        }
        if (anakarakterY>=ekranYuksekligi-anaKarakterYuksekligi){
            anakarakterY=(ekranYuksekligi-anaKarakterYuksekligi).toFloat()

        }
        binding.anaAraba.y=anakarakterY


        if (dokunmaKontrl) {
            anakarakterX += 20.0f;
        }else{
            anakarakterX -= 20.0f
        }
        if (anakarakterX <= 0.0f) {
            anakarakterX = 0.0f
        } else if (anakarakterX >= ekranGenisligi - anaKarakterGenisligi) {
            anakarakterX = (ekranGenisligi - anaKarakterGenisligi).toFloat()
        }
        binding.anaAraba.x = anakarakterX
    }
    fun cisimleriHareketEttirme(){
        saY-=ekranYuksekligi/104.0f
        laY-=ekranYuksekligi/74.0f
        paY-=ekranYuksekligi/104.0f

        if(saY<0.0f){
            saY=ekranYuksekligi+20.0f
            saX= floor(Math.random()*ekranGenisligi).toFloat()
        }
        binding.sa.x=saX
        binding.sa.y=saY
        if(paY<0.0f){
            paY=ekranYuksekligi+20.0f
            paX= floor(Math.random()*ekranGenisligi).toFloat()
        }
        binding.pa.x=paX
        binding.pa.y=paY
        if(laY<0.0f){
            laY=ekranYuksekligi+20.0f
            laX= floor(Math.random()*ekranGenisligi).toFloat()
        }
        binding.la.x=laX
        binding.la.y=laY

    }
    fun carpismaKontrol(){

        val laMX=laX+binding.la.width/2.0f
        val laMY=laY+binding.la.height/2.0f
        val paMY=paY+binding.pa.height/2.0f
        val paMX=paX+binding.pa.width/2.0f
        val saMX=saX+binding.sa.width/2.0f
        val saMY=saY+binding.sa.width/2.0f
        if (0.0f<laMX && laMX<=anakarakterX+anaKarakterGenisligi
            && anakarakterY<=laMY && laMY<=anakarakterY+anaKarakterYuksekligi){
          stop(binding.TextSkor)
            binding.pa.visibility=View.INVISIBLE
            binding.la.visibility=View.INVISIBLE
            binding.sa.visibility=View.INVISIBLE
            binding.anaAraba.visibility=View.INVISIBLE
            binding.bittiButton.visibility=View.VISIBLE
            binding.bittiButton.setOnClickListener {
                val intent=Intent(this@OyunActivity,SonucActivity::class.java)
                intent.putExtra("number",number)
                startActivity(intent)
            }
        }

        if (0.0f<saMX && saMX<=anakarakterX+anaKarakterGenisligi
            && anakarakterY<=saMY && saMY<=anakarakterY+anaKarakterYuksekligi){
            stop(binding.TextSkor)
            binding.pa.visibility=View.INVISIBLE
            binding.la.visibility=View.INVISIBLE
            binding.sa.visibility=View.INVISIBLE
            binding.anaAraba.visibility=View.INVISIBLE
            binding.bittiButton.visibility=View.VISIBLE
            binding.bittiButton.setOnClickListener {
                val intent=Intent(this@OyunActivity,SonucActivity::class.java)
                intent.putExtra("number",number)
                startActivity(intent)
            }
        }

            if (0.0f<paMX && paMX<=anakarakterX+anaKarakterGenisligi
                && anakarakterY<=paMY && paMY<=anakarakterY+anaKarakterYuksekligi){
                stop(binding.TextSkor)
                binding.pa.visibility=View.INVISIBLE
                binding.la.visibility=View.INVISIBLE
                binding.sa.visibility=View.INVISIBLE
                binding.anaAraba.visibility=View.INVISIBLE
                binding.bittiButton.visibility=View.VISIBLE
                binding.bittiButton.setOnClickListener {
                    val intent=Intent(this@OyunActivity,SonucActivity::class.java)
                    intent.putExtra("number",number)
                    startActivity(intent)
                }

            }
        binding.TextSkor.text=number.toString()








    }
}