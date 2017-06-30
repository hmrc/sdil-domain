package hmrc.sdil.domain

import java.io.File
import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main._
import com.github.fge.jackson.JsonLoader
import com.fasterxml.jackson.databind.JsonNode

package object jsonvalidator {
  implicit class Validator(data: JsonNode) {
    val factory = JsonSchemaFactory.byDefault
    val validator = factory.getValidator

    def validateAgainst(schema: JsonNode): Either[ProcessingReport, JsonNode] = {
      val report = validator.validate(schema, data)
      if (report.isSuccess) {
        Right(data)
      } else {
        Left(report)
      }
    }
  }

  implicit class JsonHelper(val sc: StringContext) {
    def json(args: Any*): JsonNode = JsonLoader.fromString(sc.s(args))
    def jsonFile(args: Any*): JsonNode = JsonLoader.fromFile(new File(sc.parts.mkString(" ")))
  }
}
