package com.example.declutter

import android.widget.ImageView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileInputStream


val copy = mutableListOf<Thingummy?>()

class ThingummyAdapter {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var databaseReference: DatabaseReference
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private var useCopy = false

    private var array = mutableListOf<Thingummy?>()

    fun add(thing: Thingummy?, file: File) {
        databaseReference = database.reference
        var key = databaseReference.child("allThings").push().key
        var ref = databaseReference.child("allThings/$key")
        val childUpdates = mapOf("name" to thing?.name, "type" to thing?.type,
            "dim" to thing?.dim, "description" to thing?.description,
            "contact" to thing?.contact, "city" to thing?.city)
        ref.updateChildren(childUpdates)
        array.add(thing)

        val refStore = storageRef.child("${thing?.name}.jpg")
        val stream = FileInputStream(file)
        refStore.putStream(stream).addOnSuccessListener { stream.close() }
    }

    //Display an image of a thing inside a view. Requires a view and the name of the thing
    fun setImageOnView(name: String?, thingImageView: ImageView) {
        var imageRef = storageRef.child("${name}.jpg")
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Picasso.get().load(uri).into(thingImageView)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    //Retrieve the data from the firebase at the start of the app process. Only to be called once
    fun retrieveData() {
        databaseReference = database.reference
        var query: Query = databaseReference.child("allThings")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(singleSnapShot in dataSnapshot.children) {
                    val post = singleSnapShot.getValue(Thingummy::class.java)
                    if(post !in array) {
                        array.add(post)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) { }
        })

    }

    //Sort list by city (location)
    fun sort() {
        val sortedList = array.sortedWith(compareBy({it?.city}))
        array = sortedList.toMutableList()

    }

    //Filter by a specific city
    fun sortBy(city: String?) {
        copy.clear()

        for(thing in array){
            if(thing?.city == city) {
                copy.add(thing)
            }
        }

        if(copy.size > 0) {
            useCopy = true
        }
        else{
            useCopy = false
        }
    }

    fun reset() {
        useCopy = false
    }

    //This will return the array of things.
    fun returnArray(): MutableList<Thingummy?> {
        if(useCopy) return copy
        return array
    }

    //this will return the size of the array
    fun arraySize(): Int {
        if(useCopy) return copy.size
        return this.array.size
    }
}