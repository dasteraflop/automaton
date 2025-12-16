package automaton.pdf.writer

import automaton.pdf.writer.DefaultWriter.normalTextFont
import automaton.text.tokens.*
import automaton.utils.CommonUtils.timeFormat
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator

import java.io.{File, FileOutputStream}
import java.nio.file.{Files, Paths}

private[pdf] class DefaultWriter(
  config: WriterConfig
) extends Writer {

  def write(
    tokens: Seq[Token],
  ): String = {
    val path = s"${config.outputLocation}/${timeFormat.format(System.currentTimeMillis())}"
    if (!new File(path).exists()) {
      Files.createDirectory(
        Paths.get(path)
      )
    }
    println(s"writing output to '$path'")
    val os = new FileOutputStream(s"$path/resume.pdf", true)
    implicit val doc: Document = new Document()
    val writer = PdfWriter.getInstance(doc, os)
    val groups = groupTokens(tokens.head, tokens.tail, Seq(), Seq())

    doc.open()

    printHeader(groups.head, doc)
    groups.tail.foreach(printGroup(_, doc))

    //TODO: only on success?
    doc.close()
    path
  }

  private def printHeader(tokens: Seq[Token], doc: Document) = {
    println("printing header::::")
    tokens.foreach(println)
    val nameParagraph = new Paragraph(
      new Chunk(
        tokens.head.value,
        DefaultWriter.nameFont,
      )
    )
    nameParagraph.setAlignment(Element.ALIGN_CENTER)
    doc.add(nameParagraph)
    doc.add(new Chunk("\n", DefaultWriter.normalTextFont))

    doc.add(
      new Paragraph(
        new Chunk(
          tokens(1).value,
          DefaultWriter.boldTextFont
        )
      )
    )
    doc.add(
      new Paragraph(
        new Chunk(
          tokens(2).value,
          DefaultWriter.boldTextFont
        )
      )
    )
    doc.add(Chunk.NEWLINE)
  }

  private def chunk(token: Token): Chunk = {
    new Chunk(
      token.value,
      FontFactory.getFont(
        FontFactory.HELVETICA,
        10,
        BaseColor.BLACK
      )
    )
  }


  private def printGroup(group: Seq[Token], doc: Document) = {
    println("printing group::::")
    group.foreach(println)
    group.foreach(
      {
        case t: TextToken =>
          doc.add(new Paragraph(new Chunk(t.value, DefaultWriter.normalTextFont)))
        case t: BoldTextToken =>
          doc.add(new Paragraph(new Chunk(t.value, DefaultWriter.boldTextFont)))
        case t: HeaderToken =>
          t.size match {
            case 2 =>
              val p = new Paragraph(new Chunk(t.value, DefaultWriter.sectionHeaderFont))
              p.setAlignment(Element.ALIGN_CENTER)
              doc.add(p)
              doc.add(DefaultWriter.separator)
              doc.add(Chunk.NEWLINE)
            case 3 =>
              doc.add(new Paragraph(new Chunk(t.value, DefaultWriter.boldTextFont)))
          }
        case _: BreakToken =>
        //          doc.add(Chunk.NEWLINE)
        case t: LineItemToken =>
          val list = new com.itextpdf.text.List(false, 2f)
          list.add(
            new com.itextpdf.text.ListItem(
              t.value,
              DefaultWriter.normalTextFont
            )
          )
          doc.add(list)

        //          doc.add(new Paragraph(new Chunk(t.value, DefaultWriter.nameFont)))
      }
    )
    doc.add(Chunk.NEWLINE)
  }

  private def groupTokens(head: Token, tokens: Seq[Token], group: Seq[Token], result: Seq[Seq[Token]]): Seq[Seq[Token]] = {
    head match {
      case p: ParagraphToken =>
        //we should never have paragraph as last item
        groupTokens(tokens.head, tokens.tail, Seq(), result ++ Seq(group))
      case t: Token =>
        if (tokens.isEmpty)
          return result ++ Seq(group ++ Seq(head))
        else
          groupTokens(tokens.head, tokens.tail, group ++ Seq(head), result)
    }
  }
}

object DefaultWriter {

  private val nameFont: Font = FontFactory.getFont(
    FontFactory.HELVETICA,
    16,
    BaseColor.BLACK
  )

  private val sectionHeaderFont: Font = FontFactory.getFont(
    FontFactory.HELVETICA,
    12,
    BaseColor.BLACK
  )

  private val boldTextFont: Font = FontFactory.getFont(
    FontFactory.HELVETICA_BOLD,
    8,
    BaseColor.BLACK
  )

  private val normalTextFont: Font = FontFactory.getFont(
    FontFactory.HELVETICA,
    8,
    BaseColor.BLACK
  )

  private val separator = new LineSeparator(
    FontFactory.getFont(
      FontFactory.HELVETICA,
      10,
      BaseColor.BLACK
    )
  )
}
