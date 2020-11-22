package com.cocooncreations.topstories.delegate

interface ItemClickDelegate {
    /*
    Passing Any object so we can use this method as generic purpose
    */
    fun onClick(any: Any)
}