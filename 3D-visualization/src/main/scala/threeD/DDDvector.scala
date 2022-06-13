package threeD

import scala.math.sqrt

// automatically normalized vector if length > 0
class DDDvector(xInput: Double, yInput: Double, zInput: Double, n: Boolean) {
  var x = 0.0
  var y = 0.0
  var z = 0.0
  var w = 1.0

  val length = sqrt(xInput * xInput + yInput * yInput + zInput * zInput)

  if (length > 0 && n) {
    x = xInput / length
    y = yInput / length
    z = zInput / length
  }
  else {
    x = xInput
    y = yInput
    z = zInput
  }


  def crossProduct(v: DDDvector): DDDvector = { // returns a new vector in 3D that is perpendicular to both this and v
    new DDDvector(this.y * v.z - this.z * v.y,
                  this.z * v.x - this.x * v.z,
                  this.x * v.y - this.y * v.x, n)
  }


  def dotProduct(v: DDDvector): Double = this.x * v.x + this.y * v.y + this.z * v.z

  def +(v: DDDvector): DDDvector = new DDDvector(this.x + v.x, this.y + v.y, this.z + v.z, true)

  def -(v: DDDvector): DDDvector = new DDDvector(this.x - v.x, this.y - v.y, this.z - v.z, true)

  def scale(s: Double) = new DDDvector(s * this.x, s * this.y, s * this.z, false)



}