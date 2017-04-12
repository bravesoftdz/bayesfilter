<div id="content">

Welcome to the Bayes Filter!

![Thomas\_Bayes.gif](./Thomas_Bayes.gif)

``` {.src .src-java}
HashMap<String, SampleSource> sampleSourceHashMap = new HashMap<>();
sampleSourceHashMap.put("business", new DirectorySampleSource(new File("./data/samples/business")));
sampleSourceHashMap.put("technology", new DirectorySampleSource(new File("./data/samples/technology")));

Categoriser categoriser = new Categoriser(sampleSourceHashMap);

String probableCategory = categoriser.getProbableCategoryFor(analysisText);
```

