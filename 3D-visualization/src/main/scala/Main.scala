import threeD._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene._


object Main extends JFXApp3 {


  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "MAIN"
      resizable = false
      scene = new Scene(720, 720) {
        val dddSCreen = new DDDscreen
        content = dddSCreen
      }
    }
  }

}
