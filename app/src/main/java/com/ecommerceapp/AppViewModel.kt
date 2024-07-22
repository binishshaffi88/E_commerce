package com.ecommerceapp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerceapp.models.ImageItem
import com.ecommerceapp.models.ItemModel
import com.ecommerceapp.models.SpecialOfferModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    var product = MutableLiveData<List<ItemModel>>()
    val images= MutableLiveData<List<ImageItem>>()
    val grideproduct= MutableLiveData<List<SpecialOfferModel>>()
    var errorMessage = MutableLiveData<String>()
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    //private val firestore = FirebaseFirestore.getInstance()

    fun addDummyProductsAndFetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = listOf(
                ItemModel(
                    productName = "Product 1",
                    productImage = "https://upload.wikimedia.org/wikipedia/commons/5/52/Flag_of_%C3%85land.svg",
                    productRate = 0.0,
                    productPrice = 10.0
                ),
                ItemModel(
                    productName = "Product 2",
                    productImage = "https://upload.wikimedia.org/wikipedia/commons/7/77/Flag_of_Algeria.svg",
                    productRate = 0.0,
                    productPrice = 20.0
                ),
                ItemModel(
                    productName = "Product 3",
                    productImage = "https://upload.wikimedia.org/wikipedia/commons/7/77/Flag_of_Algeria.svg",
                    productRate = 0.0,
                    productPrice = 30.0
                )
            )

            for (product in products) {
                firestore.collection("products")
                    .add(product)
                    .addOnSuccessListener {
                        Log.d("Firestore", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error writing document", e)
                    }
            }

            delay(2000)  // Wait for 2 seconds before fetching data

        }
    }

    fun getProductData() {
        firestore.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val tempList = result.mapNotNull { it.toObject<ItemModel>() }
                product.postValue(tempList)
            }
            .addOnFailureListener { exception ->
                errorMessage.postValue(exception.message)
                Log.e("Firestore", "Data retrieval failed: " + exception.message)
            }
    }
    fun geImageData() {
        firestore.collection("images")
            .get()
            .addOnSuccessListener { result ->
                val tempList = result.mapNotNull { it.toObject<ImageItem>() }
                images.postValue(tempList)
            }
            .addOnFailureListener { exception ->
                errorMessage.postValue(exception.message)
                Log.e("Firestore", "Data retrieval failed: " + exception.message)
            }
    }
    fun geTgrideproductData() {
        firestore.collection("grideproduct")
            .get()
            .addOnSuccessListener { result ->
                val tempList = result.mapNotNull { it.toObject<SpecialOfferModel>() }
               grideproduct.postValue(tempList)
            }
            .addOnFailureListener { exception ->
                errorMessage.postValue(exception.message)
                Log.e("Firestore", "Data retrieval failed: " + exception.message)
            }
    }

}