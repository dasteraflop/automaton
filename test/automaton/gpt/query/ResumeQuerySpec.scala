//package automaton.gpt.query
//
//import automaton.gpt.client.{ChatGPTClientConfig, ChatGPTGenerator}
//import automaton.gpt.query.ResumeAttributes
//import org.junit.runner.RunWith
//import org.scalatest.flatspec.AnyFlatSpec
//import org.scalatestplus.junit.JUnitRunner
//
//@RunWith(classOf[JUnitRunner])
//class ResumeQuerySpec extends AnyFlatSpec {
//
//
//  "ResumeQuery" should "build without attributes" in {
//    val expected =
//      """
//        |Please rewrite following resume for given job description.
//        |
//        |Respond with a raw markdown document.
//        |
//        |
//        |resume:
//        |current text
//        |
//        |job description:
//        |description string
//        |
//        |include following ATS keywords: cool skill
//        |
//        |exclude following ATS keywords: no want
//        |meh
//        |nah
//        |
//        |""".stripMargin
//    val text     = ChatGPTQuery.resume(
//      "current text",
//      "description string",
//      Seq("cool skill"),
//      Seq("no want", "meh", "nah"),
//      ResumeAttributes(),
//    ).text
//
//    assert(
//      text == expected
//    )
//  }
//
//  it should "build with 1 attribute" in {
//    val expected =
//      """
//        |
//        |Please rewrite following resume for given job description.
//        |
//        |Respond with a raw markdown document.
//        |
//        |
//        |please also:
//        |include ATS-friendly keywords
//        |
//        |resume:
//        |current text
//        |
//        |job description:
//        |description string
//        |
//        |
//        |
//        |
//        |
//        |
//        |"""
//    val text     = ChatGPTQuery.resume(
//      "current text",
//      "description string",
//      Seq(),
//      Seq(),
//      ResumeAttributes(
//        Some(true)
//      ),
//    ).text
//
//    println("***actual")
//    println(text)
//    println("***")
//
//
//    println("***expected")
//    println(expected)
//    println("***")
//    assert(
//      text == expected
//
//    )
//  }
//
//  //  it should "build with a mix of attribute values" in {
//  //    val text = ChatGPTQuery.resume(
//  //      "current text",
//  //      "description string",
//  //      ResumeAttributes(
//  //        Some(true),
//  //        Some(true),
//  //        Some(false),
//  //      ),
//  //    ).text
//  //
//  //    assert(
//  //      text ==
//  //        """
//  //          |please rewrite following resume for given job description and form output in markdown.
//  //          |
//  //          |please also:
//  //          |include paragraph for relevance to project,
//  //          |include ATS-friendly keywords,
//  //          |exclude all education information
//  //          |
//  //          |resume:
//  //          |current text
//  //          |
//  //          |job description:
//  //          |description string
//  //          |""".stripMargin
//  //    )
//  //  }
//
//}
