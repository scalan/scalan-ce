package scalan.graphs
package impl

import scala.annotation.unchecked.uncheckedVariance
import scalan.collections.CollectionsDsl
import scalan.common.Default
import scalan._
import scala.reflect.runtime.universe._
import scala.reflect._
import scalan.common.Default

// Abs -----------------------------------
trait FrontsAbs extends Scalan with Fronts {
  self: FrontsDsl =>
  // single proxy for each type family
  implicit def proxyFront(p: Rep[Front]): Front = {
    proxyOps[Front](p)(classTag[Front])
  }

  class FrontElem[To <: Front]
    extends EntityElem[To] {
    override def isEntityType = true
    override def tag = {
      weakTypeTag[Front].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Reifiable[_]]) = convertFront(x.asRep[Front])
    def convertFront(x : Rep[Front]): Rep[To] = {
      assert(x.selfType1.isInstanceOf[FrontElem[_]])
      x.asRep[To]
    }
    override def getDefaultRep: Rep[To] = ???
  }

  implicit def frontElement =
    new FrontElem[Front]()

  trait FrontCompanionElem extends CompanionElem[FrontCompanionAbs]
  implicit lazy val FrontCompanionElem: FrontCompanionElem = new FrontCompanionElem {
    lazy val tag = weakTypeTag[FrontCompanionAbs]
    protected def getDefaultRep = Front
  }

  abstract class FrontCompanionAbs extends CompanionBase[FrontCompanionAbs] with FrontCompanion {
    override def toString = "Front"
  }
  def Front: Rep[FrontCompanionAbs]
  implicit def proxyFrontCompanion(p: Rep[FrontCompanion]): FrontCompanion = {
    proxyOps[FrontCompanion](p)
  }

  // elem for concrete class
  class BaseFrontElem(val iso: Iso[BaseFrontData, BaseFront])
    extends FrontElem[BaseFront]
    with ViewElem[BaseFrontData, BaseFront] {
    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to BaseFront: missing fields List(bits)")
    override def getDefaultRep = super[ViewElem].getDefaultRep
    override lazy val tag = super[ViewElem].tag
  }

  // state representation type
  type BaseFrontData = (BaseCollection[Int], BitSet)

  // 3) Iso for concrete class
  class BaseFrontIso
    extends Iso[BaseFrontData, BaseFront] {
    override def from(p: Rep[BaseFront]) =
      unmkBaseFront(p) match {
        case Some((set, bits)) => Pair(set, bits)
        case None => !!!
      }
    override def to(p: Rep[(BaseCollection[Int], BitSet)]) = {
      val Pair(set, bits) = p
      BaseFront(set, bits)
    }
    lazy val tag = {
      weakTypeTag[BaseFront]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[BaseFront]](BaseFront(element[BaseCollection[Int]].defaultRepValue, element[BitSet].defaultRepValue))
    lazy val eTo = new BaseFrontElem(this)
  }
  // 4) constructor and deconstructor
  abstract class BaseFrontCompanionAbs extends CompanionBase[BaseFrontCompanionAbs] with BaseFrontCompanion {
    override def toString = "BaseFront"
    def apply(p: Rep[BaseFrontData]): Rep[BaseFront] =
      isoBaseFront.to(p)
    def apply(set: Rep[BaseCollection[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
      mkBaseFront(set, bits)
    def unapply(p: Rep[BaseFront]) = unmkBaseFront(p)
  }
  def BaseFront: Rep[BaseFrontCompanionAbs]
  implicit def proxyBaseFrontCompanion(p: Rep[BaseFrontCompanionAbs]): BaseFrontCompanionAbs = {
    proxyOps[BaseFrontCompanionAbs](p)
  }

  class BaseFrontCompanionElem extends CompanionElem[BaseFrontCompanionAbs] {
    lazy val tag = weakTypeTag[BaseFrontCompanionAbs]
    protected def getDefaultRep = BaseFront
  }
  implicit lazy val BaseFrontCompanionElem: BaseFrontCompanionElem = new BaseFrontCompanionElem

  implicit def proxyBaseFront(p: Rep[BaseFront]): BaseFront =
    proxyOps[BaseFront](p)

  implicit class ExtendedBaseFront(p: Rep[BaseFront]) {
    def toData: Rep[BaseFrontData] = isoBaseFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoBaseFront: Iso[BaseFrontData, BaseFront] =
    new BaseFrontIso

  // 6) smart constructor and deconstructor
  def mkBaseFront(set: Rep[BaseCollection[Int]], bits: Rep[BitSet]): Rep[BaseFront]
  def unmkBaseFront(p: Rep[BaseFront]): Option[(Rep[BaseCollection[Int]], Rep[BitSet])]

  // elem for concrete class
  class ListFrontElem(val iso: Iso[ListFrontData, ListFront])
    extends FrontElem[ListFront]
    with ViewElem[ListFrontData, ListFront] {
    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to ListFront: missing fields List(bits)")
    override def getDefaultRep = super[ViewElem].getDefaultRep
    override lazy val tag = super[ViewElem].tag
  }

  // state representation type
  type ListFrontData = (ListCollection[Int], BitSet)

  // 3) Iso for concrete class
  class ListFrontIso
    extends Iso[ListFrontData, ListFront] {
    override def from(p: Rep[ListFront]) =
      unmkListFront(p) match {
        case Some((set, bits)) => Pair(set, bits)
        case None => !!!
      }
    override def to(p: Rep[(ListCollection[Int], BitSet)]) = {
      val Pair(set, bits) = p
      ListFront(set, bits)
    }
    lazy val tag = {
      weakTypeTag[ListFront]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[ListFront]](ListFront(element[ListCollection[Int]].defaultRepValue, element[BitSet].defaultRepValue))
    lazy val eTo = new ListFrontElem(this)
  }
  // 4) constructor and deconstructor
  abstract class ListFrontCompanionAbs extends CompanionBase[ListFrontCompanionAbs] with ListFrontCompanion {
    override def toString = "ListFront"
    def apply(p: Rep[ListFrontData]): Rep[ListFront] =
      isoListFront.to(p)
    def apply(set: Rep[ListCollection[Int]], bits: Rep[BitSet]): Rep[ListFront] =
      mkListFront(set, bits)
    def unapply(p: Rep[ListFront]) = unmkListFront(p)
  }
  def ListFront: Rep[ListFrontCompanionAbs]
  implicit def proxyListFrontCompanion(p: Rep[ListFrontCompanionAbs]): ListFrontCompanionAbs = {
    proxyOps[ListFrontCompanionAbs](p)
  }

  class ListFrontCompanionElem extends CompanionElem[ListFrontCompanionAbs] {
    lazy val tag = weakTypeTag[ListFrontCompanionAbs]
    protected def getDefaultRep = ListFront
  }
  implicit lazy val ListFrontCompanionElem: ListFrontCompanionElem = new ListFrontCompanionElem

  implicit def proxyListFront(p: Rep[ListFront]): ListFront =
    proxyOps[ListFront](p)

  implicit class ExtendedListFront(p: Rep[ListFront]) {
    def toData: Rep[ListFrontData] = isoListFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoListFront: Iso[ListFrontData, ListFront] =
    new ListFrontIso

  // 6) smart constructor and deconstructor
  def mkListFront(set: Rep[ListCollection[Int]], bits: Rep[BitSet]): Rep[ListFront]
  def unmkListFront(p: Rep[ListFront]): Option[(Rep[ListCollection[Int]], Rep[BitSet])]

  // elem for concrete class
  class CollectionFrontElem(val iso: Iso[CollectionFrontData, CollectionFront])
    extends FrontElem[CollectionFront]
    with ViewElem[CollectionFrontData, CollectionFront] {
    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to CollectionFront: missing fields List(bits)")
    override def getDefaultRep = super[ViewElem].getDefaultRep
    override lazy val tag = super[ViewElem].tag
  }

  // state representation type
  type CollectionFrontData = (Collection[Int], BitSet)

  // 3) Iso for concrete class
  class CollectionFrontIso
    extends Iso[CollectionFrontData, CollectionFront] {
    override def from(p: Rep[CollectionFront]) =
      unmkCollectionFront(p) match {
        case Some((set, bits)) => Pair(set, bits)
        case None => !!!
      }
    override def to(p: Rep[(Collection[Int], BitSet)]) = {
      val Pair(set, bits) = p
      CollectionFront(set, bits)
    }
    lazy val tag = {
      weakTypeTag[CollectionFront]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[CollectionFront]](CollectionFront(element[Collection[Int]].defaultRepValue, element[BitSet].defaultRepValue))
    lazy val eTo = new CollectionFrontElem(this)
  }
  // 4) constructor and deconstructor
  abstract class CollectionFrontCompanionAbs extends CompanionBase[CollectionFrontCompanionAbs] with CollectionFrontCompanion {
    override def toString = "CollectionFront"
    def apply(p: Rep[CollectionFrontData]): Rep[CollectionFront] =
      isoCollectionFront.to(p)
    def apply(set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront] =
      mkCollectionFront(set, bits)
    def unapply(p: Rep[CollectionFront]) = unmkCollectionFront(p)
  }
  def CollectionFront: Rep[CollectionFrontCompanionAbs]
  implicit def proxyCollectionFrontCompanion(p: Rep[CollectionFrontCompanionAbs]): CollectionFrontCompanionAbs = {
    proxyOps[CollectionFrontCompanionAbs](p)
  }

  class CollectionFrontCompanionElem extends CompanionElem[CollectionFrontCompanionAbs] {
    lazy val tag = weakTypeTag[CollectionFrontCompanionAbs]
    protected def getDefaultRep = CollectionFront
  }
  implicit lazy val CollectionFrontCompanionElem: CollectionFrontCompanionElem = new CollectionFrontCompanionElem

  implicit def proxyCollectionFront(p: Rep[CollectionFront]): CollectionFront =
    proxyOps[CollectionFront](p)

  implicit class ExtendedCollectionFront(p: Rep[CollectionFront]) {
    def toData: Rep[CollectionFrontData] = isoCollectionFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoCollectionFront: Iso[CollectionFrontData, CollectionFront] =
    new CollectionFrontIso

  // 6) smart constructor and deconstructor
  def mkCollectionFront(set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront]
  def unmkCollectionFront(p: Rep[CollectionFront]): Option[(Rep[Collection[Int]], Rep[BitSet])]

  // elem for concrete class
  class MapBasedFrontElem(val iso: Iso[MapBasedFrontData, MapBasedFront])
    extends FrontElem[MapBasedFront]
    with ViewElem[MapBasedFrontData, MapBasedFront] {
    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to MapBasedFront: missing fields List(mmap)")
    override def getDefaultRep = super[ViewElem].getDefaultRep
    override lazy val tag = super[ViewElem].tag
  }

  // state representation type
  type MapBasedFrontData = MMap[Int,Unit]

  // 3) Iso for concrete class
  class MapBasedFrontIso
    extends Iso[MapBasedFrontData, MapBasedFront] {
    override def from(p: Rep[MapBasedFront]) =
      unmkMapBasedFront(p) match {
        case Some((mmap)) => mmap
        case None => !!!
      }
    override def to(p: Rep[MMap[Int,Unit]]) = {
      val mmap = p
      MapBasedFront(mmap)
    }
    lazy val tag = {
      weakTypeTag[MapBasedFront]
    }
    lazy val defaultRepTo = Default.defaultVal[Rep[MapBasedFront]](MapBasedFront(element[MMap[Int,Unit]].defaultRepValue))
    lazy val eTo = new MapBasedFrontElem(this)
  }
  // 4) constructor and deconstructor
  abstract class MapBasedFrontCompanionAbs extends CompanionBase[MapBasedFrontCompanionAbs] with MapBasedFrontCompanion {
    override def toString = "MapBasedFront"

    def apply(mmap: Rep[MMap[Int,Unit]]): Rep[MapBasedFront] =
      mkMapBasedFront(mmap)
    def unapply(p: Rep[MapBasedFront]) = unmkMapBasedFront(p)
  }
  def MapBasedFront: Rep[MapBasedFrontCompanionAbs]
  implicit def proxyMapBasedFrontCompanion(p: Rep[MapBasedFrontCompanionAbs]): MapBasedFrontCompanionAbs = {
    proxyOps[MapBasedFrontCompanionAbs](p)
  }

  class MapBasedFrontCompanionElem extends CompanionElem[MapBasedFrontCompanionAbs] {
    lazy val tag = weakTypeTag[MapBasedFrontCompanionAbs]
    protected def getDefaultRep = MapBasedFront
  }
  implicit lazy val MapBasedFrontCompanionElem: MapBasedFrontCompanionElem = new MapBasedFrontCompanionElem

  implicit def proxyMapBasedFront(p: Rep[MapBasedFront]): MapBasedFront =
    proxyOps[MapBasedFront](p)

  implicit class ExtendedMapBasedFront(p: Rep[MapBasedFront]) {
    def toData: Rep[MapBasedFrontData] = isoMapBasedFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoMapBasedFront: Iso[MapBasedFrontData, MapBasedFront] =
    new MapBasedFrontIso

  // 6) smart constructor and deconstructor
  def mkMapBasedFront(mmap: Rep[MMap[Int,Unit]]): Rep[MapBasedFront]
  def unmkMapBasedFront(p: Rep[MapBasedFront]): Option[(Rep[MMap[Int,Unit]])]
}

// Seq -----------------------------------
trait FrontsSeq extends FrontsDsl with ScalanSeq {
  self: FrontsDslSeq =>
  lazy val Front: Rep[FrontCompanionAbs] = new FrontCompanionAbs with UserTypeSeq[FrontCompanionAbs, FrontCompanionAbs] {
    lazy val selfType = element[FrontCompanionAbs]
  }

  case class SeqBaseFront
      (override val set: Rep[BaseCollection[Int]], override val bits: Rep[BitSet])

    extends BaseFront(set, bits)
        with UserTypeSeq[Front, BaseFront] {
    lazy val selfType = element[BaseFront].asInstanceOf[Elem[Front]]
  }
  lazy val BaseFront = new BaseFrontCompanionAbs with UserTypeSeq[BaseFrontCompanionAbs, BaseFrontCompanionAbs] {
    lazy val selfType = element[BaseFrontCompanionAbs]
  }

  def mkBaseFront
      (set: Rep[BaseCollection[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
      new SeqBaseFront(set, bits)
  def unmkBaseFront(p: Rep[BaseFront]) =
    Some((p.set, p.bits))

  case class SeqListFront
      (override val set: Rep[ListCollection[Int]], override val bits: Rep[BitSet])

    extends ListFront(set, bits)
        with UserTypeSeq[Front, ListFront] {
    lazy val selfType = element[ListFront].asInstanceOf[Elem[Front]]
  }
  lazy val ListFront = new ListFrontCompanionAbs with UserTypeSeq[ListFrontCompanionAbs, ListFrontCompanionAbs] {
    lazy val selfType = element[ListFrontCompanionAbs]
  }

  def mkListFront
      (set: Rep[ListCollection[Int]], bits: Rep[BitSet]): Rep[ListFront] =
      new SeqListFront(set, bits)
  def unmkListFront(p: Rep[ListFront]) =
    Some((p.set, p.bits))

  case class SeqCollectionFront
      (override val set: Rep[Collection[Int]], override val bits: Rep[BitSet])

    extends CollectionFront(set, bits)
        with UserTypeSeq[Front, CollectionFront] {
    lazy val selfType = element[CollectionFront].asInstanceOf[Elem[Front]]
  }
  lazy val CollectionFront = new CollectionFrontCompanionAbs with UserTypeSeq[CollectionFrontCompanionAbs, CollectionFrontCompanionAbs] {
    lazy val selfType = element[CollectionFrontCompanionAbs]
  }

  def mkCollectionFront
      (set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront] =
      new SeqCollectionFront(set, bits)
  def unmkCollectionFront(p: Rep[CollectionFront]) =
    Some((p.set, p.bits))

  case class SeqMapBasedFront
      (override val mmap: Rep[MMap[Int,Unit]])

    extends MapBasedFront(mmap)
        with UserTypeSeq[Front, MapBasedFront] {
    lazy val selfType = element[MapBasedFront].asInstanceOf[Elem[Front]]
  }
  lazy val MapBasedFront = new MapBasedFrontCompanionAbs with UserTypeSeq[MapBasedFrontCompanionAbs, MapBasedFrontCompanionAbs] {
    lazy val selfType = element[MapBasedFrontCompanionAbs]
  }

  def mkMapBasedFront
      (mmap: Rep[MMap[Int,Unit]]): Rep[MapBasedFront] =
      new SeqMapBasedFront(mmap)
  def unmkMapBasedFront(p: Rep[MapBasedFront]) =
    Some((p.mmap))
}

// Exp -----------------------------------
trait FrontsExp extends FrontsDsl with ScalanExp {
  self: FrontsDslExp =>
  lazy val Front: Rep[FrontCompanionAbs] = new FrontCompanionAbs with UserTypeDef[FrontCompanionAbs, FrontCompanionAbs] {
    lazy val selfType = element[FrontCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  case class ExpBaseFront
      (override val set: Rep[BaseCollection[Int]], override val bits: Rep[BitSet])

    extends BaseFront(set, bits) with UserTypeDef[Front, BaseFront] {
    lazy val selfType = element[BaseFront].asInstanceOf[Elem[Front]]
    override def mirror(t: Transformer) = ExpBaseFront(t(set), t(bits))
  }

  lazy val BaseFront: Rep[BaseFrontCompanionAbs] = new BaseFrontCompanionAbs with UserTypeDef[BaseFrontCompanionAbs, BaseFrontCompanionAbs] {
    lazy val selfType = element[BaseFrontCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object BaseFrontMethods {
    object contains {
      def unapply(d: Def[_]): Option[(Rep[BaseFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[BaseFrontElem] && method.getName == "contains" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[BaseFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[BaseFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object append {
      def unapply(d: Def[_]): Option[(Rep[BaseFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[BaseFrontElem] && method.getName == "append" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[BaseFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[BaseFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object BaseFrontCompanionMethods {
  }

  def mkBaseFront
    (set: Rep[BaseCollection[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
    new ExpBaseFront(set, bits)
  def unmkBaseFront(p: Rep[BaseFront]) =
    Some((p.set, p.bits))

  case class ExpListFront
      (override val set: Rep[ListCollection[Int]], override val bits: Rep[BitSet])

    extends ListFront(set, bits) with UserTypeDef[Front, ListFront] {
    lazy val selfType = element[ListFront].asInstanceOf[Elem[Front]]
    override def mirror(t: Transformer) = ExpListFront(t(set), t(bits))
  }

  lazy val ListFront: Rep[ListFrontCompanionAbs] = new ListFrontCompanionAbs with UserTypeDef[ListFrontCompanionAbs, ListFrontCompanionAbs] {
    lazy val selfType = element[ListFrontCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object ListFrontMethods {
    object contains {
      def unapply(d: Def[_]): Option[(Rep[ListFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[ListFrontElem] && method.getName == "contains" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[ListFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[ListFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object append {
      def unapply(d: Def[_]): Option[(Rep[ListFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[ListFrontElem] && method.getName == "append" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[ListFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[ListFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object ListFrontCompanionMethods {
  }

  def mkListFront
    (set: Rep[ListCollection[Int]], bits: Rep[BitSet]): Rep[ListFront] =
    new ExpListFront(set, bits)
  def unmkListFront(p: Rep[ListFront]) =
    Some((p.set, p.bits))

  case class ExpCollectionFront
      (override val set: Rep[Collection[Int]], override val bits: Rep[BitSet])

    extends CollectionFront(set, bits) with UserTypeDef[Front, CollectionFront] {
    lazy val selfType = element[CollectionFront].asInstanceOf[Elem[Front]]
    override def mirror(t: Transformer) = ExpCollectionFront(t(set), t(bits))
  }

  lazy val CollectionFront: Rep[CollectionFrontCompanionAbs] = new CollectionFrontCompanionAbs with UserTypeDef[CollectionFrontCompanionAbs, CollectionFrontCompanionAbs] {
    lazy val selfType = element[CollectionFrontCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object CollectionFrontMethods {
    object contains {
      def unapply(d: Def[_]): Option[(Rep[CollectionFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[CollectionFrontElem] && method.getName == "contains" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[CollectionFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[CollectionFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object append {
      def unapply(d: Def[_]): Option[(Rep[CollectionFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[CollectionFrontElem] && method.getName == "append" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[CollectionFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[CollectionFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object CollectionFrontCompanionMethods {
  }

  def mkCollectionFront
    (set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront] =
    new ExpCollectionFront(set, bits)
  def unmkCollectionFront(p: Rep[CollectionFront]) =
    Some((p.set, p.bits))

  case class ExpMapBasedFront
      (override val mmap: Rep[MMap[Int,Unit]])

    extends MapBasedFront(mmap) with UserTypeDef[Front, MapBasedFront] {
    lazy val selfType = element[MapBasedFront].asInstanceOf[Elem[Front]]
    override def mirror(t: Transformer) = ExpMapBasedFront(t(mmap))
  }

  lazy val MapBasedFront: Rep[MapBasedFrontCompanionAbs] = new MapBasedFrontCompanionAbs with UserTypeDef[MapBasedFrontCompanionAbs, MapBasedFrontCompanionAbs] {
    lazy val selfType = element[MapBasedFrontCompanionAbs]
    override def mirror(t: Transformer) = this
  }

  object MapBasedFrontMethods {
    object contains {
      def unapply(d: Def[_]): Option[(Rep[MapBasedFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[MapBasedFrontElem] && method.getName == "contains" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[MapBasedFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[MapBasedFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object append {
      def unapply(d: Def[_]): Option[(Rep[MapBasedFront], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[MapBasedFrontElem] && method.getName == "append" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[MapBasedFront], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[MapBasedFront], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object set {
      def unapply(d: Def[_]): Option[Rep[MapBasedFront]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[MapBasedFrontElem] && method.getName == "set" =>
          Some(receiver).asInstanceOf[Option[Rep[MapBasedFront]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[MapBasedFront]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object MapBasedFrontCompanionMethods {
  }

  def mkMapBasedFront
    (mmap: Rep[MMap[Int,Unit]]): Rep[MapBasedFront] =
    new ExpMapBasedFront(mmap)
  def unmkMapBasedFront(p: Rep[MapBasedFront]) =
    Some((p.mmap))

  object FrontMethods {
    object contains {
      def unapply(d: Def[_]): Option[(Rep[Front], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[FrontElem[_]] && method.getName == "contains" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[Front], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Front], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object append {
      def unapply(d: Def[_]): Option[(Rep[Front], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(v, _*), _) if receiver.elem.isInstanceOf[FrontElem[_]] && method.getName == "append" =>
          Some((receiver, v)).asInstanceOf[Option[(Rep[Front], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Front], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object set {
      def unapply(d: Def[_]): Option[Rep[Front]] = d match {
        case MethodCall(receiver, method, _, _) if receiver.elem.isInstanceOf[FrontElem[_]] && method.getName == "set" =>
          Some(receiver).asInstanceOf[Option[Rep[Front]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Front]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }

  object FrontCompanionMethods {
    object emptyBaseFront {
      def unapply(d: Def[_]): Option[Rep[Int]] = d match {
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "emptyBaseFront" =>
          Some(len).asInstanceOf[Option[Rep[Int]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Int]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object emptyListBasedFront {
      def unapply(d: Def[_]): Option[Rep[Int]] = d match {
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "emptyListBasedFront" =>
          Some(len).asInstanceOf[Option[Rep[Int]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Int]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object emptyCollBasedFront {
      def unapply(d: Def[_]): Option[Rep[Int]] = d match {
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "emptyCollBasedFront" =>
          Some(len).asInstanceOf[Option[Rep[Int]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Int]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object emptyMapBasedFront {
      def unapply(d: Def[_]): Option[Rep[Int]] = d match {
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "emptyMapBasedFront" =>
          Some(len).asInstanceOf[Option[Rep[Int]]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[Rep[Int]] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object fromStartNode {
      def unapply(d: Def[_]): Option[(Rep[Int], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(start, len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "fromStartNode" =>
          Some((start, len)).asInstanceOf[Option[(Rep[Int], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Int], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }

    object fromStartNodeMap {
      def unapply(d: Def[_]): Option[(Rep[Int], Rep[Int])] = d match {
        case MethodCall(receiver, method, Seq(start, len, _*), _) if receiver.elem.isInstanceOf[FrontCompanionElem] && method.getName == "fromStartNodeMap" =>
          Some((start, len)).asInstanceOf[Option[(Rep[Int], Rep[Int])]]
        case _ => None
      }
      def unapply(exp: Exp[_]): Option[(Rep[Int], Rep[Int])] = exp match {
        case Def(d) => unapply(d)
        case _ => None
      }
    }
  }
}
