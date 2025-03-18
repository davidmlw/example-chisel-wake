package syntax

import chisel3._
import chisel3.util._

class Null extends Module {
  val a = IO(Input(UInt(16.W)))
  val b = IO(Input(UInt(16.W)))
  val c = IO(Output(UInt(16.W)))
  val d = Wire(UInt(16.W))
  d := a + b
  val e = a - b
  c := d * e
}
