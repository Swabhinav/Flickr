package com.example.flickrbrowsers

class Photo(val title:String, val author:String, val authorid:String,val link:String,val tags:String, val image:String) {

    override fun toString(): String {
        return "Photo(title='$title', author='$author', authorid='$authorid', link='$link', tags='$tags', image='$image')"
    }
}