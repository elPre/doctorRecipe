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
    //appendString()
    //kotlinUnion()
    //kotlinIntersection()

    //enableDisableButton()
    //addTwoNumberLinkedListTest()
    println(addBinary("1111","1111")) //1001
    println(addBinary("11","1")) //100
    println(addBinary("1010","1011")) //10101
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

    var one = a.length - 1
    var two = b.length - 1
    var carry = 0
    var oneNum = 0
    var twoNum = 0
    var sum = 0
    val result = StringBuilder()

    while (one >= 0 || two >= 0) {

        oneNum = if (one >= 0) a[one].digitToInt() else 0
        twoNum = if (two >= 0) b[two].digitToInt() else 0

        one--
        two--

        if (carry > 0) {
            when {
                oneNum == 1 && twoNum == 1 -> {
                    sum = 1
                    carry = 1
                }

                oneNum != 0 || twoNum != 0 -> {
                    sum = 0
                    carry = 1
                }

                else -> {
                    sum = 1
                    carry = 0
                }
            }
        } else {
            sum = oneNum xor twoNum
            carry = oneNum and twoNum
        }

        result.insert(0, sum)
    }

    if (carry > 0) {
        result.insert(0, carry)
    }

    return result.toString()
}

fun appendString() {
    var result = StringBuilder()
    result.insert(0,"a")
    result.insert(0,"b")
    result.insert(0,"c")
    println(result.toString())
}

fun kotlinUnion() {
    val original = listOf(1,2,3)
    val changingList = listOf(4,5,1)
    val unionTest: Set<Int> = original.union(changingList)
    println(unionTest)
}

fun kotlinIntersection() {
    val original = listOf(1)
    val changingList = emptyList<Int>()

    val intersectionTest = original.intersect(changingList)

    val shouldEnabled = intersectionTest.size != changingList.size
    println("$intersectionTest and the button is enabled $shouldEnabled")
}

fun enableDisableButton() {
    val original = listOf(1)
    val changingList = listOf(1,2)

    val mapOriginal = original.associateWith { it }
    val mapChange = changingList.associateWith { it }

    var isEnabled = false
    run mapLabel@ {
        if(original.size == changingList.size) {
            mapOriginal.forEach { (k, _) ->
                if(mapChange.containsKey(k)) {
                    isEnabled = false
                } else {
                    isEnabled = true
                    return@mapLabel
                }
            }
        } else {
            isEnabled = true
        }
    }
    println("and the button is enabled $isEnabled ")

}

fun addTwoNumberLinkedListTest() {
    val listOne = ListNode(2)
    listOne.next = ListNode(4)
    listOne.next!!.next = ListNode(3)

    val listTwo = ListNode(5)
    listTwo.next = ListNode(6)
    listTwo.next!!.next = ListNode(4)

    //result 708

    var finalList = addTwoNumbers(listOne, listTwo)
    while (finalList != null) {
       print(finalList.value)
       finalList = finalList.next
    }
}


class ListNode(var value: Int) {
    var next: ListNode? = null
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var result = ListNode(-1)
    var walker = ListNode(-1)
    var list1 = reverseLinkedList(l1)
    var list2 = reverseLinkedList(l2)
    var sum1 = 0
    var sum2 = 0
    var sumresult = 0
    while (list1 != null || list2 != null) {
        if (list1 !=  null) {
            sum1 = (sum1 * 10) + list1.value
        }
        if (list2 != null) {
            sum2 = (sum2 * 10) + list2.value
        }
        list1 =  list1?.next
        list2 = list2?.next
    }

    sumresult = sum1 + sum2

    while (sumresult > 0) {
        val digit = sumresult % 10
        sumresult /= 10
        if (result.value == -1) {
            result.value = digit
            walker = result
        } else {
            walker.next = ListNode(digit)
            walker = walker.next!!
        }
    }
    return result
}

fun reverseLinkedList(l1: ListNode?): ListNode? {
    var root = l1
    var holder = l1
    var tmp = l1?.next
    var removePointer = l1
    while (tmp != null) {
        root = tmp
        tmp = tmp.next
        root.next = holder
        holder = root
    }
    removePointer?.next = null
    return root
}

class Node(var value: Int) {
    var left: Node? = null
    var right: Node? = null
}

fun treeToDoublyList(root:Node?): Node? {
    val linkedList: Node? = null
    traverseInOrder(root, linkedList)
    return linkedList
}

fun traverseInOrder(root:Node?, linkedList:Node?) {
    traverseInOrder(root?.left, linkedList)
    createDoublyList(root, linkedList)
    traverseInOrder(root?.right, linkedList)
}

fun createDoublyList(node:Node?, root:Node?) {
    var tmp = root
    if(root == null) {
        tmp = Node(node?.value ?: 0)
    } else {
        var holder = tmp
        while(tmp != null) {
            holder = tmp
            tmp = tmp.right
        }
        holder?.right = node
        val newNode = holder?.right
        newNode?.left = holder
    }
}