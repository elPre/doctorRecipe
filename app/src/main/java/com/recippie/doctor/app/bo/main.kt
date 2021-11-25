package com.recippie.doctor.app.bo

fun main(args: Array<String>) {
    print(lengthOfLongestSubstring("aa"))

}

fun lengthOfLongestSubstring(s: String): Int {
    var count = 0
    var i = 0
    val map = mutableMapOf<Char, Int>()
    for (j in 0 until s.length-1) {
        if (map.contains(s[j])) {
            i = map[s[j]]!! + 1
        }
        map[s[j]] = j
        count = if ((j - i) >= count) j - i + 1 else count
    }
    return count
}

fun myAtoi(s: String): Int {

    return 0
}