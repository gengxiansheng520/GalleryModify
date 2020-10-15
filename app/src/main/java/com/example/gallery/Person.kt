package com.example.gallery
//次级构造函数呢 also是什么鬼 次级函数委托 data
open class Person constructor(firstName: String) {
    val firstProperty = "First property: $firstName".also(::println)
    init {

    }
    constructor(firstName: String, parent: Person) : this(firstName) {


    }


}
class Pp: Person("xx") {

}