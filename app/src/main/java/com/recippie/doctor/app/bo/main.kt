package com.recippie.doctor.app.bo

fun main() {
//    print(lengthOfLongestSubstring("aa"))
//    println(myAtoi("9"))
//    println(myAtoi("39"))
//    print(myAtoi("369"))
//    println(romanToInt("XIX"))
    moveZeroes(intArrayOf(0,1,0,3,12))
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