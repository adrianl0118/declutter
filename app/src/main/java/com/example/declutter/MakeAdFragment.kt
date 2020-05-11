package com.example.declutter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Insets.add
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.OneShotPreDrawListener.add
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MakeAdFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var thing_type: String = "Furniture"
    private lateinit var imgBitmap: Bitmap
    private var imgUri: Uri? = null

    val thingTypes = arrayOf("Furniture", "Appliance", "Gaming", "Toys", "Household", "Pet", "Vehicle", "Sports")

    lateinit var binding: MakeAdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.make_ad, container, false)

        val adapt = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, thingTypes)

        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.thingType.adapter = adapt
        binding.thingType.setOnItemSelectedListener(this)

        //SET AutoFIll to get rid of annoying warnings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.thingNameEdit.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.ownerEmail.setAutofillHints(View.AUTOFILL_HINT_EMAIL_ADDRESS)
            binding.ownerNumber.setAutofillHints(View.AUTOFILL_HINT_PHONE)
            binding.ownerCity.setAutofillHints(View.AUTOFILL_HINT_POSTAL_CODE)
        }

        binding.takePhoto.setOnClickListener { view: View ->
            dispatchTakePictureIntent(1)
        }
        binding.submitBt.setOnClickListener { view: View ->
            buttonCheck(view)
        }

        return binding.root
    }

    private fun dispatchTakePictureIntent(actionCode: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, actionCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleSmallCameraPhoto(data)

    }

    private fun handleSmallCameraPhoto(intent: Intent?) {
        val extras = intent?.extras
        imgBitmap = extras!!.get("data") as Bitmap
        binding.thingPicture.setImageBitmap(imgBitmap)

    }

    // Method to save an bitmap to a file
    private fun getImageFile(bitmap: Bitmap): File {
        // Initialize a new file instance to save bitmap object
        var file = context?.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return file
    }


    private fun buttonCheck(view: View) {
        var readyForSubmission = true
        var numeric = true
        var error = ""
        var contactCity = 0
        var name = binding.thingNameEdit.text.toString()
        var bio = binding.thingDescEdit.text.toString()
        var contactEmail = binding.ownerEmail.text.toString()
        var contactPhone = binding.ownerNumber.text.toString()
        var contactInfo = ""


        //Name and Picture validation
        if (name == "" || binding.thingPicture.drawable == null) {
            readyForSubmission = false
            //Inform user that name and zip is required
            error = "No Name and/or Image"
            binding.erroMessage.text = error
        }

        //Email and Phone validation
        if (contactEmail == "" && contactPhone == "") {
            readyForSubmission = false
            //Inform user that either an email or phone is needed
            error += "\nNeed Email/or Phone"
            binding.erroMessage.text = error

        } else {
            if (contactEmail != "") {
                contactInfo = "Email: $contactEmail"
            }
            if (contactPhone != "") {
                contactInfo += "\nPhone#: $contactPhone"
            }
        }

        //City Validation
        try {
            contactCity = binding.ownerCity.text.toString()
        } catch (e: NumberFormatException) {
            numeric = false
        }

        if ((!numeric) || binding.ownerCity.text.toString().length != 5) {
            readyForSubmission = false
            //Inform user of improper zip code
            error += "\nImproper ZIP Code"
            binding.erroMessage.text = error
        }

        //Bio Validation
        if (bio == "") {
            bio = "No Bio Info Entered"
        }

        if (readyForSubmission) {
            val pet = Thingummy(name, pet_type, bio, contactInfo, contactZip)
            ThingummyAdapter.add(pet, getImageFile(imgBitmap))
            view.findNavController().navigate(R.id.action_makeProfileFragment_to_homePage)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when (position) {
            //"Dog", "Cat", "Bird", "Rabbit", "Hamster", "Lizard", "Snake", "Turtle", "Other"
            0 -> thing_type = "Dog"
            1 -> thing_type = "Cat"
            2 -> thing_type = "Bird"
            3 -> thing_type = "Rabbit"
            4 -> thing_type = "Hamster"
            5 -> thing_type = "Lizard"
            6 -> thing_type = "Snake"
            7 -> thing_type = "Turtle"
            else -> {
                thing_type = "Other"
            }
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


}