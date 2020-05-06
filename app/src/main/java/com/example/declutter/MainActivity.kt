package com.example.declutter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.beardedhen.androidbootstrap.TypefaceProvider
import com.example.declutter.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

val thingAdapter = ThingummyAdapter()

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TypefaceProvider.registerDefaultIconSets();
        thingAdapter.retrieveData()
        thingAdapter.sort()
        auth = FirebaseAuth.getInstance()
        auth.signInAnonymously()
        //petAdapter.add(Pet("newPet", "snake", "new pet", "3230", 53))
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}
