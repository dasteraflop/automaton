package automaton.utils

import automaton.gpt.cache.Cache
import automaton.gpt.client.ChatGPTGenerator
import automaton.pdf.reader.Reader
import automaton.pdf.writer.Writer
import automaton.text.Tokenizer
import play.api.inject.Binding
import play.api.{Configuration, Environment}

class ModuleInitializer extends play.api.inject.Module {
  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = {
    Seq(
      bind[Cache].toInstance(Cache.default()),
      bind[ChatGPTGenerator].toInstance(ChatGPTGenerator.default()),
      bind[Writer].toInstance(Writer.default()),
      bind[Reader].toInstance(Reader.default()),
      bind[Tokenizer].toInstance(Tokenizer.default()),
    )
  }
}

