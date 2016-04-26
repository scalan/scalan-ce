package scalan.graphs

import scalan.collections.{BitSetsDsl, BitSetsDslExp, BitSetsDslStd, CollectionsDsl}
import scalan._
import scala.reflect.runtime.universe.{WeakTypeTag, weakTypeTag}
import scalan.meta.ScalanAst._

package impl {
// Abs -----------------------------------
trait FrontsAbs extends scalan.ScalanDsl with Fronts {
  self: FrontsDsl =>

  // single proxy for each type family
  implicit def proxyFront(p: Rep[Front]): Front = {
    proxyOps[Front](p)(scala.reflect.classTag[Front])
  }

  // familyElem
  class FrontElem[To <: Front]
    extends EntityElem[To] {
    lazy val parent: Option[Elem[_]] = None
    lazy val tyArgSubst: Map[String, TypeDesc] = {
      Map()
    }
    override def isEntityType = true
    override lazy val tag = {
      weakTypeTag[Front].asInstanceOf[WeakTypeTag[To]]
    }
    override def convert(x: Rep[Def[_]]) = {
      implicit val eTo: Elem[To] = this
      val conv = fun {x: Rep[Front] => convertFront(x) }
      tryConvert(element[Front], this, x, conv)
    }

    def convertFront(x: Rep[Front]): Rep[To] = {
      x.selfType1 match {
        case _: FrontElem[_] => x.asRep[To]
        case e => !!!(s"Expected $x to have FrontElem[_], but got $e", x)
      }
    }

    override def getDefaultRep: Rep[To] = ???
  }

  implicit def frontElement: Elem[Front] =
    cachedElem[FrontElem[Front]]()

  implicit case object FrontCompanionElem extends CompanionElem[FrontCompanionAbs] {
    lazy val tag = weakTypeTag[FrontCompanionAbs]
    protected def getDefaultRep = Front
  }

  abstract class FrontCompanionAbs extends CompanionDef[FrontCompanionAbs] with FrontCompanion {
    def selfType = FrontCompanionElem
    override def toString = "Front"
  }
  def Front: Rep[FrontCompanionAbs]
  implicit def proxyFrontCompanionAbs(p: Rep[FrontCompanionAbs]): FrontCompanionAbs =
    proxyOps[FrontCompanionAbs](p)

  abstract class AbsBaseFront
      (set: Rep[CollectionOverArray[Int]], bits: Rep[BitSet])
    extends BaseFront(set, bits) with Def[BaseFront] {
    lazy val selfType = element[BaseFront]
  }
  // elem for concrete class
  class BaseFrontElem(val iso: Iso[BaseFrontData, BaseFront])
    extends FrontElem[BaseFront]
    with ConcreteElem[BaseFrontData, BaseFront] {
    override lazy val parent: Option[Elem[_]] = Some(frontElement)
    override lazy val tyArgSubst: Map[String, TypeDesc] = {
      Map()
    }

    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to BaseFront: missing fields List(bits)")
    override def getDefaultRep = BaseFront(element[CollectionOverArray[Int]].defaultRepValue, element[BitSet].defaultRepValue)
    override lazy val tag = {
      weakTypeTag[BaseFront]
    }
  }

  // state representation type
  type BaseFrontData = (CollectionOverArray[Int], BitSet)

  // 3) Iso for concrete class
  class BaseFrontIso
    extends EntityIso[BaseFrontData, BaseFront] with Def[BaseFrontIso] {
    override def from(p: Rep[BaseFront]) =
      (p.set, p.bits)
    override def to(p: Rep[(CollectionOverArray[Int], BitSet)]) = {
      val Pair(set, bits) = p
      BaseFront(set, bits)
    }
    lazy val eFrom = pairElement(element[CollectionOverArray[Int]], element[BitSet])
    lazy val eTo = new BaseFrontElem(self)
    lazy val selfType = new BaseFrontIsoElem
    def productArity = 0
    def productElement(n: Int) = ???
  }
  case class BaseFrontIsoElem() extends Elem[BaseFrontIso] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new BaseFrontIso())
    lazy val tag = {
      weakTypeTag[BaseFrontIso]
    }
  }
  // 4) constructor and deconstructor
  class BaseFrontCompanionAbs extends CompanionDef[BaseFrontCompanionAbs] with BaseFrontCompanion {
    def selfType = BaseFrontCompanionElem
    override def toString = "BaseFront"
    @scalan.OverloadId("fromData")
    def apply(p: Rep[BaseFrontData]): Rep[BaseFront] =
      isoBaseFront.to(p)
    @scalan.OverloadId("fromFields")
    def apply(set: Rep[CollectionOverArray[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
      mkBaseFront(set, bits)

    def unapply(p: Rep[Front]) = unmkBaseFront(p)
  }
  lazy val BaseFrontRep: Rep[BaseFrontCompanionAbs] = new BaseFrontCompanionAbs
  lazy val BaseFront: BaseFrontCompanionAbs = proxyBaseFrontCompanion(BaseFrontRep)
  implicit def proxyBaseFrontCompanion(p: Rep[BaseFrontCompanionAbs]): BaseFrontCompanionAbs = {
    proxyOps[BaseFrontCompanionAbs](p)
  }

  implicit case object BaseFrontCompanionElem extends CompanionElem[BaseFrontCompanionAbs] {
    lazy val tag = weakTypeTag[BaseFrontCompanionAbs]
    protected def getDefaultRep = BaseFront
  }

  implicit def proxyBaseFront(p: Rep[BaseFront]): BaseFront =
    proxyOps[BaseFront](p)

  implicit class ExtendedBaseFront(p: Rep[BaseFront]) {
    def toData: Rep[BaseFrontData] = isoBaseFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoBaseFront: Iso[BaseFrontData, BaseFront] =
    reifyObject(new BaseFrontIso())

  // 6) smart constructor and deconstructor
  def mkBaseFront(set: Rep[CollectionOverArray[Int]], bits: Rep[BitSet]): Rep[BaseFront]
  def unmkBaseFront(p: Rep[Front]): Option[(Rep[CollectionOverArray[Int]], Rep[BitSet])]

  abstract class AbsListFront
      (set: Rep[CollectionOverList[Int]], bits: Rep[BitSet])
    extends ListFront(set, bits) with Def[ListFront] {
    lazy val selfType = element[ListFront]
  }
  // elem for concrete class
  class ListFrontElem(val iso: Iso[ListFrontData, ListFront])
    extends FrontElem[ListFront]
    with ConcreteElem[ListFrontData, ListFront] {
    override lazy val parent: Option[Elem[_]] = Some(frontElement)
    override lazy val tyArgSubst: Map[String, TypeDesc] = {
      Map()
    }

    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to ListFront: missing fields List(bits)")
    override def getDefaultRep = ListFront(element[CollectionOverList[Int]].defaultRepValue, element[BitSet].defaultRepValue)
    override lazy val tag = {
      weakTypeTag[ListFront]
    }
  }

  // state representation type
  type ListFrontData = (CollectionOverList[Int], BitSet)

  // 3) Iso for concrete class
  class ListFrontIso
    extends EntityIso[ListFrontData, ListFront] with Def[ListFrontIso] {
    override def from(p: Rep[ListFront]) =
      (p.set, p.bits)
    override def to(p: Rep[(CollectionOverList[Int], BitSet)]) = {
      val Pair(set, bits) = p
      ListFront(set, bits)
    }
    lazy val eFrom = pairElement(element[CollectionOverList[Int]], element[BitSet])
    lazy val eTo = new ListFrontElem(self)
    lazy val selfType = new ListFrontIsoElem
    def productArity = 0
    def productElement(n: Int) = ???
  }
  case class ListFrontIsoElem() extends Elem[ListFrontIso] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new ListFrontIso())
    lazy val tag = {
      weakTypeTag[ListFrontIso]
    }
  }
  // 4) constructor and deconstructor
  class ListFrontCompanionAbs extends CompanionDef[ListFrontCompanionAbs] with ListFrontCompanion {
    def selfType = ListFrontCompanionElem
    override def toString = "ListFront"
    @scalan.OverloadId("fromData")
    def apply(p: Rep[ListFrontData]): Rep[ListFront] =
      isoListFront.to(p)
    @scalan.OverloadId("fromFields")
    def apply(set: Rep[CollectionOverList[Int]], bits: Rep[BitSet]): Rep[ListFront] =
      mkListFront(set, bits)

    def unapply(p: Rep[Front]) = unmkListFront(p)
  }
  lazy val ListFrontRep: Rep[ListFrontCompanionAbs] = new ListFrontCompanionAbs
  lazy val ListFront: ListFrontCompanionAbs = proxyListFrontCompanion(ListFrontRep)
  implicit def proxyListFrontCompanion(p: Rep[ListFrontCompanionAbs]): ListFrontCompanionAbs = {
    proxyOps[ListFrontCompanionAbs](p)
  }

  implicit case object ListFrontCompanionElem extends CompanionElem[ListFrontCompanionAbs] {
    lazy val tag = weakTypeTag[ListFrontCompanionAbs]
    protected def getDefaultRep = ListFront
  }

  implicit def proxyListFront(p: Rep[ListFront]): ListFront =
    proxyOps[ListFront](p)

  implicit class ExtendedListFront(p: Rep[ListFront]) {
    def toData: Rep[ListFrontData] = isoListFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoListFront: Iso[ListFrontData, ListFront] =
    reifyObject(new ListFrontIso())

  // 6) smart constructor and deconstructor
  def mkListFront(set: Rep[CollectionOverList[Int]], bits: Rep[BitSet]): Rep[ListFront]
  def unmkListFront(p: Rep[Front]): Option[(Rep[CollectionOverList[Int]], Rep[BitSet])]

  abstract class AbsCollectionFront
      (set: Rep[Collection[Int]], bits: Rep[BitSet])
    extends CollectionFront(set, bits) with Def[CollectionFront] {
    lazy val selfType = element[CollectionFront]
  }
  // elem for concrete class
  class CollectionFrontElem(val iso: Iso[CollectionFrontData, CollectionFront])
    extends FrontElem[CollectionFront]
    with ConcreteElem[CollectionFrontData, CollectionFront] {
    override lazy val parent: Option[Elem[_]] = Some(frontElement)
    override lazy val tyArgSubst: Map[String, TypeDesc] = {
      Map()
    }

    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to CollectionFront: missing fields List(bits)")
    override def getDefaultRep = CollectionFront(element[Collection[Int]].defaultRepValue, element[BitSet].defaultRepValue)
    override lazy val tag = {
      weakTypeTag[CollectionFront]
    }
  }

  // state representation type
  type CollectionFrontData = (Collection[Int], BitSet)

  // 3) Iso for concrete class
  class CollectionFrontIso
    extends EntityIso[CollectionFrontData, CollectionFront] with Def[CollectionFrontIso] {
    override def from(p: Rep[CollectionFront]) =
      (p.set, p.bits)
    override def to(p: Rep[(Collection[Int], BitSet)]) = {
      val Pair(set, bits) = p
      CollectionFront(set, bits)
    }
    lazy val eFrom = pairElement(element[Collection[Int]], element[BitSet])
    lazy val eTo = new CollectionFrontElem(self)
    lazy val selfType = new CollectionFrontIsoElem
    def productArity = 0
    def productElement(n: Int) = ???
  }
  case class CollectionFrontIsoElem() extends Elem[CollectionFrontIso] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new CollectionFrontIso())
    lazy val tag = {
      weakTypeTag[CollectionFrontIso]
    }
  }
  // 4) constructor and deconstructor
  class CollectionFrontCompanionAbs extends CompanionDef[CollectionFrontCompanionAbs] with CollectionFrontCompanion {
    def selfType = CollectionFrontCompanionElem
    override def toString = "CollectionFront"
    @scalan.OverloadId("fromData")
    def apply(p: Rep[CollectionFrontData]): Rep[CollectionFront] =
      isoCollectionFront.to(p)
    @scalan.OverloadId("fromFields")
    def apply(set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront] =
      mkCollectionFront(set, bits)

    def unapply(p: Rep[Front]) = unmkCollectionFront(p)
  }
  lazy val CollectionFrontRep: Rep[CollectionFrontCompanionAbs] = new CollectionFrontCompanionAbs
  lazy val CollectionFront: CollectionFrontCompanionAbs = proxyCollectionFrontCompanion(CollectionFrontRep)
  implicit def proxyCollectionFrontCompanion(p: Rep[CollectionFrontCompanionAbs]): CollectionFrontCompanionAbs = {
    proxyOps[CollectionFrontCompanionAbs](p)
  }

  implicit case object CollectionFrontCompanionElem extends CompanionElem[CollectionFrontCompanionAbs] {
    lazy val tag = weakTypeTag[CollectionFrontCompanionAbs]
    protected def getDefaultRep = CollectionFront
  }

  implicit def proxyCollectionFront(p: Rep[CollectionFront]): CollectionFront =
    proxyOps[CollectionFront](p)

  implicit class ExtendedCollectionFront(p: Rep[CollectionFront]) {
    def toData: Rep[CollectionFrontData] = isoCollectionFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoCollectionFront: Iso[CollectionFrontData, CollectionFront] =
    reifyObject(new CollectionFrontIso())

  // 6) smart constructor and deconstructor
  def mkCollectionFront(set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront]
  def unmkCollectionFront(p: Rep[Front]): Option[(Rep[Collection[Int]], Rep[BitSet])]

  abstract class AbsMapBasedFront
      (mmap: Rep[MMap[Int, Unit]])
    extends MapBasedFront(mmap) with Def[MapBasedFront] {
    lazy val selfType = element[MapBasedFront]
  }
  // elem for concrete class
  class MapBasedFrontElem(val iso: Iso[MapBasedFrontData, MapBasedFront])
    extends FrontElem[MapBasedFront]
    with ConcreteElem[MapBasedFrontData, MapBasedFront] {
    override lazy val parent: Option[Elem[_]] = Some(frontElement)
    override lazy val tyArgSubst: Map[String, TypeDesc] = {
      Map()
    }

    override def convertFront(x: Rep[Front]) = // Converter is not generated by meta
!!!("Cannot convert from Front to MapBasedFront: missing fields List(mmap)")
    override def getDefaultRep = MapBasedFront(element[MMap[Int, Unit]].defaultRepValue)
    override lazy val tag = {
      weakTypeTag[MapBasedFront]
    }
  }

  // state representation type
  type MapBasedFrontData = MMap[Int, Unit]

  // 3) Iso for concrete class
  class MapBasedFrontIso
    extends EntityIso[MapBasedFrontData, MapBasedFront] with Def[MapBasedFrontIso] {
    override def from(p: Rep[MapBasedFront]) =
      p.mmap
    override def to(p: Rep[MMap[Int, Unit]]) = {
      val mmap = p
      MapBasedFront(mmap)
    }
    lazy val eFrom = element[MMap[Int, Unit]]
    lazy val eTo = new MapBasedFrontElem(self)
    lazy val selfType = new MapBasedFrontIsoElem
    def productArity = 0
    def productElement(n: Int) = ???
  }
  case class MapBasedFrontIsoElem() extends Elem[MapBasedFrontIso] {
    def isEntityType = true
    def getDefaultRep = reifyObject(new MapBasedFrontIso())
    lazy val tag = {
      weakTypeTag[MapBasedFrontIso]
    }
  }
  // 4) constructor and deconstructor
  class MapBasedFrontCompanionAbs extends CompanionDef[MapBasedFrontCompanionAbs] with MapBasedFrontCompanion {
    def selfType = MapBasedFrontCompanionElem
    override def toString = "MapBasedFront"

    @scalan.OverloadId("fromFields")
    def apply(mmap: Rep[MMap[Int, Unit]]): Rep[MapBasedFront] =
      mkMapBasedFront(mmap)

    def unapply(p: Rep[Front]) = unmkMapBasedFront(p)
  }
  lazy val MapBasedFrontRep: Rep[MapBasedFrontCompanionAbs] = new MapBasedFrontCompanionAbs
  lazy val MapBasedFront: MapBasedFrontCompanionAbs = proxyMapBasedFrontCompanion(MapBasedFrontRep)
  implicit def proxyMapBasedFrontCompanion(p: Rep[MapBasedFrontCompanionAbs]): MapBasedFrontCompanionAbs = {
    proxyOps[MapBasedFrontCompanionAbs](p)
  }

  implicit case object MapBasedFrontCompanionElem extends CompanionElem[MapBasedFrontCompanionAbs] {
    lazy val tag = weakTypeTag[MapBasedFrontCompanionAbs]
    protected def getDefaultRep = MapBasedFront
  }

  implicit def proxyMapBasedFront(p: Rep[MapBasedFront]): MapBasedFront =
    proxyOps[MapBasedFront](p)

  implicit class ExtendedMapBasedFront(p: Rep[MapBasedFront]) {
    def toData: Rep[MapBasedFrontData] = isoMapBasedFront.from(p)
  }

  // 5) implicit resolution of Iso
  implicit def isoMapBasedFront: Iso[MapBasedFrontData, MapBasedFront] =
    reifyObject(new MapBasedFrontIso())

  // 6) smart constructor and deconstructor
  def mkMapBasedFront(mmap: Rep[MMap[Int, Unit]]): Rep[MapBasedFront]
  def unmkMapBasedFront(p: Rep[Front]): Option[(Rep[MMap[Int, Unit]])]

  registerModule(Fronts_Module)
}

// Std -----------------------------------
trait FrontsStd extends scalan.ScalanDslStd with FrontsDsl {
  self: FrontsDslStd =>
  lazy val Front: Rep[FrontCompanionAbs] = new FrontCompanionAbs {
  }

  case class StdBaseFront
      (override val set: Rep[CollectionOverArray[Int]], override val bits: Rep[BitSet])
    extends AbsBaseFront(set, bits) {
  }

  def mkBaseFront
    (set: Rep[CollectionOverArray[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
    new StdBaseFront(set, bits)
  def unmkBaseFront(p: Rep[Front]) = p match {
    case p: BaseFront @unchecked =>
      Some((p.set, p.bits))
    case _ => None
  }

  case class StdListFront
      (override val set: Rep[CollectionOverList[Int]], override val bits: Rep[BitSet])
    extends AbsListFront(set, bits) {
  }

  def mkListFront
    (set: Rep[CollectionOverList[Int]], bits: Rep[BitSet]): Rep[ListFront] =
    new StdListFront(set, bits)
  def unmkListFront(p: Rep[Front]) = p match {
    case p: ListFront @unchecked =>
      Some((p.set, p.bits))
    case _ => None
  }

  case class StdCollectionFront
      (override val set: Rep[Collection[Int]], override val bits: Rep[BitSet])
    extends AbsCollectionFront(set, bits) {
  }

  def mkCollectionFront
    (set: Rep[Collection[Int]], bits: Rep[BitSet]): Rep[CollectionFront] =
    new StdCollectionFront(set, bits)
  def unmkCollectionFront(p: Rep[Front]) = p match {
    case p: CollectionFront @unchecked =>
      Some((p.set, p.bits))
    case _ => None
  }

  case class StdMapBasedFront
      (override val mmap: Rep[MMap[Int, Unit]])
    extends AbsMapBasedFront(mmap) {
  }

  def mkMapBasedFront
    (mmap: Rep[MMap[Int, Unit]]): Rep[MapBasedFront] =
    new StdMapBasedFront(mmap)
  def unmkMapBasedFront(p: Rep[Front]) = p match {
    case p: MapBasedFront @unchecked =>
      Some((p.mmap))
    case _ => None
  }
}

// Exp -----------------------------------
trait FrontsExp extends scalan.ScalanDslExp with FrontsDsl {
  self: FrontsDslExp =>
  lazy val Front: Rep[FrontCompanionAbs] = new FrontCompanionAbs {
  }

  case class ExpBaseFront
      (override val set: Rep[CollectionOverArray[Int]], override val bits: Rep[BitSet])
    extends AbsBaseFront(set, bits)

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
    (set: Rep[CollectionOverArray[Int]], bits: Rep[BitSet]): Rep[BaseFront] =
    new ExpBaseFront(set, bits)
  def unmkBaseFront(p: Rep[Front]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: BaseFrontElem @unchecked =>
      Some((p.asRep[BaseFront].set, p.asRep[BaseFront].bits))
    case _ =>
      None
  }

  case class ExpListFront
      (override val set: Rep[CollectionOverList[Int]], override val bits: Rep[BitSet])
    extends AbsListFront(set, bits)

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
    (set: Rep[CollectionOverList[Int]], bits: Rep[BitSet]): Rep[ListFront] =
    new ExpListFront(set, bits)
  def unmkListFront(p: Rep[Front]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: ListFrontElem @unchecked =>
      Some((p.asRep[ListFront].set, p.asRep[ListFront].bits))
    case _ =>
      None
  }

  case class ExpCollectionFront
      (override val set: Rep[Collection[Int]], override val bits: Rep[BitSet])
    extends AbsCollectionFront(set, bits)

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
  def unmkCollectionFront(p: Rep[Front]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: CollectionFrontElem @unchecked =>
      Some((p.asRep[CollectionFront].set, p.asRep[CollectionFront].bits))
    case _ =>
      None
  }

  case class ExpMapBasedFront
      (override val mmap: Rep[MMap[Int, Unit]])
    extends AbsMapBasedFront(mmap)

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
    (mmap: Rep[MMap[Int, Unit]]): Rep[MapBasedFront] =
    new ExpMapBasedFront(mmap)
  def unmkMapBasedFront(p: Rep[Front]) = p.elem.asInstanceOf[Elem[_]] match {
    case _: MapBasedFrontElem @unchecked =>
      Some((p.asRep[MapBasedFront].mmap))
    case _ =>
      None
  }

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
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "emptyBaseFront" =>
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
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "emptyListBasedFront" =>
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
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "emptyCollBasedFront" =>
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
        case MethodCall(receiver, method, Seq(len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "emptyMapBasedFront" =>
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
        case MethodCall(receiver, method, Seq(start, len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "fromStartNode" =>
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
        case MethodCall(receiver, method, Seq(start, len, _*), _) if receiver.elem == FrontCompanionElem && method.getName == "fromStartNodeMap" =>
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

object Fronts_Module extends scalan.ModuleInfo {
  val dump = "H4sIAAAAAAAAAN1XX4gbRRif/LnLbXK9nimV9l6s16it2iRYpA+HlGsuJ5VccnR7VmJR5nbnclv3zzgzdyQ+tNCHPqhPIn2qDwVFkCKIj4IIWhARwVJE8NmnqpQ+2CfFb2Y3m92YPaNwFMzDMNn59vvz+/2+mdkbv6IJztDj3MA2dssOEbisq/kiFyW97gpL9FY8c8smS2Tj+6e+8PKX326m0WwbTW5ivsTtNtL8Sb1Lw7kuzAbSsGsQLjzGBXq0oSJUDM+2iSEsz61YjrMl8LpNKg2Li4UGyq57Zu91dBGlGmjW8FyDEUH0mo05Jzx4PkVkRlb4X1P/ey06iOFWZBWVSBVnGbYEpA8xZn37M4TqPddze45AM0FqLSrTApsC6VKo4bRDbRUm00A5y6EeE/2oOYiw6Zn9v1kXwwNUbFzA27gCUTsVXTDL7UhnFBuv4Q5pgok0z0INnNgbZ3uUBM4LXJixeF2KEAJWnlGJlQeYlUPMyhKzkk6YhW3rDSwXV5nX7SH/l8og1KXg4ul/cNH3QOquWXrzvPHyfb3gpOXLXZlKTiU0CY4eSVCIogew/frMO/ze89dPpFG+jfIWX1zngmFDRGUQwFXArusJlXOIIGYdYHA+iUEVZRFshmSiGZ5DsQueAiyngSjbMiwhjeWz6YCeBOxzgpK+aapLU2G9hxLqVVqqYdtevXPw2GO/1F9Ko3Q8hAYudWgG1ncq0MQy81yhAJWDFmCbHCWs94k7v5k3q+h8OkQpcDoeMeBigv/4Q+H20ZNpNNVWOl62cacNQPG6TZwWq0FmbTTlbRPmr+S2sS1nI4nKmWQDb9kigC9adwbqFuhQYhNSIkFZUOJO9QEo+Ppsei4pLa+Wfte/efeGlB9D0/6K35V/Wif++GlmQyhlCpThxO/FvTCHXg7ACJ7sq4Vqb0FVi4zhXgjX4SReKVlllgN7yzZ59svP1u5+3pxQ1BaDil/E9hbx2zooeFC8zClVhVxO+yz7BGtdFXV/WK8c5gQI2BI8Of3JUxa05bBaBprJ+8DonkMemr9nvXL9LaHUkerGt6DW+gUAYUG9d3AHofR3x0+uXNl/9/1X96kOnoIcHUxL1X/Rv/1228X+RHGoZmrBKaHEXo0vjmw6LbKtFcM1nxkgsXgKc6Leq0UTnxt+SSAttBwyKKTGSCPwFbH8m0p21ngxrnG5lfsGcji8yxqU4zE1VnedEFnZeISElg+EkPyAkP8lEQcG9Y1Fx94h+10iJevALpUMX3ZlJVj2+VDTeYHSR47C4ppriWEAHgC0D0OOcjcxxwJ2T8x6YBQR2eTIvDOwx//3ZGOADD0eUdNMUi2x29DcUOxr6mEQiqFywom1RAwbM2LKGzNx4Ebvn0XHr54898KBc2vqNJw2lZG/El5LRn9/AKIL6rZ8ZIfbMhiV6g6FryGYHP/quVuXvv3wA3UfGVQP/apqg4B7gtw7DNNNHpY0n1CSHhx7wNHF++81n/zu05/V7SMvD1C4HLnht0f01hGnU/Njw6dEBFaQuTxSI/R9JIeP/wKjGHda+Q0AAA=="
}
}

trait FrontsDsl extends impl.FrontsAbs
