### Environment vars:

AUTOMATON_GPT_TOKEN - chat GPT token

AUTOMATON_ORIGINAL_LOCATION - plain text location of old resume

AUTOMATON_DESCRIPTION_LOCATION - job you want to rewrite for

AUTOMATON_OUTPUT_LOCATION - where resulting PDF is written to

AUTOMATON_CACHE_LOCATION - location where GPT inputs and outputs will be stored at

### Other:

AUTOMATON_RESPONSE_TIME - if provided, will rewrite a new PDF from cached result

### And then:

`sbt assembly`

`java -jar automaton-assembly-0.0.1-SNAPSHOT.jar`

### Web variant:

`sbt dist`
and run executable for play app and use with something like plugin in `opera-plugin`