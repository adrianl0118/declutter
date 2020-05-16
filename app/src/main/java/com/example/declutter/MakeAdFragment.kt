package com.example.declutter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.declutter.databinding.MakeAdBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MakeAdFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var thing_type: String = "Furniture"
    private lateinit var imgBitmap: Bitmap
    private var imgUri: Uri? = null

    val thingTypes = arrayOf("Furniture", "Appliance", "Gaming", "Toys", "Household", "Pet", "Sports")

    lateinit var binding: MakeAdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.make_ad, container, false)

        val adapt = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, thingTypes)

        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.thingType.adapter = adapt
        binding.thingType.setOnItemSelectedListener(this)

        //SET AutoFIll to get rid of annoying warnings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.headlineEdit.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.widthInput.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.depthInput.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.heightInput.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.weightInput.setAutofillHints(View.AUTOFILL_HINT_NAME)
            binding.ownerEmail.setAutofillHints(View.AUTOFILL_HINT_EMAIL_ADDRESS)
            binding.ownerNumber.setAutofillHints(View.AUTOFILL_HINT_PHONE)
            binding.ownerPostal.setAutofillHints(View.AUTOFILL_HINT_POSTAL_CODE)
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
        binding.itemPicture.setImageBitmap(imgBitmap)
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
        var headline = binding.headlineEdit.text.toString()
        var desc = binding.thingDescEdit.text.toString()
        var width = binding.widthInput.text.toString().toInt()
        var depth = binding.depthInput.text.toString().toInt()
        var height = binding.heightInput.text.toString().toInt()
        var weight = binding.weightInput.text.toString().toInt()
        var contactEmail = binding.ownerEmail.text.toString()
        var contactPhone = binding.ownerNumber.text.toString()
        var contactPostal = binding.ownerPostal.text.toString()
        var contactInfo = ""

        //Name and Picture validation
        if (headline == "" || binding.itemPicture.drawable == null) {
            readyForSubmission = false
            //Inform user that name and photo are required
            error = "No Name and/or Image"
            binding.erroMessage.text = error
        }

        //Dimension validation
        try {
            width = binding.widthInput.text.toString().toInt()
            depth = binding.depthInput.text.toString().toInt()
            height = binding.heightInput.text.toString().toInt()
            weight = binding.weightInput.text.toString().toInt()
        } catch (e: NumberFormatException) {
            numeric = false
        }
        if (!numeric) {
            readyForSubmission = false
            //Inform user of improper dimensions and/or weights
            error += "\nImproper dimensions and/or weights"
            binding.erroMessage.text = error
        }

        //Email and Phone validation
        if (contactEmail == "" && contactPhone == "") {
            readyForSubmission = false
            //Inform user that either an email or phone is needed
            error += "\nNeed Email or Phone"
            binding.erroMessage.text = error

        } else {
            if (contactEmail != "") {
                contactInfo = "Email: $contactEmail"
            }
            if (contactPhone != "") {
                contactInfo += "\nPhone#: $contactPhone"
            }
        }

        //Postal Code Validation
        if (contactPostal == "" || contactPostal.length < 5 || contactPostal.length > 7) {
            readyForSubmission = false
            //Inform user that postal is required
            error += "\nPostal or ZIP code is required"
            binding.erroMessage.text = error
        }

        //Desc Validation
        if (desc == "") {
            desc = "No Bio Info Entered"
        }

        if (readyForSubmission) {
            val thing = Thingummy(headline, thing_type, width, depth, height, weight, desc, contactInfo, contactPostal)
            thingAdapter.add(thing, getImageFile(imgBitmap))
            view.findNavController().navigate(R.id.action_makeAdFragment_to_homePage)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        when (position) {
            0 -> thing_type = "Furniture"
            1 -> thing_type = "Appliance"
            2 -> thing_type = "Gaming"
            3 -> thing_type = "Toys"
            4 -> thing_type = "Household"
            5 -> thing_type = "Pet"
            else -> {
                thing_type = "Sports"
            }
        }
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }


}