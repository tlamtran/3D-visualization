package threeD
import scala.math._

class DDDpoint(xInput: Double, yInput: Double, zInput: Double) {
  var x = xInput
  var y = yInput
  var z = zInput

  // vector from this to point to point p i.e. A->(B) = vector AB
  def ->(p: DDDpoint): DDDvector = new DDDvector(p.x - this.x, p.y - this.y, p.z - this.z, true)

  def addVector(v: DDDvector) = new DDDpoint(this.x + v.x, this.y + v.y , this.z + v.z)


  def subVector(v: DDDvector) = new DDDpoint(this.x - v.x, this.y - v.y , this.z - v.z)


  def distance(p: DDDpoint) = sqrt(pow(p.x - this.x,2) + pow(p.y - this.y,2) + pow(p.z - this.z,2))


  def toVector: DDDvector = new DDDvector(this.x, this.y, this.z, false)
}
