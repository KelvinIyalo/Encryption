package com.example.encryption

import androidx.lifecycle.ViewModel
import java.security.MessageDigest

class HomeViewModel:ViewModel() {
    fun hash(plainText:String,algorithem:String):String{
    val byteArray = MessageDigest.getInstance(algorithem).digest(plainText.toByteArray())
        return toHex(byteArray)
    }
    private fun toHex(byteArray:ByteArray):String{
        return byteArray.joinToString("") { "%02x".format(it) }
    }
}