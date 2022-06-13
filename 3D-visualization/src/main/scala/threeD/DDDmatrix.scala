package threeD

import scala.math._

// matrices and matrix calculations
class DDDmatrix {


  def createMatProj(fNear: Double, fFar: Double, fFov: Double) = {
    val fFovRad = 1.0 / tan((fFov / 180.0 * Pi) / 2.0)
    val projectionMatrix = Array.ofDim[Double](4, 4)
    projectionMatrix(0)(0) = 720.0 / 720.0 * fFovRad
    projectionMatrix(1)(1) = fFovRad
    projectionMatrix(2)(2) = fFar / (fFar - fNear)
    projectionMatrix(3)(2) = (- fFar * fNear) / (fFar - fNear)
    projectionMatrix(2)(3) = 1.0
    projectionMatrix
  }


  def createMatRotX(x: Double) = {
    val matRotX = Array.ofDim[Double](4, 4)
    matRotX(0)(0) = 1.0
    matRotX(1)(1) = cos(x)
    matRotX(1)(2) = sin(x)
    matRotX(2)(1) = -sin(x)
    matRotX(2)(2) = cos(x)
    matRotX(3)(3) = 1.0
    matRotX
  }


  def createMatRotY(x: Double) = {
    val matRotY = Array.ofDim[Double](4, 4)
    matRotY(0)(0) = cos(x)
    matRotY(0)(2) = sin(x)
    matRotY(2)(0) = -sin(x)
    matRotY(1)(1) = 1.0
    matRotY(2)(2) = cos(x)
    matRotY(3)(3) = 1.0
    matRotY
  }


  def createMatRotZ(x: Double) = {
    val matRotZ = Array.ofDim[Double](4, 4)
    matRotZ(0)(0) = cos(x)
    matRotZ(0)(1) = sin(x)
    matRotZ(1)(0) = -sin(x)
    matRotZ(1)(1) = cos(x)
    matRotZ(2)(2) = 1.0
    matRotZ(3)(3) = 1.0
    matRotZ
  }
    // v(1x4) * m(4x4) => new v(1x4)
  // v = [x ,y, z] but we add an element 1 --> [x, y, z, 1] to turn it
  // https://www.scratchapixel.com/lessons/3d-basic-rendering/perspective-and-orthographic-projection-matrix/building-basic-perspective-projection-matrix
  def multiplyPointMatrix(v: DDDpoint, m: Array[Array[Double]]): DDDpoint = {
    val output = new DDDpoint(0, 0, 0)
    val x = v.x * m(0)(0) + v.y * m(1)(0) + v.z * m(2)(0) + m(3)(0)
    val y = v.x * m(0)(1) + v.y * m(1)(1) + v.z * m(2)(1) + m(3)(1)
    val z = v.x * m(0)(2) + v.y * m(1)(2) + v.z * m(2)(2) + m(3)(2)
    val w = v.x * m(0)(3) + v.y * m(1)(3) + v.z * m(2)(3) + m(3)(3)

    if (w != 0) {
      output.x = x / w
      output.y = y / w
      output.z = z / w
    }
    output
  }


  def pointAtMatrix(pos: DDDpoint, target: DDDpoint, up: DDDvector) = {
    val newForward = pos -> target
    val x = newForward.scale(up.dotProduct(newForward))
    val newUp = up - x
    val newRight = newUp.crossProduct(newForward)

    val m = Array.ofDim[Double](4, 4)
    m(0)(0)=newRight.x
    m(1)(0)=newUp.x
    m(2)(0)=newForward.x
    m(3)(0)=pos.x

    m(0)(1)=newRight.y
    m(1)(1)=newUp.y
    m(2)(1)=newForward.y
    m(3)(1)=pos.y

    m(0)(2)=newRight.z
    m(1)(2)=newUp.z
    m(2)(2)=newForward.z
    m(3)(2)=pos.z

    m(0)(3)=0.0
    m(1)(3)=0.0
    m(2)(3)=0.0
    m(3)(3)=1.0

    m
  }


  def inverseMatrix(m: Array[Array[Double]]) = {
    val matrix = Array.ofDim[Double](4, 4)
    matrix(0)(0) = m(0)(0); matrix(0)(1) = m(1)(0); matrix(0)(2) = m(2)(0); matrix(0)(3) = 0.0
		matrix(1)(0) = m(0)(1); matrix(1)(1) = m(1)(1); matrix(1)(2) = m(2)(1); matrix(1)(3) = 0.0
		matrix(2)(0) = m(0)(2); matrix(2)(1) = m(1)(2); matrix(2)(2) = m(2)(2); matrix(2)(3) = 0.0
		matrix(3)(0) = -(m(3)(0) * matrix(0)(0) + m(3)(1) * matrix(1)(0) + m(3)(2) * matrix(2)(0))
		matrix(3)(1) = -(m(3)(0) * matrix(0)(1) + m(3)(1) * matrix(1)(1) + m(3)(2) * matrix(2)(1))
		matrix(3)(2) = -(m(3)(0) * matrix(0)(2) + m(3)(1) * matrix(1)(2) + m(3)(2) * matrix(2)(2))
		matrix(3)(3) = 1.0

    matrix
  }


  def translateMatrix(x: Double, y: Double, z: Double) = {
    val matrix = Array.ofDim[Double](4, 4)
    matrix(0)(0) = 1.0
    matrix(1)(1) = 1.0
    matrix(2)(2) = 1.0
    matrix(3)(3) = 1.0
    matrix(3)(0) = x
    matrix(3)(1) = y
    matrix(3)(2) = z
    matrix
  }
}
