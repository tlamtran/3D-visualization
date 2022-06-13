package threeD

import scalafx.animation.AnimationTimer
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color._


class DDDscreen extends Canvas(720, 720.0) {

//-------------------------------------------------------------------------------------
  private val matrix = new DDDmatrix

  var camera = new DDDpoint(0, 0, 0)
  var target = new DDDpoint(0, 0, 0)
  val up = new DDDvector(0, -1, 0, true)
  var lookDir = camera -> target

  var theta = 1.0
  var matCamera = matrix.pointAtMatrix(camera, target, up)
  var matView = matrix.inverseMatrix(matCamera)
  var matRotX = matrix.createMatRotX(theta)
  var matRotZ = matrix.createMatRotZ(theta * 0.5)
  var matTran = matrix.translateMatrix(0, 0, 3)
  var matProj = matrix.createMatProj(0.1, 1000.0, 90.0)

  val light = new DDDvector(0, 0, -1, true)
  val ambientLight = 0.05
//-----------------------------------------------------------------------------------

  var polygons: Vector[Polygon] = Vector()
  // CUBE 1x1x1
  polygons = polygons :+ new Polygon( new DDDpoint(0, 0, 0), new DDDpoint(0, 1, 0), new DDDpoint(1, 1, 0), new DDDpoint(1, 0, 0), Gray, this)
  polygons = polygons :+ new Polygon( new DDDpoint(1, 0, 1), new DDDpoint(1, 1, 1), new DDDpoint(0, 1, 1), new DDDpoint(0, 0, 1), Gray, this)
  polygons = polygons :+ new Polygon( new DDDpoint(1, 0, 0), new DDDpoint(1, 1, 0), new DDDpoint(1, 1, 1), new DDDpoint(1, 0, 1), Gray, this)
  polygons = polygons :+ new Polygon( new DDDpoint(1, 1, 0), new DDDpoint(0, 1, 0), new DDDpoint(0, 1, 1), new DDDpoint(1, 1, 1), Gray, this)
  polygons = polygons :+ new Polygon( new DDDpoint(0, 0, 1), new DDDpoint(0, 1, 1), new DDDpoint(0, 1, 0), new DDDpoint(0, 0, 0), Gray, this)
  polygons = polygons :+ new Polygon( new DDDpoint(0, 0, 1), new DDDpoint(0, 0, 0), new DDDpoint(1, 0, 0), new DDDpoint(1, 0, 1), Gray, this)


  def yawHorizontally(yaw: Double) = {
    val matYaw = matrix.createMatRotY(yaw)
    target = matrix.multiplyPointMatrix(target, matYaw)
    lookDir = camera -> target
  }

  // updates elements accordingly
  def refresh(): Unit = {
    //update matrices
    theta += 0.05
    matRotX = matrix.createMatRotX(theta)
    matRotZ = matrix.createMatRotZ(theta * 0.5)
    matCamera = matrix.pointAtMatrix(camera, target, up)
    matView = matrix.inverseMatrix(matCamera)

    polygons.foreach(_.update())
    polygons.sortBy(_.averageDistance(camera))(Ordering[Double].reverse) // after sorting draws the furthest polygons first
    this.graphicsContext2D.clearRect(0, 0, 2000, 1200)
    this.graphicsContext2D.fill = Black
    this.graphicsContext2D.fillRect(0, 0, 2000, 1200)
    polygons.foreach(_.draw())
    this.graphicsContext2D.fill = White
    this.graphicsContext2D.fillText(System.currentTimeMillis().toString,20, 20)
  }

  // setup for 60 fps i.e. calls refresh 60 times in a second
  val sleepTime = 1000.0 / 60.0
  var lastRefresh: Long = 0
  val timer = AnimationTimer{ time =>
    if ((System.currentTimeMillis() - lastRefresh) > sleepTime) {
    lastRefresh = System.currentTimeMillis()
     refresh()
    }
  }
  timer.start()



}

