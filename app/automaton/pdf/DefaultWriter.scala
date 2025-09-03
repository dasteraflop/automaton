package automaton.pdf


import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.{Chunk, Document, FontFactory, Paragraph}
import automaton.text.tokens.{BreakToken, LineItemToken, Token}

import java.io.FileOutputStream

private[pdf] class DefaultWriter extends Writer {

  private def chunk(token: Token): Chunk = {
    new Chunk(
      token.value,
      FontFactory.getFont(token.font, token.size, token.color)
    )
  }

  /**
   * Prints a single token using its parameters into the doc
   *
   * @param token
   * @param doc
   */
  private def printToken(
    token: Token
  )(
    implicit doc: Document
  ): Unit = {
    val paragraph = new Paragraph(chunk(token))
    paragraph.setAlignment(token.alignment)
    doc.add(paragraph)
    token.separator.map(
      s => {
        doc.add(s)
        doc.add(Chunk.NEWLINE)
      }
    )
  }

  /**
   * Prints grouped tokens and contains all list items into a list
   *
   * @param tokens
   * @param doc
   */
  private def printGroup(
    tokens: Seq[Token]
  )(
    implicit doc: Document
  ): Unit = {
    doc.add(Chunk.NEWLINE)
    val iterator = tokens.iterator

    iterator.takeWhile(
      {
        case _: LineItemToken => false
        case t: Token => true
      }
    ).foreach(printToken)

    //line items
    val list = new com.itextpdf.text.List(false, 2f)
    iterator.foreach(
      token => list.add(
        new com.itextpdf.text.ListItem(
          token.value,
          FontFactory.getFont(token.font, token.size, token.color)
        )
      )
    )
    doc.add(list)
  }

  /**
   * Iterates, groups, and prints all tokens
   *
   * @param head
   * @param tokens
   * @param group
   * @param doc
   */
  private def print(
    head:   Option[Token],
    tokens: Iterator[Token],
    group:  Seq[Token]
  )(
    implicit doc: Document
  ): Unit = {
    head match {
      case None =>
        printGroup(group)
      case Some(_: BreakToken) =>
        printGroup(group)
        print(tokens.nextOption(), tokens, Seq())
      case Some(_) =>
        print(tokens.nextOption(), tokens, group ++ Seq(head.get))
    }
  }

  def write(
    tokens: Seq[Token],
    path:   String
  ): Unit = {
    val os = new FileOutputStream(path, true)
    implicit val doc: Document = new Document()
    val writer = PdfWriter.getInstance(doc, os)
    doc.open()
    val iterator = tokens.iterator

    iterator.takeWhile(
      !_.isInstanceOf[BreakToken]
    ).foreach(printToken)

    print(iterator.nextOption(), iterator, Seq())

    //only on success, in theory
    doc.close()
  }
}
