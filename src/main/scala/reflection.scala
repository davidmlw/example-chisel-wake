package example

import scala.reflect.runtime.universe._
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

// Sample class with a method we want to inspect
class SampleClass {
  def greet(name: String): String = {
    val greeting = "Hello"
    s"$greeting, $name!"
  }
}

object reflection extends App {
  // Create markdown content
  val markdownContent = new StringBuilder()
  markdownContent.append("# Method Analysis\n\n")
  
  // Get type information for our class
  val tpe = typeOf[SampleClass]
  val methodSymbol = tpe.decl(TermName("greet")).asMethod
  
  // Get method body by reading the source file directly
  val sourceFile = new java.io.File("src/main/scala/reflection.scala")
  val lines = scala.io.Source.fromFile(sourceFile).getLines().toList
  
  // Find the method in the source code
  val methodStart = lines.indexWhere(_.contains("def greet"))
  if (methodStart >= 0) {
    var methodEnd = methodStart
    var braceCount = 0
    var foundFirstBrace = false
    
    // Find the end of the method by counting braces
    for (i <- methodStart until lines.length if methodEnd == methodStart) {
      val line = lines(i)
      if (line.contains("{")) {
        foundFirstBrace = true
        braceCount += 1
      }
      if (line.contains("}")) {
        braceCount -= 1
      }
      if (foundFirstBrace && braceCount == 0) {
        methodEnd = i
      }
    }
    
    // Extract the method body (excluding the method signature)
    val methodBody = lines.slice(methodStart + 1, methodEnd + 1).mkString("\n")
    
    markdownContent.append("## Method Body\n\n```scala\n")
    markdownContent.append(methodBody)
    markdownContent.append("\n```\n\n")
  } else {
    markdownContent.append("Method body not found\n")
  }
  
  // Add method signature information
  markdownContent.append("## Method Signature Information\n\n")
  markdownContent.append(s"- **Method name:** ${methodSymbol.name}\n")
  markdownContent.append(s"- **Return type:** ${methodSymbol.returnType}\n")
  markdownContent.append(s"- **Parameter lists:** ${methodSymbol.paramLists}\n")
  
  // Write to markdown file
  Files.write(
    Paths.get("method_analysis.md"), 
    markdownContent.toString().getBytes(StandardCharsets.UTF_8)
  )
  
  println("Analysis has been written to method_analysis.md")
}
