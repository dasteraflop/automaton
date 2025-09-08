package automaton.gpt.query

import automaton.gpt.client.{ChatGPTClientConfig, ChatGPTGenerator}
import automaton.gpt.query.ResumeAttributes
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ResumeQuerySpec extends AnyFlatSpec {


  "ResumeQuery" should "build without attributes" in {
    val text = ChatGPTQuery.resume(
      "current text",
      "description string",
      ResumeAttributes(),
    ).text

    assert(
      text ==
        """
          |please rewrite following resume for given job description and form output in markdown.
          |
          |resume:
          |current text
          |
          |job description:
          |description string
          |""".stripMargin
    )
  }

  it should "build with 1 attribute" in {
    val text = ChatGPTQuery.resume(
      "current text",
      "description string",
      ResumeAttributes(
        Some(true)
      ),
    ).text

    assert(
      text ==
        """
          |please rewrite following resume for given job description and form output in markdown.
          |
          |please also:
          |include ATS-friendly keywords
          |
          |resume:
          |current text
          |
          |job description:
          |description string
          |""".stripMargin
    )
  }

  it should "build with 2 attributes" in {
    val text = ChatGPTQuery.resume(
      "current text",
      "description string",
      ResumeAttributes(
        Some(true),
        Some(true),
      ),
    ).text

    assert(
      text ==
        """
          |please rewrite following resume for given job description and form output in markdown.
          |
          |please also:
          |include paragraph for relevance to project,
          |include ATS-friendly keywords
          |
          |resume:
          |current text
          |
          |job description:
          |description string
          |""".stripMargin
    )
  }

  it should "build with a mix of attribute values" in {
    val text = ChatGPTQuery.resume(
      "current text",
      "description string",
      ResumeAttributes(
        Some(true),
        Some(true),
        Some(false),
      ),
    ).text

    assert(
      text ==
        """
          |please rewrite following resume for given job description and form output in markdown.
          |
          |please also:
          |include paragraph for relevance to project,
          |include ATS-friendly keywords,
          |exclude all education information
          |
          |resume:
          |current text
          |
          |job description:
          |description string
          |""".stripMargin
    )
  }

}
