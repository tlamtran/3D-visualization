package threeD

import scalafx.scene.paint.Color._
import scalafx.scene.paint.Color


// Our polygon has points A, B, C and D, where each point has coordinates x, y, z
// Here we transform the points from 3D to 2D i.e. x, y, z ---> x, y
class Polygon(A: DDDpoint, B: DDDpoint, C: DDDpoint, D: DDDpoint, c: Color, screen: DDDscreen) {

//--------------------------------------------------------------------------------------------------------------------
  private val matrix = new DDDmatrix

  private var points = Array(A, B, C, D)
  private var lightingColor: Color = c

  points = points.map(p => matrix.multiplyPointMatrix(p, screen.matRotZ)) // apply rot Z on each point
                 .map(p => matrix.multiplyPointMatrix(p, screen.matRotX)) // apply rot X on each point

  // crossProduct of vectors AB and AC = normal to the surface of the polygon
  var AB = points(0) -> points(1)
  var AC = points(0) -> points(2)
  var normal = AB.crossProduct(AC) // used in lighting and to check whether the polygon is drawable

  updateLighting(screen.light)

  //points = points.map(p => matrix.multiplyPointMatrix(p, screen.matView))
  points = points.map(p => matrix.multiplyPointMatrix(p, screen.matTran))
  points = points.map(p => matrix.multiplyPointMatrix(p, screen.matProj))
//----------------------------------------------------------------------------------------------------------------------



  // used in painter's algorithm
  def averageDistance(target: DDDpoint): Double = points.map(p => target.distance(p)).sum / points.length


  def updateLighting(lightV: DDDvector): Unit = {
    var dot = this.normal.dotProduct(lightV)
    val sign = dot.sign
    dot = sign * dot * dot
    dot = (dot + 1.0) / 2.0 * 0.8

    val ligntRatio = scala.math.min(screen.ambientLight + dot, 1)

    val red = c.getRed * 255 * ligntRatio
    val green = c.getGreen * 255 * ligntRatio
    val blue = c.getBlue * 255 * ligntRatio
    this.lightingColor = rgb(red.toInt, green.toInt, blue.toInt)
  }

  // updates positions and lighting according to the changes in matrices
  def update() = {
    points = Array(A, B, C, D)

    points = points.map(p => matrix.multiplyPointMatrix(p, screen.matRotZ))
                   .map(p => matrix.multiplyPointMatrix(p, screen.matRotX))

    AB = points(0).->(points(1))
    AC = points(0).->(points(2))
    normal = AB.crossProduct(AC)
    updateLighting(screen.light)

    //points = points.map(p => matrix.multiplyPointMatrix(p, screen.matView))
    points = points.map(p => matrix.multiplyPointMatrix(p, screen.matTran))
    points = points.map(p => matrix.multiplyPointMatrix(p, screen.matProj))
  }


  def draw() = {
    // draw the polygon only if normal of the surface within range
    val lineCameraToPoly = screen.camera.->(points(0))
    if (normal.dotProduct(lineCameraToPoly) < 0) {
      val g = screen.graphicsContext2D
      g.fill = this.lightingColor
      g.fillPolygon(Seq( ((points(0).x+1)*360, (points(0).y+1)*360),
                         ((points(1).x+1)*360, (points(1).y+1)*360),
                         ((points(2).x+1)*360, (points(2).y+1)*360),
                         ((points(3).x+1)*360, (points(3).y+1)*360)))

      g.fill = Black
      g.strokePolyline(Seq( ((points(0).x+1)*360, (points(0).y+1)*360),
                            ((points(1).x+1)*360, (points(1).y+1)*360),
                            ((points(2).x+1)*360, (points(2).y+1)*360),
                            ((points(3).x+1)*360, (points(3).y+1)*360),
                            ((points(0).x+1)*360, (points(0).y+1)*360)))
    }
  }


}
