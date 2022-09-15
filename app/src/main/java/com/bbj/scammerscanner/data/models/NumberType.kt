package com.bbj.scammerscanner.data.models

enum class NumberType(id : Int) {
    DEFAULT(0),
    SCAMMER(1),
    MAYBE_SCAMMER(2),
    SUSPICIOUS(3)
}