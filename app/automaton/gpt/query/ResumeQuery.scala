package automaton.gpt.query

import com.openai.models.ChatModel

case class ResumeQuery(
  resume:      String,
  description: String,
  attributes:  ResumeAttributes,
  model:       ChatModel,
) extends ChatGPTQuery {

  override lazy val text: String =
    s"""
       |please rewrite following resume for given job description and form output in markdown.
       |${attributesText()}
       |resume:
       |${resume}
       |
       |job description:
       |${description}
       |""".stripMargin

  private def attributesText(): String = {
    val attributeText = Seq(
      attributes.relevance.flatMap(
        {
          case true => Some("include paragraph for relevance to project")
          case false => Some("exclude all relevance to project and make resume generic")
        }
      ),
      attributes.atsFriendly.flatMap(
        {
          case true => Some("include ATS-friendly keywords")
          case false => Some("exclude ATS-friendly keywords")
        }
      ),
      attributes.education.flatMap(
        {
          case true => Some("include education paragraph")
          case false => Some("exclude all education information")
        }
      )
    ).flatten
    if (attributeText.isEmpty)
      ""
    else {
      s"""
         |please also:
         |${attributeText.mkString(",\n")}
         |""".stripMargin
    }

  }

}
