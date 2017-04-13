### Welcome to the Bayes Filter!

![Thomas\_Bayes.gif](./Thomas_Bayes.gif)
*The Rev. Thomas Bayes*

This library can be used to categorise text, based on samples.

For example, if you have a directory of "business" news stories, and another of "technology" news stories, this code will find the probable category for the string `analysisText`.

````
Categoriser categoriser = new Categoriser()
    .category("Biz", new DirectorySampleSource("./data/samples/business"))
    .category("Tech", new DirectorySampleSource("./data/samples/technology"));
        
// Will be "Biz" or "Tech"
String probableCategory = categoriser.getProbableCategoryFor(analysisText);
````


#### What is a Bayesian filter

A Bayesian filter is typically used to associated a piece of text with other similar pieces of text. An example would be a spam filter: provide the filter with a large set of spam messages and non-spam messages, then ask if it a new email is more like a spam message or a non-spam message.

In this implementation, the Bayesian filter can be provided with a set of text categories, and will then return the name of the most likely category for any given piece of text.

#### How does a Bayesian filter work?

A Bayesian filter uses Bayes' theorem to calculate the probability of a piece of text matching a particular category.

Bayes' theorem is used to calculate probabilities based on evidence. With Bayes' theorem you can take this:

````
1. Probability(Email contains word 'x' given that it is a spam email)
````

and allows you to calculate the probability the other way round:

````
2. Probability(That this is a spam email given that it contains the word 'x')
````

The value of `1` is easy to evaluate; it is simply the number of instances of the word 'x', divided by the number of known spam emails. But the value of `2` is what can be used to decide if an email is spam.

When the categoriser in this library wants to decide if a piece of text matches a given category, it first calculates the probability that text is in the category for each word:

````
p0, p1, ..... , pn
````

It then finds the 15 word probabilities that are furthest from 0.5. These are the words that are most likely to tell us whether the text is in, or out, of the category. These probabilities are then combined into a single probability:

````
p0...p14 / (p0...p14 + (1 - p0)...(1-p14))
````

This is an approximation of:

````
Probability(This piece of text belongs in the category given the words it contains)
````

The library will find the category with the highest probability of a match, and return the name of that category.

#### Main concepts in the code

##### A concordance

A concordance is effectively a map of word frequencies. 

##### A sample source

Examples of a given category. It provides a concordance of the samples, and the number of items (e.g. files) in the source.

##### A text parser

This takes a piece of text, and breaks it down into words.

##### A categoriser

This takes a set of categories and calculates the probability of a piece of text belong to one of them.
