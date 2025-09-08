package automaton.web.service

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.gpt.query.{ChatGPTQuery, ResumeAttributes}
import automaton.gpt.response.ChatGPTResponse
import automaton.pdf.reader.Reader
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import automaton.text.tokens.{LineItemToken, NameToken, ParagraphHeaderToken, SectionHeaderToken, ShortTextToken, Token}
import org.junit.runner.RunWith
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.junit.JUnitRunner
import web.service.ResumeService

import scala.collection.mutable

@RunWith(classOf[JUnitRunner])
class ResumeServiceSpec extends AnyFlatSpec {

  it should "work" in {
    val cache   = new Cache {
      val m = new mutable.HashMap[String, String]()

      override def put(value: String, tpe: String, time: Long): Unit = {
        m.put(time.toString + tpe, value)
      }

      override def get(time: String, tpe: String): String = {
        m(time + tpe)
      }
    }
    val writer  = new Writer {
      override def write(tokens: Seq[Token]): String = {
        assert(
          tokens == Seq(
            NameToken("Jimmy!"),
            ShortTextToken("12345678 - Fairplay, Colorado - email@address.com"),
            SectionHeaderToken("Professional Experience"),
            ParagraphHeaderToken("some role"),
            ShortTextToken("time — present"),
            LineItemToken("- did stuff"),
            LineItemToken("- and things"),
            LineItemToken("- and whatnot"),
            SectionHeaderToken("header"),
            LineItemToken("- good point"),
            LineItemToken("- solid stuff"),
            LineItemToken("- awesome things"),
          )
        )
        ""
      }
    }
    val reader  = new Reader {
      override def read(): String = "foo"
    }
    val service = new ResumeService(
      (query: ChatGPTQuery) => {
        new ChatGPTResponse {
          override def asString: String = {
            assert(
              query.text ==
                """
                  |please rewrite following resume for given job description and form output in markdown.
                  |
                  |resume:
                  |foo
                  |
                  |job description:
                  |description
                  |""".stripMargin
            )
            responseText
          }
        }
      },
      cache,
      Tokenizer.default(),
      writer,
      reader
    )
    service.resume(
      "description",
      ResumeAttributes()
    )
  }


  private val responseText =
    """
      |Got it ✅
      |I’ll restructure your resume into a **generic, streamlined markdown resume** that’s tailored to the Disney role, excludes education, project details, and ATS-style keyword stuffing. It will remain professional and emphasize breadth of experience without being overloaded.
      |
      |---
      |
      |# Jimmy!
      |12345678 - Fairplay, Colorado - email@address.com
      |
      |---
      |
      |## Professional Experience
      |
      |### some role
      |**time — present**
      |- did stuff
      |- and things
      |- and whatnot
      |
      |---
      |
      |## header
      |- good point
      |- solid stuff
      |- awesome things
      |
      |---
      |
      |👉 This rewrite is:
      |- very cool
      |- and
      |- fancy
      |
      |Would you like me to also do other things and stuff
      |
      |""".stripMargin
}
