### Welcome to the Bayes Filter!

![Thomas\_Bayes.gif](./Thomas_Bayes.gif)
*The Rev. Thomas Bayes*

This library can be used to categorise text, based on samples.

For example, if you have a directory of "business" news stories, and another of "technology" news stories, this code will find the probably category for the string `analysisText`.

````
HashMap<String, SampleSource> sampleSourceHashMap = new HashMap<>();
sampleSourceHashMap.put("business", new DirectorySampleSource(new File("./data/samples/business")));
sampleSourceHashMap.put("technology", new DirectorySampleSource(new File("./data/samples/technology")));

Categoriser categoriser = new Categoriser(sampleSourceHashMap);

String probableCategory = categoriser.getProbableCategoryFor(analysisText);
````

