<div id="content">

Welcome to the Bayes Filter!

<div class="figure">

![Thomas\_Bayes.gif](./Thomas_Bayes.gif)

</div>

<div class="org-src-container">

``` {.src .src-java}
HashMap<String, SampleSource> sampleSourceHashMap = new HashMap<>();
sampleSourceHashMap.put("business", new DirectorySampleSource(new File("./data/samples/business")));
sampleSourceHashMap.put("technology", new DirectorySampleSource(new File("./data/samples/technology")));

Categoriser categoriser = new Categoriser(sampleSourceHashMap);

String probableCategory = categoriser.getProbableCategoryFor(analysisText);
```

</div>

</div>

<div id="postamble" class="status">

Author: David Griffiths

Created: 2017-04-12 Wed 17:31

[Emacs](http://www.gnu.org/software/emacs/) 24.5.1
([Org](http://orgmode.org) mode 8.2.10)

[Validate](http://validator.w3.org/check?uri=referer)

</div>
