package scalan.compilation.lms.common

import scala.reflect.SourceContext
import scala.lms.common._
import scalan.compilation.lms.cxx.sharedptr.CxxShptrCodegen
import scala.collection.mutable
import scala.annotation.tailrec
import scala.lms.internal.Transforming
import scalan.compilation.lms.LmsBackendFacade

trait VectorOps extends Variables {

  def array_dotProductSparse[A: Numeric: Manifest](idxs1: Rep[Array[Int]], vals1: Rep[Array[A]], idxs2: Rep[Array[Int]], vals2: Rep[Array[A]]): Rep[A]
  def arrayBinarySearch[A: Ordering: Manifest](idx: Rep[Int], idxs: Rep[Array[A]]): Rep[Int]
}

trait VectorOpsExp extends VectorOps with EffectExp with VariablesExp with Transforming { self: LmsBackendFacade =>

  case class ArrayDotProdSparse[A](idxs1: Exp[Array[Int]], vals1: Exp[Array[A]], idxs2: Exp[Array[Int]], vals2: Exp[Array[A]])
                                  (implicit val num: Numeric[A], val m: Manifest[A]) extends Def[A]

  case class ArrayBinarySearch[A](idx: Exp[Int], idxs: Exp[Array[A]])(implicit val ordA: Ordering[A], val mA: Manifest[A])
    extends Def[Int] {
    val m = manifest[Int]
  }

  def array_dotProductSparse[A: Numeric: Manifest](idxs1: Rep[Array[Int]], vals1: Rep[Array[A]], idxs2: Rep[Array[Int]], vals2: Rep[Array[A]]) =
    ArrayDotProdSparse(idxs1, vals1, idxs2, vals2)

  def arrayBinarySearch[A: Ordering: Manifest](idx: Rep[Int], idxs: Rep[Array[A]]): Rep[Int] =
    ArrayBinarySearch(idx, idxs)

  override def mirror[A:Manifest](e: Def[A], f: Transformer)(implicit pos: SourceContext): Exp[A] = e match {
    case dp @ ArrayDotProdSparse(i1, v1,i2, v2) => array_dotProductSparse(f(i1), f(v1), f(i2), f(v2))(dp.num, mtype(dp.m))
    case bs @ ArrayBinarySearch(i, is) => arrayBinarySearch(f(i), f(is))(bs.ordA, mtype(bs.mA))
    case _ => super.mirror(e,f)
  }
}

trait ScalaGenVectorOps extends ScalaGenBase {
  val IR: VectorOpsExp
  import IR._

  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case ds @ ArrayDotProdSparse(idxs1, vals1, idxs2, vals2) =>
      // existence of implicit Numeric[A] ensures we can have + and *
      // TODO use proper source quasiquoter
      stream.println("// generating dot product")
      stream.println("val " + quote(sym) + " ={")
      stream.println("\tval idxs1 = " + quote(idxs1))
      stream.println("\tval idxs2 = " + quote(idxs2))
      stream.println("\tval vals1 = " + quote(vals1))
      stream.println("\tval vals2 = " + quote(vals2))
      stream.println("\tvar i1 = 0")
      stream.println("\tvar i2 = 0")
      stream.println("\tvar out:" + remap(ds.m) + " = 0")
      stream.println("\twhile (i1 < idxs1.length && i2 < idxs2.length) {")
      stream.println("\t\tval ind1 = idxs1(i1)")
      stream.println("\t\tval ind2 = idxs2(i2)")
      stream.println("\t\tif (ind1 == ind2) { ")
      stream.println("\t\t\tout += vals1(i1) * vals2(i2)")
      stream.println("\t\t\ti1+=1")
      stream.println("\t\t\ti2+=1")
      stream.println("\t\t} else if (ind1 < ind2 ) {")
      stream.println("\t\t\ti1+=1")
      stream.println("\t\t} else {")
      stream.println("\t\t\ti2+=1")
      stream.println("\t\t}")
      stream.println("\t}")
      stream.println("\tout")
      stream.println("}")
    case ds @ ArrayBinarySearch(i, is) =>
      // TODO use proper source quasiquoter
      stream.println("// generating Binary Search in array")
      stream.println("val " + quote(sym) + " = {")
      stream.println("\tval out:" + remap(ds.m) + " = java.util.Arrays.binarySearch(" + quote(is) + ", " + quote(i) + ")")
      stream.println("\tout")
      stream.println("}")
    case _ => super.emitNode(sym, rhs)
  }

}

trait CxxShptrGenVectorOps extends CxxShptrCodegen {
  val IR: VectorOpsExp
  import IR._

  headerFiles ++= Seq("scalan/algorithm.hpp")

  override def emitNode(sym: Sym[Any], rhs: Def[Any]) = rhs match {
    case ds @ ArrayDotProdSparse(idxs1, vals1, idxs2, vals2) =>
      // += and * operators are assumed to be defined, since A is a numeric type
      // TODO use proper source quasiquoter
      stream.println("// generating dot product")
      emitConstruct(sym)
      stream.println("{")
      stream.println(s"\tauto idxs1 = ${quote(idxs1)};")
      stream.println(s"\tauto idxs2 = ${quote(idxs2)};")
      stream.println(s"\tauto vals1 = ${quote(vals1)};")
      stream.println(s"\tauto vals2 = ${quote(vals2)};")
      stream.println("\tsize_t i1 = 0;")
      stream.println("\tsize_t i2 = 0;")
      stream.println("\twhile (i1 < idxs1->size() && i2 < idxs2->size()) {")
      stream.println("\t\tauto ind1 = (*idxs1)[i1];")
      stream.println("\t\tauto ind2 = (*idxs2)[i2];")
      stream.println("\t\tif (ind1 == ind2) { ")
      stream.println(s"\t\t\t${quote(sym)} += (*vals1)[i1] * (*vals2)[i2];")
      stream.println("\t\t\ti1+=1;")
      stream.println("\t\t\ti2+=1;")
      stream.println("\t\t} else if (ind1 < ind2 ) {")
      stream.println("\t\t\ti1+=1;")
      stream.println("\t\t} else {")
      stream.println("\t\t\ti2+=1;")
      stream.println("\t\t}")
      stream.println("\t};")
      stream.println("}")
    case ds @ ArrayBinarySearch(i, is) =>
      emitValDef(sym, src"scalan::binary_search($is->begin(), $is->end(), $i);")
    case _ => super.emitNode(sym, rhs)
  }

}
