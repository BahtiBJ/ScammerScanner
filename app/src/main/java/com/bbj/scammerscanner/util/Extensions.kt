package com.bbj.scammerscanner.util

fun String.decreaseToSize(size : Int) : String{
    if (this.length <= size){
        return this
    } else
        return (this.substring(0,size) + "...")
}

fun String.deleteStart() : String{
    return if (this.startsWith("+7"))
        this.replace("+7","8")
    else this
}