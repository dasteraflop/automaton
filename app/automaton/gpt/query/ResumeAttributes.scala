package automaton.gpt.query

/**
 * Attributes to in/exclude.
 *
 * True to include, false to exclude
 *
 * @param atsFriendly include ATS keywords
 * @param relevance   include relevance to project paragraph
 */
case class ResumeAttributes(
  atsFriendly: Option[Boolean] = None,
  relevance:   Option[Boolean] = None,
  education:   Option[Boolean] = None,
) {

}
