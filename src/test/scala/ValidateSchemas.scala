package hmrc.sdil.domain

import jsonvalidator._
import org.scalatest._
import java.io.File
import com.github.fge.jackson.JsonLoader

class TestSchemas extends WordSpec with Matchers {

  val schemas = 
    new File(".").listFiles.filter(_.getName.endsWith(".schema.json"))

  schemas foreach { schema =>
    schema.getName should {
      "parse" in {
        val jsonSchema = JsonLoader.fromFile(schema)
      }

      "have a valid accompanying JSON example" in {
        val jsonSchema = JsonLoader.fromFile(schema)
        val jsonSample = JsonLoader.fromFile(new File(schema.getName.replaceFirst("schema","sample")));
        {jsonSample validateAgainst jsonSchema} should be {Right(jsonSample)}
      }
    }
  }
}
