package com.recippie.doctor.app.bo

import java.lang.StringBuilder

fun main() {
//    print(lengthOfLongestSubstring("aa"))
//    println(myAtoi("9"))
//    println(myAtoi("39"))
//    print(myAtoi("369"))
//    println(romanToInt("XIX"))
//    moveZeroes(intArrayOf(0,1,0,3,12))
//    print("result -> ${removeDuplicates(intArrayOf(0,0,1,1,1,2,2,3,3,4))}")
    //addBinary("5","8")
    appendString()
}

fun lengthOfLongestSubstring(s: String): Int {
    var count = 0
    var i = 0
    val map = mutableMapOf<Char, Int>()
    for (j in s.indices) {
        if (map.contains(s[j])) {
            i = map[s[j]]!! + 1
        }
        map[s[j]] = j
        count = if ((j - i) >= count) j - i + 1 else count
    }
    return count
}

fun myAtoi(s: String): Int {
    var result =  0
    for (i in s.indices) {
        result = 10 * result + (s.elementAt(i) - '0')
    }
    return result
}

fun romanToInt(s: String): Int {

    val romanMap = mapOf(
        "I" to 1,
        "V" to 5,
        "X" to 10,
        "L" to 50,
        "C" to 100,
        "D" to 500,
        "M" to 1000)

    var i = s.length - 1
    var value = 0
    while (i >= 0) {
        if(romanMap.containsKey(s[i].toString())) {
            if(i < s.length - 1 && romanMap[s[i].toString()]!! < romanMap[s[i + 1].toString()]!!) {
                value -= romanMap[s[i].toString()]!!
            } else {
                value += romanMap[s[i].toString()]!!
            }
        }
        i--
    }
    return value
}

fun moveZeroes(nums: IntArray): Unit {
/*
Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
 */
    var right = 0
    var left = 0
    while(right < nums.size) {
        if(nums[right] > 0) {
            nums[left] = nums[right]
            nums[right] = 0
            left++
        }
        right++
    }
    nums.forEach { print("$it,") }
}

fun removeDuplicates(nums: IntArray): Int {
/*
Input: nums = [0,0,1,1,1,2,2,3,3,4]
Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
 */
    //always pos 0 its going to be the first one
    var j = 0
    for (i in 0 until nums.size - 1) {
        if(nums[i] != nums[i + 1] ) {
            nums[j++] = nums[i]
        }
    }
    nums[j++] = nums[nums.size - 1]
    nums.forEach { print("$it,") }
    println()
    return j
}

fun addBinary(a: String, b: String): String {
    //lets assume both strings are same size
    when {
        a.length > 10000 -> return ""
        b.length > 10000 -> return ""
    }

//    var one = a.toInt()
//    var two = a.toInt()
//    var carry = 0
//    while (two != 0) {
//        carry = one and two
//        one = one xor two
//        two = carry shl 1
//    }
//    print(one)

    var carry = 0
    var aData = 0
    var bData = 0
    var result: StringBuilder = StringBuilder()
    for (i in a.length-1 downTo 0) {
        aData = a[i].code
        if(carry > 0) {
            when {
                aData == 1 && bData == 1 -> {}
                aData == 1 && bData == 0 -> {}
                aData == 0 && bData == 1 -> {}
                aData == 0 && bData == 0 -> {}
            }
        }

        result.insert(0,"c")
    }

    return ""
}

fun appendString() {
    var result = StringBuilder()
    result.insert(0,"a")
    result.insert(0,"b")
    result.insert(0,"c")
    println(result.toString())
}