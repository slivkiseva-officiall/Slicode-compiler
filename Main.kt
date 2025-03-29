/*
 * version: 1.0.5
 * for slicode: 0.7.1
 * company: slivki company
*/
package main.compiler.file
import java.util.*
import java.io.File

class Compiler {
    private var spaces: Int = 0
    private var tabs: Int = 0
    private var flags: Boolean = false
    private var line: String = ""
    private var comments: Boolean = false
    private val variables = ArrayList<String>()
    private var compilateCode: String = ""

    public fun compiling(line: String) {
        if (!tabsSpacesCounter(line)) {
            println("compiling error: tabs: $tabs spaces: $spaces")
            return
        }
        if (line.startsWith("start main()")) {
            compilateCode += "fun main(){\n"
        }
        else if (line.startsWith("fun")) {
            compilateCode += line
        }
        else if (line == "}"){
            compilateCode += "}"
        }
        if (tabs >= 1 && spaces == 0) {
            var compilingvar = line.substring(tabs, line.length)
            when {
                compilingvar.startsWith("out(") -> {
                    compilingout(compilingvar)
                }
                compilingvar.startsWith("outln(") -> {
                    compilingoutln(compilingvar)
                }
                compilingvar.startsWith("var") -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    when{
                        compilingvar.indexOf("in.") != -1 -> {
                            when{
                                compilingvar.indexOf("int()") != -1 -> {
                                    for (char in compilingvar){
                                        if (char == '='){
                                            compilateCode += "="
                                            break
                                        }
                                        else{
                                            compilateCode += "$char"
                                        }
                                    }
                                    compilateCode += "readLine()?.toIntOrNull()\n"
                                }
                                compilingvar.indexOf("str()") != -1 -> {
                                    for (char in compilingvar){
                                        if (char == '='){
                                            compilateCode += "="
                                            break
                                        }
                                        else{
                                            compilateCode += "$char"
                                        }
                                    }
                                    compilateCode += "readLine()\n"
                                }
                                compilingvar.indexOf("float()") != -1 -> {
                                    for (char in compilingvar){
                                        if (char == '='){
                                            compilateCode += "="
                                            break
                                        }
                                        else{
                                            compilateCode += "$char"
                                        }
                                    }
                                    compilateCode += "readLine()?.toFloatOrNull()\n"
                                }
                                compilingvar.indexOf("bool()") != -1 -> {
                                    for (char in compilingvar){
                                        if (char == '='){
                                            compilateCode += "="
                                            break
                                        }
                                        else{
                                            compilateCode += "$char"
                                        }
                                    }
                                    compilateCode += "readLine()?.toBooleanStrictOrNull()\n"
                                }
                                else -> {
                                    println("error in line: ${this.line}")
                                }
                            }
                        }
                        else -> {
                            compilateCode += "$compilingvar\n"
                        }
                    }
                    
                    
                }
                compilingvar.startsWith("val") -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    compilateCode += "${compilingvar}\n"
                }
                compilingvar.startsWith("if") -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    compilateCode += compilingvar + "\n"
                }
                
                compilingvar == "}" -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    compilateCode += "}\n"
                }
                compilingvar.startsWith("while") -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    compilateCode += compilingvar + "\n"
                }
                compilingvar.startsWith("for") -> {
                    for (i in 0 until tabs){
                        compilateCode += "\t"
                    }
                    compilateCode += compilingvar + "\n"
                }
                compilingvar.startsWith("//") -> {}
                compilingvar.startsWith("/*") -> {
                    comments = true
                }
                compilingvar.startsWith("*/") -> {
                    comments = false
                }
            }
        }
    }

    private fun tabsSpacesCounter(input: String): Boolean {
        tabs = 0
        spaces = 0
        for (char in input) {
            when {
                char == ' ' -> spaces++
                char == '\t' -> tabs++
                else -> break
            }
        }

        return when {
            spaces >= 1 && tabs >= 1 -> false
            else -> true
        }
    }

    
    private fun compilingoutln(line: String) {
        if (tabs >= 1){
            for (i in 0 until tabs){
               compilateCode += "\t"
          }
        }
        var content = line.substring(6, line.length - 1)
        compilateCode += "println("
        for (char in content) {
            when {
                char == '"' -> {
                    compilateCode += "\""
                    flags = !flags
                }
                char == ',' && !flags -> {
                    compilateCode += "+"
                }
                else -> {
                    compilateCode += char
                }
            }
        }
        compilateCode += ")\n"
    }
    
    private fun compilingout(line: String) {
        if (tabs >= 1){
            for (i in 0 until tabs){
               compilateCode += "\t"
          }
        }
        var content = line.substring(4, line.length - 1)
        compilateCode += "print("
        for (char in content) {
            when {
                char == '"' -> {
                    compilateCode += "\""
                    flags = !flags
                }
                char == ',' && !flags -> {
                    compilateCode += "+"
                }
                else -> {
                    compilateCode += char
                }
            }
        }
        compilateCode += ")\n"
    }

    public fun outputCode(){
        println(compilateCode)
    }
}

fun main() {
    var compiler = Compiler()
    compiler.compiling("start main(){")
    compiler.compiling("\tvar a: Int = in.int()")
    compiler.compiling("\tif (a == 10){")
    compiler.compiling("\t\toutln(\"hello \", a)")
    compiler.compiling("\t}")
    compiler.compiling("}")
    compiler.outputCode()
}
