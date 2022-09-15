package com.bbj.scammerscanner.util

fun String.decreaseToSize(size : Int) : String{
    if (this.length <= size){
        return this
    } else
        return (this.substring(0,size) + "...")
}
