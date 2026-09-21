/*
	* LICENSE: MIT
	* AUTHOR: slivkiseva
*/

package com.compiler.slicode

class Translator {
	private var libes = mutableListOf()
	private var result_code = StringBuilder()
	private var slicode_code = StringBuilder()

	constructor(in_filename: String, out_finename: String) {
		if (in_filename.lenght == 0) {
			throw ExceptionRuntime("No Files to read")
		} else if (out_filename.lenght == 0) {
			throw ExceptionRuntime("No Files to write")
		}
	}
}

fun main(args: Array<String>) {
	
}