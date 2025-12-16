package automaton.gpt.query

import com.openai.models.ChatModel

case class ResumeQuery(
  resume:      String,
  description: String,
  include:     Seq[String],
  exclude:     Seq[String],
  attributes:  ResumeAttributes,
  model:       ChatModel,
) extends ChatGPTQuery {

  override lazy val text: String =
    s"""
       |Please rewrite following resume for given job description.
       |
       |Respond with a raw markdown document.
       |
       |${attributesText()}
       |resume:
       |${resume}
       |
       |job description:
       |${description}
       |
       |${includes}
       |
       |${excludes}
       |
       |""".stripMargin
  private lazy val excludes = {
    if (exclude.nonEmpty)
      s"exclude following ATS keywords: ${exclude.mkString("\n")}"
    else
      ""
  }
  private lazy val includes = {
    if (include.nonEmpty)
      s"include following ATS keywords: ${include.mkString("\n")}"
    else
      ""
  }

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
